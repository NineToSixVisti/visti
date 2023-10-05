package com.ssafy.presentation

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.ssafy.domain.usecase.fcm.FcmUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "FcmService"
class FcmService : FirebaseMessagingService() {
    // 새로운 토큰이 생성될 때 마다 해당 콜백이 호출된다.
//    @Inject
//    lateinit var fcmUseCase: FcmUseCase

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        // 새로운 토큰 수신 시 서버로 전송
        Log.e(TAG, "$token")

        CoroutineScope(Dispatchers.IO).launch {
            Log.e(TAG, "하위")
//            RetrofitUtil.chatApi.uploadToken(FcmToken(token))
         //   fcmUseCase.uploadFcmToken(token)
        }
    }

    // Foreground, Background 모두 처리하기 위해서는 data에 값을 담아서 넘긴다.
    //https://firebase.google.com/docs/cloud-messaging/android/receive
    @SuppressLint("MissingPermission")
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        var messageTitle = ""
        var messageContent = ""

        if (remoteMessage.notification != null) { // notification이 있는 경우 foreground처리
            //foreground
            messageTitle = remoteMessage.notification!!.title.toString()
            messageContent = remoteMessage.notification!!.body.toString()

        } else {  // background 에 있을경우 혹은 foreground에 있을경우 두 경우 모두
            var data = remoteMessage.data
            Log.d(TAG, "data.message: ${data}")
            Log.d(TAG, "data.message: ${data.get("title")}")
            Log.d(TAG, "data.message: ${data.get("body")}")

            messageTitle = data.get("title").toString()
            messageContent = data.get("body").toString()
        }

        val mainIntent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val mainPendingIntent: PendingIntent =
            PendingIntent.getActivity(this, 0, mainIntent, PendingIntent.FLAG_IMMUTABLE)

        val builder1 = NotificationCompat.Builder(this, MainActivity.CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(messageTitle)
            .setContentText(messageContent)
            .setAutoCancel(true)
            .setContentIntent(mainPendingIntent)
            .setFullScreenIntent(mainPendingIntent, true)

        NotificationManagerCompat.from(this).apply {
            notify(101, builder1.build())
        }
    }
}