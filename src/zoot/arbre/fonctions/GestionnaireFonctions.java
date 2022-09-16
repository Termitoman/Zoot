package zoot.arbre.fonctions;

import java.util.ArrayList;

/**
 * Singleton GestionnaireFonctions.
 */
public class GestionnaireFonctions {
    private final ArrayList<Fonction> fonctions;
    private boolean fonctionsSontTraitees; //Booléen qui sert à différencier un bloc d'instructions de fonctions du bloc d'instruction du programme.

    /**
     * Constructeur privé du singleton GestionnaireFonctions.
     */
    private GestionnaireFonctions() {
        this.fonctions = new ArrayList<>();
        this.fonctionsSontTraitees = false;
    }

    private static final GestionnaireFonctions instance = new GestionnaireFonctions();

    /**
     * Fonction qui retourne une instance de la classe GestionnaireFonctions.
     *
     * @return une instance de la classe GestionnaireFonctions.
     */
    public static GestionnaireFonctions getInstance() {
        return instance;
    }

    /**
     * Fonction qui retourne le booléen isFonctionsSontTraitees.
     *
     * @return le booléen isFonctionsSontTraitees.
     */
    public boolean isFonctionsSontTraitees() {
        return fonctionsSontTraitees;
    }

    /**
     * Procédure qui définit le booléen isFonctionsSontTraitees.
     *
     * @param fonctionsSontTraitees le booléen a set.
     */
    public void setFonctionsSontTraitees(boolean fonctionsSontTraitees) {
        this.fonctionsSontTraitees = fonctionsSontTraitees;
    }

    /**
     * Fonction qui ajooute une fonction à l'ArrayList de fonctions.
     *
     * @param fonc la fonction a ajouter.
     */
    public void ajouter(Fonction fonc) {
        this.fonctions.add(fonc);
    }

    /**
     * Fonction qui retourne le code mips d'une fonction.
     *
     * @return le code mips d'une fonction.
     */
    public String toMipsFonctions() {
        StringBuilder sb = new StringBuilder();
        for (Fonction f : this.fonctions) {
            sb.append(f.toMIPS());
        }
        return sb.toString();
    }

    /**
     * Procédure qui vérifie les fonctions.
     */
    public void verifier() {
        for (Fonction f : this.fonctions) {
            f.verifier();
        }
    }

    /**
     * Fonction qui retourne les fonctions du gestionnaire.
     *
     * @return les fonctions du gestionnaire.
     */
    public ArrayList<Fonction> getFonctions() {
        return fonctions;
    }
}
