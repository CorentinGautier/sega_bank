SEGA BANK

Informations sur l'application SEGA BANK

- Base de données : celle-ci est en MySQL, une fois le script exécuté dans votre SGBD préféré, il vous faudra modifier le fichier db.properties         pour concorder avec le BDD_NAME, le LOGIN et le PWD de votre base.

- Programme : 
    bo (Buisness Object) comprend l'ensemble des classes nécessaire à l'application.
    
    dal (Data Access Layer) comprend l'ensemble des classes implémentant l'interface IDAO permettant la gestion de la connexion et des requêtes versla base de données

    App comprend le main permettant de lancé l'application

Certaines méthodes ne sont spécialement utiles pour cette première version console de l'application. Par exemple, versement() pourrait, dans l'état actuel de l'application ne rien retourner et donc être void. Cependant, dans le cas où un plafond serait imposé par la suite le type boolean de cette méthode pourrait s'avérer utile. (COMPLETER LES EXEMPLES)

L'Application

Celle-ci est séparé en deux menus distincts, le premier gérant la connexion et le deuxième la gestion d'un compte en particulier.
Pour ce faire voici 3 comptes des 3 types différents (Simple, Epargne et Payant) pour tester les différentes propriétés appliquées :
 Compte Simple, entrer l'id 1
 Compte Epargne, entrer l'id *
 Compte Payant, entrer l'id *

Vous pourrez ensuite tester les différentes opérations ainsi que les différentes fonctionnalités applicables sur les comptes.
