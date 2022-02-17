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

import com.ahmfarisi.laundrypalembang.R;
import com.b2019.studentspadmysql.API.APIRequestData;
import com.b2019.studentspadmysql.API.RetroServer;
import com.b2019.studentspadmysql.Model.ResponseModel;


import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InsertActivity extends AppCompatActivity {

    private static final int RECOGNIZER_RESULT = 1;

    private EditText etNote_title, etNote_sub, etNote_text;
    private ImageView btnSave;
    private String Note_Title, Note_Sub, Note_Text;
    ImageView ImagetoText1;

    TextToSpeech tts;

    ImageView imageSpeech;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        ImageView imageBack = findViewById(R.id.imageBack);
        ImageView ImagetoText1= findViewById(R.id.ImagetoText1);

        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }

        });

        ImagetoText1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InsertActivity.this,TextRecognitionActivity.class);
                startActivity(intent);
            }
        });

        etNote_title = findViewById(R.id.et_note_title);
        etNote_sub = findViewById(R.id.et_note_sub);
        etNote_text = findViewById(R.id.et_note_text);

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
                            tts.speak(etNote_title.getText().toString(), TextToSpeech.QUEUE_ADD, null);
                            tts.speak(etNote_sub.getText().toString(), TextToSpeech.QUEUE_ADD, null);
                            tts.speak(etNote_text.getText().toString(), TextToSpeech.QUEUE_ADD, null);
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

        btnSave = findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Note_Title = etNote_title.getText().toString();
                Note_Sub = etNote_sub.getText().toString();
                Note_Text = etNote_text.getText().toString();

                if(Note_Title.trim().equals("")){
                    etNote_title.setError("Note Title is required!");
                }
                else if(Note_Text.trim().equals("")){
                    etNote_text.setError("Note description is required!");
                }
                else{
                    createData();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        //Speech To Text
        if (requestCode == RECOGNIZER_RESULT && resultCode == RESULT_OK){
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            etNote_text.setText(matches.get(0).toString());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public static int getMaxSpeechInputLength() {
        return 4000;
    }

        private void createData(){
        APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ResponseModel> simpanData = ardData.ardCreateData(Note_Title, Note_Sub, Note_Text);

        simpanData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(InsertActivity.this, " "+kode+" "+pesan, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(InsertActivity.this, "Failed to Connect to Server. | "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });




    }
}
