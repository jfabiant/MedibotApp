package pe.edu.tecsup.jfabiant.medibotoriginalapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import pe.edu.tecsup.jfabiant.medibotoriginalapp.R;
import pe.edu.tecsup.jfabiant.medibotoriginalapp.models.H_Medico;

public class H_MedicoAdapter extends RecyclerView.Adapter<H_MedicoAdapter.ViewHolder>{

    private List<H_Medico> h_medicos;
    private Context mContext;

    public H_MedicoAdapter(){
        this.h_medicos = new ArrayList<>();
    }

    public void setH_medicos (Context mContext, List<H_Medico> h_medicos){
        this.mContext = mContext;
        this.h_medicos = h_medicos;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView fechaText;
        public TextView descripcionText;
        public TextView usuarioText;
        public TextView enfermedadText;
        public TextView hospitalText;

        public ViewHolder(View itemView) {
            super(itemView);
            fechaText = itemView.findViewById(R.id.fecha_text);
            descripcionText = itemView.findViewById(R.id.descripcion_text);
            usuarioText = itemView.findViewById(R.id.usuario_text);
            enfermedadText = itemView.findViewById(R.id.enfermedad_text);
            hospitalText = itemView.findViewById(R.id.hospital_text);
            

        }
    }

    @Override
    public H_MedicoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_h_medico, parent, false);
        return new H_MedicoAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final H_MedicoAdapter.ViewHolder viewHolder, int position) {

        H_Medico h_medico = this.h_medicos.get(position);

        viewHolder.fechaText.setText(h_medico.getFecha().toString());
        viewHolder.descripcionText.setText(h_medico.getDescripcion());

    }

    @Override
    public int getItemCount() {
        return this.h_medicos.size();
    }
}

