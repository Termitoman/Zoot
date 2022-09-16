package zoot.gestionErreurs;

import java.util.ArrayList;

/**
 * Singleton StockageEtterrus représentant le stockage des erreurs qui apparaissent lors de la vérification du code.
 */
public class StockageErreurs {
    private final ArrayList<Erreur> listeErreurs;

    /**
     * Constructeur privé du singleton StockageErreurs.
     */
    private StockageErreurs() {
        this.listeErreurs = new ArrayList<>();
    }

    private static final StockageErreurs instance = new StockageErreurs();

    /**
     * Fonction qui retourne une instance de la classe StockageErreurs.
     *
     * @return une instance de la classe TDS.
     */
    public static StockageErreurs getInstance() {
        return instance;
    }

    /**
     * Procédure qui ajoute une erreur à la liste des erreurs.
     *
     * @param erreur l'erreur à ajouter
     */
    public void ajouter(Erreur erreur) {
        this.listeErreurs.add(erreur);
    }

    /**
     * Fonction qui retourne l'erreur à l'indice donné ou null si elle n'existe pas.
     *
     * @param i l'indice de l'erreur à retourner
     * @return l'erreur ou null
     */
    public Erreur getErreurI(int i) {
        if (i >= listeErreurs.size() || i < 0)
            return null;
        return this.listeErreurs.get(i);
    }

    /**
     * Fonction qui retourne le nombre d'erreur.
     *
     * @return le nombre d'erreur.
     */
    public int getNbErreurs() {
        return listeErreurs.size();
    }

    /**
     * Fonction qui retourne la liste des erreurs.
     *
     * @return la liste des erreurs.
     */
    public ArrayList<Erreur> getListeErreurs() {
        return listeErreurs;
    }
}
