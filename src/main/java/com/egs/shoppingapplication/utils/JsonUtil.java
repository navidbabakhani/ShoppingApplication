package com.egs.shoppingapplication.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JsonUtil {

    static final ObjectMapper mapper = new ObjectMapper();

    static {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Timestamp.class, new FromStringDeserializer<Timestamp>(Timestamp.class) {
            @Override
            protected Timestamp _deserialize(String s, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
                final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
                String dateString = s;

                DateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
                Date d = null;
                try {
                    d = formatter.parse(dateString);
                } catch (ParseException e) {
                    //logger.error(e);
                }

                Timestamp sq = null;
                if (d == null) {
                    sq = new Timestamp(Long.valueOf(s));
                } else {
                    //Timestamp ts = Timestamp.valueOf(String.valueOf(d));
                    sq = new Timestamp(d.getTime());
                }
                return sq;
            }
        });
        module.addSerializer(BigDecimal.class, new ToStringSerializer());
        module.addDeserializer(BigDecimal.class, new FromStringDeserializer<BigDecimal>(BigDecimal.class) {
            @Override
            protected BigDecimal _deserialize(String s, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
                return new BigDecimal(s);
            }
        });
        mapper.registerModule(module);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    public static String getJson(Object obj) {
        if (obj == null) return null;
        if (obj instanceof JSONArray || obj instanceof JSONObject) {
            return obj.toString();
        } else {
            try {
                ByteArrayOutputStream bout = new ByteArrayOutputStream();
                mapper.writeValue(bout, obj);
                byte[] objectBytes = bout.toByteArray();
                return new String(objectBytes);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static <T> T getObject(byte[] json, Class<T> classOfT) {
        try {
            return mapper.readValue(new String(json, "utf-8"), classOfT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <B> B getObject(String json, Class<?> mainClass, Class[] parametricClasses) {
        JavaType type = mapper.getTypeFactory().constructParametricType(mainClass, parametricClasses);
        try {
            return mapper.readValue(json, type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T getObject(String json, Class<T> classOfT) {
        try {
            return mapper.readValue(json, classOfT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T getObject(String json, TypeReference<T> typeReference) {
        try {
            return mapper.readValue(json, typeReference);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static JsonNode getJsonNode(String json) {
        try {
            return mapper.readTree(json);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static ObjectMapper getMapper() {
        return mapper;
    }
}