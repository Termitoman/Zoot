package zoot.tableDesSymboles;

import java.util.ArrayList;

/**
 * Classe Entrée.
 */
public class Entree {
    private final String idf;
    private final String type; //Vaut soit "variable" soit "fonction"
    private final ArrayList<String> typeParam; //Type des paramètres

    /**
     * Constructeur de la classe Entrée.
     *
     * @param idf  l'identifiant
     * @param type le type de l'entrée
     */
    public Entree(String idf, String type) {
        this.idf = idf;
        this.type = type;
        this.typeParam = new ArrayList<>();
    }

    /**
     * Constructeur de la classe Entrée.
     *
     * @param idf       l'identifiant
     * @param type      le type de l'entrée
     * @param typeParam le type de/des param(s)
     */
    public Entree(String idf, String type, ArrayList<String> typeParam) {
        this.idf = idf;
        this.type = type;
        this.typeParam = typeParam;
    }

    /**
     * Fonction qui retourne l'identifiant de l'entrée.
     *
     * @return l'identifiant de l'entrée
     */
    public String getIdf() {
        return idf;
    }

    /**
     * Fonction qui retourne le type de l'entrée.
     *
     * @return le type de l'entrée.
     */
    public String getType() {
        return type;
    }

    /**
     * Fonction qui retourne les types des paramètres.
     *
     * @return les types des paramètres.
     */
    public ArrayList<String> getTypeParam() {
        return this.typeParam;
    }
}
