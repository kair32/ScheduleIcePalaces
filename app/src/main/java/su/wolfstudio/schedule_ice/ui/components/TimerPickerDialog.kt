package su.wolfstudio.schedule_ice.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import su.wolfstudio.schedule_ice.R
import su.wolfstudio.schedule_ice.ui.theme.GreenBlueColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerPickerDialog(
    showTimePicker: MutableState<Boolean>,
    component: TimerPickerComponent,
    result: (Int, Int) -> Unit
) {
    val isStart by component.isStart.collectAsState()

    if (showTimePicker.value) {
        val state = rememberTimePickerState(
            initialHour = if (isStart) component.hourStart.value else component.hourEnd.value,
            initialMinute = if (isStart) component.minStart.value else component.minEnd.value
        )
        DatePickerDialog(
            onDismissRequest = { showTimePicker.value = false },
            confirmButton = { }) {
            TimePicker(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .align(CenterHorizontally),
                state = state
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20))
                        .clickable { showTimePicker.value = false }
                        .padding(16.dp),
                    text = stringResource(id = R.string.back),
                    style = TextStyle(
                        color = GreenBlueColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                )
                Text(
                    modifier = Modifier
                        .padding(end = 20.dp)
                        .clip(RoundedCornerShape(20))
                        .clickable {
                            result.invoke(state.hour, state.minute)
                            showTimePicker.value = false
                        }
                        .padding(16.dp),
                    text = stringResource(id = R.string.ok),
                    style = TextStyle(
                        color = GreenBlueColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                )
            }
        }
    }
}