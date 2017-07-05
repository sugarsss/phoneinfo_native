package com.niu.user.tools;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by niu on 2017/6/14.
 * 权限检查工具类
 * 注意判断api>=23
 */

public class PermissionUtil {

    /**
     * 申请权限
     *
     * @param cxt         为Activity或者Fragment
     * @param permission  权限
     * @param requestCode
     */
    public static void requestPermissionsWrapper(Object cxt, String[] permission, int requestCode) {
        if (cxt instanceof Activity) {
            Activity activity = (Activity) cxt;
            ActivityCompat.requestPermissions(activity, permission, requestCode);
//             Toast.makeText(activity, "avtivity", Toast.LENGTH_SHORT).show();
        } else if (cxt instanceof Fragment) {
            Fragment fragment = (Fragment) cxt;
            fragment.requestPermissions(permission, requestCode);
//             Toast.makeText(fragment.getActivity(), "fragment", Toast.LENGTH_SHORT).show();

        } else {
            throw new RuntimeException("cxt is not a activity or fragment");
        }
    }


    /**
     * 检查权限
     *
     * @param cxt        为Activity或者Fragment
     * @param permission
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean checkPermissionWrapper(Object cxt, String permission) {
        if (cxt instanceof Activity) {
            Activity activity = (Activity) cxt;
            return ActivityCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED;
        } else if (cxt instanceof Fragment) {
            Fragment fragment = (Fragment) cxt;
            return fragment.getActivity().checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
        } else {
            throw new RuntimeException("cxt is not a activity or fragment");
        }
    }

    /**
     * 检查权限组
     *
     * @param cxt
     * @param permission
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static String[] checkPermissionArray(Object cxt, String[] permission) {
        ArrayList<String> permiList = new ArrayList<>();
        for (String p : permission) {
            if (!checkPermissionWrapper(cxt, p)) {
                permiList.add(p);
            }
        }

        return permiList.toArray(new String[permiList.size()]);
    }

    /*回调的
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //已经授权，更新

        } else {
            //点击了不再提示,拒绝权限
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                //跳转到设置界面
//                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                    Uri uri = Uri.fromParts("package", getPackageName(), null);
//                    intent.setData(uri);
//                    startActivityForResult(intent, savePermissionCode);

            } else {
                //拒绝权限

            }
        }
    }*/

}



