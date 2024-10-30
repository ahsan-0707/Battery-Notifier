package com.example.batterynotifier

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build
import androidx.annotation.RequiresApi

class PowerConnectionReceiver : BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context, intent: Intent) {
        val batteryLevel = getBatteryLevel(context)
        val isCharging = intent.action == Intent.ACTION_POWER_CONNECTED

        val serviceIntent = Intent(context, BatteryService::class.java).apply {
            putExtra("batteryLevel", batteryLevel)
            putExtra("isCharging", isCharging)
        }
        context.startForegroundService(serviceIntent)
    }

    private fun getBatteryLevel(context: Context): Int {
        val intent = IntentFilter(Intent.ACTION_BATTERY_CHANGED).let { filter ->
            context.registerReceiver(null, filter)
        }
        return intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
    }
}
