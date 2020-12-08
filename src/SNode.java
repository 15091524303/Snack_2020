import java.awt.*;

//食物类的定义：
public class SNode {
    private int x,y;     //食物的起始坐标，与游戏的起始点关联
    private Color color; //食物的颜色
    private Rectangle rec; //食物的矩形区域

    public Color getColor() {
        return color;
    }

    public int getX() { //读取数据
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Rectangle getRec() {
        return rec;
    }

    public void setRec(Rectangle rec) {
        this.rec = rec;
    }

    //随机产生某个位置的食物
    public SNode(Color color) {
        this.x = (int) Constant.rec.getX()+this.generate(50)* Constant.FOOD_WIDTH;
        this.y = (int) Constant.rec.getY()+this.generate(35)* Constant.FOOD_HEIGHT;
        this.rec = new Rectangle(this.x, this.y, Constant.FOOD_WIDTH, Constant.FOOD_HEIGHT);
        this.color = color;
    }

    //生成固定位置的结点，用于产生蛇头
    public SNode(int x,int y,Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.rec = new Rectangle(this.x, this.y, Constant.FOOD_WIDTH, Constant.FOOD_HEIGHT);
    }

    //随机产生数字
    public static int generate(int number) { //[0,numbder)
        int x = 0;
        x = (int)(Math.random()*number);
        return x;
    }

    //定义画食物的方法：基于图形缓存画法Graphics2D
    public void paint(Graphics2D g2d) {
        g2d.setColor(this.color);
        g2d.fillRect(this.x, this.y, Constant.FOOD_WIDTH, Constant.FOOD_HEIGHT);
    }

}
