package turingmediastudios.android.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import turingmediastudios.android.Adapters.CategoriesAdapter;
import turingmediastudios.android.Models.Category;
import turingmediastudios.android.Network.ApiService;
import turingmediastudios.android.R;

public class HomeFragment extends Fragment {

    private RecyclerView categoriesRecyclerview;
    private SwipeRefreshLayout refreshCategories;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        refreshCategories = getActivity().findViewById(R.id.refreshCategories);

        initRecyclerview();

        refreshCategories.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initRecyclerview();
                refreshCategories.setRefreshing(false);
            }
        });

    }

    private void initRecyclerview() {
        categoriesRecyclerview = getActivity().findViewById(R.id.categoriesRecyclerView);
        categoriesRecyclerview.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        //retofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        //call
        Call<List<Category>> call = apiService.getCategories();

        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                categoriesRecyclerview.setAdapter(new CategoriesAdapter(response.body(), getActivity()));
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {

            }
        });

    }
}
