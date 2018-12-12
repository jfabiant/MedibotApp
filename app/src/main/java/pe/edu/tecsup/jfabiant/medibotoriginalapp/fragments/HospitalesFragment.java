package pe.edu.tecsup.jfabiant.medibotoriginalapp.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
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

import pe.edu.tecsup.jfabiant.medibotoriginalapp.adapters.HospitalAdapter;
import pe.edu.tecsup.jfabiant.medibotoriginalapp.models.Hospital;
import pe.edu.tecsup.jfabiant.medibotoriginalapp.services.ApiService;
import pe.edu.tecsup.jfabiant.medibotoriginalapp.services.ApiServiceGeneratorUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HospitalesFragment extends Fragment {

    private ProgressBar progressBar;
    private Handler handler;
    private Runnable runnable;
    private Timer timer;
    private final static String TAG = HospitalesFragment.class.getSimpleName();
    public RecyclerView listHospitales;

    public HospitalesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hospitales, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).setTitle("Hospitales");

        listHospitales = getView().findViewById(R.id.list_hospitales);
        listHospitales.setLayoutManager(new LinearLayoutManager(getContext()));
        listHospitales.setAdapter(new HospitalAdapter());

        progressBar = getActivity().findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {

                //Mientras carga estara haciendo la peticion al servicio web.
                //Servira para evitar la espera del usuario mostrando la vista en blanco
                listHospitales.setLayoutManager(new LinearLayoutManager(getContext()));
                listHospitales.setAdapter(new HospitalAdapter());
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

        Call<List<Hospital>> call = service.getHospitales();

        call.enqueue(new Callback<List<Hospital>>() {
            @Override
            public void onResponse(Call<List<Hospital>> call, Response<List<Hospital>> response) {
                try {

                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);

                    if (response.isSuccessful()) {

                        List<Hospital> hospitales = response.body();
                        Log.d(TAG, "Hospitales: " + hospitales);

                        HospitalAdapter adapter = (HospitalAdapter) listHospitales.getAdapter();
                        adapter.setHospitales(getContext(), hospitales);
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
            public void onFailure(Call<List<Hospital>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
}
