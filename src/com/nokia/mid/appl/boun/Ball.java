// Decompiled with: FernFlower
// Class Version: 1
package com.nokia.mid.appl.boun;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class Ball {
    private static final boolean CD_DEBUG = false;
    private boolean mDebugCD = false;
    public int xPos;
    public int yPos;
    public int globalBallX;
    public int globalBallY;
    public int xOffset;
    public int xSpeed;
    public int ySpeed;
    public int direction;
    public int ballSize;
    public int mHalfBallSize;
    public int respawnX;
    public int respawnY;
    public int ballState;
    public int jumpOffset;
    public int speedBonusCntr;
    public int gravBonusCntr;
    public int jumpBonusCntr;
    public boolean mGroundedFlag;
    public boolean mCDRubberFlag;
    public boolean mCDRampFlag;
    public boolean mCDEdgeFlag;
    public int slideCntr;
    public static final int MOVE_UP = 8;
    public static final int MOVE_DOWN = 4;
    public static final int MOVE_RIGHT = 2;
    public static final int MOVE_LEFT = 1;
    public static final int MOVE_STAT = 0;
    public static final int SMALL_SIZE_STATE = 0;
    public static final int LARGE_SIZE_STATE = 1;
    public static final int NORMAL_SIZE = 12;
    public static final int HALF_NORMAL_SIZE = 6;
    public static final int POPPED_SIZE = 12;
    public static final int HALF_POPPED_SIZE = 6;
    public static final int ENLARGED_SIZE = 16;
    public static final int HALF_ENLARGED_SIZE = 8;
    public static final int BALL_STATE_NORMAL = 0;
    public static final int BALL_STATE_DEAD = 1;
    public static final int BALL_STATE_POPPED = 2;
    public static final int POPPED_FRAMES = 5;
    public static final int BONUS_DURATION = 300;
    public static final int THIN_TILE_SIZE = 4;
    public static final byte[][] TRI_TILE_DATA = new byte[][]{{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1}, {0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1}, {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1}, {0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1}, {0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1}, {0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1}, {0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1}, {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}};
    public static final byte[][] SMALL_BALL_DATA = new byte[][]{{0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0}, {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0}, {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0}, {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0}, {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0}, {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0}, {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0}, {0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0}};
    public static final byte[][] LARGE_BALL_DATA = new byte[][]{{0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0}, {0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0}, {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0}, {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0}, {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0}, {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0}, {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0}, {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0}, {0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0}, {0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0}};
    public static final int JUMP_STRENGTH = -67;
    public static final int JUMP_STRENGTH_INC = -10;
    public static final int JUMP_BONUS_STRENGTH = -80;
    public static final int NORMAL_GRAVITY_ACCELL = 4;
    public static final int UWATER_GRAVITY_ACCELL = 6;
    public static final int LARGE_GRAVITY_ACCELL = 3;
    public static final int LARGE_UWATER_GRAVITY_ACCELL = -2;
    public static final int BASE_GRAVITY = 10;
    public static final int BOUNCE_NUMERATOR = -1;
    public static final int BOUNCE_DENOMINATOR = 2;
    public static final int ROOF_COLLISION_SPEED = 6;
    public static final int MIN_BOUNCE_SPEED = 10;
    public static final int MAX_HORZ_SPEED = 50;
    public static final int HORZ_ACCELL = 6;
    public static final int FRICTION_DECELL = 4;
    public static final int NORMAL_MAX_GRAVITY = 80;
    public static final int UWATER_MAX_GRAVITY = 42;
    public static final int LARGE_MAX_GRAVITY = 38;
    public static final int UWATER_LARGE_MAX_GRAVITY = -30;
    public static final int RING_POINTS = 500;
    public static final int GEM_POINTS = 200;
    public static final int LIFE_POINTS = 1000;
    public static final int EXIT_POINTS = 5000;
    public BounceCanvas myParent;
    public Image ballImage;
    public Image poppedImage;
    public Image largeBallImage;
    public Image smallBallImage;
    private int popCntr;

    public Ball(int var1, int var2, int var3, BounceCanvas var4) {
        this.xPos = var1;
        this.yPos = var2;
        this.globalBallX = 0;
        this.globalBallY = 0;
        this.xSpeed = 0;
        this.ySpeed = 0;
        this.xOffset = 0;
        this.myParent = var4;
        this.jumpOffset = 0;
        this.mGroundedFlag = false;
        this.mCDRubberFlag = false;
        this.mCDRampFlag = false;
        this.popCntr = 0;
        this.speedBonusCntr = 0;
        this.gravBonusCntr = 0;
        this.jumpBonusCntr = 0;
        this.slideCntr = 0;
        this.ballState = 0;
        this.direction = 0;
        this.myParent.setBallImages(this);
        if (var3 == 0) {
            this.ballSize = 12;
            this.mHalfBallSize = 6;
            this.ballImage = this.smallBallImage;
        } else if (var3 == 1) {
            this.ballSize = 16;
            this.mHalfBallSize = 8;
            this.ballImage = this.largeBallImage;
        }

    }

    public void setRespawn(int var1, int var2) {
        this.respawnX = var1;
        this.respawnY = var2;
        if (this.ballSize == 16) {
            this.myParent.mBallSize = 1;
        } else {
            this.myParent.mBallSize = 0;
        }

    }

    public void setDirection(int var1) {
        if (var1 == 8 || var1 == 4 || var1 == 2 || var1 == 1) {
            this.direction |= var1;
        }

    }

    public void releaseDirection(int var1) {
        if (var1 == 8 || var1 == 4 || var1 == 2 || var1 == 1) {
            this.direction &= ~var1;
        }

    }

    public boolean collisionDetection(int var1, int var2) {
        int var3 = 0;
        if (var2 < 0) {
            var3 = Math.abs(var2) / 12 + 1;
            var2 = 0;
        }

        int var4 = (var1 - this.mHalfBallSize) / 12;
        int var5 = (var2 - this.mHalfBallSize) / 12 - var3;
        this.globalBallX = var1 - this.mHalfBallSize;
        this.globalBallY = var2 - this.mHalfBallSize - var3 * 12;
        if (this.xPos < this.myParent.divisorLine) {
            this.globalBallX += this.myParent.tileX * 12;
            this.globalBallY += this.myParent.tileY * 12;
        } else {
            this.globalBallX += (this.myParent.divTileX - 13) * 12 - this.myParent.divisorLine;
            this.globalBallY += this.myParent.divTileY * 12;
        }

        int var6 = (var1 - 1 + this.mHalfBallSize) / 12 + 1;
        int var7 = (var2 - 1 + this.mHalfBallSize) / 12 + 1;
        boolean var8 = true;

        for(int var9 = var4; var9 < var6; ++var9) {
            for(int var10 = var5; var10 < var7; ++var10) {
                if (var9 * 12 > 156) {
                    var8 = this.testTile(this.myParent.tileY + var10, this.myParent.tileX + var9 - 13, var8);
                } else if (this.xPos < this.myParent.divisorLine) {
                    var8 = this.testTile(this.myParent.tileY + var10, this.myParent.tileX + var9, var8);
                } else {
                    int var11 = this.myParent.divTileX - 13 - this.myParent.divisorLine / 12;
                    var8 = this.testTile(this.myParent.divTileY + var10, var11 + var9, var8);
                }
            }
        }

        return var8;
    }

    public void enlargeBall() {
        int var1 = 2;
        this.ballSize = 16;
        this.mHalfBallSize = 8;
        this.ballImage = this.largeBallImage;
        boolean var2 = false;

        while(!var2) {
            var2 = true;
            if (this.collisionDetection(this.xPos, this.yPos - var1)) {
                this.yPos -= var1;
            } else if (this.collisionDetection(this.xPos - var1, this.yPos - var1)) {
                this.xPos -= var1;
                this.yPos -= var1;
            } else if (this.collisionDetection(this.xPos + var1, this.yPos - var1)) {
                this.xPos += var1;
                this.yPos -= var1;
            } else if (this.collisionDetection(this.xPos, this.yPos + var1)) {
                this.yPos += var1;
            } else if (this.collisionDetection(this.xPos - var1, this.yPos + var1)) {
                this.xPos -= var1;
                this.yPos += var1;
            } else if (this.collisionDetection(this.xPos + var1, this.yPos + var1)) {
                this.xPos += var1;
                this.yPos += var1;
            } else {
                var2 = false;
                ++var1;
            }
        }

    }

    public void shrinkBall() {
        byte var1 = 2;
        this.ballSize = 12;
        this.mHalfBallSize = 6;
        this.ballImage = this.smallBallImage;
        if (this.collisionDetection(this.xPos, this.yPos + var1)) {
            this.yPos += var1;
        } else if (this.collisionDetection(this.xPos, this.yPos - var1)) {
            this.yPos -= var1;
        }

    }

    public void popBall() {
        if (!this.myParent.mInvincible) {
            this.popCntr = 5;
            this.ballState = 2;
            this.xOffset = 0;
            --this.myParent.numLives;
            this.speedBonusCntr = 0;
            this.gravBonusCntr = 0;
            this.jumpBonusCntr = 0;
            this.myParent.mPaintUIFlag = true;
            this.myParent.mSoundPop.play(1);
        }

    }

    public void addRing() {
        this.myParent.add2Score(500);
        ++this.myParent.numRings;
        this.myParent.mPaintUIFlag = true;
    }

    public void redirectBall(int var1) {
        int var2 = this.xSpeed;
        switch(var1) {
        case 30:
        case 32:
        case 34:
        case 36:
            this.xSpeed = -this.ySpeed;
            this.ySpeed = -var2;
            break;
        case 31:
        case 33:
        case 35:
        case 37:
            this.xSpeed = this.ySpeed;
            this.ySpeed = var2;
        }

    }

    public boolean squareCollide(int var1, int var2) {
        int var3 = var2 * 12;
        int var4 = var1 * 12;
        int var5 = this.globalBallX - var3;
        int var6 = this.globalBallY - var4;
        int var7;
        int var8;
        if (var5 >= 0) {
            var7 = var5;
            var8 = 12;
        } else {
            var7 = 0;
            var8 = this.ballSize + var5;
        }

        int var9;
        int var10;
        if (var6 >= 0) {
            var9 = var6;
            var10 = 12;
        } else {
            var9 = 0;
            var10 = this.ballSize + var6;
        }

        byte[][] var11;
        if (this.ballSize == 16) {
            var11 = LARGE_BALL_DATA;
        } else {
            var11 = SMALL_BALL_DATA;
        }

        if (var8 > 12) {
            var8 = 12;
        }

        if (var10 > 12) {
            var10 = 12;
        }

        for(int var12 = var7; var12 < var8; ++var12) {
            for(int var13 = var9; var13 < var10; ++var13) {
                if (var11[var13 - var6][var12 - var5] != 0) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean triangleCollide(int var1, int var2, int var3) {
        int var4 = var2 * 12;
        int var5 = var1 * 12;
        int var6 = this.globalBallX - var4;
        int var7 = this.globalBallY - var5;
        byte var8 = 0;
        byte var9 = 0;
        switch(var3) {
        case 30:
        case 34:
            var9 = 11;
            var8 = 11;
            break;
        case 31:
        case 35:
            var9 = 11;
        case 32:
        case 36:
        default:
            break;
        case 33:
        case 37:
            var8 = 11;
        }

        int var10;
        int var11;
        if (var6 >= 0) {
            var10 = var6;
            var11 = 12;
        } else {
            var10 = 0;
            var11 = this.ballSize + var6;
        }

        int var12;
        int var13;
        if (var7 >= 0) {
            var12 = var7;
            var13 = 12;
        } else {
            var12 = 0;
            var13 = this.ballSize + var7;
        }

        byte[][] var14;
        if (this.ballSize == 16) {
            var14 = LARGE_BALL_DATA;
        } else {
            var14 = SMALL_BALL_DATA;
        }

        if (var11 > 12) {
            var11 = 12;
        }

        if (var13 > 12) {
            var13 = 12;
        }

        for(int var15 = var10; var15 < var11; ++var15) {
            for(int var16 = var12; var16 < var13; ++var16) {
                if ((TRI_TILE_DATA[Math.abs(var16 - var9)][Math.abs(var15 - var8)] & var14[var16 - var7][var15 - var6]) != 0) {
                    if (!this.mGroundedFlag) {
                        this.redirectBall(var3);
                    }

                    return true;
                }
            }
        }

        return false;
    }

    public boolean thinCollide(int var1, int var2, int var3) {
        int var4 = var2 * 12;
        int var5 = var1 * 12;
        int var6 = var4 + 12;
        int var7 = var5 + 12;
        switch(var3) {
        case 3:
        case 5:
        case 9:
        case 13:
        case 14:
        case 17:
        case 18:
        case 21:
        case 22:
        case 43:
        case 45:
            var4 += 4;
            var6 -= 4;
            break;
        case 4:
        case 6:
        case 15:
        case 16:
        case 19:
        case 20:
        case 23:
        case 24:
        case 44:
        case 46:
            var5 += 4;
            var7 -= 4;
        case 7:
        case 8:
        case 10:
        case 11:
        case 12:
        case 25:
        case 26:
        case 27:
        case 28:
        case 29:
        case 30:
        case 31:
        case 32:
        case 33:
        case 34:
        case 35:
        case 36:
        case 37:
        case 38:
        case 39:
        case 40:
        case 41:
        case 42:
        }

        return TileCanvas.rectCollide(this.globalBallX, this.globalBallY, this.globalBallX + this.ballSize, this.globalBallY + this.ballSize, var4, var5, var6, var7);
    }

    public boolean testTile(int var1, int var2, boolean var3) {
        if (var1 < this.myParent.mTileMapHeight && var1 >= 0 && var2 < this.myParent.mTileMapWidth && var2 >= 0) {
            if (this.ballState == 2) {
                return false;
            } else {
                int var4 = this.myParent.tileMap[var1][var2] & -65;
                switch(var4) {
                case 0:
                case 8:
                case 11:
                case 12:
                case 25:
                case 26:
                case 27:
                case 28:
                default:
                    break;
                case 1:
                    if (this.squareCollide(var1, var2)) {
                        var3 = false;
                    } else {
                        this.mCDRampFlag = true;
                    }
                    break;
                case 2:
                    if (this.squareCollide(var1, var2)) {
                        this.mCDRubberFlag = true;
                        var3 = false;
                    } else {
                        this.mCDRampFlag = true;
                    }
                    break;
                case 3:
                case 4:
                case 5:
                case 6:
                    if (this.thinCollide(var1, var2, var4)) {
                        var3 = false;
                        this.popBall();
                    }
                    break;
                case 7:
                    this.myParent.add2Score(200);
                    this.myParent.tileMap[this.respawnY][this.respawnX] = 128;
                    this.setRespawn(var2, var1);
                    this.myParent.tileMap[var1][var2] = 136;
                    this.myParent.mSoundPickup.play(1);
                    break;
                case 9:
                    if (this.thinCollide(var1, var2, var4) && this.myParent.mOpenFlag) {
                        this.myParent.mExitFlag = true;
                        this.myParent.mSoundPickup.play(1);
                    }
                    break;
                case 10:
                    int var5 = this.myParent.findSpikeIndex(var2, var1);
                    if (var5 != -1) {
                        int var6 = this.myParent.mMOTopLeft[var5][0] * 12 + this.myParent.mMOOffset[var5][0];
                        int var7 = this.myParent.mMOTopLeft[var5][1] * 12 + this.myParent.mMOOffset[var5][1];
                        if (TileCanvas.rectCollide(this.globalBallX, this.globalBallY, this.globalBallX + this.ballSize, this.globalBallY + this.ballSize, var6, var7, var6 + 24, var7 + 24)) {
                            var3 = false;
                            this.popBall();
                        }
                    }
                    break;
                case 13:
                    if (this.thinCollide(var1, var2, var4)) {
                        if (this.ballSize == 16) {
                            var3 = false;
                        } else {
                            this.addRing();
                            this.myParent.tileMap[var1][var2] = 145;
                            this.myParent.tileMap[var1 + 1][var2] = 146;
                            this.myParent.mSoundHoop.play(1);
                        }
                    }
                    break;
                case 14:
                    if (this.thinCollide(var1, var2, var4)) {
                        if (this.ballSize == 16) {
                            var3 = false;
                        } else {
                            this.addRing();
                            this.myParent.tileMap[var1][var2] = 146;
                            this.myParent.tileMap[var1 - 1][var2] = 145;
                        }
                    }
                    break;
                case 15:
                    if (this.thinCollide(var1, var2, var4)) {
                        if (this.ballSize == 16) {
                            var3 = false;
                        } else {
                            this.addRing();
                            this.myParent.tileMap[var1][var2] = 147;
                            this.myParent.tileMap[var1][var2 + 1] = 148;
                            this.myParent.mSoundHoop.play(1);
                        }
                    }
                    break;
                case 16:
                    if (this.thinCollide(var1, var2, var4)) {
                        if (this.ballSize == 16) {
                            var3 = false;
                        } else {
                            this.addRing();
                            this.myParent.tileMap[var1][var2] = 148;
                            this.myParent.tileMap[var1][var2 - 1] = 147;
                        }
                    }
                    break;
                case 17:
                case 18:
                case 19:
                case 20:
                    if (this.thinCollide(var1, var2, var4) && this.ballSize == 16) {
                        var3 = false;
                    }
                    break;
                case 21:
                    if (this.thinCollide(var1, var2, var4)) {
                        this.addRing();
                        this.myParent.tileMap[var1][var2] = 153;
                        this.myParent.tileMap[var1 + 1][var2] = 154;
                        this.myParent.mSoundHoop.play(1);
                    }
                    break;
                case 22:
                    if (this.thinCollide(var1, var2, var4)) {
                        this.addRing();
                        this.myParent.tileMap[var1][var2] = 154;
                        this.myParent.tileMap[var1 - 1][var2] = 153;
                    }
                    break;
                case 23:
                    if (this.thinCollide(var1, var2, var4)) {
                        this.addRing();
                        this.myParent.tileMap[var1][var2] = 155;
                        this.myParent.tileMap[var1][var2 + 1] = 156;
                        this.myParent.mSoundHoop.play(1);
                    }
                    break;
                case 24:
                    if (this.thinCollide(var1, var2, var4)) {
                        this.addRing();
                        this.myParent.tileMap[var1][var2] = 156;
                        this.myParent.tileMap[var1][var2 - 1] = 155;
                    }
                    break;
                case 29:
                    this.myParent.add2Score(1000);
                    if (this.myParent.numLives < 5) {
                        ++this.myParent.numLives;
                        this.myParent.mPaintUIFlag = true;
                    }

                    this.myParent.tileMap[var1][var2] = 128;
                    this.myParent.mSoundPickup.play(1);
                    break;
                case 30:
                case 31:
                case 32:
                case 33:
                    if (this.triangleCollide(var1, var2, var4)) {
                        var3 = false;
                        this.mCDRampFlag = true;
                    }
                    break;
                case 34:
                case 35:
                case 36:
                case 37:
                    if (this.triangleCollide(var1, var2, var4)) {
                        this.mCDRubberFlag = true;
                        var3 = false;
                        this.mCDRampFlag = true;
                    }
                    break;
                case 38:
                    this.speedBonusCntr = 300;
                    this.myParent.mSoundPickup.play(1);
                    var3 = false;
                    break;
                case 39:
                case 40:
                case 41:
                case 42:
                    var3 = false;
                    if (this.ballSize == 16) {
                        this.shrinkBall();
                    }
                    break;
                case 43:
                case 44:
                case 45:
                case 46:
                    if (this.thinCollide(var1, var2, var4)) {
                        var3 = false;
                        if (this.ballSize == 12) {
                            this.enlargeBall();
                        }
                    }
                    break;
                case 47:
                case 48:
                case 49:
                case 50:
                    this.gravBonusCntr = 300;
                    this.myParent.mSoundPickup.play(1);
                    var3 = false;
                    break;
                case 51:
                case 52:
                case 53:
                case 54:
                    this.jumpBonusCntr = 300;
                    this.myParent.mSoundPickup.play(1);
                    var3 = false;
                }

                return var3;
            }
        } else {
            var3 = false;
            return false;
        }
    }

    public void update() {
        int var1 = this.xPos;
        int var2 = 0;
        int var3 = 0;
        byte var4 = 0;
        boolean var5 = false;
        if (this.ballState == 2) {
            this.xOffset = 0;
            --this.popCntr;
            if (this.popCntr == 0) {
                this.ballState = 1;
                if (this.myParent.numLives < 0) {
                    this.myParent.mExitFlag = true;
                }
            }

        } else {
            int var6 = this.xPos / 12;
            int var7 = this.yPos / 12;
            if (this.xPos >= 156) {
                var6 = this.myParent.tileX + var6 - 13;
                var7 = this.myParent.tileY + var7;
            } else if (this.xPos < this.myParent.divisorLine) {
                var6 = this.myParent.tileX + var6;
                var7 = this.myParent.tileY + var7;
            } else {
                var6 = this.myParent.divTileX - 13 - this.myParent.divisorLine / 12 + var6;
                var7 = this.myParent.divTileY + var7;
            }

            if ((this.myParent.tileMap[var7][var6] & 64) != 0) {
                if (this.ballSize == 16) {
                    var3 = -30;
                    var2 = -2;
                    if (this.mGroundedFlag) {
                        this.ySpeed = -10;
                    }
                } else {
                    var3 = 42;
                    var2 = 6;
                }
            } else if (this.ballSize == 16) {
                var3 = 38;
                var2 = 3;
            } else {
                var3 = 80;
                var2 = 4;
            }

            if (this.gravBonusCntr != 0) {
                var5 = true;
                var3 *= -1;
                var2 *= -1;
                --this.gravBonusCntr;
                if (this.gravBonusCntr == 0) {
                    var5 = false;
                    this.mGroundedFlag = false;
                    var3 *= -1;
                    var2 *= -1;
                }
            }

            if (this.jumpBonusCntr != 0) {
                if (-1 * Math.abs(this.jumpOffset) > -80) {
                    if (var5) {
                        this.jumpOffset = 80;
                    } else {
                        this.jumpOffset = -80;
                    }
                }

                --this.jumpBonusCntr;
            }

            ++this.slideCntr;
            if (this.slideCntr == 3) {
                this.slideCntr = 0;
            }

            for(int var8 = 0; var8 < Math.abs(this.ySpeed) / 10; ++var8) {
                int var9 = 0;
                if (this.ySpeed != 0) {
                    var9 = this.ySpeed / Math.abs(this.ySpeed);
                }

                if (this.collisionDetection(this.xPos, this.yPos + var9)) {
                    this.yPos += var9;
                    this.mGroundedFlag = false;
                    if (var3 == -30) {
                        var7 = this.myParent.tileY + this.yPos / 12;
                        if ((this.myParent.tileMap[var7][var6] & 64) == 0) {
                            this.ySpeed /= 2;
                            if (this.ySpeed <= 10 && this.ySpeed >= -10) {
                                this.ySpeed = 0;
                            }
                        }
                    }
                } else {
                    if (this.mCDRampFlag && this.xSpeed < 10 && this.slideCntr == 0) {
                        byte var10 = 1;
                        if (this.collisionDetection(this.xPos + var10, this.yPos + var9)) {
                            this.xPos += var10;
                            this.yPos += var9;
                            this.mCDRampFlag = false;
                        } else if (this.collisionDetection(this.xPos - var10, this.yPos + var9)) {
                            this.xPos -= var10;
                            this.yPos += var9;
                            this.mCDRampFlag = false;
                        }
                    }

                    if (var9 <= 0 && (!var5 || var9 >= 0)) {
                        if (var9 < 0 || var5 && var9 > 0) {
                            if (var5) {
                                this.ySpeed = -6;
                            } else {
                                this.ySpeed = 6;
                            }
                        }
                    } else {
                        this.ySpeed = this.ySpeed * -1 / 2;
                        this.mGroundedFlag = true;
                        if (this.mCDRubberFlag && (this.direction & 8) != 0) {
                            this.mCDRubberFlag = false;
                            if (var5) {
                                this.jumpOffset += 10;
                            } else {
                                this.jumpOffset += -10;
                            }
                        } else if (this.jumpBonusCntr == 0) {
                            this.jumpOffset = 0;
                        }

                        if (this.ySpeed < 10 && this.ySpeed > -10) {
                            if (var5) {
                                this.ySpeed = -10;
                            } else {
                                this.ySpeed = 10;
                            }
                        }
                    }
                }
            }

            if (var5) {
                if (var2 == -2 && this.ySpeed < var3) {
                    this.ySpeed += var2;
                    if (this.ySpeed > var3) {
                        this.ySpeed = var3;
                    }
                } else if (!this.mGroundedFlag && this.ySpeed > var3) {
                    this.ySpeed += var2;
                    if (this.ySpeed < var3) {
                        this.ySpeed = var3;
                    }
                }
            } else if (var2 == -2 && this.ySpeed > var3) {
                this.ySpeed += var2;
                if (this.ySpeed < var3) {
                    this.ySpeed = var3;
                }
            } else if (!this.mGroundedFlag && this.ySpeed < var3) {
                this.ySpeed += var2;
                if (this.ySpeed > var3) {
                    this.ySpeed = var3;
                }
            }

            if (this.speedBonusCntr != 0) {
                var4 = 100;
                --this.speedBonusCntr;
            } else {
                var4 = 50;
            }

            if ((this.direction & 2) != 0 && this.xSpeed < var4) {
                this.xSpeed += 6;
            } else if ((this.direction & 1) != 0 && this.xSpeed > -var4) {
                this.xSpeed -= 6;
            } else if (this.xSpeed > 0) {
                this.xSpeed -= 4;
            } else if (this.xSpeed < 0) {
                this.xSpeed += 4;
            }

            if (this.ballSize == 16 && this.jumpBonusCntr == 0) {
                if (var5) {
                    this.jumpOffset += 5;
                } else {
                    this.jumpOffset += -5;
                }
            }

            if (this.mGroundedFlag && (this.direction & 8) != 0) {
                if (var5) {
                    this.ySpeed = 67 + this.jumpOffset;
                } else {
                    this.ySpeed = -67 + this.jumpOffset;
                }

                this.mGroundedFlag = false;
            }

            int var20 = Math.abs(this.xSpeed);
            int var21 = var20 / 10;

            for(int var11 = 0; var11 < var21; ++var11) {
                int var12 = 0;
                if (this.xSpeed != 0) {
                    var12 = this.xSpeed < 0 ? -1 : 1;
                }

                if (this.collisionDetection(this.xPos + var12, this.yPos)) {
                    this.xPos += var12;
                } else if (this.mCDRampFlag) {
                    this.mCDRampFlag = false;
                    byte var13 = 0;
                    if (var5) {
                        var13 = 1;
                    } else {
                        var13 = -1;
                    }

                    if (this.collisionDetection(this.xPos + var12, this.yPos + var13)) {
                        this.xPos += var12;
                        this.yPos += var13;
                    } else if (this.collisionDetection(this.xPos + var12, this.yPos - var13)) {
                        this.xPos += var12;
                        this.yPos -= var13;
                    }
                }
            }

            this.xOffset = this.xPos - var1;
            if (this.xPos > 156 + this.ballSize) {
                this.xPos -= 156;
            }

            if (this.xPos - this.ballSize < 0) {
                this.xPos += 156;
            }

        }
    }

    public void paint(Graphics var1) {
        if (this.ballState == 2) {
            var1.drawImage(this.poppedImage, this.xPos - 6, this.yPos - 6, 20);
            if (this.xPos > 144) {
                var1.drawImage(this.poppedImage, this.xPos - 156 - 6, this.yPos - 6, 20);
            }
        } else {
            var1.drawImage(this.ballImage, this.xPos - this.mHalfBallSize, this.yPos - this.mHalfBallSize, 20);
            if (this.xPos > 156 - this.ballSize) {
                var1.drawImage(this.ballImage, this.xPos - 156 - this.mHalfBallSize, this.yPos - this.mHalfBallSize, 20);
            }
        }

        this.dirtyTiles();
    }

    public void dirtyTiles() {
        int var1 = (this.xPos - this.mHalfBallSize) / 12;
        int var2 = (this.yPos - this.mHalfBallSize) / 12;
        int var3 = (this.xPos - 1 + this.mHalfBallSize) / 12 + 1;
        int var4 = (this.yPos - 1 + this.mHalfBallSize) / 12 + 1;
        if (var2 < 0) {
            var2 = 0;
        }

        if (var4 > 8) {
            var4 = 8;
        }

        int var5 = 0;
        int var6 = 0;

        for(int var7 = var1; var7 < var3; ++var7) {
            for(int var8 = var2; var8 < var4; ++var8) {
                if (var7 * 12 >= 156) {
                    var5 = this.myParent.tileX + var7 - 13;
                    var6 = this.myParent.tileY + var8;
                } else if (this.xPos < this.myParent.divisorLine) {
                    var5 = this.myParent.tileX + var7;
                    var6 = this.myParent.tileY + var8;
                } else {
                    var5 = this.myParent.divTileX - 13 - this.myParent.divisorLine / 12 + var7;
                    var6 = this.myParent.divTileY + var8;
                }

                this.myParent.tileMap[var6][var5] = (short)(this.myParent.tileMap[var6][var5] | 128);
            }
        }

    }
}
 