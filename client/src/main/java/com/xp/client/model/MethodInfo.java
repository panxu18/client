package com.xp.client.model;

import lombok.Data;
import org.springframework.http.HttpMethod;

import java.util.Map;

@Data
public class MethodInfo<T> {
    private HttpMethod method;
    private String url;
    private Map<String, Object> param;
    private T requestBody;
    private Class<?> returnType;

}
