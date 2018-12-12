package pe.edu.tecsup.jfabiant.medibotoriginalapp.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import pe.edu.tecsup.jfabiant.medibotoriginalapp.R;
import pe.edu.tecsup.jfabiant.medibotoriginalapp.adapters.H_MedicoAdapter;
import pe.edu.tecsup.jfabiant.medibotoriginalapp.models.H_Medico;
import pe.edu.tecsup.jfabiant.medibotoriginalapp.services.ApiService;
import pe.edu.tecsup.jfabiant.medibotoriginalapp.services.ApiServiceGeneratorUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistorialFragment extends Fragment {

    private ProgressBar progressBar;
    private Handler handler;
    private Runnable runnable;
    private Timer timer;
    private final static String TAG = HistorialFragment.class.getSimpleName();
    public RecyclerView listHistoriales;

    public HistorialFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_historial, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).setTitle("Historiales medicos");

        listHistoriales = getView().findViewById(R.id.list_h_medicos);
        listHistoriales.setLayoutManager(new LinearLayoutManager(getContext()));
        listHistoriales.setAdapter(new H_MedicoAdapter());

        progressBar = getActivity().findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {

                //Mientras carga estara haciendo la peticion al servicio web.
                //Servira para evitar la espera del usuario mostrando la vista en blanco
                listHistoriales.setLayoutManager(new LinearLayoutManager(getContext()));

                listHistoriales.setAdapter(new H_MedicoAdapter());
                initialize();

            }
        };

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);

            }
        }, 1000, 1000);

    }

    private void initialize () {

        ApiService service = ApiServiceGeneratorUser.createService(getContext(), ApiService.class);

        Call<List<H_Medico>> call = service.getH_Medicos();

        call.enqueue(new Callback<List<H_Medico>>() {
            @Override
            public void onResponse(Call<List<H_Medico>> call, Response<List<H_Medico>> response) {
                try {

                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);

                    if (response.isSuccessful()) {

                        List<H_Medico> h_medicos = response.body();
                        Log.d(TAG, "Historiales : " + h_medicos);

                        H_MedicoAdapter adapter = (H_MedicoAdapter) listHistoriales.getAdapter();
                        adapter.setH_medicos(getContext(), h_medicos);
                        adapter.notifyDataSetChanged();

                        progressBar.setVisibility(View.GONE);
                        timer.cancel();
                    } else {
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Error en el servicio");
                    }

                } catch (Throwable t) {
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }catch (Throwable x){}
                }
            }

            @Override
            public void onFailure(Call<List<H_Medico>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
}

