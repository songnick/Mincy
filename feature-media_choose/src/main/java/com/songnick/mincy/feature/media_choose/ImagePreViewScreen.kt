package com.songnick.mincy.feature.media_choose

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.font.FontWeight
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
import com.songnick.mincy.core.data.model.Image
import com.songnick.mincy.core.data.model.Media
import com.songnick.mincy.core.data.model.createEmptyImage
import com.songnick.mincy.core.design_system.MincyTheme
import com.songnick.mincy.core.design_system.component.MincyBackground
import com.songnick.mincy.core.design_system.component.MincySimpleTopAppBar
import com.songnick.mincy.core.design_system.theme.colorWithAlpha
import com.songnick.mincy.core.nav.MincyNavRoute
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

/*****
 * @author qfsong
 * Create Time: 2022/9/28
 **/
@OptIn(
    ExperimentalPagerApi::class,
    ExperimentalMaterial3Api::class,
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
                    if (statusBarVisible.value){
                        MincySimpleTopAppBar(
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
                    }
                },
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
                        statusBarVisible.value = !statusBarVisible.value
                    })
                }
                LaunchedEffect(pagerState){
                    snapshotFlow {
                        pagerState.currentPage
                    }.collect{
                        Log.i("ImagePreViewScreen", " current index: $it")
                        index = it+1
                        currentMedia.value = pictureList[index]
                        CompositionLocalProvider(currentCompositionLocalContext) {


                        }
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
                PreviewImageFloatingAction(modifier = Modifier)
            }
        }
    }
}

private val LocalCurrentImage = staticCompositionLocalOf { createEmptyImage() }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreviewImageFloatingAction(modifier: Modifier){
    val context = LocalContext.current
    val curImage = LocalCurrentImage.current
    Box(modifier = modifier
        .fillMaxSize()
    ){
        Card(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(start = 24.dp, end = 24.dp, bottom = 24.dp),
            shape = RoundedCornerShape(14.dp)
        ) {
            Row(modifier = Modifier.background(MaterialTheme.colorScheme.surface)
            ) {
                val modifier = Modifier.weight(1.0f)
                FloatActionTab(modifier = modifier,R.drawable.share,"分享")
                FloatActionTab(modifier = modifier,R.drawable.heart_plus,"收藏")
                FloatActionTab(
                    modifier = modifier,
                    R.drawable.edit_square,"编辑",
                    onClick = {
                        if (context is Activity){
                            context.startActivity(Intent().apply {
                                action = MincyNavRoute.IMAGE_EDIT_ROUTE
                                putExtra("edit_image", curImage)
                            })
                        }
                    }
                )
                FloatActionTab(modifier = modifier,R.drawable.delete,"删除")
            }
        }

    }
}

@Composable
fun FloatActionTab(
    modifier: Modifier,
    tabIcon:Int,
    tabName:String,
    onClick: () -> Unit = {}
){
    Column(modifier = modifier
        .padding(top = 16.dp, bottom = 16.dp)
        .clickable {
            onClick.invoke()
        }
    ) {
        val childModifier = Modifier.align(Alignment.CenterHorizontally)
        Icon(
            painter = painterResource(id = tabIcon),
            contentDescription = tabName,
            modifier = childModifier)
        Spacer(modifier = childModifier.height(2.dp))
        Text(text = tabName, fontSize = 12.sp, fontWeight = FontWeight.Bold , color = Color.Black, modifier = childModifier)
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
                    colorWithAlpha(Color.Black, 0.3f)
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