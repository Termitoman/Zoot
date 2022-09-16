package zoot;

import zoot.analyse.AnalyseurLexical;
import zoot.analyse.AnalyseurSyntaxique;
import zoot.arbre.ArbreAbstrait;
import zoot.arbre.fonctions.GestionnaireFonctions;
import zoot.exceptions.AnalyseException;
import zoot.gestionErreurs.Erreur;
import zoot.gestionErreurs.StockageErreurs;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe Zoot.
 */
public class Zoot {

    public Zoot(String nomFichier) {
        try {
            AnalyseurSyntaxique analyseur = new AnalyseurSyntaxique(new AnalyseurLexical(new FileReader(nomFichier)));
            ArbreAbstrait arbre = (ArbreAbstrait) analyseur.parse().value;

            arbre.verifier();
            GestionnaireFonctions.getInstance().setFonctionsSontTraitees(true);
            GestionnaireFonctions.getInstance().verifier();
            GestionnaireFonctions.getInstance().setFonctionsSontTraitees(false);

            if (StockageErreurs.getInstance().getNbErreurs() == 0) {
                System.out.println("COMPILATION OK");
                String nomSortie = nomFichier.replaceAll("[.]zoot", ".mips");
                PrintWriter flot = new PrintWriter(new BufferedWriter(new FileWriter(nomSortie)));
                flot.println(arbre.toMIPS());
                flot.close();
            } else {
                for (Erreur e : StockageErreurs.getInstance().getListeErreurs()) {
                    System.err.println("ERREUR SEMANTIQUE : Ligne n°" + e.getNumLigne() + " : " + e.getMessage());
                }
            }
        } catch (FileNotFoundException ex) {
            System.err.println("Fichier " + nomFichier + " inexistant");
        } catch (AnalyseException ex) {
            System.err.println(ex.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(Zoot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Nombre incorrect d'arguments");
            System.err.println("\tjava -jar zoot.jar <fichierSource.zoot>");
            System.exit(1);
        }
        new Zoot(args[0]);
    }

}
