package com.songnick.mincy.feature.media_choose

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.songnick.mincy.core.data.model.Image
import com.songnick.mincy.feature.media_choose.PreviewImage
import dagger.hilt.android.AndroidEntryPoint


/*****
 * @author qfsong
 * Create Time: 2022/9/28
 **/
@AndroidEntryPoint
class ImagePreviewActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val imageList:ArrayList<Image> = intent.getParcelableArrayListExtra<Image>(MediaChooseConstants.KEY_IMAGE_LIST) as ArrayList<Image>
        val index = intent.getIntExtra(MediaChooseConstants.KEY_PREVIEW_INDEX, 0)
        setContent {
            ImagePreViewScreen(pictureList = imageList, previewIndex = index)
        }
    }
}