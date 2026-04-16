package pt.appinionsdk.appinion.android.ui.ui.feedbackFlow

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pt.appinionsdk.appinion.android.appinionsdk.AuthorizationData
import pt.appinionsdk.appinion.android.connect.model.Feedback
import pt.appinionsdk.appinion.android.connect.services.datasharing.DataSharingService

internal class FeedbackViewModel(): ViewModel() {
    fun sendFeedbackToServer(feedback: Feedback, completionHandler: ()-> Unit){
        viewModelScope.launch(Dispatchers.IO){
            DataSharingService().sendFeedback(
                feedback = feedback,
                stage = AuthorizationData.authorizationData?.app ?: "",
                completionHandler  = { _, error ->
                    if (error != null){
                        Log.e("FEEDBACK", error.toString())
                    }
                    viewModelScope.launch(Dispatchers.Main){
                        completionHandler()
                    }
                }
            )
        }
    }
}