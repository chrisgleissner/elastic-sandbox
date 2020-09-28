package com.github.chrisgleissner.elastic.spring.util;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class StopWordTemplaterTest {

    @Test
    void template() throws IOException {
        String settingsJson = Files.readString(Path.of("src/main/resources/elastic/book-settings.json"));
        String templatedSettingsJson = StopWordTemplater.template(settingsJson, "\"_customStopWords\"",
                Path.of("src/main/resources/elastic/stopwords.properties"));
        assertThat(templatedSettingsJson).contains("\"a\", \"all\", \"am\", \"an\", \"and\"");
    }
}