package com.socialquantum.acityinte

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.facebook.applinks.AppLinkData
import com.socialquantum.acityinte.MamaClass.Companion.pampam
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

class Helper() {

    fun deepL(context: Context) {
        AppLinkData.fetchDeferredAppLinkData(
            context
        ) {
            // Process app link
            val deepData = it!!.targetUri?.host.toString()
            Log.d("FB_TAG", "deepL: I'm alive")
            pampam["FBData"] = deepData
        }
    }

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