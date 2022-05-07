package com.songnick.mincy.compose.custom

import android.graphics.Rect
import android.graphics.RectF
import android.util.Log
import android.view.MotionEvent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.triStateToggleable
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
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
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.pow
import kotlin.math.sqrt

object Tag{
    const val TAG = " Crop Window"
}

sealed class CropIndex(index:Int = 0){
    val index = index
    object LeftTop: CropIndex(0)
    object RightTop: CropIndex(1)
    object LeftBottom: CropIndex(2)
    object RightBottom: CropIndex(3)

    companion object{
        fun getIndex(index: Int): CropIndex {
            when (index) {
                0 -> return LeftTop
                1 -> return RightTop
                2 -> return RightBottom
                3 -> return LeftBottom
            }
            return LeftTop
        }
    }
}

@Composable
fun CropWindow(cornerWidth:Dp = 15.dp, lineWidth:Dp = 2.dp, aspectRatio:Float = 1.0f){
    BoxWithConstraints(contentAlignment = Alignment.BottomEnd) {
        var cropRect by remember {
            mutableStateOf(RectF(0f, 0f, 0f, 0f))
        }
        val cornerLineWidth = with(LocalDensity.current){
            cornerWidth.toPx()
        }

        val stroke = with(LocalDensity.current){
            lineWidth.toPx()
        }
        var cropIndex:CropIndex = CropIndex.LeftTop
        val surfacePadding = 20.dp
        val closePointDistance = sqrt((cornerLineWidth*3).pow(2) + (cornerLineWidth*3).pow(2))
        var canvasInde by remember {
            mutableStateOf(0)
        }
        Surface(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(surfacePadding)
            .pointerInput(Unit) {
                detectDragGestures(onDragStart = {
                    val cornerPoint = arrayOf(
                        cropRect.left, cropRect.top,
                        cropRect.right, cropRect.top,
                        cropRect.right, cropRect.bottom,
                        cropRect.left, cropRect.bottom
                    )
                    var distance = closePointDistance
                    var cropIn = 0
                    for (index in cornerPoint.indices step 2) {
                        val cornerSlop = sqrt(
                            (it.x - cornerPoint[index])
                                .toDouble()
                                .pow(2.0) + (it.y - cornerPoint[index + 1])
                                .toDouble()
                                .pow(2.0)
                        )

                        if (cornerSlop < distance) {
                            distance = cornerSlop.toFloat()
                            cropIn = index / 2
                        }
                        Log.i(Tag.TAG, " corner slop: $cornerSlop  distance: $closePointDistance ")
                    }
                    cropIndex = CropIndex.getIndex(cropIn)
                }) { change, dragAmount ->
                    Log.i(
                        Tag.TAG,
                        " detectDragGestures x: ${change.position.x} drag amount y: ${dragAmount.y} drag amount x: ${dragAmount.x} crop index: $cropIndex"
                    )
                    when (cropIndex) {
                        is CropIndex.LeftTop -> {

                        }
                        is CropIndex.LeftBottom -> {
                            Log.i(Tag.TAG, " left bottom >>>>>>>>>>>>>>> ${cropRect.hashCode()} ")
                            cropRect.bottom = cropRect.bottom + dragAmount.y
                            cropRect.left = cropRect.left + dragAmount.x
                            Log.i(Tag.TAG, " left bottom <<<<<<<<<<<<>>>>> ${cropRect.hashCode()} ")
                        }
                        is CropIndex.RightTop -> {

                        }
                        is CropIndex.RightBottom -> {

                        }
                    }
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
            val padding = LocalDensity.current.run {
                surfacePadding.toPx()
            }
            if (cropRect.width()> 0){
                Log.i(Tag.TAG, " >>>>> canvas conmpose crop rect: $cropRect")
            }
            cropRect.set(0f, 0f,(width-padding*2), (height-padding*2))
            Log.i(Tag.TAG, " width : $width crop rect $cropRect")
            Canvas(modifier = Modifier){
                if (canvasInde>0){
                    Log.i(Tag.TAG, " canvas conmpose start")
                }
                if (cropRect.width()> 0){
                    Log.i(Tag.TAG, " canvas conmpose crop rect: $cropRect")
                }
                //vertical grid
                drawLine(
                    color= Color.Yellow,
                    start = Offset(cornerLineWidth/2 + cropRect.left, cropRect.height()/3f + cropRect.top),
                    end = Offset(cropRect.width()-cornerLineWidth/2 + cropRect.left, cropRect.height()/3f+ cropRect.top),
                    strokeWidth = stroke
                )
                drawLine(
                    color= Color.Yellow,
                    start = Offset(cornerLineWidth/2+ cropRect.left, cropRect.height()/3f*2+ cropRect.top),
                    end = Offset(cropRect.width()-cornerLineWidth/2+ cropRect.left, cropRect.height()/3f*2+ cropRect.top),
                    strokeWidth = stroke
                )
                //horizontal grid
                drawLine(
                    color= Color.Yellow,
                    start = Offset(cropRect.width()/3f+ cropRect.left, cornerLineWidth/2+ cropRect.top),
                    end = Offset(cropRect.width()/3f+ cropRect.left, cropRect.height()-cornerLineWidth/2+ cropRect.top),
                    strokeWidth = stroke
                )
                drawLine(
                    color= Color.Yellow,
                    start = Offset(cropRect.width()/3f*2+ cropRect.left,cornerLineWidth/2 + cropRect.top ),
                    end = Offset(cropRect.width()/3f*2+ cropRect.left, cropRect.height()-cornerLineWidth/2 + cropRect.top),
                    strokeWidth = stroke
                )
                val pointList = listOf(
                    Offset(cornerLineWidth/2+ cropRect.left, cornerLineWidth/2+ cropRect.top),
                    Offset(cropRect.width()-cornerLineWidth/2+ cropRect.left, cornerLineWidth/2+ cropRect.top),
                    Offset(cropRect.width()-cornerLineWidth/2+ cropRect.left, cornerLineWidth/2+ cropRect.top),
                    Offset(cropRect.width()-cornerLineWidth/2+ cropRect.left, cropRect.height()-cornerLineWidth/2+ cropRect.top),
                    Offset(cropRect.width()-cornerLineWidth/2+ cropRect.left, cropRect.height()-cornerLineWidth/2+ cropRect.top),
                    Offset(cornerLineWidth/2+ cropRect.left, cropRect.height()-cornerLineWidth/2+ cropRect.top),
                    Offset(cornerLineWidth/2+ cropRect.left, cropRect.height()-cornerLineWidth/2+ cropRect.top),
                    Offset(cornerLineWidth/2+ cropRect.left, cornerLineWidth/2+ cropRect.top)
                )
                //image crop line
                drawPoints(
                    pointList,
                    pointMode = PointMode.Lines,
                    color=Color.Yellow,
                    strokeWidth=stroke
                )

                clipRect(
                    left = cornerLineWidth+ cropRect.left,
                    top = cornerLineWidth+ cropRect.top,
                    right =  cropRect.right - cornerLineWidth,
                    bottom = cropRect.bottom - cornerLineWidth,
                    clipOp = ClipOp.Difference
                ){
                    clipRect(
                        left = cornerLineWidth*3+ cropRect.left,
                        top =  cropRect.top,
                        right = cropRect.right - 3*cornerLineWidth,
                        bottom = cropRect.bottom,
                        clipOp = ClipOp.Difference
                    ){
                        clipRect(
                            left =  cropRect.left,
                            top = cornerLineWidth*3f  + cropRect.top,
                            right = cropRect.right,
                            bottom = cropRect.top + cropRect.height()-3*cornerLineWidth,
                            clipOp = ClipOp.Difference
                        ){
                            drawRect(color = Color.Yellow,
                                topLeft = Offset(cropRect.left, cropRect.top),
                                size = Size(width = cropRect.width(), height = cropRect.height())
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
//        CropWindow()
        Column(modifier = Modifier.padding(16.dp)) {
            var name by remember { mutableStateOf("") }
            var index by remember {
                mutableStateOf(0)
            }
            var rect by remember {
                mutableStateOf(Rect())
            }
            if (name.isNotEmpty()) {
                Log.i(Tag.TAG, " compose start ")
            }

            if (index > 0) {
                Log.i(Tag.TAG, " compose start index ")
            }
            if (rect.width()> 0){
                Log.i(Tag.TAG, " compose start rect $rect ")
            }
            OutlinedTextField(
                value = "name",
                onValueChange = {
                    name = "it i    ndex++"
                    rect.right = 1000 + rect.right
                                },
                label = { Text("Name") }
            )
        }
    }
}