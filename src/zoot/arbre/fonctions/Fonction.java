package zoot.arbre.fonctions;

import zoot.arbre.ArbreAbstrait;
import zoot.arbre.BlocDInstructions;
import zoot.gestionErreurs.Erreur;
import zoot.gestionErreurs.StockageErreurs;
import zoot.tableDesSymboles.TDS;

import java.util.ArrayList;

/**
 * Classe Fonction.
 */
public class Fonction extends ArbreAbstrait {
    private final String idf;
    private final ArbreAbstrait bloc;
    private final ArrayList<String> typeParams;
    private final ArrayList<String> nomParams;
    private final int noBlocFonc;

    /**
     * Constructeur de la classe Fonction.
     *
     * @param n    le numéro de ligne
     * @param idf  l'identifiant
     * @param bloc le bloc d'instruction
     */
    public Fonction(int n, String idf, ArbreAbstrait bloc, ArrayList<String> typeParams, ArrayList<String> nomParams, int noBlocFonc) {
        super(n);
        this.idf = idf;
        this.bloc = bloc;
        this.typeParams = typeParams;
        this.nomParams = nomParams;
        this.noBlocFonc = noBlocFonc;
    }

    @Override
    public void verifier() {
        TDS.getInstance().entreeBlocVerif();
        this.bloc.verifier();
        TDS.getInstance().sortieBloc();

        boolean retourneExiste = false;

        //Chaque a correpsond à une instruction de la fonction.
        for (ArbreAbstrait a : ((BlocDInstructions) this.bloc).getProgramme()) {
            if (a.contientUnRetourne()) {
                retourneExiste = true;
            }
        }
        if (!retourneExiste) {
            StockageErreurs.getInstance().ajouter(new Erreur("Attention, la fonction à la ligne donnée ne possède pas d'instruction retourne où n'en contient pas partout !", this.noLigne));
        }
    }

    @Override
    public String toMIPS(String... registres) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n\n#Déclaration de la fonction ").append(this.idf).append("\n");
        sb.append(this.idf).append(this.typeParams.size()).append(": \n").append(this.bloc.toMIPS()); //On ajoute le nombre de paramètre à l'étiquette car c'est la façon dont on différencie nos fonctions.
        sb.append("\tjr $ra"); //On revient à l'endroit ou l'appel de fonction a été effectué
        return sb.toString();
    }

    /**
     * Fonction qui retourne l'identifiant d'une fonction.
     *
     * @return l'identifiant d'une fonction.
     */
    public String getIdf() {
        return idf;
    }

    /**
     * Fonction qui retourne le nombre de paramètres d'une fonction.
     *
     * @return le nombre de paramètre d'une fonction.
     */
    public int getNbParams() {
        return typeParams.size();
    }

    /**
     * Fonction qui retourne les types des paramètres stockés dans une ArrayList.
     *
     * @return une ArrayList contenant les types de paramètres.
     */
    public ArrayList<String> getTypeParams() {
        return typeParams;
    }

    /**
     * Fonction qui retourne le nom des paramètres d'une fonction.
     *
     * @return le nom des paramètres.
     */
    public ArrayList<String> getNomParams() {
        return nomParams;
    }

    /**
     * Fonction qui retourne le numéro de bloc qui correspond à la fonction.
     *
     * @return le numéro de bloc qui correspond à la fonction.
     */
    public int getNoBlocFonc() {
        return noBlocFonc;
    }
}
