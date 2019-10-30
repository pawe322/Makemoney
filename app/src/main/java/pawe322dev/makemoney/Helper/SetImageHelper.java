package pawe322dev.makemoney.Helper;

import android.app.AlertDialog;
import android.app.WallpaperManager;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.lang.ref.WeakReference;

public class SetImageHelper implements Target {

    private Context context;
    private WeakReference<AlertDialog> alertDialogWeakReference;
    private WeakReference<ContentResolver> contentResolverWeakReference;

    public SetImageHelper(Context context, AlertDialog alertDialog, ContentResolver contentResolver) {
        this.context = context;
        alertDialogWeakReference = new WeakReference<AlertDialog>(alertDialog);
        contentResolverWeakReference = new WeakReference<ContentResolver>(contentResolver);
    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {

    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        ContentResolver c = contentResolverWeakReference.get();
        AlertDialog aD = alertDialogWeakReference.get();

        final WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);

        if (c != null) {
            aD.dismiss();
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    wallpaperManager.setBitmap(bitmap,null,true,WallpaperManager.FLAG_SYSTEM);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            Toast.makeText(context, "Wallpaper set successfully", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
        Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
    }
}
