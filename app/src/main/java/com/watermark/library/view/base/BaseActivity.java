package com.watermark.library.view.base;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;

import com.zenchn.inspection.library.base.BaseAppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

/**
 * activity基础类
 *
 * @author xuxu
 */
@RuntimePermissions
public abstract class BaseActivity extends BaseAppCompatActivity {

    @Override
    protected void initVariables() {
    }

    private PermissionHandler mHandler;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        BaseActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    //-----------------------------------------------------------
    /**
     * 请求相机权限
     *
     * @param permissionHandler
     */
    public void requestCameraPermission(PermissionHandler permissionHandler) {
        this.mHandler = permissionHandler;
        BaseActivityPermissionsDispatcher.handleCameraPermissionWithCheck(this);
    }


    @NeedsPermission(Manifest.permission.CAMERA)
    void handleCameraPermission() {
        if (mHandler != null)
            mHandler.onGranted();
    }

    @OnPermissionDenied(Manifest.permission.CAMERA)
    void deniedCameraPermission() {
        if (mHandler != null)
            mHandler.onDenied();
    }

    @OnNeverAskAgain(Manifest.permission.CAMERA)
    void OnCameraNeverAskAgain() {
        showDialog("[相机]");
    }

    //-----------------------------------------------------------
    /**
     * 请求电话权限
     *
     * @param permissionHandler
     */
    public void requestCallPermission(PermissionHandler permissionHandler) {
        this.mHandler = permissionHandler;
        BaseActivityPermissionsDispatcher.handleCallPermissionWithCheck(this);
    }


    @NeedsPermission(Manifest.permission.CALL_PHONE)
    void handleCallPermission() {
        if (mHandler != null)
            mHandler.onGranted();
    }

    @OnPermissionDenied(Manifest.permission.CALL_PHONE)
    void deniedCallPermission() {
        if (mHandler != null)
            mHandler.onDenied();
    }

    @OnNeverAskAgain(Manifest.permission.CALL_PHONE)
    void OnCallNeverAskAgain() {
        showDialog("[电话]");
    }

    //-----------------------------------------------------------
    /**
     * 请求存储权限
     *
     * @param permissionHandler
     */
    public void requestStoragePermission(PermissionHandler permissionHandler) {
        this.mHandler = permissionHandler;
        BaseActivityPermissionsDispatcher.handleStoragePermissionWithCheck(this);
    }


    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    void handleStoragePermission() {
        if (mHandler != null)
            mHandler.onGranted();
    }

    @OnPermissionDenied(Manifest.permission.READ_EXTERNAL_STORAGE)
    void deniedStoragePermission() {
        if (mHandler != null)
            mHandler.onDenied();
    }

    @OnNeverAskAgain(Manifest.permission.READ_EXTERNAL_STORAGE)
    void OnStorageNeverAskAgain() {
        showDialog("[存储空间]");
    }

    //-----------------------------------------------------------
    /**
     * 请求位置信息权限
     *
     * @param permissionHandler
     */
    public void requestLocationPermission(PermissionHandler permissionHandler) {
        this.mHandler = permissionHandler;
        BaseActivityPermissionsDispatcher.handleLocationPermissionWithCheck(this);
    }


    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    void handleLocationPermission() {
        if (mHandler != null)
            mHandler.onGranted();
    }

    @OnPermissionDenied(Manifest.permission.ACCESS_FINE_LOCATION)
    void deniedLocationPermission() {
        if (mHandler != null)
            mHandler.onDenied();
    }

    @OnNeverAskAgain(Manifest.permission.ACCESS_FINE_LOCATION)
    void OnLocationNeverAskAgain() {
        showDialog("[位置信息]");
    }

    public void showDialog(String permission) {
        new AlertDialog.Builder(this)
                .setTitle("权限申请")
                .setMessage("在设置-应用-巡检助手-权限中开启" + permission + "权限，以正常使用巡检助手功能")
                .setPositiveButton("去开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mHandler != null) mHandler.onDenied();
                        dialog.dismiss();
                    }
                })
                .setCancelable(false)
                .show();
    }

}
