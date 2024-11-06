package edu.uga.cs.statecapitolsquiz.db.api;

import java.util.List;

import edu.uga.cs.statecapitolsquiz.db.models.Quiz;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface QuizService{
    // Get all quizzes
    @GET("quizzes")
    Call<List<Quiz>> getAllQuizzes();

    // Get a specific quiz by ID
    @GET("quiz/{id}")
    Call<Quiz> getQuizById(@Path("id") long id);

    // Create a new quiz
    @POST("quiz")
    Call<Quiz> createQuiz(@Body Quiz quiz);

    // Update an existing quiz
    @PUT("quiz/{id}")
    Call<Void> updateQuiz(@Path("id") long id, @Body Quiz quiz);

    // Delete a quiz by ID
    @DELETE("quiz/{id}")
    Call<Void> deleteQuiz(@Path("id") long id);
}
