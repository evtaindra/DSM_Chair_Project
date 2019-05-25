package turingmediastudios.android.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import turingmediastudios.android.Models.Section;
import turingmediastudios.android.R;

public class SectionListAdapter extends RecyclerView.Adapter<SectionListAdapter.ViewHolder> {

    private List<Section> sections;
    private Context context;

    public SectionListAdapter(List<Section> sections, Context context) {
        this.sections = sections;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_section_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Section section = sections.get(position);

        holder.title.setText(section.getSection_title());

    }

    @Override
    public int getItemCount() {
        return sections.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title = itemView.findViewById(R.id.sectionListTitle);

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
