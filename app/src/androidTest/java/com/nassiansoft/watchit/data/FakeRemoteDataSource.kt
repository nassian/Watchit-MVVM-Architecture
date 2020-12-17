package com.nassiansoft.watchit.data

import com.nassiansoft.watchit.data.model.Movie
import com.nassiansoft.watchit.data.network.RemoteDataSourceInterface
import com.nassiansoft.watchit.data.network.Resource
import io.reactivex.Flowable

class FakeRemoteDataSource:RemoteDataSourceInterface {

    override fun searchMovie(term: String): Flowable<Resource<List<Movie>>> {
       return Flowable.just(Resource.Success(list))
    }

    companion object{
        val movie1=Movie(
            trackId = "543685056",
            trackName = "Taxi Driver",
            trackViewUrl = "https://itunes.apple.com/us/movie/taxi-driver/id543685056?uo=4",
            previewUrl="https://video-ssl.itunes.apple.com/itunes-assets/Video127/v4/96/96/20/969620b6-632d-77a5-5379-8fc00766046a/mzvf_4879542103527864225.640x458.h264lc.U.p.m4v",
            artworkUrl100="https://is1-ssl.mzstatic.com/image/thumb/Video71/v4/b7/51/26/b751263e-df03-1fc3-aad4-b7f968e6dc2e/source/100x100bb.jpg",
            releaseDate="1976-03-04T08:00:00Z",
            trackTimeMillis="6837997",
            primaryGenreName="Drama",
            longDescription="Considered among the best films of all time, Martin Scorsese's Taxi Driver was nominated for Academy Awards® for Best Picture, Best Actor (Robert De Niro), Best Supporting Actress (Jodie Foster) and Best Original Score (Bernard Herrmann). Solitary, alienated, and emotionally scarred from Vietnam, taxi driver Travis Bickle (De Niro) works the night shift in Manhattan.Though the world around him is teeming with life, Travis is unable to connect with anyone. His date with Betsy (Cybill Shepherd), a beautiful campaign aide, fails when he takes her to a porno movie. He tries to save a 12-year-old prostitute, Iris (Foster), from her pimp, Sport (Harvey Keitel) - though it's unclear Iris wants to be saved. Travis's pent-up anger and misplaced loyalty finally boil over in a paroxysm of revenge and violence.",
            country="USA", inMyList=true, watched=false)

        val movie2=Movie(trackId="1338530881",
            trackName="Shrek",
            trackViewUrl="https://itunes.apple.com/us/movie/shrek/id1338530881?uo=4",
            previewUrl="https://video-ssl.itunes.apple.com/itunes-assets/Video62/v4/d7/1f/5e/d71f5ef7-ae4b-20b8-874e-4c0639ef0833/mzvf_5932144163492999631.640x478.h264lc.U.p.m4v",
            artworkUrl100="https://is5-ssl.mzstatic.com/image/thumb/Video118/v4/86/db/d6/86dbd661-f524-18ec-a78b-952d5927f0b2/source/100x100bb.jpg",
            releaseDate="2001-05-16T07:00:00Z",
            trackTimeMillis="5431097",
            primaryGenreName="Kids & Family",
            longDescription="Winner of the first Academy® Award for Best Animated Feature (2002), Shrek sparked a motion picture phenomenon and captured the world’s imagination with…the Greatest Fairy Tale Never Told! Shrek (Mike Myers) goes on a quest to rescue the feisty Princess Fiona (Cameron Diaz) with the help of his loveable Donkey (Eddie Murphy) and win back the deed to his swamp from scheming Lord Farquaad.",
            country="USA", inMyList=true, watched=false)

        val list= listOf(movie1,movie2)
    }


}