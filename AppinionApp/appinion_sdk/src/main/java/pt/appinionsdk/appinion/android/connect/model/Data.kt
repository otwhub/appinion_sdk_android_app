package pt.appinionsdk.appinion.android.connect.model


import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Keep
@Serializable
data class Data(
    @SerialName("access_token")
    val accessToken: String? = null,
    @SerialName("expires_in")
    val expiresIn: Int? = null,
    @SerialName("scopes")
    val scopes: List<String?>? = null,
    @SerialName("subscription_id")
    val subscriptionId: String? = null,
    @SerialName("token_type")
    val tokenType: String? = null
)