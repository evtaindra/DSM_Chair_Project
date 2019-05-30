package turingmediastudios.android.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import turingmediastudios.android.Models.Story;
import turingmediastudios.android.Network.ApiService;
import turingmediastudios.android.R;
import turingmediastudios.android.Activities.StoryContentActivity;

public class StoriesAdapter extends RecyclerView.Adapter<StoriesAdapter.ViewHolder> {

    private List<Story> stories;
    private Context context;

    public StoriesAdapter(List<Story> stories, Context context) {
        this.stories = stories;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_story, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Story story = stories.get(position);

        holder.title.setText(story.getStory_title());
        holder.author.setText(story.getUser_name() + " " + story.getUser_lastname());

        //image url
        String image_url = ApiService.BASE_URL +
                story.getStory_image_path() +
                story.getStory_image_name() +
                story.getStory_image_format();

        if (story.getStory_image_name() != null) {
            Glide.with(context)
                    .load(image_url)
                    .apply(RequestOptions.circleCropTransform())
                    .into(holder.image);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, StoryContentActivity.class);
                intent.putExtra("story_data", story);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return stories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title, author;
        private ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.storyTitle);
            image = itemView.findViewById(R.id.storyImage);
            author = itemView.findViewById(R.id.storyAuthor);

        }
    }
}
