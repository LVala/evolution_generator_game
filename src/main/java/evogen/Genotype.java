package evogen;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

public class Genotype {
    private static final int LOWER_BOUND = 0;  // inclusive
    private static final int UPPER_BOUND = 8;  // exclusive
    private static final int GENES_LENGTH = 32;

    public static Genotype generateRandomGenotype() {
        int[] new_genes = new int[GENES_LENGTH];
        for (int i = 0; i < GENES_LENGTH; i++) {
            new_genes[i] = new Random().nextInt(UPPER_BOUND) + LOWER_BOUND;
        }

        return new Genotype(new_genes);
    }


    private final int[] genes;

    public Genotype (int[] genes) {
        if (genes.length != GENES_LENGTH) throw new IllegalArgumentException("Invalid genes array: length is not 32");

        for (int gene: genes) {
            if (!(gene >= LOWER_BOUND && gene < UPPER_BOUND))
                throw new IllegalArgumentException("Invalid genes arrays: gene not between 0 and 7");
        }
        Arrays.sort(genes);
        this.genes = genes;
    }

    @Override
    public String toString() {
        return Arrays.stream(this.genes).mapToObj(String::valueOf).collect(Collectors.joining(""));
    }

    public int getRandomGene() {
        int rnd = new Random().nextInt(this.genes.length);
        return this.genes[rnd];
    }

    public int[] getPartOfGenotype(double splitPoint, boolean takeLeft) {
        // if takeLft is true, take the left part to the splitPoint inclusive, else right exclusive

        int sp = (int) (splitPoint * GENES_LENGTH);
        if (takeLeft) return Arrays.copyOfRange(this.genes, 0, sp);
        else return Arrays.copyOfRange(this.genes, sp, this.genes.length);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Genotype)) return false;
        for (int i = 0; i < GENES_LENGTH; i++) {
            if (this.genes[i] != ((Genotype) other).genes[i]) return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.genes);
    }
}
