1) Install PCF-DEV in your local machine . ( for downloading  PCF-DEV go to this link   https://docs.pivotal.io/pcf-dev/install-windows.html)
2) Go to command prompt , enter following command  (cf dev start)   if it is first time it will take lot of time.
Downloading VM...
Progress: |====================>| 100%
VM downloaded
Importing VM...
Starting VM...
Provisioning VM...
Waiting for services to start...
40 out of 40 running
 _______  _______  _______    ______   _______  __   __
|       ||       ||       |  |      | |       ||  | |  |
|    _  ||       ||    ___|  |  _    ||    ___||  |_|  |
|   |_| ||       ||   |___   | | |   ||   |___ |       |
|    ___||      _||    ___|  | |_|   ||    ___||       |
|   |    |     |_ |   |      |       ||   |___  |     |
|___|    |_______||___|      |______| |_______|  |___|
is now running.
To begin using PCF Dev, please run:
    cf login -a https://api.local.pcfdev.io --skip-ssl-validation
Admin user => Email: admin / Password: admin
Regular user => Email: user / Password: pass

this message shown in comand prompt

3) enter command :  cf login -a https://api.local.pcfdev.io --skip-ssl-validation
 enter  email admin and password admin
4) enter following command for haash-broker deploy on pcf-dev
   go to following directory where cf-service-registry-broker-0.0.1-SNAPSHOT.jar is placed
   in my pc it is in following location
   D:\PCF-ASSIGNMENT\haash-broker\target\cf-service-registry-broker-0.0.1-SNAPSHOT.jar
   so you move to the target folder and enter following command.
   
   cf push -m 512m -p cf-service-registry-broker-0.0.1-SNAPSHOT.jar haash-broker
   
5) Once the app is running, register the broker with the Cloud Controller (substitute the route for your broker app):
  
   cf create-service-broker haash-broker root root http://haash-broker.s#.pcf.pivotal.io
 
6) Next we have to make plan for the service-broker
   cf enable-service-access haash
   
7) Now you see the service in marketplace.
   cf marketplace
Getting services from marketplace in org default / space development as admin...
OK

service   plans   description
HaaSh     basic   HaaSh - HashMap as a Service

8) Create instance of service
cf create-service HaaSh basic my-hash

9) deploy the haash-client open the new command prompt.
     go to the directory where haash-client jar is placed .
	 in my laptop it is in following path.
	 
	 D:\PCF-ASSIGNMENT\haash-broker-client\target\cf-service-registry-broker-client-0.0.1-SNAPSHOT.jar
	 go to target directory and enter following command
    
	 cf push -m 512m -p cf-service-registry-broker-client-0.0.1-SNAPSHOT.jar haash-client
	 
	 
10) Once the application is running , you can test by powershell command prompt

curl haash-client.s#.pcf.pivotal.io/HaaSh/foo -d '{"value":"bar"}' -X PUT
{}

$ curl haash-client.s#.pcf.pivotal.io/HaaSh/foo
{"value":"bar"}