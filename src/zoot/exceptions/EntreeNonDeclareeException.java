package zoot.exceptions;

/**
 * Classe EntreeNonDeclareeException représentant l'exception se déclenchant lorsque le compilateur détecte une entrée utilisée sans avoir été déclarée.
 */
public class EntreeNonDeclareeException extends AnalyseLexicaleSemantique {
    public EntreeNonDeclareeException(String m) {
        super(m);
    }
}
