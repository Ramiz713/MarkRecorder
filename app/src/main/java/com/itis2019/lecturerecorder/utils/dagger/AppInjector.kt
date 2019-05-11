package com.itis2019.lecturerecorder.utils.dagger

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector

fun Application.autoInject() {

    this.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity?, p1: Bundle?) {
            activity?.also {
                if (it is HasSupportFragmentInjector) AndroidInjection.inject(it)

                if (it is FragmentActivity) {
                    it.supportFragmentManager.registerFragmentLifecycleCallbacks(
                        object : FragmentManager.FragmentLifecycleCallbacks() {
                            override fun onFragmentActivityCreated(
                                fm: FragmentManager,
                                f: Fragment,
                                savedInstanceState: Bundle?
                            ) {
                                if (f is FragmentInjectable) AndroidSupportInjection.inject(f)
                            }

                        }, true
                    )
                }
            }
        }

        override fun onActivityPaused(activity: Activity?) {}
        override fun onActivityResumed(activity: Activity?) {}
        override fun onActivityStarted(activity: Activity?) {}
        override fun onActivityDestroyed(activity: Activity?) {}
        override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {}
        override fun onActivityStopped(activity: Activity?) {}
    })
}
