package com.harol.newsfeed.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.harol.newsfeed.ui.screens.main.MainViewModel

/**Todo 1: create SearchBar composable with a Card and TextField, customizing
 * its keyboard to show the Search icon as the action button
 * Create  @param [query] to keep track of hte query word and get the value
 * from the TextField
 * Todo 10 create newsManager variable
 */
@Composable
fun SearchView(query: MutableState<String>, viewModel: MainViewModel) {
    val localFocusManager = LocalFocusManager.current
    Card(
        elevation = 6.dp, shape = RoundedCornerShape(4.dp), modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        backgroundColor = MaterialTheme.colors.primary
    ) {
        TextField(value = query.value, onValueChange = {
            query.value = it
        }, modifier = Modifier
            .fillMaxWidth(),
            label = {
                Text(text = "Search", color = White)
            }, keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search,
            ),
            leadingIcon = {
                Icon(imageVector = Icons.Filled.Search, "", tint = White)
            },
            trailingIcon = {
                if (query.value != "") {
                    IconButton(
                        onClick = {
                            query.value =
                                ""
                        }
                    ) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "",
                            tint = White
                        )
                    }
                }
            },
            textStyle = TextStyle(color = White, fontSize = 18.sp),
            keyboardActions = KeyboardActions(
                onSearch = {
                    if (query.value != "") {
                        //Todo 11: call getSearchArticles when search action is clicked
                        viewModel.getSearchArticles(query.value)
                    }
                    localFocusManager.clearFocus()
                }
            ),
            colors = TextFieldDefaults.textFieldColors(textColor = White)
        )
    }
}


//Todo 2: create a preview function for the SearchBar
@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun SearchBarPreview() {
    //Todo 12:pass in NewsManager for preview
    SearchView(query = mutableStateOf(""), viewModel())
}