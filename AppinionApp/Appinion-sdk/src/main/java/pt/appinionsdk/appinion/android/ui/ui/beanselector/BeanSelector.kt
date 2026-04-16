package pt.appinionsdk.appinion.android.ui.ui.beanselector

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BeanSelector(
    items: List<BeanItem>,
    selectedItems: Set<Int>,
    onSelectionChanged: (Int) -> Unit
) {
    // FlowRow organiza os beans e muda de linha automaticamente
    FlowRow(
        modifier = Modifier.padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items.forEach { item ->
            val isSelected = selectedItems.contains(item.id)

            FilterChip(
                selected = isSelected,
                onClick = { onSelectionChanged(item.id) },
                label = {
                    Text(
                        text = item.label,
                        maxLines = 1,
                        fontWeight = FontWeight.Normal,
                        style = MaterialTheme.typography.bodyMedium,
                        autoSize = TextAutoSize.StepBased(
                            minFontSize = 10.sp,
                            maxFontSize = MaterialTheme.typography.bodyMedium.fontSize, // nunca aumenta
                            stepSize = 1.sp
                        )
                    )
                },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                    selectedLabelColor = MaterialTheme.colorScheme.onSecondary
                )
            )
        }
    }
}

data class BeanItem(
    val id: Int,
    val label: String
)

@Composable
@Preview(showBackground = true)
fun BeanSelectorPreview() {

    val beans = listOf(
        BeanItem(1, "Opção A"),
        BeanItem(2, "Opção B"),
        BeanItem(3, "Opção C")
    )

    var selectedIds by remember { mutableStateOf(setOf<Int>(1)) }

    Column {
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