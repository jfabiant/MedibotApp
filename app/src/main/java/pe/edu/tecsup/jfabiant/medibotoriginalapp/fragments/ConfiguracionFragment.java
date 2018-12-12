package pe.edu.tecsup.jfabiant.medibotoriginalapp.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import pe.edu.tecsup.jfabiant.medibotoriginalapp.R;
import pe.edu.tecsup.jfabiant.medibotoriginalapp.activities.DashboardActivity;
import pe.edu.tecsup.jfabiant.medibotoriginalapp.activities.LoginActivity;
import pe.edu.tecsup.jfabiant.medibotoriginalapp.activities.MainActivity;
import pe.edu.tecsup.jfabiant.medibotoriginalapp.models.Login;
import pe.edu.tecsup.jfabiant.medibotoriginalapp.services.ApiService;
import pe.edu.tecsup.jfabiant.medibotoriginalapp.services.ApiServiceGenerator;
import pe.edu.tecsup.jfabiant.medibotoriginalapp.services.ApiServiceGeneratorUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class ConfiguracionFragment extends Fragment {

    private ImageView profilePicture;
    private TextView usernameText;
    private TextView firstnameText;
    private TextView lastnameText;
    private TextView emailText;
    private Button logoutBtn;
    private Button configBtn;

    public ConfiguracionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_configuracion, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        //Find TextView
        usernameText = getView().findViewById(R.id.username_text);
        firstnameText = getView().findViewById(R.id.firstname_text);
        lastnameText = getView().findViewById(R.id.lastname_text);
        emailText = getView().findViewById(R.id.email_text);
        profilePicture = getView().findViewById(R.id.profile_picture);
        logoutBtn = getView().findViewById(R.id.logout_btn);
        //configBtn = getView().findViewById(R.id.config_btn);

        //Get preferences:
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        String username = preferences.getString("username", "");
        String first_name = preferences.getString("first_name", "");
        String last_name = preferences.getString("last_name", "");
        String email = preferences.getString("email", "");
        String profile_picture = preferences.getString("profile_picture", "");

        usernameText.setText(username);
        firstnameText.setText(first_name);
        lastnameText.setText(last_name);
        emailText.setText(email);

        //Toast.makeText(getContext(), profile_picture, Toast.LENGTH_SHORT).show();
        if(profile_picture.isEmpty()){
            Picasso.with(getContext()).load(R.drawable.ic_user).into(profilePicture);
        } else {
            Picasso.with(getContext()).load(profile_picture.toString()).into(profilePicture);
        }
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });


    }

    public void logout(){
        //SharedPreferences preferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
        //preferences.edit().remove("token").commit();

                LoginManager.getInstance().logOut();

                ApiService service = ApiServiceGeneratorUser.createService(getContext(), ApiService.class);
                Call<Login>call = null;
                call = service.logout("", "");

                call.enqueue(new Callback<Login>() {
                    @Override
                    public void onResponse(Call<Login> call, Response<Login> response) {
                        try{
                            if(response.isSuccessful()){
                                /*Toast.makeText
                                        (getContext(),
                                                "Sesion cerrada y con Token eliminado",
                                                Toast.LENGTH_SHORT).show();*/
                                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

                                preferences.edit().remove("token").commit();
                                preferences.edit().remove("username").commit();
                                preferences.edit().remove("firstname").commit();
                                preferences.edit().remove("lastname").commit();
                                preferences.edit().remove("email").commit();

                                Log.e("Token actuality: ", preferences.getString("token", ""));

                                startActivity(new Intent(getActivity(), MainActivity.class));

                            } else {
                                Toast.makeText
                                        (getContext(),
                                                "No se pudo cerrar la sesion",
                                                Toast.LENGTH_SHORT).show();
                            }
                        }catch(Throwable t){
                        }
                    }

                    @Override
                    public void onFailure(Call<Login> call, Throwable t) {
                        Toast.makeText
                                (getContext(),
                                        "Error al conectarse",
                                        Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
