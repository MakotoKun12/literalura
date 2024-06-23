package com.reto.literalura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvierteDatos implements IConvierteDatos{
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T obtenerDatos(String json, Class<T> clase) {
        try {
            return objectMapper.readValue(json,clase);
        }catch (JsonProcessingException e){
            System.err.println("Error procesando JSON: " + e.getMessage());
            System.err.println("JSON recibido: " + json);
            throw new RuntimeException("Error al convertir JSON a " + clase.getSimpleName(), e);
        }
    }
}
