import com.project.Server;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.project.Server.shutdownServer;
import static org.junit.Assert.assertTrue;

public class HealthCheckTest {

    @Before
    public void startServer(){

        Server.main(null);

    }


    @After
    public void stopServer(){

        shutdownServer();

    }


    @Test
    public void checkServerIsAlive(){

        int code = 0;

        try {
            HttpURLConnection request = request();

            code = request.getResponseCode();

        } catch (IOException e) {
            e.printStackTrace();
        }

        assertTrue(code == 200);

    }

    public static HttpURLConnection request() throws MalformedURLException, IOException {
        URL url = new URL(String.format("http://localhost:8080/api/health-check", 8080));
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setConnectTimeout(2000);
        con.setReadTimeout(2000);

        return con;
    }

}
