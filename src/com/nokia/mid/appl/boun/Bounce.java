// Decompiled with: FernFlower
// Class Version: 1
package com.nokia.mid.appl.boun;

import com.nokia.mid.ui.DeviceControl;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class Bounce extends MIDlet {
    private BounceUI mUI = new BounceUI(this);
    public static final boolean DEBUG = true;

    public void destroyApp(boolean var1) {
        this.mUI.mCanvas.stop();
        Display.getDisplay(this).setCurrent((Displayable)null);
        this.mUI = null;
        System.gc();
        this.notifyDestroyed();
    }

    protected void pauseApp() {
        this.mUI.mCanvas.stop();
        this.mUI.setGameData(1);
        this.mUI.setGameData(2);
        this.notifyPaused();
    }

    protected void startApp() throws MIDletStateChangeException {
        DeviceControl.setLights(0, 100);
    }
}
 