package su.wolfstudio.schedule_ice.ui.view.add_schedule

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import su.wolfstudio.schedule_ice.R
import su.wolfstudio.schedule_ice.ui.base.AddScheduleViewModelFactory
import su.wolfstudio.schedule_ice.ui.components.ScheduleItem
import su.wolfstudio.schedule_ice.ui.components.dashedBorder
import su.wolfstudio.schedule_ice.ui.theme.GreenBlueColor

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun AddScheduleUi(
    modifier: Modifier,
    navigation: NavController,
    palaceId: Long,
    viewModel: AddScheduleViewModel = viewModel(factory = AddScheduleViewModelFactory(palaceId))
) {
    val palace by viewModel.palace.collectAsState()
    val schedules by viewModel.schedules.collectAsState()
    val currentDate by viewModel.currentDate.collectAsState()

    Column(
        modifier = modifier
    ) {
        TopAppBar(
            title = {
                Text(text = stringResource(id = R.string.schedule) + " " + palace.name)
            },
            colors = TopAppBarDefaults.topAppBarColors(SkateColor),
            navigationIcon = {
                IconButton(onClick = { navigation.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            },
        )
        DateItemsUi(viewModel)

        LazyColumn(
            modifier = Modifier.weight(1f),
            content = {
                items(
                    items = schedules.filter { it.date == currentDate },
                    key = { it.id }
                ) {
                    ScheduleItem(
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .animateItemPlacement(),
                        schedule = it,
                        onUpdateTime = viewModel::onUpdateTime,
                        onRemoveSchedule = viewModel::onRemoveSchedule,
                        onUpdateSchedule = viewModel::onUpdateSchedule
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
                            .clickable { viewModel.onAddSchedule() }
                            .padding(10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = stringResource(id = R.string.add_more))
                    }
                }
            })
    }
}