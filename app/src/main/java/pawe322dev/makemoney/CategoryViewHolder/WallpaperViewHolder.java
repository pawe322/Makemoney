package pawe322dev.makemoney.CategoryViewHolder;

import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import pawe322dev.makemoney.R;

public class WallpaperViewHolder extends RecyclerView.ViewHolder {

    public ImageView imageView;

    public WallpaperViewHolder(View itemView) {
        super(itemView);

        imageView = (ImageView)itemView.findViewById(R.id.imageViewWallpaper);

    }
}
