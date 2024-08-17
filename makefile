compile:
	javac -d bin src/main/java/Server/ProducerConsumer.java src/main/java/Server/RequestHandler.java src/main/java/Server/RequestParser.java src/main/java/Server/Server.java

clean:
	rm bin/src/main/java/Server/*.class

server:
	java -cp bin src.main.java.Server.Server