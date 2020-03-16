package com.example.devizavlt.api.model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.devizavlt.api.ValutaApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BankViewModel extends ViewModel {
    private MutableLiveData<Arfolyamok> arfolyamok;
    public LiveData<Arfolyamok> getArfolyamok(String apiCode){
        if(arfolyamok==null){
            arfolyamok=new MutableLiveData<>();
            loadArfolyamok(apiCode);
        }
        return arfolyamok;
    }

    public MutableLiveData<Arfolyamok> updateArfolyamok(String apiCode) {
        arfolyamok=new MutableLiveData<>();
        loadArfolyamok(apiCode);
        return arfolyamok;
    }

    private void loadArfolyamok(String apiCode) {

        Call<Arfolyamok> bankApi = ValutaApi.getInstance().getApi().getByBank(apiCode);
        Log.d("apiTest", bankApi.request().toString());

        bankApi.enqueue(new Callback<Arfolyamok>() {

            @Override
            public void onResponse(Call<Arfolyamok> call, Response<Arfolyamok> response) {
                arfolyamok.setValue(response.body());
                Log.d("apiTest", response.toString());
                for (Item i : response.body().getValuta()) {
                    Log.d("apiTest", i.getPenznem());
                }

            }

            @Override
            public void onFailure(Call<Arfolyamok> call, Throwable t) {
                Log.d("apiTest", t.toString());


            }
        });


    }


}
