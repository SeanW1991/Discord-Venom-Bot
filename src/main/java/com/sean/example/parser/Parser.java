package com.sean.example.parser;

import java.io.IOException;

public interface Parser<D extends Definition> {

    public D parse() throws IOException, IOException;
}
