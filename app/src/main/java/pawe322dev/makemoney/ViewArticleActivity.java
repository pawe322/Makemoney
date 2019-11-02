package pawe322dev.makemoney;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.squareup.picasso.Picasso;

import java.util.UUID;

import pawe322dev.makemoney.Helper.SaveImageHelper;
import pawe322dev.makemoney.Helper.SetImageHelper;
import pawe322dev.makemoney.Utils.Utils;

public class ViewArticleActivity extends AppCompatActivity {

    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Button fabDownload, fabWallpaper;
    private ImageView i1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_wallpaper);

        initialize();

        Picasso.get().load(Utils.selected_article.getImageLink()).into(i1);

        fabWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder b = new AlertDialog.Builder(ViewArticleActivity.this);
                b.setMessage("Setting as wallpaper..");
                AlertDialog alertDialog = b.create();
                alertDialog.show();

                Picasso.get().load(Utils.selected_article.getImageLink()).into(new SetImageHelper(getBaseContext(),alertDialog,getApplicationContext().getContentResolver()));

            }
        });

        fabDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fileName = UUID.randomUUID().toString()+".png";

                AlertDialog.Builder b = new AlertDialog.Builder(ViewArticleActivity.this);
                b.setMessage("Downloading..");
                AlertDialog alertDialog = b.create();
                alertDialog.show();

                Picasso.get().load(Utils.selected_article.getImageLink()).into(new SaveImageHelper(getBaseContext(),alertDialog,getApplicationContext().getContentResolver(),fileName,"Mini Image"));
            }
        });

    }

    private void initialize() {
        i1 = (ImageView) findViewById(R.id.thumbImage);
        fabDownload = (Button)findViewById(R.id.fab_download);
        fabDownload.setWidth(halfOfScreen());
        fabWallpaper = (Button)findViewById(R.id.fab_wallpaper);
        fabWallpaper.setWidth(halfOfScreen());
    }

    private int halfOfScreen() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = displaymetrics.widthPixels;
        int buttonWidth = width/2 - 50;
        return buttonWidth;
    }


}
