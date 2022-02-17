package com.b2019.studentspadmysql.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.b2019.studentspadmysql.API.APIRequestData;
import  com.b2019.studentspadmysql.API.RetroServer;
import  com.b2019.studentspadmysql.Activity.MainActivity;
import com.b2019.studentspadmysql.Activity.UpdateActivity;
import  com.b2019.studentspadmysql.Model.DataModel;
import  com.b2019.studentspadmysql.Model.ResponseModel;
import com.ahmfarisi.laundrypalembang.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterData extends RecyclerView.Adapter<AdapterData.HolderData>{
    private final Context ctx;
    private final List<DataModel> listData;
    private List<DataModel> listLaundry;
    private int idLaundry;


    public AdapterData(Context ctx, List<DataModel> listData) {
        this.ctx = ctx;
        this.listData = listData;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder,@SuppressLint("RecyclerView")final int position) {
        DataModel dm = listData.get(position);

        holder.tvId.setText(String.valueOf(dm.getId()));
        holder.tvNama.setText(dm.getNama());
        holder.tvAlamat.setText(dm.getAlamat());
        holder.tvTelepon.setText(dm.getTelepon());

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {
        TextView tvId, tvNama, tvAlamat, tvTelepon;
        LinearLayout layoutNote;
        RoundedImageView imageNote;

        public HolderData(@NonNull View itemView) {
            super(itemView);

            tvId = itemView.findViewById(R.id.tv_id);
            tvNama = itemView.findViewById(R.id.tv_note_title);
            tvAlamat = itemView.findViewById(R.id.tv_note_sub);
            tvTelepon = itemView.findViewById(R.id.tv_note_text);
            layoutNote = itemView.findViewById(R.id.layoutNote);
            imageNote = itemView.findViewById(R.id.imageNote);

            //Alert Dialog Delete
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    AlertDialog.Builder dialogPesan = new AlertDialog.Builder(ctx);
                    dialogPesan.setMessage("Choose Operation to Perform");
                    dialogPesan.setTitle("Attention");
                    dialogPesan.setIcon(R.mipmap.ic_launcher_round);
                    dialogPesan.setCancelable(true);

                    idLaundry = Integer.parseInt(tvId.getText().toString());

                    dialogPesan.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            deleteData();
                            dialogInterface.dismiss();
                            Handler hand = new Handler();
                            hand.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    ((MainActivity) ctx).retrieveData();
                                }
                            }, 1000);
                        }
                    });

                    dialogPesan.setNegativeButton("Update", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            getData();
                            dialogInterface.dismiss();
                        }
                    });

                    dialogPesan.show();

                    return false;
                }
            });
        }

        private void deleteData(){
            APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
            Call<ResponseModel> hapusData = ardData.ardDeleteData(idLaundry);

            hapusData.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    int kode = response.body().getKode();
                    String pesan = response.body().getPesan();

                    Toast.makeText(ctx, " "+kode+" | "+pesan, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    Toast.makeText(ctx, "Failed to connect to Server: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        private void getData(){
            APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
            Call<ResponseModel> ambilData = ardData.ardGetData(idLaundry);

            ambilData.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    int kode = response.body().getKode();
                    String pesan = response.body().getPesan();
                    listLaundry = response.body().getData();

                    int varIdLaundry = listLaundry.get(0).getId();
                    String varNamaLaundry = listLaundry.get(0).getNama();
                    String varAlamatLaundry = listLaundry.get(0).getAlamat();
                    String varTeleponLaundry = listLaundry.get(0).getTelepon();

                    //Toast.makeText(ctx, "Kode : "+kode+" | Pesan : "+pesan+ " | Data : "+varIdLaundry+" | "+varNamaLaundry + " | "+varAlamatLaundry+" | "+varTeleponLaundry, Toast.LENGTH_SHORT).show();

                    Intent kirim = new Intent(ctx, UpdateActivity.class);
                    kirim.putExtra("xId", varIdLaundry);
                    kirim.putExtra("xNama", varNamaLaundry);
                    kirim.putExtra("xAlamat", varAlamatLaundry);
                    kirim.putExtra("xTelepon", varTeleponLaundry);
                    ctx.startActivity(kirim);
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    Toast.makeText(ctx, "Failed to connect to Server. : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
