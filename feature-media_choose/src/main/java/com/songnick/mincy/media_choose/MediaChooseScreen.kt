package com.songnick.mincy.media_choose

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.songnick.mincy.R
import com.songnick.mincy.base_ui.MincyIcons
import kotlinx.coroutines.launch
import kotlin.math.round

/*****
 * @author qfsong
 * Create Time: 2022/9/14
 **/
const val TAG = "ForMediaChooseRoute"
@OptIn(ExperimentalLifecycleComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ForMediaChooseRoute(modifier: Modifier = Modifier, chooseModel:MediaChooseVM = hiltViewModel()){
    Scaffold(
        topBar = {
            NiaTopAppBar(
                titleRes = R.string.app_name,
                navigationIcon = MincyIcons.Search,
                navigationIconContentDescription = "",
                actionIcon = MincyIcons.AccountCircle,
                actionIconContentDescription = "",
                onNavigationClick = {},
                onActionClick = {}
            )
        },
        containerColor = Color.Transparent
    ) { _->
        Box(
            modifier = modifier
                .background(MaterialTheme.colorScheme.surface)
                .fillMaxWidth()
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ){
            val uiState:MediaChooseUiState by chooseModel._uiState.collectAsStateWithLifecycle()
            if (uiState == MediaChooseUiState.Loading){
                Text(text = "数据正在加载中")
            }else if (uiState is MediaChooseUiState.Success){
                val mediaList = (uiState as MediaChooseUiState.Success).mediaList
                LazyVerticalGrid(
                    columns = GridCells.Fixed(4),
                    contentPadding = PaddingValues(10.dp)
                    ,content = {
                        items(mediaList.size){
                            Log.i(TAG, " current path: " + mediaList[it].path)
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(mediaList[it].path)
                                    .crossfade(true)
                                    .build(),
                                contentDescription ="",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .padding(4.dp)
                                    .aspectRatio(1.0f)
                                    .clip(RoundedCornerShape(10.dp))
                            )
                        }

                    })
            }
        }
    }
}

@Composable
fun NiaTopAppBar(
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