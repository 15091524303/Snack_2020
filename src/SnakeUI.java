import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class SnakeUI extends JFrame implements KeyListener {
    private BufferedImage uiImg = new BufferedImage(Constant.JFRAME_WIDTH,
            Constant.JFRAME_HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
    private Rectangle rec;
    private SNode food;
    private Snake snake; // 定义蛇对象
    private static int SPEED = Constant.SNAKE_NORMAL;

    public SnakeUI() {
        this.rec = Constant.rec;
        this.food = new SNode(Color.BLUE);
        this.snake = new Snake(this);// 初始化蛇对象
        this.launchFrame();
    }

    // 获得当前界面的食物
    public SNode getFood() {
        return food;
    }

    private void launchFrame() {
        this.setTitle("贪吃蛇V0.1");
        this.setBounds(200, 100, Constant.JFRAME_WIDTH, Constant.JFRAME_HEIGHT);
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        new Thread(new UIUpdate()).start(); // 启动更新线程
        this.addKeyListener(this); // 监听键盘事件
    }

    public void paint(Graphics g) {
        // 1.直接画入图形。g.drawRect画入闪烁效果[不可取]

        // 2.将图形画入到缓存图片，再讲图片画入到Frame中，避免闪烁效果
        // 设置矩形的颜色，并绘入到uiImg图片，将此图片直接写入到内存
        Graphics2D g2d = (Graphics2D) uiImg.getGraphics();
        // 在缓存图片画白色背景
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());

        // 设置边框颜色
        g2d.setColor(Color.BLACK);
        g2d.drawRect((int) this.rec.getX(), (int) this.rec.getY(),
                (int) this.rec.getWidth(), (int) this.rec.getHeight());

        // 设置中间方格
        g2d.setColor(Color.CYAN);
        int startx = (int) this.rec.getX();
        int starty = (int) this.rec.getY();
        for (int i = 0; i < 35; i++) {
            for (int j = 0; j < 50; j++) {
                g2d.drawRect(startx + j * Constant.FOOD_WIDTH, starty + i
                                * Constant.FOOD_HEIGHT, Constant.FOOD_WIDTH,
                        Constant.FOOD_HEIGHT);
            }
        }

        // 设置版本文字
        g2d.setColor(Color.RED);
        g2d.setFont(new Font("宋体", Font.ITALIC, 16));// 设置字体
        g2d.drawString("版本文字   2019", 580, 530);

        // 画食物,食物paint方法在SNode类定义，在UI界面中的paint方法被调用
        this.food.paint(g2d);

        // 画蛇
        this.snake.paint(g2d);

        // 蛇吃食物绘制:蛇已经在吃食物,食物被吃完后重新定义一个食物对象，并与蛇的身体做重叠判断
        if (this.snake.eat(this.food)) {
            this.food = new SNode(Color.BLUE);
            while (this.snake.hit(this.food)) {
                this.food = new SNode(Color.BLUE);
            }
        }
        g.drawImage(uiImg, 0, 0, null); // 写入到内存
    }

    public static void main(String[] args) {
        new SnakeUI();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    // 按压键盘主要目的更改蛇运动方向
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_LEFT:
                snake.setDir(Constant.L);
                System.out.println("dddl");
                break;
            case KeyEvent.VK_RIGHT:
                snake.setDir(Constant.R);
                System.out.println("dddr");
                break;
            case KeyEvent.VK_UP:
                snake.setDir(Constant.U);
                System.out.println("dddu");
                break;
            case KeyEvent.VK_DOWN:
                snake.setDir(Constant.D);
                break;
            case KeyEvent.VK_W:
                SPEED = Constant.SNAKE_ADD;
                break;
            case KeyEvent.VK_D:
                SPEED = Constant.SNAKE_NORMAL;
                break;
            case KeyEvent.VK_S:
                SPEED = Constant.SNAKE_SUB;
                break;
            case KeyEvent.VK_T:
                SPEED = Constant.SNAKE_SUPER;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        SPEED = Constant.SNAKE_NORMAL;
    }

    // 不断刷新界面的线程
    class UIUpdate implements Runnable {
        @Override
        public void run() {
            while (SnakeUI.this.snake.isLive()) {
                System.out.println("..............thread....");
                SnakeUI.this.repaint();
                // 为了减缓更新的速度，设定每一次绘制之后停顿1秒。
                try {
                    Thread.sleep(SPEED);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            JOptionPane.showMessageDialog(SnakeUI.this, "游戏越界，game over.");
        }
    }
}

