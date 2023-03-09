package com.socialquantum.acityinte

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.socialquantum.acityinte.MamaClass.Companion.pampam
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class SecondActivity : AppCompatActivity() {
    private var mCompositeDisposable: CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        mCompositeDisposable = CompositeDisposable()


        val reqTwo = Retrofit.Builder()
            .baseUrl("http://pro.ip-api.com/")
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(Helper.RequestA::class.java)


        mCompositeDisposable?.add(
            reqTwo.getDataA()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ response -> onResponse(response) }, { t -> onFailure(t) })
        )
    }

    private fun onFailure(t: Throwable) {
        Toast.makeText(this,t.message, Toast.LENGTH_SHORT).show()
    }

    private fun onResponse(response: Helper.RetroA) {
        MamaClass.pampam.put("GEO", response.countryCode)
        startActivity(Intent(this,MainActivity::class.java))
    }


}