package com.songnick.mincy.compose.custom

import android.graphics.Rect
import android.util.Log
import android.view.MotionEvent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object Tag{
    const val TAG = " Crop Window"
}

@Composable
fun CropWindow(cornerWidth:Dp = 15.dp, lineWidth:Dp = 2.dp, aspectRatio:Float = 1.0f){
    BoxWithConstraints(contentAlignment = Alignment.BottomEnd) {
        var cropRect by remember {
            mutableStateOf(Rect())
        }
        var leftDrag by remember {
          mutableStateOf(false)
        }
        val cornerLineWidth = with(LocalDensity.current){
            cornerWidth.toPx()
        }

        val stroke = with(LocalDensity.current){
            lineWidth.toPx()
        }
        val canvasWidth = maxWidth-20.dp
        val canvasHeight = maxHeight - 20.dp
        var deltaWidth by remember {
            mutableStateOf(0.dp)
        }
        var deltaHeight by remember {
            mutableStateOf(0.dp)
        }
        Log.i("TAG", " canvas width: $canvasWidth + height: $canvasHeight")
        Surface(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .pointerInput(Unit) {
                detectDragGestures(onDragStart = {

                }) { change, dragAmount ->
                    Log.i(Tag.TAG, " detectDragGestures x: ${change.position.x} y: ${dragAmount.x}")

                }
            },
            color = Color.Blue
        ) {
            var width by remember {
                mutableStateOf(maxWidth + deltaWidth)
            }
            val height = maxWidth/aspectRatio
            Log.i(Tag.TAG, " current size width: $width + height: $height")
            Canvas(modifier = Modifier
                .width(100.dp)
                .height(200.dp)
                .padding(cornerWidth / 2)
            ){

                Log.i(Tag.TAG, " current canvas size : $size")
                //vertical grid
                drawLine(
                    color= Color.Yellow,
                    start = Offset(cornerLineWidth/2, size.height/3f),
                    end = Offset(size.width-cornerLineWidth/2 , size.height/3f),
                    strokeWidth = stroke
                )
                drawLine(
                    color= Color.Yellow,
                    start = Offset(cornerLineWidth/2, size.height/3f*2),
                    end = Offset(size.width-cornerLineWidth/2, size.height/3f*2),
                    strokeWidth = stroke
                )
                //horizontal grid
                drawLine(
                    color= Color.Yellow,
                    start = Offset(size.width/3f, cornerLineWidth/2),
                    end = Offset(size.width/3f, size.height-cornerLineWidth/2),
                    strokeWidth = stroke
                )
                drawLine(
                    color= Color.Yellow,
                    start = Offset(size.width/3f*2,cornerLineWidth/2 ),
                    end = Offset(size.width/3f*2, size.height-cornerLineWidth/2),
                    strokeWidth = stroke
                )
                val pointList = listOf(
                    Offset(cornerLineWidth/2, cornerLineWidth/2),
                    Offset(size.width-cornerLineWidth/2, cornerLineWidth/2),
                    Offset(size.width-cornerLineWidth/2, cornerLineWidth/2),
                    Offset(size.width-cornerLineWidth/2, size.height-cornerLineWidth/2),
                    Offset(size.width-cornerLineWidth/2, size.height-cornerLineWidth/2),
                    Offset(cornerLineWidth/2, size.height-cornerLineWidth/2),
                    Offset(cornerLineWidth/2, size.height-cornerLineWidth/2),
                    Offset(cornerLineWidth/2, cornerLineWidth/2)
                )
                //image crop line
                drawPoints(
                    pointList,
                    pointMode = PointMode.Lines,
                    color=Color.Yellow,
                    strokeWidth=stroke
                )

                clipRect(
                    left = cornerLineWidth,
                    top = cornerLineWidth,
                    right = size.width - cornerLineWidth,
                    bottom = size.height - cornerLineWidth,
                    clipOp = ClipOp.Difference
                ){
                    clipRect(
                        left = cornerLineWidth*3,
                        top = 0f,
                        right = size.width - 3*cornerLineWidth,
                        bottom = size.height,
                        clipOp = ClipOp.Difference
                    ){
                        clipRect(
                            left = 0f,
                            top = cornerLineWidth*3f,
                            right = size.width,
                            bottom = size.height-3*cornerLineWidth,
                            clipOp = ClipOp.Difference
                        ){
                            drawRect(color = Color.Yellow,
                                topLeft = Offset(0f, 0f),
                                size = Size(width = size.width, height = size.height)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Stable
fun Modifier.test(){

}

@Preview
@Composable
fun PreViewD(){
    MaterialTheme {
        Surface(Modifier.fillMaxHeight().fillMaxWidth(), color=Color.Yellow) {
            Canvas(modifier = Modifier.width(100.dp).height(100.dp)){
                drawLine(
                    color= Color.Yellow,
                    start = Offset(10f/2, size.height/3f),
                    end = Offset(size.width-10f/2 , size.height/3f),
                    strokeWidth = 5f
                )
                drawRoundRect(Color.Red, Offset(0f, 0f), Size(size.width, size.height))
            }
        }
    }
}