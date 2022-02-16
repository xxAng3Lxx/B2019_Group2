package com.b2019.studentspadmysql.API;

import  com.b2019.studentspadmysql.Model.ResponseModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIRequestData {

    @GET("https://b2019cc107group2.000webhostapp.com/tbl_Notes/retrieve.php")
    Call<ResponseModel> ardRetrieveData();

    @FormUrlEncoded
    @POST("https://b2019cc107group2.000webhostapp.com/tbl_Notes/create.php")
    Call<ResponseModel> ardCreateData(
            @Field("note_title") String note_title,
            @Field("note_sub") String note_sub,
            @Field("note_text") String note_text
    );

    @FormUrlEncoded
    @POST("https://b2019cc107group2.000webhostapp.com/tbl_Notes/delete.php")
    Call<ResponseModel> ardDeleteData(
            @Field("id") int id
    );

    @FormUrlEncoded
    @POST("https://b2019cc107group2.000webhostapp.com/tbl_Notes/get.php")
    Call<ResponseModel> ardGetData(
            @Field("id") int id
    );

    @FormUrlEncoded
    @POST("https://b2019cc107group2.000webhostapp.com/tbl_Notes/update.php")
    Call<ResponseModel> ardUpdateData(
            @Field("id") int id,
            @Field("note_title") String note_title,
            @Field("note_sub") String note_sub,
            @Field("note_text") String note_text
    );
}
