package su.wolfstudio.schedule_ice.ui

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import su.wolfstudio.schedule_ice.cashe.StateValue
import su.wolfstudio.schedule_ice.cashe.stateValue
import su.wolfstudio.schedule_ice.ui.theme.DimnessLightGray
import su.wolfstudio.schedule_ice.ui.theme.GreenBlueColor

@Composable
fun ScheduleOutlinedTextField(
    text: String,
    labelText: Int,
    isError: Boolean,
    onValueChange: (String) -> Unit
) = ScheduleOutlinedTextField(
    text = text,
    labelText = labelText,
    onValueChange = onValueChange,
    readOnly = false,
    isError = isError
) {}

@Composable
fun ScheduleOutlinedTextField(
    text: String,
    labelText: Int,
    onValueChange: (String) -> Unit,
    readOnly: Boolean,
    isError: Boolean,
    clickable: () -> Unit
){
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 10.dp
            ),
        readOnly = readOnly,
        isError = isError,
        shape = RoundedCornerShape(20),
        label = { Text(text = stringResource(labelText)) },
        value = text,
        onValueChange = {
            onValueChange(it)
        },
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences,
            imeAction = ImeAction.Next
        ),
        textStyle = LocalTextStyle.current,
        maxLines = 1,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            cursorColor = GreenBlueColor,
            backgroundColor = DimnessLightGray,
            focusedBorderColor = DimnessLightGray,
            unfocusedBorderColor = DimnessLightGray,
            focusedLabelColor = GreenBlueColor,
            unfocusedLabelColor = Color.Gray,
            errorLabelColor = Color.Red
        ),
        interactionSource = remember { MutableInteractionSource() }
            .also { interactionSource ->
                LaunchedEffect(interactionSource) {
                    interactionSource.interactions.collect {
                        if (it is PressInteraction.Release) {
                            clickable()
                        }
                    }
                }
            }
    )
}

class ScheduleOutlinedField {
    val text: StateValue<String> by stateValue("")
    fun updateText(it: String) {
        text.compareAndSet(text.value, it)
    }
}