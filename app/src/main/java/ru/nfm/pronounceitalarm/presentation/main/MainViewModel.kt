package ru.nfm.pronounceitalarm.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.nfm.pronounceitalarm.domain.usecase.AddAlarmUseCase
import ru.nfm.pronounceitalarm.domain.usecase.DeleteAlarmUseCase
import ru.nfm.pronounceitalarm.domain.usecase.GetAlarmsUseCase
import ru.nfm.pronounceitalarm.domain.usecase.PopulateTestDataUseCase
import ru.nfm.pronounceitalarm.domain.usecase.UpdateAlarmUseCase
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val populateTestDataUseCase: PopulateTestDataUseCase,
    private val deleteAlarmUseCase: DeleteAlarmUseCase,
    private val getAlarmsUseCase: GetAlarmsUseCase,
    private val addAlarmUseCase: AddAlarmUseCase,
    private val updateAlarmUseCase: UpdateAlarmUseCase
) : ViewModel() {

    private val _screenState = MutableStateFlow<MainScreenState>(MainScreenState.Initial)
    val screenState: StateFlow<MainScreenState> = _screenState

    init {
        viewModelScope.launch {
            getAlarmsUseCase().collect { alarms ->
                _screenState.value = MainScreenState.Alarms(alarms)
            }
        }
    }

    fun populateDb() {
        viewModelScope.launch {
            populateTestDataUseCase()
        }
    }
}