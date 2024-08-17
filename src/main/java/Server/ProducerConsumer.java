package src.main.java.Server;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantLock;
import java.util.UUID;

public class ProducerConsumer extends Thread {

  private Queue<HashMap<String, String>> requestQueue;
  private HashMap<String, String> responses;
  private ReentrantLock mutex;
  private RequestParser rp;

  // this is the main producer-consumer handler
  // it will parse all the requests in the request
  // queue and output to the responses map
  @Override
  public void run() {
    this.requestQueue = new LinkedList<>();
    this.responses = new HashMap<>();
    this.mutex = new ReentrantLock();
    this.rp = new RequestParser();

    // load request Queue from backup file, if any data exists
    try {
      ObjectInputStream ois = new ObjectInputStream(
          new FileInputStream("requests.txt"));
      this.requestQueue = (Queue<HashMap<String, String>>) ois.readObject();
      ois.close();
    } catch (Exception e) {
      System.out.println(e.toString());
    }

    // load responses from backup file, if any data exists
    try {
      ObjectInputStream ois = new ObjectInputStream(
          new FileInputStream("responses.txt"));
      this.responses = (HashMap<String, String>) ois.readObject();
      ois.close();
    } catch (Exception e) {
      System.out.println(e.toString());
    }
  }

  public String getResponse(String req_id) {
    // while there is no response for the
    // uuid, call dequeueAndProcess
    if (!this.responses.containsKey(req_id)) {
      dequeueAndProcess();
    }

    // send response
    return this.responses.get(req_id).toString();
  }

  public String addRequest(String newReq) {
    // lock the queue
    mutex.lock();

    // generate a UUID for the request
    String uuid = UUID.randomUUID().toString().replace("-", "");
    // add it to the queue for processing
    HashMap<String, String> req = new HashMap<>();
    req.put(uuid, newReq);
    this.requestQueue.add(req);

    // unlock the queue
    mutex.unlock();

    return uuid;
  }

  private void dequeueAndProcess() {
    // lock the queue
    mutex.lock();

    // dequeue and process requests
    // and add response to map
    while (!requestQueue.isEmpty()) {
      HashMap<String, String> req = requestQueue.poll();
      String req_id = req.keySet().toArray()[0].toString();
      String res = this.rp.parseRequest(req_id, req.get(req_id), this.responses);
      this.responses.put(req_id, res);
    }

    // unlock the queue to add requests
    mutex.unlock();
  }
}
