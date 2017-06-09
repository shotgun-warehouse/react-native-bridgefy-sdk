# react-native-bridgefy-sdk

## Getting started

`$ npm install react-native-bridgefy-sdk --save`

### Mostly automatic installation

`$ react-native link react-native-bridgefy-sdk`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-sdk` and add `BridgefySdk.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libBridgefySdk.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.bridgefy.react.sdk.BridgefySdkPackage;` to the imports at the top of the file
  - Add `new BridgefySdkPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-bridgefy-sdk'
  	project(':react-native-bridgefy-sdk').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-bridgefy-sdk/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-bridgefy-sdk')
  	```


## Usage
```javascript
import BridgefySdk from 'react-native-bridgefy-sdk';

// TODO: What to do with the module?
BridgefySdk;
```
