package neu.mobileappdev.carspec.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import neu.mobileappdev.carspec.api.ApiService
import neu.mobileappdev.carspec.api.LoginRepository

class LoginViewModel(
    private val repository: LoginRepository = LoginRepository(),
) : ViewModel() {
    // login event handler
    private val loginSuccessEvent = MutableLiveData<Unit>()
    val loginSuccess get() = loginSuccessEvent

    // error handler
    private val messageData = MutableLiveData<String?>()
    val message get() = messageData

    // login function
    fun tryLogin(
        username: String,
        password: String,
    ) {
        viewModelScope.launch {
            try {
                if (repository.login(username, password)) {
                    loginSuccessEvent.postValue(Unit)
                } else {
                    messageData.value = "Invalid username or password\nPlease Try Again"
                }
            } catch (e: ApiService.FetchException) {
                messageData.value = e.message
            } catch (e: Exception) {
                messageData.value = "An error occurred while trying to login\nPlease Try Again"
            }
        }
    }

    // get login hint
    fun getHint() {
        viewModelScope.launch {
            messageData.value = repository.getHint()
        }
    }

    fun clearErrorMessage() {
        messageData.value = null
    }
}
