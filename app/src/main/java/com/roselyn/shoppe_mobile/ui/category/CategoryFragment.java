package com.roselyn.shoppe_mobile.ui.category;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.roselyn.shoppe_mobile.APIClient;
import com.roselyn.shoppe_mobile.CategoryApi;
import com.roselyn.shoppe_mobile.R;
import com.roselyn.shoppe_mobile.databinding.FragmentCategoryBinding;
import com.roselyn.shoppe_mobile.model.CategoryResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    FragmentCategoryBinding binding;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private CategoryApi categoryApi;

    public CategoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoryFragment newInstance(String param1, String param2) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        categoryApi = APIClient.getClient().create(CategoryApi.class);


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
//        View root = inflater.inflate(R.layout.fragment_category, container, false);
        binding = FragmentCategoryBinding.inflate(getLayoutInflater());

//        ListView listCategory = (ListView) root.findViewById(R.id.list_category);
        ListView listCategory = binding.listCategory;
        ArrayList<String> categoryArray = new ArrayList<>();

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
                for (CategoryResponse categoryResponse : categoryResponseList) {
                    categoryArray.add(categoryResponse.getCategoryName());
                    System.out.println(categoryResponse.getCategoryName());
                }

                ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, categoryArray);
                listCategory.setAdapter(categoryAdapter);
            }

            @Override
            public void onFailure(Call<List<CategoryResponse>> call, Throwable t) {
                Toast.makeText(getContext(), "Something went wrong while load the categories", Toast.LENGTH_LONG).show();
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button newButton = binding.newCategory;
        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment categoryFragmentForm = new CategoryFragmentForm();
                fragmentTransaction.replace(R.id.nav_host_fragment, categoryFragmentForm);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }
}