import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertTrue;

public class LoadPropertiesTest {

    @Test
    public void testLoad() {


        String resourceName = "application.properties";

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(resourceName).getFile());
        String absolutePath = file.getAbsolutePath();

        System.out.println(absolutePath);

        assertTrue(absolutePath.endsWith("application.properties"));

    }

    @Test
    public void testLoadIndex() {


        String resourceName = "index.html";

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(resourceName).getFile());
        String absolutePath = file.getAbsolutePath();

        System.out.println(absolutePath);

        assertTrue(absolutePath.endsWith("index.html"));

    }

}
