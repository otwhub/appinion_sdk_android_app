package pt.appinionsdk.appinion.android.connect.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
data class Feedback(
    @SerialName("id")
    val id: String = "",
    @SerialName("stage")
    val stage: String,

    @SerialName("vote")
    val vote: Boolean,

    @SerialName("feedback")
    val feedback: String? = null,

    @SerialName("answers")
    val answers: List<String>,

    @SerialName("device")
    val device: String? = null,

    @SerialName("os")
    val os: String? = null,

    @SerialName("version")
    val version: String? = null,

    @SerialName("completed")
    val completed: Boolean,

    @SerialName("date")
    val date: String? = null,

    @SerialName("extra_data")
    val extraData: List<Map<String, String>>? = null,

    @SerialName("rating")
    val rating: Int? = null
)