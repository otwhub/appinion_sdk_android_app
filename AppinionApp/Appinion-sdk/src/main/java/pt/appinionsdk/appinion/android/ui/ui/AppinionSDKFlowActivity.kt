package pt.appinionsdk.appinion.android.ui.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pt.appinionsdk.appinion.android.connect.model.StageConfiguration
import pt.appinionsdk.appinion.android.connect.services.datasharing.DataSharingService
import pt.appinionsdk.appinion.android.ui.ui.feedbackFlow.StoreFeedbackAskView
import pt.appinionsdk.appinion.android.ui.ui.feedbackFlow.StoreFeedbackConclusionView
import pt.appinionsdk.appinion.android.ui.ui.feedbackFlow.StoreFeedbackMoreView
import pt.appinionsdk.appinion.android.ui.ui.theme.AppinionAppTheme
import android.os.Parcelable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.google.android.play.core.review.ReviewManagerFactory
import kotlinx.parcelize.Parcelize

@Parcelize
data class AppinionConfig(
    val stage: String
) : Parcelable

class AppinionSDKFlowActivity : ComponentActivity() {
    companion object {
        private const val EXTRA_CONFIG = "com.appinion.sdk.EXTRA_CONFIG"

        /**
         * Método oficial para iniciar o fluxo do SDK.
         */

        fun launch(context: Context, config: AppinionConfig) {
            val intent = Intent(context, AppinionSDKFlowActivity::class.java).apply {
                putExtra(EXTRA_CONFIG, config)
            }
            context.startActivity(intent)
        }
    }

    // Recupera a configuração de forma segura
    private val sdkConfig: AppinionConfig by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_CONFIG, AppinionConfig::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_CONFIG)
        }
            ?: throw IllegalStateException("O AppinionSDK precisa de uma configuração válida para iniciar.")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        overridePendingTransition(R.anim.slide_up, R.anim.hold)
        lifecycleScope.launch(Dispatchers.IO) {
            DataSharingService().getConfiguration(
                stage = sdkConfig.stage,
                completionHandler = { resp, error ->
                    if (error == null) {
                        resp?.let { stageConfiguration ->
                            lifecycleScope.launch(Dispatchers.Main) {
                                openFlow(stageConfiguration, this@AppinionSDKFlowActivity)
                            }
                        }
                    } else {
                        val activity = this@AppinionSDKFlowActivity as? Activity
                        activity?.finish()
                    }
                }
            )
        }
    }

    private fun openFlow(stageConfiguration: StageConfiguration, activity: Activity) {
        enableEdgeToEdge()
        setContent {
            AppinionAppTheme {
                SdkNavigation(stageConfiguration, activity)
            }
        }
    }
}


@Composable
fun SdkNavigation(stageConfiguration: StageConfiguration, activity: Activity) {
    val navController = rememberNavController()
    val context = LocalContext.current
    NavHost(navController = navController, Steps.FIRST.step) {
        composable(Steps.FIRST.step) {
            StoreFeedbackAskView(
                firstMessage = stageConfiguration.firstMessage ?: "",
                type = stageConfiguration.type ?: "yn",
                like = {
                    navController.navigate(Steps.STORE.step)
                },
                notLike = {
                    navController.navigate(Steps.SECOND.step)
                },
                closeClick = {
                    val activity = context as? Activity
                    activity?.finish()
                }
            )
        }

        composable(Steps.STORE.step) {
            launchInAppReview(activity = activity){
                activity.finish()
            }
        }

        composable(Steps.SECOND.step) {
            StoreFeedbackMoreView(
                id = stageConfiguration.id,
                secondMessage = stageConfiguration.secondMessage ?: "",
                questions = stageConfiguration.questions ?: emptyList(),
                onClickSend = {
                    navController.navigate(Steps.CONCLUDED.step)
                },
                onClickNotNow = {
                    val activity = context as? Activity
                    activity?.finish()
                },
                closeClick = {
                    val activity = context as? Activity
                    activity?.finish()
                }
            )
        }

        composable(Steps.CONCLUDED.step) {
            StoreFeedbackConclusionView(
                finalMessage = "Thank you for your feedback" ?: "",
                finishFlow = {
                    val activity = context as? Activity
                    activity?.finish()
                },
                closeClick = {
                    val activity = context as? Activity
                    activity?.finish()
                }
            )
        }
    }
}

fun launchInAppReview(activity: Activity, completion:()-> Unit) {
//    val manager = FakeReviewManager(activity)
    val manager = ReviewManagerFactory.create(activity)
    val request = manager.requestReviewFlow()

    request.addOnCompleteListener { task ->
        if (task.isSuccessful) {
            val reviewInfo = task.result
            val flow = manager.launchReviewFlow(activity, reviewInfo)
            flow.addOnCompleteListener {
                completion()
            }
        } else {
            completion()
        }
    }
}

// Análise no futuro pra verificar como podemos fazer com a HarmonyStore
fun redirectToStore(context: Context) {
    val packageName = context.packageName // O ID da app hospedeira
    try {
        // Tenta abrir a app da Play Store
        context.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("market://details?id=$packageName")
            )
        )
    } catch (e: Exception) {
        // Se não houver Play Store (ex: Huawei), abre o browser
        context.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
            )
        )
    }
}

enum class Steps(val step: String) {
    FIRST("FIRST"),
    SECOND("SECOND"),
    CONCLUDED("CONCLUDED"),
    STORE("STORE")
}