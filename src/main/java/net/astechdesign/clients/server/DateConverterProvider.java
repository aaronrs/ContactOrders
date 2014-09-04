package net.astechdesign.clients.server;

import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverterProvider implements ParamConverterProvider {

    @Override
    public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
        if (rawType == Date.class)
        return new ParamConverter() {
            @Override
            public Object fromString(String value) {
                try {
                    return new SimpleDateFormat("yyyy-MM-dd").parse(value);
                } catch (ParseException e) {
                    return new Date();
                }
            }

            @Override
            public String toString(Object value) {
                return new SimpleDateFormat("yyyy-MM-dd").format(value);
            }
        };
        return null;
    }
}
