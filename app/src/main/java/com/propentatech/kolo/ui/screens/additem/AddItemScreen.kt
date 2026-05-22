package com.propentatech.kolo.ui.screens.additem

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.propentatech.kolo.ui.components.KoloButton
import com.propentatech.kolo.ui.components.KoloTextField
import com.propentatech.kolo.ui.localization.LocalStrings
import com.propentatech.kolo.viewmodel.KoloViewModel
import com.propentatech.kolo.viewmodel.KoloViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItemScreen(
    projectId: Long,
    onBack: () -> Unit,
    onItemAdded: () -> Unit,
    viewModel: KoloViewModel = viewModel(
        factory = KoloViewModelFactory(
            (LocalContext.current.applicationContext as com.propentatech.kolo.KoloApplication).repository,
            (LocalContext.current.applicationContext as com.propentatech.kolo.KoloApplication).preferences,
            (LocalContext.current.applicationContext as com.propentatech.kolo.KoloApplication).backupManager
        )
    )
) {
    val strings = LocalStrings.current

    var title by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var requiresSaving by remember { mutableStateOf(false) }

    var titleError by remember { mutableStateOf(false) }
    var amountError by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(strings.addItem) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = strings.cancel)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp)
        ) {
            KoloTextField(
                value = title,
                onValueChange = { 
                    title = it
                    titleError = false
                },
                label = strings.itemTitle,
                isError = titleError
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = strings.itemRequiresSaving,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.weight(1f))
                Switch(
                    checked = requiresSaving,
                    onCheckedChange = { 
                        requiresSaving = it
                        if (!it) {
                            amount = ""
                            amountError = false
                        }
                    }
                )
            }

            AnimatedVisibility(visible = requiresSaving) {
                Column {
                    Spacer(modifier = Modifier.height(16.dp))
                    KoloTextField(
                        value = amount,
                        onValueChange = { 
                            amount = it
                            amountError = false
                        },
                        label = strings.itemAmount,
                        isError = amountError,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            KoloButton(
                text = strings.projectSave,
                onClick = {
                    val isTitleValid = title.isNotBlank()
                    var isAmountValid = true
                    var parsedAmount = 0.0

                    if (!isTitleValid) titleError = true

                    if (requiresSaving) {
                        parsedAmount = amount.toDoubleOrNull() ?: 0.0
                        isAmountValid = parsedAmount > 0
                        if (!isAmountValid) amountError = true
                    }

                    if (isTitleValid && isAmountValid) {
                        viewModel.addProjectItem(
                            projectId = projectId,
                            title = title.trim(),
                            amount = parsedAmount,
                            requiresSaving = requiresSaving
                        )
                        onItemAdded()
                    }
                }
            )
        }
    }
}
