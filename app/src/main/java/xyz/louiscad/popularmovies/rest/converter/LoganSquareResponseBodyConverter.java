package xyz.louiscad.popularmovies.rest.converter;

import com.bluelinelabs.logansquare.LoganSquare;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.lang.reflect.Type;

import retrofit.Converter;

/**
 * Created by Louis Cognault on 10/10/15.
 */
public class LoganSquareResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private final Type mType;

    public LoganSquareResponseBodyConverter(Type type) {
        mType = type;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T convert(ResponseBody value) throws IOException {
        return LoganSquare.parse(value.byteStream(), (Class<T>) mType);
    }
}
