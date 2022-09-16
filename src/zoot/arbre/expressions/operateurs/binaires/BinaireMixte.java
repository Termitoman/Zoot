package zoot.arbre.expressions.operateurs.binaires;

import zoot.arbre.GenerateurNum;
import zoot.arbre.expressions.Expression;
import zoot.gestionErreurs.Erreur;
import zoot.gestionErreurs.StockageErreurs;

public abstract class BinaireMixte extends Expression {
    protected Expression eGauche;
    protected Expression eDroite;
    protected int numUnique;

    /**
     * Constructeur de la classe BinaireMixte.
     *
     * @param n numéro de ligne
     */
    protected BinaireMixte(int n, Expression eGauche, Expression eDroite) {
        super(n);
        this.eDroite = eDroite;
        this.eGauche = eGauche;
        this.numUnique = GenerateurNum.getInstance().genererNombre();
    }

    @Override
    public void verifier() {
        eGauche.verifier();
        eDroite.verifier();
        if (!eGauche.getType().equals(eDroite.getType()))
            StockageErreurs.getInstance().ajouter(new Erreur("Les expressions autour d'un binaire mixte ne sont pas de même type !", noLigne));
    }

    @Override
    public String getType() {
        return "booleen";
    }

    @Override
    public int getNbErchov() {
        return eGauche.getNbErchov() == eDroite.getNbErchov() ? eGauche.getNbErchov() + 1 : Math.max(eGauche.getNbErchov(), eDroite.getNbErchov());
    }

    /**
     * Foonction qui supprime un registre de la liste des registres libres lors de son utilisation.
     *
     * @param n         l'indice du registre utilisé/à supprimer
     * @param registres la liste de registres disponibles actuellement
     * @return la liste de registre disponible après l'appel de la fonction
     */
    public String[] supprRegistreInutile(int n, String... registres) {
        String[] temp = new String[registres.length - 1];
        int i = 0;
        for (int j = 0; j < registres.length; j++) {
            if (i == n)
                j++;
            if (j < registres.length)
                temp[i] = registres[j];
            i++;
        }
        return temp;
    }
}
