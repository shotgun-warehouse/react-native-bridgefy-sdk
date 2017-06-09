package com.bridgefy.react.sdk.framework;

import com.bridgefy.sdk.client.Device;
import com.bridgefy.sdk.client.Session;
import com.bridgefy.sdk.client.StateListener;
import com.facebook.react.bridge.Callback;

/**
 * @author kekoyde on 6/9/17.
 */

class BridgefyDevices extends StateListener {
    Callback errorCallback;
    Callback successCallback;

    public BridgefyDevices(Callback errorCallback, Callback successCallback) {
        this.errorCallback = errorCallback;
        this.successCallback = successCallback;
    }

    @Override
    public void onStarted() {
        successCallback.invoke("onStarted");
    }

    @Override
    public void onStartError(String message, int errorCode) {
        errorCallback.invoke("onStartError", message, errorCode);
    }

    @Override
    public void onStopped() {
        successCallback.invoke("onStopped");
    }

    @Override
    public void onDeviceConnected(Device device, Session session) {
        successCallback.invoke("onDeviceConnected", device, session);
    }

    @Override
    public void onDeviceLost(Device device) {
        successCallback.invoke("onDeviceLost", device);
    }
}
