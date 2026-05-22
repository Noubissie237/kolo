package com.propentatech.kolo.domain

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

/**
 * Utility functions for financial calculations and formatting.
 *
 * All business logic lives here — not in composables or ViewModels.
 */
object KoloUtils {

    // ========================================================
    // Financial Calculations
    // ========================================================

    /**
     * Calculate progress percentage (0-100).
     */
    fun calculateProgress(saved: Double, target: Double): Float {
        if (target <= 0) return 0f
        return ((saved / target) * 100).toFloat().coerceIn(0f, 100f)
    }

    /**
     * Calculate remaining amount to save.
     */
    fun calculateRemaining(saved: Double, target: Double): Double {
        return (target - saved).coerceAtLeast(0.0)
    }

    // ========================================================
    // Time Calculations
    // ========================================================

    /**
     * Calculate days remaining until target date.
     */
    fun daysRemaining(targetDate: Long): Long {
        val now = System.currentTimeMillis()
        val diff = targetDate - now
        return TimeUnit.MILLISECONDS.toDays(diff).coerceAtLeast(0)
    }

    /**
     * Calculate months and days remaining as a pair.
     */
    fun monthsAndDaysRemaining(targetDate: Long): Pair<Int, Int> {
        val totalDays = daysRemaining(targetDate)
        val months = (totalDays / 30).toInt()
        val days = (totalDays % 30).toInt()
        return Pair(months, days)
    }

    // ========================================================
    // Smart Forecasting
    // ========================================================

    /**
     * Calculate how much to save per day to reach the target.
     */
    fun dailySavingTarget(remaining: Double, targetDate: Long): Double {
        val days = daysRemaining(targetDate)
        if (days <= 0) return remaining
        return remaining / days
    }

    /**
     * Calculate how much to save per week to reach the target.
     */
    fun weeklySavingTarget(remaining: Double, targetDate: Long): Double {
        return dailySavingTarget(remaining, targetDate) * 7
    }

    /**
     * Calculate how much to save per month to reach the target.
     */
    fun monthlySavingTarget(remaining: Double, targetDate: Long): Double {
        return dailySavingTarget(remaining, targetDate) * 30
    }

    // ========================================================
    // Formatting
    // ========================================================

    /**
     * Format amount with thousands separator.
     * Example: 850000 → "850 000"
     */
    fun formatAmount(amount: Double): String {
        val format = NumberFormat.getNumberInstance(Locale.FRANCE)
        format.maximumFractionDigits = 0
        return format.format(amount)
    }

    /**
     * Format amount with currency.
     * Example: 850000 → "850 000 FCFA"
     */
    fun formatAmountWithCurrency(amount: Double, currency: String = "FCFA"): String {
        return "${formatAmount(amount)} $currency"
    }

    /**
     * Format a timestamp to a readable date string.
     * Example: "22 Mai 2026"
     */
    fun formatDate(timestamp: Long, locale: Locale = Locale.FRANCE): String {
        val sdf = SimpleDateFormat("dd MMM yyyy", locale)
        return sdf.format(Date(timestamp))
    }

    /**
     * Format a timestamp to a short date.
     * Example: "22/05/2026"
     */
    fun formatDateShort(timestamp: Long): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE)
        return sdf.format(Date(timestamp))
    }

    /**
     * Format time remaining as a human-readable string.
     */
    fun formatTimeRemaining(targetDate: Long, format: String): String {
        val (months, days) = monthsAndDaysRemaining(targetDate)
        return String.format(format, months, days)
    }
}
