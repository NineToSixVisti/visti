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
    private val fcmUseCase: FcmUseCase
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

    var initialTotalTimeInMillis = 10000L
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
                getHomeStoryBox()
            }
        }.start()
    }

    fun startTimer() = viewModelScope.launch {
        val updateIntervalMillis = 1000L
        while (true) {
            val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
            val currentTimeString = sdf.format(Calendar.getInstance().time)
            timerText.value = currentTimeString
            // TODO 만약에 밤 12시가 지나면 다시 호출해야할지도?
            if (currentTimeString == "00:00:00")
                getHomeStoryBox()
            delay(updateIntervalMillis)
        }
    }

    init {
        getHomeStory()
        getHomeStoryBox()
        getMemberInformation()
        getFcmToken()
    }


    fun getFcmToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }
            // token log 남기기
            if (task.result != null) {
                Log.e("TAG", "getFcmToken: ${task.result}")
                uploadFcmToken(task.result!!)
            }
        })
//        createNotificationChannel(channel_id, "ecoMate")
    }

    fun uploadFcmToken(fcmToken: String) {
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
                        var timeDiffInMillis =
                            targetCalendar.timeInMillis - currentCalendar.timeInMillis

                        initialTotalTimeInMillis = timeDiffInMillis
                        if (initialTotalTimeInMillis > 345600000 || initialTotalTimeInMillis < 0) {//4일
                            startTimer()
                        } else {
                            _homeLastStoryBoxState.value =
                                HomeLastStoryBoxState(
                                    storyBox = HomeLastStoryBox(
                                        id = lastStoryBox.id,
                                        name = lastStoryBox.name
                                    )
                                )
                            startCountDownTimer()
                        }
                    } else {
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
//    suspend fun getHomeLastStoryBox(): HomeLastStoryBox {
//        val response = api.getHomeStoryBox().detail
//
//        val currentCalendar = Calendar.getInstance()
//        if (response.size!=0) {
//            val lastStoryBoxDto = response[0]// TODO 하드코딩 고치기
//            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
//            val targetCalendar = Calendar.getInstance()
//            targetCalendar.time = sdf.parse(lastStoryBoxDto.finishedAt) ?: Calendar.getInstance().time
//
//            var timeDiffInMillis = targetCalendar.timeInMillis - currentCalendar.timeInMillis
//
//            if (timeDiffInMillis < 0) {
//                timeDiffInMillis = 0
//            }
//
//            return HomeLastStoryBox(
//                lastStoryBoxDto.id,
//                lastStoryBoxDto.encryptedId,
//                lastStoryBoxDto.boxImgPath,
//                lastStoryBoxDto.name,
//                lastStoryBoxDto.createdAt,
//                timeDiffInMillis,
//                lastStoryBoxDto.blind
//            )
//        }
//
//        currentCalendar.timeInMillis
//
//        return HomeLastStoryBox(finishAt = currentCalendar.timeInMillis)
//    }

//    private fun getHomeLastStoryBox() {
//        getHomeLastStoryBoxUseCase().onEach { result ->
//            when (result) {
//                is Resource.Success -> {
//                    _homeLastStoryBoxState.value =
//                        HomeLastStoryBoxState(storyBox = result.data ?: HomeLastStoryBox())
//                    initialTotalTimeInMillis = result.data?.finishAt ?: 123456789L
//                    if (result.data!!.id != -1)
//                        startCountDownTimer()
//                    else
//                        startTimer()
//
//                }
//
//                is Resource.Error -> {
//                    _homeLastStoryBoxState.value =
//                        HomeLastStoryBoxState(error = result.message ?: "An error occurred")
//                }
//
//                is Resource.Loading -> {
//                    _homeLastStoryBoxState.value = HomeLastStoryBoxState(isLoading = true)
//                }
//            }
//        }.launchIn(viewModelScope)
//    }


    fun getMemberInformation() {
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