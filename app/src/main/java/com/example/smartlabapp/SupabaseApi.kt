import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface SupabaseApi {

    @GET("rest/v1/actions")
    fun getActions(@Header("Authorization") apiKey: String): Call<SupabaseResponse<Action>>

    @GET("rest/v1/products")
    fun getProducts(@Header("Authorization") apiKey: String): Call<SupabaseResponse<Product>>
}

data class Action(
    val id: String,
    val imageResId: Int, // Идентификатор ресурса изображения
    val title: String // Заголовок или описание акции
)

data class Category(
    val id: String,
    val name: String
)
data class Product(
    val id: String,
    val name: String,
    val price: String
)

data class SupabaseResponse<T>(
    val data: List<T>,
    val error: String?
)
