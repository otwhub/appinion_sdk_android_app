package pt.appinionsdk.appinion.android.connect.services.datasharing

import pt.appinionsdk.appinion.android.appinionsdk.AuthorizationData
import pt.appinionsdk.appinion.android.connect.api.NetworkingController
import pt.appinionsdk.appinion.android.connect.api.endpoint.Endpoint
import pt.appinionsdk.appinion.android.connect.api.endpoint.configuration
import pt.appinionsdk.appinion.android.connect.api.endpoint.send
import pt.appinionsdk.appinion.android.connect.model.AppinionError
import pt.appinionsdk.appinion.android.connect.model.Feedback
import pt.appinionsdk.appinion.android.connect.model.StageConfiguration
import pt.appinionsdk.appinion.android.connect.model.StageConfigurationResponse

typealias Callback = (StageConfiguration?, AppinionError?) -> Unit
typealias SendFeedbackCallback = (Feedback?, AppinionError?) -> Unit

class DataSharingService {
    private val networkingController: NetworkingController = NetworkingController()

    suspend fun getConfiguration(stage: String, completionHandler: Callback) {
        val app = AuthorizationData.authorizationData?.app ?: run {
            completionHandler(null, AppinionError.expiredSubscription)
            return
        }

        val endpoint = Endpoint.configuration(stage = stage, app = app)
        val headers: MutableMap<String, String> = mutableMapOf()
        headers["Authorization"] = "${AuthorizationData.authorizationData?.data?.tokenType} " + AuthorizationData.authorizationData?.data?.accessToken

        val response = networkingController.get<StageConfigurationResponse>(
            url = endpoint.url,
            headers = headers
        )
        response.onSuccess {
            completionHandler(it.data, null)
        }
        response.onFailure {
            completionHandler(null, AppinionError.invalidServerResponse)
        }
    }

    suspend fun sendFeedback(
        feedback: Feedback,
        stage: String,
        completionHandler: SendFeedbackCallback
    ) {
        val endpoint = Endpoint.send(feedback = feedback, app = stage)

        val headers: MutableMap<String, String> = mutableMapOf()
        headers["Authorization"] = "${AuthorizationData.authorizationData?.data?.tokenType} " + AuthorizationData.authorizationData?.data?.accessToken

        val response = networkingController.post<Feedback, Any>(
            url = endpoint.url,
            body = endpoint.body,
            headers = headers
        )
        response.onSuccess {
            completionHandler(null, null)
        }
        response.onFailure {
            completionHandler(null, AppinionError.invalidServerResponse)
        }


    }
}