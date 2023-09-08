package ru.nfm.pronounceitalarm.presentation.alarmlist

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ru.nfm.pronounceitalarm.R
import ru.nfm.pronounceitalarm.domain.entity.Alarm
import ru.nfm.pronounceitalarm.domain.entity.DayOfWeek
import ru.nfm.pronounceitalarm.navigation.Screen
import ru.nfm.pronounceitalarm.presentation.main.AppTopBar
import ru.nfm.pronounceitalarm.ui.theme.ButtonColorYellow
import ru.nfm.pronounceitalarm.ui.theme.NotActive
import ru.nfm.pronounceitalarm.ui.theme.NotActiveSwitchDarkBlue
import ru.nfm.pronounceitalarm.ui.theme.OnPrimaryDarkBlue

@Composable
fun AlarmListScreen(
    navHostController: NavHostController,
    viewModel: AlarmListViewModel
) {
    val mainScreenState = viewModel.screenState.collectAsState()

    AlarmListContent(
        screenState = mainScreenState,
        viewModel = viewModel,
        onAlarmCardClickListener = {
            navHostController.navigate(Screen.AlarmItem.getRouteWithArgs(it))
        },
        onFabClickListener = {
            navHostController.navigate(Screen.AlarmItem.getRouteWithArgs(0))
        }
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun AlarmListContent(
    screenState: State<AlarmListScreenState>,
    viewModel: AlarmListViewModel,
    onAlarmCardClickListener: (Int) -> Unit,
    onFabClickListener: () -> Unit
) {

    when (val currentState = screenState.value) {
        is AlarmListScreenState.Initial -> {
            viewModel.populateDb()
        }

        is AlarmListScreenState.Alarms -> {
            Log.d("AlarmListContent", currentState.alarms.toString())
            Scaffold(
                modifier = Modifier.statusBarsPadding(),
                topBar = {
                    AppTopBar(screenName = stringResource(id = R.string.alarm_title))
                },
                content = {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(200.dp),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                top = it.calculateTopPadding(),
                                start = 16.dp,
                                end = 16.dp,
                                bottom = 16.dp
                            )
                    ) {
                        items(currentState.alarms) { alarmItem ->
                            AlarmCard(
                                alarm = alarmItem,
                                viewModel = viewModel,
                                onAlarmCardClickListener = onAlarmCardClickListener
                            )
                        }
                    }
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { onFabClickListener() },
                        containerColor = ButtonColorYellow,
                        shape = CircleShape,
                    ) {
                        Icon(
                            painter = painterResource(
                                id = R.drawable.baseline_add_24
                            ),
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                },
                floatingActionButtonPosition = FabPosition.Center
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AlarmCard(
    alarm: Alarm,
    viewModel: AlarmListViewModel,
    onAlarmCardClickListener: (Int) -> Unit,
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(size = 20.dp),
        colors = CardDefaults.cardColors(
            containerColor = OnPrimaryDarkBlue
        ),
        onClick = {
            onAlarmCardClickListener(alarm.id)
        }
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            AlarmTitle(alarm, alarm.isEnabled)
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 8.dp,
                        end = 8.dp,
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = alarm.time,
                    fontSize = 36.sp,
                    fontWeight = FontWeight(500),
                    color = if (alarm.isEnabled) Color.White else Color.Gray
                )

                Switch(
                    checked = alarm.isEnabled,
                    onCheckedChange = { viewModel.changeEnabledState(alarm) },
                    colors = SwitchDefaults.colors(
                        checkedTrackColor = ButtonColorYellow,
                        checkedThumbColor = Color.White,
                        uncheckedThumbColor = Color.White,
                        uncheckedTrackColor = NotActiveSwitchDarkBlue
                    ),
                    modifier = Modifier
                        .padding(PaddingValues(end = 8.dp))
                        .weight(0.2f)
                )
            }


            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                val shortDayOfWeekNames = stringArrayResource(id = R.array.short_weekdays)

                alarm.activeDays.forEach { dayOfWeekItem ->
                    ActiveDays(
                        alarm.isEnabled,
                        shortDayOfWeekNames,
                        dayOfWeekItem,
                        Modifier.weight(1f)
                    )
                }
            }

        }
    }
}

@Composable
private fun ActiveDays(
    isChecked: Boolean,
    shortDayOfWeekNames: Array<String>,
    dayOfWeekItem: Map.Entry<DayOfWeek, Boolean>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        val alpha: Float = if (isChecked && dayOfWeekItem.value) {
            1f
        } else {
            0f
        }
        Dot(alpha = alpha)
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = shortDayOfWeekNames[dayOfWeekItem.key.ordinal],
            fontSize = 10.sp,
            color = if (isChecked && dayOfWeekItem.value) {
                ButtonColorYellow
            } else {
                NotActive
            }
        )
    }
}

@Composable
private fun AlarmTitle(alarm: Alarm, isChecked: Boolean) {
    Text(
        modifier = Modifier.padding(
            start = 8.dp,
            end = 8.dp
        ),
        text = alarm.name,
        fontSize = 16.sp,
        color = if (isChecked) Color.White else Color.Gray,
        textAlign = TextAlign.Center,
    )
}

@Composable
fun Dot(
    alpha: Float
) {
    Box(
        modifier = Modifier
            .size(3.dp)
            .alpha(alpha)
            .background(
                color = ButtonColorYellow,
                shape = CircleShape
            )
    )
}