package turingmediastudios.android.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import turingmediastudios.android.Adapters.SectionListAdapter;
import turingmediastudios.android.Models.Section;
import turingmediastudios.android.Models.Story;
import turingmediastudios.android.Network.ApiService;
import turingmediastudios.android.R;

public class EditStoryActivity extends AppCompatActivity {

    //update project

    private RecyclerView sectionsRecyclerview;
    private Toolbar mToolbar;
    private Story mStory;
    private Retrofit retrofit;
    private ApiService apiService;
    private Button addSectionButton;
    private LinearLayout bottomSheetView;
    private static BottomSheetBehavior bsb;
    private  TextView sectionTitle, sectionContent;
    private ImageView closeBs;
    private Button insertCompleted;
    private SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_story);

        //get views
        bottomSheetView = findViewById(R.id.bottom_sheet);
        addSectionButton = findViewById(R.id.addSectionButton);
        refreshLayout = findViewById(R.id.refreshSections);

        bottomSheetSetup();

        //fetch section data from user story
        mStory = (Story) getIntent().getSerializableExtra("section_data");

        //toolbar config
        toolbarSetup();

        //create retrofit
        retrofitSetup();

        //loadSectionlist
        loadSections();

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadSections();
                refreshLayout.setRefreshing(false);
            }
        });

        //INSERT
        insertSection();


    }

    private void loadSections() {
        sectionsRecyclerview = findViewById(R.id.sectionListRecyclerview);
        sectionsRecyclerview.setLayoutManager(new GridLayoutManager(this, 1));

        //retrofit call of sections
        Call<List<Section>> callSections = apiService.getSections(mStory.getStory_id());
        callSections.enqueue(new Callback<List<Section>>() {
            @Override
            public void onResponse(Call<List<Section>> call, Response<List<Section>> response) {
                sectionsRecyclerview.setAdapter(new SectionListAdapter(response.body(), EditStoryActivity.this));
            }

            @Override
            public void onFailure(Call<List<Section>> call, Throwable t) {
                Log.d("debug", t.getMessage());

            }
        });

    }

    private void toolbarSetup() {
        mToolbar = findViewById(R.id.editStoryToolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(mStory.getStory_title());
        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_primary_color_24px));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditStoryActivity.this.finish();
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

    private void bottomSheetSetup() {
        sectionTitle = findViewById(R.id.insertSectionTitle);
        sectionContent = findViewById(R.id.insertSectionContent);
        insertCompleted = findViewById(R.id.insertCompleted);
        closeBs = findViewById(R.id.closeBs);

        bsb = BottomSheetBehavior.from(bottomSheetView);

        //default state hidden
        bsb.setState(BottomSheetBehavior.STATE_HIDDEN);

        //when clicks new section button
        addSectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bsb.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        //when clicks X bottom sheet goona hide
        closeBs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bsb.setState(BottomSheetBehavior.STATE_HIDDEN);
                sectionTitle.setText("");
                sectionContent.setText("");

            }
        });

        //when action is completed and valid
        insertCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertSection();

            }
        });

    }

    private void insertSection() {
        //validate inputs
        String title = sectionTitle.getText().toString().trim();
        String content = sectionContent.getText().toString().trim();

        if (title.isEmpty() || title.equals("")) {
            sectionTitle.setError(getResources().getString(R.string.require_title));
            sectionTitle.requestFocus();
            return;
        }

        if (content.isEmpty() || content.equals("")) {
            sectionContent.setError(getResources().getString(R.string.require_title));
            sectionContent.requestFocus();
            return;
        }

        Call<turingmediastudios.android.Models.Responses.Response> call = apiService.insertSection(title, content, mStory.getStory_id());

        call.enqueue(new Callback<turingmediastudios.android.Models.Responses.Response>() {
            @Override
            public void onResponse(Call<turingmediastudios.android.Models.Responses.Response> call, Response<turingmediastudios.android.Models.Responses.Response> response) {
                turingmediastudios.android.Models.Responses.Response responseA = response.body();

                if (!responseA.isError()) {
                    bsb.setState(BottomSheetBehavior.STATE_HIDDEN);
                    sectionContent.setText("");
                    sectionTitle.setText("");
                    Toast.makeText(EditStoryActivity.this, responseA.getMessage(), Toast.LENGTH_SHORT).show();
                    loadSections();
                } else {
                    Toast.makeText(EditStoryActivity.this, responseA.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<turingmediastudios.android.Models.Responses.Response> call, Throwable t) {

            }
        });

    }

}
