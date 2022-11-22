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
    protected Sound mSoundHoop = this.loadSound("/sounds/up.ott");
    protected Sound mSoundPickup = this.loadSound("/sounds/pickup.ott");
    protected Sound mSoundPop = this.loadSound("/sounds/pop.ott");
    private BounceUI mUI;
    public Ball myBall;
    public int numRings;
    public int numLives;
    public int mScore;
    public int bonusCntrValue;
    public int mLevelDisCntr;
    public boolean mExitFlag;
    public boolean mOpenExitFlag;
    public boolean mPaintUIFlag;
    public Image mFullScreenBuffer;
    public Graphics mFullScreenGraphics = null;
    public boolean mClearScreenFlag;
    private boolean mCheat = false;
    public boolean mInvincible = false;
    private int mCheatSeq = 0;
    private static final boolean MEM_FREE_DISPLAY = false;
    public static final int UI_X_OFFSET = 5;
    public static final int UI_LIVES_Y_OFFSET = 3;
    public static final int UI_RINGS_Y_OFFSET = 16;
    public static final int UI_COLOUR = 545706;
    public static final int BONUS_COLOUR = 16750611;
    public static final int TEXT_COLOUR = 16777214;
    public static final Font TEXT_FONT = Font.getFont(32, 0, 8);
    public static final int SCORE_TEXT_POS_X = 64;
    public static final int SCORE_TEXT_POS_Y = 100;
    public static final int LEVEL_TEXT_POS_X = 44;
    public static final int LEVEL_TEXT_POS_Y = 84;
    public static final int BONUS_SEG_SIZE = 30;
    public static final int LEVEL_DISPLAY_TIME = 120;
    public static final int SPLASH_NONE = -1;
    public static final int SPLASH_NOKIA = 0;
    public static final int SPLASH_GAME = 1;
    private static final String[] SPLASH_NAME = new String[]{"/icons/nokiagames.png", "/icons/bouncesplash.png"};
    private static final int SPLASH_TIMER_DELAY = 30;
    public long mRepaintTime = 0L;
    public int mRepaintCount = 0;

    public BounceCanvas(BounceUI var1, int var2) {
        super(var1.mDisplay);
        this.mUI = var1;
        this.mFullScreenBuffer = Image.createImage(128, 128);
        this.mSplashIndex = 1;

        try {
            this.mSplashImage = Image.createImage(SPLASH_NAME[this.mSplashIndex]);
        } catch (IOException var4) {
            this.mSplashImage = Image.createImage(1, 1);
        }

        this.start();
    }

    public void resetGame(int var1) {
        super.mLevelNum = var1;
        this.numRings = 0;
        this.numLives = 3;
        this.mScore = 0;
        this.mExitFlag = false;
        this.mOpenExitFlag = false;
        this.mClearScreenFlag = false;
        this.createNewLevel();
    }

    public void createNewLevel() {
        this.disposeLevel();
        this.loadLevel(super.mLevelNum);
        this.numRings = 0;
        this.mLevelDisCntr = 120;
        this.mPaintUIFlag = true;
        this.createBufferFocused(super.mStartPosX, super.mStartPosY);
    }

    public void createBufferFocused(int var1, int var2) {
        if (var1 * 12 + 6 < 64) {
            super.scrollFlag = false;
            super.scrollOffset = 64;
            super.rightDrawEdge = 128;
            super.leftDrawEdge = 0;
            super.tileX = 0;
        } else if (var1 * 12 + 6 > super.mTileMapWidth * 12 - 64) {
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
            super.tileX = var1 - 6;
        }

        super.divisorLine = 156;
        super.tileY = var2 / 7 * 7;
        super.divTileX = super.tileX + 13;
        super.divTileY = super.tileY;
        byte var3 = 0;
        if (super.mBallSize == 1) {
            var3 = 16;
        } else {
            var3 = 12;
        }

        this.myBall = new Ball((var1 - super.tileX) * 12 + var3 / 2, (var2 - super.tileY) * 12 + var3 / 2, super.mBallSize, this);
        this.myBall.setRespawn(var1, var2);
        if (super.mBallSize == 1 && !this.myBall.collisionDetection(this.myBall.xPos, this.myBall.yPos)) {
            byte var4 = 4;
            if (this.myBall.collisionDetection(this.myBall.xPos - var4, this.myBall.yPos)) {
                this.myBall.xPos -= var4;
            } else if (this.myBall.collisionDetection(this.myBall.xPos, this.myBall.yPos - var4)) {
                this.myBall.yPos -= var4;
            } else if (this.myBall.collisionDetection(this.myBall.xPos - var4, this.myBall.yPos - var4)) {
                this.myBall.xPos -= var4;
                this.myBall.yPos -= var4;
            }
        }

        this.createNewBuffer();
    }

    public void checkData() {
        if (super.mLevelNum > this.mUI.mBestLevel) {
            this.mUI.mBestLevel = super.mLevelNum;
            this.mUI.setGameData(1);
        }

        if (this.mScore > this.mUI.mBestScore) {
            this.mUI.mBestScore = this.mScore;
            this.mUI.mNewBestScore = true;
            this.mUI.setGameData(2);
        }

        this.mUI.mLastScore = this.mScore;
    }

    public void screenFlip() {
        int var1 = this.myBall.xPos / 12;
        int var2 = this.myBall.xPos - var1 * 12 - 6;
        this.cleanBuffer(false);
        if (this.myBall.yPos < 0) {
            super.tileY -= 7;
            super.divTileY -= 7;
            this.myBall.yPos += 84;
        } else if (this.myBall.yPos > 96) {
            super.tileY += 7;
            super.divTileY += 7;
            this.myBall.yPos -= 84;
        }

        if (!super.scrollFlag && super.tileX - (13 - super.divisorLine / 12) == 0) {
            super.tileX = 0;
            super.leftDrawEdge = 0;
            super.rightDrawEdge = 128;
            if (this.myBall.xPos > super.divisorLine) {
                this.myBall.xPos -= super.divisorLine;
            }

            super.scrollOffset = 64;
        } else if (!super.scrollFlag) {
            super.tileX = super.mTileMapWidth - 13;
            super.leftDrawEdge = 28;
            super.rightDrawEdge = 156;
            if (this.myBall.xPos > super.divisorLine) {
                this.myBall.xPos = 156 - (super.divisorLine + 156 - this.myBall.xPos);
            } else {
                this.myBall.xPos = 156 - (super.divisorLine - this.myBall.xPos);
            }

            super.scrollOffset = 92;
        } else {
            if (this.myBall.xPos > super.divisorLine) {
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
            this.myBall.xPos = 78 + var2;
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

        if (this.myBall != null) {
            this.myBall.paint(super.bufferGraphics);
        }

        while(!super.hoopImageList.isEmpty()) {
            Image var1 = (Image)super.hoopImageList.firstElement();
            Integer var2 = (Integer)super.hoopXPosList.firstElement();
            Integer var3 = (Integer)super.hoopYPosList.firstElement();
            super.bufferGraphics.drawImage(var1, var2.intValue(), var3.intValue(), 20);
            super.hoopImageList.removeElementAt(0);
            super.hoopXPosList.removeElementAt(0);
            super.hoopYPosList.removeElementAt(0);
        }

        if (super.buffer != null) {
            if (super.leftDrawEdge < super.rightDrawEdge) {
                this.mFullScreenGraphics.drawImage(super.buffer, 0 - super.leftDrawEdge, 0, 20);
            } else {
                this.mFullScreenGraphics.drawImage(super.buffer, 0 - super.leftDrawEdge, 0, 20);
                this.mFullScreenGraphics.drawImage(super.buffer, 156 - super.leftDrawEdge, 0, 20);
            }
        }

        if (this.mLevelDisCntr != 0) {
            this.mFullScreenGraphics.setColor(0xFFFFFE);
            this.mFullScreenGraphics.setFont(TEXT_FONT);
            this.mFullScreenGraphics.drawString(super.mLevelNumStr, 44, 84, 20);
        }

        if (this.mPaintUIFlag) {
            this.mFullScreenGraphics.setColor(0x0853AA);
            this.mFullScreenGraphics.fillRect(0, 97, 128, 32);

            for(int var4 = 0; var4 < this.numLives; ++var4) {
                this.mFullScreenGraphics.drawImage(super.mUILife, 5 + var4 * (super.mUILife.getWidth() - 1), 99, 20);
            }

            for(int var5 = 0; var5 < super.mTotalNumRings - this.numRings; ++var5) {
                this.mFullScreenGraphics.drawImage(super.mUIRing, 5 + var5 * (super.mUIRing.getWidth() - 1), 112, 20);
            }

            this.mFullScreenGraphics.setColor(0xFFFFFE);
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
                var1.drawImage(this.mSplashImage, super.mWidth / 2, super.mHeight / 2, 3);
            }
        } else if (this.mClearScreenFlag) {
            var1.setColor(0);
            var1.fillRect(0, 0, super.mWidth, super.mHeight);
            this.mClearScreenFlag = false;
        } else {
            var1.drawImage(this.mFullScreenBuffer, 0, 0, 20);
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
                        } catch (IOException var3) {
                            this.mSplashImage = Image.createImage(1, 1);
                        }

                        this.repaint();
                        break;
                    case 1:
                        this.mSplashIndex = -1;
                        this.mUI.displayMainMenu();
                    }

                    this.mSplashTimer = 0;
                } else {
                    ++this.mSplashTimer;
                }
            } else {
                this.mUI.displayMainMenu();
            }

            this.repaint();
        } else {
            if (this.mLevelDisCntr != 0) {
                --this.mLevelDisCntr;
            }

            if (this.myBall.yPos >= 0 && this.myBall.yPos <= 96) {
                this.cleanBuffer(true);
                this.myBall.update();
                this.testScroll(this.myBall.xPos, this.myBall.xOffset);
            } else {
                this.screenFlip();
            }

            if (this.myBall.ballState == 1) {
                if (this.numLives < 0) {
                    this.checkData();
                    this.stop();
                    this.mUI.displayGameOver();
                    return;
                }

                this.createBufferFocused(this.myBall.respawnX, this.myBall.respawnY);
            }

            if (super.mNumMoveObj != 0) {
                this.updateMovingSpikeObj();
            }

            if (this.numRings == super.mTotalNumRings) {
                this.mOpenExitFlag = true;
            }

            if (this.mOpenExitFlag && super.mExitPos != -1) {
                int var1 = super.leftDrawEdge;
                int var2 = super.rightDrawEdge;
                if (super.mExitPos <= super.divisorLine) {
                    if (super.leftDrawEdge > super.divisorLine) {
                        var1 = super.leftDrawEdge - 156;
                    }

                    if (super.rightDrawEdge > super.divisorLine) {
                        var2 = super.rightDrawEdge - 156;
                    }
                }

                if (super.mExitPos > super.divisorLine) {
                    if (super.leftDrawEdge < super.divisorLine) {
                        var1 = super.leftDrawEdge + 156;
                    }

                    if (super.rightDrawEdge < super.divisorLine) {
                        var2 = super.rightDrawEdge + 156;
                    }
                }

                if (super.mExitPos >= var1 && super.mExitPos <= var2) {
                    if (super.mOpenFlag) {
                        super.mExitPos = -1;
                        this.mOpenExitFlag = false;
                    } else {
                        this.openExit();
                    }

                    super.tileMap[super.mTopLeftTileRow][super.mTopLeftTileCol] = (short)(super.tileMap[super.mTopLeftTileRow][super.mTopLeftTileCol] | 128);
                    this.cleanBuffer(true);
                }
            }

            this.bonusCntrValue = 0;
            if (this.myBall.speedBonusCntr != 0 || this.myBall.gravBonusCntr != 0 || this.myBall.jumpBonusCntr != 0) {
                if (this.myBall.speedBonusCntr > this.bonusCntrValue) {
                    this.bonusCntrValue = this.myBall.speedBonusCntr;
                }

                if (this.myBall.gravBonusCntr > this.bonusCntrValue) {
                    this.bonusCntrValue = this.myBall.gravBonusCntr;
                }

                if (this.myBall.jumpBonusCntr > this.bonusCntrValue) {
                    this.bonusCntrValue = this.myBall.jumpBonusCntr;
                }

                if (this.bonusCntrValue % 30 == 0 || this.bonusCntrValue == 1) {
                    this.mPaintUIFlag = true;
                }
            }

            this.scrollBuffer(this.myBall.xPos, this.myBall.xOffset, 16);
            this.paint2Buffer();
            this.repaint();
            if (this.mExitFlag) {
                this.mExitFlag = false;
                super.mLoadLevelFlag = true;
                ++super.mLevelNum;
                this.add2Score(5000);
                this.checkData();
                if (super.mLevelNum >= 11) {
                    this.mUI.displayGameOver();
                } else {
                    this.mUI.displayLevelComplete();
                    this.mClearScreenFlag = true;
                    this.repaint();
                }
            }

        }
    }

    public void keyPressed(int var1) {
        if (this.mSplashIndex != -1) {
            this.mSplashTimer = 31;
        } else if (this.myBall != null) {
            switch(var1) {
            case -7:
                this.mUI.displayMainMenu();
            case -6:
            case -5:
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
                    this.myBall.setDirection(8);
                    break;
                case 2:
                    this.myBall.setDirection(1);
                case 3:
                case 4:
                default:
                    break;
                case 5:
                    this.myBall.setDirection(2);
                    break;
                case 6:
                    this.myBall.setDirection(4);
                }
            }

        }
    }

    public void keyReleased(int var1) {
        if (this.myBall != null) {
            switch(this.getGameAction(var1)) {
            case 1:
                this.myBall.releaseDirection(8);
                break;
            case 2:
                this.myBall.releaseDirection(1);
            case 3:
            case 4:
            default:
                break;
            case 5:
                this.myBall.releaseDirection(2);
                break;
            case 6:
                this.myBall.releaseDirection(4);
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
            System.out.println("loadSound for " + var1 + " failed: " + var8.toString());
            var9 = new Sound(1000, 500L);
            var9.play(3);
        }

        return var9;
    }
}
 