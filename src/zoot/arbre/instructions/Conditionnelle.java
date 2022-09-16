package zoot.arbre.instructions;

import zoot.arbre.ArbreAbstrait;
import zoot.arbre.BlocDInstructions;
import zoot.arbre.GenerateurNum;
import zoot.arbre.expressions.Expression;
import zoot.gestionErreurs.Erreur;
import zoot.gestionErreurs.StockageErreurs;

public class Conditionnelle extends Instruction {
    private final Expression exp;
    private final ArbreAbstrait blocAlors;
    private final ArbreAbstrait blocSinon;
    private final int numUnique;

    public Conditionnelle(int n, Expression exp, ArbreAbstrait blocAlors, ArbreAbstrait blocSinon) {
        super(n);
        this.exp = exp;
        this.blocAlors = blocAlors;
        this.blocSinon = blocSinon;
        this.numUnique = GenerateurNum.getInstance().genererNombre();
    }

    @Override
    public void verifier() {
        exp.verifier();
        if (!exp.getType().equals("booleen"))
            StockageErreurs.getInstance().ajouter(new Erreur("La condition d'un 'Si' ne peux pas être autre que booléenne !", noLigne));
        blocAlors.verifier();
        blocSinon.verifier();
    }

    @Override
    public String toMIPS(String... registres) {
        StringBuilder sb = new StringBuilder();
        sb.append("# Si ").append(exp.toString()).append(" \n");
        sb.append("\t").append(exp.toMIPS("$v0", "$t0", "$t1", "$t2", "$t3", "$t4", "$t5", "$t6", "$t7")).append("\n");
        sb.append("\n# Initialiser $t8 avec la valeur faux\n");
        sb.append("\tla $t8, faux\n");
        sb.append("\tbeq $t8, $v0, Sinon").append(numUnique).append("\n");
        sb.append("# Alors \n");
        sb.append(blocAlors.toMIPS(registres));
        sb.append("\tb FinSi").append(numUnique).append("\n");
        sb.append("Sinon").append(numUnique).append(":").append("\n");
        sb.append("# Sinon \n");
        sb.append(blocSinon.toMIPS(registres));
        sb.append("FinSi").append(numUnique).append(":\n");
        return sb.toString();
    }

    @Override
    public boolean contientUnRetourne() {
        boolean temp1 = false, temp2 = false;
        for (ArbreAbstrait a : ((BlocDInstructions) this.blocAlors).getProgramme()) {
            if (a.contientUnRetourne())
                temp1 = true;
        }
        for (ArbreAbstrait a : ((BlocDInstructions) this.blocSinon).getProgramme()) {
            if (a.contientUnRetourne())
                temp2 = true;
        }
        return temp1 && temp2;
    }
}