package com.songnick.mincy.media_choose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel

/*****
 * @author qfsong
 * Create Time: 2022/9/14
 **/

@Composable
fun ForMediaChooseRoute(modifier: Modifier = Modifier, chooseModel:MediaChooseVM = hiltViewModel()){
    Box(
        modifier = modifier
            .background(Color.Blue)
            .fillMaxWidth()
            .fillMaxHeight(),
        contentAlignment = Alignment.Center
    ){
        LazyVerticalGrid(columns = GridCells.Fixed(22) ){
            item {
                Text(text = "hello world")
            }
        }
    }
}