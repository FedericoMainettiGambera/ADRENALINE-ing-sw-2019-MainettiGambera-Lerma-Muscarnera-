#######################						#######################
#######################			R E A D M E		#######################
#######################						#######################

> How to start to play?
i) How to start server.

Connect to the same router, and choose a player who will run the server application.
In order to run server, open Command Prompt and type:
	java -classpath %jar folder path here%/adrenalina-1.0-SNAPSHOT.jar it.polimi.se2019.controller.StartServer
When the server starts, it will display the ip and the port ( for Socket connection ) to use to connect and start
the game.

ii) How to start clients.

Now that Server has started, the player can connect to it and play.
To start a client the player needs to open Command Prompt and type
	java -classpath %jar folder path here%/adrenalina-1.0-SNAPSHOT.jar it.polimi.se2019.controller.StartClient

iii) How to configure clients

The client applications will ask you all the informations the game needs to be ready for a match!
Just type the ip and (eventually, not needed if using RMI connection ) the port of the server, and start the game.
