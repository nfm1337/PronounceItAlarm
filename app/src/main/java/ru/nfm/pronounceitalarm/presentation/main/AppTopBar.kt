package ru.nfm.pronounceitalarm.presentation.main

import androidx.compose.foundation.Image
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import ru.nfm.pronounceitalarm.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    screenName: String
) {
    TopAppBar(
        title = { Text(text = screenName) },
        actions = {
            IconButton(
                content = {
                    Image(
                        painterResource(id = R.drawable.ic_more_button),
                        contentDescription = null
                    )
                },
                onClick = {}
            )
        }
    )
}