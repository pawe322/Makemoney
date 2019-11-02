package pawe322dev.makemoney;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.squareup.picasso.Picasso;

import pawe322dev.makemoney.Utils.Utils;

public class ListArticlesActivity extends AppCompatActivity {

    private ImageView titleImage;
    private TextView titleTextView, contentTextView;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_article);

        initialize();

        Picasso.get().load(Utils.selected_article.getImageLink()).into(titleImage);
        titleTextView.setText(Utils.selected_article.getTitle());
        contentTextView.setText(Utils.selected_article.getContent());
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.selected_article.getRegisterLink() != null) {
                    Uri uriUrl = Uri.parse(Utils.selected_article.getRegisterLink());
                    Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                    startActivity(launchBrowser);
                }
            }
        });
    }

    private void initialize() {
        titleImage = (ImageView) findViewById(R.id.titleImage);
        titleTextView = (TextView) findViewById(R.id.titleTextView);
        contentTextView = (TextView) findViewById(R.id.contentTextView);
        registerButton = (Button) findViewById(R.id.registerButton);
    }

}
