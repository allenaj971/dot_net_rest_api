package src.main.java.Server;

import java.io.IOException;
import java.net.*;

// This is the main server file
// It will initiatise a producer and consumer queues
// to store requests. The server supports multiple
// clients connecting and sending requests.
public class Server {
    public static void main(String[] args) {
        // start the server
        ServerSocket serverSock = null;
        Socket socket = null;
        // start the producer-consumer queues
        ProducerConsumer pc = new ProducerConsumer();
        pc.start();
        // start the server on port 3000
        try {
            serverSock = new ServerSocket(3000);
            System.out.println("Starting server with address " + serverSock.getInetAddress().toString());
        } catch (Exception e) {
            System.out.println("Failed to start server: " + e.toString());
            try {
                serverSock.close();
            } catch (IOException io) {
                System.out.println("Failed to close server: " + io.toString());
            }
        }

        // accept new client connections
        while (true) {
            try {
                // initialise the client connection
                socket = serverSock.accept();
                System.out.println("Client connected with address " + socket.getRemoteSocketAddress().toString());
                // initialise request handler
                new RequestHandler(socket, pc).start();
            } catch (Exception e) {
                System.out.println("Failed to connect to client: " + e.toString());
            }
        }
    }
}
