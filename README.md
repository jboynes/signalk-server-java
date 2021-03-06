Signal K java server
=================================================

An example Signal K server using java.

Provided under an Apache 2 licence

Current capabilities:
* Accepts as input:
	* NMEA0813
	* AIS 
	* NMEA2000 in Canboat format
	* Signalk json
* Reads input from:
	* TCP/IP sockets
	* Websockets
	* HTTP via REST api
	* MQTT
	* STOMP
	* Serial
	* USB
	* local files
* Outputs to:
	* TCP/IP sockets
	* UDP sockets
	* Websockets
	* HTTP via REST api
	* MQTT
	* STOMP
	* Serial
	* USB
* Supports:
	* *NEW: provides discovery over Bonjour, Zeroconf and DNS-SD*
	* AIS targets displayed
	* TCP and MQTT clients (listen to remote servers)
    * On-demand user apps, sort of works, anyway :-)  Bit early for some of the apps yet.
	* Delta and Full signalk formats, and translations between them.
	* Subscriptions, with * and ? wildcards. Configurable format, period and delivery policy
	* LIST - get a list of available signalk keys with * and ? wildcard support
	* GET - get data matching keys on demand
	* PUT - send data on demand 
	* UPDATES - periodic messages.
	* has http://hawt.io management console on localhost:8000/hawtio 
	* Anchor watch
	* metadata based alarms and gui configs.
	
* Todo:
	* _attr based security

Installation
------------

You will need Java 1.7+ installed, and git. You need to be able to type 'java' on the command line and get java responding, or you need to edit the JAVA_HOME variable in start.sh or start.bat. 

Same for `npm`, so install `nodejs` from https://nodejs.org/ The server doesnt need nodejs, but the client installs do use `npm` which comes with it.

On linux just use the package manager, eg for debian:
```shell
$ sudo apt-get install npm
//On debian/ubuntu linux 'node' binary installs as 'nodejs' so you need to do:
$ sudo ln /usr/bin/nodejs /usr/bin/node  
//Once npm is installed you need to install bower
$ sudo npm install -g bower
```

Thats all the prep, then:

```shell
$ git clone https://github.com/SignalK/signalk-java-server.git
```

Then on Linux:

```shell
$ cd signalk-java-server
$ ./startpc.sh
```
NOTE: Windows users - DONT put any of this in directories with spaces or anything but simple ascii names. Use something like eg C:\dev\SignalK-server
Use the startpc.bat file to launch. 

_NOTE: ports have changed to 8080 and 3000, and REST api now has /v1 PRE-pended_

You should now have a SignalK server running, on http://localhost:8080. There is a menu page there that allows you to access the following:

* hawt.io management console on `http://localhost:8000/hawtio`
* webserver on `http://localhost:8080` 
	* REST api on `http://localhost:8080/signalk/v1/api/`
	* Authentication on `http://localhost:8080/signalk/v1/auth` - but its a pass all for now so you dont need to login
* websockets server on `http://localhost:3000`. 
* signalk output streamed as TCP over port 55555. On linux you can watch this with `$ ncat localhost 55555` **see below for subscriptions
* signalk output streamed as UDP over port 55554.
* nmea output will be streamed as TCP over port 55557. On linux you can watch this with `$ ncat localhost 55557`, or use telnet to connect.
* nmea output will be streamed as UDP over port 55556.

It will be streaming a demo file and dumping logging to screen. Control logging by editing conf/log4j.properties.

It currently streams out a demo file taken from a boat sailing in a race in San Francisco. The output includes AIS data. 
If you edit the `conf/signalk.cfg` file and make `signalk.demo=false` (default=true), then it will stop doing that.
Normally it only sends output in signalk delta format to subscribed clients, so clients MUST subscribe or you see only the heartbeat message every 1000ms.
You can subscribe by sending the following json. It supports * and ? wildcards In linux you can paste it into the screen you opened earlier and press [Enter]. :
```
{"context":"vessels.self","subscribe":[{"path":"environment.depth.belowTransducer"},{"path":"navigation.position"}]}
``` 
Then you will see those values every 1000ms.

Try:
```
{"context":"vessels.366982320","subscribe":[{"path":"navigation.position"}]}
{"context":"vessels.366982320","unsubscribe":[{"path":"navigation.position"}]}

{"context":"vessels.*","subscribe":[{"path":"navigation.position"}]}

{"context":"vessels.366982320","subscribe":[{"path":"navigation.position"}]}
{"context":"vessels.366982320","unsubscribe":[{"path":"navigation.position"}]}

{"context":"vessels.*","subscribe":[{"path":"navigation.position.l*"}]}
{"context":"vessels.*","unsubscribe":[{"path":"navigation.position.l*"}]}

{"context":"vessels.*","subscribe":[{"path":"navigation.course*"}]}
{"context":"vessels.*","unsubscribe":[{"path":"navigation.course*"}]}

``` 

Devices (aka GPS) attached on serial>USB adaptors will be automatically found and used. The input can be NMEA0183 compatible, or signalk, and expects 38400 baud by default. The configuration can be changed by editing conf/signalk.cfg

Development
-----------
The project is developed and built using maven and eclipse. You will need to clone the signalk-core-java project and build it with maven , then the signalk-server-java project.
The signalk-core-java project is usuable separately and contains the core model, and useful helpers.


See http://www.42.co.nz/freeboard and http://http://signalk.github.io/ for more.