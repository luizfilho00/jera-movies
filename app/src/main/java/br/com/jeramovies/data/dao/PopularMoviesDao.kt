package br.com.jeramovies.data.dao

import androidx.paging.DataSource
import androidx.room.*
import br.com.jeramovies.data.entity.local.DbPopularMovie

@Dao
interface PopularMoviesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(rooms: List<DbPopularMovie>)

    @Query("SELECT * FROM DbPopularMovie ORDER BY sequenceId")
    fun getAll(): DataSource.Factory<Int, DbPopularMovie>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateMovie(room: DbPopularMovie)

    @Transaction
    @Query("UPDATE DbPopularMovie SET saved = :status WHERE id = :id")
    suspend fun updateMovieSavedStatus(id: Int, status: Boolean)

    @Query("SELECT COUNT(id) FROM DbPopularMovie")
    suspend fun getCount(): Int
}