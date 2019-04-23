package com.itis2019.lecturerecorder.di

import android.app.Application
import com.itis2019.lecturerecorder.di.module.appModule
import com.itis2019.lecturerecorder.di.module.repositoryModule
import com.itis2019.lecturerecorder.di.module.roomModule
import com.itis2019.lecturerecorder.di.module.viewModelModule
import org.kodein.di.Kodein

fun initKodein(app: Application) =
    Kodein.lazy {
        import(appModule(app))
        import(roomModule())
        import(repositoryModule())
        import(viewModelModule())
    }
