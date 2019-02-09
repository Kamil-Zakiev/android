package com.example.pokemon_go_alpha;

import org.junit.Test;

import java.util.Arrays;
import java.security.InvalidAlgorithmParameterException;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class TilesFactoryTests {
    @Test
    public void Tiles_generated_correctly() throws InvalidAlgorithmParameterException {
        int resourceCount = 6;
        int[] resourceIds = new int[resourceCount];
        for (int i = 0; i < resourceCount; i++) {
            resourceIds[i] = i + 1;
        }

        TilesFactory tilesFactory = new TilesFactory(resourceIds);

        int tilesCount = resourceCount * 2;
        Tile[] tiles = tilesFactory.CreateBasedOn(new int[]{1,2,3,4,5,6,7,8,9,10,11,12});

        int[] numbers = new int[tilesCount];
        int[] usedResourceIds = new int[tilesCount];
        for (int i = 0; i < tiles.length; i++) {
            Tile tile = tiles[i];
            numbers[i] = tile.GetId();
            usedResourceIds[i] = tile.GetResourceId();
        }

        Arrays.sort(numbers);
        Arrays.sort(usedResourceIds);


        // check ordered numbers. Expected: 1, 2, 3, ...
        assertEquals(1, numbers[0]);
        for (int i = 0; i < tiles.length - 1; i++) {
            assertEquals(1, numbers[i + 1] - numbers[i]);
        }

        // check resource pairs. Expected: 1, 1, 2, 2, 3, 3, 4, 4, 5, 5
        assertEquals(1, usedResourceIds[0]);
        for (int i = 0; i < tiles.length - 1; i++) {
            int diff = usedResourceIds[i + 1] - usedResourceIds[i];
            if (i % 2 == 0) {
                assertEquals(0, diff);
            } else {
                assertEquals(1, diff);
            }
        }
    }

    @Test
    public void Tiles_generated_randomly() throws InvalidAlgorithmParameterException {
        int resourceCount = 6;
        int[] resourceIds = new int[resourceCount];
        for (int i = 0; i < resourceCount; i++) {
            resourceIds[i] = i + 1;
        }

        TilesFactory tilesFactory = new TilesFactory(resourceIds);

        Tile[] firstTiles = tilesFactory.CreateBasedOn(new int[]{1,2,3,4,5,6,7,8,9,10,11,12});
        Tile[] secondTiles = tilesFactory.CreateBasedOn(new int[]{1,2,3,4,5,6,7,8,9,10,11,12});

        assertNotEquals(firstTiles[0].GetId(), secondTiles[0].GetId());
        assertNotEquals(firstTiles[0].GetResourceId(), secondTiles[0].GetResourceId());
    }
}