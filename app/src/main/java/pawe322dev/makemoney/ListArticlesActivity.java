package pawe322dev.makemoney;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import pawe322dev.makemoney.CategoryViewHolder.WallpaperViewHolder;
import pawe322dev.makemoney.Model.FullArticleItem;
import pawe322dev.makemoney.Utils.Utils;

public class ListArticlesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Query query;

    private FirebaseRecyclerOptions<FullArticleItem> options;
    private FirebaseRecyclerAdapter<FullArticleItem,WallpaperViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_wallpaper);
        recyclerView = (RecyclerView) findViewById(R.id.ryWallpaper);
        recyclerView.setHasFixedSize(true);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        query = FirebaseDatabase.getInstance().getReference("Articles")
                .orderByChild("articleId").equalTo(Utils.ARTICLE_ID);

        options = new FirebaseRecyclerOptions.Builder<FullArticleItem>()
                .setQuery(query, FullArticleItem.class).build();

        adapter = new FirebaseRecyclerAdapter<FullArticleItem, WallpaperViewHolder>(options) {
            @Override
            protected void onBindViewHolder(final WallpaperViewHolder holder, final int position, final FullArticleItem model) {

                Picasso.get().load(model.getImageLink())
                        .networkPolicy(NetworkPolicy.OFFLINE)
                        .into(holder.imageView, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError(Exception e) {
                                Picasso.get().load(model.getImageLink())
                                        .error(R.drawable.ic_terrain_black_24dp)
                                        .into(holder.imageView);
                            }
                        });

                holder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Utils.ARTICLE_ID = adapter.getRef(position).getKey();
                        Utils.ARTICLE_SELECTED = model.articleId;
                        Utils.selected_article = model;

                        Intent my = new Intent(getApplicationContext(), ViewArticleActivity.class);
                        startActivity(my);
                    }
                });
            }

            @Override
            public WallpaperViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_wallpaper_item, parent, false);

                int height = parent.getMeasuredHeight() / 2;
                v.setMinimumHeight(height);

                return new WallpaperViewHolder(v);
            }
        };

        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(adapter!=null){
            adapter.startListening();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(adapter!=null){
            adapter.startListening();
        }
    }

    @Override
    protected void onDestroy() {
        if(adapter!=null){
            adapter.stopListening();
        }
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        if(adapter!=null){
            adapter.stopListening();
        }
        super.onStop();
    }

}
