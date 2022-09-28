package com.songnick.mincy.feature.media_choose

import android.content.Intent
import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.songnick.mincy.R
import com.songnick.mincy.base_ui.MincyIcons
import com.songnick.mincy.core.data.model.Video
import com.songnick.mincy.feature.media_choose.component.CardState
import com.songnick.mincy.feature.media_choose.component.ImageCard
import com.songnick.mincy.feature.media_choose.component.VideoCard


/*****
 * @author qfsong
 * Create Time: 2022/9/14
 **/
const val TAG = "ForMediaChooseRoute"
const val MAX_SELECTED_PIC = 9
@OptIn(
    ExperimentalLifecycleComposeApi::class,
    ExperimentalMaterial3Api::class,
    ExperimentalLayoutApi::class
)
@Composable
fun ForMediaChooseRoute(modifier: Modifier = Modifier, chooseModel:MediaChooseVM = hiltViewModel()){
    Scaffold(
        topBar = {
            MincyTopAppBar(
                titleRes = R.string.app_name,
                navigationIcon = MincyIcons.Search,
                navigationIconContentDescription = "点点滴滴的",
                actionIcon = MincyIcons.AccountCircle,
                actionIconContentDescription = "点点滴滴",
                onNavigationClick = {},
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                ),
                onActionClick = {},
                modifier = Modifier.windowInsetsPadding(
                    WindowInsets.safeDrawing.only(WindowInsetsSides.Top)
                )
            )
        },
        containerColor = Color.Transparent
    ) { padding->
        Box(
            modifier = modifier
                .background(MaterialTheme.colorScheme.surface)
                .fillMaxWidth()
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ){
            val uiState:MediaChooseUiState by chooseModel.uiState.collectAsStateWithLifecycle()
            if (uiState == MediaChooseUiState.Loading){
                Text(text = "数据正在加载中")
            }else if (uiState is MediaChooseUiState.Success){
                val mediaList = (uiState as MediaChooseUiState.Success).mediaList
                var removeState by remember {
                    mutableStateOf(CardState(false, 0))
                }
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    contentPadding = PaddingValues(16.dp),
                    modifier = modifier
                        .padding(padding)
                        .consumedWindowInsets(padding)
                        .fillMaxSize()
                        .testTag("MediaChoose")
                    ){
                    Log.i(TAG, " before update item ")
                    items(mediaList.size){
                        var state by remember {
                            mutableStateOf(CardState(false, 0))
                        }
                        if (removeState.selectedIndex > 0 && state.selectedIndex > removeState.selectedIndex){
                            state = CardState(true, state.selectedIndex-1)
                        }
                        Log.i(TAG, " state is changed")
                        val media = mediaList[it]
                        if (media is Video){
                            VideoCard(
                                modifier = Modifier
                                    .padding(4.dp)
                                    .aspectRatio(1.0f)
                                    .clip(RoundedCornerShape(10.dp)),
                                video = media,
                                onClick = {

                                }
                            )
                        }else{
                            val context = LocalContext.current
                            ImageCard(modifier = Modifier
                                .padding(4.dp)
                                .aspectRatio(1.0f)
                                .clip(RoundedCornerShape(10.dp)),
                                path = media.path,
                                cardState = state,
                                onClick = {
                                    if (chooseModel.curIndex >= MAX_SELECTED_PIC){
                                        return@ImageCard
                                    }
                                    state = if (state.selected){
                                        chooseModel.curIndex--
                                        removeState = state
                                        CardState(false, chooseModel.curIndex)
                                    }else{
                                        chooseModel.curIndex++
                                        CardState(true, chooseModel.curIndex)
                                    }
                                },
                                preViewOnClick = {
                                    context.startActivity(Intent(context, ImagePreviewActivity::class.java))
                                }
                            )
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun MincyTopAppBar(
    @StringRes titleRes: Int,
    navigationIcon: ImageVector,
    navigationIconContentDescription: String?,
    actionIcon: ImageVector,
    actionIconContentDescription: String?,
    modifier: Modifier = Modifier,
    colors: TopAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
    onNavigationClick: () -> Unit = {},
    onActionClick: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = { Text(text = stringResource(id = titleRes)) },
        navigationIcon = {
            IconButton(onClick = onNavigationClick) {
                Icon(
                    imageVector = navigationIcon,
                    contentDescription = navigationIconContentDescription,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        actions = {
            IconButton(onClick = onActionClick) {
                Icon(
                    imageVector = actionIcon,
                    contentDescription = actionIconContentDescription,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        colors = colors,
        modifier = modifier
    )
}