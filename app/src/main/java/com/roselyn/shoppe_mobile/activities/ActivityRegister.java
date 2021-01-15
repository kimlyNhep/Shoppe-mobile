package com.roselyn.shoppe_mobile.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.roselyn.shoppe_mobile.APIClient;
import com.roselyn.shoppe_mobile.AuthApi;
import com.roselyn.shoppe_mobile.R;
import com.roselyn.shoppe_mobile.RegisterRequest;
import com.roselyn.shoppe_mobile.TokenResponse;
import com.roselyn.shoppe_mobile.model.ErrorResponse;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityRegister extends AppCompatActivity {

    private AuthApi authApi;
    private Context context;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText usernameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        authApi = APIClient.getClient().create(AuthApi.class);
        context = ActivityRegister.this;

        firstNameEditText = (EditText) findViewById(R.id.firstname);
        lastNameEditText = (EditText) findViewById(R.id.lastname);
        usernameEditText = (EditText) findViewById(R.id.username);
        emailEditText = (EditText) findViewById(R.id.email);
        passwordEditText = (EditText) findViewById(R.id.password);

        Button signUp = (Button) findViewById(R.id.register);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = firstNameEditText.getText().toString();
                String lastName = lastNameEditText.getText().toString();
                String userName = usernameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                register(firstName, lastName, userName, email, password);
            }
        });

    }

    private void register(String firstName, String lastName, String username, String email, String password) {
        Call<TokenResponse> call = authApi.registerUser(new RegisterRequest(firstName, lastName, username, email, password));
        call.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(@NotNull Call<TokenResponse> call, @NotNull Response<TokenResponse> response) {
                if (!response.isSuccessful()) {
                    Gson gson = new Gson();
                    ErrorResponse errorResponse;
                    assert response.errorBody() != null;
                    errorResponse = gson.fromJson(response.errorBody().charStream(), ErrorResponse.class);
                    Toast.makeText(context, errorResponse.getMessage(), Toast.LENGTH_LONG).show();
                    return;
                }
                assert response.body() != null;
                SharedPreferences preferences = context.getSharedPreferences("tokenPrefer", MODE_PRIVATE);
                SharedPreferences.Editor preferencesEdit = preferences.edit();
                preferencesEdit.putString("token", response.body().getToken());
                preferencesEdit.apply();

                Intent intent = new Intent(context, NavigationActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(@NotNull Call<TokenResponse> call, @NotNull Throwable t) {
                System.out.println("Error while login");
            }
        });
    }
}