package com.songnick.mincy

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil.setContentView
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.songnick.mincy.databinding.ActivityMainBinding
import com.songnick.mincy.fragment.MainFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object{
        private const val  REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSION = 3009
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        requestReaderPermission()
    }

    private fun navigateToGallery() {
        val directions = MainFragmentDirections.actionMainToGridFragment()
        findNavController(R.id.nav_host).navigate(directions)
    }

    private fun requestReaderPermission(){
        val needRequest = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        if (needRequest){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSION)
        }
    }

    override fun onResume() {
        super.onResume()
        val needRequest = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        if(!needRequest){
            navigateToGallery()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        when(requestCode){
            REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSION -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    navigateToGallery()
                }
            }

            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }

    }
}
