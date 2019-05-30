package turingmediastudios.android.Models;

import java.io.Serializable;

public class Section implements Serializable {

    private int section_id;
    private String section_title;
    private String section_content;
    private String section_image_name;
    private String section_image_format;
    private String section_image_path;

    public int getSection_id() {
        return section_id;
    }

    public void setSection_id(int section_id) {
        this.section_id = section_id;
    }

    public String getSection_title() {
        return section_title;
    }

    public void setSection_title(String section_title) {
        this.section_title = section_title;
    }

    public String getSection_content() {
        return section_content;
    }

    public void setSection_content(String section_content) {
        this.section_content = section_content;
    }

    public String getSection_image_name() {
        return section_image_name;
    }

    public void setSection_image_name(String section_image_name) {
        this.section_image_name = section_image_name;
    }

    public String getSection_image_format() {
        return section_image_format;
    }

    public void setSection_image_format(String section_image_format) {
        this.section_image_format = section_image_format;
    }

    public String getSection_image_path() {
        return section_image_path;
    }

    public void setSection_image_path(String section_image_path) {
        this.section_image_path = section_image_path;
    }

    public int getStory_id() {
        return story_id;
    }

    public void setStory_id(int story_id) {
        this.story_id = story_id;
    }

    private int story_id

            ;

}
