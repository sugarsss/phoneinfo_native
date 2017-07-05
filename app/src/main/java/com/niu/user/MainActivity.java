package com.niu.user;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.niu.user.entrty.CallLogInfoBean;
import com.niu.user.entrty.ContactBean;
import com.niu.user.entrty.MessageBean;
import com.niu.user.entrty.NativeAppInfoBean;
import com.niu.user.tools.CallLogUtils;
import com.niu.user.tools.ContactUtil;
import com.niu.user.tools.GalleryUtil;
import com.niu.user.tools.MessageUtil;
import com.niu.user.tools.NativeAppUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.btn_get_app_list)
    Button btnGetAppList;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.btn_get_call_log)
    Button btnGetCallLog;
    @BindView(R.id.btn_get_contact)
    Button btnGetContact;
    @BindView(R.id.btn_get_message)
    Button btnGetMessage;
    @BindView(R.id.btn_get_location)
    Button btnGetLocation;

    private MainActivity mActivity;

    private int callLogRequestCode = 1;
    private int contactRequestCode = 2;
    private int smsRequestCode = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActivity = this;
        ButterKnife.bind(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick({R.id.btn_get_app_list, R.id.btn_get_call_log, R.id.btn_get_contact,
            R.id.btn_get_message, R.id.btn_get_location})
    public void onCLick(View v) {
        switch (v.getId()) {
            //读取本机APP安装列表
            case R.id.btn_get_app_list:
                tvContent.setText("");
//                readAppInfo();

                List<String> systemPhotoList = GalleryUtil.getSystemPhotoList(this);
                tvContent.setText(systemPhotoList.toString());

                break;
            //读取本机通话记录
            case R.id.btn_get_call_log:
                tvContent.setText("");

                boolean b = checkPermission(Manifest.permission.READ_CALL_LOG);
                if (b) {
                    readCallLog();

                } else {
                    requestPerssion(new String[]{Manifest.permission.READ_CALL_LOG}, callLogRequestCode);
                }

                break;
            //获取本机通讯录
            case R.id.btn_get_contact:
                tvContent.setText("");
                boolean isReadContact = checkPermission(Manifest.permission.READ_CONTACTS);
                if (isReadContact) {
                    readContact();

                } else {
                    requestPerssion(new String[]{Manifest.permission.READ_CONTACTS}, contactRequestCode);
                }
                break;
            //获取本机短信
            case R.id.btn_get_message:
                tvContent.setText("");
                boolean isReadMessage = checkPermission(Manifest.permission.READ_SMS);
                if (isReadMessage) {
                    readMessage();

                } else {
                    requestPerssion(new String[]{Manifest.permission.READ_SMS}, smsRequestCode);
                }
                break;
            //获取位置
            case R.id.btn_get_location:
                Intent intent = new Intent(mActivity,LocationActivity.class);
                startActivity(intent);

                break;
            default:
                break;
        }
    }

    /**
     * 读取本机安装应用的列表
     */
    private void readAppInfo() {
        try {
            List<NativeAppInfoBean> allApps = NativeAppUtils.getAllApps(mActivity, 0);
            tvContent.setText(allApps.toString());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    /**
     * 读取短信
     */
    private void readMessage() {
        List<MessageBean> smsInPhone = MessageUtil.getSmsInPhone(0);
        tvContent.setText(smsInPhone.toString());
    }

    /**
     * 读取通话记录
     */
    private void readCallLog() {
        List<CallLogInfoBean> callLogInfos = CallLogUtils.getCallLogInfos(mActivity, 0);
        tvContent.setText(callLogInfos.toString());
    }

    /**
     * 读取本机通讯录信息
     *
     * @throws Throwable
     */
    private void readContact() {
        try {
            List<ContactBean> allContact = ContactUtil.getAllContact(mActivity);
            tvContent.setText(allContact.toString());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == callLogRequestCode) {
            readCallLog();
        } else if (requestCode == contactRequestCode) {
            readContact();
        }else if(requestCode == smsRequestCode){
            readMessage();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (callLogRequestCode == requestCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //已经授权
                readCallLog();

            } else {
                //点击了不再提示,拒绝权限
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                    //跳转到设置界面
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivityForResult(intent, callLogRequestCode);

                } else {
                    Toast.makeText(mActivity, "权限拒绝", Toast.LENGTH_SHORT).show();

                }
            }
        } else if (contactRequestCode == requestCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //已经授权
                readContact();

            } else {
                //点击了不再提示,拒绝权限
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                    //跳转到设置界面
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivityForResult(intent, callLogRequestCode);

                } else {
                    Toast.makeText(mActivity, "权限拒绝", Toast.LENGTH_SHORT).show();

                }
            }
        } else if (smsRequestCode == requestCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //已经授权
                readMessage();

            } else {
                //点击了不再提示,拒绝权限
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                    //跳转到设置界面
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivityForResult(intent, callLogRequestCode);

                } else {
                    Toast.makeText(mActivity, "权限拒绝", Toast.LENGTH_SHORT).show();

                }
            }
        }
    }


}
