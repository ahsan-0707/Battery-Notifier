package com.example.batterynotifier.ui.theme.BatteryReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class BatteryReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            Intent.ACTION_POWER_CONNECTED -> {
                // Broadcast to notify power is connected
                LocalBroadcastManager.getInstance(context)
                    .sendBroadcast(Intent("com.example.batterynotifier.POWER_CONNECTED"))
            }
            Intent.ACTION_POWER_DISCONNECTED -> {
                // Broadcast to notify power is disconnected
                LocalBroadcastManager.getInstance(context)
                    .sendBroadcast(Intent("com.example.batterynotifier.POWER_DISCONNECTED"))
            }
        }
    }
}
