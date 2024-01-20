package com.example.contactapp.notification

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

object MyManagers {
    const val channelId = "one-channel"
    private const val channelName = "My Channel One"
    var notificationManager: NotificationManager? = null
    var alarmManager: AlarmManager? = null

    /** 한 번만 불려야 함. 앱 처음? */
    fun init(context: Context) {
        if (notificationManager != null || alarmManager != null) {
            Log.e("myTag", "매니저가 있는데 init이 호출됨")
        }
        notificationManager = makeNotificationManager(context)
        alarmManager = context.getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager
    }

    fun makeNotificationManager(context: Context) =
        (context.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager).apply {
            // 안드 8.0 Oreo 이상부터는 알림 채널을 먼저 만들어야 한다
            createNotificationChannel(
                NotificationChannel(
                    channelId,
                    channelName,
                    NotificationManager.IMPORTANCE_DEFAULT
                ).apply {
                    description = "My Channel One Description"
                    setShowBadge(true)
                    val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)!!
                    val audioAttributes = AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .setUsage(AudioAttributes.USAGE_ALARM)
                        .build()
                    setSound(uri, audioAttributes)
                    enableVibration(true)
                })
        }
}