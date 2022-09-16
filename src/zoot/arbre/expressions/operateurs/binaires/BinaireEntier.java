package zoot.arbre.expressions.operateurs.binaires;

import zoot.arbre.expressions.Expression;
import zoot.gestionErreurs.Erreur;
import zoot.gestionErreurs.StockageErreurs;

/**
 * Classe BinaireEntier.
 */
public abstract class BinaireEntier extends Expression {
    protected Expression eGauche;
    protected Expression eDroite;

    /**
     * Constructeur de la classe BinaireEntier.
     *
     * @param n numéro de ligne
     */
    protected BinaireEntier(int n, Expression eGauche, Expression eDroite) {
        super(n);
        this.eDroite = eDroite;
        this.eGauche = eGauche;
    }

    @Override
    public void verifier() {
        eGauche.verifier();
        eDroite.verifier();
        if (!eGauche.getType().equals("entier") || !eDroite.getType().equals("entier"))
            StockageErreurs.getInstance().ajouter(new Erreur("Une des expressions autour de l'opérateur binaire n'est pas un entier !", noLigne));
    }

    @Override
    public String getType() {
        return "entier";
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
