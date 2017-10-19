package com.tsutsuku.artcollection.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.tsutsuku.artcollection.BuildConfig;


/**
 * @Author Sun Renwei
 * @Create 2016/11/29
 * @Description 跳转到应用详情
 */

public class AppManagerUtils {
    public static void openAppPermission(Context context){
        gotoMiuiPermission(context);
    }

    private static void gotoMiuiPermission(Context context){
        Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
        ComponentName name = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
        intent.setComponent(name);
        intent.putExtra("extra_pkgname", BuildConfig.APPLICATION_ID);
        try {
            context.startActivity(intent);
        } catch (Exception e){
            gotoMeizuPermission(context);
        }
    }

    private static void gotoMeizuPermission(Context context){
        Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
        try {
            context.startActivity(intent);
        } catch (Exception e){
            gotoHuaweiPermission(context);
        }
    }

    private static void gotoHuaweiPermission(Context context) {
        Intent intent = new Intent().setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ComponentName name = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmaganger.ui.MainActivity");
        intent.setComponent(name);
        try {
            context.startActivity(intent);
        } catch (Exception e){
            openPermissionNormal(context);
        }
    }

    private static void openPermissionNormal(Context context){
        Intent intent = new Intent().setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts("package", BuildConfig.APPLICATION_ID, null));
        try {
            context.startActivity(intent);
        } catch (Exception e){

        }
    }
}
