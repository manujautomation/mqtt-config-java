# mqtt-config-java

# run the jar file and use this URL from post man 
POST localhost:8080/upload-multiple
![image](https://github.com/user-attachments/assets/52ac8e80-28fa-497e-a0e1-a335d5fd0d10)

#curl command to download the json file 

& "C:\Windows\System32\curl.exe" -X POST http://localhost:8080/upload-multiple `
>>   -F "deviceid=@<path to file>" `
>>   -F "ca_cert=@<path to file>" `
>>   -F "client_cert=@<path to file>" `
>>   -F "client_key=@<path to file>" `
>>   -o mqtt.json
>>
>> 

Added a new rest API call to publish messages to IOT core topics/things
URL - localhost:8080/publish
Method Type - POST 
Request body- "{
  "certType": "clientCert",
  "filename": "bbu-device-cert.pem",
  "content": "----BEGIN RSA PRIVATE KEY-----
-----END CERTIFICATE-----"
}"
![image](https://github.com/user-attachments/assets/a4ebf9eb-1eef-4023-a484-4a86c5f6aae9)

On AWS side you can see the published payload -
![image](https://github.com/user-attachments/assets/bf52cf17-6cdf-4877-805d-cbef4c9d0317)

Added a new rest API call to create thing and create certificate and attach certs to the thing on AWS, stores the cert information in the s3 bucket -
URL - localhost:8080/register/{thingName}
Method Type - POST 
![image](https://github.com/user-attachments/assets/772e3cf3-96e4-4d03-99f1-17e405cb66ab)
![image](https://github.com/user-attachments/assets/5c3d26a8-3e1b-4963-85e5-bbb9b64e5f8e)
![image](https://github.com/user-attachments/assets/4000a6f6-24fc-4a94-818b-cc525df1309c)

Another API to retrieve appropriate keys and generate mqtt.json file for the users -

URL - localhost:8080/retrievemqttjsonfile/{thingName}
Method type - POST
![image](https://github.com/user-attachments/assets/bb161ccb-c7bb-435b-b12d-93d671fe38cb)








