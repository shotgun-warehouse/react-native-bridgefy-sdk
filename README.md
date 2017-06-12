## React Native Android Library Bridgefy
Import Bridgefy React Native modules that can be installed through NPM and easily be used in production.

## Getting started
1. Clone the project
2. Modify/Build the Project in Android Studio
    * Start `Android Studio` and select `File -> New -> Import Project` and select the **android** folder of this package.
    * If you get a `Plugin with id 'android-library' not found` Error, install `android support repository`.
    * If you get asked to upgrade _gradle_ to a new version, you can skip it.

## Installing it as a library in your main project
There are many ways to do this, here's the way I do it:

1. Push it to **GitHub**.
2. Do `npm install --save git+https://github.com/kekoyde/react-native-bridgefy-sdk.git` in your main project.
3. Link the library:
    * Add the following to `android/settings.gradle`:
        ```
        include ':react-native-bridgefy-sdk'
        project(':react-native-bridgefy-sdk').projectDir = new File(settingsDir, '../node_modules/react-native-bridgefy-sdk/android')
        ```

    * Add the following to `android/app/build.gradle`:
        ```xml
        ...

        repositories {
        ...
            maven {
                url "http://maven.bridgefy.com/artifactory/libs-release-local"
                artifactUrls = ["http://jcenter.bintray.com/"]
            }
        }


        dependencies {
            ...
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
                    new BridgefySdkPackage()     // add this for react-native-bridgefy-sdk
                );
            }
        }
        ```
4. Simply `import/require` it by the name defined in your library's `index.android.js`:

    ```javascript
    import BridgefySDK from 'react-native-bridgefy-sdk'

    import {
        AppRegistry,
        StyleSheet,
        Text,
        View,
        DeviceEventEmitter
    } from 'react-native';

    ```
4. Register Bridgefy
    ```javascript

    BridgefySDK.init("BRIDGEFY_APY_KEY", 
    (errorCode, message)=>{
      console.log(message + ":" + errorCode);
    },
    (client) => {
      console.log(client);
    }
    );

    ```
5. Start Bridgefy SDK
    ```javascript

    ...

    BridgefySDK.start();

    ...

    ```
6. Send messages
    ```javascript
    ...

    var message = {
                    content:{ // Custom content
                            message:"Hello world!!"
                          },
                    sender_id:userID,
                    receiver_id:device.UserId
                  };

    // Direct Message
        BridgefySDK.sendMessage(message);

    // Broadcast Message
        BridgefySDK.sendBroadcastMessage(message);

    ...

    ```
7. Message and Device listener
    ```javascript

    /*
    * BridgefyMessageListener
    */

    DeviceEventEmitter.addListener('onMessageReceived', (message)=> {
                        console.log('onMessageReceived: '+ message.Content.message);
                      }
    );

    DeviceEventEmitter.addListener('onMessageSent', (message)=> {
                        console.log('onMessageSent: '+ message);
                      }
    );

    DeviceEventEmitter.addListener('onMessageReceivedException', (error)=> {
                        console.log('onMessageReceivedException: '+ error);
                      }
    );

    DeviceEventEmitter.addListener('onMessageFailed', (error)=> {
                        console.log('onMessageFailed: '+ error);
                      }
    );

    DeviceEventEmitter.addListener('onBroadcastMessageReceived', (message)=> {
                        console.log('onBroadcastMessageReceived: '+ message.Content.message);
                      }
    );

    /*
    * BridgefyStateListener
    */    
    DeviceEventEmitter.addListener('onStarted', (device)=> {
                        console.log('onStarted: '+ device);
                      }
    );

    DeviceEventEmitter.addListener('onStartError', (error)=> {
                        console.log('onStartError: '+ error);
                      }
    );

    DeviceEventEmitter.addListener('onStopped', ()=> {
                        console.log('onStopped');
                      }
    );

    DeviceEventEmitter.addListener('onDeviceConnected', (device)=> {
                        console.log('onDeviceConnected: ' + device);
                      }
    );

    DeviceEventEmitter.addListener('onDeviceLost', (device)=> {
                        console.log('onDeviceLost: ' + device);
                      }
    );

    ```

8. You can test and develop your library by importing the `node_modules` library into **Android Studio** if you don't want to install it from _git_ all the time.