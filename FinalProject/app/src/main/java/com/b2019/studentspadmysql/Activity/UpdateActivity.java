package com.b2019.studentspadmysql.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.b2019.studentspadmysql.API.APIRequestData;
import com.b2019.studentspadmysql.API.RetroServer;
import com.b2019.studentspadmysql.Model.ResponseModel;
import com.ahmfarisi.laundrypalembang.R;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateActivity extends AppCompatActivity {

    private static final int RECOGNIZER_RESULT = 1;

    private int xId;
    private String xNote_Title, xNote_Sub, xNote_Text;
    private EditText etNote_Title, etNote_Sub, etNote_Text;
    private ImageView btnUpdate;
    private String yNote_Title, yNote_Sub, yNote_Text;

    TextToSpeech tts;

    ImageView imageSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        ImageView imageBack = findViewById(R.id.imageBack);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Intent terima = getIntent();
        xId = terima.getIntExtra("xId", -1);
        xNote_Title = terima.getStringExtra("xNama");
        xNote_Sub = terima.getStringExtra("xAlamat");
        xNote_Text = terima.getStringExtra("xTelepon");

        etNote_Title = findViewById(R.id.et_note_title);
        etNote_Sub = findViewById(R.id.et_note_sub);
        etNote_Text = findViewById(R.id.et_note_text);
        btnUpdate = findViewById(R.id.btn_update);

        etNote_Title.setText(xNote_Title);
        etNote_Sub.setText(xNote_Sub);
        etNote_Text.setText(xNote_Text);

        // Text To Speech Code
        ImageView imageSpeak = findViewById(R.id.imageSpeak);
        imageSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int i) {
                        if (i == TextToSpeech.SUCCESS) {
                            tts.setLanguage(Locale.getDefault());
                            tts.setSpeechRate(1.0f);
                            tts.speak(etNote_Title.getText().toString(), TextToSpeech.QUEUE_ADD, null);
                            tts.speak(etNote_Sub.getText().toString(), TextToSpeech.QUEUE_ADD, null);
                            tts.speak(etNote_Text.getText().toString(), TextToSpeech.QUEUE_ADD, null);
                        }

                    }
                });

            }
        });

        // Speech To Text Code
        imageSpeech = findViewById(R.id.imageSpeech);
        imageSpeech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                speechIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak here!");
                startActivityForResult(speechIntent, RECOGNIZER_RESULT);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yNote_Title = etNote_Title.getText().toString();
                yNote_Sub = etNote_Sub.getText().toString();
                yNote_Text = etNote_Text.getText().toString();


                updateData();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        //Speech To Text
        if (requestCode == RECOGNIZER_RESULT && resultCode == RESULT_OK){
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            etNote_Text.setText(matches.get(0).toString());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void updateData(){
        APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ResponseModel> ubahData = ardData.ardUpdateData(xId, yNote_Title, yNote_Sub, yNote_Text);

        ubahData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(UpdateActivity.this, " "+kode+" "+pesan, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(UpdateActivity.this, "Failed to Connect to Server"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}