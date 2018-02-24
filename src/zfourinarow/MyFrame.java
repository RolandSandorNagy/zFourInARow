package zfourinarow;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JFrame;

abstract class MyFrame extends JFrame {
    abstract void initFrame(int r, int c); 
    abstract void setIcon(String img);
    abstract void centerFrameLoc(); 
    abstract void setMenuBar();
    abstract void doUponExit();

    public static int getScreenWorkingWidth() 
    {
        return java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
    }

    public static int getScreenWorkingHeight() 
    {
        return java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
    }    

    public static AbstractAction getAbstractActionNewGame(int r, int c) 
    {
        return new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GameFrame(r,c);
            }
        };
    }

    public static AbstractAction getAbstractActionExit() 
    {
        return new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        };
    }
    
}
