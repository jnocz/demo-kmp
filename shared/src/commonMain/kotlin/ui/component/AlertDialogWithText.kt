package ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import bazarsearchmultiplatform.shared.generated.resources.Res
import bazarsearchmultiplatform.shared.generated.resources.dialog_cancel
import bazarsearchmultiplatform.shared.generated.resources.dialog_ok
import org.jetbrains.compose.resources.stringResource

@Composable
fun AlertDialogWithTextField(
    initValue: String,
    onDismissRequest: () -> Unit,
    onConfirmation: (String) -> Unit,
    dialogTitle: String,
    iconTextField: ImageVector,
    maxChar: Int = 50,
    keyboardOptions: KeyboardOptions
) {

    val focusRequester = remember { FocusRequester() }

    var fieldText by rememberSaveable { mutableStateOf(initValue) }
    val locationHint = dialogTitle

    AlertDialog(icon = {
        //Icon(icon, contentDescription = "")
    }, title = {
        Text(text = dialogTitle)
    }, text = {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            enabled = true,
            keyboardOptions = keyboardOptions,
            leadingIcon = {
                Icon(
                    imageVector = iconTextField, contentDescription = ""
                )
            },
            value = fieldText,
            onValueChange = {
                if (it.length <= maxChar) {
                    fieldText = it
                }
            },
            label = { Text(locationHint) },
            singleLine = true
        )

        LaunchedEffect(focusRequester) {
            focusRequester.requestFocus()
        }
    }, onDismissRequest = {
        onDismissRequest()
    }, confirmButton = {
        TextButton(onClick = {
            onConfirmation(fieldText)
        }) {
            Text(stringResource(Res.string.dialog_ok))
        }
    }, dismissButton = {
        TextButton(onClick = {
            onDismissRequest()
        }) {
            Text(stringResource(Res.string.dialog_cancel))
        }
    }, containerColor = MaterialTheme.colorScheme.primaryContainer)
}