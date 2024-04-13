package neu.mobileappdev.carspec.Database
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "favorites")
data class FavoriteCar(
    @PrimaryKey val id: Int,
    val name: String,
    val make: String,
    val year: Int,
    val imageUrl: String
)
