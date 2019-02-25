package com.example.myapplication2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication2.models.CicloParqueaderosBogota;
import com.example.myapplication2.models.CP;
import com.example.myapplication2.api.Service;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {


    private static final String TAG="CICLODEX";
    private Retrofit retrofit;
    private TextView textfield;
    ArrayList<String> listDatos;                        // Para recycler-view
    RecyclerView recycler;                              // Para recycler-view
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recycler=findViewById(R.id.recycler);          // Para recycler-view
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));          // Para recycler-view
        listDatos=new ArrayList<String>();             // Para recycler-view

        textfield=findViewById(R.id.tvw);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://www.datos.gov.co/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        obtenerDatos();
    }


    private void obtenerDatos()
    {
        Service service = retrofit.create(Service.class);
        Call<ArrayList<CP>> cicloRespuestaCall = service.obtenerListaCP();


        cicloRespuestaCall.enqueue(new Callback<ArrayList<CP>>() {


            @Override
            public void onResponse(Call<ArrayList<CP>> call, Response<ArrayList<CP>> response)
            {
                if(response.isSuccessful()){

                    ArrayList lista = response.body();

                    for(int i=0;i<lista.size();i++)
                    {

                        CP c=(CP) lista.get(i);

                        listDatos.add("                        PARQUEADERO NUMERO "+i+"\n\nNOMBRE: "+c.getNombre()+"\nCUPOS: "+c.getCupos()+"\nDIRECCCION: "+c.getDireccion()+"\nHORARIO: "+c.getHorario()+
                                "\nLOCALIDAD: "+c.getLocalidad()+"\nCORDENADA EN x: "+c.getX()+
                                "\nCOORDENADA EN y: "+c.getY()+"\n\n");

                    }
                    AdapterDatos adapter= new AdapterDatos(listDatos);
                    recycler.setAdapter(adapter);

                }else
                {
                    Log.e(TAG, "Error en metodo onResponse, solucione lo siguiente: "+response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<CP>> call, Throwable t) {
                Log.e(TAG," Error en método onFailure: "+t.getMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id==R.id.descripcion)
        {
            Intent i = new Intent(this, Descripcion.class );
            startActivity(i);
        }
        else if (id==R.id.acercade)
        {
            Intent i = new Intent(this, AcercaDe.class );
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }



}
