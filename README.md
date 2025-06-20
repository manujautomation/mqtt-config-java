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
