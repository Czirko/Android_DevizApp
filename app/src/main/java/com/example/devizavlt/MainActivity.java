package com.example.devizavlt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.devizavlt.adapters.MyAdapter;
import com.example.devizavlt.api.ValutaApi;
import com.example.devizavlt.api.model.Arfolyamok;
import com.example.devizavlt.api.model.BankViewModel;
import com.example.devizavlt.api.model.Item;
import com.example.devizavlt.api.model.ValutaViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.devizavlt.R.layout.spinner_item;

public class MainActivity extends AppCompatActivity {
    private List<Item> items;
    private Map<String, String> banksMap;
    private Map<String, String> valutaMap;
    private Spinner spinnerChooser;
    private Switch bankOrValuta;
    private List<String> bankOrValutaList;
    private RecyclerView rvMain;
    private MyAdapter myListadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bankOrValutaList = new ArrayList<>();


        banksMap = initBanksMap();
        valutaMap = initValutaMap();
        bankOrValutaList.addAll(banksMap.values());
        bankOrValutaList.add(0, "Kérlek Válassz");
        bankOrValuta = findViewById(R.id.switchBankOrValuta);

        rvMain = findViewById(R.id.rvList);
        rvMain.setHasFixedSize(false);
        rvMain.setLayoutManager(new LinearLayoutManager(this));


        bankOrValuta.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                bankOrValutaList.clear();
                bankOrValutaList.add(0, "Kérlek Válassz");
                if (isChecked) {
                    bankOrValutaList.addAll(valutaMap.values());
                } else {
                    bankOrValutaList.addAll(banksMap.values());
                }
                initSpinnerAdapter();

            }
        });


        spinnerChooser = findViewById(R.id.spChooser);
        initSpinnerAdapter();


        spinnerChooser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Toast.makeText(getApplicationContext(), "position: " + pos + ", id: " + id, Toast.LENGTH_SHORT).show();
                if (pos != 0) {
                    if (items != null)
                        items.clear();
                    getItems(pos);
                    /*myListadapter = new MyAdapter(items,valutaMap,banksMap,bankOrValuta.isChecked());
                    rvMain.setAdapter(myListadapter);*/
                }


            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    public static <K, V> K getKey(Map<K, V> map, V value) {
        for (K key : map.keySet()) {
            if (value.equals(map.get(key))) {
                return key;
            }
        }
        return null;
    }

    private void getItems(int pos) {
        Log.d("ApiTest", "getItems");
        String apiCode;
        if (bankOrValuta.isChecked()) {
            apiCode = getKey(valutaMap, bankOrValutaList.get(pos));
            Log.d("ApiTest", "apicode: " + apiCode);
            valutaApiCall(apiCode);
        } else {
            apiCode = getKey(banksMap, bankOrValutaList.get(pos));
            Log.d("ApiTest", "apicode: " + apiCode);

            bankApiCall(apiCode);

        }


    }

    private void valutaApiCall(String apiCode) {

        ValutaViewModel vm = ViewModelProviders.of(this).get(ValutaViewModel.class);

        if (myListadapter == null) {
            vm.getArfolyamok(apiCode).observe(this, new Observer<Arfolyamok>() {
                @Override
                public void onChanged(Arfolyamok arfolyamok) {


                    items = arfolyamok.getValuta();

                    myListadapter = new MyAdapter(items, valutaMap, banksMap, bankOrValuta.isChecked());
                    rvMain.setAdapter(myListadapter);
                }


            });
        } else {
            vm.updateArfolyamok(apiCode).observe(this, new Observer<Arfolyamok>() {
                @Override
                public void onChanged(Arfolyamok arfolyamok) {
                    items.clear();
                    items=arfolyamok.getValuta();
                    myListadapter.setItems(items);
                    myListadapter.notifyDataSetChanged();
                }
            });
        }
    }

   /* private void resetDatas(List<Item> newItems) {
        Log.d("apiTest", "reset " + newItems.size() + newItems.get(0).getBank());

        for (Item i : newItems) {
            Log.d("apiTest", i.getBank());
        }
        myListadapter = new MyAdapter(newItems, valutaMap, banksMap, bankOrValuta.isChecked());
        rvMain.swapAdapter(myListadapter, true);
    }*/


    private void bankApiCall(String apiCode) {
        Log.d("apiTest", "bankapi");
        BankViewModel bvm = ViewModelProviders.of(this).get(BankViewModel.class);
        if (myListadapter == null) {
            bvm.getArfolyamok(apiCode).observe(this, new Observer<Arfolyamok>() {
                @Override
                public void onChanged(Arfolyamok arfolyamok) {
                    items = arfolyamok.getValuta();
                    myListadapter = new MyAdapter(items, valutaMap, banksMap, bankOrValuta.isChecked());
                    rvMain.setAdapter(myListadapter);

                }
            });
        } else {
            bvm.updateArfolyamok(apiCode).observe(this, new Observer<Arfolyamok>() {
                @Override
                public void onChanged(Arfolyamok arfolyamok) {
                    items.clear();
                    items = arfolyamok.getValuta();
                    myListadapter.setItems(items);
                    myListadapter.notifyDataSetChanged();
                }
            });
        }

    }

    private void initSpinnerAdapter() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, bankOrValutaList);
        spinnerChooser.setAdapter(adapter);
    }

    private Map<String, String> initValutaMap() {
        Map<String, String> vm = new HashMap<String, String>();
        vm.put("GBP", "Angol Font");
        vm.put("AUD", "Ausztrál Dollár");
        vm.put("DKK", " Dán korona");
        vm.put("JPY", "Japán yen");
        vm.put("CAD", "Kanadai Dollár");
        vm.put("NOK", "Norvég korona");
        vm.put("CHF", "Svájci Frank");
        vm.put("SEK", "Svéd Korona");
        vm.put("USD", "Amerika Dollár");
        vm.put("CZK", "Cseh Korona");
        vm.put("PLN", "Lengyel Zloty");
        vm.put("EUR", "Euro");
        vm.put("HRK", "Horvát Kuna");
        vm.put("RON", "Új Román Lej");
        vm.put("TRY", "Új Török Líra");

        return vm;


    }

    private Map<String, String> initBanksMap() {
        Map<String, String> bm = new HashMap<String, String>();
        bm.put("bb", "Budapest Bank");
        bm.put("allianz", "Allianz");
        bm.put("cib", "Cib Bank");
        bm.put("citibank", "CityBank ");
        bm.put("commerz", "Commerz Bank ");
        bm.put("erste", "Erste Bank ");
        bm.put("kdb", "KDB Bank ");
        bm.put("kh", "K&H Bank ");
        bm.put("mkb", "MKB Bank ");
        bm.put("oberbank", "OberBank ");
        bm.put("otp", "OTP Bank ");
        bm.put("raiffeisen", "Raiffeisen Bank ");
        bm.put("unicredit", "UniCredit Bank");
        bm.put("volksbank", "Sberbank");
        bm.put("mnb", "MNB");
        bm.put("sopron", "Sporon Bank");
        bm.put("mfb", "MFB");
        bm.put("fhb", "FHB");

        return bm;


    }
}
