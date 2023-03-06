package com.socialquantum.acityinte

import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

class Helper() {

    data class RetroB(val appsChecker : String, val view : String, val geo : String)

    interface RequestInterface {

        @GET("typo.json")
        fun getData() : Observable<RetroB>
    }

    data class RetroA(val countryCode : String)

    interface RequestA {

        @GET("json/?key=LbwKKoO9eF4GLMz")
        fun getDataA() : Observable<RetroA>
    }
}