# React Native interface for Bridgefy
This repository contains a module for [React Native](https://facebook.github.io/react-native/) that is an interface to use [Bridgefy SDK](https://www.bridgefy.me/), this interface can be used for Android and iOS projects. If you want to know how to use the framework natively in android, you can find it [here](https://github.com/bridgefy/bridgefy-android-samples/blob/master/README.md), in the other hand the official iOS repository is [here](https://bitbucket.org/bridgefy/bridgefy-ios-dist).

## Install on existing project

**Note: **This section explains how to add Bridgefy to an existing project with native code. If you don't know how to create a Native React project with native code, you can check it [here](https://facebook.github.io/react-native/docs/getting-started.html), under the tab **Building Projects with Native Code**.  

Let's suppose your project name is `AwesomeProject`, go to the root directory of the project and run the following command:

```
npm install --save npm install --save git+ssh://git@bitbucket.org/bridgefy/react-native-bridgefy-sdk.git
```

It will download and install the bridgefy module, don't forget the parameter `--save` if you want to save the dependency in your `package.json`, so you can install/update Bridgefy easier in the future.  
At this point you already have the module, but in order to be able to use it, you will need to make some configurations for every platform.

### Android install

First, open the project in Android Studio, this is located in `AwesomeProject/android`.  
<br>
Once the project is open, you will need to indicate where the module is installed, to do this open the file  `android/settings.gradle` and add the following code:
```xml
include ':react-native-bridgefy-sdk'
project(':react-native-bridgefy-sdk').projectDir = new File(settingsDir, '../node_modules/react-native-bridgefy-sdk/android'
```
After this you will need to indicate the maven repository to download the native SDK and add the React Native interface as a dependency. To do this open the file `android/app/build.gradle` and add the followind code:

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

As final step, open the main activuty (`android/app/src/main/java/**/MainApplication.java`) and add the following segments of code:

```java
  ...
  // Import the module
  import com.bridgefy.react.sdk.BridgefySdkPackage;
  ...
  // Add this method in the MainApplication class to indicate the packages to use.
  @Override
  protected List<ReactPackage> getPackages() {
            return Arrays.<ReactPackage>asList(
                  new MainReactPackage(),
                  new BridgefySdkPackage() // add this for react-native-bridgefy-sdk
            );
          }
  ...
```

### iOS Install

First, go to the official [Bridgefy iOS repository](https://bitbucket.org/bridgefy/bridgefy-ios-dist) to download the last version of `BFTransmitter.framewok`.

Once you have the framework file, move to `AwesomeProject/ios` and copy there the downloaded file.

Next, move to the root directory (`AwesomeProject`) and run there the following command to link the interface module to the project:

```
react-native link
````
Open the XCode project, you will need to add `BFTransmitter.framework` to "Embedded binaries", you can do this by dragging the file like is shown in the following image:  
PENDING IMAGE

Finally, select a Development team for the target `AwesomeProject` and  `AwesomeProjectTests` like is shown in the following image:  
PENDING IMAGE

## Usage

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