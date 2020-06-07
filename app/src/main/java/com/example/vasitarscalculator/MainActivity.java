package com.example.vasitarscalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.infideap.drawerbehavior.Advance3DDrawerLayout;

import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Advance3DDrawerLayout drawer;
    public static String FACEBOOK_URL = "https://www.facebook.com/vasitars/";
    public static String FACEBOOK_PAGE_ID = "vasitars";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorBlack));
        setSupportActionBar(toolbar);
        // changeToolbarFont((Toolbar) findViewById(R.id.toolbar), this);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        drawer = (Advance3DDrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        drawer.setViewScale(Gravity.START, 0.9f);
        drawer.setRadius(Gravity.START, 20);
        drawer.setViewElevation(Gravity.START, 100);
        drawer.setViewRotation(Gravity.START, 5);

        drawer.setViewScale(Gravity.END, 0.9f);
        drawer.setRadius(Gravity.END, 35);
        drawer.setViewElevation(Gravity.END, 20);


        // Fragment Main
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment,new mainFirst()).commit();
        //


    }
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (drawer.isDrawerOpen(GravityCompat.END)){
                drawer.closeDrawer(GravityCompat.END);
            } else {
                super.onBackPressed();
            }
        }


    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getSupportFragmentManager();
        int j = fragmentManager.getBackStackEntryCount();
        if(id == R.id.nav_home){
            if(j != 0){
                for(int i = 0; i < j; ++i) {
                    fragmentManager.popBackStack();
                }
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations( R.anim.fade_in, 0, 0, R.anim.fade_out).replace(R.id.fragment,new mainFirst()).commit();
            }drawer.closeDrawer(GravityCompat.START);
        }else if(id == R.id.nav_leaking){
            if(j != 0){
                for(int i = 0; i < j; ++i) {
                    fragmentManager.popBackStack();
                }
            }
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations( R.anim.fade_in, 0, 0, R.anim.fade_out).replace(R.id.fragment,new leakingCalculate()).addToBackStack("leaking").commit();
            drawer.closeDrawer(GravityCompat.START);
        }else if(id == R.id.nav_nonLeaking){
            if(j != 0){
                for(int i = 0; i < j; ++i) {
                    fragmentManager.popBackStack();
                }
            }
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations( R.anim.fade_in, 0, 0, R.anim.fade_out).replace(R.id.fragment,new nonLeakingCalculate()).addToBackStack("nonleaking").commit();
            drawer.closeDrawer(GravityCompat.START);
        }else if(id == R.id.nav_contact){
            drawer.closeDrawer(GravityCompat.START);
            drawer.openDrawer(GravityCompat.END);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_right_drawer:
                drawer.openDrawer(GravityCompat.END);
                //getWindow().setStatusBarColor((ContextCompat.getColor(this,R.color.color3)));
                return true;

        }
        return super.onOptionsItemSelected(item);
    }



    public void onClickLeaking(View view) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations( R.anim.fade_in, 0, 0, R.anim.fade_out).replace(R.id.fragment,new leakingCalculate()).addToBackStack("leaking").commit();
    }

    public void onClickNonLeaking(View view) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations( R.anim.fade_in, 0, 0, R.anim.fade_out).replace(R.id.fragment,new nonLeakingCalculate()).addToBackStack("nonleaking").commit();
    }

    public void onClicklinkedin(View view){
        drawer.closeDrawer(GravityCompat.END);
        Intent intent=null;
        try {
            intent =new Intent(Intent.ACTION_VIEW);
            intent.setPackage("com.linkedin.android");
            intent.setData(Uri.parse("https://www.linkedin.com/company/vasitars-pvt.-ltd./"));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://www.linkedin.com/company/vasitars-pvt.-ltd./"));
            startActivity(intent);
        }
    }

    public void onClickyoutube(View view){
        drawer.closeDrawer(GravityCompat.END);
        Intent intent=null;
        try {
            intent =new Intent(Intent.ACTION_VIEW);
            intent.setPackage("com.google.android.youtube");
            intent.setData(Uri.parse("https://www.youtube.com/channel/UCIiLdfMBBecUO1yVDIfqFvQ"));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://www.youtube.com/channel/UCIiLdfMBBecUO1yVDIfqFvQ"));
            startActivity(intent);
        }
    }

    public void onClicktweet(View view){
        drawer.closeDrawer(GravityCompat.END);
        Intent intent=null;
        try {
            intent =new Intent(Intent.ACTION_VIEW);
            intent.setPackage("com.twitter.android");
            intent.setData(Uri.parse("twitter://user?screen_name=vasitars_iitbbs"));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://twitter.com/vasitars_iitbbs"));
            startActivity(intent);
        }
    }

    public void onClickinsta(View view){
        drawer.closeDrawer(GravityCompat.END);
        Uri uri = Uri.parse("https://www.instagram.com/vasitars/");
        Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

        likeIng.setPackage("com.instagram.android");

        try {
            startActivity(likeIng);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.instagram.com/vasitars/")));
        }
    }

    public String getFacebookPageURL() {
        PackageManager packageManager = getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                return "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            } else { //older versions of fb app
                return "fb://page/" + FACEBOOK_PAGE_ID;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return FACEBOOK_URL; //normal web url
        }
    }

    public void onClickfb(View view){
        drawer.closeDrawer(GravityCompat.END);
        Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
        String facebookUrl = getFacebookPageURL();
        facebookIntent.setData(Uri.parse(facebookUrl));
        startActivity(facebookIntent);
    }

    public void onClickwebsitee(View view){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://www.vasitars.com/"));
        Intent choser = Intent.createChooser(intent,"Choose Your Browser");
        startActivity(choser);
    }
}
