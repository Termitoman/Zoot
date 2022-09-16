package zoot.arbre.expressions;

import zoot.arbre.fonctions.GestionnaireFonctions;
import zoot.exceptions.EntreeNonDeclareeException;
import zoot.gestionErreurs.Erreur;
import zoot.gestionErreurs.StockageErreurs;
import zoot.tableDesSymboles.Entree;
import zoot.tableDesSymboles.Symbole;
import zoot.tableDesSymboles.TDS;

/**
 * Classe Idf.
 */
public class Idf extends Expression {

    private final String nom;
    private int depl;
    private String type;

    /**
     * Constructeur de la classe IDF.
     *
     * @param texte texte
     * @param n     numéro de ligne
     */
    public Idf(String texte, int n) {
        super(n);
        this.nom = texte;
    }

    @Override
    public void verifier() {
        try {
            //Si la variable existe bien, on stocke sa position dans la pile (déplacement).
            Symbole temp = TDS.getInstance().identifier(new Entree(nom, "variable"));
            this.depl = temp.getDeplacement();
            this.type = temp.getType();
        } catch (EntreeNonDeclareeException e) {
            //Si la variable existe, elle est frocément dans le main, car elle n'est pas dans la fonction. (dans le try, on check si elle est dans la fonction, donc si l'on va dans le catch, cela signifie qu'elle n'y était pas.)
            //Sinon si on est dans une fonction, on cherche si la variable existe dans le bloc supérieur (le main)
            if (GestionnaireFonctions.getInstance().isFonctionsSontTraitees()) {
                try {
                    //On revient dans le bloc du main
                    TDS.getInstance().sortieBloc();

                    //Si la variable existe bien, on stocke sa position dans la pile (déplacement).
                    Symbole temp2 = TDS.getInstance().identifier(new Entree(nom, "variable"));
                    this.depl = temp2.getDeplacement();
                    this.type = temp2.getType();
                } catch (EntreeNonDeclareeException e2) {
                    //Sinon, on ajoute une erreur, on donne un type erreur et on passe à la suite.
                    StockageErreurs.getInstance().ajouter(new Erreur(e2.getMessage(), this.getNoLigne()));
                    this.type = "erreur";
                }
                //On revient dans le bloc de la fonction
                TDS.getInstance().entreeBlocPrec();
            } else {
                //Sinon, on ajoute une erreur, on donne un type erreur et on passe à la suite.
                StockageErreurs.getInstance().ajouter(new Erreur(e.getMessage(), this.getNoLigne()));
                this.type = "erreur";
            }
        }
    }

    @Override
    public String toMIPS(String... registres) {
        return "\tlw $v0, " + this.depl + "($s7)\n";
    }

    @Override
    public String getType() {
        return type;
    }

    /**
     * Fonction qui retourne le déplacement d'un idf
     *
     * @return le déplacement d'un idf
     */
    public int getDepl() {
        return depl;
    }

    @Override
    public String toString() {
        return nom;
    }
}
