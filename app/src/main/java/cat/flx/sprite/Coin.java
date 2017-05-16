package cat.flx.sprite;

import android.widget.TextView;

class Coin extends Character {

    private static int[][] states = {
            { 41, 42, 43, 44, 45 }
    };
    int[][] getStates() { return states; }

    Coin(Game game) {
        super(game);
        padLeft = padTop = 0;
        colWidth = colHeight = 12;
        frame = (int)(Math.random() * 5);
    }

    void physics() {
    }
}
