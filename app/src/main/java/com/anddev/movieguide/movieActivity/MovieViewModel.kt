package com.anddev.movieguide.movieActivity

import android.widget.LinearLayout

class MovieViewModel {
    var title: String = ""
    var originalTitle: String = ""
    var overview: String = ""
    var releaseDate: String = ""
    var genres: String = ""
    var voteAverage: String = ""
    var runtime: String = ""
    var productionCountries: String = ""
    var posterUrl: String = ""
    var orienation = LinearLayout.VERTICAL
    var backgroundColor = -1
    var backgroundAlpha1 = 255
    var backgroundAlpha2 = 255
    var backgroundAlpha3 = 255


//    voteAverage.setText(getPercentageFromDouble(movie.getVote_average(), 10));
//    runtime.setText(movie.getRuntime() + " min.");
//    productionCountries.setText(movie.productionCountriesToString());
}