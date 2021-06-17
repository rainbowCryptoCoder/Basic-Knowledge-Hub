package com.basicknowledgehub.basicknowledgehub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.SignInButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    NavigationView navigationView;

    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    private Vibrator v;
    EditText search_txt;
    TextView search_txt2;
    TextView search_txt3;
    ImageView userPhoto;
    TextView userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_Layout);
        toolbar = findViewById(R.id.toolbar);
        search_txt = findViewById(R.id.search_ed_txt);
        search_txt2 = findViewById(R.id.search_txt);
        search_txt3 = findViewById(R.id.search_txt2);
        userName = findViewById(R.id.user_name);
        userPhoto = findViewById(R.id.user_profile_img);

        //Initializing the Firebase
        mAuth = FirebaseAuth.getInstance();
        //Getting the current user
        currentUser = mAuth.getCurrentUser();

        if (currentUser!=null){
            //Getting the user name from "currentUser" and setting it in the textView
            userName.setText(currentUser.getDisplayName());
            //Using Glide to display the user image
            Glide.with(this).load(currentUser.getPhotoUrl()).into(userPhoto);
        }
        else {
        }

        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = findViewById(R.id.nav_view);
        updateNavView();
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
                        intent_contact = new Intent(MainActivity.this, Web_view_contact_us.class);
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
                        Vibrate(150);
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(getApplicationContext(), LoginPage.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    private void updateNavView() {
        navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);

        if (currentUser != null){
            TextView userMail = headerView.findViewById(R.id.tv_user_mail);
//        ImageView userPhoto2 = headerView.findViewById(R.id.user_profile_img2);

            userMail.setText(currentUser.getEmail());
//        Glide.with(this).load(currentUser.getPhotoUrl()).into(userPhoto2);
        }
        else {
        }
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
        search_txt.setVisibility(View.VISIBLE);
        search_txt2.setVisibility(View.INVISIBLE);
        search_txt3.setVisibility(View.INVISIBLE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInputFromWindow(search_txt.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
        search_txt.requestFocus();

        search_txt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        Intent intent = new Intent(MainActivity.this, web_view.class);
                        String query = search_txt.getText().toString();
                        intent.putExtra("url", "https://basicknowledgehub.com/" + query);
                        startActivity(intent);
                    return true;
                }
                    return false;
            }
        });
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
        intent_search.putExtra("url", "https://basicknowledgehub.com/category/seo-tips-tricks/");
        startActivity(intent_search);
    }

    public void card_iphone(View view) {
        Intent intent_search;
        intent_search = new Intent(this, web_view.class);
        intent_search.putExtra("url", "https://basicknowledgehub.com/category/iphone-tips-tricks/");
        startActivity(intent_search);
    }

    public void card_mobile(View view) {
        Intent intent_search;
        intent_search = new Intent(this, web_view.class);
        intent_search.putExtra("url", "https://basicknowledgehub.com/category/mobile-tips-tricks/");
        startActivity(intent_search);
    }

    public void card_android(View view) {
        Intent intent_search;
        intent_search = new Intent(this, web_view.class);
        intent_search.putExtra("url", "https://basicknowledgehub.com/category/samsung-tips-tricks/");
        startActivity(intent_search);
    }

    public void card_how_to_fix(View view) {
        Intent intent_search;
        intent_search = new Intent(this, Web_view_howtofix.class);
        intent_search.putExtra("url", "https://basicknowledgehub.com/category/how-to-fix/");
        startActivity(intent_search);
    }

    public void card_app_tricks(View view) {
        Intent intent_search;
        intent_search = new Intent(this, web_view.class);
        intent_search.putExtra("url", "https://basicknowledgehub.com/category/app-tips-tricks/");
        startActivity(intent_search);
    }

    public void root_search(View view) {
        Intent intent_search;
        intent_search = new Intent(this, web_view.class);
        intent_search.putExtra("url", "https://basicknowledgehub.com/category/root-tips-tricks/");
        startActivity(intent_search);
    }
}