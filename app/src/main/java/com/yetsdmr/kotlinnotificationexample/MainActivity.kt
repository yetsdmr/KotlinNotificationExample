package com.yetsdmr.kotlinnotificationexample

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RemoteViews
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var notificationManager : NotificationManager
    lateinit var notificationChannel : NotificationChannel
    lateinit var builder : Notification.Builder
    private val channelId = "com.yetsdmr.kotlinnotificationexample"
    private val description = "Test notification"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        btnNotify.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT)

            val contentView = RemoteViews(packageName,R.layout.notification_layout)
            contentView.setTextViewText(R.id.tvTitle,"Yetsdmr")
            contentView.setTextViewText(R.id.tvContent,"Text notification")

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                notificationChannel = NotificationChannel(channelId,description,NotificationManager.IMPORTANCE_HIGH)
                notificationChannel.enableLights(true)
                notificationChannel.lightColor = Color.GREEN
                notificationChannel.enableVibration(false)
                notificationManager.createNotificationChannel(notificationChannel)

                builder = Notification.Builder(this,channelId)
                    .setContent(contentView)
                    .setSmallIcon(R.drawable.ic_launcher_round)
                    .setLargeIcon(BitmapFactory.decodeResource(this.resources,R.drawable.ic_launcher))
                    .setContentIntent(pendingIntent)

            }else {

                builder = Notification.Builder(this)
                    .setContent(contentView)
                    .setSmallIcon(R.drawable.ic_launcher_round)
                    .setLargeIcon(BitmapFactory.decodeResource(this.resources,R.drawable.ic_launcher))
                    .setContentIntent(pendingIntent)
            }

            notificationManager.notify(1234,builder.build())

        }
    }
}