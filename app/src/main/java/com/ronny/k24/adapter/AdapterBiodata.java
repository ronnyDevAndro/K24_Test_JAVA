package com.ronny.k24.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ronny.k24.R;
import com.ronny.k24.activity.DetailUser;
import com.ronny.k24.helper.Util;
import com.ronny.k24.model.ModelBiodata;

import java.util.ArrayList;

public class AdapterBiodata extends RecyclerView.Adapter<AdapterBiodata.Muncul> {

    Context context;
    private ArrayList<ModelBiodata> kegiatanview;

    public AdapterBiodata(Context context, ArrayList<ModelBiodata> MDView) {
        this.kegiatanview = MDView;
        this.context = context;
    }

    @Override
    public AdapterBiodata.Muncul onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, null);
        return new AdapterBiodata.Muncul(v);
    }

    @Override
    public void onBindViewHolder(final AdapterBiodata.Muncul holder, final int position) {
        holder.tvName.setText(kegiatanview.get(position).getNamaUser());
        holder.tvJnsKel.setText(kegiatanview.get(position).getJenKel());
        holder.tvTglLhr.setText(Util.viewFormatDate(kegiatanview.get(position).getTglUser()));
        holder.tvAlamt.setText(kegiatanview.get(position).getAlmtUser());
        holder.tvKodeMember.setText(kegiatanview.get(position).getKodeMember());

        final ModelBiodata kegiatan = new ModelBiodata();

        kegiatan.setIdUser(kegiatanview.get(position).getIdUser());
        kegiatan.setKodeMember(kegiatanview.get(position).getKodeMember());
        kegiatan.setNamaUser(kegiatanview.get(position).getNamaUser());
        kegiatan.setJenKel(kegiatanview.get(position).getJenKel());
        kegiatan.setAlmtUser(kegiatanview.get(position).getAlmtUser());
        kegiatan.setTglUser(kegiatanview.get(position).getTglUser());
        kegiatan.setUserName(kegiatanview.get(position).getUserName());
        kegiatan.setPassUser(kegiatanview.get(position).getPassUser());


        holder.lLBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(view.getContext(), DetailUser.class);
                it.putExtra("PARCELABEL", kegiatan);
                view.getContext().startActivity(it);

            }
        });


    }

    @Override
    public int getItemCount() {
        return kegiatanview.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public class Muncul extends RecyclerView.ViewHolder {
        TextView tvName, tvJnsKel, tvAlamt, tvTglLhr,tvKodeMember;
        LinearLayout lLBtn;

        public Muncul(View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_nama);
            tvJnsKel = itemView.findViewById(R.id.tv_jns_kel);
            tvAlamt = itemView.findViewById(R.id.tv_alamat);
            tvTglLhr = itemView.findViewById(R.id.tv_tgl_lhr);
            lLBtn = itemView.findViewById(R.id.ll_btn);
            tvKodeMember = itemView.findViewById(R.id.tv_kode_member);

        }
    }
}