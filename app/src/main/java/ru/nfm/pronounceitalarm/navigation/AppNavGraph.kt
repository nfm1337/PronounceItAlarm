package ru.nfm.pronounceitalarm.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    alarmListScreenContent: @Composable () -> Unit,
    alarmItemScreenContent: @Composable (Int) -> Unit
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.AlarmList.route
    ) {
        composable(Screen.AlarmList.route) {
            alarmListScreenContent()
        }
        composable(
            route = Screen.AlarmItem.route,
            arguments = listOf(
                navArgument(Screen.KEY_ALARM) {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val alarmId = backStackEntry.arguments?.getInt(Screen.KEY_ALARM)
            alarmId?.let {
                alarmItemScreenContent(it)
            }
        }
    }
}