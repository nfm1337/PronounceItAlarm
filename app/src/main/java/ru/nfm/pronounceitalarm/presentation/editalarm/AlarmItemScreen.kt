package ru.nfm.pronounceitalarm.presentation.editalarm

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ru.nfm.pronounceitalarm.R
import ru.nfm.pronounceitalarm.domain.entity.Alarm
import ru.nfm.pronounceitalarm.navigation.Screen

@Composable
fun AlarmItemScreen(
    navHostController: NavHostController,
    viewModel: AlarmItemViewModel
) {
    val screenState by viewModel.screenState
    val modifiedAlarm by viewModel.modifiedAlarm

    val navBackStackEntry = navHostController.currentBackStackEntry
    val alarmId = navBackStackEntry?.arguments?.getInt(Screen.KEY_ALARM) ?: 0

    // Call the getAlarm method to fetch the alarm
    LaunchedEffect(key1 = alarmId) {
        viewModel.getAlarm(alarmId = alarmId)
    }

    when (screenState) {
        is AlarmItemScreenState.Initial -> {

        }

        is AlarmItemScreenState.EditAlarmItem -> {
            EditScreen(
                alarm = (screenState as AlarmItemScreenState.EditAlarmItem).alarm,
                onDismissButtonClickListener = { /*TODO*/ },
                onAcceptButtonClickListener = {  }
            )
        }

        is AlarmItemScreenState.CreateAlarmItem -> {
            EditScreen(
                alarm = modifiedAlarm ?: Alarm(),
                onDismissButtonClickListener = { /*TODO*/ }) {
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditScreen(
    alarm: Alarm,
    onDismissButtonClickListener: () -> Unit,
    onAcceptButtonClickListener: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (alarm.id == 0) {
                            stringResource(id = R.string.create_alarm_title)
                        } else stringResource(
                            id = R.string.edit_alarm_title
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onDismissButtonClickListener() }) {
                        Icon(
                            painterResource(
                                id = R.drawable.ic_back_button
                            ),
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { onAcceptButtonClickListener() }) {
                        Icon(
                            painterResource(id = R.drawable.ic_confirm_button),
                            contentDescription = "Confirm"
                        )
                    }
                }
            )
        },
        content = {
            it.calculateTopPadding()
            EditableTimePicker()
        }
    )
}

@Composable
fun EditableTimePicker() {
    var hours by remember { mutableStateOf(0) }
    var minutes by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = "Edit Time Picker", fontSize = 20.sp)

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            NumberPicker(value = hours, onValueChange = { newValue ->
                hours = newValue
            }, label = "Hours")

            Spacer(modifier = Modifier.width(16.dp))

            NumberPicker(value = minutes, onValueChange = { newValue ->
                minutes = newValue
            }, label = "Minutes")
        }

        Text(
            text = "Selected Time: $hours:$minutes",
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NumberPicker(
    value: Int,
    onValueChange: (Int) -> Unit,
    label: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = label, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = value.toString(),
            onValueChange = { newValue ->
                val newIntValue = newValue.toIntOrNull() ?: value
                onValueChange(newIntValue)
            },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.width(60.dp),
        )
    }
}