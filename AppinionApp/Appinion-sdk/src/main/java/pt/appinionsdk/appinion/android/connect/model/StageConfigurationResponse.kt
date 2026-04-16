package pt.appinionsdk.appinion.android.connect.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
data class StageConfigurationResponse(
    @SerialName("data")
    val `data`: StageConfiguration? = StageConfiguration(),
    @SerialName("status")
    val status: String? = ""
)