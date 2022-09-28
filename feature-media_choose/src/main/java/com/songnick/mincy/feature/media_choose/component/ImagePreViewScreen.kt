package com.songnick.mincy.feature.media_choose.component

import android.os.Build
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.songnick.mincy.R
import com.songnick.mincy.base_ui.MincyTheme
import com.songnick.mincy.base_ui.component.MincyBackground
import com.songnick.mincy.core.data.model.Image
import com.songnick.mincy.feature.media_choose.MediaChooseVM
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/*****
 * @author qfsong
 * Create Time: 2022/9/28
 **/
@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ImagePreViewScreen(
    previewIndex:Int = 0,
    pictureList:List<Image>
) {
    MincyTheme {
        MincyBackground {
            Scaffold(
                topBar = {
                    PreviewTopAppBar(
                        title = R.string.app_name,
                        modifier = Modifier.windowInsetsPadding(
                            WindowInsets.safeDrawing.only(WindowInsetsSides.Top)
                        ),
                        navigationIcon = Icons.Rounded.ArrowBack,
                        actionIcon = Icons.Rounded.Search,
                        navigationOnClick = {},
                        actionOnClick = {}
                    )
                },
                containerColor = Color.Transparent
            ) { paddingValues ->
                val pagerState = rememberPagerState()
                HorizontalPager(
                    count = pictureList!!.size,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    state = pagerState
                ) {
                    PreviewImage(data = pictureList[it])
                }
                LaunchedEffect(key1=pictureList.size){
                    GlobalScope.launch {
                        pagerState.scrollToPage(previewIndex)
                    }
                }
            }
        }
    }
}

@Composable
fun PreviewImage(data: Image){
    //may need to load gif
    val imageLoader = if (data.path.endsWith("gif")){
        ImageLoader.Builder(LocalContext.current)
            .components {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }
            .build()
    }else{
        ImageLoader.Builder(LocalContext.current).build()
    }
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(data.path)
            .crossfade(true)
            .build(),
        imageLoader = imageLoader,
        contentDescription = "",
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
    )
}


@Composable
fun PreviewTopAppBar(
    @StringRes title:Int,
    modifier: Modifier,
    navigationIcon:ImageVector,
    actionIcon:ImageVector,
    colors: TopAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
    navigationOnClick:() -> Unit = {},
    actionOnClick:() -> Unit = {}
    ){
    CenterAlignedTopAppBar(
        title = { Text(text = stringResource(id = title))},
        navigationIcon = { IconButton(onClick = navigationOnClick) {
            Icon(imageVector = navigationIcon, contentDescription ="" )
        }},
        actions = {
            IconButton(onClick = actionOnClick) {
                Icon(imageVector = actionIcon, contentDescription = "")
            }
        },
        colors = colors,
        modifier = modifier
    )
}