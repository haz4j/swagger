package com.haz4j.swagger;

import com.haz4j.swagger.structure.ApiStruct;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.aop.TargetClassAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
public class SwaggerListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private JsonGenerator jsonGenerator;

    @Autowired
    private ApplicationContext context;

    @Autowired(required = false)
    private List<DocketApi> dockets;

    private String swaggerJson;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            swaggerJson = generateJson(dockets);
        } catch (Exception e){
            log.error("Cannot create swagger4jsonrpc json because of " + ExceptionUtils.getStackTrace(e));
        }
    }

    public <T extends DocketApi> String generateJson(List<T> dockets) {
        if (CollectionUtils.isEmpty(dockets)) {
            return null;
        }

        if (dockets.size() > 1) {
            throw new RuntimeException("swagger4jsonrpc supports only one Docket in app. Stay tuned!");
        }

        if (dockets.size() > 0) {

            //Get all interfaces annotated with @Api
            List<Class> apis = getClasses();
            List<String> jsons = new ArrayList<>();

            //Collect all data to the only collection
            for (DocketApi docket : dockets) {

                List<Class> api = filterClasses(apis, docket.getPathMapping());

                ApiStruct apiStruct = ApiMapper.toStruct(api);

                String hostName = Optional
                        .ofNullable(docket.getHost())
                        .orElse("host_placeholder");

                jsons.add(jsonGenerator.createJson(hostName, apiStruct));
            }

            return jsons.get(0);
        }
        return null;
    }

    public List<Class> getClasses() {
        //These are beans-implementations, not interfaces
        Collection<Object> beansImpls = context.getBeansWithAnnotation(Api.class).values();

        List<Class> apis = new ArrayList<>();

        // get target class from each bean
        // get interfaces from target class
        // filter interface annotated with @Api
        for (Object beansImpl : beansImpls) {
            Class classImpl = getRealClass(beansImpl);
            Class[] interfaces = classImpl.getInterfaces();
            for (Class interfacesItem : interfaces) {
                if (interfacesItem.getAnnotation(Api.class) != null) {
                    apis.add(interfacesItem);
                }
            }
        }
        return apis;
    }

    private Class<?> getRealClass(Object beansImpl) {
        if (beansImpl instanceof TargetClassAware) {
            return ((TargetClassAware)beansImpl).getTargetClass();
        }
        return beansImpl.getClass();
    }

    public List<Class> filterClasses(List<Class> apis, String pathMapping) {
        if (StringUtils.isEmpty(pathMapping)) {
            return apis;
        }
        return apis.stream()
                .filter(c -> ReflectionUtils.getPath(c).contains(pathMapping))
                .collect(Collectors.toList());
    }

    public String getJson() {
        return swaggerJson;
    }
}
