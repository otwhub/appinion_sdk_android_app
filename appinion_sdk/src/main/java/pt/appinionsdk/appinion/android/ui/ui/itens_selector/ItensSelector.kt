package pt.appinionsdk.appinion.android.ui.ui.itens_selector

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import pt.appinionsdk.appinion.android.ui.ui.beanselector.BeanItem



@Composable
fun ItensSelector(
    items: List<BeanItem>,
    selectedItems: Set<Int>,
    onSelectionChanged: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier.wrapContentHeight(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(items, key = { it.id }) { item ->
            ItemRow(
                item = item,
                isSelected = selectedItems.contains(item.id),
                onCheckedChange = { onSelectionChanged(item.id) }
            )
        }
    }
}

@Composable
private fun ItemRow(
    item: BeanItem,
    isSelected: Boolean,
    onCheckedChange: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange() }
            .padding(vertical = 12.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.weight(1F),
            text = item.label,
            style = MaterialTheme.typography.bodyLarge
        )
        Checkbox(
            checked = isSelected,
            colors = CheckboxDefaults.colors(checkmarkColor = Color.White),
            onCheckedChange = { onCheckedChange() }
        )
    }
}