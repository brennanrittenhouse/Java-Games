import java.awt.event.MouseEvent;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.MouseListener;
import javax.swing.JFrame;

// 
// Decompiled by Procyon v0.5.36
// 

public class Board extends JFrame implements MouseListener
{
    public static final int X_DIM = 30;
    public static final int Y_DIM = 30;
    public static final int X_OFFSET = 30;
    public static final int Y_OFFSET = 50;
    public static final int MESSAGE_HEIGHT = 80;
    public static final int FONT_SIZE = 14;
    private static final int NUM_LINES = 100;
    public static final Color GRID_COLOR_A;
    public static final Color GRID_COLOR_B;
    public static final Color YELLOW;
    public static final Color BLUE;
    public static final Color CYAN;
    public static final Color GREEN;
    public static final Color PINK;
    public static final Color BLACK;
    public static final Color WHITE;
    public static final Color RED;
    public static final Color ORANGE;
    private Color[][] grid;
    private Coordinate lastClick;
    private String message;
    private int numLines;
    private int[][] line;
    private int columns;
    private int rows;
    
    public Board(final int rows, final int columns) {
        this.message = "";
        this.numLines = 0;
        this.line = new int[4][100];
        this.columns = columns;
        this.setSize(60 + 30 * columns, 100 + 30 * (this.rows = rows) + 80);
        this.setTitle("Board game");
        this.setResizable(false);
        this.grid = new Color[columns][rows];
        for (int i = 0; i < columns; ++i) {
            for (int j = 0; j < rows; ++j) {
                this.grid[i][j] = this.invisible(i, j);
            }
        }
        this.addMouseListener(this);
        this.setDefaultCloseOperation(3);
        this.setVisible(true);
    }
    
    public Board(final int n) {
        this(1, n);
    }
    
    private void paintText(final Graphics graphics) {
        graphics.setColor(Color.BLACK);
        graphics.drawRect(30, 50, 30 * this.grid.length, 30 * this.grid[0].length);
        graphics.setFont(new Font(graphics.getFont().getFontName(), 3, 14));
        graphics.clearRect(30, 50 + 30 * this.grid[0].length + 1, 30 + 30 * this.grid.length, 80);
        graphics.drawString(this.message, 30, 100 + 30 * this.grid[0].length);
    }
    
    private void paintGrid(final Graphics graphics) {
        for (int i = 0; i < this.grid.length; ++i) {
            for (int j = 0; j < this.grid[i].length; ++j) {
                if ((i % 2 == 0 && j % 2 != 0) || (i % 2 != 0 && j % 2 == 0)) {
                    graphics.setColor(Board.GRID_COLOR_A);
                }
                else {
                    graphics.setColor(Board.GRID_COLOR_B);
                }
                graphics.fillRect(30 + 30 * i, 50 + 30 * j, 30, 30);
                final Color color = this.grid[i][j];
                if (color != this.invisible(i, j)) {
                    graphics.setColor(color);
                    graphics.fillOval(30 + 30 * i + 7, 50 + 30 * j + 7, 15, 15);
                }
            }
        }
    }
    
    private void drawLine(final Graphics graphics) {
        for (int i = 0; i < this.numLines; ++i) {
            ((Graphics2D)graphics).setStroke(new BasicStroke(5.0f));
            graphics.drawLine(45 + this.line[0][i] * 30, 65 + this.line[1][i] * 30, 45 + this.line[2][i] * 30, 65 + this.line[3][i] * 30);
            ((Graphics2D)graphics).setStroke(new BasicStroke(0.5f));
        }
    }
    
    @Override
    public void paint(final Graphics graphics) {
        this.paintGrid(graphics);
        this.paintText(graphics);
        this.drawLine(graphics);
    }
    
    private Color invisible(final int n, final int n2) {
        if ((n % 2 == 0 && n2 % 2 != 0) || (n % 2 != 0 && n2 % 2 == 0)) {
            return Board.GRID_COLOR_A;
        }
        return Board.GRID_COLOR_B;
    }
    
    public void displayMessage(final String message) {
        this.message = message;
        this.repaint();
    }
    
    public void putPeg(final Color color, final int n, final int n2) {
        this.grid[n2][n] = color;
        this.repaint();
    }
    
    public void putPeg(final Color color, final int n) {
        this.grid[n][0] = color;
        this.repaint();
    }
    
    public void removePeg(final int n, final int n2) {
        this.grid[n2][n] = this.invisible(n2, n);
        this.repaint();
    }
    
    public void removePeg(final int n) {
        this.grid[n][0] = this.invisible(n, 0);
        this.repaint();
    }
    
    public void drawLine(final int n, final int n2, final int n3, final int n4) {
        this.line[0][this.numLines] = n2;
        this.line[1][this.numLines] = n;
        this.line[2][this.numLines] = n4;
        this.line[3][this.numLines] = n3;
        ++this.numLines;
        this.repaint();
    }
    
    public void removeLine(final int n, final int n2, final int n3, final int n4) {
        for (int i = 0; i < this.numLines; ++i) {
            if (this.line[0][i] == n2 && this.line[1][i] == n && this.line[2][i] == n4 && this.line[3][i] == n3) {
                --this.numLines;
                this.line[0][i] = this.line[0][this.numLines];
                this.line[1][i] = this.line[1][this.numLines];
                this.line[2][i] = this.line[2][this.numLines];
                this.line[3][i] = this.line[3][this.numLines];
                --i;
            }
        }
        this.repaint();
    }
    
    public Coordinate getClick() {
        Coordinate coordinate = null;
        synchronized (this) {
            this.lastClick = null;
            while (this.lastClick == null) {
                try {
                    this.wait();
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                    System.exit(-1);
                }
            }
            coordinate = new Coordinate((int)Math.floor((this.lastClick.getRow() - 50) / 30), (int)Math.floor((this.lastClick.getCol() - 30) / 30));
        }
        return coordinate;
    }
    
    public int getPosition() {
        return this.getClick().getCol();
    }
    
    public void mouseClicked(final MouseEvent mouseEvent) {
        final int n = (int)mouseEvent.getPoint().getX();
        final int n2 = (int)mouseEvent.getPoint().getY();
        synchronized (this) {
            if (n >= 30 && n <= 30 + this.columns * 30 - 1 && n2 >= 50 && n2 <= 50 + this.rows * 30 - 1) {
                this.lastClick = new Coordinate((int)mouseEvent.getPoint().getY(), (int)mouseEvent.getPoint().getX());
                this.notifyAll();
            }
        }
    }
    
    public int getColumns() {
        return this.grid.length;
    }
    
    public int getRows() {
        return this.grid[0].length;
    }
    
    public void mouseEntered(final MouseEvent mouseEvent) {
    }
    
    public void mouseExited(final MouseEvent mouseEvent) {
    }
    
    public void mousePressed(final MouseEvent mouseEvent) {
    }
    
    public void mouseReleased(final MouseEvent mouseEvent) {
    }
    
    public void mouseMoved(final MouseEvent mouseEvent) {
    }
    
    public void mouseDragged(final MouseEvent mouseEvent) {
    }
    
    public static void main(final String[] array) {
        final Board board = new Board(10, 10);
        while (true) {
            board.displayMessage("Test");
            board.drawLine(0, 0, 9, 0);
            final Coordinate click = board.getClick();
            System.out.println("Clicked at row " + click.getRow() + ", column " + click.getCol());
        }
    }
    
    static {
        GRID_COLOR_A = new Color(84, 137, 139);
        GRID_COLOR_B = new Color(103, 156, 158);
        YELLOW = Color.YELLOW;
        BLUE = Color.BLUE;
        CYAN = Color.CYAN;
        GREEN = Color.GREEN;
        PINK = Color.PINK;
        BLACK = Color.BLACK;
        WHITE = Color.WHITE;
        RED = Color.RED;
        ORANGE = Color.ORANGE;
    }
}
