package com.example.contactapp.data

import android.graphics.Canvas
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class StickyHeaderItemDecoration(
    recyclerView: RecyclerView,
    private val isHeader: (itemPosition: Int) -> Boolean
) : RecyclerView.ItemDecoration() {

    private var currentHeaderToShow: Pair<Int, RecyclerView.ViewHolder>? = null

    init {
        recyclerView.adapter?.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                currentHeaderToShow = null
            }
        })

        recyclerView.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
            currentHeaderToShow = null
        }
    }

    override fun onDrawOver(c: Canvas, recyclerView: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, recyclerView, state)

        val topChildView = getTopChildView(recyclerView) ?: return
        val topChildViewPosition = recyclerView.getChildAdapterPosition(topChildView)
        if (topChildViewPosition == RecyclerView.NO_POSITION) return

        val headerView = getHeaderViewToShow(topChildViewPosition, recyclerView) ?: return

        val contactedNewHeader = getContactedNewHeader(headerView, recyclerView)
        if (contactedNewHeader != null) {
            drawMovedHeader(c, headerView, contactedNewHeader)
        } else {
            drawHeader(c, headerView, recyclerView.paddingTop)
        }
    }

    private fun getTopChildView(recyclerView: RecyclerView)
            = recyclerView.findChildViewUnder(
        recyclerView.paddingStart.toFloat(),
        recyclerView.paddingTop.toFloat()
    )

    private fun getHeaderViewToShow(topChildItemPosition: Int, recyclerView: RecyclerView): View? {
        recyclerView.adapter ?: return null

        val headerPositionToShow = getHeaderPositionToShow(topChildItemPosition)
        if (headerPositionToShow == RecyclerView.NO_POSITION) return null

        return getHeaderView(headerPositionToShow, recyclerView)
    }

    private fun getHeaderView(headerPositionToShow: Int, recyclerView: RecyclerView): View? {
        val headerHolderType = recyclerView.adapter!!.getItemViewType(headerPositionToShow)
        if (currentHeaderToShow?.first == headerPositionToShow && currentHeaderToShow?.second?.itemViewType == headerHolderType) {
            return currentHeaderToShow?.second?.itemView
        }

        val headerHolder = recyclerView.adapter!!.createViewHolder(recyclerView, headerHolderType)
        recyclerView.adapter!!.onBindViewHolder(headerHolder, headerPositionToShow)
        fixLayoutSize(recyclerView, headerHolder.itemView)

        currentHeaderToShow = headerPositionToShow to headerHolder

        return headerHolder.itemView
    }

    private fun getHeaderPositionToShow(topChildItemPosition: Int): Int {
        var headerPosition = RecyclerView.NO_POSITION
        var currentPosition = topChildItemPosition
        do {
            if (isHeader(currentPosition)) {
                headerPosition = currentPosition
                break
            }
            currentPosition -= 1
        } while (currentPosition >= 0)
        return headerPosition
    }

    private fun fixLayoutSize(parent: ViewGroup, view: View) {

        val widthSpec = View.MeasureSpec.makeMeasureSpec(parent.width, View.MeasureSpec.EXACTLY)
        val heightSpec = View.MeasureSpec.makeMeasureSpec(parent.height, View.MeasureSpec.UNSPECIFIED)


        val childWidthSpec = ViewGroup.getChildMeasureSpec(
            widthSpec,
            parent.paddingStart + parent.paddingEnd,
            view.layoutParams.width
        )
        val childHeightSpec = ViewGroup.getChildMeasureSpec(
            heightSpec,
            parent.paddingTop + parent.paddingBottom,
            view.layoutParams.height
        )

        view.measure(childWidthSpec, childHeightSpec)
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
    }

    private fun drawHeader(c: Canvas, header: View, paddingTop: Int) {
        c.save()
        c.translate(0f, paddingTop.toFloat())
        header.draw(c)
        c.restore()
    }

    private fun getContactedNewHeader(headerView: View, recyclerView: RecyclerView): View? {
        val contactPoint = headerView.bottom + recyclerView.paddingTop
        val contactedChildView = getContactedChildView(recyclerView, contactPoint) ?: return null
        val contactedChildViewPosition = recyclerView.getChildAdapterPosition(contactedChildView)

        return if (isHeader(contactedChildViewPosition)) {
            contactedChildView
        } else {
            null
        }
    }

    private fun getContactedChildView(recyclerView: RecyclerView, contactPoint: Int): View? {
        var childInContact: View? = null
        for (i in 0 until recyclerView.childCount) {
            val child = recyclerView.getChildAt(i)
            val bounds = Rect()
            recyclerView.getDecoratedBoundsWithMargins(child, bounds)
            if (bounds.bottom > contactPoint) {
                if (bounds.top <= contactPoint) {
                    childInContact = child
                    break
                }
            }
        }
        return childInContact
    }

    private fun drawMovedHeader(c: Canvas, contactedTopHeader: View, contactedBottomHeader: View) {
        c.save()
        c.translate(0f, (contactedBottomHeader.top - contactedTopHeader.height).toFloat())
        contactedTopHeader.draw(c)
        c.restore()
    }
}