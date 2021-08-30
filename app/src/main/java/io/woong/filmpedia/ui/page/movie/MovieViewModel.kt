package io.woong.filmpedia.ui.page.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.woong.filmpedia.data.movie.Genres
import io.woong.filmpedia.repository.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.StringBuilder

class MovieViewModel : ViewModel() {

    private val repository: MovieRepository = MovieRepository()

    private val _title: MutableLiveData<String> = MutableLiveData()
    val title: LiveData<String>
        get() = _title

    private val _poster: MutableLiveData<String> = MutableLiveData()
    val poster: LiveData<String>
        get() = _poster

    private val _slides: MutableLiveData<List<String>> = MutableLiveData()
    val slides: LiveData<List<String>>
        get() = _slides

    private val _releaseDate: MutableLiveData<String> = MutableLiveData()
    val releaseDate: LiveData<String>
        get() = _releaseDate

    private val _runtime: MutableLiveData<String> = MutableLiveData()
    val runtime: LiveData<String>
        get() = _runtime

    private val _genres: MutableLiveData<List<Genres.Genre>> = MutableLiveData()
    val genres: LiveData<List<Genres.Genre>>
        get() = _genres

    private val _rating: MutableLiveData<Double> = MutableLiveData()
    val rating: LiveData<Double>
        get() = _rating

    private val _tagline: MutableLiveData<String> = MutableLiveData()
    val tagline: LiveData<String>
        get() = _tagline

    private val _overview: MutableLiveData<String> = MutableLiveData()
    val overview: LiveData<String>
        get() = _overview

    fun load(apiKey: String, language: String, movieId: Int) = CoroutineScope(Dispatchers.Default).launch {
        repository.fetchMovieDetail(key = apiKey, lang = language, id = movieId) { movie ->
            if (movie != null) {
                _title.postValue(movie.title)
                _poster.postValue(movie.posterPath)
                _releaseDate.postValue(movie.releaseDate)
                _runtime.postValue(convertRuntimeToString(movie.runtime))
                _rating.postValue(movie.voteAverage)
                _tagline.postValue(movie.tagline)
                _overview.postValue(movie.overview)
                _genres.postValue(movie.genres)
            }
        }

        repository.fetchImages(key = apiKey, id = movieId) { images ->
            if (images != null) {
                val slides = images.backdrops
                if (slides.isNotEmpty()) {
                    val slidePaths = mutableListOf<String>()
                    slides.forEach { slide ->
                        slidePaths.add(slide.path)
                    }
                    _slides.postValue(slidePaths)
                }
            }
        }
    }

    private fun convertRuntimeToString(runtime: Int?): String? {
        return if (runtime != null) {
            val builder = StringBuilder()
            var time = runtime

            val hours = time / 60
            time %= 60
            builder.append("(${hours}h ${time}m)")

            builder.toString()
        } else {
            null
        }
    }
}