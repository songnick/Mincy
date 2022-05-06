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
        val cornerLineWidth = with(LocalDensity.current){
            cornerWidth.toPx()
        }

        val stroke = with(LocalDensity.current){
            lineWidth.toPx()
        }

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
            val width = LocalDensity.current.run {
                maxWidth.toPx()
            }
            val height = LocalDensity.current.run {
                (maxWidth/aspectRatio).toPx()
            }
            cropRect.set(0, 0,width.toInt(), height.toInt())
            Log.i(Tag.TAG, " current size + height: $height")
            Canvas(modifier = Modifier
                .padding(cornerWidth / 2)
            ){

                Log.i(Tag.TAG, " current canvas size : $size")
                //vertical grid
                drawLine(
                    color= Color.Yellow,
                    start = Offset(cornerLineWidth/2, cropRect.height()/3f),
                    end = Offset(cropRect.width()-cornerLineWidth/2 , cropRect.height()/3f),
                    strokeWidth = stroke
                )
                drawLine(
                    color= Color.Yellow,
                    start = Offset(cornerLineWidth/2, cropRect.height()/3f*2),
                    end = Offset(cropRect.width()-cornerLineWidth/2, cropRect.height()/3f*2),
                    strokeWidth = stroke
                )
                //horizontal grid
                drawLine(
                    color= Color.Yellow,
                    start = Offset(cropRect.width()/3f, cornerLineWidth/2),
                    end = Offset(cropRect.width()/3f, cropRect.height()-cornerLineWidth/2),
                    strokeWidth = stroke
                )
                drawLine(
                    color= Color.Yellow,
                    start = Offset(cropRect.width()/3f*2,cornerLineWidth/2 ),
                    end = Offset(cropRect.width()/3f*2, cropRect.height()-cornerLineWidth/2),
                    strokeWidth = stroke
                )
                val pointList = listOf(
                    Offset(cornerLineWidth/2, cornerLineWidth/2),
                    Offset(cropRect.width()-cornerLineWidth/2, cornerLineWidth/2),
                    Offset(cropRect.width()-cornerLineWidth/2, cornerLineWidth/2),
                    Offset(cropRect.width()-cornerLineWidth/2, cropRect.height()-cornerLineWidth/2),
                    Offset(cropRect.width()-cornerLineWidth/2, cropRect.height()-cornerLineWidth/2),
                    Offset(cornerLineWidth/2, cropRect.height()-cornerLineWidth/2),
                    Offset(cornerLineWidth/2, cropRect.height()-cornerLineWidth/2),
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
                    right = cropRect.width() - cornerLineWidth,
                    bottom = cropRect.height() - cornerLineWidth,
                    clipOp = ClipOp.Difference
                ){
                    clipRect(
                        left = cornerLineWidth*3,
                        top = 0f,
                        right = cropRect.width() - 3*cornerLineWidth,
                        bottom = cropRect.height().toFloat(),
                        clipOp = ClipOp.Difference
                    ){
                        clipRect(
                            left = 0f,
                            top = cornerLineWidth*3f,
                            right = cropRect.width().toFloat(),
                            bottom = cropRect.height()-3*cornerLineWidth,
                            clipOp = ClipOp.Difference
                        ){
                            drawRect(color = Color.Yellow,
                                topLeft = Offset(0f, 0f),
                                size = Size(width = cropRect.width().toFloat(), height = cropRect.height().toFloat())
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreViewD(){
    MaterialTheme {
        CropWindow()
//        Surface(
//            Modifier
//                .fillMaxHeight()
//                .fillMaxWidth(), color=Color.Yellow) {
//            Canvas(modifier = Modifier
//                .width(100.dp)
//                .height(100.dp)){
//                drawLine(
//                    color= Color.Yellow,
//                    start = Offset(10f/2, size.height/3f),
//                    end = Offset(size.width-10f/2 , size.height/3f),
//                    strokeWidth = 5f
//                )
//                drawRoundRect(Color.Red, Offset(0f, 0f), Size(size.width, size.height))
//            }
//        }
    }
}