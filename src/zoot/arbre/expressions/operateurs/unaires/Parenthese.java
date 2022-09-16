package zoot.arbre.expressions.operateurs.unaires;

import zoot.arbre.expressions.Expression;

/**
 * Classe Parenthese.
 */
public class Parenthese extends Expression {
    private final Expression exp;

    /**
     * Constructeur de la classe Parenthese.
     *
     * @param n numéro de ligne
     */
    public Parenthese(int n, Expression exp) {
        super(n);
        this.exp = exp;
    }

    @Override
    public void verifier() {
        exp.verifier();
    }

    @Override
    public String toMIPS(String... registres) {
        return exp.toMIPS(registres);
    }

    @Override
    public String getType() {
        return exp.getType();
    }

    @Override
    public String toString() {
        return "(" + exp.toString() + ")";
    }

    @Override
    public int getNbErchov() {
        return exp.getNbErchov();
    }
}
