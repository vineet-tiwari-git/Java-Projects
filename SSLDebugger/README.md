# Java SSL Debugger

Java Swing based tool that can be used to capture detailed SSL handshake logs during the http call. Logs can be used to troubleshoot SSL issues. http://docs.oracle.com/javase/1.5.0/docs/guide/security/jsse/ReadDebug.html

## Installation

```bash
mvn clean package
java -jar target/SSLDebugger-0.0.1-SNAPSHOT-jar-with-dependencies.jar
```

![Image](https://github.com/user-attachments/assets/2b3ff92e-e6f1-4d59-8c1a-4a1919b690dc)

## Usage

**This program is going to need below as input:**

1. Transport Endpoint.
2. Endpoint credentials if there are any.
3. Public cert path (if SSL endpoint).
4. Private key path and its password (if SSL endpoint).
5. Sample data file.
6. HTTP Method (POST/GET).
7. HTTP headers (pre-populated with content-type)
8 Timeout (prepopulated default is 1 minute ).

**The program when executed does following:**

1. Creates a new key store (if SSL).
2. Imports public key into it (if SSL).
3. Imports private key into it (if SSL).
4. Registers custom protocols with custom ProtocolSocketFactory implementations.
5. Creates the HTTP Client instance & method.
6. Attempts to make the connection and post the sample data to the endpoint.
7. Logs SSL Handshake in details.
8. Delete all the temp files that were created in the process. 

**This can help in:**

1. Troubleshoot SSL handshakes (the exact way Big blue transports does it).
2. Troubleshoot lenient SSL connections (the exact way Big blue transports does it).
3. Trouble standard HTTPS connections.
4. Get detailed SSL handshake logs that can help you understand the root cause of SSL failure. 
   
