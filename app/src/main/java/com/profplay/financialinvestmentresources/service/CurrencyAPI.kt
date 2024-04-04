package com.profplay.financialinvestmentresources.service


import android.util.ArrayMap
import com.profplay.financialinvestmentresources.model.CurrencyModel
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response
import retrofit2.http.GET

interface CurrencyAPI {
    @GET("embed/altin.json")
    suspend fun getData() : Response<ArrayMap<String,CurrencyModel>>

}