package com.songnick.mincy.compose.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.songnick.mincy.data.MediaData
import java.io.File

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
                Row(modifier = Modifier.padding(top = 15.dp, end = 10.dp)) {
                    repeat(rowItemCount) { index ->
                        val listIndex = it * rowItemCount + index
                        if (listIndex < pictureList.size){
                            ItemCard(
                                pictureData = pictureList[listIndex],
                                Modifier
                                    .weight(1.0f / rowItemCount.toFloat())
                                    .padding(start = 10.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

fun <T> LazyListScope.gridItems(
    data: List<T>,
    columnCount: Int,
    modifier: Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    itemContent: @Composable BoxScope.(T) -> Unit,
) {
    val size = data.count()
    val rows = if (size == 0) 0 else 1 + (size - 1) / columnCount
    items(rows, key = { it.hashCode() }) { rowIndex ->
        Row(
            horizontalArrangement = horizontalArrangement,
            modifier = modifier
        ) {
            for (columnIndex in 0 until columnCount) {
                val itemIndex = rowIndex * columnCount + columnIndex
                if (itemIndex < size) {
                    Box(
                        modifier = Modifier.weight(1F, fill = true),
                        propagateMinConstraints = true
                    ) {
                        itemContent(data[itemIndex])
                    }
                } else {
                    Spacer(Modifier.weight(1F, fill = true))
                }
            }
        }
    }
}

@Composable
fun ItemCard(pictureData: MediaData, modifier: Modifier){
    Card(
        backgroundColor = Color.Red,
        modifier = modifier,
        elevation = 8.dp,
        shape = RoundedCornerShape(25.dp)
    ){
        Box(modifier = Modifier
            .fillMaxHeight()
            .fillMaxHeight()){
            Image(painter = rememberAsyncImagePainter(File(pictureData.path)),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.aspectRatio(1.0f/1.0f))
            var checked by remember { mutableStateOf(false) }
            Checkbox(checked = checked,
                onCheckedChange ={checked = it },
                modifier = Modifier.padding(bottom = 15.dp, end = 10.dp) )
        }


    }
}