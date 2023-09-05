package su.wolfstudio.schedule_ice.ui.athletes.add

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import su.wolfstudio.schedule_ice.R
import su.wolfstudio.schedule_ice.ui.ScheduleOutlinedTextField
import su.wolfstudio.schedule_ice.ui.base.AddAthleteViewModelFactory
import su.wolfstudio.schedule_ice.ui.theme.SkateColor
import su.wolfstudio.schedule_ice.ui.view.add_schedule.Date
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAthleteUi(
    modifier: Modifier,
    navigation: NavController,
    athleteId: Int,
    viewModel: AddAthleteViewModel = viewModel(factory = AddAthleteViewModelFactory(athleteId))
) {
    val athlete by viewModel.athlete.collectAsState()
    val isError by viewModel.isError.collectAsState()
    var showDatePicker by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier.fillMaxSize(),
        content = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(
                            id = if (athleteId == 0) R.string.edit_athlete
                            else R.string.add_athlete
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
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
                actions = {
                    IconButton(onClick = {
                        viewModel.onSave()
                        navigation.popBackStack()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_save),
                            contentDescription = stringResource(id = R.string.save)
                        )
                    }
                }
            )

            ScheduleOutlinedTextField(
                text = athlete.surname,
                labelText = R.string.surname,
                isError = isError && athlete.surname.isEmpty(),
                onValueChange = { viewModel.updateSurname(it) }
            )

            ScheduleOutlinedTextField(
                text = athlete.name,
                labelText = R.string.name,
                isError = isError && athlete.name.isEmpty(),
                onValueChange = { viewModel.updateName(it) }
            )

            ScheduleOutlinedTextField(
                text = athlete.middleName ?: "",
                labelText = R.string.middle_name,
                isError = false,
                onValueChange = { viewModel.updateMiddleName(it) }
            )

            ScheduleOutlinedTextField(
                text = Date.getDateWithYear(athlete.birthday),
                isError = isError && athlete.birthday == 0L,
                labelText = R.string.date_birth,
                onValueChange = {},
                readOnly = true,
            ) {
                showDatePicker = true
            }

            if (showDatePicker)
                DatePickerDialogAddAthlete(
                    initialSelectedDateMillis = athlete.birthday,
                    {
                        viewModel.updateBirthday(it)
                    }, {
                        focusManager.clearFocus()
                        showDatePicker = false
                    }
                )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialogAddAthlete(
    initialSelectedDateMillis: Long = 0L,
    onUpdateDate: (Long) -> Unit,
    onHideDatePicker: () -> Unit
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = if (initialSelectedDateMillis == 0L)
            null else initialSelectedDateMillis,
        yearRange = IntRange(1950, LocalDate.now().year.minus(1))
    )

    DatePickerDialog(
        onDismissRequest = {
            onHideDatePicker()
        },
        confirmButton = {
            TextButton(onClick = {
                onHideDatePicker()
                datePickerState.selectedDateMillis?.let {
                    onUpdateDate(it)
                }
            }) {
                Text(text = stringResource(id = R.string.confirm))
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onHideDatePicker()
            }) {
                Text(text = stringResource(id = R.string.cancel))
            }
        }
    ) {
        DatePicker(
            title = {
                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = stringResource(id = R.string.choose_date_birth)
                )
            },
            state = datePickerState
        )
    }
}