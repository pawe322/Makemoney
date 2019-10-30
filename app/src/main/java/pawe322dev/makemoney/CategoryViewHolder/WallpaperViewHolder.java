package pawe322dev.makemoney.CategoryViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import pawe322dev.makemoney.R;

public class WallpaperViewHolder extends RecyclerView.ViewHolder {

    public ImageView imageView;

    public WallpaperViewHolder(View itemView) {
        super(itemView);

        imageView = (ImageView)itemView.findViewById(R.id.imageViewWallpaper);

    }
}
