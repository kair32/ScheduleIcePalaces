package su.wolfstudio.schedule_ice.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import su.wolfstudio.schedule_ice.R
import su.wolfstudio.schedule_ice.model.Schedule
import su.wolfstudio.schedule_ice.ui.theme.DimnessLightGray
import su.wolfstudio.schedule_ice.ui.view.add_schedule.AddScheduleComponent

@Composable
fun ScheduleItem(
    modifier: Modifier,
    schedule: Schedule,
    component: AddScheduleComponent
) {
    val componentTime = TimerPickerComponent(schedule)
    val hourStart by componentTime.hourStart.collectAsState()
    val minStart by componentTime.minStart.collectAsState()
    val hourEnd by componentTime.hourEnd.collectAsState()
    val minEnd by componentTime.minEnd.collectAsState()
    val showTimePicker = remember { mutableStateOf(false) }

    TimerPickerDialog(
        showTimePicker = showTimePicker,
        component = componentTime
    ) { h, m ->
        componentTime.updateTime(h,m)
        val timeStart = hourStart * 60 + minStart
        val timeEnd = hourEnd * 60 + minEnd
        component.onUpdateTime(schedule.id, timeStart, timeEnd)
    }

    CircleRow(modifier = modifier) {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(start = 6.dp, top = 6.dp, bottom = 6.dp)
                .clip(CircleShape)
                .background(Color.White)
                .clickable {
                    componentTime.updateStart(true)
                    showTimePicker.value = true
                }
                .padding(10.dp),
            text = Schedule.getTimeWithZero(hourStart, minStart),
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(6.dp)
                .clip(CircleShape)
                .background(Color.White)
                .clickable {
                    componentTime.updateStart(false)
                    showTimePicker.value = true
                }
                .padding(10.dp),
            text = Schedule.getTimeWithZero(hourEnd, minEnd),
            textAlign = TextAlign.Center
        )
        Icon(
            modifier = Modifier
                .padding(end = 6.dp, top = 6.dp, bottom = 6.dp)
                .clip(CircleShape)
                .background(Color.White)
                .clickable { component.onRemoveSchedule(scheduleId = schedule.id) }
                .padding(10.dp)
                .align(Alignment.CenterVertically),
            painter = painterResource(id = R.drawable.ic_delete),
            contentDescription = null,
            tint = Color.Red
        )
    }
}

@Composable
fun CircleRow(
    modifier: Modifier,
    background: Color = DimnessLightGray,
    content: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)
            .clip(CircleShape)
            .background(background),
        verticalAlignment = Alignment.CenterVertically,
        content = content
    )
}