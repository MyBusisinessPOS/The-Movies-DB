package com.solucionestpvpos.themoviesegc.interfaces

interface Constant {

    companion object{

        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val PATH_IMAGE = "https://image.tmdb.org/t/p/w500"
        const val API_KEY_MOVIES = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI1MzBiODQ4NDVkZmRhNzI0YTQ0YmZkNTJmNjk0NjkwYyIsInN1YiI6IjYxZDkxY2JmMWMwOWZiMDA0MTk5NTA4NSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.zeUBnXDM7O-1Jvfisx68Ow9NCL1h7WREv48h9mOvYVw"
        const val CALL_PLAYING_LOGS = "callPlayingLogs"
        const val CALL_POPULAR_LOGS = "callPopularsLogs"
        const val PLAYING = "playing"
        const val POPULAR = "popular"
        const val MAX_CACHE_SIZE_MB = 10

    }
}