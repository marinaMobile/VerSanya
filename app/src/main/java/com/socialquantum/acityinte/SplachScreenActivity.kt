package com.socialquantum.acityinte

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.facebook.applinks.AppLinkData
import com.socialquantum.acityinte.MamaClass.Companion.pampam
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class SplachScreenActivity : AppCompatActivity() {
    private var mCompositeDisposable: CompositeDisposable? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splach_screen)

        val help = Helper()


        val prefs = getSharedPreferences("ActivityPREF", MODE_PRIVATE)
        if (prefs.getBoolean("activity_exec", false)) {
            val prefsInception = getSharedPreferences("NEWPR", Context.MODE_PRIVATE)
            val entryCode =  prefsInception.getString("ENTRY_CODE", "0")
            if (entryCode == "web"){
                startActivity(Intent(this, InfiActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this, GamefiAct::class.java))
                finish()
            }
        } else {
            val exec = prefs.edit()
            exec.putBoolean("activity_exec", true)
            exec.apply()

            help.deepL(this)


            mCompositeDisposable = CompositeDisposable()

            val requestInterface = Retrofit.Builder()
                .baseUrl("http://infinitytiger.live/")
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(Helper.RequestInterface::class.java)

            mCompositeDisposable?.add(
                requestInterface.getData()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({ response -> onResponse(response) }, { t -> onFailure(t) })
            )
        }





    }
    private fun onFailure(t: Throwable) {
        Toast.makeText(this,t.message, Toast.LENGTH_SHORT).show()
    }

    private fun onResponse(response: Helper.RetroB) {
        pampam["AppsCh"] = response.appsChecker
        pampam["GeoHose"] = response.geo
        pampam["View"] = response.view
        startActivity(Intent(this, SecondActivity::class.java))
        finish()
    }


}