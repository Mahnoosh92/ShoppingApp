package com.mahnoosh.core.di

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.mahnoosh.core.R
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val notificationModule = module {
    single {
        provideNotificationBuilder(context = androidContext())
    }
    single {
        provideNotificationManager(context = androidContext())
    }
}

private fun provideNotificationBuilder(
    context: Context
): NotificationCompat.Builder {
    return NotificationCompat.Builder(
        context,
        R.string.notification_channel_id_firebase.toString()
    )
}


private fun provideNotificationManager(
    context: Context
): NotificationManagerCompat {
    val notificationManager = NotificationManagerCompat.from(context)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            R.string.notification_channel_id_firebase.toString(),
            R.string.notification_channel_name_firebase.toString(),
            NotificationManager.IMPORTANCE_HIGH
        )
        channel.enableLights(true)
        channel.lightColor = Color.RED
        channel.enableVibration(true)
        notificationManager.createNotificationChannel(channel)
    }
    return notificationManager
}