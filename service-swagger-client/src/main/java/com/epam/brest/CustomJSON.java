package com.epam.brest;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CustomJSON extends JSON {

    private Gson gson;
    private final LocalDateTypeAdapterCustom localDateTypeAdapter = new LocalDateTypeAdapterCustom();

    public CustomJSON() {
        gson = createGson()
                .registerTypeAdapter(LocalDate.class, localDateTypeAdapter)
                .create();
    }

    /**
     * Get Gson.
     *
     * @return Gson
     */
    public Gson getGson() {
        return gson;
    }

    /**
     * Set Gson.
     *
     * @param gson Gson
     * @return JSON
     */
    public JSON setGson(Gson gson) {
        this.gson = gson;
        return this;
    }

    /**
     * Serialize the given Java object into JSON string.
     *
     * @param obj Object
     * @return String representation of the JSON
     */
    public String serialize(Object obj) {
        return gson.toJson(obj);
    }

    /**
     * Deserialize the given JSON string to Java object.
     *
     * @param <T>        Type
     * @param body       The JSON string
     * @param returnType The type to deserialize into
     * @return The deserialized Java object
     */
    @Override
    public <T> T deserialize(String body, Type returnType) {
        return gson.fromJson(body, returnType);
    }

    /**
     * Gson TypeAdapter for JSR310 LocalDate type
     */
    public class LocalDateTypeAdapterCustom extends LocalDateTypeAdapter {

        private DateTimeFormatter deserializeFormatter;

        public void setDeserializeFormat(DateTimeFormatter dateFormat) {
            this.deserializeFormatter = dateFormat;
        }

        @Override
        public LocalDate read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            String date = in.nextString();
            return LocalDate.parse(date, deserializeFormatter);
        }
    }

    public CustomJSON setLocalDateDeserializeFormat(DateTimeFormatter dateFormat) {
        localDateTypeAdapter.setDeserializeFormat(dateFormat);
        return this;
    }

}
