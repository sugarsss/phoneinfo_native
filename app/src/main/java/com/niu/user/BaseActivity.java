package com.niu.user;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;

import com.niu.user.tools.PermissionUtil;

/**
 * Created by niu on 2017/7/1.
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 检查权限
     *
     * @param permission
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean checkPermission(String permission) {
        boolean b = PermissionUtil.checkPermissionWrapper(this, permission);
        return b;
    }

    /**
     * 申请权限
     *
     * @param permission
     * @param requestCode
     */
    public void requestPerssion(String[] permission, int requestCode) {
        PermissionUtil.requestPermissionsWrapper(this, permission, requestCode);
    }
}
