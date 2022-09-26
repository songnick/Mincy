plugins {
    id("mincy.android.application")
    id("mincy.android.application.compose")
    id("mincy.android.application.jacoco")
    kotlin("kapt")
    id("jacoco")
    id("dagger.hilt.android.plugin")
    id("mincy.spotless")
}
android {
    defaultConfig {
        applicationId ="com.songnick.mincy"
        versionCode =2
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
//            minifyEnabled =false
//            proguardFiles = getDefaultProguardFile('proguard-android-optimize.txt'); 'proguard-rules.pro'
        }
    }
}
dependencies {
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material.iconsExtended)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.lifecycle.viewModelCompose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.compose.material3.windowSizeClass)
    implementation(libs.androidx.window.manager)
    implementation(libs.androidx.profileinstaller)
    implementation(libs.androidx.navigation.compose)

    //room
    kapt(libs.room.compiler)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)

    //coil
    implementation(libs.coil.kt)
    implementation(libs.coil.kt.compose)
    implementation(libs.coil.kt.svg)
    implementation(project(":core-designsystem"))
    implementation(project(":base-nav"))
    implementation(project(":feature-media_choose"))
    implementation(project(":feature-take_photo"))
    //    kapt ("com.google.dagger:hilt-android-compiler:2.38.1")
//    implementation("com.google.dagger:hilt-android:2.38.1")

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    kapt(libs.hilt.ext.compiler)

    kaptAndroidTest(libs.hilt.compiler)
    kaptAndroidTest(libs.hilt.ext.compiler)


}
