package com.itis2019.lecturerecorder.di.module

import android.app.Application
import android.content.Context
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

fun appModule(app: Application) = Kodein.Module("app") {

    bind<Context>() with singleton { app }
}
