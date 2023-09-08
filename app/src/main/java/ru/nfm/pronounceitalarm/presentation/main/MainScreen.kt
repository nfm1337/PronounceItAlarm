package ru.nfm.pronounceitalarm.presentation.main

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.nfm.pronounceitalarm.navigation.AppNavGraph
import ru.nfm.pronounceitalarm.navigation.rememberNavigationState
import ru.nfm.pronounceitalarm.presentation.alarmlist.AlarmListScreen
import ru.nfm.pronounceitalarm.presentation.alarmlist.AlarmListViewModel
import ru.nfm.pronounceitalarm.presentation.editalarm.AlarmItemScreen
import ru.nfm.pronounceitalarm.presentation.editalarm.AlarmItemViewModel

@Composable
fun MainScreen() {

    val navigationState = rememberNavigationState()
    val alarmListViewModel: AlarmListViewModel = viewModel()
    val alarmItemViewModel: AlarmItemViewModel = viewModel()
    AppNavGraph(
        navHostController = navigationState.navHostController,
        alarmListScreenContent = {
            AlarmListScreen(
                navigationState.navHostController,
                alarmListViewModel
            )
        },
        alarmItemScreenContent = {
            AlarmItemScreen(
                navigationState.navHostController,
                alarmItemViewModel
            )
        }
    )
}




