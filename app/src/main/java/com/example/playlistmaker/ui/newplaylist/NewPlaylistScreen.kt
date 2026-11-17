package com.example.playlistmaker.ui.newplaylist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.playlistmaker.R
import com.example.playlistmaker.ui.common.ScreenHeader
import com.example.playlistmaker.ui.theme.IconGray
import com.example.playlistmaker.ui.theme.LightGray
import com.example.playlistmaker.ui.theme.PlaylistBlue
import com.example.playlistmaker.ui.theme.TextPrimary
import com.example.playlistmaker.ui.theme.White

@Composable
fun NewPlaylistScreen(
    viewModel: NewPlaylistViewModel,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var name by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(PlaylistBlue),
    ) {
        ScreenHeader(
            title = stringResource(R.string.title_new_playlist),
            onBackClick = navigateBack,
        )

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = White,
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.Top,
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(
                            text = stringResource(R.string.new_playlist_name),
                            color = IconGray,
                            fontSize = 16.sp,
                        )
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(8.dp),
                    colors = transparentTextFieldColors(),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(
                            text = stringResource(R.string.new_playlist_description),
                            color = IconGray,
                            fontSize = 16.sp,
                        )
                    },
                    singleLine = false,
                    minLines = 3,
                    shape = RoundedCornerShape(8.dp),
                    colors = transparentTextFieldColors(),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        viewModel.createNewPlaylist(name = name, description = description)
                        navigateBack()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    enabled = name.isNotBlank(),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PlaylistBlue,
                        contentColor = White,
                        disabledContainerColor = LightGray,
                        disabledContentColor = IconGray,
                    ),
                ) {
                    Text(
                        text = stringResource(R.string.create_playlist),
                        fontSize = 16.sp,
                    )
                }
            }
        }
    }
}

@Composable
private fun transparentTextFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedTextColor = TextPrimary,
    unfocusedTextColor = TextPrimary,
    focusedContainerColor = LightGray,
    unfocusedContainerColor = LightGray,
    focusedBorderColor = PlaylistBlue,
    unfocusedBorderColor = LightGray,
    cursorColor = PlaylistBlue,
)
