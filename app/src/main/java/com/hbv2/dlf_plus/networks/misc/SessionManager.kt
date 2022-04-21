package com.hbv2.dlf_plus.networks.misc

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.hbv2.dlf_plus.R
import com.hbv2.dlf_plus.data.model.Forum
import com.hbv2.dlf_plus.networks.responses.ForumsResponseItem
import com.hbv2.dlf_plus.networks.responses.LoginResponse

class SessionManager (context: Context) {
    private var prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val KEY: String = "USER_DETAILS"
    }


    fun saveAuthedUser(user: LoginResponse) {
        val editor = prefs.edit()

        editor.putString(KEY, Gson().toJson(user))
        editor.apply()
    }

    fun isUserStored(): Boolean {
        return prefs.getString(KEY, null) != null
    }

    fun fetchAuthedUserDetails(): LoginResponse? {
        val details = prefs.getString(KEY, null)
        if (details == "USER_DETAILS")
            return null
        return Gson().fromJson(details,LoginResponse::class.java)
    }

    fun updateFavorites(newFavs: ArrayList<ForumsResponseItem>) {
        var details = fetchAuthedUserDetails();
        details?.favoriteForums = newFavs;
        saveAuthedUser(details!!);
    }

    fun removeAuthedUser(){
        val editor = prefs.edit()
        editor.remove(KEY)
        editor.apply()
    }
}