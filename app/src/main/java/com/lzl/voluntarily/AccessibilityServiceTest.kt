package com.lzl.voluntarily

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.provider.Settings.SettingNotFoundException
import android.text.TextUtils.SimpleStringSplitter
import android.util.Log
import android.view.KeyEvent
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import androidx.annotation.RequiresApi
import androidx.core.view.MotionEventCompat.getSource
import java.lang.Exception
import java.util.*
import kotlin.concurrent.timerTask


/**
 * @Description:
 * @Author:      lzl
 * @CreateDate:  2022/6/6 17:17
 */
class AccessibilityServiceTest : AccessibilityService() {

    var isMark=false
    var number=0
    var phone1="15220076132"
    var phone2="13797700373"
    var password1="123456"
    var password2="123456"

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
//        Log.d("XLZH:", event.toString())
//        val eventType: Int = event!!.getEventType()
//        when (eventType) {
//            AccessibilityEvent.TYPE_VIEW_SCROLLED -> {
//                val nodeInfo = rootInActiveWindow
//                val list =
//                    nodeInfo.findAccessibilityNodeInfosByViewId("com.tendcloud.demo:id/pager")
//                val contentNodeInfo = list[0]
//                Log.d("XLZH size:", contentNodeInfo.childCount.toString())
//                Log.d("XLZH class: ", contentNodeInfo.className.toString())
//                if (contentNodeInfo.findAccessibilityNodeInfosByText("button_test1") != null) {
//                    Log.d("XLZH :", "first page")
//                }
//                if (contentNodeInfo.findAccessibilityNodeInfosByText("button1") != null) {
//                    Log.d("XLZH :", "second page")
//                }
//                if (contentNodeInfo.findAccessibilityNodeInfosByText("tvweb") != null) {
//                    Log.d("XLZH :", "third page")
//                }
//            }
//        }

//        if (event == null || event.packageName == null || event.packageName == "") {
//            return
//        }
//
//        if (event.packageName == "zanyouninesix.say") {
//
//
//        }
        Log.e("onAccessibilityEvent","00==="+event.toString())



        for (index in 0..rootInActiveWindow.childCount) {
//                        if (info.getChild(index).className.equals("android.widget.ImageView")){
//                            info.getChild(index).performAction(AccessibilityNodeInfo.ACTION_FOCUS)
//                            info.getChild(index).performAction(AccessibilityNodeInfo.ACTION_CLICK)
//                            break
//                        }
//            Log.e("onAccessibilityEvent","333=="+rootInActiveWindow.getChild(index).toString())
        }

//        if (null != list && list.isNotEmpty()) {
//            for (info in list) {
//                if ("android.widget.ScrollView".equals(info.className)){
//                    for (index in 0..info.childCount) {
////                        if (info.getChild(index).className.equals("android.widget.ImageView")){
////                            info.getChild(index).performAction(AccessibilityNodeInfo.ACTION_FOCUS)
////                            info.getChild(index).performAction(AccessibilityNodeInfo.ACTION_CLICK)
////                            break
////                        }
//                        Log.e("onAccessibilityEvent","11==="+info.getChild(index).toString())
//
//                        for (index in 0..info.getChild(index).childCount) {
////                        if (info.getChild(index).className.equals("android.widget.ImageView")){
////                            info.getChild(index).performAction(AccessibilityNodeInfo.ACTION_FOCUS)
////                            info.getChild(index).performAction(AccessibilityNodeInfo.ACTION_CLICK)
////                            break
////                        }
//                            Log.e("onAccessibilityEvent","22=="+info.getChild(index).toString())
//                        }
//                    }
//                    break
//                }
//            }
//        }

        val eventType: Int = event!!.getEventType()
        when (eventType) {
            AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED -> {
                val nodeInfo = rootInActiveWindow
                //切换到 我的页面
                val list = nodeInfo.findAccessibilityNodeInfosByViewId("zanyouninesix.say:id/mineLl")
                if (null != list) {
                    for (info in list) {
//                        Timer().schedule(timerTask {
//                            info.performAction(AccessibilityNodeInfo.ACTION_FOCUS)
//                            info.performAction(AccessibilityNodeInfo.ACTION_CLICK)
//                        }, 3000)
                    }
                }


                //先判断有没有登录 如果没有登录 跳登录
                val list1 = nodeInfo.findAccessibilityNodeInfosByViewId("zanyouninesix.say:id/userLoginRegTv")
                if (null != list1 && list1.isNotEmpty()) {
                    for (info in list1) {
                        if (info.getText().toString().equals("注册/登录")) {
                            //找到你的节点以后 就直接点击他就行了
                            Timer().schedule(timerTask {
                                info.performAction(AccessibilityNodeInfo.ACTION_FOCUS)
                                info.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                            }, 1000)
                        }
                    }
                }

                //到了登录页面
                if (event.className.contains("com.zanyou.sya.user.ui.activity.LoginActivity")){

                    //输入密码
                    Timer().schedule(timerTask {
                        val list = nodeInfo.findAccessibilityNodeInfosByViewId("zanyouninesix.say:id/login_phone")
                        if (null != list && list.isNotEmpty()) {
                            val arguments = Bundle()
                            arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, if (number==0) phone1 else phone2)
                            list[0].performAction(AccessibilityNodeInfo.ACTION_FOCUS)
                            list[0].performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments)
                        }
                    }, 500)

                    //输入账号
                    Timer().schedule(timerTask {
                        val list = nodeInfo.findAccessibilityNodeInfosByViewId("zanyouninesix.say:id/login_password")
                        if (null != list && list.isNotEmpty()) {
                            val arguments = Bundle()
                            arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, if (number==0) password1 else password2)
                            list[0].performAction(AccessibilityNodeInfo.ACTION_FOCUS)
                            list[0].performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments)
                        }
                    }, 1000)

                    //点击登录按钮
                    Timer().schedule(timerTask {
                        val list = nodeInfo.findAccessibilityNodeInfosByViewId("zanyouninesix.say:id/login_sumbit")
                        if (null != list && list.isNotEmpty()) {
                            isMark=true
                            list[0].performAction(AccessibilityNodeInfo.ACTION_FOCUS)
                            list[0].performAction(AccessibilityNodeInfo.ACTION_CLICK)
                        }
                    }, 1500)

                }

                if (isMark){
                    //
                    Timer().schedule(timerTask {
                        val list = nodeInfo.findAccessibilityNodeInfosByViewId("zanyouninesix.say:id/iv_banner")
                        if (null != list && list.isNotEmpty()) {
                            list[0].performAction(AccessibilityNodeInfo.ACTION_FOCUS)
                            list[0].performAction(AccessibilityNodeInfo.ACTION_CLICK)
                            isMark=false
                        }
                    }, 500)
                }

                //点击设置按钮
                if (!isMark && number==1){
                    val list = nodeInfo.findAccessibilityNodeInfosByViewId("zanyouninesix.say:id/iv_setting")
                    if (null != list && list.isNotEmpty()) {
                        list[0].performAction(AccessibilityNodeInfo.ACTION_FOCUS)
                        list[0].performAction(AccessibilityNodeInfo.ACTION_CLICK)
                    }
                }

                //退出登录
                val logout_tv = nodeInfo.findAccessibilityNodeInfosByViewId("zanyouninesix.say:id/logout_tv")
                if (null != logout_tv && logout_tv.isNotEmpty()) {
                    logout_tv[0].performAction(AccessibilityNodeInfo.ACTION_FOCUS)
                    logout_tv[0].performAction(AccessibilityNodeInfo.ACTION_CLICK)
                }


                //优量汇 激励视频页面
                if (event.className.contains("com.qq.e.ads.RewardvideoPortraitADActivity") || event.className.contains("com.qq.e.ads.PortraitADActivity") ){
                    //调用返回键
                    Timer().schedule(timerTask {
//                        performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK)

                        val list = nodeInfo.findAccessibilityNodeInfosByViewId("android:id/content")
                        if (null != list && list.isNotEmpty()) {
                            for (info in list) {
                                if ("android.widget.FrameLayout".equals(info.className)){
                                    for (index in 0..info.childCount) {
                                        if (info.getChild(index).className.equals("android.widget.ImageView")){
                                            info.getChild(index).performAction(AccessibilityNodeInfo.ACTION_FOCUS)
                                            info.getChild(index).performAction(AccessibilityNodeInfo.ACTION_CLICK)
                                            break
                                        }
                                    }
                                    break
                                }
                            }
                        }

                        number++

                    }, 60000)
                }

                //sigmob 激励视频页面
                if (event.className.contains("com.sigmob.sdk.base.common.AdActivity")){
                    //调用返回键
                    Timer().schedule(timerTask {
                        val list = nodeInfo.findAccessibilityNodeInfosByText("关闭按钮")
//                        val list = nodeInfo.findAccessibilityNodeInfosByViewId("app")
                        Log.e("onAccessibilityEvent","sigmob==="+list.toString())
                        if (null != list && list.isNotEmpty()) {
                            list[0].performAction(AccessibilityNodeInfo.ACTION_FOCUS)
                            list[0].performAction(AccessibilityNodeInfo.ACTION_CLICK)
                        }

                        number++
                    }, 60000)
                    Log.e("onAccessibilityEvent","sigmob===")
                }

                //百度 激励视频页面
                if (event.className.contains("com.baidu.mobads.sdk.api.MobRewardVideoActivity")){

                    //调用返回键
                    Timer().schedule(timerTask {
                        val list = nodeInfo.findAccessibilityNodeInfosByViewId("android:id/content")
                        if (null != list && list.isNotEmpty()) {
                            for (info in list) {
                                if ("android.widget.FrameLayout".equals(info.className)){
                                    for (index in 0..info.childCount) {
                                        if (info.getChild(index).className.equals("android.widget.ImageView")){
                                            info.getChild(index).performAction(AccessibilityNodeInfo.ACTION_FOCUS)
                                            info.getChild(index).performAction(AccessibilityNodeInfo.ACTION_CLICK)
                                            break
                                        }
                                    }
                                    break
                                }
                            }
                        }

                        number++

                    }, 60000)


                }

                //穿山甲 激励视频页面
                if (event.className.contains("com.bytedance.sdk.openadsdk.stub.activity.Stub_Standard_Portrait_Activity")){

                    //调用返回键
                    Timer().schedule(timerTask {
                        val list = nodeInfo.findAccessibilityNodeInfosByViewId("android:id/content")
                        if (null != list && list.isNotEmpty()) {
                            for (info in list) {
                                if ("android.widget.FrameLayout".equals(info.className)){
                                    for (index in 0..info.childCount) {
                                        if (info.getChild(index).className.equals("android.widget.ImageView")){
                                            info.getChild(index).performAction(AccessibilityNodeInfo.ACTION_FOCUS)
                                            info.getChild(index).performAction(AccessibilityNodeInfo.ACTION_CLICK)
                                            break
                                        }
                                    }
                                    break
                                }
                            }
                        }

                        number++

                    }, 60000)

                }


                val list2 = nodeInfo.findAccessibilityNodeInfosByViewId("zanyouninesix.say:id/adDurationCloseTv")
                //关闭弹窗
                if (null != list2 && list2.isNotEmpty()) {
                    for (info in list2) {
                        //找到你的节点以后 就直接点击他就行了
                        Timer().schedule(timerTask {
                            info.performAction(AccessibilityNodeInfo.ACTION_FOCUS)
                            info.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                        }, 1000)
                    }
                }

//                val list3 = nodeInfo.findAccessibilityNodeInfosByViewId("zanyouninesix.say:id/deposit_read_close")
//                //关闭弹窗
//                if (null != list3 && list3.isNotEmpty()) {
//                    for (info in list3) {
//                        //找到你的节点以后 就直接点击他就行了
//                        Timer().schedule(timerTask {
//                            info.performAction(AccessibilityNodeInfo.ACTION_FOCUS)
//                            info.performAction(AccessibilityNodeInfo.ACTION_CLICK)
//                        }, 2000)
//                        Log.e("onAccessibilityEvent","关闭===")
//                    }
//                }





//                val listtp = rootInActiveWindow.findAccessibilityNodeInfosByViewId("android:id/content")
//
//                Log.e("onAccessibilityEvent","333=="+listtp.get(0).getChild(0).getChild(0).getChild(0).getChild(0).getChild(0).getChild(0).getChild(0)
//                    //第二节
//                    .getChild(1).getChild(0).getChild(1).toString())
//
//                try {
//                    val info=listtp.get(0).getChild(0).getChild(0).getChild(0).getChild(0).getChild(0).getChild(0).getChild(0)
//                        //android.widget.ScrollView
//                        .getChild(1).getChild(0).getChild(1)
//                    info.performAction(AccessibilityNodeInfo.ACTION_FOCUS)
//                    info.performAction(AccessibilityNodeInfo.ACTION_CLICK)
//                }catch (e:Exception){
//
//                }

            }

            AccessibilityEvent.TYPE_VIEW_CLICKED -> {
                val nodeInfo = rootInActiveWindow
                val list = nodeInfo.findAccessibilityNodeInfosByViewId("zanyouninesix.say:id/fans_activation_submit")
                if (null != list && list.isNotEmpty()) {
                    for (info in list) {
                        if (info.getText().toString().equals("去续期")) {
                            //找到你的节点以后 就直接点击他就行了
                            Timer().schedule(timerTask {
                                info.performAction(AccessibilityNodeInfo.ACTION_FOCUS)
                                info.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                            }, 2000)
                        }
                    }
                }
            }

            AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED -> {
                val nodeInfo = rootInActiveWindow
                val list2 = nodeInfo.findAccessibilityNodeInfosByViewId("zanyouninesix.say:id/adDurationCloseTv")
                //关闭弹窗
                if (null != list2 && list2.isNotEmpty()) {
                    for (info in list2) {
                        //找到你的节点以后 就直接点击他就行了
                        Timer().schedule(timerTask {
                            info.performAction(AccessibilityNodeInfo.ACTION_FOCUS)
                            info.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                        }, 2000)
                        Log.e("onAccessibilityEvent","关闭===")
                    }
                }

//                val list1 = nodeInfo.findAccessibilityNodeInfosByViewId("zanyouninesix.say:id/deposit_read_close")
//                //关闭弹窗
//                if (null != list1 && list1.isNotEmpty()) {
//                    for (info in list1) {
//                        //找到你的节点以后 就直接点击他就行了
//                        Timer().schedule(timerTask {
//                            info.performAction(AccessibilityNodeInfo.ACTION_FOCUS)
//                            info.performAction(AccessibilityNodeInfo.ACTION_CLICK)
//                        }, 2000)
//                        Log.e("onAccessibilityEvent","关闭===")
//                    }
//                }


            }
        }

    }

    override fun onInterrupt() {

    }

    companion object{
        fun isAccessibilityServiceSettingOn(mContext: Context): Boolean {
            var accessibilityEnabled = 0
            val service = mContext.packageName + "/" + AccessibilityServiceTest::class.java.getCanonicalName()
//        Log.d(AccessibilityServiceTest.TAG, service)
            try {
                accessibilityEnabled = Settings.Secure.getInt(
                    mContext.applicationContext.contentResolver,
                    Settings.Secure.ACCESSIBILITY_ENABLED
                )
//            Log.d(
//                com.shuakuaishou3.AccessibilityServiceTest.TAG,
//                "accessibilityEnabled = $accessibilityEnabled"
//            )
            } catch (e: SettingNotFoundException) {
//            Log.e(
//                com.shuakuaishou3.AccessibilityServiceTest.TAG,
//                "Error finding setting, default accessibility to not found: "
//                        + e.message
//            )
            }
            val mStringColonSplitter = SimpleStringSplitter(':')
            if (accessibilityEnabled == 1) {
//            Log.d(
//                com.shuakuaishou3.AccessibilityServiceTest.TAG,
//                "***ACCESSIBILITY IS ENABLED*** -----------------"
//            )
                val settingValue = Settings.Secure.getString(
                    mContext.applicationContext.contentResolver,
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
                )
                if (settingValue != null) {
                    mStringColonSplitter.setString(settingValue)
                    while (mStringColonSplitter.hasNext()) {
                        val accessibilityService = mStringColonSplitter.next()
//                    Log.d(
//                        com.shuakuaishou3.AccessibilityServiceTest.TAG,
//                        "-------------- > accessibilityService :: $accessibilityService $service"
//                    )
                        if (accessibilityService.equals(service, ignoreCase = true)) {
//                        Log.d(
//                            com.shuakuaishou3.AccessibilityServiceTest.TAG,
//                            "We've found the correct setting - accessibility is switched on!"
//                        )
                            return true
                        }
                    }
                }
            } else {
//            Log.d(com.shuakuaishou3.AccessibilityServiceTest.TAG, "***ACCESSIBILITY IS DISABLED***")
            }
            return false
        }
    }




    override fun onKeyEvent(event: KeyEvent?): Boolean {
        Log.e("onAccessibilityEvent","onKeyEvent===")
//        performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK)
//        return false
        if(event!!.getKeyCode()==KeyEvent.KEYCODE_HOME)
            return true
        else
            return super.onKeyEvent(event);
    }




}