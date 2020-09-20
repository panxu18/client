package com.xp.client.proxy;

import com.xp.client.model.MethodInfo;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class ProxyUtil {

    public static Object newProxyInstance(Class<?> type) {
        WebClient webClient = WebClient.builder().build();

        return Proxy.newProxyInstance(ProxyUtil.class.getClass().getClassLoader(), new Class[]{type},
                new InvocationHandler() {
                    MethodInfo<?> methodInfo = new MethodInfo<>();
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        buildMethodInfo(method, methodInfo);

                        return null;
                    }

                    private void buildMethodInfo(Method method, MethodInfo<?> methodInfo) {
                        Annotation[] annotations = method.getAnnotations();
                        for (Annotation annotation : annotations) {
                            if (annotation instanceof GetMapping) {
                                methodInfo.setMethod(HttpMethod.GET);
                                methodInfo.setUrl( ((GetMapping) annotation).value()[0]);
                            } else if (annotation instanceof PostMapping) {
                                methodInfo.setMethod(HttpMethod.POST);
                                methodInfo.setUrl( ((PostMapping) annotation).value()[0]);
                            } else if (annotation instanceof DeleteMapping) {
                                methodInfo.setMethod(HttpMethod.DELETE);
                                methodInfo.setUrl( ((DeleteMapping) annotation).value()[0]);
                            } else if (annotation instanceof RequestMapping) {
                                RequestMapping requestMapping = (RequestMapping) annotation;
                                RequestMethod[] methods = requestMapping.method();
                                if (methods.length == 0) {
                                    methodInfo.setMethod(HttpMethod.GET);
                                } else {
                                    methodInfo.setMethod(HttpMethod.valueOf(methods[0].name()));
                                }
                                methodInfo.setUrl(requestMapping.value()[0]);
                            }
                        }
                        Parameter[] parameters = method.getParameters();
                        Map<String, Object> params = new HashMap<>();
                        for (Parameter param : parameters) {
                            if (param.getAnnotation(RequestBody.class) != null) {
                                methodInfo.setRequestBody(param.getParameterizedType());
                            }
                        }
                    }

                });
    }

}
