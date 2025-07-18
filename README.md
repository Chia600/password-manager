Gestionnaire de Mots de Passe
Description
Le Gestionnaire de Mots de Passe est une application Java sécurisée permettant de stocker et gérer des mots de passe chiffrés. Les mots de passe sont générés aléatoirement, chiffrés avec une clé AES, et stockés dans une base de données PostgreSQL. L'application offre une interface en ligne de commande (CLI) pour ajouter et afficher des mots de passe.
Fonctionnalités

Génération de mots de passe aléatoires sécurisés.
Chiffrement AES des mots de passe avec une clé maître.
Stockage des mots de passe dans une base PostgreSQL.
Interface CLI pour ajouter et afficher les mots de passe.

Structure du projet

Langage : Java 21

Dépendances : Maven, PostgreSQL, Gson

Conteneurisation : Docker et Docker Compose

Structure des dossiers :
password-manager/
├── src/
│   ├── main/
│   │   ├── java/com/monprojet/passwordmanager/
│   │   │   ├── Main.java
│   │   │   ├── model/PasswordEntry.java
│   │   │   ├── dao/PasswordDAO.java
│   │   │   ├── dao/PostgresPasswordDAO.java
│   │   │   ├── security/EncryptionService.java
│   │   │   ├── security/PasswordGenerator.java
│   │   │   ├── view/CLIView.java
│   ├── resources/
├── docker/
│   ├── master_key.txt
├── Dockerfile
├── docker-compose.yml
├── pom.xml



Prérequis

Docker : Installez Docker et Docker Compose sur votre machine (Docker Installation).
Java 21 : Requis uniquement si vous compilez localement (inclus dans l'image Docker).
Maven : Requis uniquement si vous compilez localement (inclus dans l'image Docker).
Un fichier docker/master_key.txt contenant une clé AES de 32 octets (par exemple, my32bytekey1234567890123456789012).

Installation et configuration

Cloner le projet :
git clone <repository-url>
cd password-manager


Créer la clé maître :

Créez un fichier docker/master_key.txt avec une clé de 32 octets :echo -n "my32bytekey1234567890123456789012" > docker/master_key.txt


Assurez-vous qu'il n'y a pas de saut de ligne (vérifiez avec cat docker/master_key.txt | wc -c, résultat attendu : 32).


Vérifier la structure :

Assurez-vous que tous les fichiers Java sont dans src/main/java/com/monprojet/passwordmanager/ et ses sous-dossiers.
Vérifiez que pom.xml, Dockerfile, et docker-compose.yml sont présents à la racine.



Exécution de l'application

Construire et lancer avec Docker Compose :
docker-compose up --build


Cela construit l'image Docker de l'application (gestionnaire_mdp-app) et lance le conteneur PostgreSQL (gestionnaire_mdp-db-1).
La CLI s'affichera dans les logs si tout est configuré correctement.


Interagir avec la CLI :

Si la CLI n'est pas interactive dans les logs, lancez le conteneur en mode interactif :docker-compose run --rm app


Vous verrez :
1. Ajouter un mot de passe
2. Afficher les mots de passe
3. Quitter
Choix :


Entrez 1 pour ajouter un mot de passe (suivez les invites pour le site et le login).
Entrez 2 pour afficher les mots de passe stockés.
Entrez 3 pour quitter.


Vérifier la base de données :

Vérifiez les mots de passe stockés dans PostgreSQL :docker exec -it gestionnaire_mdp-db-1 psql -U user -d password_manager -c "SELECT * FROM passwords;"


Arrêter l'application :
docker-compose down


Fonctionnement de la CLI

Option 1 : Ajouter un mot de passe :
Entrez le nom du site (par exemple, example.com).
Entrez le login (par exemple, user@example.com).
Un mot de passe aléatoire est généré, chiffré, et stocké dans la base de données.


Option 2 : Afficher les mots de passe :
Affiche tous les mots de passe stockés (déchiffrés à l’affichage).


Option 3 : Quitter :
Ferme l'application.



Dépendances

Java 21 : Pour la compilation et l'exécution.
PostgreSQL JDBC Driver (42.7.3) : Pour la connexion à la base de données.
Gson (2.11.0) : Pour la sérialisation JSON (non utilisé actuellement, mais inclus pour extensions futures).
Maven Shade Plugin : Pour créer un JAR exécutable avec toutes les dépendances.

Prochaines étapes

Ajouter des fonctionnalités comme la suppression ou la modification des mots de passe.
Implémenter une interface graphique avec JavaFX.
Étendre le projet avec un analyseur de trafic réseau (par exemple, avec Pcap4J).

