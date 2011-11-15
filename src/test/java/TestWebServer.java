import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by IntelliJ IDEA.
 * User: pyoussef
 * Date: 11/14/11
 * Time: 9:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestWebServer {
    @Test
    public void testGet() throws Exception{
        final Integer testPort = 8081;

        // Start the webserver in a seperate thread
        Thread webserverThread = new Thread() {
            public void run() {
                try {
                    String []a = {testPort.toString()};
                    WebServer.main(a);
                } catch (Exception e) {
                    fail("Error occured in webserver: " + e.toString());
                }
            }
        };
        webserverThread.start();

        // Make a request to the webserver
        // TODO: Replace this with a better library, usch as Jersey?
        // http://stackoverflow.com/questions/221442/rest-clients-for-java
        URL yahoo = new URL(String.format("http://localhost:%d/index.html", testPort));
        URLConnection yc = yahoo.openConnection();
        BufferedReader in = new BufferedReader(
                                new InputStreamReader(
                                yc.getInputStream()));
        assertTrue(((HttpURLConnection) yc).getResponseCode() == 200);

        in.close();
    }
}
