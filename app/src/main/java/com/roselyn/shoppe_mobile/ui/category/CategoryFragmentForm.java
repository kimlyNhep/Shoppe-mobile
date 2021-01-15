package com.roselyn.shoppe_mobile.ui.category;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.gson.Gson;
import com.roselyn.shoppe_mobile.APIClient;
import com.roselyn.shoppe_mobile.CategoryApi;
import com.roselyn.shoppe_mobile.R;
import com.roselyn.shoppe_mobile.model.CategoryRequest;
import com.roselyn.shoppe_mobile.model.ErrorResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryFragmentForm extends Fragment {

    CategoryViewModel categoryViewModel;
    private EditText categoryNameEditText;
    private CategoryApi categoryApi;

    public static CategoryFragmentForm newInstance() {
        return new CategoryFragmentForm();
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        categoryApi = APIClient.getClient().create(CategoryApi.class);

        View root = inflater.inflate(R.layout.fragment_category_form, container, false);
        categoryNameEditText = root.findViewById(R.id.category_name);

        if (getArguments() != null) {
            categoryViewModel.setText(getArguments().getString("categoryName"));
//            categoryViewModel.setCategoryId(getArguments().getInt("id"));
        }

        categoryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                categoryNameEditText.setText(s);
            }
        });

        Button createCategory = (Button) root.findViewById(R.id.createCategory);

        if (getArguments() != null) {
            createCategory.setText("Update");
        }

        createCategory.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (getArguments() != null) {
                    updateCategory(categoryNameEditText.getText().toString(), root);
                } else createCategory(categoryNameEditText.getText().toString(), root);
            }
        });
        return root;
    }

    public void createCategory(String categoryName, View root) {

        SharedPreferences preferences = requireActivity().getSharedPreferences("tokenPrefer", Context.MODE_PRIVATE);
        String token = "Bearer " + preferences.getString("token", "");

        Call<CategoryRequest> call = categoryApi.createCategory(new CategoryRequest(categoryName), token);
        call.enqueue(new Callback<CategoryRequest>() {
            @Override
            public void onResponse(Call<CategoryRequest> call, Response<CategoryRequest> response) {
                if (!response.isSuccessful()) {
                    Gson gson = new Gson();
                    ErrorResponse errorResponse;
                    assert response.errorBody() != null;
                    errorResponse = gson.fromJson(response.errorBody().charStream(), ErrorResponse.class);
                    hideKeyboard(getActivity());
                    Toast.makeText(getContext(), errorResponse.getMessage(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "Create Category Successfully", Toast.LENGTH_LONG).show();
                    hideKeyboard(getActivity());
                    Navigation.findNavController(root).navigate(R.id.to_nav_category_content);
                }
            }

            @Override
            public void onFailure(Call<CategoryRequest> call, Throwable t) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
                hideKeyboard(getActivity());
            }
        });
    }

    public void updateCategory(String categoryName, View root) {

        SharedPreferences preferences = requireActivity().getSharedPreferences("tokenPrefer", Context.MODE_PRIVATE);
        String token = "Bearer " + preferences.getString("token", "");

        assert getArguments() != null;
        Call<ResponseBody> call = categoryApi.updateCategory(getArguments().getInt("id"), new CategoryRequest(categoryName), token);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (!response.isSuccessful()) {
                    Gson gson = new Gson();
                    ErrorResponse errorResponse;
                    assert response.errorBody() != null;
                    errorResponse = gson.fromJson(response.errorBody().charStream(), ErrorResponse.class);
                    hideKeyboard(getActivity());
                    Toast.makeText(getContext(), errorResponse.getMessage(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "Create Category Successfully", Toast.LENGTH_LONG).show();
                    hideKeyboard(getActivity());
                    Navigation.findNavController(root).navigate(R.id.to_nav_category_content);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
                hideKeyboard(getActivity());
            }
        });
    }
}