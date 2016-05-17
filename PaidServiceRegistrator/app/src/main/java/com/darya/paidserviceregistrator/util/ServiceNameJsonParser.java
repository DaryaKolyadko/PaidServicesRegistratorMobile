package com.darya.paidserviceregistrator.util;

import android.util.JsonReader;

import com.darya.paidserviceregistrator.entities.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 13.05.2016.
 */
public class ServiceNameJsonParser {

    public List parse(String string) throws IOException {
        InputStream stream = new ByteArrayInputStream(string.getBytes(StandardCharsets.UTF_8));
        JsonReader reader = new JsonReader(new InputStreamReader(stream));
        try {
            return readServiceArray(reader);
        } finally {
            reader.close();
        }
    }

    private List readServiceArray(JsonReader reader) throws IOException {
        List services = new ArrayList();

        reader.beginArray();

        while (reader.hasNext()) {
            services.add(readService(reader));
        }

        reader.endArray();
        return services;
    }

    private Service readService(JsonReader reader) throws IOException {
        Service service = new Service();

        reader.beginObject();

        while (reader.hasNext()) {
            String name = reader.nextName();

            if (name.equals("Id")) {
                service.setId(reader.nextInt());
            } else if (name.equals("Name")) {
                service.setName(reader.nextString());
            }
        }

        reader.endObject();
        return service;
    }
}
