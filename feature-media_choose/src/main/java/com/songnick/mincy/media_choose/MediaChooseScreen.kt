package com.songnick.mincy.media_choose

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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.songnick.mincy.R
import com.songnick.mincy.base_ui.MincyIcons
import com.songnick.mincy.media_choose.component.ImageCard
import com.songnick.mincy.media_choose.component.ImageCardState

/*****
 * @author qfsong
 * Create Time: 2022/9/14
 **/
const val TAG = "ForMediaChooseRoute"
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
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    contentPadding = PaddingValues(16.dp),
                    modifier = modifier
                        .padding(padding)
                        .consumedWindowInsets(padding)
                        .fillMaxSize()
                        .testTag("MediaChoose")
                    ){
                    items(mediaList.size){
                        Log.i(TAG, " current path: " + mediaList[it].path)
                        var state by remember {
                            mutableStateOf(ImageCardState(false, 0))
                        }
                        val event:Boolean by chooseModel.selectIndexEvent.collectAsStateWithLifecycle()
                        if (event){
                            if (state.selected){
                                state = ImageCardState(true, state.selectedIndex-1)
                            }
                        }
                        ImageCard(modifier = Modifier
                            .padding(4.dp)
                            .aspectRatio(1.0f)
                            .clip(RoundedCornerShape(10.dp)),
                            path = mediaList[it].path,
                            imageCardState = state,
                            onClick = {
                                state = if (state.selected){
                                    chooseModel.selectIndexEvent.value = true
                                    ImageCardState(false, chooseModel.curIndex)
                                }else{
                                    chooseModel.curIndex++
                                    ImageCardState(true, chooseModel.curIndex)
                                }
                            }
                        )
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