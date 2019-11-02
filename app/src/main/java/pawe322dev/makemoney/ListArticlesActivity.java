package pawe322dev.makemoney;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.squareup.picasso.Picasso;

import pawe322dev.makemoney.Utils.Utils;

public class ListArticlesActivity extends AppCompatActivity {

    private ImageView titleImage;
    private TextView titleTextView, contentTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_article);

        initialize();

        Picasso.get().load(Utils.selected_article.getImageLink()).into(titleImage);
        titleTextView.setText(Utils.selected_article.getTitle());
        contentTextView.setText(Utils.selected_article.getContent());

    }

    private void initialize() {
        titleImage = (ImageView) findViewById(R.id.titleImage);
        titleTextView = (TextView) findViewById(R.id.titleTextView);
        contentTextView = (TextView) findViewById(R.id.contentTextView);
    }

}
