package com.socialquantum.acityinte

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.webkit.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class InfiActivity : AppCompatActivity() {


    private val INPUT_FILE_REQUEST_CODE = 1
    protected var mRequestCodeFilePicker: Int = INPUT_FILE_REQUEST_CODE
    var filePathCallbacks: ValueCallback<Array<Uri>>? = null

    lateinit var webBew: WebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        webBew = WebView(this)
        setOfSett()
        setContentView(webBew)

        webBew.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView,
                url: String
            ): Boolean {

                val pm = applicationContext.packageManager
                val isInstalled = isPackageInstalled("org.telegram.messenger", pm)

                try {
                    if (URLUtil.isNetworkUrl(url)) {
                        return false
                    }
                    if (isInstalled) {
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse(url)
                        this@InfiActivity.startActivity(intent)
                    } else {
                        Toast.makeText(
                            this@InfiActivity,
                            "Application is not installed",
                            Toast.LENGTH_LONG
                        ).show()
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/details?id=org.telegram.messenger")
                        )
                    }
                    return true
                } catch (e: Exception) {
                    return false
                }
            }

            override fun onReceivedError(
                view: WebView?,
                errorCode: Int,
                description: String?,
                failingUrl: String?
            ) {
                Toast.makeText(this@InfiActivity, description, Toast.LENGTH_SHORT).show()
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
//                saveUrl(url)
            }
        }

        webBew.webChromeClient = object : WebChromeClient() {
            override fun onShowFileChooser(
                webView: WebView?,
                filePathCallback: ValueCallback<Array<Uri>>?,
                fileChooserParams: FileChooserParams
            ):Boolean {

                filePathCallbacks?.onReceiveValue(null)
                filePathCallbacks = filePathCallback

                try {
                    openChooser()
                } catch (e: java.lang.Exception) {
                    Toast.makeText(this@InfiActivity, e.toString(), Toast.LENGTH_LONG).show()
                }
                return true
            }
        }
        webBew.loadUrl(urururururururur())
    }

    fun setOfSett() {
        CookieManager.getInstance().setAcceptCookie(true)
        CookieManager.getInstance().setAcceptThirdPartyCookies( webBew, true)
        val webViewSet = webBew.settings
        webViewSet.javaScriptEnabled = true
        webViewSet.useWideViewPort = true
        webViewSet.loadWithOverviewMode = true
        webViewSet.allowFileAccess = true
        webViewSet.domStorageEnabled = true
        webViewSet.userAgentString = webViewSet.userAgentString.replace("; wv", "")
        webViewSet.javaScriptCanOpenWindowsAutomatically = true
        webViewSet.setSupportMultipleWindows(false)
        webViewSet.displayZoomControls = false
        webViewSet.builtInZoomControls = true
        webViewSet.allowFileAccess = true
        webViewSet.allowContentAccess = true
        webViewSet.setSupportZoom(true)
        webViewSet.pluginState = WebSettings.PluginState.ON
        webViewSet.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        webViewSet.cacheMode = WebSettings.LOAD_DEFAULT
        webViewSet.allowContentAccess = true
        webViewSet.mediaPlaybackRequiresUserGesture = false
    }

    private fun isPackageInstalled(packageName: String, packageManager: PackageManager): Boolean {
        return try {
            packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    var exitexitexitexit = false
    var urlfififif = ""

    fun saveUrl(lurlurlurlurlur: String?) {
        if (!lurlurlurlurlur!!.contains("t.me")) {

            if (urlfififif == "") {

                    urlfififif = getSharedPreferences(
                        "SP_WEBVIEW_PREFS",
                        AppCompatActivity.MODE_PRIVATE
                    ).getString(
                        "SAVED_URL",
                        lurlurlurlurlur
                    ).toString()

                val spspspspsppspspsp =
                    getSharedPreferences(
                        "SP_WEBVIEW_PREFS",
                        AppCompatActivity.MODE_PRIVATE
                    )
                val ededededededed = spspspspsppspspsp?.edit()
                ededededededed?.putString("SAVED_URL", lurlurlurlurlur)
                ededededededed?.apply()
            }
        }
    }

    override fun onBackPressed() {
        if (webBew.canGoBack()) {
            if (exitexitexitexit) {
                webBew.stopLoading()
                webBew.loadUrl(urlfififif)
            }
            this.exitexitexitexit = true
            webBew.goBack()
            Handler(Looper.getMainLooper()).postDelayed({
                exitexitexitexit = false
            }, 2000)

        } else {
            super.onBackPressed()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == INPUT_FILE_REQUEST_CODE && (resultCode == RESULT_OK)) {

            if ((null == filePathCallbacks )) {
                return;
            } else {
                val dataString: String? = data?.dataString

                if (dataString != null) {
                    val result = arrayOf(Uri.parse(dataString))
                    filePathCallbacks?.onReceiveValue(result)
                    filePathCallbacks = null
                }
            }
        }
    }
    private fun openChooser() {

        val chooserIntent = Intent(Intent.ACTION_GET_CONTENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "image/*"
        }
        val intent = Intent(Intent.ACTION_CHOOSER).apply {
            putExtra(Intent.EXTRA_INTENT, chooserIntent)
            putExtra(Intent.EXTRA_TITLE, "Image Chooser")
        }
        startActivityForResult(Intent.createChooser(intent, "File Chooser"), INPUT_FILE_REQUEST_CODE);
    }

    private fun urururururururur(): String {

//        val spoon = getSharedPreferences("SP_WEBVIEW_PREFS", MODE_PRIVATE)

        val sharPre = getSharedPreferences("NEWPR",
            Context.MODE_PRIVATE)

        val link = sharPre.getString("link", null)
        Log.d("Lololol", link.toString())

        return link.toString()

//        return spoon.getString("SAVED_URL", link).toString()
    }
}

