package com.assessment.teladochealth.presentation.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction.Companion.Done
import androidx.compose.ui.unit.dp
import com.assessment.teladochealth.R.string.input
import com.assessment.teladochealth.R.string.search_here
import com.assessment.teladochealth.presentation.compose.theme.DarkViolet
import com.assessment.teladochealth.presentation.compose.theme.LightBlue
import com.assessment.teladochealth.presentation.compose.theme.White

@Composable
@OptIn(ExperimentalComposeUiApi::class)
fun SearchableToolbar(
    onQueryUpdated: (String) -> Unit,
    query: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(96.dp)
            .clip(RoundedCornerShape(bottomEnd = 24.dp, bottomStart = 24.dp))
            .background(LightBlue)
            .padding(top = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        val keyboardController = LocalSoftwareKeyboardController.current
        TextField(
            value = query,
            onValueChange = {
                onQueryUpdated.invoke(it)
            },

            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },

            keyboardOptions = KeyboardOptions(imeAction = Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            ),
            textStyle = MaterialTheme.typography.body1,
            placeholder = {
                Text(
                    text = stringResource(id = search_here)
                )
            },
            singleLine = true,
            maxLines = 1,
            shape = RoundedCornerShape(32.dp),
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(54.dp)
                .testTag(stringResource(input)),
            colors = TextFieldDefaults.textFieldColors(
                textColor = DarkViolet,
                backgroundColor = White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
            ),
        )
    }
}


