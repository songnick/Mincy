package com.songnick.mincy.compose.custom

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberAsyncImagePainter
import java.io.File
const val TAG = "CropImageView"
@Composable
fun CropImageView(path:String) {
    Box(modifier = Modifier.fillMaxWidth()) {
        var imageRotation by remember {
            mutableStateOf(0f)
        }
        var scale by remember {
            mutableStateOf(0f)
        }
        Image(painter = rememberAsyncImagePainter(File(path)),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .rotate(imageRotation)
                .scale(scale)
                .pointerInput(Unit) {
                    detectTransformGestures(false) { centroid, pan, zoom, rotation ->
                        Log.i(
                            TAG,
                            " centroid: $centroid, pan: $pan, zoom: $zoom, rotation: $rotation"
                        )
                        scale = zoom
                        imageRotation = rotation
                    }
                })
    }
}

private fun transitionImageView(centroid:Offset, zoom:Float, rotation:Float){

}

@Preview
@Composable
fun PreView(){
    MaterialTheme {
        CropImageView("")
    }
}