package com.propentatech.kolo.ui.screens.addsaving

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
fun AddSavingScreen(
    projectId: Long,
    onBack: () -> Unit,
    onSavingAdded: () -> Unit,
    viewModel: KoloViewModel = viewModel(
        factory = KoloViewModelFactory(
            (LocalContext.current.applicationContext as com.propentatech.kolo.KoloApplication).repository,
            (LocalContext.current.applicationContext as com.propentatech.kolo.KoloApplication).preferences,
            (LocalContext.current.applicationContext as com.propentatech.kolo.KoloApplication).backupManager
        )
    )
) {
    val strings = LocalStrings.current

    var amount by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var amountError by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(strings.addSaving) },
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
                value = amount,
                onValueChange = { 
                    amount = it
                    amountError = false
                },
                label = strings.itemAmount,
                isError = amountError,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            KoloTextField(
                value = description,
                onValueChange = { description = it },
                label = strings.projectDescription,
                singleLine = false,
                modifier = Modifier.height(100.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            KoloButton(
                text = strings.projectSave,
                onClick = {
                    val parsedAmount = amount.toDoubleOrNull()
                    if (parsedAmount != null && parsedAmount > 0) {
                        viewModel.addSavingEntry(
                            projectId = projectId,
                            amount = parsedAmount,
                            description = description.trim().takeIf { it.isNotBlank() }
                        )
                        onSavingAdded()
                    } else {
                        amountError = true
                    }
                }
            )
        }
    }
}
