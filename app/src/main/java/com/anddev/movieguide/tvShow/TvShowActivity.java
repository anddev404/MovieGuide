package com.anddev.movieguide.tvShow;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anddev.movieguide.R;
import com.anddev.movieguide.model.TvShow;
import com.anddev.movieguide.searchEngineActivity.SearchEngineActivity;
import com.anddev.movieguide.tools.ActionBarTools;
import com.anddev.movieguide.tools.ConnectionInterface;
import com.anddev.movieguide.tools.ImageTools;
import com.anddev.movieguide.tools.LanguageTools;
import com.anddev.movieguide.tools.NavigationDrawerTools;
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


@EActivity(R.layout.activity_tvshow)
public class TvShowActivity extends AppCompatActivity {

    Activity activity;
    NavigationDrawerTools navigationDrawer;
    ActionBarTools actionBarTools;

    TvShow tvShow;
    Integer tvShowId;

    @BindView(R.id.poster_tv_show_imageView)
    ImageView poster;

    @BindView(R.id.title_tv_show_textView)
    TextView title;

    @BindView(R.id.oryginal_title_tv_show_textView)
    TextView oryginalTitle;

    @BindView(R.id.overview_tv_show_TextView)
    TextView overview;

    @BindView(R.id.vote_average_tv_show_textView)
    TextView voteAverage;

    @BindView(R.id.release_data_tv_show_textView)
    TextView releaseData;

    @AfterViews
    public void onCreate() {
        this.activity = this;
        ButterKnife.bind(this);
        navigationDrawer = new NavigationDrawerTools(activity, R.id.movie_navigation_draver);
        actionBarTools = new ActionBarTools(this).addMenuButton().setTitle("TV Show");

        try {
            if (activity.getIntent().getExtras() != null) {
                tvShowId = activity.getIntent().getExtras().getInt("Id", 0);
                Toast.makeText(activity, "id" + tvShowId, Toast.LENGTH_SHORT).show();
            } else {
                tvShowId = RetrofitTools.EXAMPLE_ID_MOVIE;
            }
        } catch (Exception e) {
            tvShowId = RetrofitTools.EXAMPLE_ID_MOVIE;
        }
        ConnectionInterface client = RetrofitTools.getConnectionInterface();
        downloadTvShowInBackground(client, tvShowId, RetrofitTools.API_KEY, LanguageTools.getLanguage(this));
    }

    @Background
    void downloadTvShowInBackground(ConnectionInterface client, Integer id, String apiKey, String language) {
        try {

            Call<TvShow> call = client.tvShow(id, apiKey, language);

            call.enqueue(new Callback<TvShow>() {

                @Override
                public void onResponse(Call<TvShow> call, Response<TvShow> response) {

                    if (response.code() == 200) {
                        tvShow = response.body();
                        ImageTools.getImageFromInternet(activity, ImageTools.IMAGE_PATH_ORYGINAL + tvShow.getPoster_path(), poster, ImageTools.DRAWABLE_FILM);
                        showDataOfTvShow(tvShow);

                    } else {

                    }

                }

                @Override
                public void onFailure(Call<TvShow> call, Throwable t) {

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
    public void showDataOfTvShow(TvShow tvShow) {

        title.setText(tvShow.getName());
        oryginalTitle.setText(tvShow.getOriginal_name());
        overview.setText(tvShow.getOverview());
        voteAverage.setText(Double.toString(tvShow.getVote_average()));
        releaseData.setText(tvShow.getFirst_air_date());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar, menu);

        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint(getString(R.string.search_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                SearchEngineActivity.searchAndOpenResults(query, activity);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:

                navigationDrawer.openOrCloseNavigationDrawer();

                break;

        }

        return true;

    }
}
