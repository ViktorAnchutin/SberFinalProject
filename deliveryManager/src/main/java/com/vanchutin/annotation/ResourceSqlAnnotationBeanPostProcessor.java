package com.vanchutin.annotation;

import com.vanchutin.annotation.ResourceSql;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Paths;

@Component
@Slf4j
public class ResourceSqlAnnotationBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

       // System.out.println("I'm here!!!!!!!!!!");

        Field[] fields = bean.getClass().getDeclaredFields();
        for(Field field : fields){
            ResourceSql annotation = field.getAnnotation(ResourceSql.class);
            if(annotation!=null){
                String fileName = annotation.value();
                String query = readQuery(fileName);
                field.setAccessible(true);
                ReflectionUtils.setField(field, bean, query);
            }
        }
        return null;
    }

    private String readQuery(String fileName){
        String query = null;
        String currentDirectory = Paths.get("").toAbsolutePath().toString();
        String resourceFilePath = String.format("%s/src/main/resources/sql/%s",currentDirectory,fileName);
        try(BufferedReader reader = new BufferedReader(new FileReader(new File(resourceFilePath)))){
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while ( (line = reader.readLine()) != null){
                stringBuilder.append(line);
                stringBuilder.append(" ");
            }
            query = stringBuilder.toString();
        } catch (IOException e){
            log.error(String.format("Failed to extract sql query from the file. %s", e));
        }
        return query;
    }
}
