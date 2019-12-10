package com.mohseen78.countryfacts.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mohseen78.countryfacts.model.Fact
import com.mohseen78.countryfacts.model.FactsList
import com.mohseen78.countryfacts.util.FactsApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

enum class FactsApiStatus { LOADING, ERROR, DONE }

class FactsViewModel : ViewModel(){

    private val _status = MutableLiveData<FactsApiStatus>()

    val status: LiveData<FactsApiStatus>
        get() = _status

    private var _title = MutableLiveData<String>()
    val title: LiveData<String>
        get() = _title

    private val _properties = MutableLiveData<List<Fact>>()

    val properties: LiveData<List<Fact>>
        get() = _properties

    init {
        getFactsProperties()
    }

    private fun getFactsProperties(){
        _status.value = FactsApiStatus.LOADING
        FactsApi.retrofitService.getProperties().enqueue(object: Callback<FactsList>{
            override fun onFailure(call: Call<FactsList>, t: Throwable) {
                _status.value = FactsApiStatus.ERROR
                Log.e("FactsViewModel", "" + t.message)
            }

            override fun onResponse(call: Call<FactsList>, response: Response<FactsList>) {
                _properties.value = response.body()!!.facts
                _title.value = response.body()!!.title
                _status.value = FactsApiStatus.DONE
            }
        })
    }

    fun refreshFacts (){
        getFactsProperties()
    }
}