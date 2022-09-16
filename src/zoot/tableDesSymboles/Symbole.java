package zoot.tableDesSymboles;

/**
 * Classe Symbole.
 */
public class Symbole {
    private String type;
    private int deplacement;

    /**
     * Constructeur de la classe Symbole.
     *
     * @param type, type de la variable
     */
    public Symbole(String type) {
        this.type = type;
    }

    /**
     * Fonction qui retourne le type d'une variable.
     *
     * @return le type de la variable
     */
    public String getType() {
        return type;
    }

    /**
     * Procédure qui définit le type de la variable.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Fonction qui retourne la position de la variable dans la pile.
     *
     * @return le déplacement
     */
    public int getDeplacement() {
        return deplacement;
    }

    /**
     * Procédure qui définit la position de la variable dans la pile.
     *
     * @param deplacement le déplacement
     */
    public void setDeplacement(int deplacement) {
        this.deplacement = deplacement;
    }
}
