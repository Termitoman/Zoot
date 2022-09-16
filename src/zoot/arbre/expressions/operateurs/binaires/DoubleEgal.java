package zoot.arbre.expressions.operateurs.binaires;

import zoot.arbre.expressions.Expression;

public class DoubleEgal extends BinaireMixte {
    public DoubleEgal(int n, Expression eGauche, Expression eDroite) {
        super(n, eGauche, eDroite);
    }

    @Override
    public String toMIPS(String... registres) {
        StringBuilder sb = new StringBuilder();
        sb.append(eGauche.toMIPS(registres));
        if (registres.length != 1) {
            sb.append("\tmove ").append(registres[1]).append(", ").append(registres[0]).append("\n");
            sb.append(eDroite.toMIPS(this.supprRegistreInutile(1, registres)));
            sb.append("\tbeq ").append(registres[1]).append(", ").append(registres[0]).append(", Sinon").append(numUnique).append("\n");
            sb.append("\tla $v0, faux\n");
            sb.append("\tb FinSi").append(numUnique).append("\n");
            sb.append("Sinon").append(numUnique).append(":").append("\n");
            sb.append("\tla $v0, vrai\n");
            sb.append("FinSi").append(numUnique).append(":\n");
        } else {
            sb.append("\tsw $v0,($sp)\n");
            sb.append("\tadd $sp,$sp,-4\n");
            sb.append(eDroite.toMIPS(registres)).append("\n");
            sb.append("\tadd $sp,$sp,4\n");
            sb.append("\tlw $t8,($sp)\n");
            sb.append("\tbeq $t8, $v0, Sinon").append(numUnique).append("\n");
            sb.append("\tla $v0, faux\n");
            sb.append("\tb FinSi").append(numUnique).append("\n");
            sb.append("Sinon").append(numUnique).append(":").append("\n");
            sb.append("\tla $v0, vrai\n");
            sb.append("FinSi").append(numUnique).append(":\n");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return eGauche.toString() + "==" + eDroite.toString();
    }
}
