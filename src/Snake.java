import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Snake {
    private SNode snakeHead; // 蛇头结点
    private int startx, starty; // 蛇头的坐标
    private List<SNode> nodes = new ArrayList<SNode>(); // 整条蛇，由SNode结点组成:
    // 线性表List
    private int dir; // 方位
    private boolean isLive = true; // 蛇处于运行状态

    //给蛇添加运行速度,每一次运行走3个像素
    private int speed = 3;

    // getter.setter蛇的方向
    public int getDir() {
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    // 传入蛇所在的界面，通过界面获得当前的食物对象
    public Snake(SnakeUI ui) {
        this.dir = Constant.U;
        this.noOverride(ui.getFood());
    }

    // 随机产生蛇头的坐标与食物不重复
    public void noOverride(SNode food) {
        this.generateSnake();
        while (this.hit(food)) { // 蛇与食物重叠
            this.generateSnake();
        }
    }

    private void generateSnake() {
        int x = SNode.generate(50); // [0,49)
        int y = SNode.generate(35);
        if (x == 0) {
            x += 1;
        } else if (x == 49) {
            x -= 1;
        }
        if (y == 0) {
            y += 1;
        } else if (y == 34) {
            y -= 1;
        }
        this.startx = ((int) Constant.rec.getX() + x * Constant.FOOD_WIDTH);
        this.starty = ((int) Constant.rec.getY() + y * Constant.FOOD_HEIGHT);
        this.snakeHead = new SNode(this.startx, this.starty, Color.GREEN);
        nodes.add(snakeHead);
        this.addNode(); // 默认是2个结点，需再添加1个结点
    }

    // 判定蛇是否与食物是否重叠:true:重叠 ,false：不重叠
    public boolean hit(SNode food) {
        boolean result = false;
        for (SNode snode : nodes) {
            boolean res = snode.getRec().intersects(food.getRec());
            if (res) {
                result = true;
                break;
            }
        }
        return result;
    }

    // 吃食物
    public boolean eat(SNode food) {
        boolean result = false;
        for (SNode snode : nodes) {
            boolean res = snode.getRec().intersects(food.getRec());
            if (res) {
                result = true;
                if (nodes.size() >= 2) { // 1.初始相遇，2.在移动中相遇
                    this.addNode(); // 蛇的身体增长1
                }
                break;
            }
        }
        return result;
    }

    // 给蛇的尾部添加一个结点，与蛇方位有关
    private void addNode() {
        int size = nodes.size();
        switch (dir) {
            case Constant.L: // 向左走,则右边添加第2个结点，且没有碰壁
                if (size == 1) { // 蛇静止，需要添加一个结点到蛇尾
                    int x = this.startx + Constant.FOOD_WIDTH;
                    int y = this.starty;
                    SNode snode = new SNode(x, y, Color.GREEN);
                    nodes.add(snode); // 添加到集合末尾
                } else {
                    int x = this.startx - Constant.FOOD_WIDTH;
                    int y = this.starty;
                    SNode snode = new SNode(x, y, Color.GREEN);
                    nodes.add(0, snode);
                    this.snakeHead = snode;
                    this.startx = x;
                    this.starty = y;
                    // 判断蛇头左越界
                    if (this.startx < Constant.rec.getX()) {
                        this.isLive = false;
                    }
                }
                break;
            case Constant.R:
                if (size == 1) { // 蛇静止，需要添加一个结点到蛇尾
                    int x = this.startx - Constant.FOOD_WIDTH;
                    int y = this.starty;
                    SNode snode = new SNode(x, y, Color.GREEN);
                    nodes.add(snode); // 添加到集合末尾
                } else {
                    int x = this.startx + Constant.FOOD_WIDTH;
                    int y = this.starty;
                    SNode snode = new SNode(x, y, Color.GREEN);
                    nodes.add(0, snode);
                    this.snakeHead = snode;
                    this.startx = x;
                    this.starty = y;
                    //判断蛇头右越界
                    if (this.startx > Constant.GAME_WIDTH) {
                        this.isLive = false;
                    }
                }
                break;
            case Constant.U:
                if (size == 1) { // 蛇静止，需要添加一个结点到蛇尾
                    int x = this.startx;
                    int y = this.starty + Constant.FOOD_HEIGHT;
                    SNode snode = new SNode(x, y, Color.GREEN);
                    nodes.add(snode); // 添加到集合末尾
                } else {
                    int x = this.startx;
                    int y = this.starty - Constant.FOOD_HEIGHT;
                    SNode snode = new SNode(x, y, Color.GREEN);
                    nodes.add(0, snode);
                    this.snakeHead = snode;
                    this.startx = x;
                    this.starty = y;
                    // 判断蛇头上越界
                    if (this.starty < Constant.rec.getY()) {
                        this.isLive = false;
                    }
                }
                break;
            case Constant.D:
                if (size == 1) { // 蛇静止，需要添加一个结点到蛇尾
                    int x = this.startx;
                    int y = this.starty - Constant.FOOD_HEIGHT;
                    SNode snode = new SNode(x, y, Color.GREEN);
                    nodes.add(snode); // 添加到集合末尾
                } else {
                    int x = this.startx;
                    int y = this.starty + Constant.FOOD_HEIGHT;
//				int y = this.starty + this.speed;
                    SNode snode = new SNode(x, y, Color.GREEN);
                    nodes.add(0, snode);
                    this.snakeHead = snode;
                    this.startx = x;
                    this.starty = y;
                    //判断蛇头下越界
                    if (this.starty > ((int) Constant.rec.getY()+ Constant.GAME_HEIGHT- Constant.FOOD_HEIGHT)) {
                        this.isLive = false;
                    }
                }
                break;
        }

    }

    public boolean isLive() {
        return isLive;
    }

    // 蛇的移动，不断变换蛇头，删除蛇尾
    private void move() {
        this.addNode(); // 再添加一个新结点，删除末尾结点
        int len = nodes.size();
        nodes.remove(len - 1);
    }

    // 画蛇及蛇的移动
    public void paint(Graphics2D g2d) {
        // 对nodes结点依次绘制
        g2d.setColor(this.snakeHead.getColor());

        // 对蛇的身体进行绘制
        for (SNode snode : nodes) {
            g2d.fillRect(snode.getX(), snode.getY(), Constant.FOOD_WIDTH,
                    Constant.FOOD_HEIGHT);
        }
        this.move();
    }
}

