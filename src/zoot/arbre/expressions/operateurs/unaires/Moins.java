package zoot.arbre.expressions.operateurs.unaires;

import zoot.arbre.expressions.Expression;
import zoot.gestionErreurs.Erreur;
import zoot.gestionErreurs.StockageErreurs;

public class Moins extends Expression {
    private final Expression exp;

    /**
     * Constructeur de la classe Expression.
     *
     * @param n numéro de ligne
     */
    public Moins(int n, Expression exp) {
        super(n);
        this.exp = exp;
    }

    @Override
    public void verifier() {
        exp.verifier();
        if (!exp.getType().equals("entier"))
            StockageErreurs.getInstance().ajouter(new Erreur("Appliquer un '-' sur une expression autre qu'entière n'est pas autorisé !", noLigne));
    }

    @Override
    public String toMIPS(String... registres) {
        StringBuilder sb = new StringBuilder();
        sb.append(exp.toMIPS(registres));
        sb.append("\tsub ").append(registres[0]).append(", $zero, ").append(registres[0]).append("\n");
        return sb.toString();
    }

    @Override
    public String getType() {
        return "entier";
    }

    @Override
    public int getNbErchov() {
        return exp.getNbErchov();
    }

    @Override
    public String toString() {
        return "-" + exp.toString();
    }
}
