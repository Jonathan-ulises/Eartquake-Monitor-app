package com.macrobios.earthquakemonitor.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.macrobios.earthquakemonitor.api.RequestStatus;
import com.macrobios.earthquakemonitor.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        MainViewModel viewModel = new ViewModelProvider(this,
                new MainViewModelFactory(getApplication())).get(MainViewModel.class);


        //LayoutManayer = Como se mostraran los datos
        binding.rclEarth.setLayoutManager(new LinearLayoutManager(this));


        //Se crea una instancia de del adapter para la RecycleView, ademas de asignarla a la misma
        EarthquakeAdapter adapter = new EarthquakeAdapter(this);

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

        //Actualiza los datos si ocurre algun cambio en las variables LiveData
        viewModel.getEqList().observe(this, eqList -> {
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
        });

        viewModel.getStatusWithDescription().observe(this, statusWithDescription -> {
            if(statusWithDescription.getStatus() == RequestStatus.LOADING){
                //Muestra ruedita de cargando :)
                binding.prgbLoading.setVisibility(View.VISIBLE);
            } else {
                binding.prgbLoading.setVisibility(View.GONE);
                //Ocultar ruedita de cargando
            }

            if(statusWithDescription.getStatus() == RequestStatus.ERROR){
                //Muestra de toas con error.
                Toast.makeText(this, statusWithDescription.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //Consulta los datos en el viewModel para que puedan ser observados.
        viewModel.downloadEarthquakes();
    }
}