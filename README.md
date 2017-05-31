# CodeChat-client
An [XMPP client](https://xmpp.org/software/clients.html) with code highlighting text area that enables users to share code as they are being typed out. It works based on the principle of HOST/GUEST code sharing. This project is a restart of a similar prior [project](https://github.com/yemikudaisi/coderchatclient) with a similar name (Coder Chat) based on a NodeJS server.  The focus is however not to implement all the [XEP series](https://xmpp.org/extensions/) but mainly to make code sharing as easy as possible through instant message. 

This project was inspired by the need to remotely teach programming to beginners in a hands-on way.

## Architecture
The client application is being developed in [JavaFX](http://www.oracle.com/technetwork/java/javafx/overview/index.html) and designed to be compatible with most [Jabber/XMPP](https://xmpp.org/software/servers.html) Servers. 


## Dependencies
+ [Java8](http://www.oracle.com/technetwork/java/javase/overview/java8-2100321.html)
+ [Babbler XMPP Client](https://bitbucket.org/sco0ter/babbler/overview)

## How it works
+ The host offers a CodeChat to a client.
+ The client approves
+ The CodeChat project structure is offered the client.
+ Host edits the code in one of the project files and the file is opened the Client sees changes to the code in near real time (every new line/ change in line )
+ At any point in the time only the host is capable of editing code. While the guest watches.
+ A guest can however annotate (CodeChat) the code while the master is editing a source.
+ The role (HOST/GUEST) can be switched at any point as long as the master conceeds to it.
+ The guest can also be multiple user, allowiing for the app to be used in a class room scenario.

## Status
In progress.

## TODO
+ Run and compile source code from within the application
+ Group code chat
+ Desing  for version control(VC) intergration
+ Actual implemntation of the design for VC
+ Shell integration

## Issue Reporting

If you have found a bug or if you have a feature request, please report them at this repository issues section.

## Author

[Yemi Kudaisi](https://github.com/yemikudaisi)

## License

This project is licensed under the MIT license. See the [LICENSE](LICENSE) file for more info.