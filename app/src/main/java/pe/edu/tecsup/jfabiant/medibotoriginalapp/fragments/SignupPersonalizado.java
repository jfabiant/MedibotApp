package pe.edu.tecsup.jfabiant.medibotoriginalapp.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import pe.edu.tecsup.jfabiant.medibotoriginalapp.R;
import pe.edu.tecsup.jfabiant.medibotoriginalapp.activities.DashboardActivity;
import pe.edu.tecsup.jfabiant.medibotoriginalapp.activities.LoginActivity;
import pe.edu.tecsup.jfabiant.medibotoriginalapp.models.Login;
import pe.edu.tecsup.jfabiant.medibotoriginalapp.models.Registration;
import pe.edu.tecsup.jfabiant.medibotoriginalapp.models.User;
import pe.edu.tecsup.jfabiant.medibotoriginalapp.services.ApiService;
import pe.edu.tecsup.jfabiant.medibotoriginalapp.services.ApiServiceGenerator;
import pe.edu.tecsup.jfabiant.medibotoriginalapp.services.ApiServiceGeneratorUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupPersonalizado extends DialogFragment {

    private Button btnSignup;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = getActivity().getLayoutInflater().inflate(R.layout.signup, null);

        builder.setTitle("Registro de usuario");

        final EditText inputUsername = view.findViewById(R.id.input_username);
        final EditText inputEmail = view.findViewById(R.id.input_email);
        final EditText inputPassword = view.findViewById(R.id.input_password);
        final EditText inputRePassword = view.findViewById(R.id.input_rePassword);
        final EditText inputFirstname = view.findViewById(R.id.input_firstname);
        final EditText inputLastname = view.findViewById(R.id.input_lastname);

        btnSignup = view.findViewById(R.id.btn_signup);

        btnSignup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(inputUsername.getText().toString().isEmpty() || inputFirstname.getText().toString().isEmpty() ||
                        inputLastname.getText().toString().isEmpty() || inputEmail.getText().toString().isEmpty() ||
                        inputPassword.getText().toString().isEmpty() || inputRePassword.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), "Todos los campos son obligatorios",Toast.LENGTH_SHORT).show();
                    return;
                }

                Call<Registration> call = null;

                final ApiService service = ApiServiceGenerator.createService(getActivity(), ApiService.class);
                call = service.registration(
                        inputUsername.getText().toString(),
                        inputEmail.getText().toString(),
                        inputPassword.getText().toString(),
                        inputRePassword.getText().toString()
                );

                call.enqueue(new Callback<Registration>() {
                    @Override
                    public void onResponse(Call<Registration> call, Response<Registration> response) {
                        try{
                            if(response.isSuccessful()){
                                final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                                preferences.edit().putString("token", response.body().getKey()).commit();
                                final ProgressDialog progressDialog = new ProgressDialog(getActivity(),
                                        R.style.AppTheme_Dark_Dialog);

                                progressDialog.setIndeterminate(true);
                                progressDialog.setMessage("Autenticando ...");
                                progressDialog.setCancelable(false);
                                progressDialog.show();

                                new android.os.Handler().postDelayed(
                                        new Runnable() {
                                            public void run() {

                                                //Registration
                                                final ApiService service = ApiServiceGeneratorUser.createService(getActivity(), ApiService.class);
                                                Call<User> call = null;
                                                call = service.getUser();

                                                call.enqueue(new Callback<User>() {
                                                    @Override
                                                    public void onResponse(Call<User> call, Response<User> response) {
                                                        try{
                                                            if(response.isSuccessful()){

                                                                preferences.edit().putString("username", response.body().getUsername()).commit();
                                                                preferences.edit().putString("first_name", response.body().getFirst_name()).commit();
                                                                preferences.edit().putString("last_name", response.body().getLast_name()).commit();
                                                                preferences.edit().putString("email", response.body().getEmail()).commit();

                                                                Log.d("MyId", response.body().getPk().toString());

                                                            } else {
                                                                //Obvius
                                                            }
                                                        }catch(Throwable t){
                                                        }
                                                    }

                                                    @Override
                                                    public void onFailure(Call<User> call, Throwable t) {
                                                    }
                                                });

                                                // On complete call either onLoginSuccess or onLoginFailed
                                                onLoginSuccess();
                                                // onLoginFailed();
                                                progressDialog.dismiss();
                                            }
                                        }, 3000);

                            }else{

                            }
                        }catch (Throwable t){

                        }
                    }

                    @Override
                    public void onFailure(Call<Registration> call, Throwable t) {

                    }
                });

            }
        });

        builder.setView(view);
        return builder.create();
    }

    public void onLoginSuccess() {

        btnSignup.setEnabled(true);
        startActivity(new Intent(getActivity(), DashboardActivity.class));
        dismiss();
    }

}
