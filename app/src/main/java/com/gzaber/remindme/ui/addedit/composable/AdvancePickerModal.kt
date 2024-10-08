package com.gzaber.remindme.ui.addedit.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun AdvancePickerModal(
    title: String,
    advanceUnits: List<String>,
    selectedAdvanceUnit: String,
    advanceValues: List<Int>,
    selectedAdvanceValue: Int,
    onAdvanceUnitSelected: (Int) -> Unit,
    onAdvanceValueSelected: (Int) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
            Row(
                modifier = Modifier
                    .selectableGroup()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                advanceUnits.forEachIndexed { index, text ->
                    Row(
                        Modifier
                            .weight(1f)
                            .selectable(
                                selected = (text == selectedAdvanceUnit),
                                onClick = { onAdvanceUnitSelected(index) },
                                role = Role.RadioButton
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (text == selectedAdvanceUnit),
                            onClick = null
                        )
                        Text(
                            text = text.lowercase(),
                            style = MaterialTheme.typography.bodyMedium.merge(),
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
            }
            FlowRow {
                for (i in advanceValues) {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 4.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "$i",
                            modifier = Modifier
                                .clickable { onAdvanceValueSelected(i) }
                                .background(
                                    color = if (i == selectedAdvanceValue) MaterialTheme.colorScheme.primary else
                                        MaterialTheme.colorScheme.surface
                                )
                                .border(BorderStroke(1.dp, MaterialTheme.colorScheme.primary))
                                .padding(horizontal = 8.dp, vertical = 8.dp)
                        )
                    }
                }
            }
        }
    }
}