# mqtt-config-java
#curl command to download the json file 
# run the jar file and use this URL from post man 
POST localhost:8080/upload-multiple
![image](https://github.com/user-attachments/assets/52ac8e80-28fa-497e-a0e1-a335d5fd0d10)


& "C:\Windows\System32\curl.exe" -X POST http://localhost:8080/upload-multiple `
>>   -F "deviceid=@<path to file>" `
>>   -F "ca_cert=@<path to file>" `
>>   -F "client_cert=@<path to file>" `
>>   -F "client_key=@<path to file>" `
>>   -o mqtt.json
>>
>> 
