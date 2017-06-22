## React Native Android Library Bridgefy
Import Bridgefy React Native modules that can be installed through NPM and easily be used in production.

## Installing it as a library in your main project
There are many ways to do this, here's the way I do it:

*1. Push it to **Repository**.*

*2. Do `npm install --save npm install --save git+ssh://git@bitbucket.org/bridgefy/react-native-bridgefy-sdk.git` in your main project.*

*3. Link the library:*

 * Add the following to `android/settings.gradle`:
```xml
include ':react-native-bridgefy-sdk'
project(':react-native-bridgefy-sdk').projectDir = new File(settingsDir, '../node_modules/react-native-bridgefy-sdk/android'
```
 * Add the following to `android/app/build.gradle`:

```xml
 repositories {
             maven {
                 url "http://maven.bridgefy.com/artifactory/libs-release-local"
                 artifactUrls = ["http://jcenter.bintray.com/"]
             }
 }
 dependencies {
                compile project(':react-native-bridgefy-sdk')
}
```

 * Add the following to `android/app/src/main/java/**/MainApplication.java`:

```java
  package com.your.package;
  import com.bridgefy.react.sdk.BridgefySdkPackage;  // add this for react-native-bridgefy-sdk
  public class MainApplication extends Application implements ReactApplication {
  @Override
  protected List<ReactPackage> getPackages() {
            return Arrays.<ReactPackage>asList(
                  new MainReactPackage(),
                  new BridgefySdkPackage() // add this for react-native-bridgefy-sdk
            );
          }
  }
```

*4. Simply `import/require` it by the name defined in your library's `index.android.js`:*

```javascript
  import Bridgefy from 'react-native-bridgefy-sdk'

  import {
      ...
      DeviceEventEmitter,
    } from 'react-native';
```

*5. Register Bridgefy*

```javascript
  Bridgefy.init("BRIDGEFY_APY_KEY", 
    (errorCode, message)=>{
                console.log(message + ":" + errorCode);
                },
    (client) => {
                console.log(client);
                }
    );
```

*6. Start Bridgefy SDK*

```javascript
BridgefySDK.start();
```

*7. Send messages*

```javascript
  var message = {
                 content:{ // Custom content
                          message:"Hello world!!"
                 },
                 sender_id: client.UserUuid, // Client id of Bridgefy
                 receiver_id:device.UserId,  // Client id of Bridgefy to deliver messages
               };
 // Direct Message
 Bridgefy.sendMessage(message);
 // Broadcast Message
 Bridgefy.sendBroadcastMessage(message);
```

*8. Message and Device listener*

```javascript
//
// BridgefyMessageListener
//
 DeviceEventEmitter.addListener('onMessageReceived', (message)=> {
              console.log('onMessageReceived: '+ JSON.stringify(message));
      }
);
 DeviceEventEmitter.addListener('onMessageSent', (message)=> {
             console.log('onMessageSent: '+ JSON.stringify(message));
      }
 );
 DeviceEventEmitter.addListener('onMessageReceivedException', (error)=> {
               console.log('onMessageReceivedException: '+ error);
               console.log('sender: ' + error.sender); // User ID of the sender
               console.log('code: ' + error.conde); // error code
               console.log('message' + error.message); // message object empty
               console.log('description' + error.description); // Error cause
      }
 );
 DeviceEventEmitter.addListener('onMessageFailed', (error)=> {
              console.log('onMessageFailed: '+ error);
              console.log('code: ' + error.conde); // error code
              console.log('message' + error.message); // message object
              console.log('description' + error.description); // Error cause
     }
 );
 DeviceEventEmitter.addListener('onBroadcastMessageReceived', (message)=> {
             console.log('onBroadcastMessageReceived: '+ JSON.stringify(message));
     }
 );
  //
  // BridgefyStateListener
  //
  DeviceEventEmitter.addListener('onStarted', (device)=> {
             console.log('onStarted: '+ JSON.stringify(device));
  }
 );
  DeviceEventEmitter.addListener('onStartError', (error)=> {
             console.log('onStartError: '+ error);
             console.log('code: ' + error.conde); // error code
             console.log('message' + error.message); // message object empty
             console.log('description' + error.description); // Error cause 
       }
 );
  DeviceEventEmitter.addListener('onStopped', ()=> {
             console.log('onStopped');
       }
 );
  DeviceEventEmitter.addListener('onDeviceConnected', (device)=> {
             console.log('onDeviceConnected: ' + JSON.stringify(device));
      }
 );
  DeviceEventEmitter.addListener('onDeviceLost', (device)=> {
             console.log('onDeviceLost: ' + device);
     }
 );
```

*9. You can test and develop your library by importing the `node_modules` library into **Android Studio** if you don't want to install it from _git_ all the time.*