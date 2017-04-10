package com.sean.example.parser;

import java.io.IOException;

/**
 * @author Sean
 * @param <D> The type of {@link Definition}.
 */
public interface Parser<D extends Definition> {

    /**
     * Parses a specific {@link Definition}.
     * @return The {@link Definition} type containing the parsed information.
     * @throws IOException The exception thrown when an i/o error occurs.
     */
    public D parse() throws IOException;
}
