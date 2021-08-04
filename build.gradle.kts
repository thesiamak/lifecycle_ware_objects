// Top-level build file where you can add configuration options common to all sub-projects/modules.
val tmpVal = ""
buildscript {

    val appVersionName :String by extra("interviewTest0.1.")
    var appReleaseFileName :String by extra("")
    val lifecycleVersion :String by extra( "2.2.0")
    val kotlin :String by extra( "1.5.0")
    val dagger :String by extra( "2.35.1")


    repositories {
        google()
        jcenter()
        maven("https://maven.google.com")
        maven("https://jitpack.io")
    }

    dependencies {
        "classpath"(group = "com.android.tools.build",name = "gradle", version = "4.1.3")
        "classpath"(group = "org.jetbrains.kotlin",name = "kotlin-gradle-plugin", version = kotlin)
        "classpath"(group = "androidx.navigation",name = "navigation-safe-args-gradle-plugin", version = "2.3.5")
    }
}

allprojects {

    repositories {
        google()
        jcenter()
        maven("https://jitpack.io" )
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
