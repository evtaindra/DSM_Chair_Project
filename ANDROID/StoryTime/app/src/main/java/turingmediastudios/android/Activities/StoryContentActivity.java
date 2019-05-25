package turingmediastudios.android.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import turingmediastudios.android.Adapters.SectionsAdapter;
import turingmediastudios.android.Models.Section;
import turingmediastudios.android.Models.Story;
import turingmediastudios.android.Network.ApiService;
import turingmediastudios.android.R;

public class StoryContentActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private Toolbar mToolbar;
    private Story story = new Story();
    private int story_id;
    private String story_title, story_author;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_content);

        //fetch data from previous activity
        fetchStoryData();

        //toolbar init
        toolbarSetup();

        //load the content
        loadSections();

    }

    private void fetchStoryData() {
        story = (Story) getIntent().getSerializableExtra("story_data");
        story_id = story.getStory_id();
        story_title = story.getStory_title();
        story_author = getResources().getString(R.string.story_author_by) + " " +
                story.getUser_name() + " " + story.getUser_lastname();
    }

    private void loadSections() {
        mViewPager = findViewById(R.id.sectionsViewPager);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        //call sections
        Call<List<Section>> call = apiService.getSections(story_id);
        call.enqueue(new Callback<List<Section>>() {
            @Override
            public void onResponse(Call<List<Section>> call, Response<List<Section>> response) {
                mViewPager.setAdapter(new SectionsAdapter(StoryContentActivity.this, response.body()));

            }

            @Override
            public void onFailure(Call<List<Section>> call, Throwable t) {
                Log.d("debug", "Error: " + t.getMessage());

            }
        });

    }

    private void toolbarSetup() {
        mToolbar = findViewById(R.id.storyContentToolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(story_title);
        mToolbar.setSubtitle(story_author);
        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_primary_color_24px));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoryContentActivity.this.finish();
            }
        });
    }


}
