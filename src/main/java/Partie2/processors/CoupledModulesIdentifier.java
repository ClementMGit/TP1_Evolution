package Partie2.processors;

import spoon.reflect.CtModel;
import spoon.reflect.declaration.CtType;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Identifie les modules fortement couplés et construit un dendrogramme hiérarchique multi-branches.
 */
public class CoupledModulesIdentifier {

    private final CtModel model;
    private final double CP; // seuil de couplage minimum

    public static class ClusterNode {
        public final String name; // nom du cluster ou de la classe
        public final Set<String> classes; // noms complets
        public final List<ClusterNode> children;
        public final double coupling;

        public ClusterNode(String name, Set<String> classes, List<ClusterNode> children, double coupling) {
            this.name = name;
            this.classes = classes;
            this.children = children;
            this.coupling = coupling;
        }

        public boolean isLeaf() {
            return children == null || children.isEmpty();
        }
    }

    public CoupledModulesIdentifier(CtModel model, double CP) {
        this.model = model;
        this.CP = CP;
    }

    /**
     * Construit le dendrogramme multi-branches à partir des noms complets.
     */
    public ClusterNode buildDendrogram() {
        CouplingProcessor processor = new CouplingProcessor(model);
        Map<String, Double> couplingMap = processor.computeNormalizedCoupling();

        // Initialiser chaque classe comme une feuille avec nom complet
        List<ClusterNode> clusters = model.getAllTypes().stream()
                .map(CtType::getQualifiedName)
                .map(name -> new ClusterNode(name,
                        new HashSet<>(Collections.singletonList(name)),
                        Collections.emptyList(),
                        1.0))
                .collect(Collectors.toList());

        int id = 1;
        boolean mergedAny;

        do {
            mergedAny = false;
            List<int[]> toMerge = new ArrayList<>();

            for (int i = 0; i < clusters.size(); i++) {
                for (int j = i + 1; j < clusters.size(); j++) {
                    double cpl = computeAverageCoupling(clusters.get(i).classes, clusters.get(j).classes, couplingMap);
                    if (cpl >= CP) {
                        toMerge.add(new int[]{i, j});
                    }
                }
            }

            if (!toMerge.isEmpty()) {
                mergedAny = true;
                Set<Integer> mergedIndices = new HashSet<>();
                List<ClusterNode> newClusters = new ArrayList<>();

                for (int[] pair : toMerge) {
                    int i = pair[0], j = pair[1];
                    if (mergedIndices.contains(i) || mergedIndices.contains(j)) continue;

                    ClusterNode c1 = clusters.get(i);
                    ClusterNode c2 = clusters.get(j);

                    Set<String> mergedClasses = new HashSet<>(c1.classes);
                    mergedClasses.addAll(c2.classes);
                    List<ClusterNode> children = new ArrayList<>();
                    if (!c1.isLeaf() || !c1.classes.isEmpty()) children.add(c1);
                    if (!c2.isLeaf() || !c2.classes.isEmpty()) children.add(c2);

                    double avgCoupling = computeAverageCoupling(mergedClasses, mergedClasses, couplingMap);

                    ClusterNode merged = new ClusterNode("Cluster_" + (id++), mergedClasses, children, avgCoupling);
                    newClusters.add(merged);
                    mergedIndices.add(i);
                    mergedIndices.add(j);
                }

                // Ajouter les clusters non fusionnés
                for (int k = 0; k < clusters.size(); k++) {
                    if (!mergedIndices.contains(k)) {
                        newClusters.add(clusters.get(k));
                    }
                }

                clusters = newClusters;
            }

        } while (mergedAny && clusters.size() > 1);

        // Si plusieurs clusters restants, créer une racine neutre
        if (clusters.size() == 1) return clusters.get(0);

        Set<String> allClasses = clusters.stream()
                .flatMap(c -> c.classes.stream())
                .collect(Collectors.toSet());

        return new ClusterNode("Root", allClasses, clusters, 0.0);
    }
    private double computeAverageCoupling(Set<String> c1, Set<String> c2, Map<String, Double> map) {
        double sum = 0;
        int count = 0;
        for (String a : c1) {
            for (String b : c2) {
                Double ab = map.get(a + "↔" + b);
                Double ba = map.get(b + "↔" + a);
                if (ab != null) { sum += ab; count++; }
                if (ba != null) { sum += ba; count++; }
            }
        }
        return count == 0 ? 0 : sum / count;
    }
}
