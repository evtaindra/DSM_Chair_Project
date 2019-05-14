package turingmediastudios.android.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import turingmediastudios.android.Models.Responses.SignupResponse;
import turingmediastudios.android.Network.ApiService;
import turingmediastudios.android.R;

public class SignUpActivity extends AppCompatActivity {

    private EditText signupNameInput,
            signupLastnameInput,
            signupEmailInput,
            signupPhoneInput,
            signupPasswordInput,
            signupConfirmPasswordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signupNameInput = findViewById(R.id.signupNameInput);
        signupLastnameInput = findViewById(R.id.signupLastnameInput);
        signupEmailInput = findViewById(R.id.signupEmailInput);
        signupPhoneInput = findViewById(R.id.signupPhoneInput);
        signupPasswordInput = findViewById(R.id.signupPasswordInput);
        signupConfirmPasswordInput = findViewById(R.id.signupConfirmPasswordInput);

    }

    public void signUp(View view) {

        //fetching edittext data
        String name = signupNameInput.getText().toString().trim();
        String lastname = signupLastnameInput.getText().toString().trim();
        String email = signupEmailInput.getText().toString().trim();
        String phone = signupPhoneInput.getText().toString().trim();
        String password = signupPasswordInput.getText().toString().trim();
        String passwordC = signupConfirmPasswordInput.getText().toString().trim();

        //form validations
        if (name.isEmpty()) {
            signupNameInput.setError(getResources().getString(R.string.require_name));
            signupNameInput.requestFocus();
            return;
        }

        if (lastname.isEmpty()) {
            signupLastnameInput.setError(getResources().getString(R.string.require_lastname));
            signupLastnameInput.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            signupEmailInput.setError(getResources().getString(R.string.require_email));
            signupEmailInput.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            signupEmailInput.setError(getResources().getString(R.string.invalid_email));
            signupEmailInput.requestFocus();
            return;
        }

        if (phone.length() != 8 && !phone.isEmpty()) {
            signupPhoneInput.setError(getResources().getString(R.string.phone_length));
            signupPhoneInput.requestFocus();
            return;

        }

        if (password.isEmpty()) {
            signupPasswordInput.setError(getResources().getString(R.string.require_password));
            signupPasswordInput.requestFocus();
            return;
        }

        if (!password.isEmpty() && passwordC.isEmpty()) {
            signupConfirmPasswordInput.setError(getResources().getString(R.string.require_password_confirmation));
            signupConfirmPasswordInput.requestFocus();
            return;
        }

        //Password validation
        if (!password.equals(passwordC)) {
            signupConfirmPasswordInput.setError(getResources().getString(R.string.password_match));
            signupConfirmPasswordInput.requestFocus();
            return;

        }

        /**WHEN ALL FIELDS ARE VALIDATED, REQUEST WILL BE EXECUTED**/
        signUpRequest(name, lastname, email, phone, password);

    }

    private void signUpRequest(String name, String lastname, String email, String phone, String password) {
        //retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        //Callback to do the request
        Call<SignupResponse> call = apiService.signUp(name, lastname, email, phone, password);

        call.enqueue(new Callback<SignupResponse>() {
            @Override
            public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
                SignupResponse signupResponse = response.body();

                if (!signupResponse.isError()) {
                    Log.d("DEBUG", signupResponse.getMessage());
                    Toast.makeText(SignUpActivity.this,
                            getResources().getString(R.string.signup_success),
                            Toast.LENGTH_SHORT).show();

                    //Finish this activity and back to login
                    SignUpActivity.this.finish();

                } else {
                    Toast.makeText(SignUpActivity.this, signupResponse.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SignupResponse> call, Throwable t) {

            }
        });

    }

}
