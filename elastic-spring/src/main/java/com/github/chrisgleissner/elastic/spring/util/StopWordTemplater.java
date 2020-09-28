package com.github.chrisgleissner.elastic.spring.util;

import lombok.SneakyThrows;
import org.testcontainers.shaded.org.apache.commons.lang.WordUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.util.stream.Collectors.joining;

public class StopWordTemplater {

    @SneakyThrows
    public static String template(String json, String stopVariable, Path stopWordPath) {
        List<String> stopWords = Files.readAllLines(stopWordPath);
        String jsonWords = stopWords.stream().filter(w -> !w.startsWith("#") && !w.startsWith("//"))
                .map(w -> "\"" + w.replaceAll("\"", "") + "\"")
                .collect(joining(", "));
        String stopJson = WordUtils.wrap(jsonWords, 120, "\n", false);
        return json.replaceAll(stopVariable, stopJson);
    }
}
