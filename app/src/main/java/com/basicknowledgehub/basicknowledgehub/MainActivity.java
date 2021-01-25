package com.basicknowledgehub.basicknowledgehub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    NavigationView navigationView;
    private Vibrator v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_Layout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id){
                    case R.id.home:
                        Intent intent_home;
                        intent_home = new Intent(MainActivity.this, web_view.class);
                        intent_home.putExtra("url", "https://basicknowledgehub.com/");
                        startActivity(intent_home);
                        break;

                    case R.id.privacy:
                        Intent intent_privacy;
                        intent_privacy = new Intent(MainActivity.this, web_view.class);
                        intent_privacy.putExtra("url", "https://basicknowledgehub.com/privacy-policy/");
                        startActivity(intent_privacy);
                        break;

                    case R.id.contact_us:
                        Intent intent_contact;
                        intent_contact = new Intent(MainActivity.this, web_view.class);
                        intent_contact.putExtra("url", "https://basicknowledgehub.com/contact-us/");
                        startActivity(intent_contact);
                        break;

                    case R.id.about_us:
                        Intent intent_about;
                        intent_about = new Intent(MainActivity.this, web_view.class);
                        intent_about.putExtra("url", "https://basicknowledgehub.com/about-us/");
                        startActivity(intent_about);
                        break;

                    case R.id.rate_us:
                        Vibrate(150);
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("market://details?id=" + "com.basicknowledgehub.basicknowledgehub")));
                        }
                        catch (Exception e){
                            startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("https://play.google.com/store/apps/details?id=com.gossipfunda.gossipfunda_2")));
                        }
                        break;

                    case R.id.log_out:
                        break;
                }
                return true;
            }
        });
    }

    public void Vibrate(long milliSecond){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        { ((Vibrator)getSystemService(VIBRATOR_SERVICE))
                    .vibrate(VibrationEffect.createOneShot(milliSecond, VibrationEffect.DEFAULT_AMPLITUDE));
        }
        else {
            ((Vibrator)getSystemService(VIBRATOR_SERVICE)).vibrate(milliSecond);
        }
    }


    public void card_search(View view) {
        Vibrate(80);
        Intent intent_search;
        intent_search = new Intent(this, web_view.class);
        intent_search.putExtra("url", "https://basicknowledgehub.com/");
        startActivity(intent_search);
    }

    public void card_blogging(View view) {
        Intent intent_search;
        intent_search = new Intent(this, web_view.class);
        intent_search.putExtra("url", "https://basicknowledgehub.com/category/blogging-tips-tricks/");
        startActivity(intent_search);
    }

    public void card_seo(View view) {
        Intent intent_search;
        intent_search = new Intent(this, web_view.class);
        intent_search.putExtra("url", "https://basicknowledgehub.com/category/computer-tips-tricks/");
        startActivity(intent_search);
    }

    public void card_iphone(View view) {
        Intent intent_search;
        intent_search = new Intent(this, web_view.class);
        intent_search.putExtra("url", "https://basicknowledgehub.com/category/iphone-tips-tricks/");
        startActivity(intent_search);
    }

    public void card_google(View view) {
        Intent intent_search;
        intent_search = new Intent(this, web_view.class);
        intent_search.putExtra("url", "https://basicknowledgehub.com/category/google-tips-tricks/");
        startActivity(intent_search);
    }

    public void card_android(View view) {
        Intent intent_search;
        intent_search = new Intent(this, web_view.class);
        intent_search.putExtra("url", "https://basicknowledgehub.com/category/android-tips-tricks/");
        startActivity(intent_search);
    }
}