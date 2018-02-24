package zfourinarow;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

class GameFrame extends MyFrame 
{
    private DrawArea drawArea;
    private int rows;
    private int columns;
    
    public GameFrame(int rows, int columns) 
    {
        setSize((rows + 1) * Table.CELLSIZE - Table.CELLSIZE + 7, 
                (columns + 1) * Table.CELLSIZE + 207);
        drawArea = new DrawArea(rows, columns);
        add(drawArea);        
        initFrame(rows, columns);
    }    

    @Override
    protected void initFrame(int rows, int columns) {
        setTitle("Four In A Row: Game " + rows + "x" + columns);
        addWindowListenerExit();
        setIcon("icon.png");
        setResizable(false);
        setMenuBar();
        setVisible(true);
        this.columns = columns;        
        this.rows = rows;
    }
       
    @Override
    protected void setIcon(String img)
    {
        java.net.URL url = MenuFrame.class.getResource(img);
        setIconImage(Toolkit.getDefaultToolkit().getImage(url));        
    }    
    
    @Override
    void setMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        JMenuItem reset = new JMenuItem(getAbstractActionReset());
        JMenuItem quit = new JMenuItem(MyFrame.getAbstractActionExit());
        reset.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.ALT_MASK));
        quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.ALT_MASK));
        reset.setMnemonic('R');
        quit.setMnemonic('Q');
        reset.setText("Reset");
        quit.setText("Quit");
        menu.setMnemonic('F');
        menu.add(reset);
        menu.add(quit);
        menuBar.add(menu);
        setJMenuBar(menuBar);
    }
    
    private AbstractAction getAbstractActionReset()
    {
        return new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e) {
                drawArea.initDrawArea(rows, columns);
            }
        };        
    }
    
    private void addWindowListenerExit() 
    {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) 
            {
                doUponExit();
            }
        });        
    }
    
    @Override
    void centerFrameLoc() {
        setLocation((int)Math.floor(MyFrame.getScreenWorkingWidth() / 2 - (getFrameWidth() / 2)), 
                    (int)Math.floor(MyFrame.getScreenWorkingHeight() / 2 - (getFrameHeight() / 2)));
    }
    
    private int getFrameWidth()
    {
        return columns * Table.CELLSIZE;
    }

    private int getFrameHeight()
    {
        return rows * Table.CELLSIZE;
    }

    @Override
    protected void doUponExit() 
    {
        this.dispose();
    }
}
