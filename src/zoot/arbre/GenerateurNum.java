package zoot.arbre;

public class GenerateurNum {
    private int nb;

    /**
     * Constructeur privé du singleton GenerateurNum.
     */
    private GenerateurNum() {
        this.nb = 0;
    }

    private static final GenerateurNum instance = new GenerateurNum();

    /**
     * Fonction qui retourne une instance de la classe GenerateurNum.
     *
     * @return une instance de la classe TDS.
     */
    public static GenerateurNum getInstance() {
        return instance;
    }

    public int genererNombre() {
        return nb++;
    }
}