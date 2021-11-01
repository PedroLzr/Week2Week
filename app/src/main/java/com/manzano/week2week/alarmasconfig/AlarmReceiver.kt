package com.manzano.week2week.alarmasconfig

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmReceiver : BroadcastReceiver() {

    //Este método se llama cuando el BroadcastReceiver recibe un Intent broadcast
    override fun onReceive(context: Context, intent: Intent) {
        val notificationUtils = NotificationUtils(context)
        val notification = notificationUtils.getNotificationBuilder().build()
        notificationUtils.getManager().notify(150, notification)
    }
}