package com.cavallaro.udp;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import java.util.HashMap;
import java.util.Map;
import java.io.*;
import java.net.*;

public class UDPReactModule extends ReactContextBaseJavaModule {
  private DatagramSocket socket;
  private InetAddress address;

  private byte[] msg;

  public UDPReactModule(ReactApplicationContext reactContext) {
    super(reactContext);
  }

  @Override
  public String getName() {
    return "UDPReact";
  }

  /**
    Method to send a packet to a given address over a specific port using UDP.
    @param String message - Message to be sent
    @param String ip - ip address to send to
    @param int port - port to send to
    @return Returns the response from the server
     */
  @ReactMethod
  public void sendUDPPacket(String message, String ip, int port, Promise promise/*Callback errorCallback, Callback successCallback*/ ) {
    
    try {
      //Initializing variables
      socket = new DatagramSocket();
      address = InetAddress.getByName(ip);
    } catch(SocketException socEx) {
      //errorCallback.invoke(socEx.getMessage());
      promise.reject("SocketException", socEx.getMessage()); //error handling in case of promise
    } catch(UnknownHostException uEx) {
      //errorCallback.invoke(uEx.getMessage());
      promise.reject("UnknownHostException", uEx.getMessage()); //error handling in case of promise
    } 

    msg = message.getBytes();

    //Prepare packet to send
    DatagramPacket packet = null;
    try {
      packet = new DatagramPacket(msg, msg.length, address, port);
      socket.send(packet); //send packet 
      //Response stuff
      //packet = new DatagramPacket(msg, msg.length); //prepare packet for response
      //socket.receive(packet); //retrieve response
    } catch(IOException ex)  {
      //errorCallback.invoke(ex.getMessage());
      promise.reject("IOException", ex.getMessage()); //error handling in case of promise
      //promise.resolve("IOException " + ex.getMessage()); // possibly use this so it always resolves
    }

    String response =  new String(packet.getData(), 0, packet.getLength());//string will return response
    
    //successCallback.invoke(response);
    promise.resolve(response); //in case of using promise
  } 

  /**
  Method will close a UDP connection should one exist
  */
  @ReactMethod
  public void closeUDPSocket() {
    socket.close();
  }
}
