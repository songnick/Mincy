package com.songnick.mincy.feature.media_choose

import android.app.Activity
import android.os.Build
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.songnick.mincy.R
import com.songnick.mincy.base_ui.component.MincyBackground
import com.songnick.mincy.core.data.model.Image
import com.songnick.mincy.core.data.model.Media
import com.songnick.mincy.core.design_system.MincyTheme
import com.songnick.mincy.core.design_system.theme.colorWithAlpha
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

/*****
 * @author qfsong
 * Create Time: 2022/9/28
 **/
@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun ImagePreViewScreen(
    previewIndex:Int = 14,
    pictureList:List<Image>
) {
    val activity = LocalContext.current as Activity
    var index by remember {
        mutableStateOf(1)
    }
    val systemUiController = rememberSystemUiController()
    val statusBarVisible = remember {
        mutableStateOf(true)
    }
    val showInfo = remember {
        mutableStateOf(false)
    }
    systemUiController.isStatusBarVisible = statusBarVisible.value
    MincyTheme {
        MincyBackground {
            Scaffold(
                modifier = Modifier.semantics { testTagsAsResourceId = true },
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.onBackground,
                topBar = {
                    PreviewTopAppBar(
                        title = index.toString().plus("/").plus(pictureList.size.toString()),
                        modifier = Modifier.windowInsetsPadding(
                            WindowInsets.safeDrawing.only(WindowInsetsSides.Top)
                        ),
                        navigationIcon = Icons.Rounded.ArrowBack,
                        actionIcon = ImageVector.vectorResource(id = R.drawable.info),
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        navigationOnClick = {
                            activity.finish()
                        },
                        actionOnClick = {
                            showInfo.value = !showInfo.value
                        }
                    )
                },
                floatingActionButton = {
                    Text(text = "helloWorld!")

                },
                floatingActionButtonPosition = FabPosition.Center
            ) { paddingValues ->
                val pagerState = rememberPagerState()
                val top = paddingValues.calculateTopPadding()
                val currentMedia = remember {
                    mutableStateOf(pictureList[previewIndex])
                }
                HorizontalPager(
                    count = pictureList!!.size,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface),
                    state = pagerState
                ) {
                    PreviewImage(data = pictureList[it], onClick = {
//                        statusBarVisible.value = !statusBarVisible.value
                    })
                }
                LaunchedEffect(pagerState){
                    snapshotFlow {
                        pagerState.currentPage
                    }.collect{
                        Log.i("ImagePreViewScreen", " current index: $it")
                        index = it+1
                        currentMedia.value = pictureList[index]
                    }

                }
                LaunchedEffect(key1=pictureList.size){
                    MainScope().launch {
                        pagerState.scrollToPage(previewIndex)
                    }
                }
                if (showInfo.value){
                    PreviewImageInfo(media = currentMedia.value)
                }
            }
        }
    }
}


@Composable
fun PreviewImageInfo(media:Media){
    val padding = 28.dp
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(start = padding, end = padding)
    ){
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .aspectRatio(1.333f)
                .clip(RoundedCornerShape(12.dp))
                .background(
                    Color(
                        red = Color.Black.red,
                        green = Color.Black.green,
                        blue = Color.Black.blue,
                        alpha = 0.3f
                    )
                )
        ) {
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)) {
                ImageInfoText(typeName = "文件名称  ", typeContent = media.name)
                Spacer(modifier = Modifier
                    .height(12.dp)
                    .fillMaxWidth())
                ImageInfoText(typeName = "拍摄时间  ", typeContent = getDateString(media.date))
                Spacer(modifier = Modifier
                    .height(12.dp)
                    .fillMaxWidth())
                ImageInfoText(typeName = "文件路径  ", typeContent = media.path)
            }
        }

    }
}

private fun getDateString(dateTime:Long):String{
//    val secondApiFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
//
//    val date = Date(dateTime)
//    val timestampAsDateString = LocalDate.now()
//
//    Log.d("parseTesting", timestampAsDateString) // prints 2019-08-07T20:27:45Z
    val pattern = "yyyy-MM-dd HH:mm:ss"
    val simpleDateFormat = SimpleDateFormat(pattern)
    val date: String = simpleDateFormat.format(Date(dateTime))
    println(date)
    return date
}

@Composable
fun ImageInfoText(typeName:String, typeContent:String){
    Row() {
        Text(text = typeName,color= colorWithAlpha(Color.White, 0.8f), fontSize = 14.sp)
        Text(text = typeContent,color= Color.White, fontSize = 14.sp)
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PreviewImage(data: Image, onClick:()->Unit){
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
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .fillMaxSize()
            .combinedClickable(
                onClick = onClick
            )
    )
}


@Composable
fun PreviewTopAppBar(
    title:String,
    modifier: Modifier,
    navigationIcon:ImageVector,
    actionIcon:ImageVector,
    colors: TopAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
    navigationOnClick:() -> Unit = {},
    actionOnClick:() -> Unit = {}
    ){
    CenterAlignedTopAppBar(
        title = { Text(text = title)},
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

@Preview
@Composable
fun ViewShape(){
    Box(modifier = Modifier
        .fillMaxSize()
    ){
        Column(modifier = Modifier
            .align(Alignment.Center)
            .width(220.dp)
            .aspectRatio(1.333f)
            .padding(10.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(
                Color(
                    red = Color.Black.red,
                    green = Color.Black.green,
                    blue = Color.Black.blue,
                    alpha = 1f
                )
            )
        ) {
//                            Text(text = "信息", color = Color.White, fontSize = 14.sp, modifier = Modifier.fillMaxSize())
            Surface(modifier = Modifier
                .padding(22.dp)
                .background(Color.Red)
                .fillMaxWidth()
                .fillMaxHeight(), contentColor = Color.Red,color = Color.Red) {
                Text(text = "hhhlll")
            }
        }

    }
}