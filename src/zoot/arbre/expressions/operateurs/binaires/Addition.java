package zoot.arbre.expressions.operateurs.binaires;

import zoot.arbre.expressions.Expression;

import java.util.Arrays;

/**
 * Classe Addition.
 */
public class Addition extends BinaireEntier {

    /**
     * Constructeur de la classe Addition.
     *
     * @param n       numéro de ligne
     * @param eGauche expression gauche
     * @param eDroite expression droite
     */
    public Addition(int n, Expression eGauche, Expression eDroite) {
        super(n, eGauche, eDroite);
    }

    @Override
    public String toMIPS(String... registres) {
        StringBuilder sb = new StringBuilder();
        sb.append(eGauche.toMIPS(registres));
        //Si on a encore au moins 1 registre temporaire disponible, on l'utilise
        if (registres.length != 1) {
            //On stocke le résultat dans un registre pour pouvoir réutiliser v0
            sb.append("\tmove ").append(registres[1]).append(", ").append(registres[0]).append("\n");

            sb.append(eDroite.toMIPS(supprRegistreInutile(1, registres)));
            //On ajoute les 2 entiers stockés dans les 2 registres, puis stocke le résultat de la somme de v0.
            sb.append("\tadd ").append(registres[0]).append(",").append(registres[0]).append(",").append(registres[1]).append("\n");
        } else { //Sinon, on utilise la pile
            sb.append("\tsw $v0,($sp)\n");
            sb.append("\tadd $sp,$sp,-4\n");
            sb.append(eDroite.toMIPS(registres)).append("\n");
            sb.append("\tadd $sp,$sp,4\n");
            sb.append("\tlw $t8,($sp)\n");
            sb.append("\tadd $v0, $t8, $v0\n");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return eGauche.toString() + " + " + eDroite.toString();
    }
}
