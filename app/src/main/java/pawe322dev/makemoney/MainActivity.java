package pawe322dev.makemoney;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import pawe322dev.makemoney.CategoryViewHolder.ArticlesViewHolder;
import pawe322dev.makemoney.Helper.ConnectivityHelper;
import pawe322dev.makemoney.Model.FullArticleItem;
import pawe322dev.makemoney.Utils.Utils;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private RecyclerView recyclerView;
    private DatabaseReference categoryBackgroundReference;
    private FirebaseRecyclerOptions<FullArticleItem> options;
    private DrawerLayout drawer;
    private GridLayoutManager gridLayoutManager;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    private String sponsorsUrl, instagramUrl, facebookUrl, privacyPolicyUrl, FirebaseUnitID, rateAppUrl;
    private AdView mAdView;

    private FirebaseRecyclerAdapter<FullArticleItem, ArticlesViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

//        GetFirebaseAdmobData();
        GetFirebaseConnection();
        InitializeView();
        AddListenersToViewElements();
        GetFirebaseUrls();
    }

    private void GetFirebaseUrls() {
        GetFirebaseInstagramUrl();
        GetFirebaseFacebookUrl();
        GetFirebaseRateAppUrl();
    }

    private void GetFirebaseInstagramUrl() {
        Firebase.setAndroidContext(this);
        Firebase firebaseConnection = new Firebase("https://extra-money-ideas.firebaseio.com/instagram");
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
        Firebase firebaseConnection = new Firebase("https://extra-money-ideas.firebaseio.com/facebook");
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

    private void GetFirebaseRateAppUrl() {
        Firebase.setAndroidContext(this);
        Firebase firebaseConnection = new Firebase("https://extra-money-ideas.firebaseio.com/rate_app");
        firebaseConnection.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                rateAppUrl = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private void GetFirebaseAdmobData(){
        Firebase.setAndroidContext(this);
        Firebase adMobFirebase = new Firebase("https://extra-money-ideas.firebaseio.com/admob");
        adMobFirebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FirebaseUnitID = dataSnapshot.getValue(String.class);
                LoadAdMobAd(FirebaseUnitID);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private void LoadAdMobAd(String unitId){
        View adContainer = findViewById(R.id.adMobView);
        mAdView = new AdView(this);
        mAdView.setAdSize(AdSize.BANNER);
        mAdView.setAdUnitId(unitId);
        ((RelativeLayout)adContainer).addView(mAdView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    private void goToUrl(String url) {
        if (url != null) {
            Uri uriUrl = Uri.parse(url);
            Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
            startActivity(launchBrowser);
        }
    }

    private void GetFirebaseConnection() {
        categoryBackgroundReference = FirebaseDatabase.getInstance().getReference().child("Articles");
        categoryBackgroundReference.keepSynced(true);

        options = new FirebaseRecyclerOptions.Builder<FullArticleItem>()
                .setQuery(categoryBackgroundReference, FullArticleItem.class).build();

        adapter = new FirebaseRecyclerAdapter<FullArticleItem, ArticlesViewHolder>(options) {
            @Override
            protected void onBindViewHolder(final ArticlesViewHolder holder, final int position, final FullArticleItem model) {

                if (model.getImageLink() != null && !model.getImageLink().equals("")) {
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
                }
                holder.idTextView.setText("#" + model.getArticleId());
                holder.titleTextView.setText(model.getTitle());
                holder.subtitleTextView.setText(model.getSubtitle());

                holder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Utils.ARTICLE_ID = adapter.getRef(position).getKey();
                        Utils.ARTICLE_SELECTED = model.getTitle();
                        Utils.selected_article = model;

                        Intent i = new Intent(MainActivity.this, ListArticlesActivity.class);
                        startActivity(i);

                    }
                });
            }

            @NonNull
            @Override
            public ArticlesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_article_item,parent,false);

                return new ArticlesViewHolder(v);
            }
        };
    }

    private void InitializeView() {
        recyclerView = findViewById(R.id.recyclerview);
        gridLayoutManager = new GridLayoutManager(getApplicationContext(),1);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar_main);
        drawer = findViewById(R.id.drawer_layout);
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
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(ConnectivityHelper.isConnectedToNetwork(getApplicationContext())) {
//            if (id == R.id.instagram) {
//                    goToUrl(instagramUrl);
//            } else if (id == R.id.facebook) {
//                    goToUrl(facebookUrl);
    //        } else if (id == R.id.gymcalculator) {
    //            if(ConnectivityHelper.isConnectedToNetwork(getApplicationContext())) {
    //                goToUrl(gymCalculatorUrl);
//            } else
            if (id == R.id.privacy_policy) {
                    goToUrl("https://sites.google.com/view/extramoneyideasprivacypolicy");
            }
            else if (id == R.id.rate_app) {
                    goToUrl(rateAppUrl);
            }
        } else {
            Snackbar snackbar = Snackbar.make(recyclerView,"No internet connection.", Snackbar.LENGTH_SHORT);
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
