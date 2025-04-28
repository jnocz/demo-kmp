package ui.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import bazarsearchmultiplatform.shared.generated.resources.Res
import bazarsearchmultiplatform.shared.generated.resources.dialog_ok
import bazarsearchmultiplatform.shared.generated.resources.error_general_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun CustomAlertDialog(error: String, shouldShowDialog: MutableState<Boolean>) {
    if (shouldShowDialog.value) {
        AlertDialog(
            onDismissRequest = {
                shouldShowDialog.value = false
            },
            title = { Text(text = stringResource(Res.string.error_general_title)) },
            text = { Text(text = error) },
            confirmButton = {
                Button(
                    onClick = {
                        shouldShowDialog.value = false
                    }
                ) {
                    Text(
                        text = stringResource(Res.string.dialog_ok),
                        color = Color.White
                    )
                }
            }
        )
    }
}