package zoot.arbre;

import zoot.arbre.fonctions.GestionnaireFonctions;
import zoot.tableDesSymboles.TDS;

import java.util.ArrayList;

/**
 * 21 novembre 2018
 *
 * @author brigitte wrobel-dautcourt
 */

public class BlocDInstructions extends ArbreAbstrait {

    protected ArrayList<ArbreAbstrait> programme;

    public BlocDInstructions(int n) {
        super(n);
        programme = new ArrayList<>();
    }

    public void ajouter(ArbreAbstrait a) {
        programme.add(a);
    }

    @Override
    public void verifier() {
        for (ArbreAbstrait a : programme) {
            a.verifier();
        }
    }

    @Override
    public String toMIPS(String... registres) {
        StringBuilder sb = new StringBuilder(programme.size() * 16);
        if (!GestionnaireFonctions.getInstance().isFonctionsSontTraitees()) { //Si on est en train de traiter le bloc d'instruction du programme principal, on construit le code du programme principal.
            GestionnaireFonctions.getInstance().setFonctionsSontTraitees(true);
            sb.append("#IOPETI Hugo & YVOZ Ludovic\n\n");
            sb.append(".data\n");
            sb.append("vrai:\t.word 1\n");
            sb.append("faux:\t.word 0\n");
            sb.append("vraiAff:\t.asciiz \"vrai\"\n");
            sb.append("fauxAff:\t.asciiz \"faux\"\n");
            sb.append("sautLigne:\t.asciiz \"\\n\"\n");
            sb.append(".text\n");
            sb.append("main :\n");
            sb.append("\n# Initialiser $s7 avec $sp\n");
            sb.append("\tmove $s7, $sp\n");
            sb.append("\n# Réserver la place pour ").append((TDS.getInstance().getTailleZoneVariables() * -1) / 4).append(" variables\n");
            sb.append("\tadd $sp, $sp, ").append(TDS.getInstance().getTailleZoneVariables()).append("\n\n");
            for (ArbreAbstrait a : programme) {
                sb.append(a.toMIPS()).append("\n");
            }
            sb.append("end :\n").append("\tli $v0, 10\n").append("\tsyscall");
            sb.append(GestionnaireFonctions.getInstance().toMipsFonctions());
        } else { //Sinon, on écrit le code des blocs d'instructions des fonctions
            for (ArbreAbstrait a : programme) { //On parcourt le bloc d'instruction de la fonction concernée.
                sb.append(a.toMIPS()).append("\n");
            }
        }

        return sb.toString();
    }

    @Override
    public String toString() {
        return programme.toString();
    }

    /**
     * Fonction qui retourne le programme d'un bloc d'instructions.
     *
     * @return le programme d'un bloc d'instructions.
     */
    public ArrayList<ArbreAbstrait> getProgramme() {
        return programme;
    }
}
