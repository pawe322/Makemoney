package pawe322dev.makemoney.CategoryViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import pawe322dev.makemoney.R;

public class ArticlesViewHolder extends RecyclerView.ViewHolder {

    public ImageView imageView;
    public TextView titleTextView;
    public TextView subtitleTextView;

    public ArticlesViewHolder(View itemView) {
        super(itemView);
        imageView = (ImageView)itemView.findViewById(R.id.imageView);
        titleTextView = (TextView)itemView.findViewById(R.id.titleTextView);
        subtitleTextView = (TextView)itemView.findViewById(R.id.subTitleTextView);

    }
}
