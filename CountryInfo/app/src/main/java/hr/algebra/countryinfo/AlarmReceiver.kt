package hr.algebra.countryinfo

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.core.app.NotificationBuilderWithBuilderAccessor
import android.net.Uri as Uri1

private const val DURATION = 10000L
private const val ID = 1


class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        playRingtone(context, DURATION)
        sendNotification(context)
    }

    private fun sendNotification(context: Context) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val intent = Intent(context, HostActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            ID,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder: Notification.Builder
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                context.getString(R.string.channel_ID),
                context.getString(R.string.channel_title),
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
            builder = Notification.Builder(context, channel.id)

        } else {
            @Suppress("DEPRECATION")
            builder = Notification.Builder(context)
        }

        builder.apply {
            setContentTitle(context.getString(R.string.knowledge_is_waiting))
            setContentText(context.getString(R.string.learn_some))
            setSmallIcon(R.drawable.map)
            setAutoCancel(true)
            setContentIntent(pendingIntent)
        }

        notificationManager.notify(ID,builder.build())
    }

    private fun playRingtone(context: Context, duration: Long) {
        val mediaPlayer = MediaPlayer.create(context, R.raw.this_is_sparta)
        mediaPlayer.start()
        Handler(Looper.getMainLooper()).postDelayed(
            {mediaPlayer.stop()},
            DURATION
        )
    }
}