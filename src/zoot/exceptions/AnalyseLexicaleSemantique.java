package zoot.exceptions;

/**
 * CLasse correpsondant à l'exception AnalyseLexicaleSemantique se déclanchant lorsqu'une erreur sémantique à lieu pendant l'analyse lexicale.
 */
public class AnalyseLexicaleSemantique extends AnalyseException {
    protected AnalyseLexicaleSemantique(String m) {
        super(m);
    }
}