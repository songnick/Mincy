plugins {
    id("mincy.android.library")
    id("mincy.android.library.compose")
    id("dagger.hilt.android.plugin")
    id("mincy.android.feature")
}
android {
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    lint {
        checkDependencies = true
    }
}
dependencies {
//    implementation(project(":base-nav"))
//    api(libs.kotlin.stdlib)
//    api(libs.androidx.navigation.compose)
//    api(libs.androidx.compose.ui.tooling)
//    api(libs.androidx.core.ktx)
//    api(libs.androidx.compose.runtime)
//    api(libs.androidx.compose.material.iconsExtended)
//    api(libs.androidx.compose.material3)
//    api(libs.hilt.android)
//    api(libs.hilt.compiler)
//    api(libs.hilt.ext.compiler)
//    api(libs.hilt.ext.work)
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.serialization.json)
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}