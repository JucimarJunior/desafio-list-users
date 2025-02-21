package com.picpay.desafio.android.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.picpay.desafio.android.model.User
import com.picpay.desafio.android.service.RetrofitConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {
    private val service = RetrofitConfig.getUserService()
    val contacts = MutableLiveData<List<User>>()
    val error = MutableLiveData<String>()

    fun getContacts() {
        service.getUsers().enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    contacts.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                error.value = t.message.toString()
                Log.d("TAG",t.message.toString())
            }
        })
    }
}