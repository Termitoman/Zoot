package zoot.gestionErreurs;

/**
 * Classe Erreur représentant une erreur par son message, le numéro de la ligne et du caractère où se trouve l'erreur.
 */
public class Erreur {
    private String message;
    private int numLigne;
    private int numCarac;

    /**
     * Constucteur de la classe Erreur.
     *
     * @param message  le message de l'erreur
     * @param numLigne le numéro de ligne associé à l'erreur
     * @param numCarac le numéro de caractère associé à l'erreur
     */
    public Erreur(String message, int numLigne, int numCarac) {
        this.message = message;
        this.numLigne = numLigne;
        this.numCarac = numCarac;
    }

    /**
     * Constructeur de la classe Erreur.
     *
     * @param message  le numéro de ligne associé à l'erreur
     * @param numLigne le numéro de caractère associé à l'erreur
     */
    public Erreur(String message, int numLigne) {
        this.message = message;
        this.numLigne = numLigne;
        this.numCarac = 0;
    }

    /**
     * Fonction qui retourne le message de l'erreur.
     *
     * @return le message de l'erreur
     */
    public String getMessage() {
        return message;
    }

    /**
     * Procédure qui définit le message de l'erreur.
     *
     * @param message le message de l'erreur
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Fonction qui retourne le numéro de la ligne associé à l'erreur.
     *
     * @return le numéro de ligne
     */
    public int getNumLigne() {
        return numLigne;
    }

    /**
     * Procédure qui définit le numéro de la ligne associé à l'erreur.
     *
     * @param numLigne le numéro de ligne
     */
    public void setNumLigne(int numLigne) {
        this.numLigne = numLigne;
    }

    /**
     * Fonction qui retourne le numéro du caractère associé à l'erreur.
     *
     * @return le numéro du caractère
     */
    public int getNumCarac() {
        return numCarac;
    }

    /**
     * Procédure qui définit le numéro du caractère associé à l'erreur.
     *
     * @param numCarac le numéro du caractère
     */
    public void setNumCarac(int numCarac) {
        this.numCarac = numCarac;
    }
}
