package com.example.assignment_and103_ph39920;

import com.example.assignment_and103_ph39920.Model.MotorModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface APIService {
    String DOMAIN = "http://192.168.1.103:3000/";
//http://localhost:3000/

    APIService apiService = new Retrofit.Builder()
            .baseUrl(APIService.DOMAIN)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIService.class);

    @POST("/api/motor/add")
    Call<MotorModel> addMotor(@Body MotorModel motorModel);

    @GET("/api/list")
    Call<ArrayList<MotorModel>> getMotor();

    @DELETE("/api/motor/delete/{id}")
    Call<MotorModel> deleteMotor(@Path("id") String idMotor);

    @PUT("/api/motor/update/{id}")
    Call<MotorModel> updateMotor (@Path("id") String idMotor,@Body MotorModel motor);
}
