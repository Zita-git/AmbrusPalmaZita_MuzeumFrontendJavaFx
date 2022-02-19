package com.example.muzeumfrontendjavafx.api;

import com.example.muzeumfrontendjavafx.muzeum.Statues;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class StatuesApi {

    private static final String API_URL = "http://127.0.0.1:8000/api/statues";
    private static Gson jsonConverter = new Gson();

    public static List<Statues> get() throws IOException {
        String json = Api.get(API_URL);
        Type type = new TypeToken<List<Statues>>() {
        }.getType();
        return jsonConverter.fromJson(json, type);
    }

    public static Statues post(Statues uj) throws IOException {
        String ujJson = jsonConverter.toJson(uj);
        String json = Api.post(API_URL, ujJson);
        return jsonConverter.fromJson(json, Statues.class);
    }

    public static boolean delete(int id) throws IOException {
        return Api.delete(API_URL, id).getResponseCode() == 204;
    }

    public static Statues put(Statues modosit, int id) throws IOException {
        String modositJson = jsonConverter.toJson(modosit);
        String json = Api.put(API_URL, modositJson, id);
        return jsonConverter.fromJson(json, Statues.class);
    }
}
