package pe.edu.tecsup.jfabiant.medibotoriginalapp.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import pe.edu.tecsup.jfabiant.medibotoriginalapp.R;
import pe.edu.tecsup.jfabiant.medibotoriginalapp.models.Enfermedad;

public class EnfermedadAdapter extends RecyclerView.Adapter<EnfermedadAdapter.ViewHolder>{

    private List<Enfermedad> enfermedades;
    private Context mContext;

    public EnfermedadAdapter(){
        this.enfermedades = new ArrayList<>();
    }

    public void setEnfermedades (Context mContext, List<Enfermedad> enfermedades){
        this.mContext = mContext;
        this.enfermedades = enfermedades;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nombreText;
        public TextView descripcionText;
        public ImageView fotoImage;

        public ViewHolder(View itemView) {
            super(itemView);
            fotoImage = itemView.findViewById(R.id.foto_image);
            nombreText = itemView.findViewById(R.id.nombre_text);
            descripcionText = itemView.findViewById(R.id.descripcion_text);

        }
    }

    @Override
    public EnfermedadAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int i) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_enfermedad, parent, false);

        /*itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = ((FragmentActivity)mContext).getSupportFragmentManager();
                DialogoError dialogo = new DialogoError(enfermedad.getNombre(),"No se ha podido encontrar tu cuenta en medibot");
                dialogo.show(fragmentManager, "tagAlerta");

                //View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_desc, parent, false);
                Fragment fragment = new DescriptionFragment();
                ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();


            }
        });*/

        return new EnfermedadAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EnfermedadAdapter.ViewHolder viewHolder, int position) {

        Enfermedad enfermedad = this.enfermedades.get(position);
        viewHolder.nombreText.setText(enfermedad.getNombre());
        viewHolder.descripcionText.setText(enfermedad.getDescripcion());

        String url = enfermedad.getEnf_img();
        Picasso.with(viewHolder.itemView.getContext()).load(url).into(viewHolder.fotoImage);

    }

    @Override
    public int getItemCount() {
        return this.enfermedades.size();
    }
}
