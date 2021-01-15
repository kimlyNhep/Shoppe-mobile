package com.roselyn.shoppe_mobile;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.Navigation;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.roselyn.shoppe_mobile.model.CategoryResponse;
import com.roselyn.shoppe_mobile.model.ErrorResponse;
import com.roselyn.shoppe_mobile.model.ResolveCategory;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryAdapter extends ArrayAdapter<CategoryResponse> {
    private final Activity context;
    private final ArrayList<CategoryResponse> items;
    private CategoryApi categoryApi;

    public CategoryAdapter(Activity context, ArrayList<CategoryResponse> items) {
        super(context, R.layout.category_list, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        categoryApi = APIClient.getClient().create(CategoryApi.class);
        LayoutInflater inflater = context.getLayoutInflater();
        View root = inflater.inflate(R.layout.category_list, null, true);
        TextView name = root.findViewById(R.id.category_name);
        name.setText(items.get(position).getCategoryName());

        FloatingActionButton editCategory = root.findViewById(R.id.btnEditCategory);
        editCategory.setOnClickListener(v -> {
            SharedPreferences preferences = context.getSharedPreferences("tokenPrefer", Context.MODE_PRIVATE);
            String token = "Bearer " + preferences.getString("token", "");

            Call<ResolveCategory> categoryRequest = categoryApi.fetchCategoryById(items.get(position).getId(), token);
            categoryRequest.enqueue(new Callback<ResolveCategory>() {
                @Override
                public void onResponse(Call<ResolveCategory> call, Response<ResolveCategory> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(getContext(), "Error while get category", Toast.LENGTH_LONG).show();
                        return;
                    }

                    ResolveCategory resolveCategory = response.body();
                    assert resolveCategory != null;
                    System.out.println(resolveCategory.getCategoryResponse().getCategoryName());
                    Bundle bundle = new Bundle();
                    bundle.putString("categoryName", resolveCategory.getCategoryResponse().getCategoryName());
                    bundle.putInt("id", items.get(position).getId());
                    Navigation.findNavController(root).navigate(R.id.to_nav_category_form, bundle);
                }

                @Override
                public void onFailure(Call<ResolveCategory> call, Throwable t) {
                    Toast.makeText(getContext(), "Error while get category", Toast.LENGTH_LONG).show();
                }

            });
        });

        FloatingActionButton deleteCategory = root.findViewById(R.id.btnDeleteCategory);
        deleteCategory.setOnClickListener(v -> {
            SharedPreferences preferences = context.getSharedPreferences("tokenPrefer", Context.MODE_PRIVATE);
            String token = "Bearer " + preferences.getString("token", "");

            Call<ResponseBody> deleteCategoryRequest = categoryApi.deleteCategory(items.get(position).getId(), token);
            deleteCategoryRequest.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (!response.isSuccessful()) {
                        Gson gson = new Gson();
                        ErrorResponse errorResponse;
                        assert response.errorBody() != null;
                        errorResponse = gson.fromJson(response.errorBody().charStream(), ErrorResponse.class);
                        Toast.makeText(getContext(), errorResponse.getMessage(), Toast.LENGTH_LONG).show();
                        return;
                    }
                    Toast.makeText(getContext(), "You have delete category successfully", Toast.LENGTH_LONG).show();
//                    Navigation.findNavController(root).navigate(R.id.to_nav_category_content);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getContext(), "Error while delete category", Toast.LENGTH_LONG).show();
                }
            });
        });

        return root;
    }
}
