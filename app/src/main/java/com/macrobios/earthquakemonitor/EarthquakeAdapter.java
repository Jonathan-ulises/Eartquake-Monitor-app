package com.macrobios.earthquakemonitor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.macrobios.earthquakemonitor.databinding.EarthListItemBinding;

//Clase publica que exteiende de ListAdapter, en este se especifica el tipo de lista con las que
//trabajaremos y el viewHolder del adapter
public class EarthquakeAdapter extends ListAdapter<Earthquake, EarthquakeAdapter.EarthquakeAdapterViewHolder> {

    //Para dar formato a los decimales
    private Context ctx;

    //Ayuda a comparar items y no tener items repetidos; ademas de que ayuda a reordenar la lista
    //de distintas maneras.
    public static final DiffUtil.ItemCallback<Earthquake> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Earthquake>() {
                //Compara si los items de la lista son iguales
                @Override
                public boolean areItemsTheSame(
                        @NonNull Earthquake oldEarthquake, @NonNull  Earthquake newEarthquake) {
                    return oldEarthquake.getId().equals(newEarthquake.getId());
                }

                //Compara si el contenido de un item es el mismo que otro
                @Override
                public boolean areContentsTheSame(
                        @NonNull Earthquake oldEarthquake, @NonNull Earthquake newEarthquake) {
                    return oldEarthquake.equals(newEarthquake);
                }
            };


    protected EarthquakeAdapter(Context ctx) {
        super(DIFF_CALLBACK);
        this.ctx = ctx;
    }

    //EVENTO CLICK EN UN ITEM
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onItemClick(Earthquake earthquake);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
    //---------------------------------------


    //Se utiliza asi cuando implementemos dataBinding
    @NonNull
    @Override
    public EarthquakeAdapter.EarthquakeAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //parent = El RecycleView al cual pertenece el adapter
        EarthListItemBinding binding = EarthListItemBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new EarthquakeAdapterViewHolder(binding);
    }

    //se utiliza cuando emplearemos findByID
    /*@NonNull
    @Override
    public EarthquakeAdapter.EarthquakeAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //parent = El RecycleView al cual pertenece el adapter
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.earth_list_item, parent, false);
        return new EarthquakeAdapterViewHolder(view);
    }*/


    @Override
    public void onBindViewHolder(@NonNull EarthquakeAdapter.EarthquakeAdapterViewHolder holder, int position) {
        //Cuando se mande la lista, se puede obtener un objeto de ella utilizando el valor position.
        Earthquake earthquake = getItem(position);

        holder.bind(earthquake);
    }


    class EarthquakeAdapterViewHolder extends RecyclerView.ViewHolder{

        private final EarthListItemBinding binding;

        //Se utiliza asi cuando implementemos dataBinding
        public EarthquakeAdapterViewHolder(@NonNull EarthListItemBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }

        //Se utiliza cuando implementemos findById
        /*public EarthquakeAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            txtMagnitude = itemView.findViewById(R.id.txtMagnitude);
            txtPlace = itemView.findViewById(R.id.txtPlace);
        }*/

        //Asigna los valores a la View del holder
        public void bind(Earthquake earthquake){
            binding.txtMagnitude.setText(String.valueOf(earthquake.getMagnitud()));
            binding.txtMagnitude.setText(ctx.getString(R.string.magnitude_format, earthquake.getMagnitud()));
            binding.txtPlace.setText(earthquake.getPlace());

            //EventoClick
            binding.getRoot().setOnClickListener(v -> {
                onItemClickListener.onItemClick(earthquake);
            });

            //En simple pálabras, no espera el proceso del binding(16ms para pintar), y pinta
            //los view inmediatamente en cada implementacion del binding.
            binding.executePendingBindings();
        }


    }
}
