package ru.nfm.pronounceitalarm.presentation.alarmlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.nfm.pronounceitalarm.domain.entity.Alarm
import ru.nfm.pronounceitalarm.domain.usecase.DeleteAlarmUseCase
import ru.nfm.pronounceitalarm.domain.usecase.GetAlarmsUseCase
import ru.nfm.pronounceitalarm.domain.usecase.PopulateTestDataUseCase
import ru.nfm.pronounceitalarm.domain.usecase.UpdateAlarmUseCase
import javax.inject.Inject

@HiltViewModel
class AlarmListViewModel @Inject constructor(
    private val populateTestDataUseCase: PopulateTestDataUseCase,
    private val deleteAlarmUseCase: DeleteAlarmUseCase,
    private val getAlarmsUseCase: GetAlarmsUseCase,
    private val updateAlarmUseCase: UpdateAlarmUseCase
) : ViewModel() {

    private val _screenState = MutableStateFlow<AlarmListScreenState>(AlarmListScreenState.Initial)
    val screenState: StateFlow<AlarmListScreenState> = _screenState.asStateFlow()

    init {
        viewModelScope.launch {
            getAlarmsUseCase().collect { alarms ->
                _screenState.emit(AlarmListScreenState.Alarms(alarms))
            }
        }
    }

    fun getAlarms() {
        viewModelScope.launch {
            getAlarmsUseCase().collect {
                _screenState.value = AlarmListScreenState.Alarms(alarms = it)
            }
        }
    }


    fun populateDb() {
        viewModelScope.launch {
            populateTestDataUseCase()
        }
    }

    fun changeEnabledState(alarm: Alarm) {
        viewModelScope.launch {
            updateAlarmUseCase(alarm = alarm.copy(isEnabled = !alarm.isEnabled))
        }
    }

    fun deleteAlarm(alarm: Alarm) {
        viewModelScope.launch {
            deleteAlarmUseCase(alarm)
        }
    }
}