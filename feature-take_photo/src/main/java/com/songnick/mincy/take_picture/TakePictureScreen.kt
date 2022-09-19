package com.songnick.mincy.take_picture

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

/*****
 * @author qfsong
 * Create Time: 2022/9/19
 **/
@Composable
fun TakePictureScreen(){
    Box(
        modifier = Modifier
            .background(Color.Green)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ){
        Text(text = "I don't know")
    }
}