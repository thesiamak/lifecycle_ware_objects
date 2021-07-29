import java.util.*

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    kotlin("android.extensions")

    id("androidx.navigation.safeargs.kotlin")
    id("dagger.hilt.android.plugin")
}


android {
    compileSdkVersion(30)
    buildToolsVersion("30.0.3")

    buildTypes {
        getByName("release"){
            // Enables code shrinking, obfuscation, and optimization for only
            // your project"s release build type.
            isMinifyEnabled = true

            // Enables resource shrinking, which is performed by the
            // Android Gradle plugin.
            isShrinkResources = true

            // Includes the default ProGuard rules files that are packaged with
            // the Android Gradle plugin. To learn more, go to the section about
            // R8 configuration files.
            var appReleaseFileName :String by rootProject.extra
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro")

            // Release build file auto rename
            applicationVariants.all {
                val variant = this
                variant.outputs
                    .map { it as com.android.build.gradle.internal.api.BaseVariantOutputImpl }
                    .forEach { output ->
                        output.outputFileName = "dindinn_${variant.buildType.name}_${defaultConfig.versionName}" +
                                "_${Date()}.apk"
                        appReleaseFileName = output.outputFileName
                    }
            }
        }

        getByName("debug"){
            isMinifyEnabled = false
        }
    }

    compileOptions{
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        dataBinding = true
    }


    val code = generateVersionCode()
    val appVersionName :String by rootProject.extra
    val name = "$appVersionName$code"

    defaultConfig {
        applicationId = "ir.drax.dindinn"
        versionName = name
        versionCode = 1
        minSdkVersion(21)
        targetSdkVersion(30)

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    val kotlin: String by rootProject.extra
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlin")
    implementation("androidx.core:core-ktx:1.5.0")
    implementation("androidx.appcompat:appcompat:1.3.0")
    implementation("com.google.android.material:material:1.3.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    // Hilt
    val hilt: String by rootProject.extra
    implementation("com.google.dagger:hilt-android:$hilt")
    kapt("com.google.dagger:hilt-android-compiler:$hilt")
    // Retrofit
    val retrofitVersion = "2.9.0"
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.4.0")
    // Lifecycle compiler
    val lifecycleVersion :String by rootProject.extra
    implementation("androidx.lifecycle:lifecycle-extensions:$lifecycleVersion")
    //    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    // Room
    val room_version = "2.3.0-alpha03"
    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    kapt ("androidx.room:room-compiler:$room_version")
    // Glide
    val glideVersion = "4.10.0"
    implementation("com.github.bumptech.glide:glide:$glideVersion")
    kapt("com.github.bumptech.glide:compiler:$glideVersion")
    // architecture comp. navigation
    val nav_version = "2.3.0"
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")
    // FCM messaging
    implementation("com.google.firebase:firebase-messaging:22.0.0")
    //Add the Firebase SDK for Google Analytics.
    implementation("com.google.firebase:firebase-analytics:19.0.0")
    // Add the Firebase Crashlytics SDK.
    implementation("com.google.firebase:firebase-crashlytics:18.0.0")
    //kotlin coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.3")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0-alpha01")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.4.32")
    //secure storage for storing credentials
    val secure_storage_version = "1.2.4"
    implementation("de.adorsys.android:securestoragelibrary:${secure_storage_version}")
    //UI Utils
    val androidUtil = "0.5.6"
    implementation("com.github.draxdave.androidutil:expandable:$androidUtil")
    implementation("com.github.draxdave.androidutil:modal:$androidUtil")
    // Log Tools
    val logTools = "1.6.0"
    implementation("com.facebook.stetho:stetho:$logTools")
    implementation("com.facebook.stetho:stetho-okhttp3:$logTools")
    //===================== TEST DEPENDENCIES =============================
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
    androidTestImplementation("androidx.test:core:1.3.0")
    //arch
    testImplementation("androidx.arch.core:core-testing:2.1.0")
    androidTestImplementation("androidx.arch.core:core-testing:2.1.0")
    testImplementation("android.arch.core:core-testing:1.1.1")
    //Mockito
    testImplementation("org.mockito:mockito-core:2.28.2")
    testImplementation("org.mockito:mockito-all:1.10.19")
    androidTestImplementation("org.mockito:mockito-android:2.25.0")
    //testimplementation("org.mockito:mockito-inline:2.8.47")
    //mockito-kotlin
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")
    //kotlin coroutines
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.6")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin")
    androidTestImplementation("androidx.test:runner:1.4.0-beta01")
    //for test
    implementation("com.squareup.retrofit2:converter-scalars:2.1.0")
}

task("releaseAndPublishFile"){
    dependsOn("assembleRelease")

    doLast {
        val appReleaseFileName :String by rootProject.extra
        println("Publishing ...")
        exec{
            executable("curl")
            args("-F", "my_file=@build/outputs/apk/release/$appReleaseFileName",
            "files.drax.ir/tmp/uploader.php")
        }

    }
}
//Auto generate unique build codes for every minute
fun generateVersionCode() = (System.currentTimeMillis() / 60000).toInt()
