package com.songnick.mincy.compose.custom

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CropWindow(){
    Box {
        Surface(modifier = Modifier
            .width(300.dp)
            .height(400.dp)
            .padding(5.dp)
            .scrollable(
                orientation = Orientation.Vertical,
                enabled = true,
                state = rememberScrollableState { delta ->
                    Log.i("TAG", " current deltate: $delta")
                    delta
                }),
            color = Color.Blue
        ) {

        }
        var offsetX by remember {
            mutableStateOf(0f)
        }
        val width = 30.dp
        val height = 10.dp
        val modifier = Modifier.scrollable(
            orientation = Orientation.Vertical,
            enabled = true,
            state = rememberScrollableState { delta ->
                offsetX +=  delta
            Log.i("TAG" ," current deltate: $offsetX")
              delta
        })
        RectLine(
            width = width,
            height = height,
            modifier.align(Alignment.TopStart).offset())
        RectLine(
            width = width,
            height = height,
            Modifier.align(Alignment.TopEnd))
        RectLine(
            width = width,
            height = height,
            Modifier.align(Alignment.BottomStart))
        RectLine(
            width = width,
            height = height,
            modifier
                .align(Alignment.BottomEnd)
                .offset())

    }
}

@Composable
fun CornerRect(width:Dp, height:Dp){
    Box(){
//        var offset by remember { mutableStateOf(0f) }
////        val modifier = Modifier
////            .size(11.dp)
////            .scrollable(
////                orientation = Orientation.Vertical,
////                state = rememberScrollableState { delta ->
////                    offset += delta
////                    delta
////                }
////            )
////            .align(Alignment.TopStart)

    }
}

@Composable
fun RectLine(width:Dp, height:Dp, modifier: Modifier){
    RectLine(
        modifier = modifier
            .height(height)
            .width(width)
    )
    RectLine(
        modifier = modifier
            .height(width)
            .width(height)
    )
}

@Composable
fun RectLine(modifier: Modifier, color: Color = Color.White){
    Surface(color=color, modifier = modifier){

    }
}

@Preview
@Composable
fun PreViewD(){
    MaterialTheme {
        CropWindow()
    }
}