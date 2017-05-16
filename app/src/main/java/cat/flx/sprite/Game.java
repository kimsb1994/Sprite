package cat.flx.sprite;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

class Game {
    private Context context;

    private BitmapSet bitmapSet;
    private Scene scene;
    private Bonk bonk;
    private Audio audio;
    private List<Coin> coins;
    private List<Enemy> enemies;
    private List<Volador> voladors;
    private int screenOffsetX, screenOffsetY;
    private int puntacion2;
    private TextView puntacion;
    private Boolean direccion = true;


    public Game(Activity activity,TextView puntacion) {
        this.context = activity;
        puntacion.setText("0");

        this.puntacion = puntacion;
        bitmapSet = new BitmapSet(context.getResources());
        audio = new Audio(activity);
        scene = new Scene(this);
        bonk = new Bonk(this);
        Volador volador = new Volador(this);
        volador.x = 30 * 10;
        volador.y = 150;
        Volador volador1 = new Volador(this);
        volador1.x = 60 * 10;
        volador1.y = 20 * 6;
        Volador volador2 = new Volador(this);
        volador2.x = 40 * 10;
        volador2.y = 20 * 9;
        coins = new ArrayList<>();
        enemies = new ArrayList<>();
        voladors = new ArrayList<>();
        voladors.add(volador);
        voladors.add(volador1);
        voladors.add(volador2);
        scene.loadFromFile(R.raw.scene);
        bonk.x = 16 * 10;
        bonk.y = 0;
    }
    Context getContext() { return context; }
    Resources getResources() { return context.getResources(); }

    BitmapSet getBitmapSet() { return bitmapSet; }
    Scene getScene() { return scene; }
    Audio getAudio() { return audio; }
    Bonk getBonk() { return bonk; }

    void addCoin(Coin coin) {
        coins.add(coin);
    }
    List<Coin> getCoins() { return coins; }

    void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }
    List<Enemy> getEnemies() { return enemies; }

    void addVolador(Volador volador) {
        voladors.add(volador);
    }
    List<Volador> getVoladors() { return voladors; }



    void physics() {
        bonk.physics();
        for(Enemy enemy : enemies) {
            enemy.physics();
            if (bonk.state == 3) continue;
            if (enemy.getCollisionRect().intersect(bonk.getCollisionRect())) {
                if (bonk.vy > 0) {
                    enemy.x = -1000;
                    enemy.y = -1000;
                }
                else {
                    audio.die();
                    bonk.state = 3;
                }
            }
        }
        for(Volador volador : voladors) {
            volador.physics();
            if (bonk.state == 3) continue;
            if (volador.getCollisionRect().intersect(bonk.getCollisionRect())) {
                if (bonk.vy > 0) {
                    volador.x = -1000;
                    volador.y = -1000;
                }
                else {
                    audio.die();
                    bonk.state = 3;
                }
            }
        }
        for(Coin coin : coins) {
            coin.physics();
            if (coin.getCollisionRect().intersect(bonk.getCollisionRect())) {

                puntacion2 = puntacion2 + 1;
                puntacion.setText("" + puntacion2);

                audio.coin();
                coin.x = -1000;
                coin.y = -1000;
            }
        }
    }



    private float sc;
    private int scX, scY;

    void draw(Canvas canvas) {
        if (canvas.getWidth() == 0) return;
        if (sc == 0) {
            scY = 16 * 16;
            sc = canvas.getHeight() / (float) scY;
            scX = (int) (canvas.getWidth() / sc);
        }
        screenOffsetX = Math.min(screenOffsetX, bonk.x - 100);
        screenOffsetX = Math.max(screenOffsetX, bonk.x - scX + 100);
        screenOffsetX = Math.max(screenOffsetX, 0);
        screenOffsetX = Math.min(screenOffsetX, scene.getWidth() - scX - 1);
        screenOffsetY = Math.min(screenOffsetY, bonk.y - 50);
        screenOffsetY = Math.max(screenOffsetY, bonk.y - scY + 75);
        screenOffsetY = Math.max(screenOffsetY, 0);
        screenOffsetY = Math.min(screenOffsetY, scene.getHeight() - scY);
        canvas.scale(sc, sc);
        canvas.translate(-screenOffsetX, -screenOffsetY);
        scene.draw(canvas);
        bonk.draw(canvas);
        for(Enemy enemy : enemies) {
            enemy.draw(canvas);
        }

        for(Volador volador : voladors) {
            volador.draw(canvas);
        }
        for(Coin coin : coins) {
            coin.draw(canvas);
        }
    }


        private int keyCounter = 0;
    private boolean keyLeft, keyRight, keyJump;
    void keyLeft(boolean down) { keyCounter = 0; if (down) keyLeft = true; }
    void keyRight(boolean down) { keyCounter = 0; if (down) keyRight = true; }
    void keyJump(boolean down) { keyCounter = 0; if (down) keyJump = true; }

    private boolean left, right, jump;
    void left(boolean down) {
        if (left && !down) left = false;
        else if (!left && down) left = true;
    }
    void right(boolean down) {
        if (right && !down) right = false;
        else if (!right && down) right = true;
    }
    void jump(boolean down) {
        if (jump && !down) jump = false;
        else if (!jump && down) jump = true;
    }

    void events() {
        if (++keyCounter > 2) {
            keyCounter = 0;
            keyLeft = keyRight = keyJump = false;
        }
        if (keyLeft || left) { bonk.left(); }
        if (keyRight || right) { bonk.right(); }
        if (keyJump || jump) { bonk.jump(); }
    }
}
