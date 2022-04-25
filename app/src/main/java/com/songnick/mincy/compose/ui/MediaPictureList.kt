package com.songnick.mincy.compose.ui

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.songnick.mincy.data.MediaData
import java.io.File

/*****
 * @author qfsong
 * Create Time: 2022/4/22
 **/
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MediaPictureList(pictureList:List<MediaData>?) {
    pictureList?.apply {
        LazyVerticalGrid(cells = GridCells.Fixed(4),
            contentPadding = PaddingValues(start = 10.dp, end = 5.dp, top = 5.dp, bottom = 5.dp),){
            items(pictureList.size){
                Log.i("TAG","dddd")
                Card(
                    backgroundColor = Color.Red,
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth(),
                    elevation = 8.dp,
                    shape = RoundedCornerShape(25.dp)
                ){
//                    Text(text = "dddd",modifier = Modifier.aspectRatio(3.0f/4.0f))
                    Image(painter = rememberAsyncImagePainter(File(pictureList[it].path)),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .aspectRatio(3.0f/4.0f))
                    var checked by remember { mutableStateOf(false) }
                    Checkbox(checked = checked,
                        onCheckedChange ={checked = it },
                        modifier = Modifier.clip(RoundedCornerShape(4.dp)) )

                }

            }

        }
    }
}