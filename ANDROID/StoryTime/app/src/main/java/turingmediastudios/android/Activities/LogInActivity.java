package turingmediastudios.android.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import turingmediastudios.android.Models.Responses.LoginResponse;
import turingmediastudios.android.Models.SharedPreferencesManager;
import turingmediastudios.android.Network.ApiService;
import turingmediastudios.android.R;

public class LogInActivity extends AppCompatActivity {

    public static LogInActivity logInActivity;
    private EditText loginEmailInput, loginPasswordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        loginEmailInput = findViewById(R.id.loginEmailInput);
        loginPasswordInput = findViewById(R.id.loginPasswordInput);

        //get context
        logInActivity = this;

        MobileAds.initialize(this, "ca-app-pub-4805588703731685~7046533858");

        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");

    }

    @Override
    protected void onStart() {
        super.onStart();

        //if the user is already logged in, main activity will start automatically
        if (SharedPreferencesManager.getInstance(this).isLoggedIn()) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            this.finish();
        }
    }

    public void logIn(View view) {

        //fetch edit text data
        final String password = loginPasswordInput.getText().toString().trim();
        final String email = loginEmailInput.getText().toString().trim();

        //validations
        if (email.isEmpty()) {
            loginEmailInput.setError(getResources().getString(R.string.require_email));
            loginEmailInput.requestFocus();
            return;

        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            loginEmailInput.setError(getResources().getString(R.string.invalid_email));
            loginEmailInput.requestFocus();
            return;

        }

        if (password.isEmpty()) {
            loginPasswordInput.setError(getResources().getString(R.string.require_password));
            loginPasswordInput.requestFocus();
            return;

        }

        /*if (password.length() < 6) {
            loginPasswordInput.setError(getResources().getString(R.string.password_length));
            loginPasswordInput.requestFocus();
            return;

        }*/

        //Retrofit setup
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService api = retrofit.create(ApiService.class);

        //call userlogin, set the user fetched data from edit text to put as call parameters
        Call<LoginResponse> call = api.userLogin(email, password);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();

                if (!loginResponse.isError()) {
                    //saving user data in shared preferences
                    Log.d("DEBUG", loginResponse.getMessage());
                    SharedPreferencesManager.getInstance(LogInActivity.this)
                            .saveUser(loginResponse.getUser());

                    //start next activity
                    startNextActivity();

                } else {
                    Log.d("DEBUG", loginResponse.getMessage());
                    Toast.makeText(LogInActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });
    }

    private void startNextActivity() {
        //stories activity will be killed
        //StoriesActivity.storiesActivity.finish();
        //starts main activity and finish this activity
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        this.finish();
    }

    public void signUp(View view) {
        //starts signup activiy
        startActivity(new Intent(this, SignUpActivity.class));
    }
}
