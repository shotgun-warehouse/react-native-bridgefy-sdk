package com.bridgefy.react.sdk.framework;

import com.bridgefy.react.sdk.utils.Utils;
import com.bridgefy.sdk.client.Bridgefy;
import com.bridgefy.sdk.client.BridgefyClient;
import com.bridgefy.sdk.client.Message;
import com.bridgefy.sdk.client.RegistrationListener;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactContext;

/**
 * @author kekoyde on 6/9/17.
 */

public class BridgefySDK extends RegistrationListener{

    private ReactContext reactContext;
    private Callback errorRegisterCallback;
    private Callback successRegisterCallback;

    public BridgefySDK(ReactContext reactContext){
        this.reactContext = reactContext;
    }

    public void sendMessage(Message message)
    {
        Bridgefy.sendMessage(message);
    }

    public void sendBroadcastMessage(Message message)
    {
        Bridgefy.sendBroadcastMessage(message);
    }

    public void initialize(String apiKey, Callback error, Callback success)
    {
        Bridgefy.initialize(reactContext.getApplicationContext(), apiKey, this);
        this.errorRegisterCallback = error;
        this.successRegisterCallback = success;
    }


    public void startSDK(Callback errorCallback, Callback successCallback){
        Bridgefy.start(new BridgefyMessages(errorCallback, successCallback), new BridgefyDevices(errorCallback, successCallback));
    }



    @Override
    public void onRegistrationSuccessful(BridgefyClient bridgefyClient) {
        successRegisterCallback.invoke(Utils.getBridgefyClient(bridgefyClient));
    }

    @Override
    public void onRegistrationFailed(int errorCode, String message) {
        errorRegisterCallback.invoke(errorCode, message);
    }
}
