package pawe322dev.makemoney;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import pawe322dev.makemoney.CategoryViewHolder.CategoryViewHolder;
import pawe322dev.makemoney.Helper.ConnectivityHelper;
import pawe322dev.makemoney.Model.CategoryItem;
import pawe322dev.makemoney.Utils.Utils;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private RecyclerView recyclerView;
    private DatabaseReference categoryBackgroundReference;
    private FirebaseRecyclerOptions<CategoryItem> options;
    private DrawerLayout drawer;
    private GridLayoutManager gridLayoutManager;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    private String gymCalculatorUrl, sponsorsUrl, instagramUrl, facebookUrl, privacyPolicyUrl;

    private FirebaseRecyclerAdapter<CategoryItem, CategoryViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GetFirebaseConnection();
        InitializeView();
        GetFirebaseUrls();
    }

    private void GetFirebaseUrls() {
        GetFirebaseInstagramUrl();
        GetFirebaseFacebookUrl();
        GetFirebaseGymCalculatorUrl();
        GetFirebaseSponsorsUrl();
    }

    private void GetFirebaseInstagramUrl() {
        Firebase.setAndroidContext(this);
        Firebase firebaseConnection = new Firebase("https://gymwallpapers.firebaseio.com/instagram");
        firebaseConnection.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                instagramUrl = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private void GetFirebaseFacebookUrl() {
        Firebase.setAndroidContext(this);
        Firebase firebaseConnection = new Firebase("https://gymwallpapers.firebaseio.com/facebook");
        firebaseConnection.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                facebookUrl = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private void GetFirebaseGymCalculatorUrl() {
        Firebase.setAndroidContext(this);
        Firebase firebaseConnection = new Firebase("https://gymwallpapers.firebaseio.com/gymcalculator");
        firebaseConnection.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                gymCalculatorUrl = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private void GetFirebaseSponsorsUrl() {
        Firebase.setAndroidContext(this);
        Firebase firebaseConnection = new Firebase("https://gymwallpapers.firebaseio.com/sponsors");
        firebaseConnection.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                sponsorsUrl = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private void goToUrl (String url) {
        if (url != null) {
            Uri uriUrl = Uri.parse(url);
            Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
            startActivity(launchBrowser);
        };
    }

    private void GetFirebaseConnection() {
        categoryBackgroundReference = FirebaseDatabase.getInstance().getReference().child("CategoryBackground");

        options = new FirebaseRecyclerOptions.Builder<CategoryItem>()
                .setQuery(categoryBackgroundReference,CategoryItem.class).build();

        adapter = new FirebaseRecyclerAdapter<CategoryItem, CategoryViewHolder>(options) {
            @Override
            protected void onBindViewHolder(final CategoryViewHolder holder, final int position, final CategoryItem model) {

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
                holder.textView.setText(model.getName());

                holder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Utils.CATEGORY_ID = adapter.getRef(position).getKey();
                        Utils.CATEGORY_SELECTED = model.getName();

                        Intent i = new Intent(MainActivity.this,ListWallpaperActivity.class);
                        startActivity(i);

                    }
                });
            }

            @NonNull
            @Override
            public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_category_item,parent,false);

                return new CategoryViewHolder(v);
            }
        };
    }

    private void InitializeView() {
        recyclerView = findViewById(R.id.recyclerview);
        gridLayoutManager = new GridLayoutManager(getApplicationContext(),1);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar_main);
        drawer = findViewById(R.id.drawer_layout);

        AddListenersToViewElements();
    }

    private void AddListenersToViewElements() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter.startListening();
        recyclerView.setAdapter(adapter);
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
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
    protected void onStop() {
        if(adapter!=null){
            adapter.startListening();
        }
        super.onStop();
    }

    @Override
    public boolean onNavigationItemSelected( MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(ConnectivityHelper.isConnectedToNetwork(getApplicationContext())) {
            if (id == R.id.instagram) {
                    goToUrl(instagramUrl);
            } else if (id == R.id.facebook) {
                    goToUrl(facebookUrl);
    //        } else if (id == R.id.gymcalculator) {
    //            if(ConnectivityHelper.isConnectedToNetwork(getApplicationContext())) {
    //                goToUrl(gymCalculatorUrl);
            } else if (id == R.id.privacy_policy) {
                    goToUrl("https://sites.google.com/view/gymwallpapersprivacypolicy");
            } else if (id == R.id.sponsors) {
                    goToUrl(sponsorsUrl);
            }
        } else {
            Snackbar snackbar = Snackbar.make(recyclerView,"No internet connection.",Snackbar.LENGTH_SHORT);
            snackbar.show();
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
