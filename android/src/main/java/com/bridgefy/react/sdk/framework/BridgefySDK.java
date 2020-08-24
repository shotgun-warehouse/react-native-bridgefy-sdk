package com.bridgefy.react.sdk.framework;

import com.bridgefy.react.sdk.utils.BridgefyEvent;
import com.bridgefy.react.sdk.utils.Utils;
import com.bridgefy.sdk.client.Bridgefy;
import com.bridgefy.sdk.client.BridgefyClient;
import com.bridgefy.sdk.client.Message;
import com.bridgefy.sdk.client.RegistrationListener;
// import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactContext;

import android.util.Log;

/**
 * @author kekoyde on 6/9/17.
 */

public class BridgefySDK extends RegistrationListener{

    private ReactContext reactContext;
    private Promise initializePromise;

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

    public void initialize(String apiKey, Promise promise)
    {
        this.initializePromise = promise;
        // this.errorRegisterCallback = error;
        // this.successRegisterCallback = success;
        Utils.onEventOccurred(reactContext, BridgefyEvent.BFEventStartWaiting.getValue(), "Waiting for online validation to start the transmitter.");
        Bridgefy.initialize(reactContext.getApplicationContext(), apiKey, this);
    }

    public void startSDK(Promise promise){
        Config.Builder builder = new Config.Builder();

        builder.setAutoConnect(true);                                      // Determinate on-demand / auto connect
        // builder.setEngineProfile(BFEngineProfile.BFConfigProfileLongReach)  // Engine Profile
        builder.setEnergyProfile(BFEnergyProfile.HIGH_PERFORMANCE);          // Energy Profile 
        builder.setEncryption(false); 

        Bridgefy.start(
            new BridgefyMessages(reactContext),
            new BridgefyDevices(reactContext, promise),
            builder.build()
        );
    }

    public void stopSDK() {
        Bridgefy.stop();
    }

    @Override
    public void onRegistrationSuccessful(BridgefyClient bridgefyClient) {
        // successRegisterCallback.invoke(Utils.getBridgefyClient(bridgefyClient));
        this.initializePromise.resolve(Utils.getBridgefyClient(bridgefyClient));
    }

    @Override
    public void onRegistrationFailed(int errorCode, String message) {
        // errorRegisterCallback.invoke(errorCode, message);
        this.initializePromise.reject(String.valueOf(errorCode), message);
    }
}
