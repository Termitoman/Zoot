package zoot.arbre.expressions;

/**
 * Classe ConstanteBooleenne qui représente les constantes booléennes (vrai/faux)
 */
public class ConstanteBooleenne extends Constante {
    /**
     * Constructeur de la classe ConstanteBooleenne.
     *
     * @param texte texte
     * @param n     numéro de ligne
     */
    public ConstanteBooleenne(String texte, int n) {
        super(texte, n);
    }

    @Override
    public String toMIPS(String... registres) {
        return "\tla $v0, " + this.cste + "\n";
    }

    @Override
    public String getType() {
        return "booleen";
    }
}
