package ru.nfm.pronounceitalarm.presentation.editalarm

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import ru.nfm.pronounceitalarm.domain.entity.Alarm
import ru.nfm.pronounceitalarm.domain.usecase.AddAlarmUseCase
import ru.nfm.pronounceitalarm.domain.usecase.GetAlarmByIdUseCase
import ru.nfm.pronounceitalarm.domain.usecase.UpdateAlarmUseCase
import javax.inject.Inject

@HiltViewModel
class AlarmItemViewModel @Inject constructor(
    private val addAlarmUseCase: AddAlarmUseCase,
    private val updateAlarmUseCase: UpdateAlarmUseCase,
    private val getAlarmByIdUseCase: GetAlarmByIdUseCase
) : ViewModel() {

    private val _screenState = mutableStateOf<AlarmItemScreenState>(AlarmItemScreenState.Initial)
    val screenState: State<AlarmItemScreenState>
        get() = _screenState

    private val _modifiedAlarm = mutableStateOf<Alarm?>(null)
    val modifiedAlarm: State<Alarm?> = _modifiedAlarm

    fun getAlarm(alarmId: Int) {
        viewModelScope.launch {
            getAlarmByIdUseCase(alarmId = alarmId)
                .firstOrNull()
                ?.let{
                _screenState.value = AlarmItemScreenState.EditAlarmItem(it)
                _modifiedAlarm.value = it
            } ?: run {
                _screenState.value = AlarmItemScreenState.CreateAlarmItem
                _modifiedAlarm.value = Alarm()
            }
        }
    }
}