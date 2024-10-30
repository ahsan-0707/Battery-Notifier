package com.example.batterynotifier

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Text
import com.example.batterynotifier.ui.theme.BatteryNotifierTheme

class MainActivity : ComponentActivity() {

    private val batteryLevel = mutableStateOf(0)
    private val isPowerConnected = mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intentFilter = IntentFilter().apply {
            addAction(Intent.ACTION_BATTERY_CHANGED)
            addAction(Intent.ACTION_POWER_CONNECTED)
            addAction(Intent.ACTION_POWER_DISCONNECTED)
        }
        registerReceiver(BatteryReceiver(), intentFilter)

        setContent {
            BatteryNotifierTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    BatteryNotificationScreen(
                        batteryLevel = batteryLevel.value,
                        isPowerConnected = isPowerConnected.value
                    )
                }
            }
        }
    }

    inner class BatteryReceiver : BroadcastReceiver() {
        @RequiresApi(Build.VERSION_CODES.O)
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                Intent.ACTION_BATTERY_CHANGED -> {
                    batteryLevel.value = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)
                    val status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
                    isPowerConnected.value = status == BatteryManager.BATTERY_STATUS_CHARGING

                    val serviceIntent = Intent(context, BatteryService::class.java).apply {
                        putExtra("batteryLevel", batteryLevel.value)
                        putExtra("isCharging", isPowerConnected.value)
                    }
                    context?.startForegroundService(serviceIntent)
                }

                Intent.ACTION_POWER_CONNECTED -> {
                    isPowerConnected.value = true
                }

                Intent.ACTION_POWER_DISCONNECTED -> {
                    isPowerConnected.value = false
                }
            }
        }
    }
}

@Composable
fun BatteryNotificationScreen(batteryLevel: Int, isPowerConnected: Boolean) {
    val animationAlpha by animateFloatAsState(
        targetValue = if (isPowerConnected) 1f else 0f,
        animationSpec = tween(durationMillis = 1000)
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (isPowerConnected) "Charging: $batteryLevel%" else "Not Charging: $batteryLevel%",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = if (isPowerConnected) Color.Green else Color.Red,
            modifier = Modifier.alpha(animationAlpha)
        )
    }
}
