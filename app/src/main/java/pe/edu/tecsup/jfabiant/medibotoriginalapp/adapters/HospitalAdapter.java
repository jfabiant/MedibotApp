package pe.edu.tecsup.jfabiant.medibotoriginalapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import pe.edu.tecsup.jfabiant.medibotoriginalapp.R;
import pe.edu.tecsup.jfabiant.medibotoriginalapp.activities.MapsActivity;
import pe.edu.tecsup.jfabiant.medibotoriginalapp.models.Hospital;

public class HospitalAdapter extends RecyclerView.Adapter<HospitalAdapter.ViewHolder>{

    private List<Hospital> hospitales;
    private Context mContext;

    public HospitalAdapter(){
        this.hospitales = new ArrayList<>();
    }

    public void setHospitales (Context mContext, List<Hospital> hospitales){
        this.mContext = mContext;
        this.hospitales = hospitales;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nombreText;
        public ImageView fotoImage;
        public TextView distritoText;
        public Button actionButton;
        public ViewHolder(View itemView) {
            super(itemView);
            fotoImage = itemView.findViewById(R.id.foto_image);
            nombreText = itemView.findViewById(R.id.nombre_text);
            distritoText = itemView.findViewById(R.id.distrito_text);
            actionButton = itemView.findViewById(R.id.action_button);
        }
    }

    @Override
    public HospitalAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hospital, parent, false);
        return new HospitalAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final HospitalAdapter.ViewHolder viewHolder, int position) {

        final Hospital hospital = this.hospitales.get(position);
        viewHolder.nombreText.setText(hospital.getNombre());
        viewHolder.distritoText.setText(hospital.getC_distrito());
        String url = hospital.getHosp_img();
        Picasso.with(viewHolder.itemView.getContext()).load(url).into(viewHolder.fotoImage);
        /*Log.d("Location", "Lat "+hospital.getLatitud().toString()+", Long "+hospital.getLongitud());*/

        viewHolder.actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, MapsActivity.class);
                i.putExtra("latitud", hospital.getLatitud().toString());
                i.putExtra("longitud", hospital.getLongitud().toString());
                i.putExtra("nombre", hospital.getNombre());
                mContext.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return this.hospitales.size();
    }
}
