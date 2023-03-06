package com.socialquantum.acityinte

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.appsflyer.AppsFlyerLib
import com.my.tracker.MyTracker
import com.onesignal.OneSignal
import com.socialquantum.acityinte.MamaClass.Companion.pampam
import org.json.JSONException
import org.json.JSONObject


class FilerMeNow : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filer_me_now)
        createURL()
    }

    fun createURL() {
        val appsData = pampam["AppsData"]
        val depData = pampam["FBData"]
        val gaid = pampam["GAID"]
        val view = pampam["View"]
        val geoHost = pampam["GeoHose"]
        val geo = pampam["GEO"]
        val appsCheck = pampam["AppsCh"]
        val trackerParams = MyTracker.getTrackerParams()

        trackerParams.setCustomUserId(gaid)
        pushToOS(gaid.toString())


        val shP = getSharedPreferences("NEWPR", Context.MODE_PRIVATE)

        val instID = MyTracker.getInstanceId(applicationContext)

        val afId = AppsFlyerLib.getInstance().getAppsFlyerUID(this)
        AppsFlyerLib.getInstance().setCollectAndroidID(true)
        val buildVers = Build.VERSION.RELEASE


        val sub1 = "sub_id_1="
        val sub2 = "ad_id="
        val sub3 = "deviceID="
        val sub4 = "sub_id_4="
        val sub5 = "sub_id_5="
        val nam = "naming"
        val depp = "deeporg"



        var link = ""

        when (appsCheck) {
            "1" ->
                if (appsData != "null") {
                    link =
                        "$view$sub1$appsData&$sub3$afId&$sub2$gaid&$sub4$buildVers&$sub5$nam"

                    shP.edit().putString("link", link).apply()
                    shP.edit().putString("ENTRY_CODE", "web").apply()
                    Toast.makeText(this, "Naming", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this, InfiActivity::class.java))
                    finish()


                } else if (depData != null || geoHost!!.contains(geo.toString())) {
                    link =
                        "$view$sub1$depData&$sub3$afId&$sub2$gaid&$sub4$buildVers&$sub5$nam"
                    shP.edit().putString("link", link).apply()
                    shP.edit().putString("ENTRY_CODE", "web").apply()
                    Toast.makeText(this, "DeepOrg", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, InfiActivity::class.java))
                    finish()
                } else {
                    startActivity(Intent(this, GamefiAct::class.java))
                    finish()
                }
            "0" ->
                if (depData != null) {
                    link =
                        "$view$sub1$depData&$sub3$instID&$sub2$gaid&$sub4$buildVers&$sub5$depp"
                    shP.edit().putString("link", link).apply()
                    shP.edit().putString("ENTRY_CODE", "web").apply()
                    startActivity(Intent(this, InfiActivity::class.java))
                    finish()

                    Toast.makeText(this, "Null FB", Toast.LENGTH_LONG).show()
                } else if (geoHost!!.contains(geo.toString())) {
                    link = "$view$sub3$instID&$sub2$gaid&$sub4$buildVers&$sub5$nam"
                    shP.edit().putString("link", link).apply()
                    shP.edit().putString("ENTRY_CODE", "web").apply()
                    Toast.makeText(this, "Null MT", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this, InfiActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Go to game", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this, GamefiAct::class.java))
                    finish()
                }
        }
    }

    fun pushToOS(id: String) {
        Toast.makeText(this, "PushExpressed", Toast.LENGTH_SHORT).show()
        OneSignal.setExternalUserId(
            id,
            object : OneSignal.OSExternalUserIdUpdateCompletionHandler {
                override fun onSuccess(results: JSONObject) {
                    try {
                        if (results.has("push") && results.getJSONObject("push").has("success")) {
                            val isPushSuccess = results.getJSONObject("push").getBoolean("success")
                            OneSignal.onesignalLog(
                                OneSignal.LOG_LEVEL.VERBOSE,
                                "Set external user id for push status: $isPushSuccess"
                            )
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                    try {
                        if (results.has("email") && results.getJSONObject("email").has("success")) {
                            val isEmailSuccess =
                                results.getJSONObject("email").getBoolean("success")
                            OneSignal.onesignalLog(
                                OneSignal.LOG_LEVEL.VERBOSE,
                                "Set external user id for email status: $isEmailSuccess"
                            )
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                    try {
                        if (results.has("sms") && results.getJSONObject("sms").has("success")) {
                            val isSmsSuccess = results.getJSONObject("sms").getBoolean("success")
                            OneSignal.onesignalLog(
                                OneSignal.LOG_LEVEL.VERBOSE,
                                "Set external user id for sms status: $isSmsSuccess"
                            )
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }

                override fun onFailure(error: OneSignal.ExternalIdError) {
                    OneSignal.onesignalLog(
                        OneSignal.LOG_LEVEL.VERBOSE,
                        "Set external user id done with error: $error"
                    )
                }
            })
    }
}
