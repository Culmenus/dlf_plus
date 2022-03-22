package com.hbv2.dlf_plus.networks

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.hbv2.dlf_plus.R
import com.hbv2.dlf_plus.networks.responses.LoginResponse

class SessionManager (context: Context) {
    private var prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val USER_DETAILS: String = "USER_DETAILS"
    }

    /**
     * Function to save auth token
     */
    fun saveAuthedUser(user: LoginResponse) {
        val editor = prefs.edit()

        editor.putString(USER_DETAILS, Gson().toJson(user))
        editor.apply()
    }

    fun isUserStored(): Boolean {
        return "USER_DETAILS" != prefs.getString(USER_DETAILS, null)
    }

    fun fetchAuthedUserDetails(): LoginResponse? {
        val details = prefs.getString(USER_DETAILS, null)
        if (details == "USER_DETAILS")
            return null
        return Gson().fromJson(details,LoginResponse::class.java)
    }

    fun removeAuthedUser(){
        //TODO
    }
}