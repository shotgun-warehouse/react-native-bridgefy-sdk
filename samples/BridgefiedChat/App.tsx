import React, { useState, useCallback, useEffect } from 'react';
import {
  Alert,
  View,
  Text,
  NativeEventEmitter,
  PermissionsAndroid,
  Platform
} from 'react-native';
import { GiftedChat } from 'react-native-gifted-chat';
import BridgefySdk from 'react-native-bridgefy-sdk';

const bridgefyEmitter = new NativeEventEmitter(BridgefySdk);

var messages;
var setMessages;

var client;
var setClient;

var connected;
var setConnected;

var errors;
var setErrors;

export default function App() {

  const msgState = useState([]);
  messages = msgState[0];
  setMessages = msgState[1];
  const clientState = useState(null);
  client = clientState[0];
  setClient = clientState[1];
  const connectedState = useState(false);
  connected = connectedState[0];
  setConnected = connectedState[1];
  const errorsState = useState([]);
  errors = connectedState[0];
  setErrors = connectedState[1];
  
  let clearListeners = () => {
    bridgefyEmitter.removeAllListeners('onMessageReceived');
    bridgefyEmitter.removeAllListeners('onBroadcastMessageReceived');
    bridgefyEmitter.removeAllListeners('onMessageFailed');
    bridgefyEmitter.removeAllListeners('onMessageSent');
    bridgefyEmitter.removeAllListeners('onMessageReceivedException');
    bridgefyEmitter.removeAllListeners('onStarted');
    bridgefyEmitter.removeAllListeners('onStartError');
    bridgefyEmitter.removeAllListeners('onStopped');
    bridgefyEmitter.removeAllListeners('onDeviceConnected');
    bridgefyEmitter.removeAllListeners('onDeviceLost');
    bridgefyEmitter.removeAllListeners('onEventOccurred');
  }

  let initListeners = () => {
    console.log('INITING THE BRDG RN LISTENERS');
    bridgefyEmitter.addListener('onMessageReceived', (message)=> {
        console.log('onMessageReceived: '+ JSON.stringify(message));
      }
    );
  
    // This event is launched when a broadcast message has been received, the structure 
    // of the dictionary received is explained in the appendix.
    bridgefyEmitter.addListener('onBroadcastMessageReceived', (message)=> {
        console.log('onMessageReceived: '+ JSON.stringify(message));
        if (message.content.message) {
          setMessages(
            GiftedChat.append(
              messages, 
              JSON.parse(message.content.message)
            )
          );
        }
      }
    );
  
    // This event is launched when a message could not be sent, it receives an error
    // whose structure will be explained in the appendix
    bridgefyEmitter.addListener('onMessageFailed', (error)=> {
      console.log('onMessageFailed: '+ error);
        setErrors(errors.concat([error]));
  
        console.log('code: ' + error.conde); // error code
        console.log('message' + error.message); // message object
        console.log('description' + error.description); // Error cause 
  
      }
    );
  
    // This event is launched when a message was sent, contains the message
    // itself, and the structure of message is explained in the appendix.
    bridgefyEmitter.addListener('onMessageSent', (message)=> {
        console.log('onMessageSent: '+ JSON.stringify(message));
      }
    );
  
    // This event is launched when a message was received but it contains errors, 
    // the structure for this kind of error is explained in the appendix.
    // This method is launched exclusively on Android.
    bridgefyEmitter.addListener('onMessageReceivedException', (error)=> {
  
        console.log('onMessageReceivedException: '+ error);
        console.log('sender: ' + error.sender); // User ID of the sender
        console.log('code: ' + error.code); // error code
        console.log('message' + error.message); // message object empty
        console.log('description' + error.description); // Error cause 
  
      }
    );
  
    //
    // Device listeners
    //   
  
    // This event is launched when the service has been started successfully, it receives
    // a device dictionary that will be descripted in the appendix.
    bridgefyEmitter.addListener('onStarted', (device)=> {
        // For now, device is an empty dictionary
        console.log('onStarted');
        setConnected(true);
      }
    );
  
    // This event is launched when the BridgefySdk service fails on the start, it receives
    // a dictionary (error) that will be explained in the appendix.
    bridgefyEmitter.addListener('onStartError', (error)=> {
        console.log('onStartError: ');
        console.log('code: ' + error.code); // error code
        console.log('description: ' + error.description); // Error cause 
      }
    );
  
    // This event is launched when the BridgefySdk service stops.
    bridgefyEmitter.addListener('onStopped', ()=> {
        console.log('onStopped');
        setConnected(false);
      }
    );
  
    // This method is launched when a device is nearby and has established connection with the local user.
    // It receives a device dictionary.
    bridgefyEmitter.addListener('onDeviceConnected', (device)=> {
        // BridgefyClient.deviceList.push(device);
        console.log('onDeviceConnected: ' + JSON.stringify(device));
    }
    );
    // This method is launched when there is a disconnection of a user.
    bridgefyEmitter.addListener('onDeviceLost', (device)=> {
        console.log('onDeviceLost: ' + device);
      }
    );
  
    // This is method is launched exclusively on iOS devices, notifies about certain actions like when
    // the bluetooth interface  needs to be activated, when internet is needed and others.
    bridgefyEmitter.addListener('onEventOccurred', (event)=> {
        console.log('Event code: ' + event.code + ' Description: ' + event.description);
      }
    );
  }

  let initBrdg = () => {
    function doInitBrdg() {
      // initiated = true;
      console.log('BridgefySdk = ',BridgefySdk);
      BridgefySdk.init("370d04e6-5e17-40e9-b68b-264e21381665")
        .then((brdgClient)=>{
          setClient({
            _id: brdgClient.userUuid,
            name: "Broadcast User",
            avatar: 'https://unsplash.it/200/300/?random'
          });
          console.log("Brdg client = ",brdgClient);
          BridgefySdk.start();
        })
        .catch((e)=>{
          console.log(e);
        });
    }
    if (Platform.OS=="android") {
      PermissionsAndroid.requestMultiple(
        [
          PermissionsAndroid.PERMISSIONS.ACCESS_FINE_LOCATION,
          PermissionsAndroid.PERMISSIONS.ACCESS_COARSE_LOCATION,
        ]).then((result) => {
          if (
            result['android.permission.ACCESS_COARSE_LOCATION']||
            result['android.permission.ACCESS_FINE_LOCATION']
          ) {
            doInitBrdg();
          }
          else {
            // TODO
            // reject();
          }
        })
        .catch((e)=>{
          // TODO
          // reject();
        });
    }
    else {
      doInitBrdg();
    }
  }

  let onSend = (brdgMessages = []) => {

    if (!connected) {
      Alert.alert(
        "Bridgefy not ready",
        "Your Bridgefy could not start  yet"
      );
      return false;
    }

    var message = {
      content: { message: JSON.stringify(brdgMessages[0]) },
    };
    // 'onSend', { text: 'Tutu',
    // user: { _id: 1 },
    // createdAt: Tue Aug 25 2020 17:42:36 GMT+0200 (CEST),
    // _id: 'f354718f-3e3b-4450-a475-2b41796440cb' }

    // { text: 'Hkhj',
    //   user: 
    //    { _id: 'f05b8728-f980-4935-a440-b13dc6731827',
    //      name: 'Broadcast User',
    //      avatar: 'https://unsplash.it/200/300/?random' },
    //   createdAt: Tue Aug 25 2020 17:50:17 GMT+0200 (CEST),
    //   _id: '00715249-675b-4e91-8bc7-94c7b6c58504' }

    console.log('onSend',brdgMessages[0]);

    // this.setState((previousState) => {
    //   return {
    //     messages: GiftedChat.append(previousState.messages, messages),
    //   };
    // });
    let nm = GiftedChat.append(messages, brdgMessages);
    console.log('1 - nms = ',messages,brdgMessages,nm);
    setMessages(nm);

    // for demo purpose this.answerDemo(messages);
    BridgefySdk.sendBroadcastMessage(message);
  }

  useEffect(() => {

    console.log('use effect');
    
    initListeners();
    initBrdg();
      
    return function cleanup() {

      console.log('cleanup');
      if (connected) {
        BridgefySdk.stop();
      }
      clearListeners();
    };
  }, []);

  return (
    <GiftedChat
      messages={messages}
      onSend={msgs => onSend(msgs)}
      renderChatFooter={()=> ( <ChatFooter connected={connected} /> ) }
      user={client ? client : {
        _id: 1,
      }}
    />
  )
}

const ChatFooter = (props) => {
  let { connected } = props;
  return (
    <View>
      { connected && (
        <Text>
          Bridgefy connecté
        </Text>
      )}
      { !connected && (
        <Text>
          Bridgefy déconnecté !
        </Text>
      )}
    </View>
  )
}