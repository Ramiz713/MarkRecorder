package com.itis2019.lecturerecorder

import android.app.Application
import com.itis2019.lecturerecorder.di.initKodein
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware

class App : Application(), KodeinAware {

    override val kodein: Kodein = initKodein(this)
}
