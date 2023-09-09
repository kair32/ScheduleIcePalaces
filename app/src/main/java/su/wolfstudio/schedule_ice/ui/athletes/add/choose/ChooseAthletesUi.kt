package su.wolfstudio.schedule_ice.ui.athletes.add.choose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import su.wolfstudio.schedule_ice.R
import su.wolfstudio.schedule_ice.model.Athlete
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import su.wolfstudio.schedule_ice.ui.theme.SkateColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseAthletesUi(
    athletesChooseIds: MutableList<Int>,
    onUpdateAthlete: (athleteIds: List<Int>) -> Unit,
    viewModel: ChooseAthletesViewModel = viewModel(ChooseAthletesViewModelImpl::class.java)
) {
    val athletes by viewModel.athletes.collectAsState()
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    Text(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20))
            .clickable {
                scope.launch { sheetState.show() }
            }
            .padding(horizontal = 16.dp, vertical = 10.dp),
        text = stringResource(id = R.string.add_athlete),
        textAlign = TextAlign.Center
    )

    if (sheetState.isVisible) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {
                scope.launch { sheetState.hide() }
            },
        ) {
            Row(horizontalArrangement = Arrangement.SpaceAround) {
                CenterAlignedTopAppBar(
                    title = {
                        Text(text = stringResource(id = R.string.choose_athlete))
                    }
                )
            }
            athletes.map { athlete ->
                ChooseAthleteItem(
                    athlete = athlete,
                    isChooseDef = athletesChooseIds.contains(athlete.id),
                    onUpdateAthlete = {
                        if (!athletesChooseIds.remove(athlete.id))
                            athletesChooseIds.add(athlete.id)
                        onUpdateAthlete(athletesChooseIds)
                    }
                )
            }
        }
    }
}

@Composable
fun ChooseAthleteItem(
    athlete: Athlete,
    isChooseDef: Boolean,
    onUpdateAthlete: () -> Unit
) {
    val checkedState = remember { mutableStateOf(isChooseDef) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                checkedState.value = !checkedState.value
                onUpdateAthlete()
            }
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checkedState.value,
            onCheckedChange = {
                checkedState.value = !checkedState.value
                onUpdateAthlete()
                              },
            colors = CheckboxDefaults.colors(
                checkedColor = SkateColor
            ))
        Text(
            modifier = Modifier.padding(start = 10.dp),
            text = athlete.surname + " " + athlete.name + " " + athlete.middleName,
        )
    }
}