package pawe322dev.makemoney.CategoryViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import pawe322dev.makemoney.R;

public class CategoryViewHolder extends RecyclerView.ViewHolder {

    public ImageView imageView;
    public TextView textView;

    public CategoryViewHolder(View itemView) {
        super(itemView);
        imageView = (ImageView)itemView.findViewById(R.id.imageView);
        textView = (TextView)itemView.findViewById(R.id.textView);


    }
}
