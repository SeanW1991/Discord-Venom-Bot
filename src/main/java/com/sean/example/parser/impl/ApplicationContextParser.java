package com.sean.example.parser.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sean.example.ApplicationContext;
import com.sean.example.parser.Parser;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * @author Sean
 */
public final class ApplicationContextParser implements Parser<ApplicationContext> {

    /**
     * The {@link Gson} for the deserialization of the config file.
     */
    private final Gson gson = new GsonBuilder().create();

    /**
     * The json file name containing for the application context information.
     */
    private static final String CONTEXT_FILE_NAME = "/config.json";

    /**
     * The character encoding for the json file.
     */
    private static final String CHARSET_NAME = "UTF-8";

    @Override
    public ApplicationContext parse() throws IOException {
        try(Reader reader = new InputStreamReader(ClassLoader.class.getResourceAsStream(CONTEXT_FILE_NAME), CHARSET_NAME)){
            ApplicationContext definition = gson.fromJson(reader, ApplicationContext.class);
            return definition;
        }
    }

}
