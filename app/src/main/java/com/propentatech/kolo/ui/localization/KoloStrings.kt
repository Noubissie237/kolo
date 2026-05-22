package com.propentatech.kolo.ui.localization

/**
 * Kolo internationalization system.
 *
 * All UI strings are defined here as a sealed interface with two implementations:
 * French (default) and English. Database content (user input) is NEVER translated.
 *
 * Usage in Composables:
 *   val strings = LocalStrings.current
 *   Text(strings.homeTitle)
 */
data class KoloStrings(
    // ========================================================
    // App
    // ========================================================
    val appName: String,
    val slogan: String,

    // ========================================================
    // Onboarding
    // ========================================================
    val onboardingTitle1: String,
    val onboardingDesc1: String,
    val onboardingTitle2: String,
    val onboardingDesc2: String,
    val onboardingTitle3: String,
    val onboardingDesc3: String,
    val onboardingGetStarted: String,
    val onboardingNext: String,
    val onboardingSkip: String,

    // ========================================================
    // Navigation
    // ========================================================
    val navHome: String,
    val navSettings: String,

    // ========================================================
    // Home Screen
    // ========================================================
    val homeTitle: String,
    val homeEmptyTitle: String,
    val homeEmptySubtitle: String,
    val homeCreateFirst: String,
    val homeProjects: String,
    val homeProgress: String,
    val homeRemaining: String,
    val homeTimeLeft: String,
    val homeSaved: String,
    val homeTarget: String,

    // ========================================================
    // Project
    // ========================================================
    val createProject: String,
    val editProject: String,
    val projectTitle: String,
    val projectDescription: String,
    val projectTargetDate: String,
    val projectTitleRequired: String,
    val projectDateRequired: String,
    val projectSave: String,
    val projectDelete: String,
    val projectDeleteConfirm: String,
    val projectDetails: String,

    // ========================================================
    // Project Items
    // ========================================================
    val addItem: String,
    val editItem: String,
    val itemTitle: String,
    val itemAmount: String,
    val itemRequiresSaving: String,
    val itemTitleRequired: String,
    val itemsList: String,
    val itemsEmpty: String,
    val itemsTotal: String,

    // ========================================================
    // Savings
    // ========================================================
    val addSaving: String,
    val savingAmount: String,
    val savingDescription: String,
    val savingAmountRequired: String,
    val savingsHistory: String,
    val savingsHistoryEmpty: String,
    val savingsEmpty: String,
    val savingsTotal: String,

    // ========================================================
    // Statistics & Progress
    // ========================================================
    val statsTitle: String,
    val statsTargetAmount: String,
    val statsSavedAmount: String,
    val statsRemainingAmount: String,
    val statsProgress: String,
    val statsTimeRemaining: String,
    val statsDaysRemaining: String,
    val statsMonthsRemaining: String,
    val statsDailyTarget: String,
    val statsWeeklyTarget: String,
    val statsMonthlyTarget: String,
    val statsSmartForecast: String,
    val statsCompleted: String,
    val statsOverdue: String,

    // ========================================================
    // Time
    // ========================================================
    val days: String,
    val months: String,
    val timeRemainingFormat: String,

    // ========================================================
    // Settings
    // ========================================================
    val settingsTitle: String,
    val settingsLanguage: String,
    val settingsFrench: String,
    val settingsEnglish: String,
    val settingsBackup: String,
    val settingsRestore: String,
    val settingsExportData: String,
    val settingsImportData: String,
    val settingsExportSuccess: String,
    val settingsImportSuccess: String,
    val settingsImportError: String,
    val settingsAbout: String,
    val settingsVersion: String,

    // ========================================================
    // Common
    // ========================================================
    val save: String,
    val cancel: String,
    val delete: String,
    val confirm: String,
    val yes: String,
    val no: String,
    val ok: String,
    val error: String,
    val loading: String,
    val currency: String,
)

// ============================================================
// French Strings (Default)
// ============================================================
val FrenchStrings = KoloStrings(
    appName = "Kolo",
    slogan = "Épargner • Planifier • Réaliser",

    onboardingTitle1 = "Bienvenue sur Kolo",
    onboardingDesc1 = "Votre compagnon intelligent pour planifier vos projets et atteindre vos objectifs financiers.",
    onboardingTitle2 = "Planifiez vos projets",
    onboardingDesc2 = "Créez des projets, ajoutez vos dépenses prévues et suivez votre épargne en temps réel.",
    onboardingTitle3 = "Atteignez vos objectifs",
    onboardingDesc3 = "Visualisez votre progression et recevez des prévisions intelligentes pour rester sur la bonne voie.",
    onboardingGetStarted = "Commencer",
    onboardingNext = "Suivant",
    onboardingSkip = "Passer",

    navHome = "Accueil",
    navSettings = "Paramètres",

    homeTitle = "Mes Projets",
    homeEmptyTitle = "Aucun projet",
    homeEmptySubtitle = "Commencez par créer votre premier projet d'épargne",
    homeCreateFirst = "Créer un projet",
    homeProjects = "projets",
    homeProgress = "Progression",
    homeRemaining = "Restant",
    homeTimeLeft = "Temps restant",
    homeSaved = "Économisé",
    homeTarget = "Objectif",

    createProject = "Nouveau projet",
    editProject = "Modifier le projet",
    projectTitle = "Nom du projet",
    projectDescription = "Description (optionnelle)",
    projectTargetDate = "Date cible",
    projectTitleRequired = "Le nom du projet est requis",
    projectDateRequired = "La date cible est requise",
    projectSave = "Enregistrer",
    projectDelete = "Supprimer le projet",
    projectDeleteConfirm = "Êtes-vous sûr de vouloir supprimer ce projet ? Cette action est irréversible.",
    projectDetails = "Détails du projet",

    addItem = "Ajouter un élément",
    editItem = "Modifier l'élément",
    itemTitle = "Nom de l'élément",
    itemAmount = "Montant",
    itemRequiresSaving = "Nécessite une épargne",
    itemTitleRequired = "Le nom de l'élément est requis",
    itemsList = "Éléments du projet",
    itemsEmpty = "Aucun élément ajouté",
    itemsTotal = "Coût total",

    addSaving = "Ajouter une économie",
    savingAmount = "Montant",
    savingDescription = "Description (optionnelle)",
    savingAmountRequired = "Le montant est requis",
    savingsHistory = "Historique des économies",
    savingsHistoryEmpty = "Aucune économie enregistrée pour ce projet",
    savingsEmpty = "Aucune économie enregistrée",
    savingsTotal = "Total économisé",

    statsTitle = "Statistiques",
    statsTargetAmount = "Montant objectif",
    statsSavedAmount = "Montant économisé",
    statsRemainingAmount = "Montant restant",
    statsProgress = "Progression",
    statsTimeRemaining = "Temps restant",
    statsDaysRemaining = "jours restants",
    statsMonthsRemaining = "mois restants",
    statsDailyTarget = "Économiser par jour",
    statsWeeklyTarget = "Économiser par semaine",
    statsMonthlyTarget = "Économiser par mois",
    statsSmartForecast = "Prévisions intelligentes",
    statsCompleted = "Objectif atteint !",
    statsOverdue = "Date dépassée",

    days = "jours",
    months = "mois",
    timeRemainingFormat = "%d mois %d jours restants",

    settingsTitle = "Paramètres",
    settingsLanguage = "Langue",
    settingsFrench = "Français",
    settingsEnglish = "Anglais",
    settingsBackup = "Sauvegarde",
    settingsRestore = "Restauration",
    settingsExportData = "Exporter les données",
    settingsImportData = "Importer les données",
    settingsExportSuccess = "Données exportées avec succès",
    settingsImportSuccess = "Données importées avec succès",
    settingsImportError = "Erreur lors de l'import des données",
    settingsAbout = "À propos",
    settingsVersion = "Version",

    save = "Enregistrer",
    cancel = "Annuler",
    delete = "Supprimer",
    confirm = "Confirmer",
    yes = "Oui",
    no = "Non",
    ok = "OK",
    error = "Erreur",
    loading = "Chargement...",
    currency = "FCFA",
)

// ============================================================
// English Strings
// ============================================================
val EnglishStrings = KoloStrings(
    appName = "Kolo",
    slogan = "Save • Plan • Achieve",

    onboardingTitle1 = "Welcome to Kolo",
    onboardingDesc1 = "Your smart companion to plan your projects and reach your financial goals.",
    onboardingTitle2 = "Plan your projects",
    onboardingDesc2 = "Create projects, add your expected expenses, and track your savings in real time.",
    onboardingTitle3 = "Reach your goals",
    onboardingDesc3 = "Visualize your progress and get smart forecasts to stay on track.",
    onboardingGetStarted = "Get Started",
    onboardingNext = "Next",
    onboardingSkip = "Skip",

    navHome = "Home",
    navSettings = "Settings",

    homeTitle = "My Projects",
    homeEmptyTitle = "No projects",
    homeEmptySubtitle = "Start by creating your first savings project",
    homeCreateFirst = "Create a project",
    homeProjects = "projects",
    homeProgress = "Progress",
    homeRemaining = "Remaining",
    homeTimeLeft = "Time left",
    homeSaved = "Saved",
    homeTarget = "Target",

    createProject = "New project",
    editProject = "Edit project",
    projectTitle = "Project name",
    projectDescription = "Description (optional)",
    projectTargetDate = "Target date",
    projectTitleRequired = "Project name is required",
    projectDateRequired = "Target date is required",
    projectSave = "Save",
    projectDelete = "Delete project",
    projectDeleteConfirm = "Are you sure you want to delete this project? This action is irreversible.",
    projectDetails = "Project details",

    addItem = "Add an item",
    editItem = "Edit item",
    itemTitle = "Item name",
    itemAmount = "Amount",
    itemRequiresSaving = "Requires saving",
    itemTitleRequired = "Item name is required",
    itemsList = "Project items",
    itemsEmpty = "No items added",
    itemsTotal = "Total cost",

    addSaving = "Add a saving",
    savingAmount = "Amount",
    savingDescription = "Description (optional)",
    savingAmountRequired = "Amount is required",
    savingsHistory = "Savings history",
    savingsHistoryEmpty = "No savings recorded for this project",
    savingsEmpty = "No savings recorded",
    savingsTotal = "Total saved",

    statsTitle = "Statistics",
    statsTargetAmount = "Target amount",
    statsSavedAmount = "Amount saved",
    statsRemainingAmount = "Remaining amount",
    statsProgress = "Progress",
    statsTimeRemaining = "Time remaining",
    statsDaysRemaining = "days remaining",
    statsMonthsRemaining = "months remaining",
    statsDailyTarget = "Save per day",
    statsWeeklyTarget = "Save per week",
    statsMonthlyTarget = "Save per month",
    statsSmartForecast = "Smart forecast",
    statsCompleted = "Goal reached!",
    statsOverdue = "Overdue",

    days = "days",
    months = "months",
    timeRemainingFormat = "%d months %d days remaining",

    settingsTitle = "Settings",
    settingsLanguage = "Language",
    settingsFrench = "French",
    settingsEnglish = "English",
    settingsBackup = "Backup",
    settingsRestore = "Restore",
    settingsExportData = "Export data",
    settingsImportData = "Import data",
    settingsExportSuccess = "Data exported successfully",
    settingsImportSuccess = "Data imported successfully",
    settingsImportError = "Error importing data",
    settingsAbout = "About",
    settingsVersion = "Version",

    save = "Save",
    cancel = "Cancel",
    delete = "Delete",
    confirm = "Confirm",
    yes = "Yes",
    no = "No",
    ok = "OK",
    error = "Error",
    loading = "Loading...",
    currency = "FCFA",
)

/**
 * Returns the appropriate string set based on language code.
 */
fun getStringsForLanguage(languageCode: String): KoloStrings {
    return when (languageCode) {
        "en" -> EnglishStrings
        else -> FrenchStrings
    }
}
