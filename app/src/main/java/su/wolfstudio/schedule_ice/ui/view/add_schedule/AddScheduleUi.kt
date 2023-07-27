package su.wolfstudio.schedule_ice.ui.view.add_schedule

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import su.wolfstudio.schedule_ice.ui.theme.SkateColor
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import su.wolfstudio.schedule_ice.R
import su.wolfstudio.schedule_ice.ui.components.ScheduleItem
import su.wolfstudio.schedule_ice.ui.components.dashedBorder
import su.wolfstudio.schedule_ice.ui.theme.DimnessLightGray
import su.wolfstudio.schedule_ice.ui.theme.GreenBlueColor

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun AddScheduleUi(component: AddScheduleComponent, onBackCallback: () -> Unit) {
    val palace by component.palace.collectAsState()
    val schedules by component.schedules.collectAsState()
    val currentDate by component.currentDate.collectAsState()

    Column {
        TopAppBar(
            title = {
                Text(text = palace.name)
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
        )
        DateItemsUi(component)

        LazyColumn(
            modifier = Modifier.weight(1f),
            content = {
                items(
                    items = schedules.filter { it.date == currentDate },
                    key = { it.id }
                ) {
                    ScheduleItem(
                        modifier = Modifier.animateItemPlacement(),
                        schedule = it,
                        component = component
                    )
                }
                item {
                    Box(
                        modifier = Modifier
                            .animateItemPlacement()
                            .fillMaxWidth()
                            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                            .clip(RoundedCornerShape(45))
                            .dashedBorder(2.dp, GreenBlueColor, 45.dp)
                            .clickable { component.onAddSchedule() }
                            .padding(10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = stringResource(id = R.string.add_more))
                    }
                }
            })

        Row(
            modifier = Modifier
                .padding(all = 16.dp)
        ) {
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(6.dp)
                    .clip(CircleShape)
                    .background(DimnessLightGray)
                    .clickable { onBackCallback() }
                    .padding(10.dp),
                text = stringResource(id = R.string.back),
                textAlign = TextAlign.Center
            )

            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 6.dp, top = 6.dp, bottom = 6.dp)
                    .clip(CircleShape)
                    .background(SkateColor)
                    .clickable { component.onSave() }
                    .padding(10.dp),
                text = stringResource(id = R.string.save),
                textAlign = TextAlign.Center
            )
        }
    }
}