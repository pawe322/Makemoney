package pawe322dev.makemoney;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import pawe322dev.makemoney.Adapter.IntroViewPagerAdapter;
import pawe322dev.makemoney.Model.ScreenItem;

public class IntroActivity extends AppCompatActivity {

    private ViewPager screenPager;
    private IntroViewPagerAdapter introViewPagerAdapter;
    private TabLayout tabIndicator;
    private Button btnNext, btnGetStarted, btnSkip;
    private int position = 0;
    private Animation btnAnim;
    private List<ScreenItem> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // make the activity on full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // check if first start
        if(RestorePrefData()) {
            LaunchMainActivity();
        }

        setContentView(R.layout.activity_intro);

        // ini views
        InitializeView();

        AddListenersToViewElements();

    }

    private void InitializeView() {
        btnNext = findViewById(R.id.btn_next);
        btnGetStarted = findViewById(R.id.btn_get_started);
        btnSkip = findViewById(R.id.btn_skip);
        tabIndicator = findViewById(R.id.tab_indicator);
        btnAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.button_animation);
    }

    private void AddListenersToViewElements() {
        // fill list screen
        mList = new ArrayList<>();
        mList.add(new ScreenItem(R.string.introFirstTitle,R.string.introFirstDesc,R.mipmap.ic_launcher_round));
        mList.add(new ScreenItem(R.string.introSecondTitle,R.string.introSecondDesc,R.drawable.first));
        mList.add(new ScreenItem(R.string.introThirdTitle,R.string.introThirdDesc,R.drawable.second));
        mList.add(new ScreenItem(R.string.introFourthTitle,R.string.introFourthDesc,R.drawable.third));

        // setup viewpager
        screenPager = findViewById(R.id.screen_viewpager);
        introViewPagerAdapter = new IntroViewPagerAdapter(this, mList);
        screenPager.setAdapter(introViewPagerAdapter);

        // setup tabLayout with viewpager
        tabIndicator.setupWithViewPager(screenPager);

        // next button click Listener
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position = screenPager.getCurrentItem();
                if(position < mList.size()) {
                    position++;
                    screenPager.setCurrentItem(position);
                }
                if(position == mList.size()) {
                    LoadLastScreen();
                }
            }
        });

        // tabLayout add change listener
        tabIndicator.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == mList.size()-1) {
                    LoadLastScreen();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // Get Started button click listener
        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SavePrefsData();
                LaunchMainActivity();
            }
        });

        // Skip button click listener
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SavePrefsData();
                LaunchMainActivity();
            }
        });
    }

    // show the GETSTARTED button and hide the indicator and the next button
    private void LoadLastScreen() {
        if(!btnGetStarted.isShown()) {
            btnNext.setVisibility(View.INVISIBLE);
            tabIndicator.setVisibility(View.INVISIBLE);
            btnGetStarted.setVisibility(View.VISIBLE);
            btnGetStarted.setAnimation(btnAnim);
        }
    }

    private void SavePrefsData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isIntroOpened",true);
        editor.commit();
    }

    private boolean RestorePrefData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        Boolean isFirstStart = pref.getBoolean("isIntroOpened", false);
        return isFirstStart;
    }

    private void LaunchMainActivity() {
        Intent mainActivity = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(mainActivity);
        finish();
    }
}
