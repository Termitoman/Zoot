package zoot.arbre.instructions;

import zoot.arbre.expressions.Expression;
import zoot.arbre.expressions.Idf;
import zoot.gestionErreurs.Erreur;
import zoot.gestionErreurs.StockageErreurs;

/**
 * Classe Affect.
 */
public class Affect extends Instruction {

    protected Idf idf;
    protected Expression exp;

    /**
     * Constructeur de la classe Affect.
     *
     * @param i l'identifiant
     * @param e l'expression
     * @param n le numéro de ligne
     */
    public Affect(Idf i, Expression e, int n) {
        super(n);
        idf = i;
        exp = e;
    }

    @Override
    public void verifier() {
        idf.verifier();
        exp.verifier();
        //On vérifie que la variable et l'expression sont du même type, si une erreur à déjà été détectée avec l'idf et l'exp, on ne stocke pas d'erreur de type.
        if (!idf.getType().equals("erreur") && !exp.getType().equals("erreur") && !idf.getType().equals(exp.getType())) {
            //Sinon, on stocke l'erreur et passe à la suite
            StockageErreurs.getInstance().ajouter(new Erreur("Attention le type de la variable et le type de l'expression ne correspondent pas !", this.getNoLigne()));
        }
    }

    @Override
    public String toMIPS(String... registres) {
        StringBuilder sb = new StringBuilder();
        //Construction d'un commentaire approprié.
        sb.append("# ").append(idf.toString()).append(" = ").append(exp.toString()).append("\n");

        //On stocke dans v0 le resultat de l'expression.
        sb.append(exp.toMIPS("$v0", "$t0", "$t1", "$t2", "$t3", "$t4", "$t5", "$t6", "$t7"));
        //On transfère l'expression dans la variable
        sb.append("\t").append("sw $v0, ").append(idf.getDepl()).append("($s7)\n");
        return sb.toString();
    }

}
