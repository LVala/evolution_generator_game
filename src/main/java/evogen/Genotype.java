package evogen;

import java.util.Arrays;
import java.util.Random;

public class Genotype {
    private int[] genes;

    public Genotype (int[] genes) {
        // check if there are 32 genes
        if (genes.length != 32) throw new IllegalArgumentException("Invalid genes array: length is not 32");
        // check if all the genes are between 0 and 7
        for (int gene: genes) {
            if (!(gene >= 0 && gene <= 7)) throw new IllegalArgumentException("Invalid genes arrays: gene not between 0 and 7");
        }

        this.genes = genes;
    }

    public int getRandomGene() {
        // get random gene from genes
        int rnd = new Random().nextInt(this.genes.length);
        return this.genes[rnd];
    }

    public int[] getPartOfGenotype(int splitpoint, boolean takeright) {
        //if takeright is true, take right part to splitpoint inclusive, else left
        if (takeright) return Arrays.copyOfRange(this.genes, 0, splitpoint);
        else return Arrays.copyOfRange(this.genes, splitpoint, this.genes.length);
    }
}
