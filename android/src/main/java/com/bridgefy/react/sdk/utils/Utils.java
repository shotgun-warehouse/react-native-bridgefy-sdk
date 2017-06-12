package com.bridgefy.react.sdk.utils;

import com.bridgefy.sdk.client.BridgefyClient;
import com.bridgefy.sdk.client.Device;
import com.bridgefy.sdk.client.DeviceProfile;
import com.bridgefy.sdk.client.Message;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.google.gson.Gson;

/**
 * @author kekoyde on 6/12/17.
 */

public class Utils {
    private Utils(){}

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
        mapDeviceProfile.putInt("DeviceCharacteristicsProfile", deviceProfile.getDeviceCharacteristicsProfile$51c1f94e());
        return mapDeviceProfile;
    }

}
