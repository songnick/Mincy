package com.songnick.mincy

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.songnick.mincy.compose_ui.MincyApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    companion object{
        private const val  REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSION = 3009
        private const val TAG = "MainActivity"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        requestReaderPermission()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent { MincyApp() }

    }

    private fun requestReaderPermission(){
        val needRequest = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        if (needRequest){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSION)
        }else{
        }
    }

    override fun onResume() {
        super.onResume()
//        val needRequest = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
//        if (!needRequest){
//
//        }
    }

//    @Composable
//    fun App(){
//        MincyApp(mediaViewModel, requestPermission = {
//            requestReaderPermission()
//        })
//    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSION -> {
//                mediaViewModel.permissionRequested =
//                    grantResults[0] == PackageManager.PERMISSION_GRANTED
//                if (mediaViewModel.permissionRequested){
//                    mediaViewModel.handleAction(MediaViewModel.Action.LoadData)
//                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }

    }
}
