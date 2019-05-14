package turingmediastudios.android.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

import turingmediastudios.android.Models.Section;
import turingmediastudios.android.R;

public class SectionsAdapter extends PagerAdapter {

    private Context context;
    private List<Section> sections;

    public SectionsAdapter(Context context, List<Section> sections) {
        this.context = context;
        this.sections = sections;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Section section = sections.get(position);
        //inflate layout
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.item_section, container, false);
        //layout views
        TextView title = layout.findViewById(R.id.sectionTitle);
        TextView content = layout.findViewById(R.id.sectionContent);
        //set data
        title.setText(section.getSection_title());
        content.setText(section.getSection_content());
        //add to container
        container.addView(layout);
        return layout;
    }

    @Override
    public int getCount() {
        return sections.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
