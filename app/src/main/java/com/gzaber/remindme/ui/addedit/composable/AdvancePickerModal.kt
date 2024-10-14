package com.gzaber.remindme.ui.addedit.composable

import androidx.annotation.StringRes
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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.gzaber.remindme.R

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun AdvancePickerModal(
    advanceUnits: List<String>,
    selectedAdvanceUnit: String,
    advanceValues: List<Int>,
    selectedAdvanceValue: Int,
    onAdvanceUnitSelected: (Int) -> Unit,
    onAdvanceValueSelected: (Int) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    @StringRes title: Int = R.string.add_edit_advance_title,
    @StringRes closeButtonText: Int = R.string.close_button_text
) {
    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(title),
                    style = MaterialTheme.typography.titleMedium
                )
                TextButton(onClick = onDismiss) {
                    Text(text = stringResource(closeButtonText))
                }
            }
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
                            color = if (i == selectedAdvanceValue) MaterialTheme.colorScheme.onPrimary else
                                Color.Black,
                            modifier = Modifier
                                .clickable { onAdvanceValueSelected(i) }
                                .background(
                                    color = if (i == selectedAdvanceValue) MaterialTheme.colorScheme.primary else
                                        BottomSheetDefaults.ContainerColor
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