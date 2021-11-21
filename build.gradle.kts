buildscript {
    repositories {
        google()
        maven(url = java.net.URI.create("https://jitpack.io"))
        mavenCentral()
        jcenter()
    }
    dependencies {
        classpath(Dependencies.Plugins.androidGradlePlugin)
        classpath(Dependencies.Plugins.kotlinGradlePlugin)
        classpath(Dependencies.Plugins.safeArgsPlugin)
    }
}

allprojects {
    repositories {
        google()
        maven(url = java.net.URI.create("https://jitpack.io"))
        mavenCentral()
        jcenter()
    }
}

task<Delete>("clean") {
    delete(rootProject.buildDir)
}