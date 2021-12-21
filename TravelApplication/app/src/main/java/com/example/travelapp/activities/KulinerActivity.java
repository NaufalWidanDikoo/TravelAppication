package com.example.travelapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.travelapp.R;
import com.example.travelapp.adapter.KulinerAdapter;
import com.example.travelapp.api.Api;
import com.example.travelapp.model.ModelKuliner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class KulinerActivity extends AppCompatActivity implements KulinerAdapter.onSelectData{

    RecyclerView rvKuliner;
    KulinerAdapter KulinerAdapter;
    ProgressDialog progressDialog;
    List<ModelKuliner> modelKuliner = new ArrayList<>();
    Toolbar tbKuliner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kuliner);

        tbKuliner = findViewById(R.id.toolbar_kuliner);
        tbKuliner.setTitle("Daftar Kuliner Purwakarta");
        setSupportActionBar(tbKuliner);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Mohon Tunggu");
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Sedang menampilkan data...");

        rvKuliner = findViewById(R.id.rvKuliner);
        rvKuliner.setHasFixedSize(true);
        rvKuliner.setLayoutManager(new LinearLayoutManager(this));

        getKuliner();
    }

    private void getKuliner() {
        progressDialog.show();
        AndroidNetworking.get(Api.Kuliner)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            progressDialog.dismiss();
                            JSONArray playerArray = response.getJSONArray("kuliner");
                            for (int i = 0; i < playerArray.length(); i++) {
                                JSONObject temp = playerArray.getJSONObject(i);
                                ModelKuliner dataApi = new ModelKuliner();
                                dataApi.setIdKuliner(temp.getString("id"));
                                dataApi.setTxtNamaKuliner(temp.getString("nama"));
                                dataApi.setTxtAlamatKuliner(temp.getString("alamat"));
                                dataApi.setTxtOpenTime(temp.getString("jam_buka_tutup"));
                                dataApi.setKoordinat(temp.getString("kordinat"));
                                dataApi.setGambarKuliner(temp.getString("gambar_url"));
                                dataApi.setKategoriKuliner(temp.getString("kategori"));
                                modelKuliner.add(dataApi);
                                showKuliner();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(KulinerActivity.this,
                                    "Gagal menampilkan data!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        progressDialog.dismiss();
                        Toast.makeText(KulinerActivity.this,
                                "Tidak ada jaringan internet!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showKuliner() {
        KulinerAdapter = new KulinerAdapter(KulinerActivity.this, modelKuliner, this);
        rvKuliner.setAdapter(KulinerAdapter);
    }

    @Override
    public void onSelected(ModelKuliner modelKuliner) {
        Intent intent = new Intent(KulinerActivity.this, DetailKulinerActivity.class);
        intent.putExtra("detailKuliner", modelKuliner);
        startActivity(intent);
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
