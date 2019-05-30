package turingmediastudios.android.Models;

import java.io.Serializable;

public class Story implements Serializable {

    private int story_id;
    private String story_title;
    private String story_image_name;
    private String story_image_format;
    private String story_image_path;
    private String story_description;
    private boolean story_approved;
    private int user_id;
    private int category_id;
    private String user_name;
    private String user_lastname;

    public int getStory_id() {
        return story_id;
    }

    public void setStory_id(int story_id) {
        this.story_id = story_id;
    }

    public String getStory_title() {
        return story_title;
    }

    public void setStory_title(String story_title) {
        this.story_title = story_title;
    }

    public String getStory_image_name() {
        return story_image_name;
    }

    public void setStory_image_name(String story_image_name) {
        this.story_image_name = story_image_name;
    }

    public String getStory_image_format() {
        return story_image_format;
    }

    public void setStory_image_format(String story_image_format) {
        this.story_image_format = story_image_format;
    }

    public String getStory_image_path() {
        return story_image_path;
    }

    public void setStory_image_path(String story_image_path) {
        this.story_image_path = story_image_path;
    }

    public String getStory_description() {
        return story_description;
    }

    public void setStory_description(String story_description) {
        this.story_description = story_description;
    }

    public boolean isStory_approved() {
        return story_approved;
    }

    public void setStory_approved(boolean story_approved) {
        this.story_approved = story_approved;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_lastname() {
        return user_lastname;
    }

    public void setUser_lastname(String user_lastname) {
        this.user_lastname = user_lastname;
    }
}
