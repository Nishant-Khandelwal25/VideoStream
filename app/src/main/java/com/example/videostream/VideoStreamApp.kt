package com.example.videostream

import android.app.Application
import com.example.videostream.di.appModule
import io.getstream.video.android.core.StreamVideo
import io.getstream.video.android.core.StreamVideoBuilder
import io.getstream.video.android.model.User
import io.getstream.video.android.model.UserType
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class VideoStreamApp : Application() {
    private var currentUser: String? = null
    var client: StreamVideo? = null

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@VideoStreamApp)
            modules(appModule)
        }
    }

    fun init(userName: String) {
        if (client == null || currentUser != userName) {
            StreamVideo.removeClient()
            client = StreamVideoBuilder(
                context = this,
                apiKey = "dpufue8jsv7s",
                user = User(id = userName, name = userName, type = UserType.Guest)
            ).build()
        }
    }
}