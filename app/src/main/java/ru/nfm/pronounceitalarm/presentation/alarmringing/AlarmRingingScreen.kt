package ru.nfm.pronounceitalarm.presentation.alarmringing

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.nfm.pronounceitalarm.domain.entity.Alarm

@Composable
fun AlarmRingingScreen(alarm: Alarm) {
    Column {
        Text(text = alarm.name)
        Text(text = alarm.time)
    }
}

@Preview
@Composable
fun AlarmScreenPreview() {
    AlarmRingingScreen(Alarm())
}