package zoot.tableDesSymboles;

import zoot.exceptions.DoubleDeclarationException;
import zoot.exceptions.EntreeNonDeclareeException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Singleton TDS représentant la table des symboles de zoot.
 */
public class TDS {
    //On fait une arrayList de tds (une pour le main, une par fonction).
    private final ArrayList<HashMap<Entree, Symbole>> tableDesSymboles;
    private int noBlocActuel = 0;
    private int noBlocPrec = 0;

    /**
     * Constructeur privé du singleton TDS.
     */
    private TDS() {
        this.tableDesSymboles = new ArrayList<>();
        this.tableDesSymboles.add(new HashMap<>()); //Ajout de la table des symboles représentant le bloc 0 (main)
    }

    private static final TDS instance = new TDS();

    /**
     * Fonction qui retourne une instance de la classe TDS.
     *
     * @return une instance de la classe TDS.
     */
    public static TDS getInstance() {
        return instance;
    }

    /**
     * Procédure qui ajoute un identifiant ainsi que son symbole à la table des symboles.
     *
     * @param e l'entrée
     * @param s le type de la variable
     * @throws DoubleDeclarationException Exception étant appelée lorsque l'utilisateur veut déclarer une variable déjà inscrite dans la table des symboles.
     */
    public void ajouter(Entree e, Symbole s) throws DoubleDeclarationException {
        if (this.tableDesSymboles.size() > noBlocActuel) { //Si la TDS recherchée n'existe pas (dans une fonction sans déclarations)
            for (Map.Entry<Entree, Symbole> elem : this.tableDesSymboles.get(noBlocActuel).entrySet()) {
                if (elem.getKey().getIdf().equals(e.getIdf()) && elem.getKey().getTypeParam().size() == e.getTypeParam().size()) { //Si 2 entrées sont identiques.
                    throw new DoubleDeclarationException("Le symbole " + e.getIdf() + " ne peut pas être ajouté deux fois dans la table des symboles !");
                }
                if (elem.getKey().getIdf().equals(e.getIdf()) && !elem.getKey().getType().equals(e.getType())) { //Si une variable et une fonction ont le même nom.
                    throw new DoubleDeclarationException("Le symbole " + e.getIdf() + " ne peut pas être assigné à la fois à une variable et à une fonction !");
                }
            }
        }
        s.setDeplacement(this.getTailleZoneVariables());
        this.tableDesSymboles.get(noBlocActuel).put(e, s);
    }

    /**
     * Fonction qui identifie une variable dans la table des symboles et retourne son symbole correspondant.
     *
     * @param e l'entrée
     * @return le symbole de la variable
     * @throws EntreeNonDeclareeException Exception étant appelée lorsque l'utilisateur recherche une variable n'étant pas enregistrée dans la table des symboles.
     */
    public Symbole identifier(Entree e) throws EntreeNonDeclareeException {
        Symbole s = null;
        if (this.tableDesSymboles.size() > noBlocActuel) { //Si la TDS recherchée existe (dans une fonction sans déclarations elle peut ne pas exister)
            for (Map.Entry<Entree, Symbole> elem : this.tableDesSymboles.get(noBlocActuel).entrySet()) {
                if (elem.getKey().getIdf().equals(e.getIdf()) && elem.getKey().getType().equals(e.getType()) && elem.getKey().getTypeParam().size() == e.getTypeParam().size()) {
                    s = new Symbole(elem.getValue().getType());
                    s.setDeplacement(elem.getValue().getDeplacement());
                }
            }
        }
        if (s == null) {
            throw new EntreeNonDeclareeException("Le symbole " + e.getIdf() + " n'existe pas dans la table des symboles !");
        }
        return s;
    }

    /**
     * Fonction qui retourne la taille de la zone contenant toutes les variables définies dans la table des symboles.
     *
     * @return la taille en octets.
     */
    public int getTailleZoneVariables() {
        //On définit la taille d'un entier et la taille d'un booléen à 4 octets. (on descend dans la pile donc -4)
        int taille = 0;
        for (HashMap<Entree, Symbole> hm : tableDesSymboles)
            taille += hm.size();
        taille *= -4;
        return taille;
    }

    /**
     * Fonction qui retourne le bloc dans lequel a TDS se trouve actuellement.
     *
     * @return le numéro du bloc
     */
    public int getNoBlocActuel() {
        return noBlocActuel;
    }

    /**
     * Procédure qui fait rentrer la TDS dans un nouveau bloc.
     */
    public void entreeBloc() {
        //On crée le nouveau bloc
        this.tableDesSymboles.add(new HashMap<>());
        //On se positionne dans le nouveau bloc.
        noBlocActuel = this.tableDesSymboles.size() - 1;
    }

    /**
     * Procédure qui fait sortir la TDS d'un bloc.
     */
    public void sortieBloc() {
        //Quand on sort du bloc d'une fonction, on va dans le bloc principal (interdiction de déclarer une fonction dans une fonction).
        noBlocActuel = 0;
    }

    /**
     * Procédure qui fait rentrer la TDS dans le bloc suivant lors de la vérification.
     */
    public void entreeBlocVerif() {
        //On augmente le bloc précédent
        noBlocPrec++;
        //On se positionne dans le bloc suivant.
        noBlocActuel = noBlocPrec;
    }

    /**
     * Procédure qui fait rentrer la TDS dans le bloc précedant.
     */
    public void entreeBlocPrec() {
        //On se positionne dans le bloc prec.
        noBlocActuel = noBlocPrec;
    }

    /**
     * Procédure qui fait rentrer la TDS dans un bloc spécifique.
     *
     * @param noBloc le noBloc du bloc dans lequel on veut rentrer.
     */
    public void entreeBlocI(int noBloc) {
        noBlocPrec = noBlocActuel;
        noBlocActuel = noBloc;
    }
}
