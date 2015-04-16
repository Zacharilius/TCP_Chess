# TCP_Chess
A Chess game written in Java that passes messages using TCP
Compiles Chess Server

1) Compiles Chess Server and Client
javac -d "bin/" src/client/*.java
javac -d "bin/" src/server/*.java

2) Runs Chess Server
java -classpath "bin/" server.ChessServer

3) Runs Chess Client
java -classpath "bin/" client.ChessClient

