package su.wolfstudio.schedule_ice.ui.athletes

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissState
import androidx.compose.material3.DismissValue
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.time.delay
import su.wolfstudio.schedule_ice.R
import su.wolfstudio.schedule_ice.model.Athlete
import su.wolfstudio.schedule_ice.ui.theme.ColorBackground
import su.wolfstudio.schedule_ice.ui.theme.SkateColor40
import su.wolfstudio.schedule_ice.ui.view.add_schedule.Date
import java.time.Duration

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AthleteItem(
    athlete: Athlete,
    onEdit: (id: Int) -> Unit,
    onRemove: (id: Int) -> Unit
) {
    var show by remember { mutableStateOf(true) }
    val dismissState = rememberDismissState(
        confirmValueChange = {
            if (it == DismissValue.DismissedToStart) {
                show = false
                true
            } else false
        }, positionalThreshold = { 150.dp.toPx() }
    )
    AnimatedVisibility(
        show, exit = fadeOut(spring())
    ) {

        SwipeToDismiss(
            state = dismissState,
            modifier = Modifier,
            background = {
                DismissBackground(dismissState)
            },
            dismissContent = {
                AthleteContent(athlete = athlete, onEdit = onEdit)
            },
            directions = setOf(DismissDirection.EndToStart)
        )
    }

    LaunchedEffect(show) {
        if (!show) {
            delay(Duration.ofMillis(800))
            onRemove(athlete.id)
        }
    }
}

@Composable
fun AthleteContent(
    athlete: Athlete,
    onEdit: (id: Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10))
            .background(color = ColorBackground)
            .clickable { onEdit(athlete.id) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = athlete.surname + " " + athlete.name + " " + athlete.middleName
            )
            val daysLeft = Date.getLeftUntilBirthday(athlete.birthday)
            if (daysLeft != null && daysLeft <= 10 && daysLeft > 0){
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = daysLeft.toString()
                )
                Image(
                    modifier = Modifier,
                    painter = painterResource(id = R.drawable.ic_birthday_cake),
                    contentDescription = stringResource(id = R.string.date_birth)
                )
            }
        }

        Divider(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .align(Alignment.BottomCenter),
            color = SkateColor40,
            thickness = 1.dp
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DismissBackground(dismissState: DismissState) {
    val color = if (dismissState.dismissDirection == DismissDirection.EndToStart)
        Color(0xFFFF1744)
    else Color.Transparent
    val direction = dismissState.dismissDirection

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .padding(12.dp, 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier)
        if (direction == DismissDirection.EndToStart) Icon(
            painter = painterResource(R.drawable.ic_delete),
            contentDescription = stringResource(id = R.string.delete)
        )
    }
}