package com.agitex.climax.parser;

import com.agitex.climax.model.Client;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface FileParser {

    List<Client> parse(InputStream inputStream) throws IOException;

}
