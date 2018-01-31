## React Native UDP Android Library 
This project serves as a React Native Module to be specifically implemented with Android Devices

## Callable Methods
  ```java
  /**
    Method to send a packet to a given address over a specific port using UDP.
    @param String message - Message to be sent
    @param String ip - ip address to send to
    @param String port - port to send to
    @return Returns the response from the server
     */
  @ReactMethod
  public void sendUDPPacket(String message, String ip, String portStr, Promise promise);
  ```
## Example Use
   ```javascript
   import UDPReact from 'react-native-udp';
   
   render() {
      this.sendUDPPacket('This is a test message');
   }
   
   async sendUDPPacket(message) {
      try {
         var {
            response
         } = await UDPReact.sendUDPPacket(message, 'server', 12345));
  
      } catch (e) {
         console.error(e);
      }
   }
  ```
  
