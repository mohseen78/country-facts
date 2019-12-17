package com.mohseen78.countryfacts.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mohseen78.countryfacts.MainActivity
import com.mohseen78.countryfacts.model.Fact
import com.mohseen78.countryfacts.model.FactsList
import com.mohseen78.countryfacts.util.ConnectivityReceiver
import com.mohseen78.countryfacts.util.FactsApi
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

enum class FactsApiStatus { LOADING, ERROR, DONE }

class FactsViewModel : ViewModel(){

    // Internal MutableLiveData to handle navigation
    private val _status = MutableLiveData<FactsApiStatus>()

    // The external immutable LiveData for the navigation
     val status: LiveData<FactsApiStatus>
       get() = _status

    // Internal MutableLiveData for the title
    private var _title = MutableLiveData<String>()

    // The external immutable LiveData for the title
    val title: LiveData<String>
        get() = _title

    // Internal MutableLiveData for the Fact Property
    private val _properties = MutableLiveData<List<Fact>>()

    // The external immutable LiveData for the Fact Property
     val properties: LiveData<List<Fact>>
        get() = _properties

    //Call getFactsProperties() on init so we can display status immediately.
    init {
        getFactsProperties()
    }

    //Gets Facts property information from the API Retrofit service and updates the _properties List
    private fun getFactsProperties(){
        _status.value = FactsApiStatus.LOADING
        FactsApi.retrofitService.getProperties().enqueue(object: Callback<FactsList>{
            override fun onFailure(call: Call<FactsList>, t: Throwable) {
                _status.value = FactsApiStatus.ERROR
                Log.e("FactsViewModel", "" + t.message)
            }

            override fun onResponse(call: Call<FactsList>, response: Response<FactsList>) {
                if(response.isSuccessful) {
                    _properties.value = response.body()!!.facts
                    _title.value = response.body()!!.title
                    _status.value = FactsApiStatus.DONE
                }
            }
        })
    }

    //Gets Facts property information when swipe refreshed and updates the _properties List
    fun refreshFacts (){
        getFactsProperties()
    }
}