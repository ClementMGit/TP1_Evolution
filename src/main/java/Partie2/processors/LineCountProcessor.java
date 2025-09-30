package Partie2.processors;

import Partie2.visitors.LineCountScanner;
import spoon.reflect.CtModel;

public class LineCountProcessor {

    /**
     * Lance le scanner sur le modèle et retourne le total de lignes uniques.
     */
    public int computeTotalLines(CtModel model) {
        LineCountScanner scanner = new LineCountScanner();
        // on lance depuis la racine (package racine) pour parcourir tout le projet
        model.getRootPackage().accept(scanner);
        return scanner.getTotalLines();
    }
}
