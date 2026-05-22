package com.propentatech.kolo.ui.screens.createproject

import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.propentatech.kolo.domain.KoloUtils
import com.propentatech.kolo.ui.components.KoloButton
import com.propentatech.kolo.ui.components.KoloTextField
import com.propentatech.kolo.ui.localization.LocalStrings
import com.propentatech.kolo.viewmodel.KoloViewModel
import com.propentatech.kolo.viewmodel.KoloViewModelFactory
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateProjectScreen(
    onBack: () -> Unit,
    onProjectCreated: () -> Unit,
    viewModel: KoloViewModel = viewModel(
        factory = KoloViewModelFactory(
            (LocalContext.current.applicationContext as com.propentatech.kolo.KoloApplication).repository,
            (LocalContext.current.applicationContext as com.propentatech.kolo.KoloApplication).preferences,
            (LocalContext.current.applicationContext as com.propentatech.kolo.KoloApplication).backupManager
        )
    )
) {
    val strings = LocalStrings.current
    val context = LocalContext.current

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var targetDate by remember { mutableLongStateOf(0L) }
    
    var titleError by remember { mutableStateOf(false) }
    var dateError by remember { mutableStateOf(false) }

    // DatePicker Setup
    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val selectedCalendar = Calendar.getInstance()
            selectedCalendar.set(year, month, dayOfMonth)
            targetDate = selectedCalendar.timeInMillis
            dateError = false
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )
    datePickerDialog.datePicker.minDate = System.currentTimeMillis()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(strings.createProject) },
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
                label = strings.projectTitle,
                isError = titleError
            )
            if (titleError) {
                Text(
                    text = strings.projectTitleRequired,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            KoloTextField(
                value = description,
                onValueChange = { description = it },
                label = strings.projectDescription,
                singleLine = false,
                modifier = Modifier.height(120.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Fake text field for Date Picker
            Box(
                modifier = Modifier.clickable { datePickerDialog.show() }
            ) {
                KoloTextField(
                    value = if (targetDate > 0) KoloUtils.formatDate(targetDate) else "",
                    onValueChange = { },
                    label = strings.projectTargetDate,
                    isError = dateError,
                    enabled = false,
                    modifier = Modifier.alpha(if (targetDate > 0) 1f else 0.5f)
                )
                // Transparent overlay to catch clicks
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(Color.Transparent)
                )
            }
            if (dateError) {
                Text(
                    text = strings.projectDateRequired,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            KoloButton(
                text = strings.projectSave,
                onClick = {
                    val isTitleValid = title.isNotBlank()
                    val isDateValid = targetDate > 0

                    if (!isTitleValid) titleError = true
                    if (!isDateValid) dateError = true

                    if (isTitleValid && isDateValid) {
                        viewModel.createProject(title.trim(), description.trim(), targetDate)
                        onProjectCreated()
                    }
                }
            )
        }
    }
}
