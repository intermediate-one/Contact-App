package com.example.contactapp.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import com.example.contactapp.R
import com.example.contactapp.databinding.FragmentDialogBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddContactFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddContactFragment : DialogFragment() {
    // TODO: Rename and change types of parameters
    private lateinit var binding: FragmentDialogBinding



    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDialogBinding.inflate(layoutInflater)
        val view = binding.root

        // 레이아웃 배경을 투명하게 해줌 .. (필수 아님)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        // 다이얼로그 바깥 부분 클릭 시 뒤로가기
        dialog?.setCancelable(true)
        dialog?.setContentView(view)
        dialog?.window?.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_contact, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DialogFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddContactFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}