package com.example.guildsmartconsulta.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp


@Composable
fun GuildSerchInputTextField(
//    icon: ImageVector,
//    alt: String? = UUID.randomUUID().toString(),
    searchQuery: MutableState<String>,
    onValueChange: ((String) -> Unit)? = { x -> searchQuery.value = x },
    placeholder: String,
    onEnterPressed: () -> Unit,
    onFocusState: () -> Unit,
//    onKeyEvent: (KeyEvent) -> Boolean ,
//    onClick: () -> Unit,
    maxLength: Int = 13,
) {
    var enterPressCount by remember { mutableStateOf(0) }
    val focusRequester = remember { FocusRequester() }
    var isFocused by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = searchQuery.value,
        onValueChange = { x ->
            if (x.length <= maxLength) {
                onValueChange?.invoke(x)
                searchQuery.value = x
            }
        },
        placeholder = {
            Text(text = placeholder)
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search, keyboardType = KeyboardType.Number
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                enterPressCount += 1
                if (enterPressCount == 2) {
                    onEnterPressed()
                    enterPressCount = 0
                }
            }
        ),
        maxLines = 1,


        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(bottom = 12.dp)
            .focusRequester(focusRequester),
//            .onFocusChanged { focusState ->
//                // Atualiza o estado com base no foco
//                isFocused = focusState.isFocused
//                onFocusState()
//
//            },

//            .onKeyEvent {event ->
//                onKeyEvent(event)
//            },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            cursorColor = Color.Transparent,
            textColor = Color.Transparent

        ),
//        shape = RoundedCornerShape(16.dp),
//        trailingIcon = {
//            Icon(
//                imageVector = icon,
//                contentDescription = alt,
//                tint = Color(0xFF000000),
//                modifier = Modifier
//                    .size(18.dp)
//                    .clickable {
//                        searchQuery.value = "" // Limpa a barra ao clicar no ícone
////                        onClick.invoke()
//                    }
//            )
//        }
    )
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}