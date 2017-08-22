package app.mobile.examwarrior.api;

/**
 * Created by vinod on 10/1/17.
 */

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import app.mobile.examwarrior.database.CheckTestStatus;
import app.mobile.examwarrior.database.Course;
import app.mobile.examwarrior.database.CourseDetail;
import app.mobile.examwarrior.database.StartUserExam;
import app.mobile.examwarrior.model.ChangePasswordRequestBody;
import app.mobile.examwarrior.model.CourseCategories;
import app.mobile.examwarrior.model.CourseDetailId;
import app.mobile.examwarrior.model.CourseMoreCategories;
import app.mobile.examwarrior.model.ForgetPasswordBody;
import app.mobile.examwarrior.model.ForgetPasswordResponse;
import app.mobile.examwarrior.model.LoginBody;
import app.mobile.examwarrior.model.RegistrationResponse;
import app.mobile.examwarrior.model.RelatedVideos;
import app.mobile.examwarrior.model.RelatedVideosBody;
import app.mobile.examwarrior.model.SignUpBody;
import app.mobile.examwarrior.model.StartTestBody;
import app.mobile.examwarrior.model.Type;
import app.mobile.examwarrior.model.User;
import app.mobile.examwarrior.model.VideoEntity;
import app.mobile.examwarrior.model.VideoEntityBody;
import app.mobile.examwarrior.model.VoteRequestBody;
import app.mobile.examwarrior.model.VoteVideoResponse;
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


public interface ApiInterface {

    // Get List of Questions
    @POST("/practice/mobile/startUserExam")
    Call<StartUserExam> getQuestionsList(@Header("Authorization") String auth_token, @Body StartTestBody StartTest);

    // Get Status of Test
    @POST("/practice/checkTestStatus")
    Call<CheckTestStatus> getTestStatus(@Header("Authorization") String auth_token,  @Body JSONObject Test);

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

    @GET("masterCourseCategoryDetails/details/{id}")
    Call<CourseMoreCategories> getExlporeSubCategoryData(@Path("id") String id);

    @GET("test/getAllCourseCategory")
    Call<List<CourseCategories>> getExlporeData();

    @GET("test/getCourseCategory/{id}")
    Call<List<CourseMoreCategories>> getExlporeMoreData(@Path("id") String id);

    @GET
    Call<ResponseBody> downloadFileLengthAsync(@Url String fileUrl);
}