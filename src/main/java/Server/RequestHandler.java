package src.main.java.Server;

import java.io.BufferedInputStream;
import java.io.PrintWriter;
import java.net.*;

public class RequestHandler extends Thread {

    private Socket socket;
    private ProducerConsumer pc;

    // constructor takes in server connection and Producer-Consumer queue
    public RequestHandler(Socket soc, ProducerConsumer pc) {
        this.socket = soc;
        this.pc = pc;
    }

    @Override
    public void run() {
        try {
            // connect to output stream to send response to client
            PrintWriter pw = new PrintWriter(this.socket.getOutputStream(), isAlive());
            // connect to input stream
            BufferedInputStream bis = new BufferedInputStream(this.socket.getInputStream());

            // get the number of bytes sent
            int rem_byte = bis.available();
            // create a byte array of size of the number of bytes
            byte[] barr = new byte[rem_byte];
            // read into byte array the data
            bis.read(barr, 0, rem_byte);

            // generate string from bytes
            String req = "";
            for (byte b : barr) {
                req += (char) b;
            }

            // add request to server
            String requestId = this.pc.addRequest(req);
            // get request
            String res = this.pc.getResponse(requestId);

            // send request to client
            pw.println(res);
            bis.close();
            pw.flush();
            pw.close();

        } catch (Exception e) {
            System.out.println("Request Handler error: " + e.toString());

        }
    }
}
