package pt.appinionsdk.appinion.android.ui.ui.feedbackFlow

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.appinionsdk.appinion.android.appinionsdk.AuthorizationData
import pt.appinionsdk.appinion.android.connect.model.Feedback
import pt.appinionsdk.appinion.android.ui.ui.beanselector.BeanItem
import pt.appinionsdk.appinion.android.ui.ui.beanselector.BeanSelector
import pt.appinionsdk.appinion.android.ui.ui.feedBackContainer.FeedBackContainer
import pt.appinionsdk.appinion.android.ui.ui.itens_selector.ItensSelector
import pt.appinionsdk.appinion.android.ui.ui.theme.White
import pt.appinionsdk.appinion.android.utils.getDeviceInfo

// Step 2
@Composable
fun StoreFeedbackMoreView(
    id: String,
    secondMessage: String,
    questionType: Int,
    questions: List<String>,
    onClickSend: () -> Unit,
    onClickNotNow: () -> Unit,
    closeClick: () -> Unit
) {
    val viewmodel by remember { mutableStateOf(FeedbackViewModel()) }
    var selectedIds by remember { mutableStateOf(setOf<Int>()) }
    var text by remember { mutableStateOf("") }
    val beans by remember {
        mutableStateOf(
            questions.mapIndexed { index, item ->
                BeanItem(index, item)
            }
        )
    }

    FeedBackContainer(
        content = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .wrapContentHeight(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = secondMessage,
                    modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

            Spacer(Modifier.height(16.dp))

            Box(modifier = Modifier.fillMaxWidth()) {
                if (questionType == 1) {
                    ItensSelector(
                        items = beans,
                        selectedItems = selectedIds,
                        onSelectionChanged = { id ->
                            selectedIds = if (selectedIds.contains(id)) {
                                selectedIds - id
                            } else {
                                selectedIds + id
                            }

                        }
                    )
                }else{
                    BeanSelector(
                        items = beans,
                        selectedItems = selectedIds,
                        onSelectionChanged = { id ->
                            selectedIds = if (selectedIds.contains(id)) {
                                selectedIds - id
                            } else {
                                selectedIds + id
                            }
                        }
                    )
                }
            }
            Spacer(Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                val keyboardController = LocalSoftwareKeyboardController.current
                val focusManager = LocalFocusManager.current

                Column(modifier = Modifier.padding(vertical = 8.dp)) {
//TODO: Resource
                    Text(
                        "Please, let us know more:",
                        fontWeight = FontWeight.Normal,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onPrimary
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    BasicTextField(
                        value = text,
                        onValueChange = { text = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .border(
                                width = 1.dp,
                                color = Color.Gray,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(12.dp),
                        textStyle = TextStyle(fontSize = 16.sp),
                        cursorBrush = SolidColor(Color(0xFF2962FF)),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = {
                            keyboardController?.hide()
                            focusManager.clearFocus()
                        })
                    )
                }
            }
            Spacer(Modifier.height(16.dp))
            Box(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        modifier = Modifier
                            .width(200.dp),
                        shape = RoundedCornerShape(15.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        onClick = {
                            val feedback = selectedIds.map {it -> beans[it].label }
                            val feedbackBody = Feedback(
                                stage = id,
                                vote = false,
                                feedback = text,
                                answers = feedback,
                                device = getDeviceInfo().manufacturer +" "+getDeviceInfo().model,
                                os = "Android",
                                version = getDeviceInfo().osVersion,
                                completed = true
                            )
                            viewmodel.sendFeedbackToServer(
                                feedback = feedbackBody,
                                completionHandler = {
                                    onClickSend()
                                }
                            )
                        }
                    ) {
                        Text(text = "Send", color = MaterialTheme.colorScheme.onSecondary)
                    }
                    Button(
                        modifier = Modifier.width(200.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = White),
                        onClick = {
                            val feedback = selectedIds.map {it -> beans[it].label }
                            val feedbackBody = Feedback(
                                stage = id,
                                vote = false,
                                feedback = text,
                                answers = feedback,
                                device = getDeviceInfo().manufacturer +" "+getDeviceInfo().model,
                                os = "Android",
                                version = getDeviceInfo().osVersion,
                                completed = false
                            )
                            viewmodel.sendFeedbackToServer(
                                feedback = feedbackBody,
                                completionHandler = {
                                    onClickNotNow()
                                }
                            )
                        }
                    ) {
                        Text(
                            text = "Not now",
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
        }
    ) {
        val feedback = selectedIds.map {it -> beans[it].label }
        val feedbackBody = Feedback(
            stage = id,
            vote = false,
            feedback = text,
            answers = feedback,
            device = getDeviceInfo().manufacturer +" "+getDeviceInfo().model,
            os = "Android",
            version = getDeviceInfo().osVersion,
            completed = false
        )
        viewmodel.sendFeedbackToServer(
            feedback = feedbackBody,
            completionHandler = {
                closeClick()
            }
        )

    }

}