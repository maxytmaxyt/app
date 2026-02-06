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

        // TextViews
        val deviceInfoTv = findViewById<TextView>(R.id.deviceInfo)
        val ramInfoTv = findViewById<TextView>(R.id.ramInfo)
        val cpuInfoTv = findViewById<TextView>(R.id.cpuInfo)

        // Geräte Info
        deviceInfoTv.text = """
            Hersteller: ${Build.MANUFACTURER}
            Modell: ${Build.MODEL}
            Android Version: ${Build.VERSION.RELEASE} (SDK ${Build.VERSION.SDK_INT})
        """.trimIndent()

        // RAM Info
        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val memoryInfo = ActivityManager.MemoryInfo()
        activityManager.getMemoryInfo(memoryInfo)
        ramInfoTv.text = """
            RAM Gesamt: ${memoryInfo.totalMem / 1024 / 1024} MB
            RAM Frei: ${memoryInfo.availMem / 1024 / 1024} MB
            RAM Niedrig: ${memoryInfo.lowMemory}
        """.trimIndent()

        // CPU Info
        val cpuInfo = try {
            File("/proc/cpuinfo").readText()
        } catch (e: Exception) {
            "CPU Info nicht verfügbar"
        }
        cpuInfoTv.text = cpuInfo
    }
}
