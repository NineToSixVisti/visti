package com.ssafy.presentation.ui.home


import android.os.CountDownTimer
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.domain.model.Member
import com.ssafy.domain.model.Resource
import com.ssafy.domain.model.StoryBox
import com.ssafy.domain.usecase.memberinformation.GetHomeStoryBoxUseCase
import com.ssafy.domain.usecase.memberinformation.GetHomeStoryUseCase
import com.ssafy.domain.usecase.memberinformation.GetMemberInformUseCase
import com.ssafy.domain.usecase.memberinformation.GetMyLastStoryBoxUseCase
import com.ssafy.presentation.ui.common.TimeFormatExt.timeFormat
import com.ssafy.presentation.ui.like.MemberState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMemberInformUseCase: GetMemberInformUseCase,
    private val getHomeStoryUseCase: GetHomeStoryUseCase,
    private val getHomeStoryBoxUseCase: GetHomeStoryBoxUseCase,
    private val getMyLastStoryBoxUseCase: GetMyLastStoryBoxUseCase,
) : ViewModel() {

    private val _homeStoryState = mutableStateOf(HomeStoryState())
    val homeStoryState: State<HomeStoryState> = _homeStoryState

    private val _homeStoryBoxState = mutableStateOf(HomeStoryBoxState())
    val homeStoryBoxState: State<HomeStoryBoxState> = _homeStoryBoxState

    private val _homeLastStoryBoxState = mutableStateOf(HomeLastStoryBoxState())
    val homeLastStoryBoxState: State<HomeLastStoryBoxState> = _homeLastStoryBoxState

    private val _memberInformation = mutableStateOf(MemberState())
    val memberInformation: State<MemberState> = _memberInformation

    private var countDownTimer: CountDownTimer? = null

    var initialTotalTimeInMillis = 0L
    var timeLeft = mutableStateOf(initialTotalTimeInMillis)
    val countDownInterval = 1000L // 1 seconds is the lowest

    val timerText = mutableStateOf(timeLeft.value.timeFormat())

    val isPlaying = mutableStateOf(false)

    fun startCountDownTimer() = viewModelScope.launch {
        isPlaying.value = true
        countDownTimer = object : CountDownTimer(initialTotalTimeInMillis, countDownInterval) {
            override fun onTick(currentTimeLeft: Long) {
                timerText.value = currentTimeLeft.timeFormat()
                timeLeft.value = currentTimeLeft
            }

            override fun onFinish() {
                timerText.value = initialTotalTimeInMillis.timeFormat()
                isPlaying.value = false
                getHomeLastStoryBox()
            }
        }.start()
    }

    init {
        // getHomeStory()
        //  getHomeStoryBox()
        getMemberInformation()
        getHomeLastStoryBox()
    }

    private fun getHomeStory() {
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

    private fun getHomeStoryBox() {
        getHomeStoryBoxUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _homeStoryBoxState.value =
                        HomeStoryBoxState(storyBoxList = result.data ?: emptyList())
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

    private fun getHomeLastStoryBox() {
        getMyLastStoryBoxUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _homeLastStoryBoxState.value =
                        HomeLastStoryBoxState(storyBox = result.data ?: StoryBox())
                    initialTotalTimeInMillis = result.data?.finishAt ?: 123456789L
                    startCountDownTimer()
                }

                is Resource.Error -> {
                    _homeLastStoryBoxState.value =
                        HomeLastStoryBoxState(error = result.message ?: "An error occurred")
                }

                is Resource.Loading -> {
                    _homeLastStoryBoxState.value = HomeLastStoryBoxState(isLoading = true)
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

}