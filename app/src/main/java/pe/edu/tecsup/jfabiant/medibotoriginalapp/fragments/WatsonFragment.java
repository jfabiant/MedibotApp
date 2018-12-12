package pe.edu.tecsup.jfabiant.medibotoriginalapp.fragments;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import pe.edu.tecsup.jfabiant.medibotoriginalapp.R;
import pe.edu.tecsup.jfabiant.medibotoriginalapp.activities.WatsonActivity;

public class WatsonFragment extends Fragment {

    private final static String TAG = WatsonFragment.class.getSimpleName();
    private ImageButton callButton;

    public WatsonFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_watson, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        callButton = getView().findViewById(R.id.call_button);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WatsonActivity.class);
                startActivity(intent);
            }
        });


    }

}
