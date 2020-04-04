import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.project.Server;
import com.project.model.Person;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.project.Server.shutdownServer;
import static org.junit.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ResourcesTest {

    @Before
    public void startServer() {

        Server.main(null);

    }


    @After
    public void stopServer() {

        shutdownServer();

    }

    @Test
    public void stage1CreatePersonFailTest() {
        Exception ee = null;
        Person p = new Person();

        //p.setName("João");
        p.setLastName("Silva");
        p.setAge(30);

        String json = new Gson().toJson(p);

        try {
            String s = create(json);

        } catch (IOException e) {
            ee = e;
        }

        assertTrue(ee != null);

    }

    @Test
    public void stage2CreatePersonSuccessTest() {
        Exception ee = null;
        String message = null;

        Person p = new Person();

        p.setName("Freddie");
        p.setLastName("Mercury");
        p.setAge(45);

        String json = new Gson().toJson(p);

        try {
            message = create(json);

        } catch (IOException e) {
            ee = e;
        }

        assertTrue(message != null);

    }


    @Test
    public void stage3FindAllPersonTest() {

        List<Person> personList = null;

        try {

            HttpURLConnection conn = getPersonList();
            InputStream response = conn.getInputStream();
            String json = convertStreamToString(response);

            if (!json.isEmpty()) {
                personList = new Gson().fromJson(json, new TypeToken<List<Person>>() {
                }.getType());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertTrue(personList != null && personList.size() > 0);

    }

    @Test
    public void stage4UpdatePersonTest() {

        Exception ee = null;
        String message = null;

        try {

            List<Person> personList = new ArrayList<>();

            HttpURLConnection conn = getPersonList();
            InputStream response = conn.getInputStream();
            String json = convertStreamToString(response);

            if (!json.isEmpty()) {
                personList = new Gson().fromJson(json, new TypeToken<List<Person>>() {
                }.getType());
            }

            Person person = personList.get(0);

            person.setName("Jonas");
            person.setLastName("Brothers");

            message = update(person.getId(), new Gson().toJson(person));


        } catch (IOException e) {
            ee = e;
        }

        assertTrue(message != null);

    }

    @Test
    public void stage5DeletePersonTest() {

        Exception ee = null;
        String message = null;

        try {

            List<Person> personList = new ArrayList<>();

            HttpURLConnection conn = getPersonList();
            InputStream response = conn.getInputStream();
            String json = convertStreamToString(response);

            if (!json.isEmpty()) {
                personList = new Gson().fromJson(json, new TypeToken<List<Person>>() {
                }.getType());
            }

            Person person = personList.get(0);

            message = deletePerson(person.getId());

        } catch (Exception e) {
            ee = e;
        }

        assertTrue(message != null && ee == null);
    }


    public static HttpURLConnection getPersonList() throws MalformedURLException, IOException {
        URL url = new URL(String.format("http://localhost:8080/api/person", 8080));
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        return con;
    }

    public static String deletePerson(String id) throws MalformedURLException, IOException {
        URL url = new URL(String.format("http://localhost:8080/api/person/" + id, 8080));
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("DELETE");

        InputStream in = new BufferedInputStream(con.getInputStream());
        String result = org.apache.commons.io.IOUtils.toString(in, "UTF-8");

        in.close();
        con.disconnect();

        return result;
    }

    public static String create(String json) throws MalformedURLException, IOException {
        URL url = new URL(String.format("http://localhost:8080/api/person", 8080));
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setRequestMethod("POST");

        OutputStream os = con.getOutputStream();
        os.write(json.getBytes("UTF-8"));
        os.close();

        InputStream in = new BufferedInputStream(con.getInputStream());
        String result = org.apache.commons.io.IOUtils.toString(in, "UTF-8");

        in.close();
        con.disconnect();

        return result;
    }

    public static String update(String identifier, String personJson) throws MalformedURLException, IOException {

        URL url = new URL(String.format("http://localhost:8080/api/person/" + identifier, 8080));
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        con.setDoOutput(true);
        con.setRequestMethod("PUT");

        OutputStream os = con.getOutputStream();
        os.write(personJson.getBytes("UTF-8"));
        os.close();

        InputStream in = new BufferedInputStream(con.getInputStream());
        String result = org.apache.commons.io.IOUtils.toString(in, "UTF-8");

        in.close();
        con.disconnect();

        return result;
    }

    private String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

}