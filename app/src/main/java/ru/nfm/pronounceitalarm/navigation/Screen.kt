package ru.nfm.pronounceitalarm.navigation

import android.net.Uri
import com.google.gson.Gson
import ru.nfm.pronounceitalarm.domain.Alarm

sealed class Screen(
    val route: String
) {
    object AlarmList : Screen(ROUTE_ALARM_LIST)
    object AlarmItem : Screen(ROUTE_ALARM) {

        private const val ROUTE_FOR_ARGS = "alarm"

        fun getRouteWithArgs(alarm: Alarm) : String {
            val alarmItemJson = Gson().toJson(alarm)
            return "$ROUTE_FOR_ARGS/${alarmItemJson.encode()}"
        }
    }

    companion object {
        const val KEY_ALARM = "alarm_item"

        const val ROUTE_ALARM_LIST = "alarm_list"
        const val ROUTE_ALARM = "alarm/{$KEY_ALARM}"
    }
}

fun String.encode(): String {
    return Uri.encode(this)
}
