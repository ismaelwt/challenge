import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertTrue;

public class LoadPropertiesTest {

    @Test
    public void testLoad() {


        String resourceName = "hikari.properties";

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(resourceName).getFile());
        String absolutePath = file.getAbsolutePath();

        System.out.println(absolutePath);

        assertTrue(absolutePath.endsWith("hikari.properties"));

    }

}
