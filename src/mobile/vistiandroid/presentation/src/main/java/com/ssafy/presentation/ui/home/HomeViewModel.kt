package com.ssafy.presentation.ui.home


import android.os.CountDownTimer
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.ssafy.domain.model.Member
import com.ssafy.domain.model.Resource
import com.ssafy.domain.model.home.HomeLastStoryBox
import com.ssafy.domain.usecase.fcm.FcmUseCase
import com.ssafy.domain.usecase.memberinformation.GetHomeStoryBoxUseCase
import com.ssafy.domain.usecase.memberinformation.GetHomeStoryUseCase
import com.ssafy.domain.usecase.memberinformation.GetMemberInformUseCase
import com.ssafy.presentation.ui.common.TimeFormatExt.timeFormat
import com.ssafy.presentation.ui.home.component.MemberState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMemberInformUseCase: GetMemberInformUseCase,
    private val getHomeStoryUseCase: GetHomeStoryUseCase,
    private val getHomeStoryBoxUseCase: GetHomeStoryBoxUseCase,
    private val fcmUseCase: FcmUseCase,
) : ViewModel() {

    private val _homeStoryState = mutableStateOf(HomeStoryState())
    val homeStoryState: State<HomeStoryState> = _homeStoryState

    private val _homeStoryBoxState = mutableStateOf(HomeStoryBoxState())
    val homeStoryBoxState: State<HomeStoryBoxState> = _homeStoryBoxState

    private val _homeLastStoryBoxState = mutableStateOf(HomeLastStoryBoxState())
    val homeLastStoryBoxState: State<HomeLastStoryBoxState> = _homeLastStoryBoxState

    private val _memberInformation = mutableStateOf(MemberState())
    val memberInformation: State<MemberState> = _memberInformation

    val timerText = mutableStateOf("")

    private var isTimerRunning = true

    private fun startCountDownTimer(timeDiffInMillis: Long) = viewModelScope.launch {
        object : CountDownTimer(timeDiffInMillis, DELAY_TIME) {
            override fun onTick(currentTimeLeft: Long) {
                if (!isTimerRunning) {
                    timerText.value = currentTimeLeft.timeFormat()
                }
            }

            override fun onFinish() {
                timerText.value = timeDiffInMillis.timeFormat()
                getHomeStoryBox()
            }
        }.start()
    }//TODO ui는 정상적으로 보여도 코루틴 잡은 계속 살아있음...


    private fun startTimer() = viewModelScope.launch {
        while (isTimerRunning) {
            val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
            val currentTimeString = sdf.format(Calendar.getInstance().time)
            timerText.value = currentTimeString
            // TODO 만약에 밤 12시가 지나면 다시 호출해야할지도?
            if (currentTimeString == "00:00:00")
                getHomeStoryBox()
            delay(DELAY_TIME)
        }
    }


    init {
        getHomeStory()
        getHomeStoryBox()
        getMemberInformation()
        getFcmToken()
    }


    private fun getFcmToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }
            // token log 남기기
            if (task.result != null) {
                uploadFcmToken(task.result!!)
            }
        })
    }

    private fun uploadFcmToken(fcmToken: String) {
        viewModelScope.launch {
            fcmUseCase.uploadFcmToken(fcmToken)
        }
    }


    fun getHomeStory() {
        getHomeStoryUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _homeStoryState.value = HomeStoryState(stories = result.data ?: emptyList())
                }

                is Resource.Error -> {
                    _homeStoryState.value =
                        HomeStoryState(error = result.message ?: "An error occurred")
                }

                is Resource.Loading -> {
                    _homeStoryState.value = HomeStoryState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getHomeStoryBox() {
        getHomeStoryBoxUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _homeStoryBoxState.value =
                        HomeStoryBoxState(storyBoxList = result.data ?: emptyList())

                    if (_homeStoryBoxState.value.storyBoxList.isNotEmpty()) {
                        val lastStoryBox = _homeStoryBoxState.value.storyBoxList[0]
                        val currentCalendar = Calendar.getInstance()
                        val sdf = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
                        val targetCalendar = Calendar.getInstance()
                        targetCalendar.time =
                            sdf.parse(lastStoryBox.finishAt) ?: Calendar.getInstance().time
                        val timeDiffInMillis =
                            targetCalendar.timeInMillis - currentCalendar.timeInMillis

                        if (timeDiffInMillis > SHOW_TIME || timeDiffInMillis < 0) {
                            isTimerRunning = true
                            startTimer()
                        } else {
                            isTimerRunning = false
                            _homeLastStoryBoxState.value =
                                HomeLastStoryBoxState(
                                    storyBox = HomeLastStoryBox(
                                        id = lastStoryBox.id,
                                        name = lastStoryBox.name
                                    )
                                )
                            startCountDownTimer(timeDiffInMillis)
                        }
                    } else {
                        isTimerRunning = true
                        startTimer()
                    }
                }

                is Resource.Error -> {
                    _homeStoryBoxState.value =
                        HomeStoryBoxState(error = result.message ?: "An error occurred")
                }

                is Resource.Loading -> {
                    _homeStoryBoxState.value = HomeStoryBoxState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getMemberInformation() {
        getMemberInformUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _memberInformation.value = MemberState(
                        memberInformation = result.data
                            ?: Member()
                    )
                }

                is Resource.Error -> {
                    _memberInformation.value =
                        MemberState(error = result.message ?: "An error occurred")
                }

                is Resource.Loading -> {
                    _memberInformation.value = MemberState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    companion object {
        private const val DELAY_TIME = 1000L
        private const val SHOW_TIME = 345600000 //4일
    }

}