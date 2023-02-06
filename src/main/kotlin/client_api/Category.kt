import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val `class`: String,
    val name: String
)