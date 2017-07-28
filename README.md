# Email Sentiment Analysis

Analyseur sentimental d'emails en Java et en R. Il s'appuie sur des analyseurs de fond (5 APIs d'analyse sentimale grands publics : Indico, Microsoft Text Analytics, Aylien, Meaning Cloud, Repustate) et des analyseurs de forme (émoticônes, texte en majuscule, en gras, en couleur) afin d'obtenir un score sentimental (entre 0 et 1) : négatif, neutre ou positif.
- Développement d'une interface web en Java avec Spring Boot
- Constitution d'un jeu de test, data mining et data analysis en R
- Développement d'un algorithme de régression non-linéaire à plusieurs variables en R

Réalisé durant le mois de juillet 2017 dans le cadre d'un stage chez SQLI Toulouse.

# Lancer le service web

1. Importer le projet comme projet Maven
2. Importer \src\main\resources\static\cordialement.sql dans votre base de données MySQL
3. Modifier \src\main\resources\application.properties pour la connexion à votre base de données
4. Modifier \src\main\java\fr\sqli\cordialement\services\impl\ScoreServiceImpl.java pour y mettre vos clés APIs

# Utiliser l'algorithme en R

1. Ouvrir \src\main\resources\static\cordialement-regression.R 
2. Changer le répertoire courant sous RStudio "To Source File Location"
3. Exécuter le script au minimum jusqu'à la ligne 111
4. Afficher "best$par"
5. Modifier \src\main\java\fr\sqli\cordialement\services\impl\ScoreServiceImpl.java pour y changer les valeurs de "param" dans la méthode "computeEmailScore"
