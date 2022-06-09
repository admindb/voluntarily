package com.lzl.voluntarily;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.graphics.Path;
import android.os.Build;
import android.os.Bundle;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

/**
 * @Description:
 * @Author: lzl
 * @CreateDate: 2022/6/8 13:57
 */
public class AccessibilityOperator {

    private static AccessibilityOperator accessibilityOperator;
    private AccessibilityServiceTest mAccessibilityService;
    private AccessibilityEvent mAccessibilityEvent;
    private boolean isOne = false;
    private boolean isTwo = false;
    private boolean isThree = false;
    private boolean isFour = false;
    private boolean isOk = false;
    private String[] strArr = {"dd互", "666", "拉我", "已进群"};
    private static int SHORT_CLICK = 1;
    private static int LONG_CLICK = 2;

    public static AccessibilityOperator getInstance() {
        if (accessibilityOperator == null) {
            synchronized (AccessibilityOperator.class) {
                if (accessibilityOperator == null) {
                    accessibilityOperator = new AccessibilityOperator();
                }
            }
        }
        return accessibilityOperator;
    }

    //在该方法里操作
    public void updateEvent(AccessibilityServiceTest service, AccessibilityEvent event) {
        mAccessibilityService = service;
        mAccessibilityEvent = event;
        String className = "";
        String packageName = "";
        if (event.getClassName() != null) {
            className = event.getClassName().toString();
        }
        if (event.getPackageName() != null) {
            packageName = event.getPackageName().toString();
        }

        ExtendKt.loge("packageName = " + packageName + "，，，className = " + className);
        switch (packageName) {
            case "cn.xiaochuankeji.tieba":  //最右app
                AccessibilityNodeInfo search = clickId("cn.xiaochuankeji.tieba:id/ivSearch1", "android.widget.ImageView", SHORT_CLICK);
                if (search != null) {
                    isOne = true;
                }
                ExtendKt.loge("ppp-isOne==" + isOne);
                AccessibilityNodeInfo searchContent = null;
                if (isOne) {
                    searchContent = clickId("cn.xiaochuankeji.tieba:id/containerSearchInput", "android.widget.EditText", SHORT_CLICK);
                    if (searchContent != null) {
                        isOne = false;
                        isTwo = true;
                    }
                }
                ExtendKt.loge("ppp-isTwo==" + isTwo);
                if (isTwo) {
//                    changeInput(searchContent, Utils.name);
                    changeInput(searchContent, "Utils.name");
                    clickText("用户", "android.widget.TextView", SHORT_CLICK);
                    isTwo = false;
                    isThree = true;
                }
                ExtendKt.loge("ppp-isThree==" + isThree);
                if (isThree) {
//                    clickText(Utils.name, "android.widget.TextView", SHORT_CLICK);
                    clickText("Utils.name", "android.widget.TextView", SHORT_CLICK);
                    isFour = true;
                }
                ExtendKt.loge("ppp-isFour==" + isFour);
                if (isFour) {
                    AccessibilityNodeInfo pinglun = clickId("cn.xiaochuankeji.tieba:id/option_tv2", "android.widget.TextView", SHORT_CLICK);
                    if (pinglun != null) {
                        isThree = false;
                        isFour = false;
                        isOk = true;
                    }
                }
                ExtendKt.loge("ppp-isOk==" + isOk);
                if (isOk) {
                    threadSleep(500);
                    AccessibilityNodeInfo nodeInfov2 = clickId("cn.xiaochuankeji.tieba:id/etInput", "android.widget.EditText", SHORT_CLICK);
                    if (nodeInfov2 != null) {
                        int index = (int) (Math.random() * strArr.length);
                        changeInput(nodeInfov2, strArr[index]);
                    }
                    clickId("cn.xiaochuankeji.tieba:id/send", "android.widget.TextView", SHORT_CLICK);
                    threadSleep(60000);
                    clickId("cn.xiaochuankeji.tieba:id/tvName", "android.widget.TextView", LONG_CLICK);
                    threadSleep(500);
                    clickText("删除", "android.widget.TextView", SHORT_CLICK);
                    threadSleep(500);
                    clickId("cn.xiaochuankeji.tieba:id/ZYDialog_positive_btn", "android.widget.TextView", SHORT_CLICK);
                }
                break;
        }

    }

    private void threadSleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private AccessibilityNodeInfo clickId(String id, String className, int type) {
        return performClick(findNodesById(id), className, type);
    }

    private AccessibilityNodeInfo clickText(String text, String className, int type) {
        return performClick(findNodesByText(text), className, type);
    }


    //获取根节点
    private AccessibilityNodeInfo getRootNodeInfo() {
        AccessibilityEvent curEvent = mAccessibilityEvent;
        AccessibilityNodeInfo nodeInfo = null;
        if (Build.VERSION.SDK_INT >= 16) {
            if (mAccessibilityService != null) {
                // 获得窗体根节点
                nodeInfo = mAccessibilityService.getRootInActiveWindow();
            }
        } else {
            nodeInfo = curEvent.getSource();
        }
        return nodeInfo;
    }

    //根据内容查找
    private List<AccessibilityNodeInfo> findNodesByText(String text) {
        AccessibilityNodeInfo nodeInfo = getRootNodeInfo();
        if (nodeInfo != null) {
            return nodeInfo.findAccessibilityNodeInfosByText(text);
        }
        return null;
    }

    //根据id查找
    private List<AccessibilityNodeInfo> findNodesById(String viewId) {
        AccessibilityNodeInfo nodeInfo = getRootNodeInfo();
        if (nodeInfo != null) {
            return nodeInfo.findAccessibilityNodeInfosByViewId(viewId);
        }
        return null;
    }

    //模拟点击
    private AccessibilityNodeInfo performClick(List<AccessibilityNodeInfo> nodeList, String
            className, int type) {
        if (nodeList != null && !nodeList.isEmpty()) {
            AccessibilityNodeInfo node;
            for (int i = 0; i < nodeList.size(); i++) {
                node = nodeList.get(i);
                if (node.getClassName().toString().equals(className)) {
                    return isClickState(node, type);
                }
            }
        }
        return null;
    }

    //子类没有就向父类查找可点击view，所有最好点击选择最小颗粒view的id
    private AccessibilityNodeInfo isClickState(AccessibilityNodeInfo node, int type) {
        if (node.isClickable()) {
            // 进行模拟点击
            if (type == SHORT_CLICK) {
                node.performAction(AccessibilityNodeInfo.ACTION_CLICK);

            } else if (type == LONG_CLICK) {
                node.performAction(AccessibilityNodeInfo.ACTION_LONG_CLICK);
            }
            return node;
        } else {
            if (node.getParent() != null) {
                node = node.getParent();
                isClickState(node, type);
            }
        }
        return null;
    }

    private void changeInput(AccessibilityNodeInfo info, String text) {  //改变editText的内容
        Bundle arguments = new Bundle();
        arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, text);
        if (info != null) {
            info.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);
        }
    }

    //坐标点击
    private void clickScreen(int x, int y) {
        Path mPath = new Path();
        mPath.moveTo(x, y);//配置点击坐标
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            GestureDescription.Builder builder = new GestureDescription.Builder();
            //100L 第一个是开始的时间，第二个是持续时间
            GestureDescription description = builder.addStroke(new GestureDescription.StrokeDescription(mPath, 50, 0)).build();

            mAccessibilityService.dispatchGesture(description, new AccessibilityService.GestureResultCallback() {
                @Override
                public void onCompleted(GestureDescription gestureDescription) {
                    super.onCompleted(gestureDescription);
                    ExtendKt.loge("点击成功");
                }

                @Override
                public void onCancelled(GestureDescription gestureDescription) {
                    super.onCancelled(gestureDescription);
                    ExtendKt.loge("点击失败");
                }
            }, null);
        } else {
//            ExtendKt.showShortToast("系统不支持" + Build.VERSION.SDK_INT);
        }
    }

    //仿滑动
    private void clickScroll() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            Path path = new Path();
            path.moveTo(100, 1800);//设置Path的起点
            path.lineTo(100, 0);

            GestureDescription.Builder builder = new GestureDescription.Builder();
            //100L 第一个是开始的时间，第二个是持续时间
            GestureDescription description = builder.addStroke(new GestureDescription.StrokeDescription(path, 0, 1000)).build();

            mAccessibilityService.dispatchGesture(description, new AccessibilityService.GestureResultCallback() {
                @Override
                public void onCompleted(GestureDescription gestureDescription) {
                    super.onCompleted(gestureDescription);
                }

                @Override
                public void onCancelled(GestureDescription gestureDescription) {
                    super.onCancelled(gestureDescription);
                }
            }, null);
        }
    }

    //模拟退出键
    public boolean clickBackKey() {
        return mAccessibilityService.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
    }
}

