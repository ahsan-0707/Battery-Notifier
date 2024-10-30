package com.example.batterynotifier

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.os.Handler
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import android.view.View

class BatteryService : Service() {

    private val CHANNEL_ID = "BatteryNotifierChannel"
    private val NOTIFICATION_ID = 1
    private val handler = Handler()

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    @SuppressLint("ForegroundServiceType")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val batteryLevel = intent?.getIntExtra("batteryLevel", 0) ?: 0
        val isCharging = intent?.getBooleanExtra("isCharging", false) ?: false

        startForeground(NOTIFICATION_ID, createNotification(batteryLevel, isCharging))

        if (isCharging) {
            showTemporaryMessage("Charger Connected Successfully", android.graphics.Color.GREEN, batteryLevel, isCharging)
        } else {
            showTemporaryMessage("Charger Disconnected", android.graphics.Color.RED, batteryLevel, isCharging)
        }

        return START_STICKY
    }

    private fun createNotification(batteryLevel: Int, isCharging: Boolean): Notification {
        val remoteViews = RemoteViews(packageName, R.layout.notification_layout).apply {
            setTextViewText(R.id.batteryStatus, "Battery Level: $batteryLevel%")
            setProgressBar(R.id.batteryCircleGreen, 100, batteryLevel, false)
            setProgressBar(R.id.batteryCircleRed, 100, batteryLevel, false)

            // Show/hide progress bars based on charging status
            if (isCharging) {
                setViewVisibility(R.id.batteryCircleGreen, View.VISIBLE)
                setViewVisibility(R.id.batteryCircleRed, View.GONE)
            } else {
                setViewVisibility(R.id.batteryCircleGreen, View.GONE)
                setViewVisibility(R.id.batteryCircleRed, View.VISIBLE)
            }

//            setTextViewText(R.id.batteryPercentage, "$batteryLevel%")
        }

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground) // Ensure you have this drawable
            .setCustomContentView(remoteViews)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setPriority(NotificationCompat.PRIORITY_HIGH) // Adjust priority if needed
            .build()
    }

    private fun showTemporaryMessage(message: String, color: Int, batteryLevel: Int, isCharging: Boolean) {
        val remoteViews = RemoteViews(packageName, R.layout.notification_layout).apply {
            setTextViewText(R.id.batteryStatus, message)
            setTextColor(R.id.batteryStatus, color)
            setProgressBar(R.id.batteryCircleGreen, 100, 0, false) // Reset green progress bar
            setProgressBar(R.id.batteryCircleRed, 100, 0, false) // Reset red progress bar
        }

        // Update notification with the temporary message
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setCustomContentView(remoteViews)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        // Show temporary notification
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.notify(NOTIFICATION_ID, notification)

        // Delay for 3 seconds, then update with the actual battery status
        handler.postDelayed({
            startForeground(NOTIFICATION_ID, createNotification(batteryLevel, isCharging)) // Update with actual battery level
        }, 3000) // 3 seconds delay
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Battery Notifier Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Channel for battery notifications"
                setSound(null, null) // Disable sound if not needed
            }
            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(serviceChannel)
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
