package Partie2.visitors;

import spoon.reflect.declaration.CtElement;
import spoon.reflect.visitor.CtScanner;

import java.io.File;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Scanner qui construit, pour chaque fichier source, un BitSet des lignes vues.
 * À la fin on peut récupérer le total de lignes uniques accessibles via getTotalLines().
 */
public class LineCountScanner extends CtScanner {

    // key = fichier absolu, value = bitset des lignes présentes dans ce fichier
    private final Map<String, BitSet> fileLineBits = new HashMap<>();

    @Override
    public void scan(CtElement element) {
        if (element != null && element.getPosition() != null && element.getPosition().isValidPosition()) {
            File file = element.getPosition().getFile();
            if (file != null) {
                String path = file.getAbsolutePath();
                int start = element.getPosition().getLine();      // start line (1-based)
                int end = element.getPosition().getEndLine();    // end line (1-based)

                if (start > 0 && end >= start) {
                    BitSet bits = fileLineBits.computeIfAbsent(path, p -> new BitSet(end + 1));
                    // BitSet.set(fromIndex, toIndex) uses [fromIndex, toIndex)
                    bits.set(start, end + 1);
                }
            }
        }

        // Poursuivre le scan récursif
        super.scan(element);
    }

    /** Nombre total de lignes uniques dans tous les fichiers scannés */
    public int getTotalLines() {
        int total = 0;
        for (BitSet bs : fileLineBits.values()) {
            total += bs.cardinality();
        }
        return total;
    }

    /** Optionnel : retourne la map fichier -> nombre de lignes (utile pour debug) */
    public Map<String, Integer> getLinesPerFile() {
        Map<String, Integer> res = new HashMap<>();
        for (Map.Entry<String, BitSet> e : fileLineBits.entrySet()) {
            res.put(e.getKey(), e.getValue().cardinality());
        }
        return res;
    }
}
