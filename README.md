📌 README.md — Application Java AHP (Méthode d'Aide à la Décision) + Interface Graphique
# 🧠 Application Java — Implémentation de la Méthode AHP avec Interface Graphique

Une application Java permettant d’appliquer la méthode **AHP (Analytic Hierarchy Process)** pour aider à la prise de décision multicritère.  
Elle intègre une **interface graphique (GUI)** conviviale pour saisir les critères, alternatives, matrices de comparaison, et afficher les résultats.

---

## 🎯 Objectif

L’objectif de ce projet est d’offrir une solution simple pour :

- Définir une hiérarchie de décision (Objectif → Critères → Alternatives)
- Saisir des matrices de comparaison par paires (Selon l’échelle de Saaty 1–9)
- Calculer :
  - Les poids (priorités) des critères
  - Les priorités des alternatives
  - L’indice de cohérence (CI) et le ratio de cohérence (CR)
- Déterminer l’alternative optimale

---

## 🚀 Fonctionnalités principales

- Interface graphique Java (Swing ou JavaFX, selon implémentation)
- Création dynamique :
  - Des critères
  - Des alternatives
  - Des matrices de comparaison
- Calcul automatique :
  - Matrices normalisées
  - Vecteurs propres
  - Poids globaux
  - Coefficients AHP
- Vérification de la cohérence (CR ≤ 0.1)
- Rapport final des décisions
- Export des résultats (optionnel, si implémenté)

---

## 🧱 Architecture du projet



📁 JAVA-AHP-APP
│
├── 📂 gui
│ ├── MainWindow.java
│ ├── CriteriaMatrixGUI.java
│ ├── AlternativesMatrixGUI.java
│ └── ResultsView.java
│
├── 📂 ahp
│ ├── AHPModel.java
│ ├── MatrixUtils.java
│ ├── ConsistencyChecker.java
│ └── ...
│
├── 📂 data
│ └── (optionnel) Fichiers ou modèles pour sauvegarde
│
└── README.md


---

## 🧮 Méthode AHP implémentée

### 1️⃣ Définition de l’objectif  
L’utilisateur spécifie le but de la décision.

### 2️⃣ Saisie des critères  
Ajout/modification/suppression des critères.

### 3️⃣ Comparaison par paires  
Utilisation de l’échelle fondamentale de Saaty :

| Importance | Valeur |
|-----------|--------|
| Égal      | 1      |
| Légère    | 3      |
| Forte     | 5      |
| Très forte| 7      |
| Extrême   | 9      |
| Valeurs intermédiaires | 2, 4, 6, 8 |

### 4️⃣ Calcul  
Le programme calcule :

- Matrice normalisée
- Poids des critères (vecteur propre)
- Matrices d’alternatives
- Poids globaux
- Classement final

### 5️⃣ Vérification de cohérence  
Affichage de :

- CI (Consistency Index)
- CR (Consistency Ratio)

Si **CR > 0.1**, un avertissement est affiché.

---

## 🛠 Technologies utilisées

- **Java 8+**
- **Swing ou JavaFX** (interface graphique)
- **Modèle MVC** (recommandé)
- **Algèbre linéaire simple (normalisation, produits matriciels)**

---

## ▶️ Comment exécuter l'application

### 1️⃣ Compilation
```bash
javac */*.java

2️⃣ Exécution
java gui.MainWindow


Une fenêtre graphique s’ouvrira automatiquement.

📡 Fonctionnement de l’interface

Accueil : ajout des critères & alternatives

Matrices de comparaison : table dynamique générée en fonction des éléments saisis

Calcul : un bouton déclenche les calculs AHP

Résultats :

Poids des critères

Poids des alternatives

Alternative recommandée

Vérification cohérence (CR)

📌 Améliorations futures (suggestions)

Export PDF ou Excel du rapport

Enregistrement/sauvegarde de projets AHP

Support pour niveaux multiples dans la hiérarchie

Visualisation graphique des poids

Implémenter AHP flou (Fuzzy AHP)

👨‍💻 Auteur

Projet réalisé par Mohammed Ryad Benyakoub
📧 Contact : (ajoute ton email si tu veux)

📜 Licence

Projet libre pour apprentissage, amélioration et réutilisation.
