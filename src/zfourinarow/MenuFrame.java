package zfourinarow;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

public class MenuFrame extends MyFrame
{
    private JButton button_8x5;
    private JButton button_10x6;
    private JButton button_12x7;    
    private JLabel label;    
    private int width;
    private int height;        
    
    public MenuFrame(int width, int height) 
    {
        setSize(width, height);
        initFrame(width, height);
    }
    
    @Override
    protected void initFrame(int w, int h) 
    {
        addWindowListener(getWindowAdapterConfirmExit());
        setLayout(new FlowLayout());
        setLabel("Choose Your Board");
        setTitle("Four In A Row");
        setIcon("icon.png");
        centerFrameLoc();
        setMenuBar();
        setButtons();
        setResizable(false);
        setVisible(true);        
        width  = w;
        height = h;
    }
    
    private void setLabel(String labelTxt) 
    {
        label  = new JLabel(labelTxt);
        add(label);
    }

    @Override
    protected void setIcon(String img) 
    {
        java.net.URL url = MenuFrame.class.getResource(img);
        setIconImage(Toolkit.getDefaultToolkit().getImage(url));
    }
    
    @Override
    protected void centerFrameLoc() 
    {
        setLocation((int)Math.floor(MyFrame.getScreenWorkingWidth() / 2 - (width / 2)), 
                    (int)Math.floor(MyFrame.getScreenWorkingHeight() / 2 - (height / 2)));
    }
        
    private void setButtons() 
    {
        button_12x7 = new JButton(MyFrame.getAbstractActionNewGame(12, 7));    
        button_10x6 = new JButton(MyFrame.getAbstractActionNewGame(10, 6));        
        button_8x5  = new JButton(MyFrame.getAbstractActionNewGame(8, 5));
        button_12x7.setSize( new Dimension(100,40));
        button_10x6.setSize( new Dimension(100,40));
        button_8x5.setSize(  new Dimension(100,40));
        button_12x7.setText("12 x 7"); 
        button_10x6.setText("10 x 6");
        button_8x5.setText (" 8 x 5");
        add(button_8x5);
        add(button_10x6);
        add(button_12x7);        
    }
    
    @Override
    void setMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        JMenuItem game_12x7 = new JMenuItem(MyFrame.getAbstractActionNewGame(12,7));
        JMenuItem game_10x6 = new JMenuItem(MyFrame.getAbstractActionNewGame(10,6));
        JMenuItem game_8x5  = new JMenuItem(MyFrame.getAbstractActionNewGame(8,5));
        JMenuItem quit      = new JMenuItem(MyFrame.getAbstractActionExit());
        game_12x7.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, KeyEvent.ALT_MASK));
        game_10x6.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, KeyEvent.ALT_MASK));
        game_8x5.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, KeyEvent.ALT_MASK));
        quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.ALT_MASK));
        game_12x7.setText("12x7");
        game_10x6.setText("10x6");
        game_8x5.setText (" 8x5");
        quit.setText     ("Quit");
        game_12x7.setMnemonic('3');
        game_10x6.setMnemonic('2');
        game_8x5.setMnemonic ('1');
        quit.setMnemonic     ('Q');
        menu.setMnemonic     ('F');
        menu.add(game_12x7);
        menu.add(game_10x6);
        menu.add(game_8x5);
        menu.add(quit);
        menuBar.add(menu);
        setJMenuBar(menuBar);
    }

    private WindowAdapter getWindowAdapterConfirmExit()
    {
        return new WindowAdapter() 
        {
            @Override
            public void windowClosing(WindowEvent e) 
            {
                showExitConfirmation();
            }
        };
    }    
    
    private void showExitConfirmation() 
    {
        int n =  JOptionPane.showConfirmDialog(this, 
                 "Are You Sure?",
                 "Exit Game.", 
                 JOptionPane.YES_NO_OPTION);
        if (n == JOptionPane.YES_OPTION) 
        {
            doUponExit();
        } 
    }        
    
    @Override
    protected void doUponExit()
    {
            System.exit(0);        
    }
}

