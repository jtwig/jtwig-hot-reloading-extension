package org.jtwig.hot.reloading.outdated;

import org.jtwig.environment.Environment;
import org.jtwig.environment.EnvironmentConfiguration;
import org.jtwig.environment.EnvironmentConfigurationBuilder;
import org.jtwig.environment.EnvironmentFactory;
import org.jtwig.resource.reference.ResourceReference;
import org.junit.Before;
import org.junit.Test;
import org.parboiled.common.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class DefaultIsFileOutdatedTest {
    private final HashMap<String, Long> memory = new HashMap<>();
    private DefaultIsFileOutdated underTest = new DefaultIsFileOutdated(memory);
    private File file;

    @Before
    public void setUp() throws Exception {
        memory.clear();
        Path tempFile = Files.createTempFile("reload", "test");
        file = tempFile.toFile();
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void testFalse() throws Exception {
        EnvironmentFactory environmentFactory = new EnvironmentFactory();
        EnvironmentConfiguration configuration = EnvironmentConfigurationBuilder.configuration().build();
        Environment environment = environmentFactory.create(configuration);

        ResourceReference resourceReference = new ResourceReference(ResourceReference.FILE, file.getAbsolutePath());
        FileUtils.writeAllText("test", file);
        underTest.isOutdated(environment, resourceReference);
        boolean outdated = underTest.isOutdated(environment, resourceReference);

        assertThat(outdated, is(false));
    }

    @Test
    public void testTrue() throws Exception {
        EnvironmentFactory environmentFactory = new EnvironmentFactory();
        EnvironmentConfiguration configuration = EnvironmentConfigurationBuilder.configuration().build();
        Environment environment = environmentFactory.create(configuration);

        ResourceReference resourceReference = new ResourceReference(ResourceReference.FILE, file.getAbsolutePath());
        write("one", file);
        underTest.isOutdated(environment, resourceReference);
        write("two", file);
        boolean outdated = underTest.isOutdated(environment, resourceReference);

        assertThat(outdated, is(true));
    }

    private void write(String content, File file) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(content.getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();
            Thread.sleep(1000);
            file.setLastModified(System.currentTimeMillis());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}