package org.jtwig.hot.reloading;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.environment.EnvironmentConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.parboiled.common.FileUtils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.jtwig.environment.EnvironmentConfigurationBuilder.configuration;

public class HotReloadingExtensionTest {
    private File file;

    @Before
    public void setUp() throws Exception {
        Path tempFile = Files.createTempFile("reload", "test");
        file = tempFile.toFile();
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void integrationTest() throws Exception {
        EnvironmentConfiguration configuration = configuration().extensions().add(new HotReloadingExtension()).and().build();
        FileUtils.writeAllText("{{ 1 }}", file);
        String result = JtwigTemplate.fileTemplate(file, configuration).render(JtwigModel.newModel());
        assertThat(result, is("1"));

        FileUtils.writeAllText("{{ 2 }}", file);
        result = JtwigTemplate.fileTemplate(file, configuration).render(JtwigModel.newModel());
        assertThat(result, is("2"));
    }

    @Test
    public void integrationWithWait() throws Exception {
        EnvironmentConfiguration configuration = configuration().extensions().add(new HotReloadingExtension(TimeUnit.SECONDS, 10)).and().build();
        JtwigTemplate jtwigTemplate = JtwigTemplate.fileTemplate(file, configuration);

        FileUtils.writeAllText("{{ 1 }}", file);
        String result = jtwigTemplate.render(JtwigModel.newModel());
        assertThat(result, is("1"));

        FileUtils.writeAllText("{{ 2 }}", file);
        result = jtwigTemplate.render(JtwigModel.newModel());
        assertThat(result, is("1"));
    }

    @Test
    public void integrationWithWaitTrue() throws Exception {
        EnvironmentConfiguration configuration = configuration().extensions().add(new HotReloadingExtension(TimeUnit.MILLISECONDS, 100)).and().build();
        JtwigTemplate jtwigTemplate = JtwigTemplate.fileTemplate(file, configuration);

        FileUtils.writeAllText("{{ 1 }}", file);
        String result = jtwigTemplate.render(JtwigModel.newModel());
        assertThat(result, is("1"));

        Thread.sleep(1000);

        FileUtils.writeAllText("{{ 2 }}", file);
        result = jtwigTemplate.render(JtwigModel.newModel());
        assertThat(result, is("2"));
    }

    @Test
    public void integrationWithoutConfig() throws Exception {
        EnvironmentConfiguration configuration = configuration().extensions().and().build();

        FileUtils.writeAllText("{{ 1 }}", file);
        String result = JtwigTemplate.fileTemplate(file, configuration).render(JtwigModel.newModel());
        assertThat(result, is("1"));

        FileUtils.writeAllText("{{ 2 }}", file);
        result = JtwigTemplate.fileTemplate(file, configuration).render(JtwigModel.newModel());
        assertThat(result, is("1"));
    }
}