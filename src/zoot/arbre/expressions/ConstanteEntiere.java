package zoot.arbre.expressions;

/**
 * Classe ConstanteEntière qui représente les constantes entières (nombres)
 */
public class ConstanteEntiere extends Constante {
    /**
     * Constructeur de la classe ConstanteEntiere.
     *
     * @param texte texte
     * @param n     numéro de ligne
     */
    public ConstanteEntiere(String texte, int n) {
        super(texte, n);
    }

    @Override
    public String toMIPS(String... registres) {
        return "\tli $v0, " + this.cste + "\n";
    }

    @Override
    public String getType() {
        return "entier";
    }
}
