package com.ypf.permissiondemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        Button callPhone = (Button) findViewById(R.id.callPhone_bt);
        assert callPhone != null;
        callPhone.setOnClickListener(this);
    }

    private void callSomeone() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + "10086");
        intent.setData(data);
        startActivity(intent);
    }
    public void testCall(){
        //检测权限是否被授予
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            /**
             *  返回DENIED申请权限
             *  第一个参数是Context
             *  第二个参数需要申请权限的字符串数组
             *  第三个参数是requestCode，主要用于回调时的检测，多参数的时候定位用
             */
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
        }else {
            callSomeone();
        }
    }

    /**
     * 处理权限申请回调
     * @param requestCode   定位到自己的申请
     * @param permissions   对应申请的权限字符串数组
     * @param grantResults  申请几个权限数组就为多少，对应权限申请的结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                callSomeone();
            }else {
                //被拒绝
                Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.callPhone_bt:
                testCall();
                break;
        }
    }
}
