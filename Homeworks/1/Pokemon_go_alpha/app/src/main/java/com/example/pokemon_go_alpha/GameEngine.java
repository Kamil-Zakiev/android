package com.example.pokemon_go_alpha;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class GameEngine {
    private Map<Integer, TileState> _tileStateMap;
    private Tile _orphanTile;

    public GameEngine(Tile[] tiles) {
        _tileStateMap = new HashMap<Integer, TileState>();
        for (int i = 0; i < tiles.length; i++) {
            Tile tile = tiles[i];
            _tileStateMap.put(tile.GetId(), TileState.CLOSED);
        }
    }

    public void Tap(Tile tile) {
        int tileId = tile.GetId();
        if (!_tileStateMap.containsKey(tileId)) {
            throw new IndexOutOfBoundsException();
        }

        if (_tileStateMap.get(tileId) == TileState.OPENED) {
            return;
        }

        if (_orphanTile == null) {
            _orphanTile = tile;
            _tileStateMap.put(tileId, TileState.OPENED);
            return;
        }

        if (/*_orphanTile != tile && */_orphanTile.GetResourceId() == tile.GetResourceId()) {
            _tileStateMap.put(_orphanTile.GetId(), TileState.OPENED);
            _tileStateMap.put(tileId, TileState.OPENED);
            _orphanTile = null;
            return;
        }

        _tileStateMap.put(_orphanTile.GetId(), TileState.CLOSED);
        _tileStateMap.put(tileId, TileState.CLOSED);
        _orphanTile = null;
    }

    public boolean IsOpened(Tile tile) {
        int tileId = tile.GetId();
        if (!_tileStateMap.containsKey(tileId)) {
            throw new IndexOutOfBoundsException();
        }

        return _tileStateMap.get(tileId) == TileState.OPENED;
    }

    public boolean IsGameOver(){
        Collection<TileState> tileStates = _tileStateMap.values();
        for (TileState tileState : tileStates) {
            if(tileState == TileState.CLOSED){
                return false;
            }
        }

        return true;
    }

    private enum TileState {
        OPENED,
        CLOSED
    }
}

