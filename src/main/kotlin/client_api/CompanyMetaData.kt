import kotlinx.serialization.Serializable

@Serializable
data class CompanyMetaData(
    val Categories: List<Category>,
    val address: String,
    val id: String,
    val name: String
)