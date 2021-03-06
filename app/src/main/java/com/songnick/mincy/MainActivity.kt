package com.songnick.mincy

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.songnick.mincy.compose.ui.MincyApp
import com.songnick.mincy.fragment.MainFragmentDirections
import com.songnick.mincy.viewmodel.MediaViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object{
        private const val  REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSION = 3009
        private const val TAG = "MainActivity"
    }

    private val mediaViewModel:MediaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestReaderPermission()
        setContent { App() }

    }

    private fun requestReaderPermission(){
        val needRequest = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        if (needRequest){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSION)
        }else{
            mediaViewModel.permissionRequested = true
            mediaViewModel.handleAction(MediaViewModel.Action.LoadData)
        }
    }

    override fun onResume() {
        super.onResume()
        val needRequest = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        if (!needRequest){

        }
    }

    @Composable
    fun App(){
        MincyApp(mediaViewModel, requestPermission = {
            requestReaderPermission()
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSION -> {
                mediaViewModel.permissionRequested =
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                if (mediaViewModel.permissionRequested){
                    mediaViewModel.handleAction(MediaViewModel.Action.LoadData)
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }

    }
}
