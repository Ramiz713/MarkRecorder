object Dependencies {

    object Plugins {
        private const val androidGradleVersion = "7.0.3"
        private const val kotlinGradleVersion = "1.5.30"
        private const val safeArgsVersion = "2.3.5"

        const val androidGradlePlugin = "com.android.tools.build:gradle:$androidGradleVersion"
        const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinGradleVersion"
        const val safeArgsPlugin = "androidx.navigation:navigation-safe-args-gradle-plugin:$safeArgsVersion"
    }

    object AndroidX {
        private const val archLifecycleVersion = "1.1.1"

        const val lifecycleExt = "androidx.lifecycle:lifecycle-extensions:2.2.0"
        const val lifecycleViewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0"
        const val archLifecycleRuntime = "android.arch.lifecycle:runtime:$archLifecycleVersion"
        const val archLifecycleExt = "android.arch.lifecycle:extensions:$archLifecycleVersion"
        const val recyclerView = "androidx.recyclerview:recyclerview:1.2.1"
        const val cardView = "androidx.cardview:cardview:1.0.0"
        const val material = "com.google.android.material:material:1.4.0"
        const val support = "androidx.legacy:legacy-support-v4:1.0.0"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.1.2"
    }

    object UI {

        const val quickPermissions = "com.github.quickpermissions:quickpermissions-kotlin:0.4.1"
        const val audiovisualizer = "com.gauravk.audiovisualizer:audiovisualizer:0.9.2"
        const val yalantis = "com.yalantis:eqwaves:1.0.1"
    }

    object NavigationComponent {
        private const val navigationVersion = "2.3.5"

        const val fragment = "androidx.navigation:navigation-fragment-ktx:$navigationVersion"
        const val ktx = "androidx.navigation:navigation-ui-ktx:$navigationVersion"
    }

    object Room {
        private const val roomVersion = "2.3.0"

        const val runtime = "androidx.room:room-runtime:$roomVersion"
        const val rxJava2 = "androidx.room:room-rxjava2:$roomVersion"
        const val compiler = "androidx.room:room-compiler:$roomVersion"
    }

    object RxJava2 {
        private const val rxAndroidVersion = "2.1.1"
        private const val rxKotlinVersion = "2.3.0"

        const val rxAndroid = ("io.reactivex.rxjava2:rxandroid:$rxAndroidVersion")
        const val rxKotlin = ("io.reactivex.rxjava2:rxkotlin:$rxKotlinVersion")
    }

    object Dagger {
        private const val daggerVersion = "2.21"

        const val dagger = "com.google.dagger:dagger:$daggerVersion"
        const val support = "com.google.dagger:dagger-android-support:$daggerVersion"
        const val compiler = "com.google.dagger:dagger-compiler:$daggerVersion"
        const val processor = "com.google.dagger:dagger-android-processor:$daggerVersion"
    }
}