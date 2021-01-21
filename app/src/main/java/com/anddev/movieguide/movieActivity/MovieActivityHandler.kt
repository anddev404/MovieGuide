package com.anddev.movieguide.movieActivity

import android.app.Activity
import android.content.Context
import android.widget.Toast
import com.anddev.movieguide.R
import com.anddev.movieguide.model.Movie
import com.anddev.movieguide.tools.DateTools
import com.anddev.movieguide.trailersActivity.TrailersActivity

class MovieActivityHandler {
    var activity: Activity
    var movieActivity: MovieActvity

    constructor(movieActivity: MovieActvity) {
        this.activity = movieActivity
        this.movieActivity = movieActivity
    }

    fun youtubeOnClick() {
        Toast.makeText(activity, "Clik", Toast.LENGTH_SHORT).show()
        if (movieActivity.movie != null) {
            try {
                TrailersActivity.goToActivity(activity, movieActivity.movie.getTitle() + " " + DateTools.getOnlyYear(movieActivity.movie.getRelease_date()))
            } catch (e: Exception) {
                try {
                    TrailersActivity.goToActivity(activity, movieActivity.movie.getTitle())
                } catch (ee: Exception) {
                    Toast.makeText(activity, activity.getString(R.string.no_results), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}