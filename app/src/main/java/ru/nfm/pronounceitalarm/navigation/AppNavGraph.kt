package ru.nfm.pronounceitalarm.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.gson.Gson
import ru.nfm.pronounceitalarm.domain.Alarm

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    alarmListScreenContent: @Composable () -> Unit,
    alarmItemScreenContent: @Composable (Alarm) -> Unit
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
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val encodedAlarmJson = backStackEntry.arguments?.getString(Screen.KEY_ALARM)
            val alarm = Gson().fromJson(encodedAlarmJson, Alarm::class.java)
            alarmItemScreenContent(alarm)
        }
    }
}