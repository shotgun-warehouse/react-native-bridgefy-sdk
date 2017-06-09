
package com.bridgefy.react.sdk;

import com.bridgefy.react.sdk.framework.BridgefySDK;
import com.bridgefy.sdk.client.Message;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class BridgefyModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;
  private BridgefySDK bridgefySDK;

  public BridgefyModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
    bridgefySDK = new BridgefySDK(reactContext);
  }

  @Override
  public String getName() {
    return "BridgefySdk";
  }

  @ReactMethod
  public void init(String apiKey, Callback errorCallback, Callback successCallback)
  {
    bridgefySDK.initialize(apiKey,  errorCallback, successCallback);
  }

  @ReactMethod
  public void start(Callback errorCallback, Callback successCallback)
  {
    bridgefySDK.startSDK(errorCallback, successCallback);
  }

  @ReactMethod
  public void sendMessage(Message message)
  {
    bridgefySDK.sendMessage(message);
  }

  @ReactMethod
  public void sendBroadcastMesssage(Message message)
  {
    bridgefySDK.sendBroadcastMessage(message);
  }

}