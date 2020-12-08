import java.awt.*;


public class Constant {
    public static final int FOOD_WIDTH = 15; // 食物的宽度和高度

    public static final int FOOD_HEIGHT = 15;

    public static final int GAME_WIDTH = FOOD_WIDTH*50;

    public static final int GAME_HEIGHT = FOOD_HEIGHT*35;

    public static final int JFRAME_WIDTH = FOOD_WIDTH*52;

    public static final int JFRAME_HEIGHT = FOOD_HEIGHT*38+6;

    public static final int L=1,R=2,U=3,D=4;

    public static final int SNAKE_NORMAL = 500;

    public static final int SNAKE_ADD = 180;

    public static final int SNAKE_SUPER = 50;

    public static final int SNAKE_SUB = 850;

    //游戏方格区域
    public static final Rectangle rec = new Rectangle(FOOD_WIDTH, FOOD_HEIGHT*2+6, GAME_WIDTH, GAME_HEIGHT);

}

