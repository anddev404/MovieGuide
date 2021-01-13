package com.anddev.movieguide.movieActivity

import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import com.anddev.movieguide.model.Credits
import com.anddev.movieguide.model.Movie
import com.anddev.movieguide.model.Movies
import com.anddev.movieguide.tools.ConnectionInterface
import com.anddev.movieguide.tools.DownloadManager
import com.anddev.movieguide.tools.ImageTools
import kotlinx.coroutines.async
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class Background() {
    companion object {
        fun downloadMovieInBackground(client: ConnectionInterface, id: Int, apiKey: String, language: String, downloadManager: DownloadManager, activity: MovieActvity, poster: ImageView) {
            runBlocking {
                joinAll(
                        async {
                            Handler(Looper.getMainLooper()).postDelayed(
                                    {
//
                                        try {
                                            val call: Call<Movie> = client.movie(id, apiKey, language)
                                            call.enqueue(object : Callback<Movie?> {
                                                override fun onResponse(call: Call<Movie?>, response: Response<Movie?>) {
                                                    if (response.code() == 200) {
                                                        activity.movie = response.body()
                                                        ImageTools.getWideImageFromInternet(activity, ImageTools.IMAGE_PATH_ORYGINAL + activity.movie.getBackdrop_path(), poster, ImageTools.DRAWABLE_FILM_WIDTH)
                                                        downloadManager.changeStateDataDownload(DownloadManager.DATA_IS_DOWNLOAD)
                                                        downloadManager.changeStateDownloadInProgress(false)
                                                    }
                                                }

                                                override fun onFailure(call: Call<Movie?>, t: Throwable) {
                                                    downloadManager.changeStateDataDownload(DownloadManager.DATA_IS_NOT_DOWNLOAD)
                                                    downloadManager.changeStateDownloadInProgress(false)
                                                }
                                            })
                                        } catch (e: kotlin.Throwable) {
                                            downloadManager.changeStateDataDownload(DownloadManager.DATA_IS_NOT_DOWNLOAD)
                                            downloadManager.changeStateDownloadInProgress(false)
                                        }


                                    },
                                    0
                            )
                        })
            }
        }

        fun downloadCreditsInBackground(client: ConnectionInterface, id: Int?, apiKey: String?, activity: MovieActvity) {
            try {
                val call = client.credits(id, apiKey)
                call.enqueue(object : Callback<Credits?> {
                    override fun onResponse(call: Call<Credits?>, response: Response<Credits?>) {
                        if (response.code() == 200) {
                            activity.credits = response.body()
                            activity.showCredits(activity.credits)
                        } else {
                        }
                    }

                    override fun onFailure(call: Call<Credits?>, t: Throwable) {}
                })
            } catch (e: Throwable) {
            }
        }

        fun downloadSimilarInBackground(client: ConnectionInterface, id: Int?, apiKey: String?, language: String?, page: Int?, activity: MovieActvity) {
            try {
                val call = client.similarMovies(id, apiKey, language, page)
                call.enqueue(object : Callback<Movies?> {
                    override fun onResponse(call: Call<Movies?>, response: Response<Movies?>) {
                        if (response.code() == 200) {
                            activity.similarMovies = response.body()
                            activity.showSimilar(activity.similarMovies)
                        } else {
                        }
                    }

                    override fun onFailure(call: Call<Movies?>, t: Throwable) {}
                })
            } catch (e: Throwable) {
            }
        }
    }
}
