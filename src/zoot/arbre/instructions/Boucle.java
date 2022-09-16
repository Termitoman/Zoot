package zoot.arbre.instructions;

import zoot.arbre.ArbreAbstrait;
import zoot.arbre.BlocDInstructions;
import zoot.arbre.GenerateurNum;
import zoot.arbre.expressions.Expression;
import zoot.gestionErreurs.Erreur;
import zoot.gestionErreurs.StockageErreurs;

public class Boucle extends Instruction {
    private final Expression exp;
    private final ArbreAbstrait bloc;
    private final int numUnique;

    public Boucle(int n, Expression exp, ArbreAbstrait bloc) {
        super(n);
        this.exp = exp;
        this.bloc = bloc;
        this.numUnique = GenerateurNum.getInstance().genererNombre();
    }

    @Override
    public void verifier() {
        exp.verifier();
        if (!exp.getType().equals("booleen"))
            StockageErreurs.getInstance().ajouter(new Erreur("L'expression après un 'jusqua' doit être booléenne !", noLigne));
        bloc.verifier();
    }

    @Override
    public String toMIPS(String... registres) {
        StringBuilder sb = new StringBuilder();
        sb.append("# Répeter ... jusqu'a ").append(exp.toString()).append("\n");
        sb.append("Boucle").append(numUnique).append(":\n");
        sb.append(bloc.toMIPS());
        sb.append(exp.toMIPS("$v0", "$t0", "$t1", "$t2", "$t3", "$t4", "$t5", "$t6", "$t7"));
        sb.append("\n# Initialiser $t8 avec la valeur vrai\n");
        sb.append("\tla $t8, vrai\n");
        sb.append("\tbeq $t8, $v0, FinBoucle").append(numUnique).append("\n");
        sb.append("\tj Boucle").append(numUnique).append("\n");
        sb.append("FinBoucle").append(numUnique).append(": \n");
        return sb.toString();
    }

    @Override
    public boolean contientUnRetourne() {
        boolean temp = false;
        for (ArbreAbstrait a : ((BlocDInstructions) this.bloc).getProgramme()) {
            if (a.contientUnRetourne())
                temp = true;
        }
        return temp;
    }
}