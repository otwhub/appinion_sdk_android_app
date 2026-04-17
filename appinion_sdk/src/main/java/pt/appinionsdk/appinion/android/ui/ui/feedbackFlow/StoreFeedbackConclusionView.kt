package pt.appinionsdk.appinion.android.ui.ui.feedbackFlow

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import pt.appinionsdk.appinion.android.ui.ui.feedBackContainer.FeedBackContainer
import pt.appinionsdk.appinion.android.ui.ui.theme.LocalSdkImages

// Step 3
@Composable
fun StoreFeedbackConclusionView(
    finalMessage: String,
    finishFlow: () -> Unit,
    closeClick: () -> Unit
) {
    FeedBackContainer(
        content = {
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(60.dp),
//                horizontalArrangement = Arrangement.Center
//            ) {
//                Image(
//                    painter = painterResource(id = LocalSdkImages.current.sdkFirstImage),
//                    contentDescription = null
//                )
//            }
            Spacer(modifier = Modifier.height(24.dp))
            Text(modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                text = finalMessage,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary)

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                modifier = Modifier
                    .width(200.dp),
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                onClick = finishFlow
            ) {
                Text(color = MaterialTheme.colorScheme.onSecondary, text = "Close")
            }
        }
    ) {
        closeClick()
    }
}