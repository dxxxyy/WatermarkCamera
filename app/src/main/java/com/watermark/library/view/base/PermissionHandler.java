package com.watermark.library.view.base;

/**
 * 权限回调接口
 */
public abstract class PermissionHandler {
    /**
     * 权限通过
     */
    public abstract void onGranted();

    /**
     * 权限拒绝
     */
    public void onDenied() {
    }
}
