package com.example.muzeumfrontendjavafx.api;

import com.example.muzeumfrontendjavafx.Controller;
import com.example.muzeumfrontendjavafx.muzeum.Paintings;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class PaintingsApi extends Controller {
    private static final String API_URL = "http://127.0.0.1:8000/api/paintings";
    private static Gson jsonConverter = new Gson();

    public static List<Paintings> get() throws IOException {
        String json = Api.get(API_URL);
        Type type = new TypeToken<List<Paintings>>() {
        }.getType();
        return jsonConverter.fromJson(json, type);
    }

    public static Paintings post(Paintings uj) throws IOException {
        String ujJson = jsonConverter.toJson(uj);
        String json = Api.post(API_URL, ujJson);
        return jsonConverter.fromJson(json, Paintings.class);
    }

    public static boolean delete(int id) throws IOException {
        return Api.delete(API_URL, id).getResponseCode() == 240;
    }

    public static Paintings put(Paintings modosit, int id) throws IOException {
        String modositandoJson = jsonConverter.toJson(modosit);
        String json = Api.put(API_URL, modositandoJson, id);
        return jsonConverter.fromJson(json, Paintings.class);
    }
}
