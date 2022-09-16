package zoot.arbre.expressions.operateurs.binaires;

import zoot.arbre.GenerateurNum;
import zoot.arbre.expressions.Expression;
import zoot.gestionErreurs.Erreur;
import zoot.gestionErreurs.StockageErreurs;

/**
 * Classe abstraite BinaireBooleen.
 */
public abstract class BinaireBooleen extends Expression {
    protected Expression eGauche;
    protected Expression eDroite;
    protected int numUnique;

    /**
     * Constructeur de la classe BinaireBooleen.
     *
     * @param n       numéro de ligne
     * @param eGauche expression gauche
     * @param eDroite expression droite
     */
    protected BinaireBooleen(int n, Expression eGauche, Expression eDroite) {
        super(n);
        this.eGauche = eGauche;
        this.eDroite = eDroite;
        this.numUnique = GenerateurNum.getInstance().genererNombre();
    }

    @Override
    public void verifier() {
        eGauche.verifier();
        eDroite.verifier();
        if (!eGauche.getType().equals("booleen") || !eDroite.getType().equals("booleen"))
            StockageErreurs.getInstance().ajouter(new Erreur("Une des expressions autour de l'opérateur binaire n'est pas un booléen !", noLigne));
    }

    @Override
    public String toMIPS(String... registres) {
        return null;
    }

    @Override
    public String getType() {
        return "booleen";
    }

    @Override
    public int getNbErchov() {
        return eGauche.getNbErchov() == eDroite.getNbErchov() ? eGauche.getNbErchov() + 1 : Math.max(eGauche.getNbErchov(), eDroite.getNbErchov());
    }
}
