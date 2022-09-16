package zoot.arbre;

/**
 * Classe abstraite ArbreAbstrait.
 */
public abstract class ArbreAbstrait {

    //numéro de ligne du début de l'instruction.
    protected int noLigne;

    protected ArbreAbstrait(int n) {
        noLigne = n;
    }

    public int getNoLigne() {
        return noLigne;
    }

    public abstract void verifier();

    public abstract String toMIPS(String... registres); //Ajout d'un paramètre pour optimiser le code mips des expressions binaires.

    public boolean contientUnRetourne() {
        return false;
    }
}
