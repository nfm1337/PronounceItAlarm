package ru.nfm.pronounceitalarm.presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.SpaceEvenly
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
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.nfm.pronounceitalarm.R
import ru.nfm.pronounceitalarm.domain.Alarm
import ru.nfm.pronounceitalarm.navigation.Screen
import ru.nfm.pronounceitalarm.navigation.rememberNavigationState
import ru.nfm.pronounceitalarm.ui.theme.ButtonColorYellow
import ru.nfm.pronounceitalarm.ui.theme.NotActiveSwitchDarkBlue
import ru.nfm.pronounceitalarm.ui.theme.OnPrimaryDarkBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val viewModel: MainViewModel = viewModel()
    val mainScreenState by viewModel.screenState.collectAsState()
    val navigationState = rememberNavigationState()

    when (mainScreenState) {
        is MainScreenState.Initial -> {
            viewModel.populateDb()
        }

        is MainScreenState.Alarms -> {
            val alarms = (mainScreenState as MainScreenState.Alarms).alarms
            Scaffold(
                modifier = Modifier.statusBarsPadding(),
                topBar = {
                    AppTopBar(screenName = stringResource(id = R.string.alarm_title))
                },
                content = {
                    it.calculateTopPadding()
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        items(alarms) { alarmItem ->
                            AlarmCard(alarmItem)
                        }
                    }
                },
                floatingActionButton = {
                    FloatingActionButton(onClick = {
                        navigationState.navHostController.navigate(Screen.AlarmItem.route)
                    }
                    ) {
                        Icon(painter = painterResource(
                            id = R.drawable.baseline_add_24),
                            contentDescription = null
                        )
                    }
                }
            )
        }
    }


}

@Composable
private fun AlarmCard(alarm: Alarm) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(size = 20.dp),
        colors = CardDefaults.cardColors(
            containerColor = OnPrimaryDarkBlue
        )
    ) {
        var isChecked by remember { mutableStateOf(alarm.isEnabled) }

        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = alarm.name,
                fontSize = 14.sp,
                color = if (isChecked) Color.White else Color.Gray,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = alarm.time,
                fontSize = 36.sp,
                fontWeight = FontWeight(400),
                color = if (isChecked) Color.White else Color.Gray
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                alarm.activeDays.forEach { dayOfWeekItem ->
                    Column(
                        horizontalAlignment = CenterHorizontally,
                        verticalArrangement = SpaceEvenly
                    ) {
                        if (isChecked) {
                            Dot()
                        } else {
                            Dot(alpha = 0f)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = dayOfWeekItem.key.name,
                            fontSize = 12.sp,
                            color = if (isChecked) {
                                ButtonColorYellow
                            } else {
                                Color(0xFFCFCFCF)
                            },
                        )
                    }

                }
            }
            Switch(
                checked = alarm.isEnabled,
                onCheckedChange = { isChecked = it },
                colors = SwitchDefaults.colors(
                    checkedTrackColor = ButtonColorYellow,
                    checkedThumbColor = Color.White,
                    uncheckedThumbColor = Color.White,
                    uncheckedTrackColor = NotActiveSwitchDarkBlue
                ),
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(PaddingValues(top = 12.dp, bottom = 12.dp, end = 8.dp))
            )
        }
    }
}

@Composable
fun Dot(
    alpha: Float = 1f
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


