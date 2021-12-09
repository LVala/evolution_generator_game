package evogen;

import java.util.Arrays;
import java.util.Random;

public class Genotype {
    private static final int LOWER_BOUND = 0;  // inclusive
    private static final int UPPER_BOUND = 8;  // exclusive
    private static final int GENES_LENGTH = 32;

    public static Genotype generateRandomGenotype() {
        // generate new Genotype with random genes, static method

        int[] new_genes = new int[GENES_LENGTH];
        for (int i = 0; i < GENES_LENGTH; i++) {
            new_genes[i] = new Random().nextInt(UPPER_BOUND) + LOWER_BOUND;
        }

        return new Genotype(new_genes);
    }


    private final int[] genes;

    public Genotype (int[] genes) {
        // check if there are 32 genes
        if (genes.length != GENES_LENGTH) throw new IllegalArgumentException("Invalid genes array: length is not 32");

        // check if all the genes are between 0 and 7
        for (int gene: genes) {
            if (!(gene >= LOWER_BOUND && gene < UPPER_BOUND))
                throw new IllegalArgumentException("Invalid genes arrays: gene not between 0 and 7");
        }
        Arrays.sort(genes);
        this.genes = genes;
    }

    public String toString() {
        return Arrays.toString(this.genes);
    }

    public int getRandomGene() {
        // get random gene from genes

        int rnd = new Random().nextInt(this.genes.length);
        return this.genes[rnd];
    }

    public int[] getPartOfGenotype(double splitPoint, boolean takeLeft) {
        // if takeLft is true, take the left part to the splitPoint inclusive, else right exclusive

        int sp = (int) (splitPoint * GENES_LENGTH);
        if (takeLeft) return Arrays.copyOfRange(this.genes, 0, sp);
        else return Arrays.copyOfRange(this.genes, sp, this.genes.length);
    }
}
