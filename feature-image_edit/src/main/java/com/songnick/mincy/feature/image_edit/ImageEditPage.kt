package com.songnick.mincy.feature.image_edit

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.songnick.mincy.core.data.model.Image
import com.songnick.mincy.core.design_system.MincyTheme
import com.songnick.mincy.core.design_system.component.MincyBackground
import com.songnick.mincy.feature.image_edit.compose.ImageEditTab

/*****
 * @author songnick
 * @mail qfsong108@gmail.com
 * Create Time: 2022/9/28
 **/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageEditPage(
    modifier: Modifier = Modifier,
    picture: Image
) {
    MincyTheme {
        MincyBackground {
            Scaffold(
                modifier = Modifier,
                contentColor = Color.Transparent,
                containerColor = MaterialTheme.colorScheme.background,
                topBar = {


                }

            ) { paddingValues ->
                Box(modifier = Modifier.padding(paddingValues)){


                }
            }
        }
    }
}


@Composable
fun ImageEditPanel(){
    Box(
        modifier = Modifier
            .height(86.dp)
            .fillMaxWidth()
    ){
        Row(modifier = Modifier.fillMaxSize()) {
            val tabModifier = Modifier.weight(1.0f)
//            ImageEditTab(modifier = tabModifier, onClick = { /*TODO*/ }, tabIcon = , tabText = )
        }
    }
}