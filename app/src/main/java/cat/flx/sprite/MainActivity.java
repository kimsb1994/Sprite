package cat.flx.sprite;

        import android.content.pm.ActivityInfo;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.view.KeyEvent;
        import android.view.View;
        import android.view.Window;
        import android.view.WindowManager;
        import android.widget.ImageButton;
        import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        setContentView(R.layout.activity_main);
        final GameView gameView = (GameView) findViewById(R.id.view);
        final ImageButton play = (ImageButton) findViewById(R.id.buttonPlay);
        final ImageButton pausar = (ImageButton) findViewById(R.id.pausar);
        final TextView puntacion = (TextView) findViewById(R.id.puntuacion);
        game = new Game(this,puntacion);
        gameView.setGame(game);
        gameView.setVisibility(View.INVISIBLE);
        pausar.setVisibility(View.INVISIBLE);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                play.setVisibility(View.INVISIBLE);
                gameView.setVisibility(View.VISIBLE);
                pausar.setVisibility(View.VISIBLE);
                game.getAudio().startMusic();

            }
        });
        pausar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                play.setVisibility(View.VISIBLE);
                gameView.setVisibility(View.INVISIBLE);
                pausar.setVisibility(View.INVISIBLE);
                game.getAudio().stopMusic();

            }
        });
    }

    @Override public boolean dispatchKeyEvent(KeyEvent event) {
        boolean down = (event.getAction() == KeyEvent.ACTION_DOWN);
        switch(event.getKeyCode()) {
            case KeyEvent.KEYCODE_Z:
                game.keyLeft(down); break;
            case KeyEvent.KEYCODE_X:
                game.keyRight(down); break;
            case KeyEvent.KEYCODE_M:
                game.keyJump(down); break;
        }
        return true;
    }

}
