package com.example.travelapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.travelapp.R;
import com.example.travelapp.adapter.MasjidAdapter;
import com.example.travelapp.api.Api;
import com.example.travelapp.model.ModelMasjid;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MasjidActivity extends AppCompatActivity {

    RecyclerView rvMasjid;
    MasjidAdapter masjidAdapter;
    ProgressDialog progressDialog;
    List<ModelMasjid> modelMasjid = new ArrayList<>();
    Toolbar tbPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masjid);

        tbPlace = findViewById(R.id.toolbar_masjid);
        tbPlace.setTitle("Daftar Tempat Ibadah");
        setSupportActionBar(tbPlace);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Mohon Tunggu");
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Sedang menampilkan data");

        rvMasjid = findViewById(R.id.rvMasjid);
        rvMasjid.setHasFixedSize(true);
        rvMasjid.setLayoutManager(new LinearLayoutManager(this));

        getMasjid();
    }

    private void getMasjid() {
        progressDialog.show();
        AndroidNetworking.get(Api.TempatIbadah)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            progressDialog.dismiss();
                            JSONArray playerArray = response.getJSONArray("tempat_ibadah");
                            for (int i = 0; i < playerArray.length(); i++) {
                                JSONObject temp = playerArray.getJSONObject(i);
                                ModelMasjid dataApi = new ModelMasjid();
                                dataApi.setTxtTempatIbadah(temp.getString("nama"));
                                dataApi.setLatitude(temp.getDouble("latitude"));
                                dataApi.setLongitude(temp.getDouble("longitude"));
                                modelMasjid.add(dataApi);
                                showMasjid();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MasjidActivity.this,
                                    "Gagal menampilkan data!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        progressDialog.dismiss();
                        Toast.makeText(MasjidActivity.this,
                                "Tidak ada jaringan internet!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showMasjid() {
        masjidAdapter = new MasjidAdapter(modelMasjid);
        rvMasjid.setAdapter(masjidAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
