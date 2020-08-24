
package com.bridgefy.react.sdk;

import com.bridgefy.react.sdk.framework.BridgefySDK;
import com.bridgefy.react.sdk.utils.Utils;
// import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;

import com.facebook.react.bridge.Promise;

import android.util.Log;

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
    return "RNBridgefy";
  }

  @ReactMethod
  public void init(String apiKey, Promise promise)
  {
    bridgefySDK.initialize(apiKey, promise);
  }

  @ReactMethod
  public void start(Promise promise)
  {
    bridgefySDK.startSDK(promise);

    // FIXME, MUST BE A PROMISE
  }

  @ReactMethod
  public void stop()
  {
    bridgefySDK.stopSDK();
  }

  @ReactMethod
  public void sendMessage(ReadableMap message)
  {
    bridgefySDK.sendMessage(Utils.getMessageFromMap(message));
  }

  @ReactMethod
  public void sendBroadcastMessage(ReadableMap message)
  {
    bridgefySDK.sendBroadcastMessage(Utils.getMessageFromMap(message));
  }

}