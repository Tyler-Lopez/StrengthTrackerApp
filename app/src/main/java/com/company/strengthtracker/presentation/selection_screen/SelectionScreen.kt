package com.company.strengthtracker.presentation.selection_screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.company.strengthtracker.data.entities.exercise_data.main_categories.AllExercises

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)

@Composable
fun SelectionScreen(
    navController: NavController,
    viewModel: SelectionViewModel = hiltViewModel()

) {
//    val searchBarState by viewModel.searchBarState
//    val searchTextState by viewModel.searchTextState
//
//    Scaffold(
//        topBar = {
//            MainAppBar(
//                searchBarState = searchBarState,
//                searchTextState = searchTextState,
//                onTextChanged = {
//                    viewModel.updateSearchTextState(newValue = it)
//                },
//                onCloseClicked = {
//                    viewModel.updateSearchTextState(newValue = "")
//                    viewModel.updateSearchBarState(newValue = SearchBarState.CLOSED)
//                },
//                onSearchedClicked = {
//                    Log.d("Searched Text: ", it)
//                },
//                onSearchTriggered = {
//                    viewModel.updateSearchBarState(newValue = SearchBarState.OPENED)
//                }
//            )
//        }
//    ) {
//        Text(text = "TODO")
//    }
}

@Composable
fun ExerciseListView(
    exList:MutableList<AllExercises>,
    onExerciseSelected: (AllExercises) -> Unit
){
    LazyColumn{
        items(exList) { exItem ->
            Row(
                modifier = Modifier.fillMaxWidth(),

            ){
                Button(content = {Text(exItem.name)},
                    onClick = {
                        onExerciseSelected(exItem)
                    }
                )
            }
        }
    }
}
//
//@Composable
//fun MainAppBar(
//    searchBarState: SearchBarState,
//    searchTextState: String,
//    onTextChanged: (String) -> Unit,
//    onCloseClicked: () -> Unit,
//    onSearchedClicked: (String) -> Unit,
//    onSearchTriggered: () -> Unit,
//) {
//    when (searchBarState) {
//        SearchBarState.CLOSED -> {
//            DefaultAppBar(
//                onSearchClicked = onSearchTriggered
//            )
//        }
//        SearchBarState.OPENED -> {
//            SearchBar(
//                text = searchTextState,
//                onTextChanged = onTextChanged,
//                onCloseClicked = onCloseClicked,
//                onSearchedClicked = onSearchedClicked
//            )
//        }
//    }
//}
//
//
//@Composable
//fun DefaultAppBar(onSearchClicked: () -> Unit) {
//    TopAppBar(
//        title = {
//            Text(
//                text = "Home"
//            )
//        },
//        actions = {
//            IconButton(onClick = { onSearchClicked() }) {
//                Icon(
//                    imageVector = Icons.Filled.Search,
//                    contentDescription = "search-icon",
//                    tint = Color.Black
//                )
//            }
//        }
//    )
//}
//
//
//@Composable
//fun SearchBar(
//    text: String,
//    onTextChanged: (String) -> Unit,
//    onCloseClicked: () -> Unit,
//    onSearchedClicked: (String) -> Unit
//) {
//    Surface(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(56.dp),
//        elevation = AppBarDefaults.TopAppBarElevation,
//    ) {
//        TextField(
//            modifier = Modifier.fillMaxWidth(),
//            value = text,
//            onValueChange = {
//                onTextChanged(it)
//            },
//            placeholder = {
//                Text(
//                    modifier = Modifier.alpha(ContentAlpha.medium),
//                    text = "Search here...",
//                    color = Color.Black
//                )
//            },
//            textStyle = TextStyle(
//                fontSize = MaterialTheme.typography.subtitle1.fontSize
//            ),
//            singleLine = true,
//            leadingIcon = {
//                IconButton(
//                    modifier = Modifier.alpha(ContentAlpha.medium),
//                    onClick = { /*TODO*/ }) {
//                    Icon(
//                        imageVector = Icons.Default.Search,
//                        contentDescription = "search-icon",
//                        tint = Color.Black
//                    )
//
//                }
//            },
//            trailingIcon = {
//                IconButton(
//                    onClick = {
//                        if (text.isNotEmpty()) {
//                            onTextChanged("")
//                        } else {
//                            onCloseClicked()
//                        }
//                    }) {
//                    Icon(
//                        imageVector = Icons.Default.Search,
//                        contentDescription = "search-icon",
//                        tint = Color.Black
//                    )
//
//                }
//            },
//            keyboardOptions = KeyboardOptions(
//                imeAction = androidx.compose.ui.text.input.ImeAction.Search
//            ),
//            keyboardActions = KeyboardActions(
//                onSearch = {
//                    onSearchedClicked(text)
//                }
//            ),
//            colors = TextFieldDefaults.textFieldColors(
//                backgroundColor = Color.Transparent,
//            )
//        )
//    }
//}
//
//@Composable
//@Preview
//fun SearchAppBarPreview() {
//    SearchBar(
//        text = "bruh",
//        onTextChanged = {},
//        onCloseClicked = {},
//        onSearchedClicked = {}
//
//    )
//}
//
//
