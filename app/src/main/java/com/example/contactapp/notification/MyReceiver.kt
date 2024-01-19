package com.example.contactapp.notification

import android.app.Notification
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.contactapp.R
import com.example.contactapp.activity.ContactActivity

class MyReceiver : BroadcastReceiver() {
//    // TODO: 앱 종료하면 이것도 날아가고 매니저도 날아감. 매니저를 리시버에서 새로 만들어줘야할듯.
//    companion object {
//        var savedNotification: Notification? = null
//    }

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("myTag", "리시버 들어옴")

        // 알림창 클릭 시 실행할 액티비티
        val intent2 = Intent(context, ContactActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context, 101, intent2,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val title = intent.getStringExtra("title") ?: "알림 왔어용~ (제목 없음)"
        val text = intent.getStringExtra("text") ?: "알려줄 내용이 없네용~"

        val builder = NotificationCompat.Builder(context, MyManagers.channelId).apply {
            setSmallIcon(R.drawable.call_logo)
            setWhen(System.currentTimeMillis())  // 이건 언제 보일지가 아니라 이 알림이 언제 알림인지 정보임
            setContentTitle(title)
            setContentText(text)
            setContentIntent(pendingIntent)
            setAutoCancel(true)
//            addAction(R.mipmap.ic_launcher, "Action", pendingIntent)
        }

        MyManagers.notificationManager?.notify(11, builder.build())
            ?: Log.e("myTag", "노티 매니저 없음")

//        // TODO: 알람 설정해두고 앱 종료하면, 노티 매니저, 세이브드노티 다 날아간다.
//        if (savedNotification != null) {
//            MyManagers.notificationManager?.notify(11, savedNotification)
//                ?: Log.e("myTag", "노티 매니저 없음 (세이브드노티 o)")
//        } else {
//            MyManagers.notificationManager?.notify(11, builder.build())
//                ?: Log.e("myTag", "노티 매니저 없음 (세이브드노티 x)")
//        }

    }
}