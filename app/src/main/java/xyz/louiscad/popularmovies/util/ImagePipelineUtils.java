package xyz.louiscad.popularmovies.util;

import android.net.Uri;

import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;

import java.util.concurrent.Executor;

/**
 * Utilities for fresco Image Pipeline
 */
public class ImagePipelineUtils {

    private static ImagePipeline sPipeline;

    public static void subscribe(Executor executor, Uri uri, BaseBitmapDataSubscriber subscriber) {
        setPipelineIfNull();
        ImageRequest request = ImageRequest.fromUri(uri);
        DataSource<CloseableReference<CloseableImage>> dataSource;
        dataSource = sPipeline.fetchDecodedImage(request, null);
        dataSource.subscribe(subscriber, executor);
    }

    private static void setPipelineIfNull() {
        if (sPipeline == null) sPipeline = Fresco.getImagePipeline();
    }
}
