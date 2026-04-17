package pt.appinionsdk.appinion.android.ui.ui.feedbackFlow

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.appinionsdk.appinion.android.ui.appinionRatingBar.AppinionRatingBar
import pt.appinionsdk.appinion.android.ui.ui.feedBackContainer.FeedBackContainer
import pt.appinionsdk.appinion.android.ui.ui.theme.LocalSdkImages
import pt.appinionsdk.appinion.android.ui.ui.theme.getAppIcon

// Step 1
@Composable
fun StoreFeedbackAskView(
    firstMessage: String,
    type:String,
    like: () -> Unit,
    notLike: () -> Unit,
    closeClick: () -> Unit
) {
    var selectedRating by remember { mutableStateOf(0) }
    val context = LocalContext.current

    // 1. Obtemos o ícone da app (usando a função que criamos antes)
    val appIconBitmap = remember(context) { getAppIcon(context) }
    FeedBackContainer(
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                appIconBitmap?.let { bitmap ->
                    Image(bitmap = bitmap, contentDescription = "App Logo")
                } ?: run {
                    Image(
                        painter = painterResource(id = LocalSdkImages.current.sdkFirstImage),
                        contentDescription = null
                    )
                }
            }
            Spacer(Modifier.height(24.dp))

            Text(
                modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                text = firstMessage,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )

            Spacer(Modifier.height(32.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                if (type.lowercase() == "rating"){
                    AppinionRatingBar(
                        rating = selectedRating,
                        onRatingChange = {
                            selectedRating = it
                            if (it > 3){
                                like()
                            }else {
                                notLike()
                            }
                        }
                    )
                }else {
                    IconButton(
                        modifier = Modifier.size(54.dp),
                        onClick = like
                    ) { Text(text = "\uD83D\uDC4D", fontSize = 40.sp) }
                    IconButton(modifier = Modifier.size(54.dp), onClick = notLike) {
                        Text(
                            text = "👎",
                            fontSize = 40.sp
                        )
                    }
                }

            }
        }
    ) {
        closeClick()
    }
}