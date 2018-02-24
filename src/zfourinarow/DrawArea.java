package zfourinarow;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

class DrawArea extends JPanel
{
    private ArrayList<Disk> disks;
    private BufferedImage imgp1won;
    private BufferedImage imgp2won;
    private BufferedImage imgDraw;
    private BufferedImage imgp1;
    private BufferedImage imgp2;
    private TimerTask timerTask;
    private Timer timer;
    private Player currentPlayer;
    private Player winner;
    private Table table;
    private int columns;
    private int rows;
    private boolean hasStarted;
    private boolean draw;
    
    public DrawArea(int rows, int columns) { 
        initDrawArea(rows, columns);
    }
    
    public void drawing() 
    {
        repaint();
    }

    public void initDrawArea(int rows, int columns) 
    {
        this.rows = rows;
        this.columns = columns;
        this.hasStarted = false;
        this.disks = new ArrayList<Disk>();
        currentPlayer = Player.RED;
        table = new Table(rows, columns);
        timerTask = getTimerTask();
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(timerTask, 0, 16);
        addListener();               
        loadImages();
    }

    private void loadImages()
    {
        try {
            imgp1 = ImageIO.read(new File("./img/bgP1.png"));
            imgp2 = ImageIO.read(new File("./img/bgP2.png"));
            imgp1won = ImageIO.read(new File("./img/bgP1won.png"));
            imgp2won = ImageIO.read(new File("./img/bgP2won.png"));
            imgDraw = ImageIO.read(new File("./img/bgDraw.png"));
        } catch (IOException e) {
            System.err.println(e);
        }        
    }
    
    public void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        if(!hasStarted) 
        {
            if(winner == Player.RED) 
                g.drawImage(imgp1won,0, 0, new JLayeredPane());
            else if(winner == Player.BLUE) 
                g.drawImage(imgp2won,0, 0, new JLayeredPane());
            else if(draw) 
                g.drawImage(imgDraw,0, 0, new JLayeredPane()); 
            else 
                g.drawImage(imgp2 ,0, 0, new JLayeredPane()); 
        }
        else if(currentPlayer == Player.BLUE) 
            g.drawImage(imgp1,0, 0, new JLayeredPane());
        else if(currentPlayer == Player.RED) 
            g.drawImage(imgp2,0, 0, new JLayeredPane());
            
        for (Disk disk : disks) 
        {
            disk.draw(g);
        }
        table.draw(g);
    }

    private void addListener() 
    {
        this.addMouseListener(new MouseListener() 
        {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = Table.CELLSIZE * ((int)e.getX() / Table.CELLSIZE);
                int moveTo = calcEmtpyRow(col);
                if(moveTo < 0) return; 
                if(noDiskIsMoving()) 
                {
                    if(!hasStarted) hasStarted = true;
                    winner = Player.NOONE;
                    draw = false;
                    changePlayer();
                    disks.add(new Disk(col, -Table.CELLSIZE, Table.CELLSIZE, moveTo, table, currentPlayer));                    
                }
            }
            @Override
            public void mousePressed(MouseEvent e) { }
            @Override
            public void mouseReleased(MouseEvent e) { }
            @Override
            public void mouseEntered(MouseEvent e) { }
            @Override
            public void mouseExited(MouseEvent e) { }           
        });            
    }
    
    private boolean noDiskIsMoving() 
    {
        for (int i = 0; i < disks.size(); i++) 
        {
            if(disks.get(i).moving == true) 
            {
                return false;
            }
        }
        return true;
    }
    
    private int calcEmtpyRow(int x) 
    {
        int col = x / Table.CELLSIZE;
        int moveTo = table.getEmptyCell(col);
        return moveTo * Table.CELLSIZE;
    }
    
    private boolean isBoardFull() 
    {
        return table.getRows() * table.getColumns() == disks.size();
    }

    private void changePlayer() 
    {
        currentPlayer = (currentPlayer == Player.RED ? Player.BLUE : Player.RED);
        table.currentPlayer = currentPlayer;
    }

    private TimerTask getTimerTask() 
    {
        return new TimerTask() 
        {
            @Override
            public void run() {
                repaint();
                checkEnd();
            }
        };
    }

    private void checkEnd()
    {
        if(playerWon()) {
            winner = currentPlayer;
            reset();
        }
        if(isBoardFull()) 
        {
            draw = true;
            reset();
        }        
    }

    private boolean playerWon() 
    {
        return    table.checkMainDiagonal(currentPlayer) 
               || table.checkSubDiagonal (currentPlayer) 
               || table.checkRows        (currentPlayer) 
               || table.checkColumns     (currentPlayer);
    }
    
    private void reset() 
    {
        initDrawArea(rows, columns);
    }
}