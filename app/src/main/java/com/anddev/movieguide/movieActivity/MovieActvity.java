package com.anddev.movieguide.movieActivity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anddev.movieguide.R;
import com.anddev.movieguide.model.Movie;
import com.anddev.movieguide.tools.ConnectionInterface;
import com.anddev.movieguide.tools.ImageTools;
import com.anddev.movieguide.tools.NavigationBarTools;
import com.anddev.movieguide.tools.RetrofitTools;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@EActivity(R.layout.activity_movie)
public class MovieActvity extends AppCompatActivity {

    Activity activity;
    Movie movie;
    NavigationBarTools navigationBarTools;

    @BindView(R.id.poster_movie_imageView)
    ImageView poster;

    @BindView(R.id.title_movie_textView)
    TextView title;

    @BindView(R.id.oryginal_title_movie_textView)
    TextView oryginalTitle;

    @BindView(R.id.overview_movie_TextView)
    TextView overview;

    @BindView(R.id.vote_average_movie_textView)
    TextView voteAverage;

    @BindView(R.id.release_data_movie_textView)
    TextView releaseData;

    @AfterViews
    public void onCreate() {
        activity = this;
        ButterKnife.bind(this);
        navigationBarTools = new NavigationBarTools(this);

        ConnectionInterface client = RetrofitTools.getConnectionInterface();
        downloadMovieInBackground(client, RetrofitTools.EXAMPLE_ID_MOVIE, RetrofitTools.API_KEY, RetrofitTools.LANGUAGE);
    }

    @Background
    void downloadMovieInBackground(ConnectionInterface client, Integer id, String apiKey, String language) {
        try {

            Call<Movie> call = client.movie(id, apiKey, language);

            call.enqueue(new Callback<Movie>() {

                @Override
                public void onResponse(Call<Movie> call, Response<Movie> response) {

                    if (response.code() == 200) {
                        movie = response.body();
                        ImageTools.getImageFromInternet(activity, ImageTools.IMAGE_PATH_ORYGINAL + movie.getPoster_path(), poster, ImageTools.DRAWABLE_FILM);
                        showDataOfMovie(movie);

                    } else {

                    }

                }

                @Override
                public void onFailure(Call<Movie> call, Throwable t) {

                    showError("Brak połączenia internetowego!");

                }
            });
        } catch (Throwable e) {
            showError("Nieoczekiwany błąd!");
        }

    }

    @UiThread
    public void showError(String message) {

        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();

    }

    @UiThread
    public void showDataOfMovie(Movie movie) {

        title.setText(movie.getTitle());
        oryginalTitle.setText(movie.getOriginal_title());
        overview.setText(movie.getOverview());
        voteAverage.setText(Double.toString(movie.getVote_average()));
        releaseData.setText(movie.getRelease_date());

    }

}
