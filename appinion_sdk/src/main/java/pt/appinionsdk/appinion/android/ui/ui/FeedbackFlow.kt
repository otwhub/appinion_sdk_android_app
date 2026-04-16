package pt.appinionsdk.appinion.android.ui.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Devices.PIXEL_6
import androidx.compose.ui.tooling.preview.Preview
import pt.appinionsdk.appinion.android.ui.ui.feedbackFlow.StoreFeedbackConclusionView
import pt.appinionsdk.appinion.android.ui.ui.feedbackFlow.StoreFeedbackMoreView
import pt.appinionsdk.appinion.android.ui.ui.theme.AppinionAppTheme


@Composable
@Preview(showBackground = true, showSystemUi = true, device = PIXEL_6)
fun StoreFeedbackPreview() {
//    StoreFeedbackConclusionView(
//        finalMessage = "Mensagem final"
//    ) { }
    AppinionAppTheme() {
//        StoreFeedbackAskView("Hello meu amigo", closeClick = {}, onClick = {})
        StoreFeedbackMoreView(
            id = "",
            secondMessage = "Sorry to hear that. Can you tell us more about your experience?",
            questions = listOf(
                "Took to long to complete?",
                "Could not complete at all",
                "Bad user experience"
            ),
            onClickSend = {},
            onClickNotNow = {}
        ) { }
        StoreFeedbackConclusionView(
            finalMessage = "Obrigado!",
            finishFlow = {}
        ) { }
    }
}