// Decompiled with: FernFlower
// Class Version: 1
package com.nokia.mid.appl.boun;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.List;
import javax.microedition.rms.RecordStore;

public class BounceUI implements CommandListener {
    public Bounce mMidlet;
    public Display mDisplay;
    public BounceCanvas mCanvas;
    public int mState = 2;
    public int mBestLevel;
    public int mBestScore;
    public boolean mNewBestScore;
    public int mLastScore;
    public byte mSavedValid = -16;
    public byte mSavedLives;
    public byte mSavedRings;
    public byte mSavedLevel;
    public byte mSavedSize;
    public int mSavedScore;
    public int mSavedTileX;
    public int mSavedTileY;
    public int mSavedGlobalBallX;
    public int mSavedGlobalBallY;
    public int mSavedXSpeed;
    public int mSavedYSpeed;
    public int mSavedXPos;
    public int mSavedYPos;
    public int mSavedRespawnX;
    public int mSavedRespawnY;
    public int mSavedSpeedBonus;
    public int mSavedGravBonus;
    public int mSavedJumpBonus;
    public int mSavedTileCount;
    public int[][] mSavedTiles;
    public int mSavedSpikeCount;
    public short[][] mSavedSpikeOffset;
    public short[][] mSavedSpikeDirection;
    private Command mOkayCmd;
    private Command mBackCmd;
    private Command mContinueCmd;
    private List mMainMenu;
    private List mNewGameMenu;
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
        this.loadGameData();
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

        if (this.mState == 1 || this.mSavedValid != -16) {
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

        if (this.mState != 1 && this.mSavedValid == -16) {
            this.mMainMenu.setSelectedIndex(this.mSavedMenuItem, true);
        } else {
            this.mMainMenu.setSelectedIndex(MAIN_MENU_CONTINUE, true);
        }

        this.mCanvas.stop();
        this.mDisplay.setCurrent(this.mMainMenu);
    }

    public void displayNewGameMenu() {
        String[] var1 = new String[this.mBestLevel];
        String[] var2 = new String[1];

        for(int var3 = 0; var3 < this.mBestLevel; ++var3) {
            var2[0] = String.valueOf(var3 + 1);
            var1[var3] = Local.getText(14, var2);
        }

        this.mNewGameMenu = new List(Local.getText(16), 3, var1, (Image[])null);
        this.mNewGameMenu.addCommand(this.mBackCmd);
        this.mNewGameMenu.setCommandListener(this);
        this.mDisplay.setCurrent(this.mNewGameMenu);
    }

    public void displayGame(boolean var1, int var2) {
        this.mDisplay.setCurrent(this.mCanvas);
        if (var1) {
            this.mCanvas.resetGame(var2, 0, 3);
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

    public void displayGameOver(boolean var1) {
        this.mCanvas.stop();
        if (this.mOkayCmd == null) {
            this.mOkayCmd = new Command(Local.getText(19), 4, 1);
        }

        this.mTextPage = new Form(Local.getText(11));
        if (var1) {
            this.mTextPage.append(Local.getText(7));
        } else {
            this.mTextPage.append(Local.getText(11));
        }

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
            if (var2 == this.mNewGameMenu) {
                this.displayGame(true, this.mNewGameMenu.getSelectedIndex() + 1);
            } else {
                String var3 = this.mMainMenu.getString(this.mMainMenu.getSelectedIndex());
                this.mSavedMenuItem = this.mMainMenu.getSelectedIndex();
                if (var3.equals(this.mMainMenuItems[MAIN_MENU_CONTINUE])) {
                    if (this.mState == 1) {
                        this.displayGame(false, this.mCanvas.mLevelNum);
                    } else if (this.mSavedValid != -16) {
                        this.mDisplay.setCurrent(this.mCanvas);
                        if (this.mSavedValid == -6) {
                            this.mCanvas.resetGame(this.mSavedGlobalBallX + (this.mSavedSize >> 1), this.mSavedGlobalBallY + (this.mSavedSize >> 1));
                        } else {
                            this.mCanvas.resetGame(this.mSavedLevel, this.mSavedScore, this.mSavedLives);
                        }

                        this.mSavedTiles = null;
                        this.mCanvas.start();
                        this.mState = 1;
                    }
                } else if (var3.equals(this.mMainMenuItems[MAIN_MENU_NEW_GAME])) {
                    if (this.mState != 4) {
                        if (this.mBestLevel > 1) {
                            this.displayNewGameMenu();
                        } else {
                            this.mState = 4;
                            this.mNewBestScore = false;
                            this.displayGame(true, 1);
                        }
                    }
                } else if (var3.equals(this.mMainMenuItems[MAIN_MENU_HIGH_SCORE])) {
                    this.displayHighScore();
                } else if (var3.equals(this.mMainMenuItems[MAIN_MENU_INSTRUCTIONS])) {
                    this.displayInstructions();
                }
            }
        } else if (var1 != this.mBackCmd && var1 != this.mOkayCmd) {
            if (var1 == this.mContinueCmd) {
                this.displayGame(false, 0);
            }
        } else if (this.mDisplay.getCurrent() == this.mMainMenu) {
            this.mMidlet.destroyApp(true);
            this.mMidlet.notifyDestroyed();
        } else {
            this.displayMainMenu();
        }

    }

    public void loadGameData() {
        byte[] var1 = new byte[1];
        byte[] var2 = new byte[4];
        byte[] var3 = new byte[255];

        try {
            RecordStore var4 = RecordStore.openRecordStore("bounceRMS", true);
            if (var4.getNumRecords() != 3) {
                var4.addRecord(var1, 0, var1.length);
                var4.addRecord(var2, 0, var2.length);
                var4.addRecord(var3, 0, var3.length);
            } else {
                var1 = var4.getRecord(1);
                var2 = var4.getRecord(2);
                var3 = var4.getRecord(3);
                ByteArrayInputStream var5 = new ByteArrayInputStream(var1);
                DataInputStream var6 = new DataInputStream(var5);
                this.mBestLevel = var6.readByte();
                var5 = new ByteArrayInputStream(var2);
                var6 = new DataInputStream(var5);
                this.mBestScore = var6.readInt();
                var5 = new ByteArrayInputStream(var3);
                var6 = new DataInputStream(var5);
                this.mSavedValid = var6.readByte();
                this.mSavedLives = var6.readByte();
                this.mSavedRings = var6.readByte();
                this.mSavedLevel = var6.readByte();
                this.mSavedSize = var6.readByte();
                this.mSavedScore = var6.readInt();
                this.mSavedTileX = var6.readInt();
                this.mSavedTileY = var6.readInt();
                this.mSavedGlobalBallX = var6.readInt();
                this.mSavedGlobalBallY = var6.readInt();
                this.mSavedXSpeed = var6.readInt();
                this.mSavedYSpeed = var6.readInt();
                this.mSavedXPos = var6.readInt();
                this.mSavedYPos = var6.readInt();
                this.mSavedRespawnX = var6.readInt();
                this.mSavedRespawnY = var6.readInt();
                this.mSavedSpeedBonus = var6.readInt();
                this.mSavedGravBonus = var6.readInt();
                this.mSavedJumpBonus = var6.readInt();
                this.mSavedTileCount = var6.readByte();
                this.mSavedTiles = new int[this.mSavedTileCount][3];

                for(int var7 = 0; var7 < this.mSavedTileCount; ++var7) {
                    this.mSavedTiles[var7][0] = var6.readShort();
                    this.mSavedTiles[var7][1] = var6.readShort();
                    this.mSavedTiles[var7][2] = var6.readByte();
                }

                this.mSavedSpikeCount = var6.readByte();
                this.mSavedSpikeOffset = new short[this.mSavedSpikeCount][2];
                this.mSavedSpikeDirection = new short[this.mSavedSpikeCount][2];

                for(int var8 = 0; var8 < this.mSavedSpikeCount; ++var8) {
                    this.mSavedSpikeOffset[var8][0] = var6.readShort();
                    this.mSavedSpikeOffset[var8][1] = var6.readShort();
                    this.mSavedSpikeDirection[var8][0] = var6.readShort();
                    this.mSavedSpikeDirection[var8][1] = var6.readShort();
                }
            }
        } catch (Exception var9) {
        }

    }

    public void saveGameData(int var1) {
        ByteArrayOutputStream var2 = new ByteArrayOutputStream();
        DataOutputStream var3 = new DataOutputStream(var2);

        try {
            switch(var1) {
            case 1:
                var3.writeByte(this.mBestLevel);
                break;
            case 2:
                var3.writeInt(this.mBestScore);
                break;
            case 3:
                if (this.mDisplay.getCurrent() == this.mCanvas && this.mCanvas.mGameTimer != null) {
                    var3.writeByte(-6);
                } else if (this.mCanvas != null && this.mCanvas.numLives < 0) {
                    var3.writeByte(-16);
                } else {
                    var3.writeByte(-70);
                }

                var3.writeByte(this.mCanvas.numLives);
                var3.writeByte(this.mCanvas.numRings);
                var3.writeByte(this.mCanvas.mLevelNum);
                var3.writeByte(this.mCanvas.mBall.mBallSize);
                var3.writeInt(this.mCanvas.mScore);
                var3.writeInt(this.mCanvas.tileX);
                var3.writeInt(this.mCanvas.tileY);
                var3.writeInt(this.mCanvas.mBall.globalBallX);
                var3.writeInt(this.mCanvas.mBall.globalBallY);
                var3.writeInt(this.mCanvas.mBall.xSpeed);
                var3.writeInt(this.mCanvas.mBall.ySpeed);
                var3.writeInt(this.mCanvas.mBall.xPos);
                var3.writeInt(this.mCanvas.mBall.yPos);
                var3.writeInt(this.mCanvas.mBall.respawnX);
                var3.writeInt(this.mCanvas.mBall.respawnY);
                var3.writeInt(this.mCanvas.mBall.speedBonusCntr);
                var3.writeInt(this.mCanvas.mBall.gravBonusCntr);
                var3.writeInt(this.mCanvas.mBall.jumpBonusCntr);
                int[][] var5 = new int[50][3];
                int var6 = 0;

                for(int var7 = 0; var7 < this.mCanvas.mTileMapHeight; ++var7) {
                    for(int var8 = 0; var8 < this.mCanvas.mTileMapWidth; ++var8) {
                        byte var4 = (byte)(this.mCanvas.tileMap[var7][var8] & 'ｿ' & -65);
                        if (var4 == 7 || var4 == 29 || var4 == 13 || var4 == 14 || var4 == 21 || var4 == 22 || var4 == 15 || var4 == 16 || var4 == 23 || var4 == 24) {
                            var5[var6][0] = var7;
                            var5[var6][1] = var8;
                            var5[var6][2] = var4;
                            ++var6;
                        }
                    }
                }

                var3.writeByte(var6);

                for(int var13 = 0; var13 < var6; ++var13) {
                    var3.writeShort(var5[var13][0]);
                    var3.writeShort(var5[var13][1]);
                    var3.writeByte(var5[var13][2]);
                }

                Object var12 = null;
                var3.writeByte(this.mCanvas.mNumMoveObj);

                for(int var9 = 0; var9 < this.mCanvas.mNumMoveObj; ++var9) {
                    var3.writeShort(this.mCanvas.mMOOffset[var9][0]);
                    var3.writeShort(this.mCanvas.mMOOffset[var9][1]);
                    var3.writeShort(this.mCanvas.mMODirection[var9][0]);
                    var3.writeShort(this.mCanvas.mMODirection[var9][1]);
                }
            }

            RecordStore var10 = RecordStore.openRecordStore("bounceRMS", true);
            var10.setRecord(var1, var2.toByteArray(), 0, var2.size());
        } catch (Exception var11) {
        }

    }

    public void checkData() {
        if (this.mCanvas.mLevelNum > this.mBestLevel) {
            this.mBestLevel = Math.min(this.mCanvas.mLevelNum, 11);
            this.saveGameData(1);
        }

        if (this.mCanvas.mScore > this.mBestScore) {
            this.mBestScore = this.mCanvas.mScore;
            this.mNewBestScore = true;
            this.saveGameData(2);
        }

        this.mLastScore = this.mCanvas.mScore;
    }

    public void gameOver(boolean var1) {
        this.mState = 3;
        this.mSavedValid = -16;
        this.mCanvas.mIncomingCall = false;
        this.displayGameOver(var1);
    }
}
 