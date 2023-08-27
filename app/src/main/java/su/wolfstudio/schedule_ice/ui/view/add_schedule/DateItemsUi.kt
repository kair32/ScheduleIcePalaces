package su.wolfstudio.schedule_ice.ui.view.add_schedule

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import su.wolfstudio.schedule_ice.ui.theme.DimnessLightGray
import su.wolfstudio.schedule_ice.ui.theme.GreenBlueColor
import su.wolfstudio.schedule_ice.ui.theme.SkateColor
import su.wolfstudio.schedule_ice.ui.view.add_schedule.Date.getFullDisplayName
import su.wolfstudio.schedule_ice.ui.view.add_schedule.Date.getShortDisplayName
import java.time.LocalDate

@Composable
fun DateItemsUi(component: AddScheduleComponent) {
    val schedules by component.schedules.collectAsState()
    val currentDate by component.currentDate.collectAsState()
    val dateList = Date.getDate()
    var oldPositionScroll by remember { mutableIntStateOf(0) }
    var positionScroll by remember { mutableIntStateOf(0) }
    val listState = rememberLazyListState()

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .collect { i ->
                oldPositionScroll = positionScroll
                positionScroll = i
            }
    }

    AnimatedContent(
        targetState = dateList.getOrNull(positionScroll)?.month.getFullDisplayName(),
        label = "AnimatedContent",
        transitionSpec = {
            if (oldPositionScroll < positionScroll) {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Start,
                    animationSpec = tween(durationMillis = 500)
                )  togetherWith
                        slideOutOfContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.Start,
                            animationSpec = tween(durationMillis = 500)
                        )
            }
            else {
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, top = 16.dp, bottom = 16.dp),
            text = targetText,
            style = TextStyle(
                fontSize = 22.sp
            )
        )
    }

    LazyRow(
        modifier = Modifier,
        state = listState
    ) {
        items(
            items = dateList,
            key = {it}
        ){
            DateItem(
                modifier = Modifier,
                localDate = it,
                equalDate = currentDate == it,
                schedulesCount = schedules.count { schedule ->  schedule.date == it }
            ) { component.chooseCurrentDate(it) }
        }
    }
}

@Composable
fun DateItem(
    modifier: Modifier,
    localDate: LocalDate,
    equalDate: Boolean,
    schedulesCount: Int,
    chooseCurrentDate: () -> Unit
){
    Column(
        modifier = modifier
            .padding(start = 10.dp)
            .clip(RoundedCornerShape(20))
            .background(
                if (equalDate) SkateColor
                else DimnessLightGray
            )
            .clickable { chooseCurrentDate() }
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = localDate.dayOfMonth.toString(),
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            ),
            textAlign = TextAlign.Center
        )
        Text(
            text = localDate.dayOfWeek.getShortDisplayName(),
            textAlign = TextAlign.Center
        )
        Row{
            (0..schedulesCount.minus(1)).map{
                CircleView()
            }
        }
    }
}

@Composable
fun CircleView() {
    Box(
        modifier = Modifier
            .width(4.dp)
            .height(4.dp)
            .clip(CircleShape)
            .background(GreenBlueColor)
    )
}