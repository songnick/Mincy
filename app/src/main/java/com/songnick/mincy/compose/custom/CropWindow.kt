package com.songnick.mincy.compose.custom

import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.graphics.RectF
import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.BottomSheetValue
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
import kotlin.math.pow
import kotlin.math.sqrt

object Tag{
    const val TAG = " Crop Window"
}

sealed class CropIndex(){
    object LeftTop: CropIndex()
    object RightTop: CropIndex()
    object LeftBottom: CropIndex()
    object RightBottom: CropIndex()
    object Invalid: CropIndex()
}

sealed class CropModel(){
    object FreeModel:CropModel()
    object OneToOneModel:CropModel()
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
        var cropIndex:CropIndex = CropIndex.Invalid
        val surfacePadding = 20.dp
        val closePointDistance = sqrt((cornerLineWidth*3).pow(2) + (cornerLineWidth*3).pow(2))
        var canvasInde by remember {
            mutableStateOf(0)
        }
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
        val realHeight = LocalDensity.current.run {
            maxHeight.toPx()
        }
        //center for crop rect
        val left = 0f
        val top = (realHeight - height).div(2)
        val right = (width-padding*2)
        val bottom = (top + height-padding*2)
        cropRect.set(left, top, right, bottom)
        val originRectF by remember {
            mutableStateOf(RectF(cropRect))
        }
        Surface(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(surfacePadding)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
                        Log.i(Tag.TAG, " on drag start: $it")
                        cropIndex = findDragCorner(it, cropRect, closePointDistance)
                        canvasInde++
                    },
                    onDragEnd = {
                        Log.i(Tag.TAG, " on drag end")
                        resumeCropGrid(
                            cropIndex,
                            originRectF,
                            cropRect,
                            update={
                            canvasInde++
                        }
                        )
                    }
                ) { _, dragAmount ->
                    if (cropIndex != CropIndex.Invalid) {
                        dragCorner(
                            cropIndex = cropIndex,
                            cropRect = cropRect,
                            dragAmount = dragAmount
                        )
                        canvasInde++
                    }
                }
            },
            color = Color.Transparent
        ) {
            Log.i(Tag.TAG, " width : $width crop rect $cropRect")
            CropOverGrid(canvasInde = canvasInde, cropRect = cropRect, cornerLineWidth = cornerLineWidth, stroke =stroke )
        }
    }
}

private fun resumeCropGrid(cropIndex: CropIndex,originRectF: RectF, cropRect: RectF, update:()->Unit){
    val scaleX = originRectF.width()/cropRect.width()
    val scaleY = originRectF.height()/cropRect.height()
    val scaleXHolder = PropertyValuesHolder.ofFloat("scaleX",1.0f, scaleX)
    val scaleYHolder = PropertyValuesHolder.ofFloat("scaleY",1.0f, scaleY)
    val objectAnimator = ValueAnimator.ofPropertyValuesHolder(scaleXHolder,scaleYHolder)
    val oldRectF = RectF(cropRect)
    objectAnimator.duration = 200
    objectAnimator.start()
    objectAnimator.addUpdateListener {
        val curXScale = (it.getAnimatedValue("scaleX") as Float)
        val curYScale = (it.getAnimatedValue("scaleY") as Float)
        Log.i(Tag.TAG, " current animate x scale: $curXScale y scale $curYScale")
        when(cropIndex){
            CropIndex.LeftTop->{
                cropRect.left = cropRect.right - oldRectF.width().times(curXScale)
                cropRect.top = cropRect.bottom - oldRectF.height().times(curYScale)
            }
            CropIndex.RightTop->{
                cropRect.right = cropRect.left + oldRectF.width().times(curXScale)
                cropRect.top = cropRect.bottom - oldRectF.height().times(curYScale)
            }
            CropIndex.RightBottom->{
                cropRect.right = cropRect.left + oldRectF.width().times(curXScale)
                cropRect.bottom = cropRect.top + oldRectF.height().times(curYScale)
            }
            CropIndex.LeftBottom->{
                cropRect.left = cropRect.right - oldRectF.width().times(curXScale)
                cropRect.bottom = cropRect.top + oldRectF.height().times(curYScale)
            }
            CropIndex.Invalid->{

            }
        }
        if (cropIndex != CropIndex.Invalid){
            update.invoke()
        }
    }
}

/**
 *drag
 *
 */
private fun dragCorner(cropIndex:CropIndex, cropRect: RectF, dragAmount:Offset){
    when (cropIndex) {
        is CropIndex.LeftTop -> {
            cropRect.top = (cropRect.top + dragAmount.y).coerceAtLeast(0f)
            cropRect.left = (cropRect.left + dragAmount.x).coerceAtLeast(0f)
        }
        is CropIndex.LeftBottom -> {
            cropRect.bottom = cropRect.bottom + dragAmount.y
            cropRect.left = (cropRect.left + dragAmount.x).coerceAtLeast(0f)
        }
        is CropIndex.RightTop -> {
            cropRect.top = cropRect.top + dragAmount.y
            cropRect.right = cropRect.right + dragAmount.x
        }
        is CropIndex.RightBottom -> {
            cropRect.bottom = cropRect.bottom + dragAmount.y
            cropRect.right = cropRect.right + dragAmount.x
        }
        else -> {
            Log.i(Tag.TAG, " drag invald corner")
        }
    }
}


private fun findDragCorner(dragStartOffset: Offset, cropRect: RectF, closePointDistance:Float): CropIndex {
    val cornerPoint = arrayOf(
        cropRect.left, cropRect.top,
        cropRect.right, cropRect.top,
        cropRect.right, cropRect.bottom,
        cropRect.left, cropRect.bottom
    )
    var distance = closePointDistance
    var cropIn = -1
    for (index in cornerPoint.indices step 2) {
        val cornerSlop = sqrt(
            (dragStartOffset.x - cornerPoint[index])
                .toDouble()
                .pow(2.0) + (dragStartOffset.y - cornerPoint[index + 1])
                .toDouble()
                .pow(2.0)
        )

        if (cornerSlop < distance) {
            distance = cornerSlop.toFloat()
            cropIn = index / 2
        }

        Log.i(Tag.TAG, " corner slop: $cornerSlop  distance: $closePointDistance ")
    }
    when (cropIn) {
        0 -> return CropIndex.LeftTop
        1 -> return CropIndex.RightTop
        2 -> return CropIndex.RightBottom
        3 -> return CropIndex.LeftBottom
    }
    return CropIndex.Invalid
}


@Composable
fun CropOverGrid(canvasInde:Int, cropRect:RectF, cornerLineWidth:Float, stroke:Float){
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


@SuppressLint("UnrememberedAnimatable")
@Preview
@Composable
fun PreViewD(){
    MaterialTheme {
        CropWindow()

//        Column(modifier = Modifier.padding(16.dp)) {
//            var name by remember { mutableStateOf("") }
//            var index by remember {
//                mutableStateOf(0)
//            }
//            var rect by remember {
//                mutableStateOf(Rect())
//            }
//            if (name.isNotEmpty()) {
//                Log.i(Tag.TAG, " compose start ")
//            }
//
//            if (index > 0) {
//                Log.i(Tag.TAG, " compose start index ")
//            }
//            if (rect.width()> 0){
//                Log.i(Tag.TAG, " compose start rect $rect ")
//            }
//            OutlinedTextField(
//                value = "name",
//                onValueChange = {
//                    name = "it i    ndex++"
//                    rect.right = 1000 + rect.right
//                                },
//                label = { Text("Name") }
//            )
//        }
    }
}