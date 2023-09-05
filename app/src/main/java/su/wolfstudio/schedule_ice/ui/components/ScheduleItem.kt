package su.wolfstudio.schedule_ice.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import su.wolfstudio.schedule_ice.R
import su.wolfstudio.schedule_ice.model.Athlete
import su.wolfstudio.schedule_ice.model.Schedule
import su.wolfstudio.schedule_ice.model.ScheduleType
import su.wolfstudio.schedule_ice.ui.athletes.add.choose.ChooseAthleteItem
import su.wolfstudio.schedule_ice.ui.athletes.add.choose.ChooseAthletesUi
import su.wolfstudio.schedule_ice.ui.list.ImageIce
import su.wolfstudio.schedule_ice.ui.theme.DimnessLightGray
import su.wolfstudio.schedule_ice.ui.theme.GreenBlueColor

@Composable
fun ScheduleItem(
    modifier: Modifier,
    schedule: Schedule,
    onUpdateTime: (scheduleId: Int, startTime: Int, endTime: Int) -> Unit,
    onRemoveSchedule: (scheduleId: Int) -> Unit,
    onUpdateSchedule: (schedule: Schedule) -> Unit
) {
    val componentTime = TimerPickerComponent(schedule)
    val hourStart by componentTime.hourStart.collectAsState()
    val minStart by componentTime.minStart.collectAsState()
    val hourEnd by componentTime.hourEnd.collectAsState()
    val minEnd by componentTime.minEnd.collectAsState()
    val showTimePicker = remember { mutableStateOf(false) }
    val showMoreInfo = remember { mutableStateOf(false) }

    TimerPickerDialog(
        showTimePicker = showTimePicker,
        component = componentTime
    ) { h, m ->
        val (timeStart, timeEnd) = componentTime.updateTime(h, m)
        onUpdateTime(schedule.id, timeStart, timeEnd)
    }

    CircleColumn(modifier = modifier) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Icon(
                modifier = Modifier
                    .padding(start = 6.dp, top = 6.dp, bottom = 6.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .clickable { showMoreInfo.value = !showMoreInfo.value }
                    .padding(10.dp)
                    .align(Alignment.CenterVertically),
                imageVector = if(showMoreInfo.value) Icons.Filled.KeyboardArrowUp
                    else Icons.Filled.KeyboardArrowDown,
                contentDescription = null
            )
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
                    .clickable { onRemoveSchedule(schedule.id) }
                    .padding(10.dp)
                    .align(Alignment.CenterVertically),
                painter = painterResource(id = R.drawable.ic_delete),
                contentDescription = null,
                tint = Color.Red
            )
        }

        if (showMoreInfo.value) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                text = stringResource(id = R.string.training_details),
                style = TextStyle(fontWeight = FontWeight.Bold)
            )

            CheckingTraining(schedule.type) {
                onUpdateSchedule(schedule.copy(type = if (it) ScheduleType.ICE else ScheduleType.OFP))
            }
            CheckingAuxiliary(schedule.additional) {
                onUpdateSchedule(schedule.copy(additional = it))
            }

            ScheduleAthletes(schedule.athletes) {
                onUpdateSchedule(schedule.copy(athleteIds = it))
            }

            var text by rememberSaveable { mutableStateOf("") }
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
                    .background(Color.Transparent),
                value = text,
                onValueChange = {
                    text = it
                    onUpdateSchedule(schedule.copy(comment = it))
                                },
                label = { Text(stringResource(id = R.string.add_comment)) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        }
    }
}

@Composable
fun CheckingTraining(
    type: ScheduleType,
    onCheckTraining: (Boolean) -> Unit
){
    var switchCheckedTraining by remember { mutableStateOf(type == ScheduleType.ICE) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20))
            .clickable {
                switchCheckedTraining = !switchCheckedTraining
                onCheckTraining(switchCheckedTraining)
            }
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(end = 10.dp),
            text = stringResource(id = R.string.type)
        )
        Image(
            modifier = Modifier.size(48.dp),
            painter = painterResource(
                if (switchCheckedTraining) (R.drawable.ic_ice_skate)
                else (R.drawable.ic_running_shoe)
            ),
            contentDescription = null
        )
    }
}

@Composable
fun CheckingAuxiliary(
    additional: Boolean,
    onCheckAuxiliary: (Boolean) -> Unit
){
    var switchCheckedAuxiliary by remember { mutableStateOf(additional) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20))
            .clickable {
                switchCheckedAuxiliary = !switchCheckedAuxiliary
                onCheckAuxiliary(switchCheckedAuxiliary)
            }
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(end = 10.dp),
            text = stringResource(id = R.string.additional)
        )
        Checkbox(
            checked = switchCheckedAuxiliary,
            onCheckedChange = { switchCheckedAuxiliary = !switchCheckedAuxiliary }
        )
    }
}

@Composable
fun ScheduleAthletes(
    athletes: List<Athlete>,
    onUpdateAthlete: (athleteIds: List<Int>) -> Unit
) {
    var showAll by remember { mutableStateOf(false) }
    val sizeShow = if (showAll)
        athletes.size
    else
        if (athletes.size >= 3) 3
        else athletes.size

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                text = stringResource(id = R.string.athletes),
                style = TextStyle(fontWeight = FontWeight.Bold)
            )

            if (athletes.size > 3)
                Text(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20))
                        .clickable { showAll = !showAll }
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    text = if (showAll) stringResource(id = R.string.hide)
                    else (stringResource(id = R.string.show) + " (${athletes.size})"),
                    style = TextStyle(color = GreenBlueColor)
                )
        }

        for (i in 0 until sizeShow) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 5.dp)
            ) {
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 10.dp),
                    text =  athletes[i].surname + " " +  athletes[i].name + " ",
                )
                ImageIce(
                    modifier = Modifier,
                    res = R.drawable.ic_close
                ) {
                    val newList = athletes.map { it.id }.toMutableList()
                    newList.remove(athletes[i].id)
                    onUpdateAthlete(newList)
                }
            }
        }

        ChooseAthletesUi(
            athletes.map { it.id }.toMutableList(),
            onUpdateAthlete
        )
    }
}


@Composable
fun CircleColumn(
    modifier: Modifier,
    background: Color = DimnessLightGray,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(10))
            .background(background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        content = content
    )
}