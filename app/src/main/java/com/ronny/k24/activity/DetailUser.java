package com.ronny.k24.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ronny.k24.R;
import com.ronny.k24.helper.Util;
import com.ronny.k24.model.ModelBiodata;

public class DetailUser extends AppCompatActivity {
    EditText etNama, etTgl, etAlmt, etUserId, etPass,etKodeMember;
    AutoCompleteTextView actvJnsKel;
    TextView tvPass;
    LinearLayout lLPassBru;
    ModelBiodata modelBiodata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_user);
        Util.setToolbar(this, getString(R.string.detail_member));
        modelBiodata = getIntent().getParcelableExtra("PARCELABEL");
        etKodeMember = findViewById(R.id.et_kode);
        etNama = findViewById(R.id.et_nama);
        etTgl = findViewById(R.id.et_tgl);
        etAlmt = findViewById(R.id.et_alamat);
        etUserId = findViewById(R.id.et_userid);
        etPass = findViewById(R.id.et_password);
        actvJnsKel = findViewById(R.id.actv_jns_kel);
        tvPass = findViewById(R.id.tv_pass);
        lLPassBru = findViewById(R.id.ll_pass_bru);
        lLPassBru.setVisibility(View.GONE);
        tvPass.setText(getString(R.string.password));

        etNama.setText(modelBiodata.getNamaUser());
        etTgl.setText(modelBiodata.getTglUser());
        etAlmt.setText(modelBiodata.getAlmtUser());
        etUserId.setText(modelBiodata.getUserName());
        etPass.setText(modelBiodata.getPassUser());
        actvJnsKel.setText(modelBiodata.getJenKel());
        etKodeMember.setText(modelBiodata.getKodeMember());

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}