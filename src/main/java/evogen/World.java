package evogen;

import java.util.Arrays;

public class World {
    public static void main(String[] args) {

        Genotype ng = new Genotype(new int[]{0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1});
        System.out.println(Arrays.toString(ng.getPartOfGenotype(8, true)));

    }
}
