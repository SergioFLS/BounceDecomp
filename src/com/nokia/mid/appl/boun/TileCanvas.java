// Decompiled with: FernFlower
// Class Version: 1
package com.nokia.mid.appl.boun;

import com.nokia.mid.ui.DirectGraphics;
import com.nokia.mid.ui.DirectUtils;
import com.nokia.mid.ui.FullCanvas;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public abstract class TileCanvas extends FullCanvas {
    private static final boolean SCR_DEBUG = false;
    public int tileX;
    public int tileY;
    public int divTileX;
    public int divTileY;
    public int divisorLine;
    public int rightDrawEdge;
    public int leftDrawEdge;
    public boolean scrollFlag;
    public int scrollOffset;
    public int mExitPos;
    protected Image buffer;
    public Graphics bufferGraphics = null;
    public static final int LEFT_START_POS = 14;
    public static final int RIGHT_START_POS = 142;
    public static final int SCREEN_WIDTH = 128;
    public static final int SCREEN_HEIGHT = 128;
    public static final int BUFFER_WIDTH = 156;
    public static final int BUFFER_HEIGHT = 96;
    public static final int BUFFER_TILE_WIDTH = 13;
    public static final int BUFFER_TILE_HEIGHT = 8;
    public static final int TILE_SIZE = 12;
    public static final int SPIKE_SIZE = 24;
    public static final int TILE_DIRTY = 128;
    public static final int TILE_DIRTY_MASK = 65407;
    public static final int X = 0;
    public static final int Y = 1;
    public static final int ID_EMPTY_SPACE = 0;
    public static final int ID_BRICK_RED = 1;
    public static final int ID_BRICK_BLUE = 2;
    public static final int ID_SPIKE_FLOOR = 3;
    public static final int ID_SPIKE_LEFT_WALL = 4;
    public static final int ID_SPIKE_CEILING = 5;
    public static final int ID_SPIKE_RIGHT_WALL = 6;
    public static final int ID_RESPAWN_GEM = 7;
    public static final int ID_RESPAWN_INDICATOR = 8;
    public static final int ID_EXIT_TILE = 9;
    public static final int ID_MOVING_SPIKE_TILE = 10;
    public static final int ID_HOOP_ACTIVE_VERT_TOP = 13;
    public static final int ID_HOOP_ACTIVE_VERT_BOTTOM = 14;
    public static final int ID_HOOP_ACTIVE_HORIZ_LEFT = 15;
    public static final int ID_HOOP_ACTIVE_HORIZ_RIGHT = 16;
    public static final int ID_HOOP_INACTIVE_VERT_TOP = 17;
    public static final int ID_HOOP_INACTIVE_VERT_BOTTOM = 18;
    public static final int ID_HOOP_INACTIVE_HORIZ_LEFT = 19;
    public static final int ID_HOOP_INACTIVE_HORIZ_RIGHT = 20;
    public static final int ID_LARGE_HOOP_ACTIVE_VERT_TOP = 21;
    public static final int ID_LARGE_HOOP_ACTIVE_VERT_BOTTOM = 22;
    public static final int ID_LARGE_HOOP_ACTIVE_HORIZ_LEFT = 23;
    public static final int ID_LARGE_HOOP_ACTIVE_HORIZ_RIGHT = 24;
    public static final int ID_LARGE_HOOP_INACTIVE_VERT_TOP = 25;
    public static final int ID_LARGE_HOOP_INACTIVE_VERT_BOTTOM = 26;
    public static final int ID_LARGE_HOOP_INACTIVE_HORIZ_LEFT = 27;
    public static final int ID_LARGE_HOOP_INACTIVE_HORIZ_RIGHT = 28;
    public static final int ID_EXTRA_LIFE = 29;
    public static final int ID_TRIANGLE_TOP_LEFT = 30;
    public static final int ID_TRIANGLE_TOP_RIGHT = 31;
    public static final int ID_TRIANGLE_BOT_RIGHT = 32;
    public static final int ID_TRIANGLE_BOT_LEFT = 33;
    public static final int ID_RUBBER_TRIANGLE_TOP_LEFT = 34;
    public static final int ID_RUBBER_TRIANGLE_TOP_RIGHT = 35;
    public static final int ID_RUBBER_TRIANGLE_BOT_RIGHT = 36;
    public static final int ID_RUBBER_TRIANGLE_BOT_LEFT = 37;
    public static final int ID_SPEED = 38;
    public static final int ID_DEFLATOR_FLOOR = 39;
    public static final int ID_DEFLATOR_LEFT_WALL = 40;
    public static final int ID_DEFLATOR_CEILING = 41;
    public static final int ID_DEFLATOR_RIGHT_WALL = 42;
    public static final int ID_INFLATOR_FLOOR = 43;
    public static final int ID_INFLATOR_LEFT_WALL = 44;
    public static final int ID_INFLATOR_CEILING = 45;
    public static final int ID_INFLATOR_RIGHT_WALL = 46;
    public static final int ID_GRAVITY_FLOOR = 47;
    public static final int ID_GRAVITY_LEFT_WALL = 48;
    public static final int ID_GRAVITY_CEILING = 49;
    public static final int ID_GRAVITY_RIGHT_WALL = 50;
    public static final int ID_JUMP_FLOOR = 51;
    public static final int ID_JUMP_LEFT_WALL = 52;
    public static final int ID_JUMP_CEILING = 53;
    public static final int ID_JUMP_RIGHT_WALL = 54;
    public static final int ID_WATER_FLAG = 64;
    public static final int IMG_BRICKS_RED_NORMAL = 0;
    public static final int IMG_BRICKS_BLUE_RUBBER = 1;
    public static final int IMG_SPIKES_UPWARD = 2;
    public static final int IMG_SPIKES_DOWNWARD = 3;
    public static final int IMG_SPIKES_LEFT = 4;
    public static final int IMG_SPIKES_RIGHT = 5;
    public static final int IMG_SPIKES_WET_UPWARD = 6;
    public static final int IMG_SPIKES_WET_DOWNWARD = 7;
    public static final int IMG_SPIKES_WET_LEFT = 8;
    public static final int IMG_SPIKES_WET_RIGHT = 9;
    public static final int IMG_RESPAWN_GEM = 10;
    public static final int IMG_RESPAWN_INDICATOR = 11;
    public static final int IMG_EXIT = 12;
    public static final int IMG_HOOP_ACTIVE_HORIZ_TOP_LEFT_LARGE = 13;
    public static final int IMG_HOOP_ACTIVE_HORIZ_BOT_LEFT_LARGE = 14;
    public static final int IMG_HOOP_ACTIVE_HORIZ_TOP_RIGHT_LARGE = 15;
    public static final int IMG_HOOP_ACTIVE_HORIZ_BOT_RIGHT_LARGE = 16;
    public static final int IMG_HOOP_ACTIVE_HORIZ_TOP_LEFT_SMALL = 17;
    public static final int IMG_HOOP_ACTIVE_HORIZ_BOT_LEFT_SMALL = 18;
    public static final int IMG_HOOP_ACTIVE_HORIZ_TOP_RIGHT_SMALL = 19;
    public static final int IMG_HOOP_ACTIVE_HORIZ_BOT_RIGHT_SMALL = 20;
    public static final int IMG_HOOP_INACTIVE_HORIZ_TOP_LEFT_LARGE = 21;
    public static final int IMG_HOOP_INACTIVE_HORIZ_BOT_LEFT_LARGE = 22;
    public static final int IMG_HOOP_INACTIVE_HORIZ_TOP_RIGHT_LARGE = 23;
    public static final int IMG_HOOP_INACTIVE_HORIZ_BOT_RIGHT_LARGE = 24;
    public static final int IMG_HOOP_INACTIVE_HORIZ_TOP_LEFT_SMALL = 25;
    public static final int IMG_HOOP_INACTIVE_HORIZ_BOT_LEFT_SMALL = 26;
    public static final int IMG_HOOP_INACTIVE_HORIZ_TOP_RIGHT_SMALL = 27;
    public static final int IMG_HOOP_INACTIVE_HORIZ_BOT_RIGHT_SMALL = 28;
    public static final int IMG_HOOP_ACTIVE_VERT_TOP_LEFT_LARGE = 29;
    public static final int IMG_HOOP_ACTIVE_VERT_BOT_LEFT_LARGE = 30;
    public static final int IMG_HOOP_ACTIVE_VERT_TOP_RIGHT_LARGE = 31;
    public static final int IMG_HOOP_ACTIVE_VERT_BOT_RIGHT_LARGE = 32;
    public static final int IMG_HOOP_ACTIVE_VERT_TOP_LEFT_SMALL = 33;
    public static final int IMG_HOOP_ACTIVE_VERT_BOT_LEFT_SMALL = 34;
    public static final int IMG_HOOP_ACTIVE_VERT_TOP_RIGHT_SMALL = 35;
    public static final int IMG_HOOP_ACTIVE_VERT_BOT_RIGHT_SMALL = 36;
    public static final int IMG_HOOP_INACTIVE_VERT_TOP_LEFT_LARGE = 37;
    public static final int IMG_HOOP_INACTIVE_VERT_BOT_LEFT_LARGE = 38;
    public static final int IMG_HOOP_INACTIVE_VERT_TOP_RIGHT_LARGE = 39;
    public static final int IMG_HOOP_INACTIVE_VERT_BOT_RIGHT_LARGE = 40;
    public static final int IMG_HOOP_INACTIVE_VERT_TOP_LEFT_SMALL = 41;
    public static final int IMG_HOOP_INACTIVE_VERT_BOT_LEFT_SMALL = 42;
    public static final int IMG_HOOP_INACTIVE_VERT_TOP_RIGHT_SMALL = 43;
    public static final int IMG_HOOP_INACTIVE_VERT_BOT_RIGHT_SMALL = 44;
    public static final int IMG_EXTRA_LIFE = 45;
    public static final int IMG_MOVING_SPIKE = 46;
    public static final int IMG_SMALL_BALL = 47;
    public static final int IMG_POPPED_BALL = 48;
    public static final int IMG_LARGE_BALL = 49;
    public static final int IMG_DEFLATOR = 50;
    public static final int IMG_INFLATOR = 51;
    public static final int IMG_GRAVITY = 52;
    public static final int IMG_SPEED = 53;
    public static final int IMG_JUMP = 54;
    public static final int IMG_TRI_BOT_RIGHT = 55;
    public static final int IMG_TRI_TOP_RIGHT = 56;
    public static final int IMG_TRI_TOP_LEFT = 57;
    public static final int IMG_TRI_BOT_LEFT = 58;
    public static final int IMG_TRI_WET_BOT_RIGHT = 59;
    public static final int IMG_TRI_WET_TOP_RIGHT = 60;
    public static final int IMG_TRI_WET_TOP_LEFT = 61;
    public static final int IMG_TRI_WET_BOT_LEFT = 62;
    public static final int IMG_TRI_RUBBER_BOT_RIGHT = 63;
    public static final int IMG_TRI_RUBBER_TOP_RIGHT = 64;
    public static final int IMG_TRI_RUBBER_TOP_LEFT = 65;
    public static final int IMG_TRI_RUBBER_BOT_LEFT = 66;
    public static final int MAX_TILE_IMAGES = 67;
    public static final int OPAQUE = -16777216;
    public static final int BACKGROUND_COLOUR = 11591920;
    public static final int WATER_COLOUR = 1073328;
    public static final int MAX_LEVEL = 11;
    private Image[] tileImages;
    private Image tmpTileImage;
    private Graphics tmpTileImageG;
    Vector hoopImageList;
    Vector hoopXPosList;
    Vector hoopYPosList;
    public int mLevelNum;
    public String mLevelNumStr;
    public String mLevelCompletedStr;
    public boolean mLoadLevelFlag;
    public int mStartPosX;
    public int mStartPosY;
    public int mBallSize;
    public int mExitPosX;
    public int mExitPosY;
    public short[][] tileMap;
    public int mTileMapWidth;
    public int mTileMapHeight;
    public int mTotalNumRings;
    public int mNumMoveObj;
    public short[][] mMOTopLeft;
    public short[][] mMOBotRight;
    public short[][] mMODirection;
    public short[][] mMOOffset;
    public Image[] mMOImgPtr;
    public Graphics[] mMOImgGraphics;
    public boolean[] mMODrawFlag;
    public Image mSpikeImgPtr;
    public Image mUILife;
    public Image mUIRing;
    public int mTopLeftTileCol;
    public int mTopLeftTileRow;
    public int mBotRightTileCol;
    public int mBotRightTileRow;
    public Image mExitTileImage;
    public Image mImgPtr;
    public int mImageOffset;
    public boolean mOpenFlag;
    public static final int EXIT_TILE_IMAGE_WIDTH = 24;
    public static final int EXIT_TILE_IMAGE_HEIGHT = 24;
    public static final int IMAGE_OFFSET_INC = 4;
    public static final int FLIP_HORIZONTAL = 0;
    public static final int FLIP_VERTICAL = 1;
    public static final int FLIP_BOTH = 2;
    public static final int ROTATE_90 = 3;
    public static final int ROTATE_180 = 4;
    public static final int ROTATE_270 = 5;
    public static final int STATE_INIT = 0;
    public static final int STATE_READY = 1;
    public int mState = 0;
    public static final int MINIMUMSCROLLDELAY = 40;
    protected int mWidth = 0;
    protected int mHeight = 0;
    protected boolean mRedrawAll = false;
    protected Display mDisplay;
    private TileCanvas.GameTimer mGameTimer = null;

    public TileCanvas(Display var1) {
        this.mDisplay = var1;
        this.mWidth = super.getWidth();
        this.mHeight = super.getHeight();
        this.scrollFlag = true;
        this.divisorLine = 156;
        this.rightDrawEdge = 142;
        this.leftDrawEdge = 14;
        this.buffer = Image.createImage(156, 96);
        this.tmpTileImage = Image.createImage(12, 12);
        this.tmpTileImageG = this.tmpTileImage.getGraphics();
        this.loadTileImages();
        this.mLoadLevelFlag = false;
        this.tileX = 0;
        this.tileY = 0;
        this.mExitPos = -1;
        this.divTileX = this.tileX + 13;
        this.divTileY = this.tileY;
        this.tileMap = null;
        this.hoopImageList = new Vector();
        this.hoopXPosList = new Vector();
        this.hoopYPosList = new Vector();
    }

    public void loadLevel(int var1) {
        Object var2 = null;
        Object var3 = null;
        this.mLoadLevelFlag = false;
        String var4 = "";
        String[] var5 = new String[]{(new Integer(this.mLevelNum)).toString()};
        this.mLevelNumStr = Local.getText(14, var5);
        this.mLevelCompletedStr = Local.getText(15, var5);
        var5[0] = null;
        Object var11 = null;
        if (var1 < 10) {
            var4 = "00" + var1;
        } else if (var1 < 100) {
            var4 = "0" + var1;
        }

        InputStream var9 = this.getClass().getResourceAsStream("/levels/J2MElvl." + var4);
        DataInputStream var10 = new DataInputStream(var9);

        try {
            this.mStartPosX = var10.read();
            this.mStartPosY = var10.read();
            this.mBallSize = var10.read();
            this.mExitPosX = var10.read();
            this.mExitPosY = var10.read();
            this.createExitTileObject(this.mExitPosX, this.mExitPosY, this.mExitPosX + 1, this.mExitPosY + 2, this.tileImages[12]);
            this.mTotalNumRings = var10.read();
            this.mTileMapWidth = var10.read();
            this.mTileMapHeight = var10.read();
            this.tileMap = new short[this.mTileMapHeight][this.mTileMapWidth];

            for(int var6 = 0; var6 < this.mTileMapHeight; ++var6) {
                for(int var7 = 0; var7 < this.mTileMapWidth; ++var7) {
                    this.tileMap[var6][var7] = (short)var10.read();
                }
            }

            this.mNumMoveObj = var10.read();
            if (this.mNumMoveObj != 0) {
                this.createMovingObj(var10);
            }

            var10.close();
        } catch (IOException var8) {
        }

    }

    public static Image manipulateImage(Image var0, int var1) {
        Image var2 = DirectUtils.createImage(var0.getWidth(), var0.getHeight(), 0);
        if (var2 == null) {
            var2 = Image.createImage(var0.getWidth(), var0.getHeight());
        }

        Graphics var3 = var2.getGraphics();
        DirectGraphics var4 = DirectUtils.getDirectGraphics(var3);
        switch(var1) {
        case 0:
            var4.drawImage(var0, 0, 0, 20, 8192);
            break;
        case 1:
            var4.drawImage(var0, 0, 0, 20, 16384);
            break;
        case 2:
            var4.drawImage(var0, 0, 0, 20, 24576);
            break;
        case 3:
            var4.drawImage(var0, 0, 0, 20, 90);
            break;
        case 4:
            var4.drawImage(var0, 0, 0, 20, 180);
            break;
        case 5:
            var4.drawImage(var0, 0, 0, 20, 270);
            break;
        default:
            var3.drawImage(var0, 0, 0, 20);
        }

        return var2;
    }

    public void createMovingObj(DataInputStream var1) throws IOException {
        this.mMOTopLeft = new short[this.mNumMoveObj][2];
        this.mMOBotRight = new short[this.mNumMoveObj][2];
        this.mMODirection = new short[this.mNumMoveObj][2];
        this.mMOOffset = new short[this.mNumMoveObj][2];
        this.mMOImgPtr = new Image[this.mNumMoveObj];
        this.mMOImgGraphics = new Graphics[this.mNumMoveObj];
        this.mMODrawFlag = new boolean[this.mNumMoveObj];

        for(int var4 = 0; var4 < this.mNumMoveObj; ++var4) {
            this.mMOTopLeft[var4][0] = (short)var1.read();
            this.mMOTopLeft[var4][1] = (short)var1.read();
            this.mMOBotRight[var4][0] = (short)var1.read();
            this.mMOBotRight[var4][1] = (short)var1.read();
            this.mMODirection[var4][0] = (short)var1.read();
            this.mMODirection[var4][1] = (short)var1.read();
            int var2 = var1.read();
            int var3 = var1.read();
            this.mMOOffset[var4][0] = (short)var2;
            this.mMOOffset[var4][1] = (short)var3;
            this.mMODrawFlag[var4] = true;
        }

        this.mSpikeImgPtr = Image.createImage(24, 24);
        Graphics var5 = this.mSpikeImgPtr.getGraphics();
        var5.drawImage(this.tileImages[46], 0, 0, 20);
        var5.drawImage(manipulateImage(this.tileImages[46], 0), 12, 0, 20);
        var5.drawImage(manipulateImage(this.tileImages[46], 4), 12, 12, 20);
        var5.drawImage(manipulateImage(this.tileImages[46], 1), 0, 12, 20);
        Object var6 = null;
    }

    public void disposeLevel() {
        for(int var1 = 0; var1 < this.mNumMoveObj; ++var1) {
            this.mMOImgPtr[var1] = null;
            this.mMOImgGraphics[var1] = null;
        }

        this.mMOImgPtr = null;
        this.mMOImgGraphics = null;
        this.tileMap = null;
        Runtime.getRuntime().gc();
    }

    public void updateMovingSpikeObj() {
        for(int var1 = 0; var1 < this.mNumMoveObj; ++var1) {
            int var2 = this.mMOOffset[var1][0];
            int var3 = this.mMOOffset[var1][1];
            this.mMOOffset[var1][0] += this.mMODirection[var1][0];
            int var6 = (this.mMOBotRight[var1][0] - this.mMOTopLeft[var1][0] - 2) * 12;
            int var7 = (this.mMOBotRight[var1][1] - this.mMOTopLeft[var1][1] - 2) * 12;
            if (this.mMOOffset[var1][0] < 0) {
                this.mMOOffset[var1][0] = 0;
            } else if (this.mMOOffset[var1][0] > var6) {
                this.mMOOffset[var1][0] = (short)var6;
            }

            if (this.mMOOffset[var1][0] == 0 || this.mMOOffset[var1][0] == var6) {
                this.mMODirection[var1][0] = (short)(-this.mMODirection[var1][0]);
            }

            this.mMOOffset[var1][1] += this.mMODirection[var1][1];
            if (this.mMOOffset[var1][1] < 0) {
                this.mMOOffset[var1][1] = 0;
            } else if (this.mMOOffset[var1][1] > var7) {
                this.mMOOffset[var1][1] = (short)var7;
            }

            if (this.mMOOffset[var1][1] == 0 || this.mMOOffset[var1][1] == var7) {
                this.mMODirection[var1][1] = (short)(this.mMODirection[var1][1] * -1);
            }

            if (this.mMODrawFlag[var1]) {
                int var4 = this.mMOOffset[var1][0];
                int var5 = this.mMOOffset[var1][1];
                if (var4 < var2) {
                    short var8 = (short)var4;
                    var4 = var2;
                    var2 = var8;
                }

                if (var5 < var3) {
                    short var18 = (short)var5;
                    var5 = var3;
                    var3 = var18;
                }

                var4 += 23;
                var5 += 23;
                var2 /= 12;
                var3 /= 12;
                var4 = var4 / 12 + 1;
                var5 = var5 / 12 + 1;

                for(int var19 = var2; var19 < var4; ++var19) {
                    for(int var9 = var3; var9 < var5; ++var9) {
                        int var10 = this.mMOTopLeft[var1][0] + var19;
                        int var11 = this.mMOTopLeft[var1][1] + var9;
                        this.tileMap[var11][var10] = (short)(this.tileMap[var11][var10] | 128);
                    }
                }
            }

            this.mMODrawFlag[var1] = false;
        }

    }

    public int findSpikeIndex(int var1, int var2) {
        for(int var3 = 0; var3 < this.mNumMoveObj; ++var3) {
            if (this.mMOTopLeft[var3][0] <= var1 && this.mMOBotRight[var3][0] > var1 && this.mMOTopLeft[var3][1] <= var2 && this.mMOBotRight[var3][1] > var2) {
                return var3;
            }
        }

        return -1;
    }

    public void drawTile(int var1, int var2, int var3, int var4) {
        if (this.bufferGraphics == null) {
            this.bufferGraphics = this.buffer.getGraphics();
        }

        if ((this.tileMap[var2][var1] & 128) != 0) {
            this.tileMap[var2][var1] = (short)(this.tileMap[var2][var1] & 65407);
        }

        int var5 = this.tileMap[var2][var1];
        boolean var6 = (var5 & 64) != 0;
        if (var6) {
            var5 &= -65;
        }

        this.bufferGraphics.setColor(var6 ? 0x1060B0 : 0xB0E0F0);
        switch(var5) {
        case 0:
            this.bufferGraphics.fillRect(var3, var4, 12, 12);
            break;
        case 1:
            this.bufferGraphics.drawImage(this.tileImages[0], var3, var4, 20);
            break;
        case 2:
            this.bufferGraphics.drawImage(this.tileImages[1], var3, var4, 20);
            break;
        case 3:
            if (var6) {
                this.bufferGraphics.drawImage(this.tileImages[6], var3, var4, 20);
            } else {
                this.bufferGraphics.drawImage(this.tileImages[2], var3, var4, 20);
            }
            break;
        case 4:
            if (var6) {
                this.bufferGraphics.drawImage(this.tileImages[9], var3, var4, 20);
            } else {
                this.bufferGraphics.drawImage(this.tileImages[5], var3, var4, 20);
            }
            break;
        case 5:
            if (var6) {
                this.bufferGraphics.drawImage(this.tileImages[7], var3, var4, 20);
            } else {
                this.bufferGraphics.drawImage(this.tileImages[3], var3, var4, 20);
            }
            break;
        case 6:
            if (var6) {
                this.bufferGraphics.drawImage(this.tileImages[8], var3, var4, 20);
            } else {
                this.bufferGraphics.drawImage(this.tileImages[4], var3, var4, 20);
            }
            break;
        case 7:
            this.bufferGraphics.drawImage(this.tileImages[10], var3, var4, 20);
            break;
        case 8:
            this.bufferGraphics.drawImage(this.tileImages[11], var3, var4, 20);
            break;
        case 9:
            int var12 = (var1 - this.mTopLeftTileCol) * 12;
            int var13 = (var2 - this.mTopLeftTileRow) * 12;
            this.bufferGraphics.drawImage(this.mExitTileImage, var3 - var12, var4 - var13, 20);
            this.mExitPos = var3 - var12 + 12 - 1;
            break;
        case 10:
            int var7 = this.findSpikeIndex(var1, var2);
            if (var7 != -1) {
                if (!this.mMODrawFlag[var7]) {
                    this.mMODrawFlag[var7] = true;
                }

                int var8 = (var1 - this.mMOTopLeft[var7][0]) * 12;
                int var9 = (var2 - this.mMOTopLeft[var7][1]) * 12;
                int var10 = this.mMOOffset[var7][0] - var8;
                int var11 = this.mMOOffset[var7][1] - var9;
                if ((var10 <= -36 || var10 >= 12) && (var11 <= -36 || var11 >= 12)) {
                    this.bufferGraphics.setColor(0xB0E0F0);
                    this.bufferGraphics.fillRect(var3, var4, 12, 12);
                } else {
                    this.tmpTileImageG.setColor(0xB0E0F0);
                    this.tmpTileImageG.fillRect(0, 0, 12, 12);
                    this.tmpTileImageG.drawImage(this.mSpikeImgPtr, var10, var11, 20);
                    this.bufferGraphics.drawImage(this.tmpTileImage, var3, var4, 20);
                }
            }
        case 11:
        case 12:
        default:
            break;
        case 13:
            this.bufferGraphics.fillRect(var3, var4, 12, 12);
            this.bufferGraphics.drawImage(this.tileImages[35], var3, var4, 20);
            this.add2HoopList(this.tileImages[33], var3, var4);
            break;
        case 14:
            this.bufferGraphics.fillRect(var3, var4, 12, 12);
            this.bufferGraphics.drawImage(this.tileImages[36], var3, var4, 20);
            this.add2HoopList(this.tileImages[34], var3, var4);
            break;
        case 15:
            this.bufferGraphics.fillRect(var3, var4, 12, 12);
            this.bufferGraphics.drawImage(this.tileImages[17], var3, var4, 20);
            this.add2HoopList(this.tileImages[18], var3, var4);
            break;
        case 16:
            this.bufferGraphics.fillRect(var3, var4, 12, 12);
            this.bufferGraphics.drawImage(this.tileImages[19], var3, var4, 20);
            this.add2HoopList(this.tileImages[20], var3, var4);
            break;
        case 17:
            this.bufferGraphics.fillRect(var3, var4, 12, 12);
            this.bufferGraphics.drawImage(this.tileImages[43], var3, var4, 20);
            this.add2HoopList(this.tileImages[41], var3, var4);
            break;
        case 18:
            this.bufferGraphics.fillRect(var3, var4, 12, 12);
            this.bufferGraphics.drawImage(this.tileImages[44], var3, var4, 20);
            this.add2HoopList(this.tileImages[42], var3, var4);
            break;
        case 19:
            this.bufferGraphics.fillRect(var3, var4, 12, 12);
            this.bufferGraphics.drawImage(this.tileImages[25], var3, var4, 20);
            this.add2HoopList(this.tileImages[26], var3, var4);
            break;
        case 20:
            this.bufferGraphics.fillRect(var3, var4, 12, 12);
            this.bufferGraphics.drawImage(this.tileImages[27], var3, var4, 20);
            this.add2HoopList(this.tileImages[28], var3, var4);
            break;
        case 21:
            this.bufferGraphics.fillRect(var3, var4, 12, 12);
            this.bufferGraphics.drawImage(this.tileImages[31], var3, var4, 20);
            this.add2HoopList(this.tileImages[29], var3, var4);
            break;
        case 22:
            this.bufferGraphics.fillRect(var3, var4, 12, 12);
            this.bufferGraphics.drawImage(this.tileImages[32], var3, var4, 20);
            this.add2HoopList(this.tileImages[30], var3, var4);
            break;
        case 23:
            this.bufferGraphics.fillRect(var3, var4, 12, 12);
            this.bufferGraphics.drawImage(this.tileImages[13], var3, var4, 20);
            this.add2HoopList(this.tileImages[14], var3, var4);
            break;
        case 24:
            this.bufferGraphics.fillRect(var3, var4, 12, 12);
            this.bufferGraphics.drawImage(this.tileImages[15], var3, var4, 20);
            this.add2HoopList(this.tileImages[16], var3, var4);
            break;
        case 25:
            this.bufferGraphics.fillRect(var3, var4, 12, 12);
            this.bufferGraphics.drawImage(this.tileImages[39], var3, var4, 20);
            this.add2HoopList(this.tileImages[37], var3, var4);
            break;
        case 26:
            this.bufferGraphics.fillRect(var3, var4, 12, 12);
            this.bufferGraphics.drawImage(this.tileImages[40], var3, var4, 20);
            this.add2HoopList(this.tileImages[38], var3, var4);
            break;
        case 27:
            this.bufferGraphics.fillRect(var3, var4, 12, 12);
            this.bufferGraphics.drawImage(this.tileImages[21], var3, var4, 20);
            this.add2HoopList(this.tileImages[22], var3, var4);
            break;
        case 28:
            this.bufferGraphics.fillRect(var3, var4, 12, 12);
            this.bufferGraphics.drawImage(this.tileImages[23], var3, var4, 20);
            this.add2HoopList(this.tileImages[24], var3, var4);
            break;
        case 29:
            this.bufferGraphics.drawImage(this.tileImages[45], var3, var4, 20);
            break;
        case 30:
            if (var6) {
                this.bufferGraphics.drawImage(this.tileImages[61], var3, var4, 20);
            } else {
                this.bufferGraphics.drawImage(this.tileImages[57], var3, var4, 20);
            }
            break;
        case 31:
            if (var6) {
                this.bufferGraphics.drawImage(this.tileImages[60], var3, var4, 20);
            } else {
                this.bufferGraphics.drawImage(this.tileImages[56], var3, var4, 20);
            }
            break;
        case 32:
            if (var6) {
                this.bufferGraphics.drawImage(this.tileImages[59], var3, var4, 20);
            } else {
                this.bufferGraphics.drawImage(this.tileImages[55], var3, var4, 20);
            }
            break;
        case 33:
            if (var6) {
                this.bufferGraphics.drawImage(this.tileImages[62], var3, var4, 20);
            } else {
                this.bufferGraphics.drawImage(this.tileImages[58], var3, var4, 20);
            }
            break;
        case 34:
            this.bufferGraphics.fillRect(var3, var4, 12, 12);
            this.bufferGraphics.drawImage(this.tileImages[65], var3, var4, 20);
            break;
        case 35:
            this.bufferGraphics.fillRect(var3, var4, 12, 12);
            this.bufferGraphics.drawImage(this.tileImages[64], var3, var4, 20);
            break;
        case 36:
            this.bufferGraphics.fillRect(var3, var4, 12, 12);
            this.bufferGraphics.drawImage(this.tileImages[63], var3, var4, 20);
            break;
        case 37:
            this.bufferGraphics.fillRect(var3, var4, 12, 12);
            this.bufferGraphics.drawImage(this.tileImages[66], var3, var4, 20);
            break;
        case 38:
            this.bufferGraphics.drawImage(this.tileImages[53], var3, var4, 20);
            break;
        case 39:
            this.bufferGraphics.fillRect(var3, var4, 12, 12);
            this.bufferGraphics.drawImage(this.tileImages[50], var3, var4, 20);
            break;
        case 40:
            this.bufferGraphics.fillRect(var3, var4, 12, 12);
            this.bufferGraphics.drawImage(manipulateImage(this.tileImages[50], 5), var3, var4, 20);
            break;
        case 41:
            this.bufferGraphics.fillRect(var3, var4, 12, 12);
            this.bufferGraphics.drawImage(manipulateImage(this.tileImages[50], 4), var3, var4, 20);
            break;
        case 42:
            this.bufferGraphics.fillRect(var3, var4, 12, 12);
            this.bufferGraphics.drawImage(manipulateImage(this.tileImages[50], 3), var3, var4, 20);
            break;
        case 43:
            this.bufferGraphics.fillRect(var3, var4, 12, 12);
            this.bufferGraphics.drawImage(this.tileImages[51], var3, var4, 20);
            break;
        case 44:
            this.bufferGraphics.fillRect(var3, var4, 12, 12);
            this.bufferGraphics.drawImage(manipulateImage(this.tileImages[51], 5), var3, var4, 20);
            break;
        case 45:
            this.bufferGraphics.fillRect(var3, var4, 12, 12);
            this.bufferGraphics.drawImage(manipulateImage(this.tileImages[51], 4), var3, var4, 20);
            break;
        case 46:
            this.bufferGraphics.fillRect(var3, var4, 12, 12);
            this.bufferGraphics.drawImage(manipulateImage(this.tileImages[51], 3), var3, var4, 20);
            break;
        case 47:
            this.bufferGraphics.drawImage(this.tileImages[52], var3, var4, 20);
            break;
        case 48:
            this.bufferGraphics.drawImage(manipulateImage(this.tileImages[52], 5), var3, var4, 20);
            break;
        case 49:
            this.bufferGraphics.drawImage(manipulateImage(this.tileImages[52], 4), var3, var4, 20);
            break;
        case 50:
            this.bufferGraphics.drawImage(manipulateImage(this.tileImages[52], 3), var3, var4, 20);
            break;
        case 51:
            this.bufferGraphics.drawImage(this.tileImages[54], var3, var4, 20);
            break;
        case 52:
            this.bufferGraphics.drawImage(manipulateImage(this.tileImages[54], 5), var3, var4, 20);
            break;
        case 53:
            this.bufferGraphics.drawImage(manipulateImage(this.tileImages[54], 4), var3, var4, 20);
            break;
        case 54:
            this.bufferGraphics.drawImage(manipulateImage(this.tileImages[54], 3), var3, var4, 20);
        }

    }

    public void add2HoopList(Image var1, int var2, int var3) {
        this.hoopImageList.addElement(var1);
        this.hoopXPosList.addElement(new Integer(var2));
        this.hoopYPosList.addElement(new Integer(var3));
    }

    public void createNewBuffer() {
        for(int var1 = 0; var1 < 13; ++var1) {
            for(int var2 = 0; var2 < 8; ++var2) {
                this.drawTile(this.tileX + var1, this.tileY + var2, var1 * 12, var2 * 12);
            }
        }

    }

    public void cleanBuffer(boolean var1) {
        int var2 = this.tileX;
        int var3 = this.tileY;

        for(int var4 = 0; var4 < 13; ++var4) {
            if (var4 * 12 >= this.divisorLine && var2 >= this.tileX) {
                var2 = this.divTileX - 13;
            }

            for(int var5 = 0; var5 < 8; ++var5) {
                if ((this.tileMap[var3][var2] & 128) != 0) {
                    this.tileMap[var3][var2] = (short)(this.tileMap[var3][var2] & 65407);
                    if (var1) {
                        this.drawTile(var2, var3, var4 * 12, var5 * 12);
                    }
                }

                ++var3;
            }

            var3 = this.tileY;
            ++var2;
        }

    }

    public void scrollBuffer(int var1, int var2, int var3) {
        if (this.rightDrawEdge < 0) {
            this.rightDrawEdge += 156;
        }

        if (this.rightDrawEdge > this.divisorLine && this.rightDrawEdge <= this.divisorLine + 12) {
            if (this.tileX + this.divisorLine / 12 >= this.mTileMapWidth) {
                this.leftDrawEdge -= var2;
                this.rightDrawEdge -= var2;
                if (this.rightDrawEdge < 0) {
                    this.rightDrawEdge += 156;
                }

                if (this.scrollFlag) {
                    this.scrollFlag = false;
                    this.scrollOffset = this.rightDrawEdge - 64;
                    if (this.scrollOffset < var3) {
                        this.scrollOffset += 156;
                    }
                }
            } else {
                if (this.divisorLine >= 156) {
                    this.divisorLine = 0;
                    this.tileX += 13;
                }

                if (this.rightDrawEdge >= 156) {
                    this.rightDrawEdge -= 156;
                }

                int var4 = this.divisorLine;
                this.divisorLine += 12;
                ++this.divTileX;

                for(int var5 = 0; var5 < 8; ++var5) {
                    this.drawTile(this.tileX + var4 / 12, this.tileY + var5, var4, var5 * 12);
                }
            }
        } else if (this.rightDrawEdge > 156) {
            this.rightDrawEdge -= 156;
        }

        if (this.leftDrawEdge >= 156) {
            this.leftDrawEdge -= 156;
        }

        if (this.leftDrawEdge < 0) {
            this.leftDrawEdge += 156;
        }

        if (this.leftDrawEdge < this.divisorLine && this.leftDrawEdge >= this.divisorLine - 12) {
            if (this.tileX - (13 - this.divisorLine / 12) <= 0) {
                this.leftDrawEdge -= var2;
                this.rightDrawEdge -= var2;
                if (this.leftDrawEdge >= 156) {
                    this.leftDrawEdge -= 156;
                }

                if (this.scrollFlag) {
                    this.scrollFlag = false;
                    this.scrollOffset = (this.leftDrawEdge + 64) % 156;
                    if (this.scrollOffset < var3) {
                        this.scrollOffset += 156;
                    }
                }
            } else {
                this.divisorLine -= 12;
                int var6 = this.divisorLine;
                --this.divTileX;
                if (this.divisorLine <= 0) {
                    this.divisorLine = 156;
                    this.tileX -= 13;
                }

                for(int var7 = 0; var7 < 8; ++var7) {
                    this.drawTile(this.divTileX - 13, this.divTileY + var7, var6, var7 * 12);
                }
            }
        }

    }

    void testScroll(int var1, int var2) {
        if (!this.scrollFlag) {
            if (this.tileX - (13 - this.divisorLine / 12) <= 0 && var1 >= this.scrollOffset && var1 < this.scrollOffset + 10) {
                this.scrollFlag = true;
                var2 = var1 - this.scrollOffset;
            }

            if (this.tileX + this.divisorLine / 12 >= this.mTileMapWidth && var1 <= this.scrollOffset && var1 > this.scrollOffset - 10) {
                this.scrollFlag = true;
                var2 = var1 - this.scrollOffset;
            }
        }

        if (this.scrollFlag) {
            this.leftDrawEdge += var2;
            this.rightDrawEdge += var2;
        }

    }

    public Image createLargeBallImage(Image var1) {
        Image var2 = DirectUtils.createImage(16, 16, 0);
        if (var2 == null) {
            var2 = Image.createImage(16, 16);
        }

        Graphics var3 = var2.getGraphics();
        DirectGraphics var4 = DirectUtils.getDirectGraphics(var3);
        var3.drawImage(var1, -4, -4, 20);
        var4.drawImage(var1, 8, -4, 20, 8192);
        var4.drawImage(var1, -4, 8, 20, 16384);
        var4.drawImage(var1, 8, 8, 20, 180);
        return var2;
    }

    public Image createExitImage(Image var1) {
        Image var2 = Image.createImage(24, 48);
        Graphics var3 = var2.getGraphics();
        var3.setColor(0xB0E0F0);
        var3.fillRect(0, 0, 24, 48);
        var3.setColor(0xFC9D9E);
        var3.fillRect(4, 0, 16, 48);
        var3.setColor(0xE33A3F);
        var3.fillRect(6, 0, 10, 48);
        var3.setColor(0xC2848E);
        var3.fillRect(10, 0, 4, 48);
        var3.drawImage(var1, 0, 0, 20);
        var3.drawImage(manipulateImage(var1, 0), 12, 0, 20);
        var3.drawImage(manipulateImage(var1, 1), 0, 12, 20);
        var3.drawImage(manipulateImage(var1, 2), 12, 12, 20);
        return var2;
    }

    public void loadTileImages() {
        Image var1 = loadImage("/icons/objects_nm.png");
        this.tileImages = new Image[67];
        this.tileImages[0] = extractImage(var1, 1, 0);
        this.tileImages[1] = extractImage(var1, 1, 2);
        this.tileImages[2] = extractImageBG(var1, 0, 3, -5185296);
        this.tileImages[3] = manipulateImage(this.tileImages[2], 1);
        this.tileImages[4] = manipulateImage(this.tileImages[2], 3);
        this.tileImages[5] = manipulateImage(this.tileImages[2], 5);
        this.tileImages[6] = extractImageBG(var1, 0, 3, -15703888);
        this.tileImages[7] = manipulateImage(this.tileImages[6], 1);
        this.tileImages[8] = manipulateImage(this.tileImages[6], 3);
        this.tileImages[9] = manipulateImage(this.tileImages[6], 5);
        this.tileImages[10] = extractImage(var1, 0, 4);
        this.tileImages[11] = extractImage(var1, 3, 4);
        this.tileImages[12] = this.createExitImage(extractImage(var1, 2, 3));
        this.tileImages[14] = extractImage(var1, 0, 5);
        this.tileImages[13] = manipulateImage(this.tileImages[14], 1);
        this.tileImages[15] = manipulateImage(this.tileImages[13], 0);
        this.tileImages[16] = manipulateImage(this.tileImages[14], 0);
        this.tileImages[18] = extractImage(var1, 1, 5);
        this.tileImages[17] = manipulateImage(this.tileImages[18], 1);
        this.tileImages[19] = manipulateImage(this.tileImages[17], 0);
        this.tileImages[20] = manipulateImage(this.tileImages[18], 0);
        this.tileImages[22] = extractImage(var1, 2, 5);
        this.tileImages[21] = manipulateImage(this.tileImages[22], 1);
        this.tileImages[23] = manipulateImage(this.tileImages[21], 0);
        this.tileImages[24] = manipulateImage(this.tileImages[22], 0);
        this.tileImages[26] = extractImage(var1, 3, 5);
        this.tileImages[25] = manipulateImage(this.tileImages[26], 1);
        this.tileImages[27] = manipulateImage(this.tileImages[25], 0);
        this.tileImages[28] = manipulateImage(this.tileImages[26], 0);
        this.tileImages[29] = manipulateImage(this.tileImages[14], 5);
        this.tileImages[30] = manipulateImage(this.tileImages[29], 1);
        this.tileImages[31] = manipulateImage(this.tileImages[29], 0);
        this.tileImages[32] = manipulateImage(this.tileImages[30], 0);
        this.tileImages[33] = manipulateImage(this.tileImages[18], 5);
        this.tileImages[34] = manipulateImage(this.tileImages[33], 1);
        this.tileImages[35] = manipulateImage(this.tileImages[33], 0);
        this.tileImages[36] = manipulateImage(this.tileImages[34], 0);
        this.tileImages[37] = manipulateImage(this.tileImages[22], 5);
        this.tileImages[38] = manipulateImage(this.tileImages[37], 1);
        this.tileImages[39] = manipulateImage(this.tileImages[37], 0);
        this.tileImages[40] = manipulateImage(this.tileImages[38], 0);
        this.tileImages[41] = manipulateImage(this.tileImages[26], 5);
        this.tileImages[42] = manipulateImage(this.tileImages[41], 1);
        this.tileImages[43] = manipulateImage(this.tileImages[41], 0);
        this.tileImages[44] = manipulateImage(this.tileImages[42], 0);
        this.tileImages[45] = extractImage(var1, 3, 3);
        this.tileImages[46] = extractImage(var1, 1, 3);
        this.tileImages[47] = extractImage(var1, 2, 0);
        this.tileImages[48] = extractImage(var1, 0, 1);
        this.tileImages[49] = this.createLargeBallImage(extractImage(var1, 3, 0));
        this.tileImages[50] = extractImage(var1, 3, 1);
        this.tileImages[51] = extractImage(var1, 2, 4);
        this.tileImages[52] = extractImage(var1, 3, 2);
        this.tileImages[53] = extractImage(var1, 1, 1);
        this.tileImages[54] = extractImage(var1, 2, 2);
        this.tileImages[55] = extractImageBG(var1, 0, 0, -5185296);
        this.tileImages[56] = manipulateImage(this.tileImages[55], 3);
        this.tileImages[57] = manipulateImage(this.tileImages[55], 4);
        this.tileImages[58] = manipulateImage(this.tileImages[55], 5);
        this.tileImages[59] = extractImageBG(var1, 0, 0, -15703888);
        this.tileImages[60] = manipulateImage(this.tileImages[59], 3);
        this.tileImages[61] = manipulateImage(this.tileImages[59], 4);
        this.tileImages[62] = manipulateImage(this.tileImages[59], 5);
        this.tileImages[63] = extractImage(var1, 0, 2);
        this.tileImages[64] = manipulateImage(this.tileImages[63], 3);
        this.tileImages[65] = manipulateImage(this.tileImages[63], 4);
        this.tileImages[66] = manipulateImage(this.tileImages[63], 5);
        this.mUILife = extractImage(var1, 2, 1);
        this.mUIRing = extractImage(var1, 1, 4);
    }

    public void setBallImages(Ball var1) {
        var1.smallBallImage = this.tileImages[47];
        var1.poppedImage = this.tileImages[48];
        var1.largeBallImage = this.tileImages[49];
    }

    public static Image extractImage(Image var0, int var1, int var2) {
        return extractImageBG(var0, var1, var2, 0);
    }

    public static Image extractImageBG(Image var0, int var1, int var2, int var3) {
        Image var4 = DirectUtils.createImage(12, 12, var3);
        if (var4 == null) {
            var4 = Image.createImage(12, 12);
            Graphics var5 = var4.getGraphics();
            var5.setColor(var3);
            var5.fillRect(0, 0, 12, 12);
        }

        Graphics var6 = var4.getGraphics();
        var6.drawImage(var0, -var1 * 12, -var2 * 12, 20);
        return var4;
    }

    public static Image loadImage(String var0) {
        Image var1 = null;

        try {
            var1 = Image.createImage(var0);
        } catch (IOException var3) {
        }

        return var1;
    }

    public Image getImage(int var1) {
        return var1 < 67 ? this.tileImages[var1] : null;
    }

    public void createExitTileObject(int var1, int var2, int var3, int var4, Image var5) {
        this.mTopLeftTileCol = var1;
        this.mTopLeftTileRow = var2;
        this.mBotRightTileCol = var3;
        this.mBotRightTileRow = var4;
        this.mImgPtr = var5;
        this.mExitTileImage = Image.createImage(24, 24);
        this.mImageOffset = 0;
        this.repaintExitTile();
        this.mOpenFlag = false;
    }

    public void repaintExitTile() {
        Graphics var1 = this.mExitTileImage.getGraphics();
        var1.drawImage(this.mImgPtr, 0, 0 - this.mImageOffset, 20);
    }

    public void openExit() {
        this.mImageOffset += 4;
        if (this.mImageOffset >= 24) {
            this.mImageOffset = 24;
            this.mOpenFlag = true;
        }

        this.repaintExitTile();
    }

    public static boolean rectCollide(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
        return var0 <= var6 && var1 <= var7 && var4 <= var2 && var5 <= var3;
    }

    public abstract void run();

    public synchronized void start() {
        if (this.mGameTimer == null) {
            this.mGameTimer = new TileCanvas.GameTimer(this);
        }
    }

    public synchronized void stop() {
        if (this.mGameTimer != null) {
            this.mGameTimer.stop();
            this.mGameTimer = null;
        }
    }

    protected void timerTrigger() {
        this.run();
    }

    protected class GameTimer extends TimerTask {
        TileCanvas parent;
        Timer timer;

        public GameTimer(TileCanvas var2) {
            this.parent = var2;
            this.timer = new Timer();
            this.timer.schedule(this, 0L, 40L);
        }

        public void run() {
            this.parent.timerTrigger();
        }

        void stop() {
            if (this.timer != null) {
                this.cancel();
                this.timer.cancel();
                this.timer = null;
            }
        }
    }
}
 