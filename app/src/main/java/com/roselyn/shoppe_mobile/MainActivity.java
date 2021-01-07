package com.roselyn.shoppe_mobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.roselyn.shoppe_mobile.activities.ActivityRegister;
import com.roselyn.shoppe_mobile.activities.NavigationActivity;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private AuthApi authApi;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        authApi = APIClient.getClient().create(AuthApi.class);
        context = MainActivity.this;

        usernameEditText = (EditText) findViewById(R.id.username);
        passwordEditText = (EditText) findViewById(R.id.password);
        Button signIn = (Button) findViewById(R.id.login);
        TextView registerTextView = (TextView) findViewById(R.id.textRegister);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please fill the form", Toast.LENGTH_LONG).show();
                    return;
                }
                validate(username, password);
            }
        });

        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ActivityRegister.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void validate(String username, String password) {
        Call<TokenResponse> call = authApi.loginUser(new LoginRequest(username, password));
        call.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Sorry wrong credential",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                assert response.body() != null;
                System.out.println("Token : " + response.body().getToken());

                SharedPreferences preferences = context.getSharedPreferences("tokenPrefer", MODE_PRIVATE);
                SharedPreferences.Editor preferencesEdit = preferences.edit();
                preferencesEdit.putString("token", response.body().getToken());
                preferencesEdit.apply();

                Intent intent = new Intent(MainActivity.this, NavigationActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(@NotNull Call<TokenResponse> call, @NotNull Throwable t) {
                Toast.makeText(MainActivity.this, "Sorry wrong credential", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void register(String firstName, String lastName, String username, String email, String password) {
        Call<TokenResponse> call = authApi.registerUser(new RegisterRequest(firstName, lastName, username, email, password));
        call.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(@NotNull Call<TokenResponse> call, @NotNull Response<TokenResponse> response) {
                System.out.println("Token : " + response.body().getToken());
            }

            @Override
            public void onFailure(@NotNull Call<TokenResponse> call, @NotNull Throwable t) {
                System.out.println("Error while login");
            }
        });
    }
}