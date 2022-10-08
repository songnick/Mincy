package com.songnick.mincy.compose_ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/*****
 * @author qfsong
 * Create Time: 2022/9/29
 **/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestApp() {
    Scaffold {
        Box(modifier = Modifier
            .padding(it)
            .fillMaxSize()){
            Text(text = "hello world", modifier = Modifier.align(Alignment.Center))
        
        }
    }
}