package com.macrobios.earthquakemonitor.api;


import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.GET;

public class EarthquakeClient {

    //Unica instancia de la clase
    private static final EarthquakeClient ourInstance = new EarthquakeClient();

    //Servicio
    private static EartquakeService service;

    //Singleton
    public static EarthquakeClient getInstance(){
        return ourInstance;
    }

    private EarthquakeClient(){ }

    //Metodos HTTPS para consumir servicios
    public interface EartquakeService {
        @GET("all_hour.geojson")
        Call<String> getEartquakes();
    }

    //Construccion de la instancia de retrofit, especificando la url base, el cenversor de los datos
    //optenidos y se manda a contruir con .build
    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build();

    //Crear y obtiene el servicio, si es nulo, lo crear y lo retorna; si no, simplemente lo retorna
    public EartquakeService getService(){
        //Si servicio es null, lo crea y lo retorna
        if(service == null){
            service = retrofit.create(EartquakeService.class);
        }

        //Si se ha creado anteriormente un servicio, simplemente se retorna el servicio
        return service;
    }


}
