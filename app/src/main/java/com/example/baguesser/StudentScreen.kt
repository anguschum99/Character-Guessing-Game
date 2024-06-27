@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.baguesser

import android.app.Activity
import android.app.AlertDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.compose.BAGuesserTheme
import com.example.compose.backgroundDark

@Composable
fun StudentApp(
    studentViewModel: StudentViewModel = viewModel()
) {
    val studentUiState by studentViewModel.uiState.collectAsState()


    Column(
        modifier = Modifier
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .safeDrawingPadding()
            .padding(8.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Blue Archive", style = typography.titleLarge)
        StudentLayout(
            userGuess = studentViewModel.userGuess,
            onUserGuessChanged = { studentViewModel.updateUserGuess(it) },
            score = studentUiState.score,
            current = studentUiState.studentName,
            picture = studentUiState.imageID,
            isWrong = studentUiState.guessedNameWrong,
            onKeyboardDone = { studentViewModel.checkUserGuess() }
        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(onClick = { studentViewModel.checkUserGuess() }) {
            Text(text = "Submit")
        }

    }
    if (studentUiState.gameDone) {
        FinalScoreDialog(score = studentUiState.score, onPlayAgain = { studentViewModel.reset() })
    }

}


@Composable
fun StudentLayout(
    userGuess: String,
    onUserGuessChanged: (String) -> Unit,
    score: Int,
    current: String,
    picture: Int,
    isWrong: Boolean,
    onKeyboardDone: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card {
        Column(
            modifier = Modifier
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string._5, score),
                modifier = Modifier
                    .clip(shapes.medium)
                    .background(colorScheme.surface)
                    .padding(horizontal = 10.dp, vertical = 2.dp)
                    .align(Alignment.End)
            )

            Image(
                painter = painterResource(picture),
                contentDescription = null,
                modifier = Modifier
                    .clip(shapes.small)
                    .padding(8.dp)
            )
            Text(
                text = "Who Is This Character",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(bottom = 8.dp)
            )

            Text(text = current)

            OutlinedTextField(
                value = userGuess,
                onValueChange = onUserGuessChanged,
                singleLine = true,
                shape = shapes.large,
                label = { Text(text = "Enter Your Answer") },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = colorScheme.surface,
                    unfocusedContainerColor = colorScheme.surface,
                    disabledContainerColor = colorScheme.surface,
                ),
                isError = isWrong,
                modifier = Modifier
                    .padding(bottom = 10.dp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { onKeyboardDone() }
                )
            )
        }


    }
}

@Composable
private fun FinalScoreDialog(
    score: Int,
    onPlayAgain: () -> Unit,
    modifier: Modifier = Modifier
) {
    val activity = (LocalContext.current as Activity)

    AlertDialog(
        onDismissRequest = { /*TODO*/ },
        title = { if (score > 3) {
            Text(text = stringResource(id = R.string.congratulations))
        }
                else {
            Text(text = "YOU ARE ASS")

        }
                },
        text = { Text(text = "you scored " + score) },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = { activity.finish() }) {
                Text(text = "Exit")
            }
        },
        confirmButton = {
            TextButton(onClick = { onPlayAgain() }) {
                Text(text = "Play Again")
            }
        }
    )


}


@Preview(showBackground = true)
@Composable
fun StudentScreenPreview() {
    BAGuesserTheme {
        StudentApp()

    }
}