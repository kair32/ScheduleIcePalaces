package su.wolfstudio.schedule_ice.ui.athletes

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
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
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import su.wolfstudio.schedule_ice.R
import su.wolfstudio.schedule_ice.navigation.Screen
import su.wolfstudio.schedule_ice.ui.theme.SkateColor

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AthletesUi(
    modifier: Modifier,
    navigation: NavController,
    viewModel: AthletesViewModel = viewModel(AthletesViewModelImpl::class.java)
) {
    val athletes by viewModel.athletes.collectAsState()

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        content = {
            stickyHeader {
                TopAppBar(
                    title = {
                        Text(text = stringResource(id = R.string.athletes))
                    },
                    colors = TopAppBarDefaults.topAppBarColors(SkateColor),
                    actions = {
                        IconButton(
                            onClick = { navigation.navigate(Screen.AddAthlete.screenName + "/" + "0") }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Add,
                                contentDescription = stringResource(id = R.string.add_athlete)
                            )
                        }
                    }
                )
            }
            if (athletes.isEmpty())
                item {
                    EmptyAthletes(Modifier.fillParentMaxHeight()) {
                        navigation.navigate(Screen.AddAthlete.screenName + "/" + "0")
                    }
                }
            else {
                items(
                    items = athletes,
                    key = { it.id }
                ) {
                    AthleteItem(
                        athlete = it,
                        onEdit = { athleteId -> navigation.navigate(Screen.AddAthlete.screenName + "/" + athleteId) },
                        onRemove = { athleteId -> viewModel.onRemoveAthlete(athleteId) }
                    )
                }
            }
        }
    )
}