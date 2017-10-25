package com.example.causualninja08.cryptoprice;

import android.app.ProgressDialog;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wjK on 2017-07-17.
 */

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private static final String URL_DATA = "https://api.coinmarketcap.com/v1/ticker/";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private Display display;

    private List<ListItem> listItems;

    private boolean Change24 = true;

    AdView mAdview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        display = getWindowManager().getDefaultDisplay();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        mAdview = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        mAdview.loadAd(adRequest);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listItems = new ArrayList<>();

        loadRecyclerViewData();
    }

    private void loadRecyclerViewData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading data...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        progressDialog.dismiss();
                        try {
                            JSONArray array = new JSONArray(s);

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject o = array.getJSONObject(i);

                                ListItem item = new ListItem(
                                        o.getString("name"),
                                        "Price(USD) = $" + o.getString("price_usd"),
                                        "Price(BTC) = " + o.getString("price_btc") + "BTC",
                                        "Percentage change in 24h: " + o.getString("percent_change_24h") + "%",
                                        "https://files.coinmarketcap.com/static/img/coins/128x128/" + o.getString("id") + ".png",
                                        o.getString("percent_change_24h"),
                                        "#" + o.getString("rank") +" " + o.getString("name"),
                                        o.getString("24h_volume_usd"),
                                        o.getString("market_cap_usd"),
                                        o.getString("available_supply"),
                                        o.getString("total_supply"),
                                        "Percentage change in 1h: " + o.getString("percent_change_1h") + "%",
                                        "Percentage change in 7d: " + o.getString("percent_change_7d") + "%",
                                        o.getString("percent_change_1h"),
                                        o.getString("percent_change_7d")
                                );

                                listItems.add(item);
                            }

                            adapter = new ItemAdapter(listItems, getApplicationContext(), display);
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), /*volleyError.getMessage()*/ "No Internet", Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_items, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        newText = newText.toLowerCase();
        List<ListItem> newList = new ArrayList<ListItem>();
        for (ListItem Coins : listItems) {

            String name = Coins.getName().toLowerCase();
            if (name.contains(newText)) {
                newList.add(Coins);
            }
        }

        adapter = new ItemAdapter(newList, getApplicationContext(), display);
        recyclerView.setAdapter(adapter);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                refreshApp();
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    public void refreshApp() {
        recreate();
    }


}
























