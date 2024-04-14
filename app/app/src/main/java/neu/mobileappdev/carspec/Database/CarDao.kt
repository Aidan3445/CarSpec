package neu.mobileappdev.carspec.Database
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CarDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(car: FavoriteCar)

    @Delete
    suspend fun deleteFavorite(car: FavoriteCar)

    @Query("SELECT * FROM favorites")
    suspend fun getAllFavorites(): List<FavoriteCar>

    @Query("SELECT * FROM favorites WHERE id = :carId")
    suspend fun getFavoriteById(carId: Int): FavoriteCar?
}
