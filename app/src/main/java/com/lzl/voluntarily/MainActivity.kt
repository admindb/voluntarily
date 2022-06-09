package com.lzl.voluntarily

import android.content.ComponentName
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!AccessibilityServiceTest.isAccessibilityServiceSettingOn(this@MainActivity)) {
            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            startActivity(intent)
        }

        btn.setOnClickListener {
            var intent2 = Intent(this@MainActivity,AccessibilityServiceTest::class.java)
//        intent2.putExtra("width", width)
//        intent2.putExtra("height", height)
            startService(intent2)

            //启动app
//            var intent=packageManager.getLaunchIntentForPackage("zanyouninesix.say")
            var intent=packageManager.getLaunchIntentForPackage("com.jlsh.internation")
            startActivity(intent)
        }



    }


}