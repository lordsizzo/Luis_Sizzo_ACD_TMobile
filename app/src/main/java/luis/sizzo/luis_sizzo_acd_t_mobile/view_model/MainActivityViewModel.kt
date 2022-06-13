package luis.sizzo.luis_sizzo_acd_t_mobile.view_model

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import luis.sizzo.luis_sizzo_acd_t_mobile.model.UI_State
import luis.sizzo.luis_sizzo_acd_t_mobile.model.res.Repository
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val repository: Repository,
    private val coroutineScope: CoroutineScope,
) : ViewModel() {

    private val _searchUserResponse = MutableLiveData<UI_State>()
    val searchUserResponse: MutableLiveData<UI_State>
        get() = _searchUserResponse

    private val _userResponse = MutableLiveData<UI_State>()
    val getUserResponse: MutableLiveData<UI_State>
        get() = _userResponse

    private val _userRepoResponse = MutableLiveData<UI_State>()
    val getUserRepoResponse: MutableLiveData<UI_State>
        get() = _userRepoResponse

    private val _userSearchRepoResponse = MutableLiveData<UI_State>()
    val getSearchUserRepoResponse: MutableLiveData<UI_State>
        get() = _userSearchRepoResponse

    private val _stateView = MutableLiveData<Boolean>()
    val stateView: MutableLiveData<Boolean>
        get() = _stateView

    fun getSearchUser(search: String) {
        coroutineScope.launch {
            repository.getSearchUser(search).collect { state_result ->
                _searchUserResponse.postValue(state_result)
            }
        }
    }

    fun getStateView(state: Boolean) {
        _stateView.value = state
    }

    fun getUser(username: String) {
        coroutineScope.launch {
            repository.getUser(username).collect {
                _userResponse.postValue(it)
            }
        }
    }

    fun getUserRepo(username: String) {
        coroutineScope.launch {
            repository.getUserRepo(username).collect {
                _userRepoResponse.postValue(it)
            }
        }
    }

    fun getUserSearchRepo(username: String, reponame: String) {
        coroutineScope.launch {
            repository.getUserSearchRepo(username, reponame).collect {
                _userSearchRepoResponse.postValue(it)
            }
        }
    }
}