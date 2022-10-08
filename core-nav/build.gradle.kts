plugins {
    id("mincy.android.library")
}

dependencies {

    api(libs.androidx.activity.compose)
    api(libs.androidx.appcompat)
    api(libs.androidx.core.ktx)
    api(libs.androidx.core.splashscreen)
    api(libs.androidx.compose.material3.windowSizeClass)
    api(libs.androidx.window.manager)
    api(libs.androidx.profileinstaller)
    api(libs.androidx.compose.material.iconsExtended)
    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.material3)
    api(libs.androidx.fragment)
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}