package com.songnick.mincy.compose.ui

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Absolute.Center
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.songnick.mincy.CropImageActivity
import com.songnick.mincy.IntentParams
import com.songnick.mincy.data.MediaData
import com.songnick.mincy.model.MediaModel
import com.songnick.mincy.viewmodel.MediaItemViewModel
import com.songnick.mincy.viewmodel.MediaViewModel
import java.io.File
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/*****
 * @author qfsong
 * Create Time: 2022/4/22
 **/
@Composable
fun MediaGrid(pictureList: List<MediaData>?, rowItemCount: Int) {
    pictureList?.let {
        LazyColumn() {
            val rowSize =
                if (pictureList.size % rowItemCount == 0) pictureList.size / rowItemCount else pictureList.size / rowItemCount + 1
            items(rowSize) {
                Log.i("TAG", " current index: $it size: ${pictureList.size} row size: $rowSize")
                Row(modifier = Modifier.padding(top = 15.dp, end = 10.dp), horizontalArrangement = Arrangement.Center) {
                    repeat(rowItemCount) { index ->
                        val listIndex = it * rowItemCount + index
                        if (listIndex < pictureList.size){
                            ItemCard(
                                data = pictureList[listIndex],
                                Modifier
                                    .weight(1.0f / rowItemCount, false)
                                    .padding(start = 10.dp)
                            )
                        }else{
                            Column(
                                Modifier
                                    .weight(1.0f / rowItemCount, false)
                                    .padding(start = 10.dp)
                            ){


                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun ItemCard(data: MediaData, modifier: Modifier){
    val viewModel:MediaViewModel = viewModel()
//    Card(
//        modifier = modifier,
//        elevation = Card,
//        shape = RoundedCornerShape(10.dp)
//    ){
//
//        var checked by remember { mutableStateOf(false) }
//        val modifierL = Modifier
//            .fillMaxHeight()
//            .fillMaxHeight()
//            .clickable {
//                viewModel.handleAction(MediaViewModel.Action.CropImage(data))
//            }
//        Box(modifier = modifierL){
//            Image(painter = rememberAsyncImagePainter(File(data.getImagePath())),
//                contentDescription = null,
//                contentScale = ContentScale.Crop,
//                modifier = Modifier.aspectRatio(1.0f/1.0f))
//            Checkbox(checked = checked,
//                onCheckedChange ={checked = it },
//                modifier = Modifier.align(Alignment.TopEnd)
//            )
//            if (data.mediaType.startsWith("video")){
//                val date = Date(data.duration)
//                val format = SimpleDateFormat("mm:ss")
//                Text(text = format.format(date),
//                    Modifier
//                        .align(Alignment.BottomEnd)
//                        .padding(top = 2.dp, end = 6.dp),fontSize = 12.sp
//                )
//            }
//        }
//    }
}

@Preview
@Composable
fun PreviewCard(){
    MaterialTheme(){
        Row(Modifier.padding(top = 15.dp, end = 10.dp)) {
            repeat(3){
                ItemCard(
                    data = MediaData("", "", 0, "video"),
                    Modifier
                        .weight(1.0f, false)
                        .padding(start = 10.dp)
                        .aspectRatio(1.0f)
                )
            }
        }
    }
}