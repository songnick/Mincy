package com.songnick.mincy.feature.image_edit.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

/*****
 * @author songnick
 * @mail qfsong108@gmail.com
 * Create Time: 2022/10/9
 **/
@Composable
fun ImageEditTab(
    modifier: Modifier,
    onClick: @Composable ()->Unit,
    tabIcon:Int,
    tabText:String
){
    Box(modifier = modifier.clickable { onClick }){
        Column() {
            val childModifier = Modifier.align(Alignment.CenterHorizontally)
            Icon(painter = painterResource(id = tabIcon), contentDescription = "", modifier = childModifier)
            Spacer(modifier = Modifier
                .height(4.dp)
                .fillMaxWidth())
            Text(text = tabText,modifier = childModifier)
        }
    }
}