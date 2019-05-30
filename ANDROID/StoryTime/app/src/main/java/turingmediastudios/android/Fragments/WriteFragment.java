package turingmediastudios.android.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import turingmediastudios.android.Models.Category;
import turingmediastudios.android.Models.SharedPreferencesManager;
import turingmediastudios.android.Models.Responses.StoryResponse;
import turingmediastudios.android.Models.User;
import turingmediastudios.android.Network.ApiService;
import turingmediastudios.android.R;

public class WriteFragment extends Fragment {

    private EditText writeTitleInput, writeDescriptionInput;
    private static Spinner categoriesSpinner;
    private Retrofit retrofit;
    private ApiService apiService;
    private static int categoryId;
    private Button publish;
    private User currentUser = SharedPreferencesManager.getInstance(getActivity()).getLoggedUser();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_write, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        writeTitleInput= getActivity().findViewById(R.id.writeTitleInput);
        writeDescriptionInput = getActivity().findViewById(R.id.writeDescriptionInput);
        categoriesSpinner = getActivity().findViewById(R.id.categoriesSpinner);
        publish = getActivity().findViewById(R.id.publishStoryButton);

        //retrofit starts to work
        retrofitSetup();

        //initialize spinner
        spinnerSetup();

        //publish a story function
        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertStory();
            }
        });

    }

    private void insertStory() {
        //get edit text value
        String title = writeTitleInput.getText().toString().trim();
        String description = writeDescriptionInput.getText().toString().trim();


        //validate title
        if (title.isEmpty() || title.equals("")) {
            writeTitleInput.setError(getResources().getString(R.string.require_title));
            writeTitleInput.requestFocus();
            return;
        }

        if (description.isEmpty() || description.equals("")) {
            writeDescriptionInput.setError(getResources().getString(R.string.require_description));
            writeDescriptionInput.requestFocus();
            return;
        }

        //insert
        Call<StoryResponse> call = apiService.insertStory(title, null, null,
                null, description, false, currentUser.getUser_id(), categoryId);

        //make the call
        call.enqueue(new Callback<StoryResponse>() {
            @Override
            public void onResponse(Call<StoryResponse> call, Response<StoryResponse> response) {
                StoryResponse storyResponse = response.body();

                if (!storyResponse.isError()) {
                    //story was inserted successfully
                    writeTitleInput.setText("");
                    writeDescriptionInput.setText("");
                    Toast.makeText(getActivity(), storyResponse.getMessage() , Toast.LENGTH_LONG).show();

                } else {
                    //there was a error
                    Toast.makeText(getActivity(), storyResponse.getMessage() , Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<StoryResponse> call, Throwable t) {
                Log.d("DEBUG", t.getMessage());

            }
        });

    }

    private void spinnerSetup() {
        //get categories
        Call<List<Category>> call = apiService.getCategories();

        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                //fetch categories from api
                List<Category> categories = response.body();

                //spinner adapter
                ArrayAdapter<Category> userArrayAdapter = new ArrayAdapter<Category>(
                        getActivity(), android.R.layout.simple_spinner_dropdown_item, categories);
                userArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                categoriesSpinner.setAdapter(userArrayAdapter);

                //get category_id from spinner
                categoriesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        /**SPINNER ITEM POSITION MUST BE EQUALS CATEGORY ID**/
                        //Original spinner position is = 0, category_id starts at 1, so we sum position + 1 to get the real_id
                        categoryId = position + 1;
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.d("DEBUG", t.getMessage());

            }
        });

    }

    private void retrofitSetup() {
        /**RETROFIT GLOBAL CLASS CONFIG START**/
        retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
        /**RETROFIT GLOBAL CLASS CONFIG END**/
    }
}
