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

import turingmediastudios.android.Activities.EditStoryActivity;
import turingmediastudios.android.Activities.StoryContentActivity;
import turingmediastudios.android.Models.Story;
import turingmediastudios.android.Network.ApiService;
import turingmediastudios.android.R;

public class UserStoriesAdapter extends RecyclerView.Adapter<UserStoriesAdapter.ViewHolder> {

    private List<Story> userStories;
    private Context context;

    public UserStoriesAdapter(List<Story> userStories, Context context) {
        this.userStories = userStories;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_story, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Story userStory = userStories.get(position);

        //image url
        String image_url = ApiService.BASE_URL +
                userStory.getStory_image_path() +
                userStory.getStory_image_name() +
                userStory.getStory_image_format();

        //load image
        if (userStory.getStory_image_name() != null) {
            Glide.with(context)
                    .load(image_url)
                    .apply(RequestOptions.circleCropTransform())
                    .into(holder.image);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, StoryContentActivity.class);
                intent.putExtra("story_data", userStory);
                context.startActivity(intent);
            }
        });

        holder.title.setText(userStory.getStory_title());
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditStoryActivity.class);
                intent.putExtra("section_data", userStory);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return userStories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView image, editButton;
        private TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.userStoryImage);
            title = itemView.findViewById(R.id.userStoryTitle);
            editButton = itemView.findViewById(R.id.editUserStory);
        }

    }

}
