package com.eternity.duakelinci.ui;

import android.app.FragmentManager;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.eternity.duakelinci.BaseActivity;
import com.eternity.duakelinci.Kios;
import com.eternity.duakelinci.R;
import com.eternity.duakelinci.WisataIndustri;
import com.eternity.duakelinci.adapter.ListRouteAdapter;
import com.eternity.duakelinci.helper.RecyclerItemClickListener;
import com.eternity.duakelinci.model.LocationModel;
import com.eternity.duakelinci.prosesproduksi;
import com.eternity.duakelinci.tentangkami;
import com.eternity.duakelinci.waroengpati;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;


public class MainActivity extends BaseActivity implements RecyclerItemClickListener.OnItemClickListener,NavigationView.OnNavigationItemSelectedListener ,View.OnClickListener, ViewPagerEx.OnPageChangeListener, BaseSliderView.OnSliderClickListener {

    public static final int FROM_LOCAL = 0;
    public static final int FROM_NET = 1;


    @InjectView(R.id.fab)
    FloatingActionButton fab;

    private ListRouteAdapter adapterRoute;
    private List<LocationModel> locationModels;
    private Realm realm;
    public NavigationView navigationView = null;
    Toolbar toolbar = null;
    private SliderLayout mDemoSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setHomeButtonEnabled(true);
        mDemoSlider = (SliderLayout)findViewById(R.id.slider);
        realm = Realm.getInstance(this);

        locationModels = new ArrayList<>();
        adapterRoute = new ListRouteAdapter(this, locationModels);

        updateView();

        MainFragment fragment = new MainFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);

        //How to change elements in the header programatically
        View headerView = navigationView.getHeaderView(0);


        navigationView.setNavigationItemSelectedListener(this);

        ConnectivityManager cManager = (ConnectivityManager)
                getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cManager.getActiveNetworkInfo();
        if (nInfo != null && nInfo.isConnected()) {
        } else {
            View parentLayout = findViewById(R.id.drawer_layout);
            Snackbar.make(parentLayout, "Tidak terhubung ke jaringan.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Dapatkan berbagai macam produk Dua Kelinci di Kios Dua Kelinci\n",R.drawable.header1); //3
        file_maps.put("Anda akan diajak berkeliling pabrik dan melihat langsung proses produksi khususnya kacang garing\n",R.drawable.header2);  //2
        file_maps.put("Nikmati Promo Spesial dari Waroeng Pati Khusus Untuk Peserta Wisata Industri\n ",R.drawable.header_3); //
        file_maps.put("Mari Berwisata Industri, mengenal dunia Industri berwawasan IPTEK yang canggih dan bernuansa rekreasi ceria\n", R.drawable.header_4); //

        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Default);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(5000);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.addOnPageChangeListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void updateView() {


    }


    private void readDataFromDb() {
        if (locationModels.size() > 0)
            locationModels.clear();
        RealmResults<LocationModel> result = realm
                .where(LocationModel.class)
                .findAll();
        // sortiny by id.
        // show greater first
        result.sort("id", RealmResults.SORT_ORDER_DESCENDING);
        for (LocationModel data : result) {
            Log.d("tag", "datanya " + data.getId());
            locationModels.add(data);
        }
        // notify adapter
        adapterRoute.notifyDataSetChanged();

        // update view
        updateView();
    }

    @OnClick(R.id.fab)
    public void onFabClick() {
        startActivity(new Intent(this, AddRouteActivity.class));
    }


    @Override
    public void onItemClick(View v, int position) {
        Toast.makeText(this, "" + locationModels.get(position).getTujuan(), Toast.LENGTH_SHORT).show();
        Bundle data = new Bundle();
        data.putInt("status", FROM_LOCAL);
        data.putString("id", locationModels.get(position).getId());
        startActivity(new Intent(this, ViewRouteActivity.class).putExtras(data));
    }
    @Override
    protected void onResume() {
        mDemoSlider.startAutoCycle();
        super.onResume();
        readDataFromDb();

    }

    @Override
    public void onClick(View view) {
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        FragmentManager fragmentManager = getFragmentManager();
        if (id == R.id.nav_beranda) {
            //Set the fragment initially
            MainFragment fragment = new MainFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
            // Handle the camera action
        } else if (id == R.id.nav_tentangkami) {
            Intent i = new Intent(MainActivity.this, tentangkami.class);
                    startActivity(i);
        } else if (id == R.id.nav_wisataindustri) {
            Intent i = new Intent(MainActivity.this, WisataIndustri.class);
            startActivity(i);
        } else if (id == R.id.nav_proses) {
            Intent i = new Intent(MainActivity.this, prosesproduksi.class);
            startActivity(i);
        } else if (id == R.id.nav_kios) {
            Intent i = new Intent(MainActivity.this, Kios.class);
            startActivity(i);
        } else if (id == R.id.nav_wp) {
            Intent i = new Intent(MainActivity.this, waroengpati.class);
            startActivity(i);
        } else if (id == R.id.nav_booking) {
            BookingFragment fragment = new BookingFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_syarat) {
            TermsFragment fragment = new TermsFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_hubungikami){
            ContactFragment fragment = new ContactFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return false;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    protected void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }
}