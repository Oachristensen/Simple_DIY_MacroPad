package com.owen.macropadgui;

import commands.MediaKeys;
import javafx.util.Pair;

import java.awt.*;

import static java.awt.event.KeyEvent.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MacroKey {
    private int keyNum;
    private int keyRow;

    private ArrayList<String> releaseFunctionList;
    private ArrayList<String> pressFunctionList;
    private Map<Pair<Integer, Integer>, Integer> keyPressCodeMap;
    private Map<Pair<Integer, Integer>, Integer> keyReleaseCodeMap;


    /*
    Key:
        Input Type: K
        Possible Function: 0 (release), 1 ``(press)
        Key Row: 0, 1
        Possible Column: 0, 1, 2, 3
    */
    public MacroKey(int key, int row) {
        keyNum = key;
        keyRow = row;
        int type;
        releaseFunctionList = new ArrayList<>();
        pressFunctionList = new ArrayList<>();
        keyPressCodeMap = new HashMap<>();
        keyReleaseCodeMap = new HashMap<>();

    }

    public void onAction(int inputType) {
        if (inputType == 1) {
            onKeyPress();
        }
        if (inputType == 0) {
            onKeyRelease();
        }
    }

    public void onKeyPress() {
        try {

            System.out.println("Key in row: " + keyRow + " with key Num: " + keyNum + " Pressed |   Function: " + keyPressCodeMap);
            Robot robot = new Robot();
            for (Pair<Integer, Integer> p : keyPressCodeMap.keySet()) {
                if (p.getValue() != null) {
                    robot.keyPress(VK_SHIFT);
                }
                if (p.getKey() > 0){
                    robot.keyPress(p.getKey());
                }
                if (p.getValue() != null) {
                    robot.keyRelease(VK_SHIFT);
                }
                if (p.getValue() != null && p.getValue() == MediaKeys.MEDIAKEY) {
                    MediaKeys.sortMediaKeys(p.getKey()).run();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onKeyRelease() {
        try {
            System.out.println("Key in row: " + keyRow + " with key Num: " + keyNum + " Released");
            Robot robot = new Robot();
            for (Pair<Integer, Integer> p :  keyReleaseCodeMap.keySet()) {
                if (p.getValue() != null) {
                    robot.keyPress(VK_SHIFT);
                }
                if (p.getKey() > 0){
                    robot.keyPress(p.getKey());
                }
                if (p.getValue() != null) {
                    robot.keyRelease(VK_SHIFT);
                }
                if (p.getValue() != null && p.getValue() == MediaKeys.MEDIAKEY) {
                    MediaKeys.sortMediaKeys(p.getKey()).run();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setKeyPressFunction(ArrayList<String> list) {
        pressFunctionList = list;
    }
    public void setKeyReleaseFunction(ArrayList<String> list) {
        releaseFunctionList = list;
    }

    public void setKeyPressCodeMap(Map<Pair<Integer, Integer>, Integer> recievedCodeMap) {
        keyPressCodeMap = recievedCodeMap;
    }
    public void setKeyReleaseCodeMap(Map<Pair<Integer, Integer>, Integer> recievedCodeMap) {
        keyReleaseCodeMap = recievedCodeMap;
    }

}

