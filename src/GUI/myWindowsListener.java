package GUI;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import static Kernel.Kernel.save;
import static Kernel.Kernel.unFamiliar;

/**
 * Created by Bill on 2016/4/26.
 */
public class myWindowsListener implements WindowListener {

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        unFamiliar.display();
        save();
        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
