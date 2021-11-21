plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdk = Config.compileSdk
    defaultConfig {
        applicationId = Config.packageName
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk
        versionCode = Config.versionCode
        versionName = Config.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        kapt {
            arguments {
                arg("room.schemaLocation", "$projectDir/schemas".toString())
            }
        }
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

androidExtensions {
    isExperimental = true
}

dependencies {
    implementation(Dependencies.AndroidX.lifecycleExt)
    implementation(Dependencies.AndroidX.lifecycleViewmodel)
    implementation(Dependencies.AndroidX.archLifecycleRuntime)
    implementation(Dependencies.AndroidX.archLifecycleExt)
    implementation(Dependencies.AndroidX.recyclerView)
    implementation(Dependencies.AndroidX.cardView)
    implementation(Dependencies.AndroidX.material)
    implementation(Dependencies.AndroidX.support)
    implementation(Dependencies.AndroidX.constraintLayout)

    implementation(Dependencies.UI.quickPermissions)
    implementation(Dependencies.UI.audiovisualizer)
    implementation(Dependencies.UI.yalantis)

    implementation(Dependencies.NavigationComponent.fragment)
    implementation(Dependencies.NavigationComponent.ktx)

    implementation(Dependencies.Room.runtime)
    implementation(Dependencies.Room.rxJava2)
    kapt(Dependencies.Room.compiler)

    implementation(Dependencies.RxJava2.rxAndroid)
    implementation(Dependencies.RxJava2.rxKotlin)

    implementation(Dependencies.Dagger.dagger)
    implementation(Dependencies.Dagger.support)
    kapt(Dependencies.Dagger.compiler)
    kapt(Dependencies.Dagger.processor)

    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

}
