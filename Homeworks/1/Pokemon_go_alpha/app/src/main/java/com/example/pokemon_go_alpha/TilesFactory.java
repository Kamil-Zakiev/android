package com.example.pokemon_go_alpha;

import java.security.InvalidAlgorithmParameterException;
import java.util.Random;

public final class TilesFactory {
    private int[] resourcesIds;
    private Random random;

    public TilesFactory(int[] resourcesIds) {
        this.resourcesIds = resourcesIds;
        this.random = new Random();
    }

    public Tile[] CreateBasedOn(int[] tilesIds) {
        int[] idsCopy = tilesIds.clone();
        int tilesCount = idsCopy.length;
        if (tilesCount > resourcesIds.length * 2) {
            // throw new InvalidAlgorithmParameterException("");
        }

        Shuffle(idsCopy);
        Shuffle(this.resourcesIds);
        Tile[] tiles = new Tile[tilesCount];
        for (int i = 0; i < tilesCount; i++) {
            tiles[i] = new Tile(idsCopy[i], this.resourcesIds[i / 2]);
        }

        return tiles;
    }

    private void Shuffle(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            int k = GetRandom(i + 1, array.length - 1);
            int b = array[k];
            array[k] = array[i];
            array[i] = b;
        }
    }

    private int GetRandom(int min, int max) {
        return min + (Math.abs(random.nextInt()) % (max - min + 1));
    }
}
