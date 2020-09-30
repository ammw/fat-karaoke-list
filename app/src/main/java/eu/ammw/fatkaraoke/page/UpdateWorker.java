package eu.ammw.fatkaraoke.page;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.concurrent.atomic.AtomicInteger;

import eu.ammw.fatkaraoke.dagger.MainModule;
import eu.ammw.fatkaraoke.db.SongDatabase;

public class UpdateWorker extends Worker {
    private static final String TAG = "FKA UPDWORK";

    private final AtomicInteger retryCount = new AtomicInteger(0);

    // TODO(ammw): Inject?
    private final PageUpdateService pageUpdateService;

    public UpdateWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);

        SongDatabase database = MainModule.songDatabase(getApplicationContext());
        PageParser parser = new PageParser();
        PageDownloadService downloadService = new PageDownloadService();
        pageUpdateService = new PageUpdateService(downloadService, parser, database);
    }

    @Override
    @NonNull
    public Result doWork() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            try {
                pageUpdateService.performUpdate();
                retryCount.set(0);
                return Result.success();
            } catch (Exception e) {
                Log.e(TAG, "Error while updating!", e);
                return retryCount.incrementAndGet() > 3 ? Result.failure() : Result.retry();
            }
        } else {
            Log.e(TAG, "No connection available!");
            return Result.failure();
        }
    }
}
