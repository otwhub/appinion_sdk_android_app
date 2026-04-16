package pt.appinionsdk.appinion.android.connect.api.endpoint

import pt.appinionsdk.appinion.android.connect.model.Authorization
import pt.appinionsdk.appinion.android.connect.model.Feedback

fun Endpoint.Companion.authorize(app:String, key:String, secret:String, scope:String) = Endpoint(path = "/api/queries/authorize", body = Authorization(scope = scope, app = app))

fun Endpoint.Companion.configuration(stage: String, app:String) = Endpoint<Unit>(path = "/api/queries/configuration/$app", body = null, queryItems = mapOf("stage" to stage))

fun Endpoint.Companion.send(feedback: Feedback, app:String) = Endpoint(path = "/api/queries/$app", body = feedback)