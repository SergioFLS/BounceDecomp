// Decompiled with: FernFlower
// Class Version: 1
package com.nokia.mid.appl.boun;

import java.io.DataInputStream;
import java.io.InputStream;

public class Local {
    private static Local loc = null;
    public static final short QHJ_BOUN_INSTRUCTIONS_PART_1 = 0;
    public static final short QHJ_BOUN_INSTRUCTIONS_PART_2 = 1;
    public static final short QHJ_BOUN_INSTRUCTIONS_PART_3 = 2;
    public static final short QHJ_BOUN_INSTRUCTIONS_PART_4 = 3;
    public static final short QHJ_BOUN_INSTRUCTIONS_PART_5 = 4;
    public static final short QHJ_BOUN_INSTRUCTIONS_PART_6 = 5;
    public static final short QTJ_BOUN_BACK = 6;
    public static final short QTJ_BOUN_CONGRATULATIONS = 7;
    public static final short QTJ_BOUN_CONTINUE = 8;
    public static final short QTJ_BOUN_EXIT = 9;
    public static final short QTJ_BOUN_GAME_NAME = 10;
    public static final short QTJ_BOUN_GAME_OVER = 11;
    public static final short QTJ_BOUN_HIGH_SCORES = 12;
    public static final short QTJ_BOUN_INSTRUCTIONS = 13;
    public static final short QTJ_BOUN_LEVEL = 14;
    public static final short QTJ_BOUN_LEVEL_COMPLETED = 15;
    public static final short QTJ_BOUN_NEW_GAME = 16;
    public static final short QTJ_BOUN_NEW_HIGH_SCORE = 17;
    public static final short QTJ_BOUN_NEXT = 18;
    public static final short QTJ_BOUN_OK = 19;
    public static final short QTJ_BOUN_PAUSE = 20;
    public static final String phoneLang = System.getProperty("microedition.locale");

    private Local() {
    }

    private static String replace(String var0, String var1, String var2) {
        int var3 = var0.indexOf(var1);
        return var3 >= 0 ? var0.substring(0, var3) + var2 + var0.substring(var3 + var1.length()) : var0;
    }

    public static synchronized String getText(int var0) {
        return getText(var0, (String[])null);
    }

    public static synchronized String getText(int var0, String[] var1) {
        try {
            if (loc == null) {
                loc = new Local();
            }

            InputStream var2 = loc.getClass().getResourceAsStream("/lang." + phoneLang);
            if (var2 == null) {
                var2 = loc.getClass().getResourceAsStream("/lang.xx");
            }

            if (var2 == null) {
                return "NoLang";
            } else {
                DataInputStream var3 = new DataInputStream(var2);
                var3.skipBytes(var0 * 2);
                short var4 = var3.readShort();
                var3.skipBytes(var4 - var0 * 2 - 2);
                String var5 = var3.readUTF();
                var3.close();
                if (var1 != null) {
                    if (var1.length == 1) {
                        var5 = replace(var5, "%U", var1[0]);
                    } else {
                        for(int var6 = 0; var6 < var1.length; ++var6) {
                            var5 = replace(var5, "%" + var6 + "U", var1[var6]);
                        }
                    }
                }

                return var5;
            }
        } catch (Exception var7) {
            return "Err";
        }
    }
}
 