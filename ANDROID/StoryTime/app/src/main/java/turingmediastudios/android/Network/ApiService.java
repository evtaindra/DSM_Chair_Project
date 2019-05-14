package turingmediastudios.android.Network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import turingmediastudios.android.Models.Category;
import turingmediastudios.android.Models.Responses.LoginResponse;
import turingmediastudios.android.Models.Section;
import turingmediastudios.android.Models.Responses.SignupResponse;
import turingmediastudios.android.Models.Story;
import turingmediastudios.android.Models.Responses.StoryResponse;

public interface ApiService {

    String BASE_URL = "http://192.168.1.24:80/BACKEND/";

    @GET("endpoints/read_stories.php?category_id=?")
    Call<List<Story>> getStories(@Query("category_id") int id);

    @GET("endpoints/read_user_stories.php?user_id=?")
    Call<List<Story>> getUserStories(@Query("user_id") int id);

    @FormUrlEncoded
    @POST("endpoints/user_login.php")
    Call<LoginResponse> userLogin(@Field("user_email") String email,
                                  @Field("user_password") String password);

    @FormUrlEncoded
    @POST("endpoints/user_signup.php")
    Call<SignupResponse> signUp(@Field("user_name") String name,
                                @Field("user_lastname") String lastname,
                                @Field("user_email") String email,
                                @Field("user_phone") String phone,
                                @Field("user_password") String password);

    @FormUrlEncoded
    @POST("endpoints/insert_story.php")
    Call<StoryResponse> insertStory(@Field("story_title") String title,
                                    @Field("story_image_name") String image_name,
                                    @Field("story_image_format") String image_format,
                                    @Field("story_image_path") String image_path,
                                    @Field("story_description") String description,
                                    @Field("story_approved") boolean approved,
                                    @Field("user_id") int user_id,
                                    @Field("category_id") int category_id);


    @GET("endpoints/read_categories.php")
    Call<List<Category>> getCategories();

    @GET("endpoints/read_sections.php?story_id=?")
    Call<List<Section>> getSections(@Query("story_id") int id);

}
