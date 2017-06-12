package com.bridgefy.react.sdk.utils;

import android.support.annotation.Nullable;

import com.bridgefy.sdk.client.BridgefyClient;
import com.bridgefy.sdk.client.Device;
import com.bridgefy.sdk.client.DeviceProfile;
import com.bridgefy.sdk.client.Message;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author kekoyde on 6/12/17.
 */

public class Utils {
    private Utils(){}

    private static final String CONTENT = "content", RECEIVER_ID = "receiver_id", SENDER_ID = "sender_id";

    public static synchronized WritableMap getBridgefyClient(BridgefyClient bridgefyClient)
    {
        WritableMap map = new WritableNativeMap();
        map.putString("API_KEY", bridgefyClient.getApiKey());
        map.putString("BundleId",bridgefyClient.getBundleId());
        map.putString("PUBLIC_KEY", bridgefyClient.getPublicKey());
        map.putString("SECRET_KEY", bridgefyClient.getSecretKey());
        map.putString("UserUuid", bridgefyClient.getUserUuid());
        map.putMap("DeviceProfile", getDeviceProfile(bridgefyClient));
        return null;
    }

    public static synchronized WritableMap getMapForMessage(Message message)
    {
        WritableMap mapMessage = new WritableNativeMap();
        mapMessage.putString("ReceiverId", message.getReceiverId());
        mapMessage.putString("SenderId", message.getSenderId());
        mapMessage.putString("Uuid", message.getUuid());
        mapMessage.putDouble("DateSent", message.getDateSent());
        mapMessage.putString("Content", new Gson().toJson(message.getContent()));
        return mapMessage;
    }

    public static synchronized WritableMap getMapForDevice(Device device){
        WritableMap mapDevice = new WritableNativeMap();
        mapDevice.putString("UserId",device.getUserId());
        mapDevice.putString("DeviceAddress",device.getDeviceAddress());
        mapDevice.putString("DeviceName",device.getDeviceName());
        mapDevice.putString("SessionId",device.getSessionId());
        mapDevice.putString("DeviceType",device.getDeviceType().toString());
        mapDevice.putDouble("Crc",device.getCrc());
        mapDevice.putInt("Retries",device.getRetries());
        return mapDevice;
    }

    public static synchronized WritableMap getDeviceProfile(BridgefyClient bridgefyClient)
    {
        DeviceProfile deviceProfile = bridgefyClient.getDeviceProfile();
        WritableMap mapDeviceProfile = new WritableNativeMap();
        mapDeviceProfile.putString("DeviceEvaluation", deviceProfile.getDeviceEvaluation());
        mapDeviceProfile.putInt("Rating", deviceProfile.getRating());
        return mapDeviceProfile;
    }

    public static synchronized void sendEvent(ReactContext reactContext, String eventName, @Nullable WritableMap params) {
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, params);
    }

    public static synchronized Message getMessageFromMap(ReadableMap readableMap)
    {
        HashMap content = recursivelyDeconstructReadableMap(readableMap.getMap(CONTENT));
        Message message = new Message(content, readableMap.getString(RECEIVER_ID), readableMap.getString(SENDER_ID));
        return message;
    }

    private static HashMap<String, Object> recursivelyDeconstructReadableMap(ReadableMap readableMap) {
        ReadableMapKeySetIterator iterator = readableMap.keySetIterator();
        HashMap<String, Object> deconstructedMap = new HashMap<>();
        while (iterator.hasNextKey()) {
            String key = iterator.nextKey();
            ReadableType type = readableMap.getType(key);
            switch (type) {
                case Null:
                    deconstructedMap.put(key, null);
                    break;
                case Boolean:
                    deconstructedMap.put(key, readableMap.getBoolean(key));
                    break;
                case Number:
                    deconstructedMap.put(key, readableMap.getDouble(key));
                    break;
                case String:
                    deconstructedMap.put(key, readableMap.getString(key));
                    break;
                case Map:
                    deconstructedMap.put(key, recursivelyDeconstructReadableMap(readableMap.getMap(key)));
                    break;
                case Array:
                    deconstructedMap.put(key, recursivelyDeconstructReadableArray(readableMap.getArray(key)));
                    break;
                default:
                    throw new IllegalArgumentException("Could not convert object with key: " + key + ".");
            }

        }
        return deconstructedMap;
    }

    private static List<Object> recursivelyDeconstructReadableArray(ReadableArray readableArray) {
        List<Object> deconstructedList = new ArrayList<>(readableArray.size());
        for (int i = 0; i < readableArray.size(); i++) {
            ReadableType indexType = readableArray.getType(i);
            switch(indexType) {
                case Null:
                    deconstructedList.add(i, null);
                    break;
                case Boolean:
                    deconstructedList.add(i, readableArray.getBoolean(i));
                    break;
                case Number:
                    deconstructedList.add(i, readableArray.getDouble(i));
                    break;
                case String:
                    deconstructedList.add(i, readableArray.getString(i));
                    break;
                case Map:
                    deconstructedList.add(i, recursivelyDeconstructReadableMap(readableArray.getMap(i)));
                    break;
                case Array:
                    deconstructedList.add(i, recursivelyDeconstructReadableArray(readableArray.getArray(i)));
                    break;
                default:
                    throw new IllegalArgumentException("Could not convert object at index " + i + ".");
            }
        }
        return deconstructedList;
    }

}
