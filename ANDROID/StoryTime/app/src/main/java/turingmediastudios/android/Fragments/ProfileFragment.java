package turingmediastudios.android.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import turingmediastudios.android.Adapters.StoriesAdapter;
import turingmediastudios.android.Activities.LogInActivity;
import turingmediastudios.android.Models.SharedPreferencesManager;
import turingmediastudios.android.Models.Story;
import turingmediastudios.android.Models.User;
import turingmediastudios.android.Network.ApiService;
import turingmediastudios.android.R;

public class ProfileFragment extends Fragment {

    private ImageView logoutButton;
    private TextView userName;
    private RecyclerView userStoriesRecyclerView;

    //current user
    private User currentUser = SharedPreferencesManager.getInstance(getActivity()).getLoggedUser();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userName = getActivity().findViewById(R.id.profileUserName);

        initUserData();

        //logout button listener
        logoutButton = getActivity().findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //user ends session
                userLogOut();
            }
        });
    }

    private void initUserData() {
        userName.setText(currentUser.getUser_name() + " " + currentUser.getUser_lastname());

        initRecyclerview();
    }

    private void initRecyclerview() {
        userStoriesRecyclerView = getActivity().findViewById(R.id.userStoriesRecyclerview);
        userStoriesRecyclerView.setVerticalScrollBarEnabled(true);
        userStoriesRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));

        //retorfit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<List<Story>> call = apiService.getUserStories(currentUser.getUser_id());

        call.enqueue(new Callback<List<Story>>() {
            @Override
            public void onResponse(Call<List<Story>> call, Response<List<Story>> response) {
                userStoriesRecyclerView.setAdapter(new StoriesAdapter(response.body(), getActivity()));
            }

            @Override
            public void onFailure(Call<List<Story>> call, Throwable t) {
                Toast.makeText(getActivity(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void userLogOut() {
        //clear shared preferences data
        SharedPreferencesManager.getInstance(getActivity()).clear();
        Toast.makeText(getActivity(), getResources().getString(R.string.user_logout), Toast.LENGTH_SHORT).show();
        //back to login activity
        Intent intent = new Intent(getActivity(), LogInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        //finish this (MainActivity)
        getActivity().finish();
    }

}
