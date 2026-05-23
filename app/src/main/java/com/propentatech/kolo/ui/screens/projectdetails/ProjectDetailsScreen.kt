package com.propentatech.kolo.ui.screens.projectdetails

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.propentatech.kolo.data.local.entities.ProjectEntity
import com.propentatech.kolo.data.local.entities.ProjectItemEntity
import com.propentatech.kolo.domain.KoloUtils
import com.propentatech.kolo.ui.localization.LocalStrings
import com.propentatech.kolo.viewmodel.KoloViewModel
import com.propentatech.kolo.viewmodel.KoloViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectDetailsScreen(
    projectId: Long,
    onBack: () -> Unit,
    onAddItem: () -> Unit,
    onAddSaving: () -> Unit,
    onViewHistory: () -> Unit,
    viewModel: KoloViewModel = viewModel(
        factory = KoloViewModelFactory(
            (LocalContext.current.applicationContext as com.propentatech.kolo.KoloApplication).repository,
            (LocalContext.current.applicationContext as com.propentatech.kolo.KoloApplication).preferences,
            (LocalContext.current.applicationContext as com.propentatech.kolo.KoloApplication).backupManager
        )
    )
) {
    val strings = LocalStrings.current
    val project by viewModel.getProjectById(projectId).collectAsState(initial = null)
    val items by viewModel.getProjectItems(projectId).collectAsState(initial = emptyList())
    
    val targetAmount by viewModel.getProjectTargetAmount(projectId).collectAsState(initial = 0.0)
    val savedAmount by viewModel.getProjectSavedAmount(projectId).collectAsState(initial = 0.0)

    var showDeleteConfirm by remember { mutableStateOf(false) }
    var showFabMenu by remember { mutableStateOf(false) }
    var itemToDelete by remember { mutableStateOf<ProjectItemEntity?>(null) }
    var itemToEdit by remember { mutableStateOf<ProjectItemEntity?>(null) }

    if (project == null) return

    val currentProject = project!!
    val progress = KoloUtils.calculateProgress(savedAmount, targetAmount)

    // Modal de confirmation pour supprimer le projet
    if (showDeleteConfirm) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirm = false },
            title = { Text(strings.projectDelete) },
            text = { Text(strings.projectDeleteConfirm) },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDeleteConfirm = false
                        viewModel.deleteProjectById(projectId)
                        onBack()
                    }
                ) {
                    Text(strings.yes, color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteConfirm = false }) {
                    Text(strings.cancel)
                }
            }
        )
    }

    // Modal de confirmation pour supprimer un élément
    itemToDelete?.let { item ->
        AlertDialog(
            onDismissRequest = { itemToDelete = null },
            title = { Text(strings.delete) },
            text = { Text("${strings.projectDeleteConfirm} '${item.title}' ?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.deleteProjectItem(item)
                        itemToDelete = null
                    }
                ) {
                    Text(strings.yes, color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { itemToDelete = null }) {
                    Text(strings.cancel)
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(strings.projectDetails) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = strings.cancel)
                    }
                },
                actions = {
                    IconButton(onClick = onViewHistory) {
                        Icon(Icons.Default.History, contentDescription = strings.savingsHistory)
                    }
                    IconButton(onClick = { showDeleteConfirm = true }) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = strings.projectDelete,
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        floatingActionButton = {
            SpeedDialFAB(
                expanded = showFabMenu,
                onExpandedChange = { showFabMenu = it },
                onAddItem = {
                    showFabMenu = false
                    onAddItem()
                },
                onAddSaving = {
                    showFabMenu = false
                    onAddSaving()
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header Section
            item {
                ProjectHeader(
                    project = currentProject,
                    savedAmount = savedAmount,
                    targetAmount = targetAmount,
                    progress = progress
                )
            }

            // Items List
            item {
                Text(
                    text = strings.itemsList,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
                )
            }

            if (items.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = strings.itemsEmpty,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            } else {
                items(items, key = { it.id }) { item ->
                    ProjectItemRow(
                        item = item,
                        onDelete = { itemToDelete = item },
                        onEdit = { itemToEdit = item }
                    )
                }
            }
            
            item { Spacer(modifier = Modifier.height(120.dp)) }
        }
    }
}

@Composable
fun ProjectHeader(
    project: ProjectEntity,
    savedAmount: Double,
    targetAmount: Double,
    progress: Float
) {
    val strings = LocalStrings.current
    
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text(
                text = project.title,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold
            )
            
            if (!project.description.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = project.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Big Stats
            Text(
                text = KoloUtils.formatAmountWithCurrency(savedAmount, strings.currency),
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "${strings.homeSaved} / ${KoloUtils.formatAmountWithCurrency(targetAmount, strings.currency)}",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(20.dp))
            
            // Progress Bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(fraction = (progress / 100f).coerceIn(0f, 1f))
                        .height(12.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(MaterialTheme.colorScheme.primary)
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = "${progress.toInt()}% ${strings.statsProgress}",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun ProjectForecast(
    project: ProjectEntity,
    savedAmount: Double,
    targetAmount: Double
) {
    val strings = LocalStrings.current
    val remaining = KoloUtils.calculateRemaining(savedAmount, targetAmount)
    val daily = KoloUtils.dailySavingTarget(remaining, project.targetDate)
    val weekly = KoloUtils.weeklySavingTarget(remaining, project.targetDate)
    val monthly = KoloUtils.monthlySavingTarget(remaining, project.targetDate)
    
    val daysRemaining = KoloUtils.daysRemaining(project.targetDate)
    
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = strings.statsSmartForecast,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            if (remaining <= 0) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = strings.statsCompleted,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            } else if (daysRemaining <= 0) {
                Text(
                    text = strings.statsOverdue,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.error
                )
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ForecastItem(strings.statsDailyTarget, daily)
                    ForecastItem(strings.statsWeeklyTarget, weekly)
                    ForecastItem(strings.statsMonthlyTarget, monthly)
                }
            }
        }
    }
}

@Composable
fun ForecastItem(label: String, amount: Double) {
    val strings = LocalStrings.current
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = KoloUtils.formatAmountWithCurrency(amount, strings.currency),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun ProjectItemRow(
    item: ProjectItemEntity,
    onDelete: () -> Unit,
    onEdit: () -> Unit
) {
    val strings = LocalStrings.current
    
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.weight(1f)
            )
            
            if (item.requiresSaving) {
                Text(
                    text = KoloUtils.formatAmountWithCurrency(item.amount, strings.currency),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                
                Spacer(modifier = Modifier.width(8.dp))
            }
            
            IconButton(onClick = onEdit) {
                Icon(
                    Icons.Default.Edit,
                    contentDescription = strings.editItem,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
            }
            
            IconButton(onClick = onDelete) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = strings.delete,
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
fun SpeedDialFAB(
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onAddItem: () -> Unit,
    onAddSaving: () -> Unit
) {
    val strings = LocalStrings.current
    val rotation by animateFloatAsState(
        targetValue = if (expanded) 45f else 0f,
        label = "FAB rotation"
    )
    
    Column(
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Mini FAB pour ajouter un élément
        AnimatedVisibility(
            visible = expanded,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Surface(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(8.dp),
                    tonalElevation = 2.dp,
                    shadowElevation = 4.dp
                ) {
                    Text(
                        text = strings.addItem,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                SmallFloatingActionButton(
                    onClick = onAddItem,
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        Icons.Default.ShoppingBag,
                        contentDescription = strings.addItem,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
        
        // Mini FAB pour ajouter une épargne
        AnimatedVisibility(
            visible = expanded,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Surface(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(8.dp),
                    tonalElevation = 2.dp,
                    shadowElevation = 4.dp
                ) {
                    Text(
                        text = strings.addSaving,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                SmallFloatingActionButton(
                    onClick = onAddSaving,
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        Icons.Default.Savings,
                        contentDescription = strings.addSaving,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
        
        // FAB principal
        FloatingActionButton(
            onClick = { onExpandedChange(!expanded) },
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            shape = RoundedCornerShape(16.dp)
        ) {
            Icon(
                imageVector = if (expanded) Icons.Default.Close else Icons.Default.Add,
                contentDescription = if (expanded) "Fermer" else "Ouvrir",
                modifier = Modifier
                    .size(24.dp)
                    .rotate(rotation)
            )
        }
    }
}
