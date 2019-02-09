package com.example.pokemon_go_alpha;

import android.content.res.Resources;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    private TilesFactory tilesFactory;
    private GameEngine gameEngine;
    private TilesDictionary tilesDictionary;
    private Tile lastTappedTile;
    private int[] tilesViewIds;
    private Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resources = getApplicationContext().getResources();
        tilesViewIds = new int[]{R.id.ic1, R.id.ic2, R.id.ic3, R.id.ic4, R.id.ic5, R.id.ic6,
                R.id.ic7, R.id.ic8, R.id.ic9, R.id.ic10, R.id.ic11, R.id.ic12};

        tilesFactory = new TilesFactory(GetResourceIds());
        tilesDictionary = new TilesDictionary(tilesViewIds);
        PrepareNewGame();

        FreezeTilesSize();
        for (int viewId : tilesViewIds) {
            AddTileClickHandler(viewId);
        }
    }

    private int[] GetResourceIds() {
        LinkedList<Integer> resourceIdsList = new LinkedList<>();
        Field[] fields = R.drawable.class.getFields();
        for (Field field : fields) {
            try {
                if (!field.getName().startsWith(resources.getString(R.string.pokemon_file_prefix))) {
                    continue;
                }
                resourceIdsList.push(field.getInt(null));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        int[] resourceIds = new int[resourceIdsList.size()];
        for (int i = 0; i < resourceIds.length; i++) {
            resourceIds[i] = resourceIdsList.get(i);
        }

        return resourceIds;
    }

    private void FreezeTilesSize() {
        int sizePerSquare = getResources().getDisplayMetrics().widthPixels / 3;
        for (int viewId : tilesViewIds) {
            ViewGroup.LayoutParams layoutParams = findViewById(viewId).getLayoutParams();
            layoutParams.width = sizePerSquare;
            layoutParams.height = sizePerSquare;
        }
    }

    private void PrepareNewGame() {
        Tile[] tiles = tilesFactory.CreateBasedOn(tilesViewIds);
        CloseAllTiles(tiles);
        tilesDictionary.RebuildWith(tiles);
        gameEngine = new GameEngine(tiles);
    }

    private boolean WaitForHandlerToCloseTiles = false;

    private void AddTileClickHandler(final int viewId) {
        findViewById(viewId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (WaitForHandlerToCloseTiles) {
                    return;
                }

                final Tile tappedTile = tilesDictionary.GetTileById(viewId);
                if(gameEngine.IsOpened(tappedTile)){
                    return;
                }

                boolean wasOpened = TryToOpenTile(tappedTile);
                if (!wasOpened) {
                    WaitForHandlerToCloseTiles = true;
                    final int notPairTileId = lastTappedTile.GetId();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            CloseTile(tappedTile);
                            CloseTile(tilesDictionary.GetTileById(notPairTileId));
                            WaitForHandlerToCloseTiles = false;
                        }
                    }, resources.getInteger(R.integer.not_matching_tiles_close_delay_ms));
                }
                lastTappedTile = tappedTile;

                HandleGameEnding();
            }
        });
    }

    private void HandleGameEnding() {
        if (!gameEngine.IsGameOver()) {
            return;
        }

        Toast toast = Toast.makeText(getApplicationContext(), R.string.win_massage, Toast.LENGTH_LONG);
        toast.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                PrepareNewGame();
            }
        }, resources.getInteger(R.integer.game_restart_delay_ms));
    }

    private boolean TryToOpenTile(Tile tile) {
        gameEngine.Tap(tile);
        ((ImageView) findViewById(tile.GetId())).setImageResource(tile.GetResourceId());
        return gameEngine.IsOpened(tile);
    }

    private void CloseAllTiles(Tile[] tiles) {
        for (Tile tile : tiles) {
            CloseTile(tile);
        }
    }

    private void CloseTile(Tile tile) {
        ((ImageView) findViewById(tile.GetId())).setImageResource(R.drawable.background);
    }
}
