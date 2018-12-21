package com.hubofallthings.datatrader.helper

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.support.customtabs.CustomTabsIntent

class ChromeHelper(private val activity: Activity){
    fun startBrowser(url : String){
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
        try {
            customTabsIntent.launchUrl(activity, Uri.parse(url))
        } catch (e : ActivityNotFoundException) {
            e.printStackTrace()
//            val intent = Intent(activity, WebviewActivity::class.java)
//            intent.putExtra(WebviewActivity.EXTRA_URL, url)
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
//            activity.startActivity(intent)
        }
    }
}