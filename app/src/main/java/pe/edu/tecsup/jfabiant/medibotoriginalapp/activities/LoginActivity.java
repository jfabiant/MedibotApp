package pe.edu.tecsup.jfabiant.medibotoriginalapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.InjectView;
import pe.edu.tecsup.jfabiant.medibotoriginalapp.R;
import pe.edu.tecsup.jfabiant.medibotoriginalapp.fragments.DialogoError;
import pe.edu.tecsup.jfabiant.medibotoriginalapp.fragments.SignupPersonalizado;
import pe.edu.tecsup.jfabiant.medibotoriginalapp.models.Login;
import pe.edu.tecsup.jfabiant.medibotoriginalapp.models.User;
import pe.edu.tecsup.jfabiant.medibotoriginalapp.services.ApiService;
import pe.edu.tecsup.jfabiant.medibotoriginalapp.services.ApiServiceGenerator;
import pe.edu.tecsup.jfabiant.medibotoriginalapp.services.ApiServiceGeneratorUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private static final int REQUEST_SIGNUP = 0;

    @InjectView(R.id.username_input) EditText usernameInput;
    @InjectView(R.id.password_input) EditText passwordInput;
    @InjectView(R.id.btn_login) Button _loginButton;
    @InjectView(R.id.link_signup) TextView _signupLink;

    //Login Facebook
    private CallbackManager callbackManager;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);

        usernameInput = findViewById(R.id.username_input);
        passwordInput = findViewById(R.id.password_input);

        ButterKnife.inject(this);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                //Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                //startActivityForResult(intent, REQUEST_SIGNUP);
                FragmentManager fragmentManager = getSupportFragmentManager();
                SignupPersonalizado signup = new SignupPersonalizado();
                signup.show(fragmentManager, "tagError");

            }
        });

        //Login Facebook
        callbackManager = CallbackManager.Factory.create();

        LoginButton loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday"));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                        R.style.AppTheme_Dark_Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Autenticando ...");
                progressDialog.show();
                progressDialog.setCancelable(false);
                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {

                                String accessToken = loginResult.getAccessToken().getToken();

                                Log.d("KeyToken", accessToken);

                                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        Log.d("response", response.toString());
                                        getData(object);

                                    }
                                });

                                //Request Graph API
                                Bundle parameters = new Bundle();
                                parameters.putString("fields", "id, email, birthday, first_name, last_name, gender");
                                request.setParameters(parameters);
                                request.executeAsync();

                                Call<Login> call = null;
                                ApiService service = ApiServiceGenerator.createService(LoginActivity.this, ApiService.class);
                                call = service.loginFacebook(accessToken);
                                call.enqueue(new Callback<Login>() {
                                    @Override
                                    public void onResponse(Call<Login> call, Response<Login> response) {
                                        try{
                                            if(response.isSuccessful()){
                                                final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                                                preferences.edit().putString("token", response.body().getKey()).commit();

                                                final ApiService service = ApiServiceGeneratorUser.createService(LoginActivity.this, ApiService.class);
                                                Call<User> call2 = null;
                                                call2 = service.getUser();

                                                call2.enqueue(new Callback<User>() {
                                                    @Override
                                                    public void onResponse(Call<User> call, Response<User> response) {
                                                        try{
                                                            if(response.isSuccessful()){
                                                                preferences.edit().putString("pk", response.body().getPk().toString()).commit();
                                                                preferences.edit().putString("username", response.body().getUsername()).commit();
                                                                preferences.edit().putString("first_name", response.body().getFirst_name()).commit();
                                                                preferences.edit().putString("last_name", response.body().getLast_name()).commit();
                                                                preferences.edit().putString("email", response.body().getEmail()).commit();

                                                                //Toast.makeText(LoginActivity.this, "Username: "+response.body().getFirst_name(),
                                                                //      Toast.LENGTH_SHORT).show();

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

                                                startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                                                finish();
                                            }
                                        }catch (Throwable t){
                                            Log.e("errorFacebook", t.getMessage());
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Login> call, Throwable t) {

                                    }
                                });

                                //Fin de la barra de progreso:
                                progressDialog.dismiss();
                            }
                        }, 3000);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        //If already login
        if(AccessToken.getCurrentAccessToken()!=null){
            //txtEmail.setText(AccessToken.getCurrentAccessToken().getUserId());
        }

    }

    public void login() {
        _loginButton.setEnabled(false);

        Log.d(TAG, "Login");

        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();

        if (username.isEmpty()) {
            usernameInput.setError("Este campo es obligatorio");
            onLoginFailed();
            return;
        }
        if (password.isEmpty()) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            DialogoError dialogo = new DialogoError("Ups!","La clave es obligatoria");
            dialogo.show(fragmentManager, "tagError");

            onLoginFailed();
            return;
        }

        Call<Login> call = null;

        final ApiService service = ApiServiceGenerator.createService(LoginActivity.this, ApiService.class);

        call = service.login(username, password);
        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {

                try{
                    if (response.isSuccessful()){
                        //Toast.makeText(LoginActivity.this,"Usuario existe con token: "+response.body().getKey(), Toast.LENGTH_SHORT).show();

                        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                        preferences.edit().putString("token", response.body().getKey()).commit();

                        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                                R.style.AppTheme_Dark_Dialog);

                        progressDialog.setIndeterminate(true);
                        progressDialog.setMessage("Autenticando ...");
                        progressDialog.setCancelable(false);
                        progressDialog.show();

                        // TODO: Implement your own authentication logic here.

                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {

                                        //Username and fullname
                                        final ApiService service = ApiServiceGeneratorUser.createService(LoginActivity.this, ApiService.class);
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
                                                        //Toast.makeText(LoginActivity.this, "Username: "+response.body().getFirst_name(),
                                                        //      Toast.LENGTH_SHORT).show();

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

                    } else {
                        onLoginFailed();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        DialogoError dialogo = new DialogoError("Ups!","No se ha podido encontrar tu cuenta en medibot");
                        dialogo.show(fragmentManager, "tagAlerta");

                    }
                }catch (Throwable t){

                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                /*
                Toast.makeText
                        (LoginActivity.this,
                                "Error al conectarse a la red",
                                Toast.LENGTH_SHORT).show();
                */

                FragmentManager fragmentManager = getSupportFragmentManager();
                DialogoError dialogo = new DialogoError("Ups!", "Error al conectarse a la red");
                dialogo.show(fragmentManager, "tagError");

                _loginButton.setEnabled(true);
            }
        });

    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
        finish();
    }

    public void onLoginFailed() {
        _loginButton.setEnabled(true);
    }

    private void getData(JSONObject object) {
        try{
            URL profile_picture = new URL("https://graph.facebook.com/"+object.getString("id")+"/picture?width=400&height=400");

            final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
            preferences.edit().putString("profile_picture", profile_picture.toString()).commit();

            //Picasso.with(this).load(profile_picture.toString()).into(imgAvatar);

            //txtEmail.setText(object.getString("email"));
            //txtBirthday.setText(object.getString("birthday"));
            //txtFirstName.setText(object.getString("first_name"));
            //txtLastName.setText(object.getString("last_name"));

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
