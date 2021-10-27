package com.github.inorichi.marvel.presentation.characterlist.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import com.github.inorichi.marvel.presentation.R

@Composable
fun CharacterListToolbar(
  query: String?,
  onQueryChanged: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  var editMode by remember {
    mutableStateOf(false)
  }

  if (editMode) {
    CharacterListEditToolbar(
      query = query.orEmpty(),
      onCloseClick = {
        onQueryChanged("")
        editMode = false
      },
      onQueryChanged = {
        onQueryChanged(it)
        editMode = false
      },
      modifier = modifier
    )
  } else if (!query.isNullOrBlank()) {
    CharacterListQueryToolbar(
      query = query,
      onCloseClick = {
        onQueryChanged("")
        editMode = false
      },
      onSearchClick = { editMode = true },
      modifier = modifier
    )
  } else {
    CharacterListMainToolbar(
      onSearchClick = { editMode = true },
      modifier = modifier
    )
  }
}

@Composable
private fun CharacterListMainToolbar(
  onSearchClick: () -> Unit,
  modifier: Modifier
) {
  TopAppBar(
    modifier = modifier,
    title = { Text(stringResource(R.string.character_list_title)) },
    actions = {
      IconButton(onClick = onSearchClick) {
        Icon(Icons.Default.Search, contentDescription = stringResource(R.string.search))
      }
    }
  )
}

@Composable
private fun CharacterListQueryToolbar(
  query: String,
  onCloseClick: () -> Unit,
  onSearchClick: () -> Unit,
  modifier: Modifier
) {
  TopAppBar(
    modifier = modifier,
    title = { Text(query) },
    navigationIcon = {
      IconButton(onClick = onCloseClick) {
        Icon(Icons.Default.Close, contentDescription = stringResource(R.string.close))
      }
    },
    actions = {
      IconButton(onClick = onSearchClick) {
        Icon(Icons.Default.Search, contentDescription = stringResource(R.string.search))
      }
    }
  )
}

@Composable
private fun CharacterListEditToolbar(
  query: String,
  onCloseClick: () -> Unit,
  onQueryChanged: (String) -> Unit,
  modifier: Modifier
) {
  val focusRequester = remember { FocusRequester() }
  val focusManager = LocalFocusManager.current
  var textFieldValue by remember {
    mutableStateOf(TextFieldValue(text = query, selection = TextRange(query.length)))
  }

  TopAppBar(
    modifier = modifier,
    title = {
      BasicTextField(
        value = textFieldValue,
        onValueChange = { textFieldValue = it },
        modifier = Modifier.focusRequester(focusRequester),
        textStyle = LocalTextStyle.current.copy(color = LocalContentColor.current),
        cursorBrush = SolidColor(LocalContentColor.current),
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = {
          onQueryChanged(textFieldValue.text)
          focusManager.clearFocus()
        })
      )
    },
    navigationIcon = {
      IconButton(onClick = onCloseClick) {
        Icon(Icons.Default.Close, contentDescription = stringResource(R.string.close))
      }
    }
  )

  LaunchedEffect(focusRequester) {
    focusRequester.requestFocus()
  }

  BackHandler(onBack = onCloseClick)
}
