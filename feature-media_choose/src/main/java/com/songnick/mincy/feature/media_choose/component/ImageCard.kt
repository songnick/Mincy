package com.songnick.mincy.feature.media_choose.component

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.songnick.mincy.R

/*****
 * @author qfsong
 * Create Time: 2022/9/23
 **/
@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class
)
/***
 * image card to show picture and gif
 * @param modifier
 * @param path the image's path
 * @param cardState image state, whether is selected
 * @param onClick click event method
 * */
@Composable
fun ImageCard(
    modifier: Modifier = Modifier,
    path:String,
    cardState: CardState,
    onClick:()->Unit,
    preViewOnClick:()->Unit
) {
    Card(
        modifier = modifier,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .combinedClickable {
                    onClick.invoke()
                }
        ) {
            //may need to load gif
            val imageLoader = if (path.endsWith("gif")){
                ImageLoader.Builder(LocalContext.current)
                    .components {
                        if (SDK_INT >= 28) {
                            add(ImageDecoderDecoder.Factory())
                        } else {
                            add(GifDecoder.Factory())
                        }
                    }
                    .build()
            }else{
                ImageLoader.Builder(LocalContext.current).build()
            }
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(path)
                    .crossfade(true)
                    .build(),
                imageLoader = imageLoader,
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            if (cardState.selected){
                CheckBoxWithIndex(
                    index = cardState.selectedIndex,
                    modifier = Modifier
                        .padding(top = 5.dp, end = 5.dp)
                        .width(23.dp)
                        .height(23.dp)
                        .align(Alignment.TopEnd)
                        .background(Color.Transparent)
                )
            }
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 10.dp, end = 10.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .combinedClickable(onClick = preViewOnClick)
            ){
                Image(
                    painter = painterResource(id = R.drawable.fullscreen),
                    contentDescription ="",
                    modifier = Modifier.background(Color(red = 0x00, green = 0x00, blue = 0x00, alpha = 0x4D))
                )
            }

        }


    }
}