package zfourinarow;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 * @author Gabi
 */
public class Table extends GameObject 
{
    public static final int CELLSIZE = 50;
    private final int playerStroke = 5;
    private final int cellStroke = 3;
    public Player currentPlayer;
    public Player[][] table;
    
    public Table(int rows, int columns) 
    {
        initTable(rows, columns);
    }

    private void initTable(int r, int c) 
    {
        table = new Player[r][c];
        currentPlayer = Player.RED;
        for (int i = 0; i < table.length; i++) 
        {
            for (int j = 0; j < table[i].length; j++) 
            {
                table[i][j] = Player.NOONE;
            }            
        }
    }    

    @Override
    public void draw(Graphics g) 
    {
        for (int i = 0; i < table.length; i++) 
        {
            for (int j = 0; j < table[i].length; j++) 
            {
                Graphics2D g2 = (Graphics2D) g;
                if(table[i][j] == Player.RED) {
                    g2.setStroke(new BasicStroke(playerStroke));
                    g2.setColor(Color.red);
                    g2.drawRoundRect(i * CELLSIZE, j * CELLSIZE + 200, Table.CELLSIZE, Table.CELLSIZE, Table.CELLSIZE, Table.CELLSIZE);
                }
                else if(table[i][j] == Player.BLUE) {
                    g2.setStroke(new BasicStroke(playerStroke));
                    g2.setColor(Color.blue);
                    g2.drawLine(i * CELLSIZE, j * CELLSIZE + 200, i * CELLSIZE + CELLSIZE, j * CELLSIZE + 200 + CELLSIZE);
                    g2.drawLine(i * CELLSIZE + CELLSIZE, j * CELLSIZE + 200, i * CELLSIZE, j * CELLSIZE + 200 + CELLSIZE);
                }
                g2.setStroke(new BasicStroke(cellStroke));
                g2.setColor(Color.black);
                g2.drawRect(i * CELLSIZE, j * CELLSIZE + 200, CELLSIZE, CELLSIZE);                    
            }            
        }        
    }

    public int getRows() 
    {
        return table[0].length;
    }

    public int getColumns() 
    {
        return table.length;
    }

    int getEmptyCell(int j) 
    {
        if(table[j][0] != Player.NOONE) return -1;
        for (int i = 0; i < table[j].length - 1; i++) 
        {
            if(table[j][i] == Player.NOONE && table[j][i + 1] != Player.NOONE) 
            {
                return i;
            }
            if(i == table[j].length - 2) 
            {
                return table[j].length - 1;
            }
        }
        return -1;
    }
    
    public boolean checkMainDiagonal(Player player) {
        boolean won = false;
        for (int k = 0; k < table[0].length - 3; k++) {
            won = checkMainDiag(player, k, 0, table[0].length - k);
            if(won) return true;
        }
        for (int k = 1; k < table.length - 3; k++) {
            won = checkMainDiag(player, -k, k, getMin(k + table[0].length, table.length));
            if(won) return true;
        }
        return false;
    }

    private boolean checkMainDiag(Player player, int k, int start, int end) 
    {
        int counter = 0;
        for (int i = start; i < end; i++) {
            if(table[i][i + k] == player) counter++;
            else counter = 0;
            if(counter == 4) return true;
        }        
        return false;
    }
    
    public boolean checkSubDiagonal(Player player) {
        boolean won = false;
        int rows = table[0].length;
        int columns = table.length;
        int diff = columns - rows;
        for (int k = 0; k < diff - 1; k++) {
            won = checkSubDiag(player, columns - 1 - k, columns - 1, diff - 1 + k);
            if(won) return true;
        }
        for (int k = 1; k < table.length - 3; k++) {
            won = checkSubDiag(player, columns - 1 - k, columns - 1 - k, getMax(diff - 1 - k, -1));
            if(won) return true;
        }
        return false;
    }

    private boolean checkSubDiag(Player player, int k, int start, int end) 
    {
        int counter = 0;
        for (int i = start; i > end; i--) {
            if(table[i][i - k] == player) counter++;
            else counter = 0;
            if(counter == 4) return true;
            k -= 2;
        }        
        return false;
    }

    public boolean checkRows(Player player) {
        for (int i = 0; i < table[0].length; i++) {
            int counter = 0;
            for (int j = 0; j < table.length; j++) {
                if(table[j][i] == player)  counter++;
                else counter = 0;
                if(counter == 4) return true;
            }            
        }
        return false;
    }

    public boolean checkColumns(Player player) {
        for (int i = 0; i < table.length; i++) {
            int counter = 0;
            for (int j = 0; j < table[i].length; j++) {
                if(table[i][j] == player) counter++;             
                else counter = 0;
                if(counter == 4) return true;
            }            
        }
        return false;
    }
    
    private int getMin(int a, int b) 
    {
        if(a < b) return a;
        else return b;
    }

    private int getMax(int a, int b) 
    {
        if(a < b) return b;
        else return a;
    }
}
