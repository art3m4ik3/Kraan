package ru.art3m4ik3.kraan.data.storage

import android.app.Activity
import android.content.Context
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.art3m4ik3.kraan.data.models.AuthResponse
import ru.art3m4ik3.kraan.data.models.User

class Auth(
    val username: String? = null,
    val password: String? = null,
    var token: String? = null
) {
    fun getNewToken(): String? {
        RetrofitHelper.kraanApi.authorization(User(username!!, password!!))
            .enqueue(object : Callback<AuthResponse> {
                override fun onResponse(
                    call: Call<AuthResponse>,
                    response: Response<AuthResponse>
                ) {
                    if (response.isSuccessful) {
                        token = response.body()!!.token
                        Log.i("Auth", "token: $token")
                    } else {
                        Log.e("Auth", "error: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                    Log.e("Auth", "error: ${t.message}")
                    token = null
                }
            })

        return token
    }

    fun authorized(context: Context): Boolean {
        val sharedPref = (context as Activity).getPreferences(Context.MODE_PRIVATE) ?: return false
        val token = sharedPref.getString("AUTH_TOKEN", null)

        return token != null
    }
}