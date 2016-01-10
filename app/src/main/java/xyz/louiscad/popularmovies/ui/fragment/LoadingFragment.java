package xyz.louiscad.popularmovies.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import java.io.IOException;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import trikita.log.Log;

/**
 * Created by Louis Cognault on 10/01/16.
 */
public class LoadingFragment extends Fragment implements Callback {

    private Callback mCallback;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (Callback) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @SuppressWarnings("unchecked call")
    public void enqueue(Call request) {
        request.enqueue(this);
    }

    @Override
    @SuppressWarnings("unchecked call")
    public void onResponse(Response response, Retrofit retrofit) {
        if (response.isSuccess() && mCallback != null) mCallback.onResponse(response, retrofit);
        else onFailure(new IOException(response.message()));
    }

    @Override
    public void onFailure(Throwable t) {
        Log.d(t);
        if (mCallback != null) mCallback.onFailure(t);
    }

    public interface Caller {
        void enqueue(Call request);
    }
}
