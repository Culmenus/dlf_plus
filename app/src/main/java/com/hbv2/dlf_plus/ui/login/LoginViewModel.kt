package com.hbv2.dlf_plus.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns

import com.hbv2.dlf_plus.R
import com.hbv2.dlf_plus.networks.BackendApiClient
import com.hbv2.dlf_plus.networks.misc.SessionManager
import com.hbv2.dlf_plus.networks.requestBody.LoginRequestBody
import com.hbv2.dlf_plus.networks.responses.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel() : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm
    private lateinit var backendApiClient: BackendApiClient
    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String) {
        // can be launched in a separate asynchronous job
        var loginResponse: LoginResponse? = null;
        backendApiClient = BackendApiClient()
        backendApiClient.getApi().login(LoginRequestBody(username,password))
            .enqueue(object : Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    _loginResult.value = LoginResult(error = R.string.login_failed)
                }

                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    loginResponse = response.body();
                    if(response.isSuccessful && loginResponse != null) {
                        _loginResult.value =
                            LoginResult(success = loginResponse)
                    } else {
                        _loginResult.value = LoginResult(error = R.string.login_failed);
                        loginResponse = null;
                    }
                }
            })
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(username).matches()

    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 1
    }
}