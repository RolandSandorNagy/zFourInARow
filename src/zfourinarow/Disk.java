package zfourinarow;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

class Disk extends GameObject
{
    private boolean bouncing;
    private boolean bounced;
    public boolean moving;
    private Player player;
    private Table table;
    private int stroke = 5;
    private int moveTo;
    private int x;
    private int y;
    private int r;
    
    public Disk(int x, int y, int r, int moveTo, Table table, Player player) 
    {
        this.bounced = false;
        this.bouncing = true;
        this.moving = true;
        this.moveTo = moveTo;
        this.player = player;
        this.table = table;
        this.x = x;
        this.y = y;
        this.r = r;
    }
    
    @Override
    public void draw(Graphics g) 
    {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(stroke));
        if(bouncing) move(moveTo);
        else 
        {
            table.table[this.x / Table.CELLSIZE][this.y / Table.CELLSIZE] = player;
            moving = false;
        }
        Color color = player == Player.RED ? Color.red : Color.blue;
        g2.setColor(color);
        if(player == Player.RED) 
        {
            g2.drawRoundRect(this.x, this.y + 200, Table.CELLSIZE, Table.CELLSIZE, Table.CELLSIZE, Table.CELLSIZE);        
        }
        else 
        {
            g2.drawLine(this.x, this.y + 200, this.x + Table.CELLSIZE, this.y + Table.CELLSIZE + 200);
            g2.drawLine(this.x + Table.CELLSIZE, this.y + 200, this.x, this.y + Table.CELLSIZE + 200);
        }
        
    }    

    private void move(int maxY) 
    {        
        if(this.y < maxY && this.bounced == false) 
        {
            this.y += 10;            
        } 
        else if(this.y < maxY && this.bounced == true) 
        {            
            this.y += 5;            
        }
        else if(this.bounced != true) 
        {
            this.y = maxY-10;
            this.bounced = true;
        } else 
        {
            bouncing = false;
        }

    }
}
