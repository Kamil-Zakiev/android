package com.example.pokemon_go_alpha;

import java.util.HashMap;
import java.util.Map;

public final class TilesDictionary {
    private Map<Integer, Tile> _map;

    public TilesDictionary(int[] ids){
        _map = new HashMap<>();
        for (int id : ids) {
            _map.put(id, null);
        }
    }

    public void RebuildWith(Tile[] tiles){
        for (Tile tile : tiles) {
            _map.put(tile.GetId(), tile);
        }
    }

    public Tile GetTileById(int id){
        return _map.get(id);
    }
}
