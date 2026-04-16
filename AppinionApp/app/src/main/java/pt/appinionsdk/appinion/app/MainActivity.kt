package pt.appinionsdk.appinion.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import pt.appinionsdk.appinion.android.ui.ui.AppinionConfig
import pt.appinionsdk.appinion.android.ui.ui.AppinionSDKFlowActivity
import pt.appinionsdk.appinion.android.ui.appinionRatingBar.AppinionRatingBar
import pt.appinionsdk.appinion.app.ui.theme.AppinionAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AppinionAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    StartSDK(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun StartSDK(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Start SDK",
            modifier = modifier
        )
        Button(onClick = {

            val config = AppinionConfig(stage = "feedback")
            AppinionSDKFlowActivity.launch(context, config)

        }) {
            Text("Start")
        }
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppinionAppTheme {
        StartSDK()
    }
}