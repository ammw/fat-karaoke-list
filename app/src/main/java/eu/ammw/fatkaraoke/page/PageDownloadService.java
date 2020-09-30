package eu.ammw.fatkaraoke.page;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Function;

import javax.inject.Inject;

public class PageDownloadService {
    private static final String TAG = "FKA DOWNLD";

    private final Function<String, HttpURLConnection> connectionProvider;

    @Inject
    PageDownloadService() {
        this(PageDownloadService::defaultConnectionProvider);
    }

    PageDownloadService(Function<String, HttpURLConnection> connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    String download(String pageUrl) throws IOException {
        InputStream is = null;

        try {
            HttpURLConnection conn = connectionProvider.apply(pageUrl);
            conn.setReadTimeout(300_000);
            conn.setConnectTimeout(15_000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);

            conn.connect();
            if (conn.getResponseCode() != 200) {
                throw new IOException("HTTP Error: code " + conn.getResponseCode());
            }

            is = conn.getInputStream();
            return readStream(is);
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    private String readStream(InputStream input) {
        return new Scanner(input, "UTF-8").useDelimiter("\\A").next();
    }

    private static HttpURLConnection defaultConnectionProvider(String url) {
        try {
            return (HttpURLConnection) (new URL(url).openConnection());
        } catch (IOException e) {
            Log.e(TAG, "Incorrect list URL, this should never happen: " + url, e);
            return null;
        }
    }
}
