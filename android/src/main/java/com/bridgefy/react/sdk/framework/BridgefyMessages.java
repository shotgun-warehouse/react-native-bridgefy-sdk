package com.bridgefy.react.sdk.framework;

import com.bridgefy.sdk.client.Message;
import com.bridgefy.sdk.client.MessageListener;
import com.bridgefy.sdk.framework.exceptions.MessageException;
import com.facebook.react.bridge.Callback;

/**
 * @author kekoyde on 6/9/17.
 */

class BridgefyMessages extends MessageListener {

    private Callback messageCallback;
    private Callback errorCallback;

    public BridgefyMessages(Callback messageCallback, Callback errorCallback) {
        this.messageCallback = messageCallback;
        this.errorCallback = errorCallback;
    }

    @Override
    public void onMessageReceived(Message message) {
        messageCallback.invoke("onMessageReceived", message);
    }

    @Override
    public void onMessageSent(Message message) {
        messageCallback.invoke("onMessageSent", message);
    }

    @Override
    public void onMessageReceivedException(String sender, MessageException e) {
        errorCallback.invoke("onMessageReceivedException", sender, e.getMessage());
    }

    @Override
    public void onMessageFailed(Message message, MessageException e) {
        errorCallback.invoke("onMessageFailed", message, e.getMessage());
    }

    @Override
    public void onBroadcastMessageReceived(Message message) {
        messageCallback.invoke("onBroadcastMessageReceived", message);
    }
}
