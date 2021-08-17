# ADRENALINE

## About this project

This project is the digitalization of an awesome board game called "Adrenaline". You can host the server directly on your computer and play online with your friends.

The entire application is written in **Java**, if you are intrested in the code you can:
- check the **code base** and all the **tests** in the src folder
- read the **documentation** in the delivery/final/JavaDoc/javadoc documentaion/JavaDoc Documentation folder
- check the **UML** in the delivery/final/UML folder
- check **sonar reports** in the delivery/final/Sonar folder
- whatch a game be played by bots using the CLI (see below).

## About Adrenaline board game
![ADRENALINE LOGO](https://github.com/FedericoMainettiGambera/ADRENALINE/blob/master/img/pic3476604.jpg "Logo ADRENALINE")

In the future, war has left the world in complete destruction and split the people into factions. The factions have decided to stop the endless war and settle their dispute in the arena. A new virtual bloodsport was created. The Adrenaline tournament. Every faction has a champion, every champion has a chance to fight and the chance to win. Will you take the chance of becoming the next champion of the Adrenaline tournament?

Play a first-person shooter on your gaming table. Grab some ammo, grab a gun, and start shooting. Build up an arsenal for a killer turn. Combat resolution is quick and diceless. And if you get shot, you get faster!

## How to start playing?

1. **How to start server application?**

Connect to the same wifi connection, and choose a player who will run the server application.

In order to run server, open Command Prompt and type:
```bash
java -classpath %jar folder path here%\adrenalina-1.0-SNAPSHOT.jar it.polimi.se2019.controller.StartServer
```
When the server starts, it will display the ip and the port (for Socket users) and the addressto (for RMI users) be used by clients to connect and start playing.

![server ip and port](https://github.com/FedericoMainettiGambera/ADRENALINE/blob/master/img/startServerscreenshot.JPG "server ip and port")

2. **How to start client application?**

Now that the server has started and the players can connect to it and play.

To start a client the player needs to open Command Prompt and type
```bash
java -classpath %jar folder path here%\adrenalina-1.0-SNAPSHOT.jar it.polimi.se2019.controller.StartClient
```

This is what should pop up:

<img src="https://github.com/FedericoMainettiGambera/ADRENALINE/blob/master/img/startClientscreenshot.JPG" width="600">

If preferred the game can be also played in the terminal with the command line interface (CLI):

<img src="https://github.com/FedericoMainettiGambera/ADRENALINE/blob/master/img/startClientscreenshotCLI.JPG" width="600">

The CLI also will ask you if you want a bot to play instead of you (warning: it makes his moves really fast!). This option has been really useful to test the game during the development process.

**UPDATE:**

In Java 11, JavaFX was removed from the SDK. It is now in its own separate module, and if you want to use it in your application you will need to specifically include it or downgrade to an older sdk.

To include javafx manually, download it from [here](https://gluonhq.com/products/javafx/), unzip the content and put it somewhere "safe" (e.g. C:\Program Files\Java).

To lounch the client application you now just need to add two options
```bash
java --module-path "%path to java fx here%\javafx-sdk-11.0.2\lib" --add-modules javafx.controls,javafx.fxml -classpath %jar folder path here%\adrenalina-1.0-SNAPSHOT.jar it.polimi.se2019.controller.StartClient
```
remember that if any of the path has spaces you must put it in quotes ("..").

3. **How to configure clients?**

The client applications will ask you all the informations the game needs to set up a match, just follow the instructions and have fun. 

Also the windows and every section of the gui is resizable to better fit your preferences, here it is an example of what an "empty table" looks like:

![Client starting](https://github.com/FedericoMainettiGambera/ADRENALINE/blob/master/img/Screenshot%20(236).png "client starting")
