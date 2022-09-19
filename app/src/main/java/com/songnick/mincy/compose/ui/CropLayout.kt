//package com.songnick.mincy.compose.ui
//
//import android.util.Log
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.gestures.*
//import androidx.compose.foundation.layout.*
//import androidx.compose.material.Button
//import androidx.compose.material.MaterialTheme
//import androidx.compose.material.Surface
//import androidx.compose.material.Text
//import androidx.compose.runtime.*
//import androidx.compose.ui.ExperimentalComposeUiApi
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.geometry.Offset
//import androidx.compose.ui.geometry.Size
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.input.pointer.*
//import androidx.compose.ui.layout.*
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.IntOffset
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.toSize
//import com.songnick.mincy.compose.custom.CropImageView
//import com.songnick.mincy.compose.custom.CropWindow
//import kotlin.math.roundToInt
//
///*****
// * @author qfsong
// * Create Time: 2022/4/26
// **/
//@Composable
//fun MyBasicColumn(
//    modifier: Modifier,
//    content: @Composable () -> Unit
//) {
//    Layout(
//        content,
//        modifier
//    ){ measurables, constraints ->
//        // Don't constrain child views further, measure them with given constraints
//        // List of measured children
//        val parcelables = measurables.map { measurable ->
//            // Measure each children
//            measurable.measure(constraints)
//        }
//
//        // Set the size of the layout as big as it can
//        layout(constraints.maxWidth, constraints.maxHeight) {
//            // Track the y co-ord we have placed children up to
//            var yPosition = 0
//
//            // Place children in the parent layout
//            parcelables.forEach { placeable ->
//                // Position item on the screen
//                placeable.placeRelative(x = 0, y = yPosition)
//
//                // Record the y co-ord placed up to
//                yPosition += placeable.height
//            }
//        }
//    }
//}
//
//@Composable
//fun VerticalGrids(
//    modifier: Modifier = Modifier,
//    columns: Int = 2,
//    content: @Composable () -> Unit
//) {
//    Layout(
//        content = content,
//        modifier = modifier
//    ) { measurables, constraints ->
//        val itemWidth = constraints.maxWidth / columns
//        // Keep given height constraints, but set an exact width
//        val itemConstraints = constraints.copy(
//            minWidth = itemWidth,
//            maxWidth = itemWidth
//        )
//        // Measure each item with these constraints
//        val placeables = measurables.map { it.measure(itemConstraints) }
//        // Track each columns height so we can calculate the overall height
//        val columnHeights = Array(columns) { 0 }
//        placeables.forEachIndexed { index, placeable ->
//            val column = index % columns
//            columnHeights[column] += placeable.height
//        }
//        val height = (columnHeights.maxOrNull() ?: constraints.minHeight)
//            .coerceAtMost(constraints.maxHeight)
//        layout(
//            width = constraints.maxWidth,
//            height = height
//        ) {
//            // Track the Y co-ord per column we have placed up to
//            val columnY = Array(columns) { 0 }
//            placeables.forEachIndexed { index, placeable ->
//                val column = index % columns
//                placeable.placeRelative(
//                    x = column * itemWidth,
//                    y = columnY[column]
//                )
//                columnY[column] += placeable.height
//            }
//        }
//    }
//}
//
//@ExperimentalComposeUiApi
//@Preview
//@Composable
//fun My(){
//    MaterialTheme {
//        val offsetX = remember { mutableStateOf(0f) }
//        val offsetY = remember { mutableStateOf(0f) }
//        var size by remember { mutableStateOf(Size.Zero) }
//        Box(
//            Modifier
////                .pointerInteropFilter {
////                    Log.i("TAG", " first layer event action: $it")
////
////                    return@pointerInteropFilter true
////                }
//                .pointerInput(Unit) {
//                    forEachGesture {
//                        awaitPointerEventScope {
//                            val event = awaitPointerEvent()
//                            Log.i("TAG", " first layer event: ")
////                            event.changes[0].consumeDownChange()
//                        }
//                    }
//                }
//                .fillMaxSize()
//
//                .onSizeChanged {
//                    size = it.toSize()
//                }
//        ) {
//            Box(
//                Modifier
//                    .offset { IntOffset(offsetX.value.roundToInt(), offsetY.value.roundToInt()) }
//                    .size(250.dp)
//                    .offset(100.dp, 100.dp)
//                    .background(Color.Blue)
//                    .pointerInput(Unit) {
//                        forEachGesture {
//                            awaitPointerEventScope {
//                                awaitPointerEvent(PointerEventPass.Final)
//                                val down = awaitFirstDown()
//                                Log.i("TAG", " down: $down")
//
//                                var change = awaitTouchSlopOrCancellation(down.id) { change, over ->
//                                    val original = Offset(offsetX.value, offsetY.value)
//                                    val summed = original + over
//                                    val newValue = Offset(
//                                        x = summed.x.coerceIn(0f, size.width - 50.dp.toPx()),
//                                        y = summed.y.coerceIn(0f, size.height - 50.dp.toPx())
//                                    )
////                                    change.consume()
//                                    offsetX.value = newValue.x
//                                    offsetY.value = newValue.y
//                                    Log.i("TAG", " chnage》》》》: $change")
//                                }
//                                Log.i("TAG", " chnage: $change")
//
//                                while (change != null && change.pressed) {
//                                    change = awaitDragOrCancellation(change.id)
//                                    Log.i("TAG", " awaitDragOrCancellation: $change")
//
//                                    if (change != null && change.pressed) {
//                                        val original = Offset(offsetX.value, offsetY.value)
//                                        val summed = original + change.positionChange()
//                                        val newValue = Offset(
//                                            x = summed.x.coerceIn(0f, size.width - 50.dp.toPx()),
//                                            y = summed.y.coerceIn(0f, size.height - 50.dp.toPx())
//                                        )
////                                        change.consume()
//                                        offsetX.value = newValue.x
//                                        offsetY.value = newValue.y
//                                    }
//                                }
//                            }
//                        }
//                    }
//            ){
//
//            }
//        }
//        Box(
//            Modifier
//                .pointerInteropFilter {
//                    Log.i("TAG", " second layer event action : $it")
//
//                    return@pointerInteropFilter true
//                }
//                .pointerInput(Unit) {
//                    forEachGesture {
//                        awaitPointerEventScope {
//                            val event = awaitPointerEvent(PointerEventPass.Final)
//                            Log.i("TAG", " second layer event: $event")
//                            event.changes[0].consumeDownChange()
//                        }
//                    }
//                }
//                .fillMaxSize()
//        ){
//            Button(onClick = {
//                Log.i("TAG", " click is invoked")
//            }) {
//                Text(text = "hello")
//            }
//        }
//    }
//}
