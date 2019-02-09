package com.example.pokemon_go_alpha;

public class Tile {
    private int _id;
    private int _resourceId;

    public Tile(int id, int resourceId) {
        _id = id;
        _resourceId = resourceId;
    }

    public int GetId() {
        return _id;
    }

    public int GetResourceId() {
        return _resourceId;
    }
}
