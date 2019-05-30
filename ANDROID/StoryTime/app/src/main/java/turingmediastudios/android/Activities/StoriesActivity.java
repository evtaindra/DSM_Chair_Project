package turingmediastudios.android.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import turingmediastudios.android.Adapters.StoriesAdapter;
import turingmediastudios.android.Models.Category;
import turingmediastudios.android.Models.Story;
import turingmediastudios.android.Network.ApiService;
import turingmediastudios.android.R;

public class StoriesActivity extends AppCompatActivity {

    public static StoriesActivity storiesActivity;
    private RecyclerView mRecyclerview;
    private int categoryId;
    private Category thisCategory;
    private ImageView categoryImage;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stories);

        categoryImage = findViewById(R.id.categoryStoriesImage);
        swipeRefreshLayout = findViewById(R.id.refreshStories);

        //fetch category
        thisCategory = (Category) getIntent().getSerializableExtra("category_data");
        categoryId = thisCategory.getCategory_id();

        //get category image
        Glide.with(this)
                .load(Uri.parse("file:///android_asset/category_image_" + thisCategory.getCategory_id() + ".jpg"))
                .into(categoryImage);

        //first set toolbar options
        toolbarSetup();

        //get context
        storiesActivity = this;

        initContent();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initContent();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        //if the user is already logged in, main activity will start automatically
        /*if (SharedPreferencesManager.getInstance(this).isLoggedIn()) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            this.finish();
        }*/
    }

    private void initContent() {

        //Recyclerview setup
        mRecyclerview = findViewById(R.id.storiesRecyclerview);
        mRecyclerview.setVerticalScrollBarEnabled(true);
        mRecyclerview.setLayoutManager(new GridLayoutManager(this, 1));


        //retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<List<Story>> listCall = apiService.getStories(categoryId);

        listCall.enqueue(new Callback<List<Story>>() {
            @Override
            public void onResponse(Call<List<Story>> call, Response<List<Story>> response) {
                mRecyclerview.setAdapter(new StoriesAdapter(response.body(), StoriesActivity.this));
            }

            @Override
            public void onFailure(Call<List<Story>> call, Throwable t) {
                Toast.makeText(StoriesActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void intentToLogin(View view) {
        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
    }

    private void toolbarSetup() {
        //get toolbar
        Toolbar toolbar = findViewById(R.id.storiesToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(thisCategory.getCategory_name());
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_24px));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoriesActivity.this.finish();
            }
        });

    }

}
