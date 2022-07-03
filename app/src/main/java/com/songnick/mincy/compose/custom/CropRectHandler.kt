package com.songnick.mincy.compose.custom

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/*****
 * @author qfsong
 * Create Time: 2022/5/11
 **/
class CropRectHandler {

    companion object{
        const val TAG = "CropRectHandler"
    }
    @Composable
    fun HelloScreen() {
        var name by rememberSaveable {
            mutableStateOf("")
        }
        HelloContent(name = name, onNameChange = { name = it })
    }

    @Composable
    fun HelloContent(name: String, onNameChange: (String) -> Unit) {
        Column(modifier = Modifier.padding(16.dp)) {
            Log.i(TAG, "HelloContent invoked")
            Text(
                text = "Hello, $name",
                modifier = Modifier.padding(bottom = 8.dp),
                style = MaterialTheme.typography.h5
            )
            OutlinedTextField(
                value = name,
                onValueChange = onNameChange,
                label = { Text("Name") }
            )
        }
    }

    @Preview
    @Composable
    fun TestD(){
        MaterialTheme() {
            HelloScreen()
        }
    }

}