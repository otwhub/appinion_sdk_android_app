package pt.appinionsdk.appinion.android.connect.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
data class Authorization(
    @SerialName("app")
    val app: String? = null,
    @SerialName("scope")
    val scope: String? = null
)