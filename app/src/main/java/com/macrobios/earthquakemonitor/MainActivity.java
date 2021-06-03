package com.macrobios.earthquakemonitor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.macrobios.earthquakemonitor.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //LayoutManayer = Como se mostraran los datos
        binding.rclEarth.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Earthquake> eqList = new ArrayList<>();
        eqList.add(new Earthquake("qwe", "Buenos Aires", 5.0, 564684221665L,  103.25, 98.8527));
        eqList.add(new Earthquake("asd", "Mexico", 9.0, 564684285236L,  103.25, 98.8527));
        eqList.add(new Earthquake("zxc", "Chiapas", 3.0, 963874221665L,  103.25, 98.8527));
        eqList.add(new Earthquake("rty", "Canada", 5.0, 564687496365L,  103.25, 98.8527));
        eqList.add(new Earthquake("fgh", "Chile", 1.6, 567785221665L,  103.25, 98.8527));

        //Se crea una instancia de del adapter para la RecycleView, ademas de asignarla a la misma
        EarthquakeAdapter adapter = new EarthquakeAdapter();

        //EventoClick
        /*adapter.setOnItemClickListener(new EarthquakeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Earthquake earthquake) {

            }
        });*/
        //Funcion lambda
        adapter.setOnItemClickListener(earthquake -> {
            Toast.makeText(this, earthquake.getPlace(), Toast.LENGTH_SHORT).show();
        });

        binding.rclEarth.setAdapter(adapter);

        //Para la lista con la cual el adapter trabajara, se utiliza el metodo .submitList, pasando
        //como parametro la lista de datos que queremos que el adapter utilice para poder mostrar
        //informacion en la lista.
        adapter.submitList(eqList);

        //Si la lista esta vacia, cambia la visibilidad del texto, esto para mostrar un mensaje
        //cuando la lista este vacia, si no lo esta, que esconda el mensaje y que muestre los
        //datos de la lista
        if(eqList.isEmpty()){
            binding.txtEmptyList.setVisibility(View.VISIBLE);
        } else {
            binding.txtEmptyList.setVisibility(View.GONE);
        }
    }
}