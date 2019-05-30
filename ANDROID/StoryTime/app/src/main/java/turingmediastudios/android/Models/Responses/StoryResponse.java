package turingmediastudios.android.Models.Responses;

import turingmediastudios.android.Models.Story;

public class StoryResponse {

    private boolean error;
    private String message;
    private Story story;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Story getStory() {
        return story;
    }

    public void setStory(Story story) {
        this.story = story;
    }
}
