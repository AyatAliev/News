package com.geektech.news;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.geektech.news.data.RetorfitBuilder;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    private  Object[] keys;
    private ArrayList<String> values;
    private Spinner spinnerOne;
    private Spinner spinnerTwo;
    private TextView tvResult;
    private EditText etCurrent;

    private double numberOne,nymberTwo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();
        fetchCurrencies();
        listener();
        setListener();
    }

    private void setupViews(){
        spinnerOne = findViewById(R.id.spinnerOne);
        spinnerTwo = findViewById(R.id.spinnerTwo);
        tvResult = findViewById(R.id.tvResult);
        etCurrent = findViewById(R.id.etCurrent);

    }

    private void fetchCurrencies() {
        RetorfitBuilder.getService().getCurrencies("1fc2a9bd0a85d3520261791025761f74")
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            parseJson(response.body());

                        } else {

                        }

                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void parseJson(JsonObject body){
        JsonObject rates = body.getAsJsonObject("rates");
        keys = rates.keySet().toArray();
        values = new ArrayList<>();
        for (Object item : keys ) {
            values.add(rates.getAsJsonPrimitive(item.toString()).toString());
        }
        setUpAdapter();
    }

    public void listener(){
        spinnerTwo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nymberTwo = Double.parseDouble(values.get(position));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });


        spinnerOne.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                numberOne = Double.parseDouble(values.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setUpAdapter(){
        ArrayAdapter arrayAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, keys);
        spinnerOne.setAdapter(arrayAdapter);
        spinnerTwo.setAdapter(arrayAdapter);


    }

    public String calck(Editable s, double numberOne, double nymberTwo) {
        double sum = (Double.parseDouble(String.valueOf(s))/numberOne)*nymberTwo;
        return String.valueOf(sum);
    }

    private void  setListener(){
        etCurrent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null) {
                    tvResult.setText(calck(s,numberOne,nymberTwo));
                }
                else
                {
                    tvResult.setText("");
                }
            }
        });
    }

}
