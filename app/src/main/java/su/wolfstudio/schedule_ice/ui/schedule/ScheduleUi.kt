package su.wolfstudio.schedule_ice.ui.schedule

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import su.wolfstudio.schedule_ice.R
import su.wolfstudio.schedule_ice.model.DateWithSchedulePalace
import su.wolfstudio.schedule_ice.model.Schedule
import su.wolfstudio.schedule_ice.ui.components.ScheduleItem
import su.wolfstudio.schedule_ice.ui.theme.DimnessLightGray
import su.wolfstudio.schedule_ice.ui.theme.SkateColor
import su.wolfstudio.schedule_ice.ui.theme.SkateColor40
import su.wolfstudio.schedule_ice.ui.view.add_schedule.Date
import su.wolfstudio.schedule_ice.ui.view.add_schedule.Date.getCopyFormatSchedule
import su.wolfstudio.schedule_ice.ui.view.add_schedule.Date.getDateWithDayOfWeek

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ScheduleUi(component: ScheduleComponent, onBackCallback: () -> Unit) {
    val palacesSchedules by component.palacesSchedules.collectAsState()
    val clipboardManager: ClipboardManager = LocalClipboardManager.current

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        content = {
            stickyHeader {
                TopAppBar(
                    title = {
                        Text(text = stringResource(id = R.string.schedule))
                    },
                    colors = TopAppBarDefaults.topAppBarColors(SkateColor),
                    navigationIcon = {
                        IconButton(onClick = onBackCallback) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = null
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            clipboardManager.setText(AnnotatedString(getCopyFormatSchedule(palacesSchedules)))
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_copy),
                                contentDescription = null
                            )
                        }
                    }
                )
            }
            item { ChooseDatePeriod(component) }
            items(
                items = palacesSchedules,
                key = { it.date.toEpochDay() },
                itemContent = { DateOfWeek(modifier = Modifier.animateItemPlacement(), item = it, component)}
            )
        }
    )
}

@Composable
fun ChooseDatePeriod(component: ScheduleComponent) {
    val currentWeek by component.currentWeek.collectAsState()
    val isNextClick = remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .size(28.dp)
                .clickable(
                    onClick = {
                        component.onPreviousWeek()
                        isNextClick.value = false
                    },
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = false)
                ),
            imageVector = Icons.Filled.KeyboardArrowLeft,
            contentDescription = null
        )

        AnimatedContent(
            targetState = Date.getRangeDate(currentWeek),
            label = "AnimatedContent",
            transitionSpec = {
                if (isNextClick.value) {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Start,
                        animationSpec = tween(durationMillis = 500)
                    ) togetherWith
                            slideOutOfContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Start,
                                animationSpec = tween(durationMillis = 500)
                            )
                } else {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.End,
                        animationSpec = tween(durationMillis = 500)
                    ) togetherWith
                            slideOutOfContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.End,
                                animationSpec = tween(durationMillis = 500)
                            )
                }
            }
        ) { targetText ->
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = targetText,
                style = TextStyle(
                    fontSize = 22.sp
                )
            )
        }

        Image(
            modifier = Modifier
                .size(28.dp)
                .clickable(
                    onClick = {
                        component.onNextWeek()
                        isNextClick.value = true
                    },
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = false)
                ),
            imageVector = Icons.Filled.KeyboardArrowRight,
            contentDescription = null
        )
    }
}

@Composable
fun DateOfWeek(
    modifier: Modifier,
    item: DateWithSchedulePalace,
    component: ScheduleComponent
) {
    val isShowSchedule = remember { mutableStateOf(false) }
    Column(modifier = modifier.fillMaxWidth()) {
        Divider(
            modifier = Modifier
                .padding(horizontal = 16.dp),
            color = SkateColor40,
            thickness = 1.dp
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = getDateWithDayOfWeek(item.date),
                style = TextStyle(
                    fontSize = 18.sp
                )
            )

            Text(
                modifier = Modifier
                    .padding(end = 10.dp)
                    .drawBehind {
                        drawCircle(
                            color = SkateColor40,
                        )
                    }
                    .padding(10.dp),
                text = item.schedules.size.toString()
            )

            Image(
                modifier = Modifier
                    .clickable(
                        onClick = { isShowSchedule.value = !isShowSchedule.value },
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(bounded = false)
                    ),
                imageVector = if (isShowSchedule.value) Icons.Filled.KeyboardArrowUp
                else Icons.Filled.KeyboardArrowDown,
                contentDescription = null
            )
        }

        AnimatedVisibility(visible = isShowSchedule.value) {
            Column(modifier = modifier.fillMaxWidth()) {
                item.palaces
                    .map { palace ->
                        PalaceSchedule(
                            name = palace.name,
                            schedules = item.schedules.filter { it.palaceId == palace.id },
                            onUpdateTime = component::onUpdateTime,
                            onRemoveSchedule = component::onRemoveSchedule
                        )
                    }
            }
        }
    }
}

@Composable
fun PalaceSchedule(
    name: String,
    schedules: List<Schedule>,
    onUpdateTime: (scheduleId: Int, startTime: Int, endTime: Int) -> Unit,
    onRemoveSchedule: (scheduleId: Int) -> Unit
) {
    Column(
        modifier = Modifier.background(DimnessLightGray)
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 10.dp),
            text = name,
            style = TextStyle(
                fontSize = 18.sp
            )
        )
        schedules.map {
            ScheduleItem(
                modifier = Modifier,
                schedule = it,
                onUpdateTime = onUpdateTime,
                onRemoveSchedule = onRemoveSchedule
            )
        }
    }
}