package com.peshale.nmedia.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.TaskStackBuilder
import com.google.android.gms.common.GoogleApiAvailabilityLight
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import com.peshale.nmedia.R
import com.peshale.nmedia.activity.AppActivity
import com.peshale.nmedia.utils.Arguments.RANDOM_INT
import kotlin.random.Random

class FcmService : FirebaseMessagingService() {
    private val action = "action"
    private val content = "content"
    private val channelId = "remote"
    private val gson = Gson()

    override fun onCreate() {
        super.onCreate()
        println("Token (onCreate) -> ${FirebaseMessaging.getInstance().token}")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_remote_name)
            val descriptionText = getString(R.string.channel_remote_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
        //googleApiCompatibility()
    }

//    private fun googleApiCompatibility() {
//        with(GoogleApiAvailability.getInstance())
//    }

    override fun onMessageReceived(message: RemoteMessage) {

        message.data[action]?.let {
            when (it) {
                "LIKE" -> handleLike(gson.fromJson(message.data[content], Like::class.java))
                "SHARE" -> handleShare(gson.fromJson(message.data[content], Share::class.java))
                "NewPost" -> handleNewPost(
                    gson.fromJson(
                        message.data[content],
                        NewPost::class.java
                    )
                )
                else -> handleElse(gson.fromJson(message.data[content], Else::class.java))
            }
        }
    }

    override fun onNewToken(token: String) {
        println("Token ->  $token")
    }

    private fun handleNewPost(content: NewPost) {
        val remoteViews = RemoteViews(packageName, R.layout.notification)
        remoteViews.setTextViewText(
            R.id.tvNotificationTitle,
            getString(
                R.string.notification_new_post,
                content.author
            )
        )
        remoteViews.setTextViewText(R.id.tvNotification, content.text)
        val resultIntent = Intent(this, AppActivity::class.java)

        val resultPendingIntent: PendingIntent? = TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(resultIntent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentIntent(resultPendingIntent)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setCustomContentView(remoteViews)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        NotificationManagerCompat.from(this)
            .notify(Random.nextInt(100_000), notification)
    }

    private fun handleLike(content: Like) {
        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.netology)
            .setContentTitle(
                getString(
                    R.string.notification_user_liked,
                    content.userName,
                    content.postAuthor,
                )
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        NotificationManagerCompat.from(this)
            .notify(Random.nextInt(RANDOM_INT), notification)
    }

    private fun handleShare(content: Share) {
        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.netology)
            .setContentTitle(
                getString(
                    R.string.notification_user_share,
                    content.userName,
                    content.postAuthor,
                )
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        NotificationManagerCompat.from(this)
            .notify(Random.nextInt(RANDOM_INT), notification)
    }

    private fun handleElse(content: Else) {
        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.netology)
            .setContentTitle(
                getString(
                    R.string.notification_else_action,
                    content.postAuthor
                )
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        NotificationManagerCompat.from(this)
            .notify(Random.nextInt(RANDOM_INT), notification)
    }
}

data class NewPost(
    val id: Long,
    val author: String,
    val text: String,
)

data class Like(
    val userId: Long,
    val userName: String,
    val postId: Long,
    val postAuthor: String,
)

data class Share(
    val userId: Long,
    val userName: String,
    val postId: Long,
    val postAuthor: String,
)

data class Else(
    val userId: Long,
    val userName: String,
    val postId: Long,
    val postAuthor: String,
)