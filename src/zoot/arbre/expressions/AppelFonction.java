package zoot.arbre.expressions;

import zoot.arbre.fonctions.Fonction;
import zoot.arbre.fonctions.GestionnaireFonctions;
import zoot.exceptions.EntreeNonDeclareeException;
import zoot.gestionErreurs.Erreur;
import zoot.gestionErreurs.StockageErreurs;
import zoot.tableDesSymboles.Entree;
import zoot.tableDesSymboles.TDS;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Classe AppelFonction.
 */
public class AppelFonction extends Expression {
    private final String idf;
    private String type;
    private final ArrayList<Expression> params;
    private Fonction fonctionAppelee = null;

    /**
     * Constructeur de la classe AppelFonction.
     *
     * @param n   numéro de la ligne
     * @param idf identifiant de la fonction appelée
     */
    public AppelFonction(int n, String idf) {
        super(n);
        this.idf = idf;
        this.params = new ArrayList<>();
    }

    /**
     * Constructeur de la classe AppelFonction.
     *
     * @param n      numéro de la ligne
     * @param idf    identifiant de la fonction appelée
     * @param params paramètres de la fonction appelée
     */
    public AppelFonction(int n, String idf, ArrayList<Expression> params) {
        super(n);
        this.idf = idf;
        this.params = params;
    }

    @Override
    public void verifier() {
        if (GestionnaireFonctions.getInstance().isFonctionsSontTraitees()) { //Si on est dans une fonction
            //On regarde la fonction la plus proche de l'expression appel fonction pour regarder si cette même fonction ne s'appelle pas récursivement.
            //On vérifie également qu'il existe bien une fonction que l'on appelle (vérficiation des paramètres.
            int stockageNoLigne = 0;
            Fonction fonctionProche = null;
            for (Fonction f : GestionnaireFonctions.getInstance().getFonctions()) {
                if (f.getNoLigne() < this.noLigne)
                    if (f.getNoLigne() >= stockageNoLigne) {
                        stockageNoLigne = f.getNoLigne();
                        fonctionProche = f;
                    }
            }

            //On vérifie que cet appel ne soit pas un appel récursif
            if (fonctionProche != null) {
                if (fonctionProche.getIdf().equals(this.idf) && fonctionProche.getNbParams() == params.size()) //Si c'est la même, cela signifie que c'est un appel récursif
                    StockageErreurs.getInstance().ajouter(new Erreur("Appel récursif d'une fonction non autorisée dans zoot !", noLigne));
                TDS.getInstance().entreeBlocI(fonctionProche.getNoBlocFonc());
            }
        }

        //On vérifie les paramètres
        for (Expression e : params) {
            e.verifier();
        }

        //On vérifie si l'appel correspond bien à une fonction au niveau des paramètres
        for (Fonction f : GestionnaireFonctions.getInstance().getFonctions()) {
            if (f.getIdf().equals(this.idf) && f.getNbParams() == this.params.size()) {
                boolean estLaBonne = true;
                for (int i = 0; i < f.getNbParams(); i++) {
                    if (!f.getTypeParams().get(i).equals(this.params.get(i).getType()))
                        estLaBonne = false;
                }
                if (estLaBonne)
                    fonctionAppelee = f;
            }
        }
        if (fonctionAppelee == null)
            StockageErreurs.getInstance().ajouter(new Erreur("Aucun prototype de fonction ne correspond à cet appel !", noLigne));

        //On récupère le type de retour de la fonction
        ArrayList<String> temp;
        if (this.getTypeParam().isEmpty())
            temp = new ArrayList<>();
        else
            temp = new ArrayList<>(Arrays.asList(this.getTypeParam().split(",")));

        //Si l'appel de la fonction ne se trouve pas dans le main, on sort du bloc actuel (bloc de la fonction) pour aller dans le bloc du main.
        if (GestionnaireFonctions.getInstance().isFonctionsSontTraitees())
            TDS.getInstance().sortieBloc();
        try {
            //On vérifie que la fonction que l'on appelle existe, et si c'est le cas, on récupère son type de retour.
            this.type = TDS.getInstance().identifier(new Entree(this.idf, "fonction", temp)).getType();
        } catch (EntreeNonDeclareeException e2) {
            StockageErreurs.getInstance().ajouter(new Erreur(e2.getMessage(), noLigne));
            this.type = "erreur";
        }
        //Une fois la vérification terminée, on revient dans le bloc de la fonction.
        if (GestionnaireFonctions.getInstance().isFonctionsSontTraitees())
            TDS.getInstance().entreeBlocPrec();
    }

    @Override
    public String toMIPS(String... registres) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n# Sauvegarde des registres avant l'appel de fonction.\n");
        sb.append("\tsw $ra,0($sp)\n");
        sb.append("\tsw $s1,-4($sp)\n");
        sb.append("\taddi $sp,$sp,-8\n");
        sb.append("\t# Passage des valeurs dans les paramètres de la fonction appelée.\n");
        TDS.getInstance().entreeBlocI(fonctionAppelee.getNoBlocFonc());
        //On parcourt les valeurs données dans un appel de fonction, on stocke chaque valeur au paramètre qui convient. Ce paramètre étant stocké dans la pile.
        for (int i = 0; i < params.size(); i++) {
            //On stocke dans v0 le resultat de l'expression.
            sb.append(params.get(i).toMIPS(registres));
            //On transfère l'expression dans la variable

            sb.append("\t").append("sw $v0, ").append(TDS.getInstance().identifier(new Entree(fonctionAppelee.getNomParams().get(i), "variable")).getDeplacement()).append("($s7)\n");
        }
        TDS.getInstance().entreeBlocPrec();
        sb.append("\t# Appel de la fonction.\n");
        sb.append("\tjal ").append(this.idf).append(this.params.size()).append("\n"); //On appelle la fonction qui possède le bon nombre de paramètres.
        sb.append("\t# Restauration des registres après l'appel de fonction.\n");
        sb.append("\tlw $s1,4($sp)\n");
        sb.append("\tlw $ra,8($sp)\n");
        sb.append("\taddi $sp,$sp,8\n");
        return sb.toString();
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public String getTypeParam() {
        //On récupère les types des paramètres pour pouvoir identifier la fonction.
        StringBuilder sb = new StringBuilder();
        if (!this.params.isEmpty()) {
            for (Expression e : this.params) {
                sb.append(e.getType()).append(",");
            }
            sb.replace(sb.length() - 1, sb.length(), "");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return idf + "(" + this.affichageParametres() + ")";
    }

    private String affichageParametres() {
        StringBuilder sb = new StringBuilder();
        for (Expression e : params)
            sb.append(e.toString()).append(",");
        if (sb.length() >= 1)
            sb.replace(sb.length() - 1, sb.length(), "");
        return sb.toString();
    }
}
