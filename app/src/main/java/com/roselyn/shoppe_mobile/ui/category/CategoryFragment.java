package com.roselyn.shoppe_mobile.ui.category;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.roselyn.shoppe_mobile.APIClient;
import com.roselyn.shoppe_mobile.CategoryAdapter;
import com.roselyn.shoppe_mobile.CategoryApi;
import com.roselyn.shoppe_mobile.R;
import com.roselyn.shoppe_mobile.model.CategoryResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryFragment extends Fragment {
    private CategoryApi categoryApi;

    public CategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryApi = APIClient.getClient().create(CategoryApi.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_category, container, false);
        loadCategories(root);
        Button newCategory = root.findViewById(R.id.new_category);
        newCategory.setOnClickListener(v -> Navigation.findNavController(root).navigate(R.id.to_nav_category_form));
        return root;
    }

    public void loadCategories(View root) {
        ListView listCategory = root.findViewById(R.id.list_category);
        ArrayList<CategoryResponse> categoryArray = new ArrayList<>();

        SharedPreferences preferences = requireActivity().getSharedPreferences("tokenPrefer", Context.MODE_PRIVATE);
        String token = "Bearer " + preferences.getString("token", "");

        Call<List<CategoryResponse>> categories = categoryApi.fetchCategories(token);

        categories.enqueue(new Callback<List<CategoryResponse>>() {
            @Override
            public void onResponse(Call<List<CategoryResponse>> call, Response<List<CategoryResponse>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), "Something went wrong while load the categories", Toast.LENGTH_LONG).show();
                    return;
                }
                List<CategoryResponse> categoryResponseList = response.body();
                assert categoryResponseList != null;
                categoryArray.addAll(categoryResponseList);

                CategoryAdapter categoryAdapter = new CategoryAdapter(getActivity(), categoryArray);
                listCategory.setAdapter(categoryAdapter);
            }

            @Override
            public void onFailure(Call<List<CategoryResponse>> call, Throwable t) {
                Toast.makeText(getContext(), "Something went wrong while loading the categories", Toast.LENGTH_LONG).show();
            }
        });
    }
}