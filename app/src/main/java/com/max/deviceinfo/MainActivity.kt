package com.max.deviceinfo

import android.app.ActivityManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.textView)
        textView.text = getDeviceInfo(this)
    }

    private fun getDeviceInfo(context: Context): String {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val memoryInfo = ActivityManager.MemoryInfo()
        activityManager.getMemoryInfo(memoryInfo)

        val cpuInfo = try {
            File("/proc/cpuinfo").readText()
        } catch (e: Exception) {
            "CPU Info nicht verfügbar"
        }

        return """
Hersteller: ${Build.MANUFACTURER}
Modell: ${Build.MODEL}
Android: ${Build.VERSION.RELEASE} (SDK ${Build.VERSION.SDK_INT})
RAM Gesamt: ${memoryInfo.totalMem / 1024 / 1024} MB

CPU:
$cpuInfo
        """.trimIndent()
    }
}
