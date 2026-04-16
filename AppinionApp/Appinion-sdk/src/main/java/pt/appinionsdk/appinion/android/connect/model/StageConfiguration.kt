package pt.appinionsdk.appinion.android.connect.model


import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class StageConfiguration(
    @SerialName("app")
    val app: String? = null,
    @SerialName("ask_every")
    val askEvery: Int? = null,
    @SerialName("creation_date")
    val creationDate: String? = null,
    @SerialName("final_message")
    val finalMessage: String? = null,
    @SerialName("first_message")
    val firstMessage: String? = null,
    @SerialName("id")
    val id: String = "",
    @SerialName("name")
    val name: String? = null,
    @SerialName("questions")
    val questions: List<String>? = null,
    @SerialName("questiontype")
    val questiontype: Int? = null,
    @SerialName("second_message")
    val secondMessage: String? = null,
    @SerialName("status")
    val status: String? = null,
    @SerialName("type")
    val type: String? = null,
    @SerialName("version_toggle")
    val versionToggle: Boolean? = null,
){
    val active: Boolean
        get() = status == "active"
}