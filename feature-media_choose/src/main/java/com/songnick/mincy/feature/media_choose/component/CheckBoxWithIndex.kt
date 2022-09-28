package com.songnick.mincy.feature.media_choose.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/*****
 * @author qfsong
 * Create Time: 2022/9/23
 **/
@Composable
fun CheckBoxWithIndex(
    index:Int,
    modifier: Modifier
) {
    Surface(modifier = modifier
        .background(Color.Transparent)
        .clip(RoundedCornerShape(4.dp))) {
        Box(modifier = Modifier
            .width(18.dp)
            .height(18.dp)
            .background(MaterialTheme.colorScheme.primary)
            .border(
                width = 2.dp,
                brush = Brush.horizontalGradient(0f to Color.White, 0f to Color.White),
                shape = RoundedCornerShape(
                    topStart = 4.dp,
                    topEnd = 4.dp,
                    bottomStart = 4.dp,
                    bottomEnd = 4.dp
                )
            )
        ){
            Text(
                text = index.toString(),
                modifier = Modifier.align(Alignment.Center),
                fontSize = 13.sp,
                color = Color.White
            )

        }
    }

}

@Preview
@Composable
fun PreviewCheckBox(){
    Surface(modifier = Modifier.background(Color.Red)) {
        CheckBoxWithIndex(index = 9, modifier = Modifier
            .width(18.dp)
            .height(18.dp))
    }
}