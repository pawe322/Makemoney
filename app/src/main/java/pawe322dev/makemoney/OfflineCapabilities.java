package pawe322dev.makemoney;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

public class OfflineCapabilities extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Store Firebase String Data in cache
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        // Store Firebase Image Data in cache
        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttp3Downloader(this,Integer.MAX_VALUE));
        Picasso built = builder.build();
        built.setIndicatorsEnabled(false);
        built.setLoggingEnabled(true);
        Picasso.setSingletonInstance(built);
    }
}
