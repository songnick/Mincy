package com.songnick.mincy.media_choose.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

/*****
 * @author qfsong
 * Create Time: 2022/9/23
 **/
@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class
)
@Composable
fun ImageCard(
    modifier: Modifier = Modifier,
    path:String,
    imageCardState: ImageCardState,
    onClick:()->Unit
) {
    Card(
        modifier = modifier,
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
                .combinedClickable{
                    onClick.invoke()
                }
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(path)
                    .crossfade(true)
                    .build(),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            if (imageCardState.selected){
                CheckBoxWithIndex(
                    index = imageCardState.selectedIndex,
                    modifier = Modifier
                        .padding(top = 5.dp, end = 5.dp)
                        .width(23.dp)
                        .height(23.dp)
                        .align(Alignment.TopEnd)
                        .background(Color.Transparent)
                )
            }
        }


    }
}