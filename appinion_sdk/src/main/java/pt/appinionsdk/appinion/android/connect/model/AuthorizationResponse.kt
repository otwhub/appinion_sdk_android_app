package pt.appinionsdk.appinion.android.connect.model


import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class AuthorizationResponse(
    @SerialName("data")
    val `data`: Data? = null,
    @SerialName("message")
    val message: String? = null,
    @SerialName("status")
    val status: String? = null,
    @SerialName("app")
    var app: String? = null,
)