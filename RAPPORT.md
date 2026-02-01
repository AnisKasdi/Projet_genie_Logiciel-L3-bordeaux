# RAPPORT - Projet Génie Logiciel

**Auteurs**: [Votre Nom], [Anis Kasdi]
**Date**: 01/02/2026

## Introduction
Ce document détaille les choix de conception, l'architecture et l'implémentation du module de gestion d'images réalisé dans le cadre du TP Génie Logiciel.

## Étape 1 : Mise en place
Nous avons initialisé une application Spring Boot 3.5.9 avec Java 21.
- **Choix techniques**: Utilisation de Maven pour la gestion de dépendances et de Spring Web pour l'API REST.
- **Configuration**: Le port serveur a été changé à `8181` dans `application.yaml` pour éviter les conflits standard.

## Étape 2 : Conception et déploiement (Mode Volatile)
L'objectif de cette étape est d'implémenter une gestion d'images en mémoire (HashMap) sans base de données, en respectant le pattern DAO.

### Architecture
- **DAO (`ImageDao`)**: Isole la logique de stockage. Pour cette étape, il utilise une `HashMap<Long, Image>`.
    - *Pourquoi ?* Permet de prototyper rapidement l'API sans gérer la complexité SQL.
- **Modèle (`Image`)**: Classe POJO représentant une image (id, nom, données binaires).
- **Contrôleur (`ImageController`)**: Expose l'API REST. Il injecte le DAO pour manipuler les données.

### Implémentation
- **Stockage**: Au démarrage, le `ImageDao` charge automatiquement une image `test.jpg` présente dans les ressources pour faciliter les tests.
- **API**:
    - `GET /images/{id}`: Retourne le flux binaire de l'image avec le bon Content-Type (`image/jpeg`).
    - `POST /images`: Accepte un fichier Multipart, l'enregistre en mémoire et retourne son ID.
    - `DELETE /images/{id}`: Supprime l'image du Map.
    - `GET /images`: Retourne une liste légère (ID + Nom) en JSON.

### Détails de l'implémentation DAO
Nous avons complété la classe `ImageDao` pour gérer le cycle de vie des images via une `HashMap` :
- `retrieve(id)`: Utilise `Optional.ofNullable()` pour gérer proprement les cas où l'ID n'existe pas, évitant les `NullPointerException`.
- `retrieveAll()`: Retourne une copie de la liste des valeurs (`new ArrayList<>(images.values())`) pour plus d'efficacité et de simplicité par rapport à une itération manuelle.
- `create(img)`: Utilise l'ID auto-généré par le constructeur de `Image` pour insérer l'objet dans la map (`put`).
- `delete(img)`: Supprime l'entrée directement via son ID (`remove`), profitant de la complexité O(1) de la HashMap.

### Tests
Nous utilisons `Mockito` pour isoler le Contrôleur du DAO lors des tests unitaires (`ImageControllerTests`). Cela garantit que nous testons uniquement la logique HTTP du contrôleur et non le stockage lui-même.

---
*À compléter au fur et à mesure de l'avancement (Étape 3...)*
