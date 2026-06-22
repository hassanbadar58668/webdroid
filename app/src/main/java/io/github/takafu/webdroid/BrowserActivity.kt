package io.github.takafu.webdroid

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.webkit.WebView

class BrowserActivity : Activity() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        var instance: BrowserActivity? = null

        var webView: WebView? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        instance = this

        // Start services
        startServices()

        // Finish activity (no UI needed)
        finish()
    }

    private fun startServices() {
        // Start AutomationService
        val automationIntent = Intent(this, AutomationService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(automationIntent)
        } else {
            startService(automationIntent)
        }

        // Start FloatingBubbleService
        val bubbleIntent = Intent(this, FloatingBubbleService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(bubbleIntent)
        } else {
            startService(bubbleIntent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        instance = null
    }
}
