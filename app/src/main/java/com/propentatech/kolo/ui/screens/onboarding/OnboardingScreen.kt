package com.propentatech.kolo.ui.screens.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.propentatech.kolo.R
import androidx.lifecycle.viewmodel.compose.viewModel
import com.propentatech.kolo.ui.components.KoloButton
import com.propentatech.kolo.ui.localization.LocalStrings
import com.propentatech.kolo.viewmodel.KoloViewModel

@Composable
fun OnboardingScreen(
    onFinished: () -> Unit,
    viewModel: KoloViewModel = viewModel(factory = com.propentatech.kolo.viewmodel.KoloViewModelFactory(
        (androidx.compose.ui.platform.LocalContext.current.applicationContext as com.propentatech.kolo.KoloApplication).repository,
        (androidx.compose.ui.platform.LocalContext.current.applicationContext as com.propentatech.kolo.KoloApplication).preferences,
        (androidx.compose.ui.platform.LocalContext.current.applicationContext as com.propentatech.kolo.KoloApplication).backupManager
    ))
) {
    val strings = LocalStrings.current
    var currentPage by remember { mutableIntStateOf(0) }

    val pages = listOf(
        Pair(strings.onboardingTitle1, strings.onboardingDesc1),
        Pair(strings.onboardingTitle2, strings.onboardingDesc2),
        Pair(strings.onboardingTitle3, strings.onboardingDesc3)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Skip Button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(
                onClick = {
                    viewModel.completeOnboarding()
                    onFinished()
                }
            ) {
                Text(
                    text = strings.onboardingSkip,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Logo
        Box(
            modifier = Modifier
                .size(300.dp)
                .clip(CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_kolo),
                contentDescription = "Kolo Logo",
                modifier = Modifier.size(120.dp),
                contentScale = ContentScale.Fit
            )
        }

        Spacer(modifier = Modifier.height(48.dp))

        // Text Content
        Text(
            text = pages[currentPage].first,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = pages[currentPage].second,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.weight(1f))

        // Pager Indicators
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            repeat(pages.size) { index ->
                val isSelected = index == currentPage
                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(if (isSelected) 10.dp else 8.dp)
                        .clip(CircleShape)
                        .background(
                            if (isSelected) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.surfaceVariant
                        )
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Action Button
        KoloButton(
            text = if (currentPage == pages.size - 1) strings.onboardingGetStarted else strings.onboardingNext,
            onClick = {
                if (currentPage < pages.size - 1) {
                    currentPage++
                } else {
                    viewModel.completeOnboarding()
                    onFinished()
                }
            }
        )
    }
}
