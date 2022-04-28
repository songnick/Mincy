package com.songnick.mincy.compose.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.songnick.mincy.viewmodel.MediaViewModel

/*****
 * @author qfsong
 * Create Time: 2022/4/20
 **/
@Composable
fun MincyApp(mediaViewModel: MediaViewModel, requestPermission: ()->Unit) {
    Surface(color = Color.Black) {
        if (!mediaViewModel.permissionRequested){
            Box(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
                contentAlignment = Alignment.Center){
                Button(onClick = requestPermission) {
                    Text(text = "点击请求权限")
                }
            }
        }else{
            when(val state = mediaViewModel.uiState.collectAsState().value){
                is MediaViewModel.UIState.Loading ->{
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                        contentAlignment = Alignment.Center){
                        Text(text = "数据正在加载中",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontFamily = FontFamily.Cursive
                        )
                    }
                }
                is MediaViewModel.UIState.ShowData ->{
                    MediaGrid(pictureList = state.mediaList, 4)
                }
            }
        }
    }
}
