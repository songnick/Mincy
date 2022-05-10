package com.songnick.mincy

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.app.ComponentActivity
import com.songnick.mincy.compose.custom.CropImageView
import com.songnick.mincy.compose.custom.CropWindow

class CropImageActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val path:String = intent.getStringExtra("pathi")!!
        setContent {
            CropImageLayout(path)
        }
    }

    @Composable
    fun CropImageLayout(path:String){
        Box(modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
        ){
            CropImageView(path)
            CropWindow()
        }
    }
}