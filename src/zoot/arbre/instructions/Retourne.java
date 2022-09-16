package zoot.arbre.instructions;

import zoot.arbre.expressions.Expression;
import zoot.arbre.fonctions.Fonction;
import zoot.arbre.fonctions.GestionnaireFonctions;
import zoot.exceptions.EntreeNonDeclareeException;
import zoot.gestionErreurs.Erreur;
import zoot.gestionErreurs.StockageErreurs;
import zoot.tableDesSymboles.Entree;
import zoot.tableDesSymboles.TDS;

import java.util.Objects;

/**
 * CLasse Retourne.
 */
public class Retourne extends Instruction {
    private final Expression exp;

    /**
     * Constructeur de la classe Retourne.
     *
     * @param n numéro de ligne
     * @param e l'expression
     */
    public Retourne(int n, Expression e) {
        super(n);
        this.exp = e;
    }

    @Override
    public void verifier() {
        if (!GestionnaireFonctions.getInstance().isFonctionsSontTraitees()) { //Si retourner n'est pas dans une fonction
            StockageErreurs.getInstance().ajouter(new Erreur("Instruction retourner en dehors d'une fonction !", noLigne));
            this.exp.verifier();
        } else {
            exp.verifier();
            //On regarde la fonction la plus proche de l'instruction retourner pour regarder son type maintenant que l'on sait que l'on est dans une fonction
            int stockageNoLigne = 0;
            Fonction foncPlusProche = null;
            for (Fonction f : GestionnaireFonctions.getInstance().getFonctions()) {
                if (f.getNoLigne() < this.noLigne)
                    if (f.getNoLigne() >= stockageNoLigne) {
                        stockageNoLigne = f.getNoLigne();
                        foncPlusProche = f;
                    }
            }
            //On vérifie à présent que la fonction soit du même type de retour que l'expression que l'on essaie de retourner
            TDS.getInstance().sortieBloc();
            try {
                if (!TDS.getInstance().identifier(new Entree(Objects.requireNonNull(foncPlusProche).getIdf(), "fonction", foncPlusProche.getTypeParams())).getType().equals(exp.getType()))
                    StockageErreurs.getInstance().ajouter(new Erreur("Le type de retour de la fonction ne correspond pas avec l'expression retournée !", noLigne));
            } catch (EntreeNonDeclareeException e) {
                StockageErreurs.getInstance().ajouter(new Erreur(e.getMessage(), this.noLigne));
            }
            TDS.getInstance().entreeBlocPrec();
        }
    }

    @Override
    public String toMIPS(String... registres) { //Appelé à la fin de la fonction.
        StringBuilder sb = new StringBuilder();

        //Construction d'un commentaire approprié
        sb.append("# ").append("Retourne ").append(exp.toString()).append("\n");

        //Stockage de la valeur de l'expression dans v0
        sb.append(exp.toMIPS("$v0", "$t0", "$t1", "$t2", "$t3", "$t4", "$t5", "$t6", "$t7"));
        return sb.toString();
    }

    @Override
    public boolean contientUnRetourne() {
        return true;
    }
}
