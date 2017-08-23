package app.mobile.examwarrior.api;

/**
 * Created by vinod on 10/1/17.
 */

import java.util.List;
import java.util.Map;

import app.mobile.examwarrior.database.Course;
import app.mobile.examwarrior.database.CourseDetail;
import app.mobile.examwarrior.database.Question;
import app.mobile.examwarrior.model.ChangePasswordRequestBody;
import app.mobile.examwarrior.model.CourseCategories;
import app.mobile.examwarrior.model.CourseDetailId;
import app.mobile.examwarrior.model.CourseMore;
import app.mobile.examwarrior.model.CourseMoreCategories;
import app.mobile.examwarrior.model.ForgetPasswordBody;
import app.mobile.examwarrior.model.ForgetPasswordResponse;
import app.mobile.examwarrior.model.LoginBody;
import app.mobile.examwarrior.model.QuestionRequestBody;
import app.mobile.examwarrior.model.RegistrationResponse;
import app.mobile.examwarrior.model.RelatedVideos;
import app.mobile.examwarrior.model.RelatedVideosBody;
import app.mobile.examwarrior.model.SignUpBody;
import app.mobile.examwarrior.model.Type;
import app.mobile.examwarrior.model.User;
import app.mobile.examwarrior.model.VideoEntity;
import app.mobile.examwarrior.model.VideoEntityBody;
import app.mobile.examwarrior.model.VoteRequestBody;
import app.mobile.examwarrior.model.VoteVideoResponse;
import okhttp3.ResponseBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;
import retrofit2.http.Url;


public interface ApiInterface {
    // Get All Courses
    @POST("/course/getAll")
    Call<List<Course>> getCourseList(@Body Type tag);

    //Get Course Details
    @POST("/moduleItems/getCourseItems")
    Call<List<CourseDetail>> getCourseDetail(@Body CourseDetailId tag);

    @POST("/auth/mobile/login")
    Call<User> userLogin(@Body LoginBody loginBody);

    @POST("/auth/mobile/register")
    Call<RegistrationResponse> userRegistration(@Body SignUpBody signUpBody);

    @POST("/auth/mobile/forgotPassword")
    Call<ForgetPasswordResponse> forgetPassword(@Body ForgetPasswordBody forgetPasswordBody);

    @POST("/auth/mobile/changePassword")
    Call<Response> changePassword(@HeaderMap Map<String, String> headers, @Body ChangePasswordRequestBody changePasswordRequestBody);

    @POST("/videoentity/mobile/getVideoEntity")
    Call<VideoEntity> getVideoEntity(@Header("Authorization") String auth_token, @Body VideoEntityBody videoEntityBody);

    @POST("/videoentity/getRelatedInfo")
    Call<RelatedVideos> getRelatedVideos(@Header("Authorization") String auth_token, @Body RelatedVideosBody relatedVideosBody);

    @POST("/videovotes/upvoteVideo")
    Call<VoteVideoResponse> upVoteVideo(@Header("Authorization") String auth_token, @Body VoteRequestBody voteRequestBody);

    @POST("/videovotes/downvoteVideo")
    Call<VoteVideoResponse> downVoteVideo(@Header("Authorization") String auth_token, @Body VoteRequestBody voteRequestBody);

    @GET("courseCategoryMaster/mobile/coursesByMCourses/{id}")
    Call<CourseMoreCategories> getExlporeSubCategoryData(@Path("id") String id);

    @GET("courseMasterDetails/mobile/courseCategory")
    Call<List<CourseCategories>> getExlporeData();

    @GET("courseMasterDetails/mobile/coursesByCat/{id}")
    Call<CourseMore> getExlporeMoreData(@Path("id") String id);

    @POST("practice/startUserExam")
    Call<Question> getQuestions(@Header("Authorization") String auth_token, @Body QuestionRequestBody questionRequestBody);

    @GET
    Call<ResponseBody> downloadFileLengthAsync(@Url String fileUrl);

}