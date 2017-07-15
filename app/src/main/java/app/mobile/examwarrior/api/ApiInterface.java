package app.mobile.examwarrior.api;

/**
 * Created by vinod on 10/1/17.
 */

import java.util.List;
import java.util.Map;

import app.mobile.examwarrior.database.Course;
import app.mobile.examwarrior.database.CourseDetail;
import app.mobile.examwarrior.model.ChangePasswordRequestBody;
import app.mobile.examwarrior.model.CourseCategories;
import app.mobile.examwarrior.model.CourseDetailId;
import app.mobile.examwarrior.model.CourseMoreCategories;
import app.mobile.examwarrior.model.ForgetPasswordBody;
import app.mobile.examwarrior.model.ForgetPasswordResponse;
import app.mobile.examwarrior.model.LoginBody;
import app.mobile.examwarrior.model.RegistrationResponse;
import app.mobile.examwarrior.model.SignUpBody;
import app.mobile.examwarrior.model.Type;
import app.mobile.examwarrior.model.User;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface ApiInterface {
    // Get All Courses
    @POST("course/getAll")
    Call<List<Course>> getCourseList(@Body Type tag);

    //Get Course Details
    @POST("moduleItems/getCourseItems")
    Call<List<CourseDetail>> getCourseDetail(@Body CourseDetailId tag);

    @POST("auth/mobile/login")
    Call<User> userLogin(@Body LoginBody loginBody);

    @POST("auth/mobile/register")
    Call<RegistrationResponse> userRegistration(@Body SignUpBody signUpBody);

    @POST("auth/mobile/forgotPassword")
    Call<ForgetPasswordResponse> forgetPassword(@Body ForgetPasswordBody forgetPasswordBody);

    @POST("auth/mobile/changePassword")
    Call<Response> changePassword(@HeaderMap Map<String, String> headers, @Body ChangePasswordRequestBody changePasswordRequestBody);


    @GET("test/getAllCourseCategory")
    Call<List<CourseCategories>> getExlporeData();

    @GET("test/getCourseCategory/{id}")
    Call<List<CourseMoreCategories>> getExlporeMoreData(@Path("id") String id);


}