# KOLO — Application Mobile de Planification d’Épargne et d’Objectifs

## Contexte du projet

Kolo est une application mobile Android moderne permettant aux utilisateurs de planifier leurs projets futurs nécessitant une préparation financière.

L’objectif principal est d’aider les utilisateurs à :
- définir des projets personnels,
- estimer leurs dépenses futures,
- suivre leurs économies,
- visualiser leur progression,
- et atteindre leurs objectifs financiers de manière organisée.

L’application est pensée pour fonctionner entièrement hors ligne dans un premier temps, sans backend externe.

---

# Vision du produit

Kolo n’est PAS une simple application de gestion de dépenses.

C’est une application de :
- planification de projets personnels,
- épargne intelligente,
- suivi d’objectifs,
- motivation financière.

L’application doit être :
- moderne,
- minimaliste,
- élégante,
- intuitive,
- fluide,
- émotionnelle,
- inspirante.

Le design doit être premium, moderne, avec une identité africaine discrète et élégante.

---

# Stack technique

## Technologies obligatoires

- Kotlin
- Jetpack Compose
- Material Design 3
- Room Database (SQLite local)
- Navigation Compose
- MVVM Architecture
- StateFlow / MutableStateFlow
- Repository Pattern

---

# Contraintes importantes

- Aucun backend externe
- Application 100% offline
- Architecture propre et scalable
- Code bien structuré
- UI moderne et responsive
- Dark theme obligatoire
- Préparer le projet pour une future évolution (sync cloud plus tard)

---

# Gestion de la langue (Internationalisation)

L’application doit supporter plusieurs langues :

- Français
- Anglais

L’utilisateur doit pouvoir changer la langue depuis la page des paramètres.

## Contraintes importantes

- Toutes les chaînes internes de l’application doivent être traduisibles.
- Utiliser un système propre d’internationalisation.
- Les textes UI doivent automatiquement changer selon la langue choisie.

Exemples :
- boutons
- titres
- labels
- messages
- erreurs
- onboarding
- paramètres
- statistiques
- navigation

---

## IMPORTANT

Les données provenant de la base de données NE DOIVENT PAS être traduites.

Exemples :
- nom des projets
- descriptions
- éléments ajoutés par l’utilisateur
- descriptions des économies

Ces données doivent toujours être affichées EXACTEMENT comme l’utilisateur les a saisies.

---

## Persistance de la langue

Le choix de langue doit être sauvegardé localement.

Exemple :
- si l’utilisateur choisit l’anglais,
- puis ferme l’application,
- au prochain lancement l’application doit rester en anglais.

Le système doit utiliser DataStore ou SharedPreferences pour persister ce choix.

---

# Gestion du premier lancement (Onboarding)

L’écran de onboarding doit apparaître UNIQUEMENT lors du premier lancement de l’application.

Comportement attendu :

- Premier lancement → afficher onboarding
- Lancements suivants → aller directement vers l’écran principal

Le système doit mémoriser localement que l’onboarding a déjà été vu.

Utiliser DataStore ou SharedPreferences pour cette persistance.

---

# Sauvegarde et restauration des données

L’application doit permettre :

- d’exporter toutes les données utilisateur,
- puis de les restaurer plus tard.

Objectif :
- migration vers un nouveau téléphone,
- sauvegarde locale,
- récupération des données.

---

## Export des données

Le système doit pouvoir exporter :
- projets,
- éléments des projets,
- historiques des économies,
- paramètres utilisateur.

Le format d’export doit être :

JSON

Exemple :
kolo_backup.json

---

## Import des données

L’utilisateur doit pouvoir :
- sélectionner un fichier JSON,
- restaurer automatiquement les données dans l’application.

Le système doit :
- vérifier la validité du fichier,
- gérer les erreurs proprement,
- éviter les crashs.

---

## Architecture attendue

Créer :
- un service d’export,
- un service d’import,
- sérialisation JSON propre,
- structure évolutive/versionnable.

Utiliser :
- Kotlin Serialization ou Gson.

---

# Fonctionnalités principales

# 1. Gestion des projets

L’utilisateur peut créer un projet personnel.

Exemples :
- Voyage vacances
- Acheter un ordinateur
- Construire une maison
- Mariage
- Formation
- Nouvelle voiture

Chaque projet contient :

## Champs

- id
- title
- description (optionnelle)
- targetDate
- createdAt
- updatedAt

---

# 2. Gestion des éléments du projet

Chaque projet contient plusieurs éléments à prévoir.

Exemples :
- Billet d’avion → 500000 FCFA (requiresSaving = true)
- Nourriture → 200000 FCFA (requiresSaving = true)
- Lunettes de soleil → (requiresSaving = false)
- Sac à dos → (requiresSaving = false)

Chaque élément contient :

## Champs

- id
- projectId
- title
- amount
- requiresSaving (Boolean)

Si requiresSaving = false :
- l’élément n’entre pas dans le calcul financier.

---

# 3. Calcul automatique du coût total

Le système doit automatiquement calculer :
- le coût total du projet,
- uniquement à partir des éléments nécessitant une épargne.

Exemple :

Billet avion → 500000
Nourriture → 200000
Habits → 150000

Total = 850000 FCFA

---

# 4. Système d’épargne

L’utilisateur peut enregistrer des économies réalisées pour un projet.

Exemple :
+50000 FCFA
Description : argent économisé aujourd’hui

Chaque dépôt contient :

## Champs

- id
- projectId
- amount
- description (optionnelle)
- createdAt

---

# 5. Calcul de progression

Le système doit calculer automatiquement :

- montant total requis
- montant déjà économisé
- montant restant
- pourcentage de progression

Exemple :

Objectif : 850000 FCFA
Déjà économisé : 200000 FCFA
Reste : 650000 FCFA
Progression : 23%

---

# 6. Temps restant

L’application doit afficher :

- jours restants
- mois restants
- date cible

Exemple :
"2 mois 5 jours restants"

---

# 7. Prévisions intelligentes

Le système doit calculer :

## Exemple

- combien économiser par jour
- combien économiser par semaine
- combien économiser par mois

Exemple :

"Il faut économiser environ 10655 FCFA par jour"

---

# 8. Historique des économies

L’utilisateur peut voir :
- tous les dépôts,
- les dates,
- les descriptions,
- les montants.

---

# 9. Dashboard principal

L’écran principal doit afficher :
- liste des projets,
- progression,
- montant restant,
- temps restant,
- barre de progression.

---

# Architecture attendue

Utiliser une architecture MVVM propre.

## Structure recommandée

app/
│
├── data/
│   ├── local/
│   │   ├── dao/
│   │   ├── entities/
│   │   ├── database/
│   │
│   ├── repository/
│
├── domain/
│
├── ui/
│   ├── screens/
│   ├── components/
│   ├── navigation/
│   ├── theme/
│
├── viewmodel/

---

# Base de données Room

## Entities attendues

### ProjectEntity

- id
- title
- description
- targetDate
- createdAt

---

### ProjectItemEntity

- id
- projectId
- title
- amount
- requiresSaving

---

### SavingEntryEntity

- id
- projectId
- amount
- description
- createdAt

---

# Écrans à implémenter

# 1. Onboarding Screen

Objectif :
- présenter l’application,
- introduire le concept.

Style :
- moderne,
- premium,
- africain discret,
- dark mode,
- illustrations modernes.

---

# 2. Home Screen

Afficher :
- liste des projets,
- progression,
- temps restant,
- montant restant.

Ajouter :
- Floating Action Button pour créer un projet.

---

# 3. Create Project Screen

Permet :
- créer un projet,
- définir la date cible,
- ajouter description.

---

# 4. Project Details Screen

Afficher :
- informations du projet,
- éléments,
- total,
- progression,
- statistiques.

Ajouter :
- bouton pour ajouter un élément,
- bouton pour ajouter une économie.

---

# 5. Add Item Screen

Ajouter un élément :
- titre,
- montant,
- nécessite une épargne ou non.

---

# 6. Add Saving Screen

Ajouter :
- montant,
- description optionnelle,
- date automatique.

---

# 7. Savings History Screen

Afficher :
- historique des dépôts,
- tri par date.

---

# Design UI/UX attendu

Le design doit être :
- élégant,
- moderne,
- minimaliste,
- premium,
- fluide.

Inspirations :
- Notion
- Stripe
- Linear
- Revolut
- Monzo

---

# Couleurs principales

Palette recommandée :

- Vert profond
- Noir/anthracite
- Blanc cassé
- Vert lime léger

---

# Identité visuelle

Nom :
KOLO

Slogan :
"Épargner • Planifier • Réaliser"

---

# Assets

Les assets existent déjà :

- logo_kolo.png (sans background)
- logo_bg_kolo.png (avec background)

Ils sont situés dans :
res/drawable/

---

# Qualité de code attendue

Le code doit être :
- propre,
- professionnel,
- commenté,
- maintenable,
- scalable.

Éviter :
- code spaghetti,
- logique métier dans les composables,
- duplication.

---

# Objectif final

Produire une application Android moderne, élégante et fonctionnelle permettant de gérer des projets d’épargne personnels avec une excellente expérience utilisateur.