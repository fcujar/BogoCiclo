package com.example.myapplication2.api;

import com.example.myapplication2.models.CP;
import com.example.myapplication2.models.CicloParqueaderosBogota;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Service
{
    @GET("resource/txu7-x8j2.json")
    Call<ArrayList<CP>> obtenerListaCP();
}

