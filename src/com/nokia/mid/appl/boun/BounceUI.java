// Decompiled with: FernFlower
// Class Version: 1
package com.nokia.mid.appl.boun;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.List;
import javax.microedition.rms.RecordStore;

public class BounceUI implements CommandListener {
    public static final String RMS_NAME = "bounceRMS";
    public static final int RMS_LEVEL_DATA_RECORD_ID = 1;
    public static final int RMS_SCORE_DATA_RECORD_ID = 2;
    private static final int STATE_GAME_PLAYING = 1;
    private static final int STATE_START = 2;
    private static final int STATE_GAME_OVER = 3;
    private static final int STATE_GAME_STARTING = 5;
    private static boolean RESTART_IS_REQUIRED = true;
    private static boolean RESTART_NOT_REQUIRED = false;
    private static final boolean OLD_HIGH_SCORE_STYLE = true;
    public Bounce mMidlet;
    public Display mDisplay;
    public BounceCanvas mCanvas;
    public int mState = 2;
    public int mBestLevel;
    public int mBestScore;
    public boolean mNewBestScore;
    public int mLastScore;
    private Command mOkayCmd;
    private Command mBackCmd;
    private Command mContinueCmd;
    private List mMainMenu;
    private Form mTextPage;
    private int mSavedMenuItem;
    private static int MAIN_MENU_CONTINUE = 0;
    private static int MAIN_MENU_NEW_GAME = 1;
    private static int MAIN_MENU_HIGH_SCORE = 2;
    private static int MAIN_MENU_INSTRUCTIONS = 3;
    private static int MAIN_MENU_COUNT = 4;
    private String[] mMainMenuItems = new String[MAIN_MENU_COUNT];

    public BounceUI(Bounce var1) {
        this.mMidlet = var1;
        this.getGameData();
        this.mCanvas = new BounceCanvas(this, 1);
        this.mCanvas.start();
        this.mDisplay = Display.getDisplay(this.mMidlet);
        this.mDisplay.setCurrent(this.mCanvas);
        this.mMainMenuItems[MAIN_MENU_CONTINUE] = Local.getText(8);
        this.mMainMenuItems[MAIN_MENU_NEW_GAME] = Local.getText(16);
        this.mMainMenuItems[MAIN_MENU_HIGH_SCORE] = Local.getText(12);
        this.mMainMenuItems[MAIN_MENU_INSTRUCTIONS] = Local.getText(13);
    }

    public void displayMainMenu() {
        this.mMainMenu = new List(Local.getText(10), 3);
        if (this.mBackCmd == null) {
            this.mBackCmd = new Command(Local.getText(6), 2, 1);
        }

        if (this.mState == 1 || this.mBestLevel > 1) {
            this.mMainMenu.append(this.mMainMenuItems[0], (Image)null);
        }

        for(int var1 = 1; var1 < this.mMainMenuItems.length; ++var1) {
            this.mMainMenu.append(this.mMainMenuItems[var1], (Image)null);
        }

        this.mMainMenu.addCommand(this.mBackCmd);
        this.mMainMenu.setCommandListener(this);
        if (this.mCanvas.mSplashIndex != -1) {
            this.mCanvas.mSplashIndex = -1;
            this.mCanvas.mSplashImage = null;
        }

        this.mMainMenu.setSelectedIndex(this.mSavedMenuItem, true);
        this.mCanvas.stop();
        this.mDisplay.setCurrent(this.mMainMenu);
    }

    public void displayGame(boolean var1, int var2) {
        this.mDisplay.setCurrent(this.mCanvas);
        if (var1) {
            this.mCanvas.resetGame(var2);
        }

        this.mCanvas.start();
        this.mState = 1;
    }

    public void displayHighScore() {
        this.mTextPage = new Form(Local.getText(12));
        this.mTextPage.append(String.valueOf(this.mBestScore));
        this.mTextPage.addCommand(this.mBackCmd);
        this.mTextPage.setCommandListener(this);
        this.mDisplay.setCurrent(this.mTextPage);
    }

    public void displayInstructions() {
        this.mTextPage = new Form(Local.getText(13));
        this.mTextPage.append(Local.getText(0));
        this.mTextPage.append(Local.getText(1));
        this.mTextPage.append(Local.getText(2));
        this.mTextPage.append(Local.getText(3));
        this.mTextPage.append(Local.getText(4));
        this.mTextPage.append(Local.getText(5));
        this.mTextPage.addCommand(this.mBackCmd);
        this.mTextPage.setCommandListener(this);
        this.mDisplay.setCurrent(this.mTextPage);
        this.mTextPage = null;
    }

    public void displayGameOver() {
        this.mState = 3;
        this.mCanvas.stop();
        if (this.mOkayCmd == null) {
            this.mOkayCmd = new Command(Local.getText(19), 4, 1);
        }

        this.mTextPage = new Form(Local.getText(11));
        this.mTextPage.append(Local.getText(11));
        this.mTextPage.append("\n\n");
        if (this.mNewBestScore) {
            this.mTextPage.append(Local.getText(17));
            this.mTextPage.append("\n\n");
        }

        this.mTextPage.append(String.valueOf(this.mLastScore));
        this.mTextPage.addCommand(this.mOkayCmd);
        this.mTextPage.setCommandListener(this);
        this.mDisplay.setCurrent(this.mTextPage);
        this.mTextPage = null;
    }

    public void displayLevelComplete() {
        this.mCanvas.stop();
        if (this.mContinueCmd == null) {
            this.mContinueCmd = new Command(Local.getText(8), 4, 1);
        }

        this.mTextPage = new Form("");
        this.mTextPage.append(this.mCanvas.mLevelCompletedStr);
        this.mTextPage.append("\n\n");
        this.mTextPage.append("" + this.mLastScore + "\n");
        this.mTextPage.addCommand(this.mContinueCmd);
        this.mTextPage.setCommandListener(this);
        this.mDisplay.setCurrent(this.mTextPage);
        this.mTextPage = null;
    }

    public void commandAction(Command var1, Displayable var2) {
        if (var1 == List.SELECT_COMMAND) {
            String var3 = this.mMainMenu.getString(this.mMainMenu.getSelectedIndex());
            this.mSavedMenuItem = this.mMainMenu.getSelectedIndex();
            if (var3.equals(this.mMainMenuItems[MAIN_MENU_CONTINUE])) {
                boolean var4 = RESTART_NOT_REQUIRED;
                int var5 = 0;
                if (this.mState != 1) {
                    var4 = RESTART_IS_REQUIRED;
                    if (this.mState == 3) {
                        var5 = this.mCanvas.mLevelNum;
                    } else {
                        var5 = this.mBestLevel;
                    }
                }

                this.displayGame(var4, var5);
            } else if (var3.equals(this.mMainMenuItems[MAIN_MENU_NEW_GAME])) {
                if (this.mState != 5) {
                    this.mState = 5;
                    this.mNewBestScore = false;
                    this.displayGame(RESTART_IS_REQUIRED, 1);
                }
            } else if (var3.equals(this.mMainMenuItems[MAIN_MENU_HIGH_SCORE])) {
                this.displayHighScore();
            } else if (var3.equals(this.mMainMenuItems[MAIN_MENU_INSTRUCTIONS])) {
                this.displayInstructions();
            }
        } else if (var1 != this.mBackCmd && var1 != this.mOkayCmd) {
            if (var1 == this.mContinueCmd) {
                this.displayGame(RESTART_NOT_REQUIRED, 0);
            }
        } else if (this.mDisplay.getCurrent() == this.mMainMenu) {
            this.mMidlet.destroyApp(true);
        } else {
            this.displayMainMenu();
        }

    }

    public void getGameData() {
        byte[] var1 = new byte[1];
        byte[] var2 = new byte[8];

        try {
            RecordStore var3 = RecordStore.openRecordStore("bounceRMS", true);
            if (var3.getNumRecords() != 2) {
                var3.addRecord(var1, 0, var1.length);
                var3.addRecord(var2, 0, var2.length);
            }

            var1 = var3.getRecord(1);
            var2 = var3.getRecord(2);
            this.mBestLevel = var1[0];

            for(int var4 = 0; var4 < 8; ++var4) {
                int var5 = 0;
                switch(var4) {
                case 0:
                    var5 = 10000000;
                    break;
                case 1:
                    var5 = 1000000;
                    break;
                case 2:
                    var5 = 100000;
                    break;
                case 3:
                    var5 = 10000;
                    break;
                case 4:
                    var5 = 1000;
                    break;
                case 5:
                    var5 = 100;
                    break;
                case 6:
                    var5 = 10;
                    break;
                case 7:
                    var5 = 1;
                }

                this.mBestScore += var2[var4] * var5;
            }
        } catch (Exception var6) {
        }

    }

    public void setGameData(int var1) {
        byte[] var2 = null;

        try {
            RecordStore var3 = RecordStore.openRecordStore("bounceRMS", true);
            if (var1 == 1) {
                var2 = new byte[]{(byte)this.mBestLevel};
            } else if (var1 == 2) {
                var2 = new byte[8];

                for(int var4 = 0; var4 < 8; ++var4) {
                    int var5 = 1;
                    switch(var4) {
                    case 0:
                        var5 = 10000000;
                        break;
                    case 1:
                        var5 = 1000000;
                        break;
                    case 2:
                        var5 = 100000;
                        break;
                    case 3:
                        var5 = 10000;
                        break;
                    case 4:
                        var5 = 1000;
                        break;
                    case 5:
                        var5 = 100;
                        break;
                    case 6:
                        var5 = 10;
                        break;
                    case 7:
                        var5 = 1;
                    }

                    var2[var4] = (byte)(this.mBestScore / var5 % 10);
                }
            }

            var3.setRecord(var1, var2, 0, var2.length);
        } catch (Exception var6) {
        }

    }
}
 