package pe.edu.tecsup.jfabiant.medibotoriginalapp.services;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.facebook.CallbackManager;

import java.util.List;

import pe.edu.tecsup.jfabiant.medibotoriginalapp.activities.MainActivity;
import pe.edu.tecsup.jfabiant.medibotoriginalapp.models.Enfermedad;
import pe.edu.tecsup.jfabiant.medibotoriginalapp.models.H_Medico;
import pe.edu.tecsup.jfabiant.medibotoriginalapp.models.Hospital;
import pe.edu.tecsup.jfabiant.medibotoriginalapp.models.Login;
import pe.edu.tecsup.jfabiant.medibotoriginalapp.models.Registration;
import pe.edu.tecsup.jfabiant.medibotoriginalapp.models.User;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ApiService {

    String API_BASE_URL = "https://medibot-api-jfabiantimoteotorres.c9users.io";

    @FormUrlEncoded
    @POST("/rest-auth/login/")
    Call<Login> login(@Field("username") String username,
                      @Field("password") String password);

    @GET("/rest-auth/user/")
    Call <User> getUser();

    @FormUrlEncoded
    @POST("/rest-auth/logout/")
    Call<Login> logout(@Field("username") String username,
                       @Field("password") String password);

    @GET("/api/enfermedades/")
    Call<List<Enfermedad>> getEnfermedades();

    @FormUrlEncoded
    @POST("/rest-auth/facebook/")
    Call<Login> loginFacebook (@Field("access_token") String access_token);

    @GET("/api/hospitales/")
    Call<List<Hospital>> getHospitales();

    @GET("/api/h_medicos/")
    Call<List<H_Medico>> getH_Medicos();

    @FormUrlEncoded
    @POST("/rest-auth/registration/")
    Call<Registration> registration (@Field("username") String username,
                                    @Field("email") String email,
                                    @Field("password1") String password1,
                                    @Field("password2") String password2);

    //@FormUrlEncoded
    //@PUT("/rest-auth/user/")
    //Call <User>

}