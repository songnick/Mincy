package com.songnick.mincy.feature.image_edit

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.songnick.mincy.core.data.model.Image

/*****
 * @author songnick
 * @mail qfsong108@gmail.com
 * Create Time: 2022/10/9
 **/
class ImageEditActivity:ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val image = intent.getParcelableExtra<Image>("edit_image")
        if (image != null){
            setContent { ImageEditPage(picture = image) }
        }else{
           Toast.makeText(this,"资源不存在", Toast.LENGTH_LONG).show()
        }

    }
}