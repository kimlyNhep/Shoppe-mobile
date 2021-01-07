package com.roselyn.shoppe_mobile.ui.category;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.roselyn.shoppe_mobile.APIClient;
import com.roselyn.shoppe_mobile.CategoryApi;
import com.roselyn.shoppe_mobile.R;
import com.roselyn.shoppe_mobile.model.CategoryRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryFragmentForm extends Fragment {

    public final static String TAG = "category_fragement_form";
    private CategoryViewModel categoryViewModel;
    private EditText categoryNameEditText;
    private CategoryApi categoryApi;

    public static CategoryFragmentForm newInstance() {
        return new CategoryFragmentForm();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        categoryApi = APIClient.getClient().create(CategoryApi.class);

        View root = inflater.inflate(R.layout.fragment_category_form, container, false);
        categoryNameEditText = root.findViewById(R.id.category_name);
        categoryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                categoryNameEditText.setText(s);
            }
        });

        Button createCategory = (Button) root.findViewById(R.id.createCategory);
        createCategory.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                createCategory(categoryNameEditText.getText().toString());
            }
        });
        return root;
    }

    public void createCategory(String categoryName) {

        SharedPreferences preferences = requireActivity().getSharedPreferences("tokenPrefer", Context.MODE_PRIVATE);
        String token = "Bearer " + preferences.getString("token", "");

        Call<CategoryRequest> call = categoryApi.createCategory(new CategoryRequest(categoryName), token);
        call.enqueue(new Callback<CategoryRequest>() {
            @Override
            public void onResponse(Call<CategoryRequest> call, Response<CategoryRequest> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), "Error while creating Category", Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(getContext(), "Create Category Successfully", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<CategoryRequest> call, Throwable t) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
            }
        });
    }
}