package com.example.pokemon_go_alpha;

import org.junit.Test;

import java.security.InvalidAlgorithmParameterException;

import static org.junit.Assert.*;

public class GameEngineTests {

    @Test
    public void FirstTileIsOpened() {
        TilesFactory tilesFactory = GetTilesGenerator();
        Tile[] tiles = tilesFactory.CreateBasedOn(new int[]{1,2,3,4,5,6,7,8,9,10,11,12});
        GameEngine engine = new GameEngine(tiles);

        Tile firstTile = tiles[0];
        engine.Tap(firstTile);

        assertTrue(engine.IsOpened(firstTile));
    }

    @Test
    public void SecondSameTileTap_Opens_both_tiles() {
        TilesFactory tilesFactory = GetTilesGenerator();
        Tile[] tiles = tilesFactory.CreateBasedOn(new int[]{1,2,3,4,5,6,7,8,9,10,11,12});
        GameEngine engine = new GameEngine(tiles);

        Tile firstTile = tiles[0];
        engine.Tap(firstTile);

        Tile pair = null;
        int i = 1;
        while(pair == null && i < tiles.length){
            if(tiles[i].GetResourceId() == firstTile.GetResourceId()){
                pair = tiles[i];
            }
            i++;
        }

        engine.Tap(pair);

        assertTrue(engine.IsOpened(firstTile));
        assertTrue(engine.IsOpened(pair));
    }

    @Test
    public void SecondOtherTileTap_closes_both_tiles() throws InvalidAlgorithmParameterException {
        TilesFactory tilesFactory = GetTilesGenerator();
        Tile[] tiles = tilesFactory.CreateBasedOn(new int[]{1,2,3,4,5,6,7,8,9,10,11,12});
        GameEngine engine = new GameEngine(tiles);

        Tile firstTile = tiles[0];
        engine.Tap(firstTile);

        Tile other = null;
        int i = 2;
        while(other == null && i < tiles.length){
            if(tiles[i].GetResourceId() != firstTile.GetResourceId()){
                other = tiles[i];
            }
            i++;
        }

        engine.Tap(other);

        assertNotEquals(firstTile.GetResourceId(), other.GetResourceId());
        assertFalse(engine.IsOpened(firstTile));
        assertFalse(engine.IsOpened(other));
    }

    @Test
    public void ManyOpensOfTheSameDoesNothing() throws InvalidAlgorithmParameterException {
        TilesFactory tilesFactory = GetTilesGenerator();
        Tile[] tiles = tilesFactory.CreateBasedOn(new int[]{1,2,3,4,5,6,7,8,9,10,11,12});
        GameEngine engine = new GameEngine(tiles);

        Tile firstTile = tiles[0];
        engine.Tap(firstTile);

        assertTrue(engine.IsOpened(firstTile));

        engine.Tap(firstTile);
        assertTrue(engine.IsOpened(firstTile));
    }

    @Test
    public void Test1() {
        TilesFactory tilesFactory = GetTilesGenerator();
        Tile[] tiles = tilesFactory.CreateBasedOn(new int[]{1,2,3,4,5,6,7,8,9,10,11,12});
        GameEngine engine = new GameEngine(tiles);

        Tile firstTile = tiles[0];
        engine.Tap(firstTile);
        engine.Tap(firstTile);

        Tile other = null;
        int i = 2;
        while(other == null && i < tiles.length){
            if(tiles[i].GetResourceId() != firstTile.GetResourceId()){
                other = tiles[i];
            }
            i++;
        }

        engine.Tap(other);

        assertNotEquals(firstTile.GetResourceId(), other.GetResourceId());
        assertFalse(engine.IsOpened(firstTile));
        assertFalse(engine.IsOpened(other));
    }

    @Test
    public void Test2() {
        TilesFactory tilesFactory = GetTilesGenerator();
        Tile[] tiles = tilesFactory.CreateBasedOn(new int[]{1,2,3,4,5,6,7,8,9,10,11,12});
        GameEngine engine = new GameEngine(tiles);

        Tile firstTile = tiles[0];
        engine.Tap(firstTile);
        engine.Tap(firstTile);

        Tile other = null;
        int i = 2;
        while(other == null && i < tiles.length){
            if(tiles[i].GetResourceId() != firstTile.GetResourceId()){
                other = tiles[i];
            }
            i++;
        }

        engine.Tap(other);
        engine.Tap(other);

        assertNotEquals(firstTile.GetResourceId(), other.GetResourceId());
        assertFalse(engine.IsOpened(firstTile));
        assertTrue(engine.IsOpened(other));
    }

    private TilesFactory GetTilesGenerator() {
        int resourceCount = 100;
        int[] resourceIds = new int[resourceCount];
        for (int i = 0; i < resourceCount; i++) {
            resourceIds[i] = i + 1;
        }

        return new TilesFactory(resourceIds);
    }
}