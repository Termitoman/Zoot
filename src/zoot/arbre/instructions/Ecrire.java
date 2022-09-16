package zoot.arbre.instructions;

import zoot.arbre.GenerateurNum;
import zoot.arbre.expressions.Expression;

/**
 * Classe Ecrire.
 */
public class Ecrire extends Instruction {

    private final Expression exp;
    private final int numUnique;

    /**
     * Constructeur de la classe Ecrire.
     *
     * @param e l'expression
     * @param n le numéro de ligne
     */
    public Ecrire(Expression e, int n) {
        super(n);
        exp = e;
        this.numUnique = GenerateurNum.getInstance().genererNombre();
    }

    @Override
    public void verifier() {
        exp.verifier();
    }

    @Override
    public String toMIPS(String... registres) {
        StringBuilder sb = new StringBuilder();

        //Ecriture d'un commentaire adapté
        sb.append("# Ecrire ").append(exp.toString()).append("\n");

        //Affichage de la valeur
        //On met le résultat de l'expression dans v0 pour l'afficher ensuite
        sb.append(exp.toMIPS("$v0", "$t0", "$t1", "$t2", "$t3", "$t4", "$t5", "$t6", "$t7"));
        //Si c'est un booleen on l'affiche tel quel
        if (exp.getType().equals("booleen")) {
            sb.append("\n# Initialiser $t8 avec la valeur faux\n");
            sb.append("\tla $t8, faux\n");
            sb.append("\tbeq $t8, $v0, Sinon").append(numUnique).append("\n");
            sb.append("\tla $a0, vraiAff\n");
            sb.append("\tli $v0, 4\n");
            sb.append("\tsyscall\n");
            sb.append("\tb FinSi").append(numUnique).append("\n");
            sb.append("Sinon").append(numUnique).append(":").append("\n");
            sb.append("\tla $a0, fauxAff\n");
            sb.append("\tli $v0, 4\n");
            sb.append("\tsyscall\n");
            sb.append("FinSi").append(numUnique).append(":\n");
        } else {
            sb.append("\tmove $a0, $v0\n"); //On met le résultat de l'expression
            sb.append("\tli $v0, 1\n");
            sb.append("\tsyscall\n");
        }
        //Affichage d'un saut de ligne
        sb.append("\tla $a0, sautLigne\n");
        sb.append("\tli $v0, 4\n");
        sb.append("\tsyscall\n");

        return sb.toString();
    }
}