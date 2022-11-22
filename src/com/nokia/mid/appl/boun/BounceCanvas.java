// Decompiled with: FernFlower
// Class Version: 1
package com.nokia.mid.appl.boun;

import com.nokia.mid.sound.Sound;
import java.io.DataInputStream;
import java.io.IOException;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class BounceCanvas extends TileCanvas {
    public int mSplashIndex;
    public Image mSplashImage;
    private int mSplashTimer;
    protected Sound mSoundHoop;
    protected Sound mSoundPickup;
    protected Sound mSoundPop;
    private BounceUI mUI;
    public Ball mBall;
    public int numRings;
    public int numLives;
    public int mScore;
    public int bonusCntrValue;
    public int mLevelDisCntr;
    public boolean mLeaveGame;
    public boolean mOpenExitFlag;
    public boolean mPaintUIFlag;
    public final Font TEXT_FONT = Font.getFont(32, 0, 8);
    public Image mFullScreenBuffer;
    public Graphics mFullScreenGraphics = null;
    public boolean mClearScreenFlag;
    private boolean mCheat = false;
    public boolean mInvincible = false;
    private int mCheatSeq = 0;
    private static final String[] SPLASH_NAME = new String[]{"/icons/nokiagames.png", "/icons/bouncesplash.png"};
    public long mRepaintTime = 0L;
    public int mRepaintCount = 0;
    public boolean mIncomingCall = true;
    private long mLastTimeRepainted = System.currentTimeMillis();

    public BounceCanvas(BounceUI var1, int var2) {
        super(var1.mDisplay);
        this.mUI = var1;
        this.mSoundHoop = this.loadSound("/sounds/up.ott");
        this.mSoundPickup = this.loadSound("/sounds/pickup.ott");
        this.mSoundPop = this.loadSound("/sounds/pop.ott");

        try {
            this.mFullScreenBuffer = Image.createImage(128, 128);
        } catch (Exception var5) {
        }

        this.mSplashIndex = 1;

        try {
            this.mSplashImage = Image.createImage(SPLASH_NAME[this.mSplashIndex]);
        } catch (IOException var4) {
            this.mSplashImage = Image.createImage(1, 1);
        }

        this.start();
    }

    public void resetGame(int var1, int var2, int var3) {
        super.mLevelNum = var1;
        this.numRings = 0;
        this.numLives = var3;
        this.mScore = var2;
        this.mLeaveGame = false;
        this.mOpenExitFlag = false;
        this.createNewLevel();
        this.mClearScreenFlag = true;
    }

    public void resetGame(int var1, int var2) {
        super.mLevelNum = this.mUI.mSavedLevel;
        this.numRings = this.mUI.mSavedRings;
        this.numLives = this.mUI.mSavedLives;
        this.mScore = this.mUI.mSavedScore;
        this.disposeLevel();
        this.loadLevel(super.mLevelNum);
        this.resetTiles();
        this.resetSpikes();
        this.mLevelDisCntr = 120;
        this.mPaintUIFlag = true;
        if (this.mUI.mSavedRespawnX != super.mStartCol && this.mUI.mSavedRespawnY != super.mStartRow) {
            super.tileMap[this.mUI.mSavedRespawnY][this.mUI.mSavedRespawnX] = (short)(8 | super.tileMap[this.mUI.mSavedRespawnY][this.mUI.mSavedRespawnX] & 64);
        }

        this.createBufferFocused(var1, var2, this.mUI.mSavedSize, this.mUI.mSavedXSpeed, this.mUI.mSavedYSpeed);
        this.mBall.setRespawn(this.mUI.mSavedRespawnX, this.mUI.mSavedRespawnY);
        this.mBall.speedBonusCntr = this.mUI.mSavedSpeedBonus;
        this.mBall.gravBonusCntr = this.mUI.mSavedGravBonus;
        this.mBall.jumpBonusCntr = this.mUI.mSavedJumpBonus;
        this.mClearScreenFlag = true;
    }

    private void createNewLevel() {
        this.disposeLevel();
        this.loadLevel(super.mLevelNum);
        this.numRings = 0;
        this.mLevelDisCntr = 120;
        this.mPaintUIFlag = true;
        this.createBufferFocused(super.mStartCol * 12 + (super.mStartBallSize >> 1), super.mStartRow * 12 + (super.mStartBallSize >> 1), super.mStartBallSize, 0, 0);
        this.mBall.setRespawn(super.mStartCol, super.mStartRow);
        this.mClearScreenFlag = true;
    }

    public void createBufferFocused(int var1, int var2, int var3, int var4, int var5) {
        int var6 = var1 / 12;
        int var7 = var2 / 12;
        if (var1 < 64) {
            super.scrollFlag = false;
            super.scrollOffset = 64;
            super.rightDrawEdge = 128;
            super.leftDrawEdge = 0;
            super.tileX = 0;
        } else if (var1 > super.mTileMapWidth * 12 - 64) {
            super.scrollFlag = false;
            super.scrollOffset = 92;
            super.rightDrawEdge = 156;
            super.leftDrawEdge = 28;
            super.tileX = super.mTileMapWidth - 13;
        } else {
            super.scrollFlag = true;
            super.scrollOffset = 0;
            super.rightDrawEdge = 143;
            super.leftDrawEdge = 15;
            super.tileX = var6 - 6;
        }

        super.divisorLine = 156;
        super.tileY = var7 / 7 * 7;
        super.divTileX = super.tileX + 13;
        super.divTileY = super.tileY;
        int var8 = var1 - super.tileX * 12;
        int var9 = var2 - super.tileY * 12 - 1;
        this.mBall = new Ball(var8, var9, var3, this);
        this.mBall.xSpeed = var4;
        this.mBall.ySpeed = var5;
        this.createNewBuffer();
    }

    public void screenFlip() {
        int var1 = this.mBall.xPos / 12;
        int var2 = this.mBall.xPos - var1 * 12 - 6;
        this.cleanBuffer(false);
        if (this.mBall.yPos < 0) {
            super.tileY -= 7;
            super.divTileY -= 7;
            this.mBall.yPos += 84;
        } else if (this.mBall.yPos > 96) {
            super.tileY += 7;
            super.divTileY += 7;
            this.mBall.yPos -= 84;
        }

        if (!super.scrollFlag && super.tileX - (13 - super.divisorLine / 12) == 0) {
            if (super.divisorLine < this.mBall.xPos) {
                this.mBall.xPos -= super.divisorLine;
            } else {
                this.mBall.xPos = this.mBall.xPos - super.divisorLine + 156;
            }

            super.tileX = 0;
            super.leftDrawEdge = 0;
            super.rightDrawEdge = 128;
            super.scrollOffset = 64;
        } else if (!super.scrollFlag) {
            super.tileX = super.mTileMapWidth - 13;
            super.leftDrawEdge = 28;
            super.rightDrawEdge = 156;
            if (this.mBall.xPos > super.divisorLine) {
                this.mBall.xPos = 156 - (super.divisorLine + 156 - this.mBall.xPos);
            } else {
                this.mBall.xPos = 156 - (super.divisorLine - this.mBall.xPos);
            }

            super.scrollOffset = 92;
        } else {
            if (this.mBall.xPos > super.divisorLine) {
                super.tileX = super.tileX - 13 + var1 - 6;
            } else {
                super.tileX = super.tileX + var1 - 6;
            }

            if (super.tileX < 0) {
                var2 += super.tileX * 12;
                super.tileX = 0;
            } else if (super.tileX > super.mTileMapWidth - 13 - 1) {
                var2 += (super.tileX - super.mTileMapWidth - 13 - 1) * 12;
                super.tileX = super.mTileMapWidth - 13 - 1;
            }

            super.leftDrawEdge = 14 + var2;
            super.rightDrawEdge = 142 + var2;
            this.mBall.xPos = 78 + var2;
        }

        super.divTileX = super.tileX + 13;
        super.divisorLine = 156;
        this.createNewBuffer();
    }

    public void add2Score(int var1) {
        this.mScore += var1;
        this.mPaintUIFlag = true;
    }

    public void paint2Buffer() {
        if (this.mFullScreenGraphics == null) {
            this.mFullScreenGraphics = this.mFullScreenBuffer.getGraphics();
        }

        if (this.mBall != null) {
            this.mBall.paint(super.mGameGraphics);
        }

        if (super.hoopImageList != null) {
            while(!super.hoopImageList.isEmpty()) {
                Image var1 = (Image)super.hoopImageList.firstElement();
                Integer var2 = (Integer)super.hoopXPosList.firstElement();
                Integer var3 = (Integer)super.hoopYPosList.firstElement();
                super.mGameGraphics.drawImage(var1, var2.intValue(), var3.intValue(), 20);
                super.hoopImageList.removeElementAt(0);
                super.hoopXPosList.removeElementAt(0);
                super.hoopYPosList.removeElementAt(0);
            }
        }

        if (super.mGameBuffer != null) {
            if (super.leftDrawEdge < super.rightDrawEdge) {
                this.mFullScreenGraphics.drawImage(super.mGameBuffer, -super.leftDrawEdge, 0, 20);
            } else {
                this.mFullScreenGraphics.drawImage(super.mGameBuffer, -super.leftDrawEdge, 0, 20);
                this.mFullScreenGraphics.drawImage(super.mGameBuffer, 156 - super.leftDrawEdge, 0, 20);
            }
        }

        if (this.mPaintUIFlag) {
            this.mFullScreenGraphics.setColor(0x0853AA);
            this.mFullScreenGraphics.fillRect(0, 97, 128, 32);

            for(int var4 = 0; var4 < this.numLives; ++var4) {
                this.mFullScreenGraphics.drawImage(super.mUILife, 5 + var4 * (super.mUILife.getWidth() - 1), 99, 20);
            }

            for(int var5 = 0; var5 < super.mTotalNumRings - this.numRings; ++var5) {
                this.mFullScreenGraphics.drawImage(super.mUIRing, 5 + var5 * (super.mUIRing.getWidth() - 4), 112, 20);
            }

            this.mFullScreenGraphics.setColor(0xFFFFFE);
            this.mFullScreenGraphics.setFont(this.TEXT_FONT);
            this.mFullScreenGraphics.drawString(zeroString(this.mScore), 64, 100, 20);
            if (this.bonusCntrValue != 0) {
                this.mFullScreenGraphics.setColor(0xFF9813);
                this.mFullScreenGraphics.fillRect(1, 128 - 3 * this.bonusCntrValue / 30, 5, 128);
            }

            this.mPaintUIFlag = false;
        }

    }

    public void paint(Graphics var1) {
        if (this.mSplashIndex != -1) {
            if (this.mSplashImage != null) {
                var1.setColor(0);
                var1.fillRect(0, 0, super.mWidth, super.mHeight);
                var1.drawImage(this.mSplashImage, super.mWidth >> 1, super.mHeight >> 1, 3);
            }
        } else {
            if (this.mClearScreenFlag) {
                var1.setColor(-1);
                var1.fillRect(0, 0, super.mWidth, super.mHeight);
                this.mClearScreenFlag = false;
            }

            var1.drawImage(this.mFullScreenBuffer, 0, 0, 20);
            if (this.mLevelDisCntr != 0) {
                var1.setColor(0xFFFFFE);
                var1.setFont(this.TEXT_FONT);
                var1.drawString(super.mLevelNumStr, 44, 84, 20);
            }
        }

    }

    public void run() {
        if (super.mLoadLevelFlag) {
            this.createNewLevel();
            this.repaint();
        } else if (this.mSplashIndex != -1) {
            if (this.mSplashImage != null && this.mSplashImage != null) {
                if (this.mSplashTimer > 30) {
                    this.mSplashImage = null;
                    Runtime.getRuntime().gc();
                    switch(this.mSplashIndex) {
                    case 0:
                        this.mSplashIndex = 1;

                        try {
                            this.mSplashImage = Image.createImage(SPLASH_NAME[this.mSplashIndex]);
                        } catch (IOException var4) {
                            this.mSplashImage = Image.createImage(1, 1);
                        }

                        this.repaint();
                        break;
                    case 1:
                        this.mSplashIndex = -1;
                        this.mIncomingCall = false;
                        this.mUI.displayMainMenu();
                    }

                    this.mSplashTimer = 0;
                } else {
                    ++this.mSplashTimer;
                }
            } else {
                this.mIncomingCall = false;
                this.mUI.displayMainMenu();
            }

            this.repaint();
        } else {
            if (this.mLevelDisCntr != 0) {
                --this.mLevelDisCntr;
            }

            if (this.mBall.yPos >= 0 && this.mBall.yPos <= 96) {
                this.cleanBuffer(true);
                this.mBall.update();
                this.testScroll(this.mBall.xPos, this.mBall.xOffset);
            } else {
                this.screenFlip();
            }

            if (this.mBall.ballState == 1) {
                if (this.numLives < 0) {
                    this.mUI.checkData();
                    this.stop();
                    this.mUI.gameOver(false);
                    return;
                }

                int var1 = this.mBall.respawnX;
                int var2 = this.mBall.respawnY;
                int var3 = this.mBall.respawnSize;
                this.createBufferFocused(this.mBall.respawnX * 12 + (this.mBall.respawnSize >> 1), this.mBall.respawnY * 12 + (this.mBall.respawnSize >> 1), this.mBall.respawnSize, 0, 0);
                this.mBall.respawnX = var1;
                this.mBall.respawnY = var2;
                this.mBall.respawnSize = var3;
            }

            if (super.mNumMoveObj != 0) {
                this.updateMovingSpikeObj();
            }

            if (this.numRings == super.mTotalNumRings) {
                this.mOpenExitFlag = true;
            }

            if (this.mOpenExitFlag && super.mExitPos != -1) {
                int var5 = super.leftDrawEdge;
                int var6 = super.rightDrawEdge;
                if (super.mExitPos <= super.divisorLine) {
                    if (super.leftDrawEdge > super.divisorLine) {
                        var5 = super.leftDrawEdge - 156;
                    }

                    if (super.rightDrawEdge > super.divisorLine) {
                        var6 = super.rightDrawEdge - 156;
                    }
                }

                if (super.mExitPos > super.divisorLine) {
                    if (super.leftDrawEdge < super.divisorLine) {
                        var5 = super.leftDrawEdge + 156;
                    }

                    if (super.rightDrawEdge < super.divisorLine) {
                        var6 = super.rightDrawEdge + 156;
                    }
                }

                if (super.mExitPos >= var5 && super.mExitPos <= var6) {
                    if (super.mOpenFlag) {
                        super.mExitPos = -1;
                        this.mOpenExitFlag = false;
                    } else {
                        this.openExit();
                    }

                    super.tileMap[super.mTopLeftExitTileRow][super.mTopLeftExitTileCol] = (short)(super.tileMap[super.mTopLeftExitTileRow][super.mTopLeftExitTileCol] | 128);
                    super.tileMap[super.mTopLeftExitTileRow][super.mTopLeftExitTileCol + 1] = (short)(super.tileMap[super.mTopLeftExitTileRow][super.mTopLeftExitTileCol + 1] | 128);
                    super.tileMap[super.mTopLeftExitTileRow + 1][super.mTopLeftExitTileCol] = (short)(super.tileMap[super.mTopLeftExitTileRow + 1][super.mTopLeftExitTileCol] | 128);
                    super.tileMap[super.mTopLeftExitTileRow + 1][super.mTopLeftExitTileCol + 1] = (short)(super.tileMap[super.mTopLeftExitTileRow + 1][super.mTopLeftExitTileCol + 1] | 128);
                    this.cleanBuffer(true);
                }
            }

            this.bonusCntrValue = 0;
            if (this.mBall.speedBonusCntr != 0 || this.mBall.gravBonusCntr != 0 || this.mBall.jumpBonusCntr != 0) {
                if (this.mBall.speedBonusCntr > this.bonusCntrValue) {
                    this.bonusCntrValue = this.mBall.speedBonusCntr;
                }

                if (this.mBall.gravBonusCntr > this.bonusCntrValue) {
                    this.bonusCntrValue = this.mBall.gravBonusCntr;
                }

                if (this.mBall.jumpBonusCntr > this.bonusCntrValue) {
                    this.bonusCntrValue = this.mBall.jumpBonusCntr;
                }

                if (this.bonusCntrValue % 30 == 0 || this.bonusCntrValue == 1) {
                    this.mPaintUIFlag = true;
                }
            }

            this.scrollBuffer(this.mBall.xPos, this.mBall.xOffset, 16);
            this.paint2Buffer();
            this.repaint();
            if (this.mLeaveGame) {
                this.mLeaveGame = false;
                this.mOpenExitFlag = false;
                super.mLoadLevelFlag = true;
                ++super.mLevelNum;
                this.add2Score(5000);
                this.mUI.checkData();
                if (super.mLevelNum > 11) {
                    this.mUI.gameOver(true);
                } else {
                    this.mIncomingCall = false;
                    this.mUI.displayLevelComplete();
                    this.repaint();
                }
            }

        }
    }

    public void keyPressed(int var1) {
        if (this.mSplashIndex != -1) {
            this.mSplashTimer = 31;
        } else if (this.mBall != null) {
            switch(var1) {
            case -7:
            case -6:
                this.mIncomingCall = false;
                this.mUI.displayMainMenu();
                break;
            case 35:
                if (this.mCheat) {
                    this.mBall.gravBonusCntr = 300;
                }
                break;
            case 49:
                if (this.mCheat) {
                    super.mLoadLevelFlag = true;
                    if (--super.mLevelNum < 1) {
                        super.mLevelNum = 11;
                    }
                }
                break;
            case 51:
                if (this.mCheat) {
                    super.mLoadLevelFlag = true;
                    if (++super.mLevelNum > 11) {
                        super.mLevelNum = 1;
                    }
                }
                break;
            case 55:
                if (this.mCheatSeq != 0 && this.mCheatSeq != 2) {
                    this.mCheatSeq = 0;
                } else {
                    ++this.mCheatSeq;
                }
                break;
            case 56:
                if (this.mCheatSeq != 1 && this.mCheatSeq != 3) {
                    if (this.mCheatSeq == 5) {
                        this.mSoundHoop.play(1);
                        this.mInvincible = true;
                        this.mCheatSeq = 0;
                    } else {
                        this.mCheatSeq = 0;
                    }
                } else {
                    ++this.mCheatSeq;
                }
                break;
            case 57:
                if (this.mCheatSeq == 4) {
                    ++this.mCheatSeq;
                } else if (this.mCheatSeq == 5) {
                    this.mSoundPop.play(1);
                    this.mCheat = true;
                    this.mCheatSeq = 0;
                } else {
                    this.mCheatSeq = 0;
                }
                break;
            default:
                switch(this.getGameAction(var1)) {
                case 1:
                    this.mBall.setDirection(8);
                    break;
                case 2:
                    this.mBall.setDirection(1);
                case 3:
                case 4:
                case 7:
                default:
                    break;
                case 5:
                    this.mBall.setDirection(2);
                    break;
                case 6:
                    this.mBall.setDirection(4);
                    break;
                case 8:
                    if (this.mCheat) {
                        this.mLeaveGame = true;
                    }
                }
            }

        }
    }

    public void keyReleased(int var1) {
        if (this.mBall != null) {
            switch(this.getGameAction(var1)) {
            case 1:
                this.mBall.releaseDirection(8);
                break;
            case 2:
                this.mBall.releaseDirection(1);
            case 3:
            case 4:
            default:
                break;
            case 5:
                this.mBall.releaseDirection(2);
                break;
            case 6:
                this.mBall.releaseDirection(4);
            }

        }
    }

    public static String zeroString(int var0) {
        String var1;
        if (var0 < 100) {
            var1 = "0000000";
        } else if (var0 < 1000) {
            var1 = "00000";
        } else if (var0 < 10000) {
            var1 = "0000";
        } else if (var0 < 100000) {
            var1 = "000";
        } else if (var0 < 1000000) {
            var1 = "00";
        } else if (var0 < 10000000) {
            var1 = "0";
        } else {
            var1 = "";
        }

        return var1 + var0;
    }

    protected Sound loadSound(String var1) {
        byte[] var3 = new byte[100];
        Sound var9 = null;
        DataInputStream var2 = new DataInputStream(this.getClass().getResourceAsStream(var1));

        try {
            int var6 = var2.read(var3);
            var2.close();
            byte[] var4 = new byte[var6];
            System.arraycopy(var3, 0, var4, 0, var6);
            var9 = new Sound(var4, 1);
        } catch (IOException var8) {
            var9 = new Sound(1000, 500L);
            var9.play(3);
        }

        return var9;
    }

    public void hideNotify() {
        if (this.mIncomingCall) {
            if (this.mBall != null) {
                this.mBall.resetDirections();
            }

            this.mUI.displayMainMenu();
        }

        this.mIncomingCall = true;
    }

    public void resetSpikes() {
        for(int var1 = 0; var1 < this.mUI.mSavedSpikeCount; ++var1) {
            super.mMODirection[var1][0] = this.mUI.mSavedSpikeDirection[var1][0];
            super.mMODirection[var1][1] = this.mUI.mSavedSpikeDirection[var1][1];
            super.mMOOffset[var1][0] = this.mUI.mSavedSpikeOffset[var1][0];
            super.mMOOffset[var1][1] = this.mUI.mSavedSpikeOffset[var1][1];
        }

        this.mUI.mSavedSpikeOffset = null;
        this.mUI.mSavedSpikeDirection = null;
        this.mUI.mSavedSpikeCount = 0;
    }

    public void resetTiles() {
        for(int var2 = 0; var2 < super.mTileMapHeight; ++var2) {
            for(int var3 = 0; var3 < super.mTileMapWidth; ++var3) {
                byte var1 = (byte)(super.tileMap[var2][var3] & 'ｿ' & -65);
                switch(var1) {
                case 7:
                case 29:
                    if (this.tileNotSavedAsActive(var2, var3, var1)) {
                        super.tileMap[var2][var3] = (short)(0 | super.tileMap[var2][var3] & 64);
                    }
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 17:
                case 18:
                case 19:
                case 20:
                case 25:
                case 26:
                case 27:
                case 28:
                default:
                    break;
                case 13:
                    if (this.tileNotSavedAsActive(var2, var3, var1)) {
                        super.tileMap[var2][var3] = (short)(17 | super.tileMap[var2][var3] & 64);
                    }
                    break;
                case 14:
                    if (this.tileNotSavedAsActive(var2, var3, var1)) {
                        super.tileMap[var2][var3] = (short)(18 | super.tileMap[var2][var3] & 64);
                    }
                    break;
                case 15:
                    if (this.tileNotSavedAsActive(var2, var3, var1)) {
                        super.tileMap[var2][var3] = (short)(19 | super.tileMap[var2][var3] & 64);
                    }
                    break;
                case 16:
                    if (this.tileNotSavedAsActive(var2, var3, var1)) {
                        super.tileMap[var2][var3] = (short)(20 | super.tileMap[var2][var3] & 64);
                    }
                    break;
                case 21:
                    if (this.tileNotSavedAsActive(var2, var3, var1)) {
                        super.tileMap[var2][var3] = (short)(25 | super.tileMap[var2][var3] & 64);
                    }
                    break;
                case 22:
                    if (this.tileNotSavedAsActive(var2, var3, var1)) {
                        super.tileMap[var2][var3] = (short)(26 | super.tileMap[var2][var3] & 64);
                    }
                    break;
                case 23:
                    if (this.tileNotSavedAsActive(var2, var3, var1)) {
                        super.tileMap[var2][var3] = (short)(27 | super.tileMap[var2][var3] & 64);
                    }
                    break;
                case 24:
                    if (this.tileNotSavedAsActive(var2, var3, var1)) {
                        super.tileMap[var2][var3] = (short)(28 | super.tileMap[var2][var3] & 64);
                    }
                }
            }
        }

        this.mUI.mSavedTiles = null;
        this.mUI.mSavedTileCount = 0;
    }

    public boolean tileNotSavedAsActive(int var1, int var2, byte var3) {
        for(int var4 = 0; var4 < this.mUI.mSavedTileCount; ++var4) {
            if (this.mUI.mSavedTiles[var4][0] == var1 && this.mUI.mSavedTiles[var4][1] == var2) {
                return false;
            }
        }

        return true;
    }
}
 