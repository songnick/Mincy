package com.songnick.mincy.compose.custom

import android.util.Log
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object Tag{
    const val TAG = " Crop Window"
}

@Composable
fun CropWindow(cornerWidth:Dp = 15.dp, lineWidth:Dp = 2.dp){
    BoxWithConstraints(contentAlignment = Alignment.BottomEnd) {
        var offset by remember {
            mutableStateOf(0f)
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
        Log.i("TAG", " canvas width: $canvasWidth + height: $canvasHeight")
        Surface(modifier = Modifier
            .width(canvasWidth)
            .height(canvasHeight)
            .padding(5.dp)
            .scrollable(
                orientation = Orientation.Vertical,
                enabled = true,
                state = rememberScrollableState { delta ->
                    offset += delta
                    Log.i(Tag.TAG, " current deltate: $delta")
                    delta
                })
            .pointerInput(Unit) {
                detectTapGestures {
                    Log.i(Tag.TAG, " detectTapGestures x: ${it.x} y: ${it.y}")
                }
                detectDragGestures { change, dragAmount ->
                    change.position.x
                }
            },
            color = Color.Blue
        ) {
            Canvas(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()){
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

@Composable
fun Line(color: Color = Color.Yellow, width: Dp, height: Dp){
    val widthSplit = width/3
    val heightSplit = height/3
    val  modifier = Modifier
        .width(1.dp)
        .offset(x = widthSplit)
        .fillMaxHeight()
    Surface(color = color, modifier = modifier) {

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
fun RectLine(modifier: Modifier, color: Color = Color.Yellow){
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