package zoot.arbre.expressions.operateurs.binaires;

import zoot.arbre.expressions.Expression;

/**
 * Classe Et.
 */
public class Et extends BinaireBooleen {
    /**
     * Constructeur de la classe Et.
     *
     * @param n       numéro de ligne
     * @param eGauche expression gauche
     * @param eDroite expression droite
     */
    public Et(int n, Expression eGauche, Expression eDroite) {
        super(n, eGauche, eDroite);
    }

    @Override
    public String toMIPS(String... registres) {
        StringBuilder sb = new StringBuilder();
        sb.append(eGauche.toMIPS(registres));
        sb.append("\n# Initialiser $t8 avec la valeur faux\n");
        sb.append("\tla $t8, faux\n");
        sb.append("\tbeq $t8, $v0, Sinon").append(numUnique).append("\n");
        sb.append(eDroite.toMIPS(registres));
        sb.append("\tbeq $t8, $v0, SinonImbrique").append(numUnique).append("\n");
        sb.append("\tla $v0, vrai\n");
        sb.append("\tb FinSiImbrique").append(numUnique).append("\n");
        sb.append("SinonImbrique").append(numUnique).append(":").append("\n");
        sb.append("\tla $v0, faux\n");
        sb.append("FinSiImbrique").append(numUnique).append(":\n");
        sb.append("\tb FinSi").append(numUnique).append("\n");
        sb.append("Sinon").append(numUnique).append(":").append("\n");
        sb.append("\tla $v0, faux\n");
        sb.append("FinSi").append(numUnique).append(":\n");
        return sb.toString();
    }

    @Override
    public String toString() {
        return eGauche.toString() + " et " + eDroite.toString();
    }
}
