package zoot.arbre.expressions;

/**
 * Classe abstraite Constante.
 */
public abstract class Constante extends Expression {

    protected String cste;

    /**
     * Constructeur de la classe Constante
     *
     * @param texte texte
     * @param n     numéro de ligne.
     */
    protected Constante(String texte, int n) {
        super(n);
        cste = texte;
    }

    @Override
    public void verifier() {
        //Il n'y a rien à vérifier dans cette classe.
    }

    @Override
    public String toString() {
        return cste;
    }
}
