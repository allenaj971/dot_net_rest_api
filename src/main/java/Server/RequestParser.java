package src.main.java.Server;

import java.util.regex.*;
import java.util.Arrays;
import java.util.HashMap;
import java.text.SimpleDateFormat;

public class RequestParser {
  // check if the user submitting a new result
  private String[] getInput(String requestBody) {
    // pattern to get data
    String newResult = "(?i)(?:\\\"input\\\"\\:.*\\[)([0-9,]+)(?:\\],?)";
    Matcher newResult_m = Pattern.compile(newResult).matcher(requestBody);
    String[] ans;
    String[] err = new String[1];

    // if there is a input field in the request body
    // find the values in the array
    if (newResult_m.find()) {
      System.out.println("input matches: " + newResult_m.group(1));
      String t = newResult_m.group(1);
      ans = t.split(",");

      // if size of array is less than 3,
      // return invalid input
      if (ans.length < 3) {
        err[0] = "input less than 3 values 400";
        return err;
        // if there are values then return
        // the array
      } else {
        return ans;
      }
    }
    // otherwise there was some error with the
    // content of the input
    err[0] = "input invalid content 400";
    return err;
  }

  // check if the user passed in a request id
  // to get the results from their previous input
  private String getPrevious(String requestBody) {
    String prevResult = "(?i)(?:\\\"req_id\\\"\\:.*\\\")([A-Za-z0-9]+)(?:\\\",?)";
    Matcher prevResult_m = Pattern.compile(prevResult).matcher(requestBody);

    String ans = "";

    // if there is a id in the req_id field
    // get the req id
    if (prevResult_m.find()) {
      ans = prevResult_m.group(1);
      return ans;
    }

    // otherwise there was some value
    return "prev no request id 400";
  }

  // check if there is content in the request body
  private boolean hasContent(String request) {
    String check = "(?:Content-Length:\\s?)(\\d+)";
    Matcher checker = Pattern.compile(check).matcher(request);
    if (checker.find()) {
      if (Integer.valueOf(checker.group(1)) == 0) {
        return false;
      }
    }
    return true;
  }

  public Double rootOfSumOfSquaresOfThreeLargestNo(String[] req) {
    // sort array
    Double[] ans = new Double[req.length];
    Arrays.sort(ans);

    // calculate sqrt root of sum of squares of 3 largest numbers
    Double sum = 0.0;
    for (int i = req.length - 1; i >= req.length - 3; i--) {
      sum += Math.pow(Double.valueOf(req[i]), 2);
    }
    return Math.sqrt(sum);
  }

  public String parseRequest(String req_id, String req, HashMap<String, String> responses) {

    String responseString = "{";

    // check if there is content in the request body
    if (hasContent(req)) {
      String[] inputValues = getInput(req);
      String prevResultid = getPrevious(req);

      System.out.println("Input values: " + rootOfSumOfSquaresOfThreeLargestNo(inputValues));
      System.out.println("\nPrev results id : " + prevResultid.toString());

      // we call 400 if we notice that the regex check doesn't pass
      if (inputValues[0] == "input less than 3 values 400") {
        responseString += generateResponse(400, "{\"error\":\"Your array contains less than 3 numbers.\"}");
      }
      if (inputValues[0] == "input invalid content 400") {
        responseString += generateResponse(400, "{\"error\":\"Your array contains invalid input.\"}");
      }
      if (prevResultid == "prev no request id 400") {
        responseString += generateResponse(400, "{\"error\":\"There is no id in the id field.\"}");
      }

      // we call 200 if the response map contains a request with that id passed
      if (responses.containsKey(prevResultid)) {
        responseString += generateResponse(200,
            "{\"input\":[" + responses.get(prevResultid) + "],\n\"value\":"
                + rootOfSumOfSquaresOfThreeLargestNo(responses.get(prevResultid)) + "}");
      } else {
        // we can calculate the value they need from input and return that
        responseString += generateResponse(200,
            "{\"input\":[" + inputValues + "],\n\"value\":" + rootOfSumOfSquaresOfThreeLargestNo(inputValues) + "}");
      }

    }
    // otherwise send a 204 if no content
    else {
      responseString += generateResponse(204, "{\"error\":\"Your request body has no content.\"}");
    }
    responseString += "}";
    return responseString;
  }

  // this function generates the string to send back to client
  public String generateResponse(int code, String res) {
    String timeStamp = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss").format(new java.util.Date());
    switch (code) {
      case 200:
        // 200 response (user inputted a valid req_id, and we just return that)
        return "HTTP/1.1 200 OK\nServer: Calculator\nContent-Type:application/json\nDate: " + timeStamp
            + "\nContent-Length: " + res.length() + "\r\n\r\n" + res;
      case 201:
        // 201 response (user inputted a new array, so we calculate the square root
        return "HTTP/1.1 201 OK\nServer: Calculator\nContent-Type:application/json\nDate: " + timeStamp
            + "\nContent-Length: " + res.length() + "\r\n\r\n" + res;
      case 204:
        // 204 response (empty request body)
        return "HTTP/1.1 204 No Content\nServer: Calculator\nContent-Type:application/json\nDate: " + timeStamp
            + "\nContent-Length: " + res.length() + "\r\n\r\n" + res;
      // 400 response (invalid elements in input array)
      case 400:
        return "HTTP/1.1 400 Bad Request\nServer: Calculator\nContent-Type:application/json\nDate: "
            + timeStamp
            + "\nContent-Length: " + res.length() + "\r\n\r\n" + res;
      // 404 response (user inputted a invalid uuid)
      case 404:
        return "HTTP/1.1 404 Not Found\nServer: Calculator\nContent-Type:application/json\nDate: "
            + timeStamp
            + "\nContent-Length: " + res.length() + "\r\n\r\n" + res;
      // 500 response (internal server error or overflow)
      default:
        return "HTTP/1.1 500 Internal Server Error\nServer: Calculator\nContent-Type:application/json\nDate: "
            + timeStamp
            + "\nContent-Length: " + res.length() + "\r\n\r\n" + res;
    }
  }

}