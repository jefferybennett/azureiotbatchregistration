package com.meddevicemaker.app;

import com.microsoft.azure.sdk.iot.service.*;
import com.microsoft.azure.sdk.iot.device.*;
import com.microsoft.azure.sdk.iot.deps.util.Base64;
import com.microsoft.azure.sdk.iot.device.auth.IotHubSasToken;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class App 
{
    public static final String iotHubServiceClientConnectionString = "{IoTHubOwner Policy Shared Access Policy Connection String}";
    public static final String iotHostName = "jbGeneralS1.azure-devices.net";
    public static final String deviceId = "testDevice1";
    public static long expiryTime = 365*24*60*60;
    
    public static void main( String[] args ) throws IOException
    {

        RegistryManager registryManager = RegistryManager.createFromConnectionString(iotHubServiceClientConnectionString);
        Device unregisteredDevice = null;
        Device registeredDevice = null;

        try {
            unregisteredDevice = Device.createFromId(deviceId, null, null);
        }
        catch (Exception e) { System.out.println(e.getMessage());}

        // Register Device with IoT Hub
        try {
            registeredDevice = registryManager.addDevice(unregisteredDevice);
        }
        catch (Exception e) { System.out.println(e.getMessage());}

        // Generate SAS Token
        IotHubSasToken token = new IotHubSasToken(iotHostName, deviceId, registeredDevice.getPrimaryKey(), null, null, expiryTime);
        
        System.out.println(token.toString());
    }
}
