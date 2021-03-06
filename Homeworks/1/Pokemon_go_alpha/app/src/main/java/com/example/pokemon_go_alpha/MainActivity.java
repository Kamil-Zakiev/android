package com.example.pokemon_go_alpha;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
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

        resources = getApplicationContext().getResources();
        tilesViewIds = new int[]{R.id.ic1, R.id.ic2, R.id.ic3, R.id.ic4, R.id.ic5, R.id.ic6, R.id.ic7, R.id.ic8, R.id.ic9, R.id.ic10, R.id.ic11, R.id.ic12};
        tilesFactory = new TilesFactory(GetResourceIds());
        tilesDictionary = new TilesDictionary(tilesViewIds);

        ShowStartPage();
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

    private void ShowStartPage(){
        setContentView(R.layout.start_page);
        long bestMs = getPreferences(MODE_PRIVATE).getLong(resources.getString(R.string.fastest_result_key), Long.MAX_VALUE);
        if(bestMs != Long.MAX_VALUE){
            ((TextView)findViewById(R.id.recordView)).setText(getString(R.string.best_time_format, bestMs / 1000));
        }

        findViewById(R.id.startButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_main);

                PrepareNewGame();
                for (int viewId : tilesViewIds) {
                    AddTileClickHandler(viewId);
                }

            }
        });
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

        Toast toast = Toast.makeText(getApplicationContext(), R.string.win_massage, Toast.LENGTH_SHORT);
        toast.show();

        long gameDurationMs = gameEngine.GetGameDurationMs();
        long bestMs = getPreferences(MODE_PRIVATE).getLong(resources.getString(R.string.fastest_result_key), Long.MAX_VALUE);
        final boolean recordWasBeaten = bestMs > gameDurationMs;
        if(recordWasBeaten){
            getPreferences(MODE_PRIVATE).edit().putLong(resources.getString(R.string.fastest_result_key), gameDurationMs).apply();
            Toast toast2 = Toast.makeText(getApplicationContext(), getString(R.string.best_time_congrats_format, gameDurationMs / 1000), Toast.LENGTH_SHORT);
            toast2.show();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ShowStartPage();
            }
        }, resources.getInteger(R.integer.game_restart_delay_ms));
    }

    private boolean TryToOpenTile(final Tile tile) {
        gameEngine.Tap(tile);
        AnimateFlip(tile.GetId(), tile.GetResourceId());
        return gameEngine.IsOpened(tile);
    }

    private void CloseAllTiles(Tile[] tiles) {
        for (Tile tile : tiles) {
            CloseTile(tile);
        }
    }

    private void CloseTile(final Tile tile) {
        AnimateFlip(tile.GetId(), R.drawable.backball);
    }

    private DecelerateInterpolator DecelerateInterpolator = new DecelerateInterpolator();
    private AccelerateDecelerateInterpolator AccelerateDecelerateInterpolator = new AccelerateDecelerateInterpolator();

    private void AnimateFlip(final int tileViewId, final int recourceId){
        final ImageView tileView = findViewById(tileViewId);
        final ObjectAnimator oa1 = ObjectAnimator.ofFloat(tileView, "scaleY", 1f, 0f);
        final ObjectAnimator oa2 = ObjectAnimator.ofFloat(tileView, "scaleY", 0f, 1f);
        oa1.setInterpolator(DecelerateInterpolator);
        oa2.setInterpolator(AccelerateDecelerateInterpolator);
        int flipDurationMs = resources.getInteger(R.integer.flip_duration_ms);
        oa1.setDuration(flipDurationMs);
        oa2.setDuration(flipDurationMs);

        oa1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                tileView.setImageResource(recourceId);
                oa2.start();
            }
        });
        oa1.start();
    }
}
