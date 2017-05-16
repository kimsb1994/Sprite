package cat.flx.sprite;

public class Volador extends Character {
    private static int[][] states = {
            { 66 ,66 ,66 ,67 ,67 ,67 }
    };
    int[][] getStates() { return states; }

    int x1, x2, dir;
    boolean right;
    int min, max;
    int direccion = 0;

    Volador(Game game) {
        super(game);
        padLeft = 0;
        padTop = 0;
        colWidth = 60;
        colHeight = 40;
        dir = 0;
        padLeft = 0;
        padTop = 0;
    }

    void physics() {
            if(y<200 && direccion==0) {
                y++;
                if(y==199){
                    direccion = 1;
                }
            }else{
                y--;
                if(y==50){
                    direccion = 0;
                }
            }
    }
}
