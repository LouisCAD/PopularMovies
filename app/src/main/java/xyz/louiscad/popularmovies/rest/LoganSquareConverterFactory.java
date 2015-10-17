package xyz.louiscad.popularmovies.rest;

import com.squareup.okhttp.ResponseBody;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import retrofit.Converter;

/**
 * Created by Louis Cognault on 10/10/15.
 */
public class LoganSquareConverterFactory extends Converter.Factory {
    public LoganSquareConverterFactory() {
    }

    @Override
    public Converter<ResponseBody, ?> fromResponseBody(Type type, Annotation[] annotations) {
        return new LoganSquareResponseBodyConverter<>(type);
    }

}
