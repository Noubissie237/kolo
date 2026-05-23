<!-- Ajout de l'image -->
<img src="./app/src/main/res/drawable/logo_kolo.png" alt="kolo logo" width="200"/>

# Kolo - Smart Savings Planner

**Kolo** est une application Android moderne de gestion de projets d'épargne qui vous aide à planifier, suivre et atteindre vos objectifs financiers.


## Fonctionnalités

### Gestion de Projets
- **Créer des projets d'épargne** avec titre, description et date cible
- **Modifier des projets** existants à tout moment
- **Supprimer des projets** avec confirmation
- **Visualiser la progression** en temps réel avec des indicateurs visuels

### Gestion des Éléments
- **Ajouter des éléments** à vos projets (articles à acheter)
- **Modifier des éléments** existants
- **Marquer les éléments** nécessitant une épargne avec montant
- **Supprimer des éléments** avec confirmation

### Suivi des Épargnes
- **Enregistrer vos épargnes** avec montant et description optionnelle
- **Historique complet** de toutes vos épargnes
- **Statistiques détaillées** : montant cible, montant épargné, reste à épargner
- **Prévisions intelligentes** : objectifs quotidiens, hebdomadaires et mensuels

### Paramètres et Personnalisation
- **Multilingue** : Français et English
- **Sauvegarde et restauration** : Export/Import des données en JSON
- **Contact** : Liens vers le site web et email du développeur

### Interface Utilisateur
- **Material Design 3** avec thème moderne
- **Animations fluides** pour une expérience premium
- **Speed Dial FAB** avec labels pour actions rapides
- **Mode sombre/clair** automatique selon les préférences système

## Architecture

### Technologies Utilisées
- **Kotlin** - Langage de programmation
- **Jetpack Compose** - UI moderne et déclarative
- **Room Database** - Persistance locale des données
- **Coroutines & Flow** - Programmation asynchrone réactive
- **MVVM Architecture** - Séparation des responsabilités
- **Material Design 3** - Design system moderne

### Structure du Projet
```
kolo/
├── app/
│   ├── src/main/java/com/propentatech/kolo/
│   │   ├── data/
│   │   │   ├── backup/          # Gestion export/import
│   │   │   ├── local/
│   │   │   │   ├── dao/         # Data Access Objects
│   │   │   │   ├── database/    # Configuration Room
│   │   │   │   └── entities/    # Entités de base de données
│   │   │   ├── preferences/     # DataStore pour préférences
│   │   │   └── repository/      # Couche d'accès aux données
│   │   ├── domain/              # Logique métier et utilitaires
│   │   ├── ui/
│   │   │   ├── components/      # Composants réutilisables
│   │   │   ├── localization/    # Système i18n
│   │   │   ├── navigation/      # Navigation Compose
│   │   │   ├── screens/         # Écrans de l'application
│   │   │   └── theme/           # Thème Material Design 3
│   │   ├── viewmodel/           # ViewModels MVVM
│   │   ├── KoloApplication.kt   # Application principale
│   │   └── MainActivity.kt      # Activité principale
│   └── src/main/res/            # Ressources (drawables, etc.)
└── README.md
```

## Base de Données

### Entités
- **ProjectEntity** : Projets d'épargne
- **ProjectItemEntity** : Éléments/articles d'un projet
- **SavingEntryEntity** : Entrées d'épargne

### Relations
```
Project (1) ─> (N) ProjectItem
Project (1) ─> (N) SavingEntry
```

## Internationalisation

L'application supporte actuellement :
- 🇫🇷 **Français** (par défaut)
- 🇬🇧 **English**

Le système i18n est extensible et permet d'ajouter facilement de nouvelles langues.

## Installation

### Prérequis
- Android Studio Hedgehog (2023.1.1) ou supérieur
- JDK 17 ou supérieur
- Android SDK 34
- Gradle 8.0+

### Étapes
1. Cloner le repository
```bash
git clone https://github.com/Noubissie237/kolo.git
cd kolo
```

2. Ouvrir le projet dans Android Studio

3. Synchroniser Gradle
```bash
./gradlew build
```

4. Lancer l'application
- Connecter un appareil Android ou démarrer un émulateur
- Cliquer sur "Run" dans Android Studio

## Build

### Debug Build
```bash
./gradlew assembleDebug
```

### Release Build
```bash
./gradlew assembleRelease
```

## Utilisation

### Premier lancement
1. L'application affiche un **onboarding** explicatif
2. Créez votre premier projet d'épargne
3. Ajoutez des éléments à votre projet
4. Enregistrez vos épargnes au fur et à mesure

### Workflow typique
```
Créer un projet → Ajouter des éléments → Épargner → Suivre la progression
```

### Fonctionnalités avancées
- **Speed Dial FAB** : Accès rapide aux actions (Ajouter élément/épargne)
- **Modales de confirmation** : Sécurité avant suppression
- **Édition en place** : Modifier projets et éléments facilement
- **Export/Import** : Sauvegardez vos données en JSON

## Sauvegarde des Données

### Export
1. Aller dans **Settings** (⚙️)
2. Section "Sauvegarde et restauration"
3. Cliquer sur "Exporter les données"
4. Choisir l'emplacement de sauvegarde

### Import
1. Aller dans **Settings** (⚙️)
2. Section "Sauvegarde et restauration"
3. Cliquer sur "Importer les données"
4. Sélectionner le fichier JSON de sauvegarde

## Captures d'écran

### Écrans principaux
- **Onboarding** : Introduction à l'application
- **Home** : Liste des projets avec progression
- **Project Details** : Détails d'un projet avec éléments et épargnes
- **Add/Edit** : Formulaires d'ajout/modification
- **Settings** : Paramètres et contact

## Contribution

Les contributions sont les bienvenues ! Pour contribuer :

1. Fork le projet
2. Créer une branche (`git checkout -b feature/AmazingFeature`)
3. Commit les changements (`git commit -m 'Add AmazingFeature'`)
4. Push vers la branche (`git push origin feature/AmazingFeature`)
5. Ouvrir une Pull Request

## Licence

Ce projet est sous licence MIT. Voir le fichier `LICENSE` pour plus de détails.

## Auteur

**Noubissie Kamga Wilfried**
-  Site web : [noubissie.propentatech.com](https://noubissie.propentatech.com)
-  Email : noubissie.k.w@gmail.com
-  Organisation : PropentaTech



## Statistiques du Projet

- **Langage** : Kotlin 100%
- **Architecture** : MVVM + Repository Pattern
- **UI Framework** : Jetpack Compose
- **Base de données** : Room
- **Min SDK** : 24 (Android 7.0)
- **Target SDK** : 34 (Android 14)

## Roadmap

### Version 1.1 (À venir)
- [ ] Notifications pour rappels d'épargne
- [ ] Graphiques de progression
- [ ] Catégories de projets
- [ ] Mode multi-utilisateurs

### Version 1.2 (Futur)
- [ ] Synchronisation cloud
- [ ] Partage de projets
- [ ] Thèmes personnalisables
- [ ] Widget pour écran d'accueil

---
