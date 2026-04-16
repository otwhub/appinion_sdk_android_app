package pt.appinionsdk.appinion.android.ui.appinionRatingBar

import android.R
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AppinionRatingBar(
    modifier: Modifier = Modifier,
    rating: Int,// Valor atual 0 - 5
    onRatingChange:(Int)-> Unit,
    maxItems:Int = 5,
    activeColor: Color = Color(0xFFFFD700),
    inativeColor: Color = Color(0xFFE0E0E0)
){
    Row(modifier = modifier){
        for (i in 1..maxItems){
            Icon(
                painter = painterResource(R.drawable.star_on),
                contentDescription = "Rating $rating",
                modifier = Modifier.size(48.dp).clickable(onClick = {onRatingChange(i)}).padding(4.dp),
                tint = if (i <= rating)activeColor else inativeColor
            )
        }
    }
}



@Composable
@Preview(showBackground = true)
fun AppinionRatingBarPreview(){
    AppinionRatingBar(
        rating = 3,
        onRatingChange = {}
    )
}
