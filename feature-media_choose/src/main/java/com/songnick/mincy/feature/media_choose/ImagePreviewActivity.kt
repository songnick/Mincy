package com.songnick.mincy.feature.media_choose

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.hilt.navigation.compose.hiltViewModel
import com.songnick.mincy.core.data.model.Image
import com.songnick.mincy.feature.media_choose.component.ImagePreViewScreen
import dagger.hilt.android.AndroidEntryPoint


/*****
 * @author qfsong
 * Create Time: 2022/9/28
 **/
@AndroidEntryPoint
class ImagePreviewActivity: ComponentActivity() {

    val chooseVM:MediaChooseVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val imageList:ArrayList<Image> = intent.getParcelableArrayListExtra<Image>("picture") as ArrayList<Image>
//        setContent {
//            ImagePreViewScreen(pictureList = imageList)
//        }
    }
}