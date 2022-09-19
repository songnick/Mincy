package com.songnick.mincy.compose.custom

import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.graphics.RectF
import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.songnick.mincy.R
import kotlin.math.pow
import kotlin.math.sqrt
import androidx.compose.ui.draw.scale


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

@ExperimentalComposeUiApi
@Composable
fun CropWindow(
    cornerWidth:Dp = 5.dp,
    lineWidth:Dp = 2.dp,
    gestureListener:(dragAmount:Offset, rotation:Float, zoom:Float)->Unit,
    dragCancel:(cancel:Boolean)->Unit, size:DpSize, initCropRect: RectF = RectF(),
    transform:(scale:Float, offset:Offset)->Unit
){
    Log.i(Tag.TAG, " crop window size: $size  init rect: $initCropRect")
    var  boxSize: IntSize
    LocalDensity.current.run {
        Log.i(Tag.TAG, " box size current density: $this")
        boxSize = IntSize(
            size.width.toPx().toInt()+cornerWidth.toPx().times(2).toInt(),
            size.height.toPx().toInt() + cornerWidth.toPx().times(2).toInt()
        )
        initCropRect.set(initCropRect.left-cornerWidth.toPx(), initCropRect.top-cornerWidth.toPx(), initCropRect.right+cornerWidth.toPx(), initCropRect.bottom+ cornerWidth.toPx())
    }
    Log.i(Tag.TAG, " box size: $boxSize")
    val finalSize = DpSize(size.width+cornerWidth*2, size.height+cornerWidth*2)
    Box(modifier = Modifier
        .onSizeChanged {
            Log.i(Tag.TAG, " on size change: $it")
        }
        .size(size = finalSize)) {
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
        val closePointDistance = sqrt((cornerLineWidth*3).pow(2) + (cornerLineWidth*3).pow(2))
        var canvasInde by remember {
            mutableStateOf(0)
        }

        if (initCropRect.width() == 0f){
            val width = boxSize.width
            val height = boxSize.height.toFloat()
            val realHeight = boxSize.height
            //center for crop rect
            val left = 0f
            val top = (realHeight - height).div(2)
            val right = width.toFloat()
            val bottom = (top + height)
            cropRect.set(left, top, right, bottom)
        }else{
            val realHeight = boxSize.height
            val realWidth = boxSize.width
            //center for crop rect
            val left = (realWidth - initCropRect.width()).div(2)
            val top = (realHeight - initCropRect.height()).div(2)
            val right = (left + initCropRect.width())
            val bottom = (top + initCropRect.height())
            cropRect.set(left, top, right, bottom)
        }

        val originRectF by remember {
            mutableStateOf(RectF(cropRect))
        }
        originRectF.set(0f, 0f, boxSize.width.toFloat(), boxSize.height.toFloat())
        Log.i(Tag.TAG, " after crop rect: $cropRect origin: $originRectF")

        Surface(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val e = awaitPointerEvent()
                        if (e.changes.size == 1) {
                            var pointInputChange = e.changes[0]
                            when {
                                pointInputChange.changedToDown() -> {
                                    cropIndex = findDragCorner(
                                        pointInputChange.position,
                                        cropRect,
                                        closePointDistance
                                    )
                                    canvasInde++
                                    Log.i(Tag.TAG, " awaitPointerEventScope cropIndex: $cropIndex")
                                    dragCancel.invoke(false)
                                }
                                pointInputChange.changedToUp() -> {
                                    Log.i(Tag.TAG, " on drag end")
                                    if (cropIndex != CropIndex.Invalid) {
                                        resumeCropGrid(cropIndex, originRectF, cropRect,
                                            update = {
                                                canvasInde++
                                            }
                                        , transform)
                                    } else {
                                        dragCancel?.invoke(true)
                                    }
                                    cropIndex = CropIndex.Invalid

                                }
                                else -> {
                                    if (cropIndex != CropIndex.Invalid) {
                                        dragCorner(
                                            cropIndex = cropIndex,
                                            cropRect = cropRect,
                                            dragAmount = pointInputChange.positionChange()
                                        )
                                        canvasInde++
                                    } else {
                                        gestureListener?.invoke(
                                            pointInputChange.positionChange(),
                                            0f,
                                            1f
                                        )
                                    }
                                }
                            }
                        }
                    }

                }
            },
            color = Color.Transparent
        ) {
            CropOverGrid(canvasInde = canvasInde, cropRect = cropRect, cornerLineWidth = cornerLineWidth, stroke =stroke )
        }
    }
}

private fun resumeCropGrid(cropIndex: CropIndex,originRectF: RectF, cropRect: RectF, update:()->Unit,tanforms:(scale:Float, offset:Offset)->Unit){
    Log.i(Tag.TAG, " current resume crop grid origin rect: $originRectF  cropRect: $cropRect")
    val targetAspectRatio = cropRect.width()/cropRect.height()
    val targetRect = RectF()
    var height = originRectF.width()/targetAspectRatio
    var width:Float
    var halfDiff:Float
    if (height > originRectF.height()){
        width = originRectF.height()*targetAspectRatio
        halfDiff = (originRectF.width() - width )/2
        targetRect.set(halfDiff,0f, width + halfDiff, originRectF.height())
    }else{
        halfDiff = (originRectF.height() - height)/2
        targetRect.set(0f, halfDiff, originRectF.width(), height + halfDiff)
    }
    val widthRatio = targetRect.width()/cropRect.width()
    val heightRatio = targetRect.height()/cropRect.height()
    Log.i(Tag.TAG, " resumeCropGrid crop rect: $cropRect  target rect: $targetRect origin rect: $originRectF" +
            " target ratio: $targetAspectRatio   width scale: $widthRatio height scale: $heightRatio")
    var scale  = if (widthRatio>heightRatio){
        widthRatio
    }else{
        heightRatio
    }
    val oldRectF = RectF(cropRect)
    var dealtX = 0f
    var dealtY = 0f
    when(cropIndex) {
        CropIndex.LeftTop -> {
            dealtX = targetRect.right - oldRectF.right
            dealtY = targetRect.bottom - oldRectF.bottom
        }
        CropIndex.RightTop -> {
            dealtX = targetRect.left - oldRectF.left
            dealtY = targetRect.bottom - oldRectF.bottom
        }
        CropIndex.RightBottom -> {
            dealtX = targetRect.left - oldRectF.left
            dealtY = targetRect.top - oldRectF.top
        }
        CropIndex.LeftBottom -> {
            dealtX = targetRect.right - oldRectF.right
            dealtY = targetRect.top - oldRectF.top
        }
        CropIndex.Invalid -> {

        }
    }
    Log.i(Tag.TAG, " resumeCropGrid dealt x: $dealtX dealt y: $dealtY")
    val scaleXHolder = PropertyValuesHolder.ofFloat("scaleX",1.0f, scale)
    val translateX =  PropertyValuesHolder.ofFloat("translationX", 0f, dealtX);
    val translateY =  PropertyValuesHolder.ofFloat("translationY", 0f, dealtY);
    val objectAnimator = ValueAnimator.ofPropertyValuesHolder(scaleXHolder, translateX, translateY)

    objectAnimator.duration = 200
    objectAnimator.start()
    objectAnimator.addUpdateListener {
        val curXScale = (it.getAnimatedValue("scaleX") as Float)
        val offsetX = it.getAnimatedValue("translationX") as Float
        val offsetY = it.getAnimatedValue("translationY") as Float
        Log.i(Tag.TAG, " current animate x scale: $curXScale y scale $curXScale")
        tanforms.invoke(curXScale, Offset(offsetX, offsetY))
        when(cropIndex){
            CropIndex.LeftTop->{
                cropRect.left = cropRect.right - oldRectF.width().times(curXScale)
                cropRect.top = cropRect.bottom - oldRectF.height().times(curXScale)
                cropRect.right = oldRectF.right+offsetX
                cropRect.bottom = oldRectF.bottom + offsetY
            }
            CropIndex.RightTop->{
                cropRect.right = cropRect.left + oldRectF.width().times(curXScale)
                cropRect.top = cropRect.bottom - oldRectF.height().times(curXScale)
                cropRect.left = oldRectF.left + offsetX
                cropRect.bottom = oldRectF.bottom + offsetY
            }
            CropIndex.RightBottom->{
                cropRect.left = oldRectF.left + offsetX
                cropRect.top = oldRectF.top + offsetY
                cropRect.right = cropRect.left + oldRectF.width().times(curXScale)
                cropRect.bottom = cropRect.top + oldRectF.height().times(curXScale)
            }
            CropIndex.LeftBottom->{
                cropRect.left = cropRect.right - oldRectF.width().times(curXScale)
                cropRect.bottom = cropRect.top + oldRectF.height().times(curXScale)
                cropRect.right = oldRectF.right + offsetX
                cropRect.top = oldRectF.top + offsetY
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
            start = Offset(cornerLineWidth + cropRect.left, cropRect.height()/3f + cropRect.top),
            end = Offset(cropRect.width()-cornerLineWidth + cropRect.left, cropRect.height()/3f+ cropRect.top),
            strokeWidth = stroke
        )
        drawLine(
            color= Color.Yellow,
            start = Offset(cornerLineWidth+ cropRect.left, cropRect.height()/3f*2+ cropRect.top),
            end = Offset(cropRect.width()-cornerLineWidth+ cropRect.left, cropRect.height()/3f*2+ cropRect.top),
            strokeWidth = stroke
        )
        //horizontal grid
        drawLine(
            color= Color.Yellow,
            start = Offset(cropRect.width()/3f+ cropRect.left, cornerLineWidth+ cropRect.top),
            end = Offset(cropRect.width()/3f+ cropRect.left, cropRect.height()-cornerLineWidth+ cropRect.top),
            strokeWidth = stroke
        )
        drawLine(
            color= Color.Yellow,
            start = Offset(cropRect.width()/3f*2+ cropRect.left,cornerLineWidth + cropRect.top ),
            end = Offset(cropRect.width()/3f*2+ cropRect.left, cropRect.height()-cornerLineWidth + cropRect.top),
            strokeWidth = stroke
        )
        val pointList = listOf(
            Offset(cornerLineWidth+ cropRect.left, cropRect.top+cornerLineWidth),
            Offset(cropRect.width()-cornerLineWidth+ cropRect.left,  cropRect.top+cornerLineWidth),
            Offset(cropRect.width()-cornerLineWidth+ cropRect.left, cropRect.top+cornerLineWidth),
            Offset(cropRect.width()-cornerLineWidth+ cropRect.left, cropRect.height()+ cropRect.top-cornerLineWidth),
            Offset(cropRect.width()-cornerLineWidth+ cropRect.left, cropRect.height()+ cropRect.top-cornerLineWidth),
            Offset(cornerLineWidth+ cropRect.left, cropRect.height()+ cropRect.top - cornerLineWidth),
            Offset(cornerLineWidth+ cropRect.left, cropRect.height()+ cropRect.top),
            Offset(cornerLineWidth+ cropRect.left, cropRect.top)
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


@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@SuppressLint("UnrememberedAnimatable")
@Preview
@Composable
fun PreViewD(){
    MaterialTheme {
        Box(modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),contentAlignment = Alignment.Center){
        var imageOffset by remember {
            mutableStateOf(Offset(0f, 0f))
        }
        Log.i(Tag.TAG, " preview d current view: ${LocalView.current}")
        var dragCancel by remember {
            mutableStateOf(false)
        }
        val o by animateOffsetAsState(if (dragCancel)Offset(0f,0f)else imageOffset, spring()){
            imageOffset = Offset(0f, 0f)
        }
        var scaleImage by remember {
            mutableStateOf(1.0f)
        }
        val cropSize = DpSize(360.dp, 640.dp)
        val image = painterResource(id = 0)
        Log.i(Tag.TAG, " painter size: ${image.intrinsicSize}")
        val imageRatio = image.intrinsicSize.width/image.intrinsicSize.height
        val cropRatio = cropSize.width/cropSize.height
        var width:Dp
        var height:Dp
        if (imageRatio > cropRatio){
             width = cropSize.width
             height = cropSize.width/imageRatio
        }else{
            width = cropSize.width*imageRatio
            height = cropSize.height
        }

        var rectF by remember {
            mutableStateOf(RectF())
        }
        LocalDensity.current.run {
            rectF.set(0f, 0f, width.toPx(), height.toPx())
        }
        Image(
            painter = image, "",
            modifier = Modifier
                .size(width = width, height = height)
                .offset {
                    if (dragCancel) {
                        IntOffset(o.x.toInt(), o.y.toInt())
                    } else {
                        IntOffset(imageOffset.x.toInt(), imageOffset.y.toInt())
                    }
                }.scale(scale = scaleImage),
        )
        
        CropWindow(gestureListener = {dragAmount,_,_ ->
            imageOffset += dragAmount
            false
        }, dragCancel = {
            dragCancel = it
        }, size = cropSize, initCropRect = rectF, transform = {scale, offset->
            scaleImage = scale
            imageOffset = offset
        })
    }}
}