package com.owen.macropadgui.devices;


import com.owen.macropadgui.GlobalData;
import commands.MediaKeys;
import javafx.util.Pair;


import java.awt.Robot;
import java.util.ArrayList;

import static java.awt.event.KeyEvent.VK_SHIFT;


/*
Button:
    Input Type: B
    Possible Function: 0 (release), 1 (press)
    Button pressed: 0, 1, 2
    Possible Matrix2 (NOT USED): -1
*/
public class MacroButton {
    public final int buttonNum;
    private ArrayList<Pair<Integer, Integer>>  buttonPressCodeMap;
    private ArrayList<Pair<Integer, Integer>>  buttonReleaseCodeMap;

    public MacroButton(int button) {
        buttonNum = button;
        buttonPressCodeMap = new ArrayList<>();
        buttonReleaseCodeMap = new ArrayList<>();
    }

    public void onAction(int type) {
        if (type == 0) {
            onKeyRelease();
        } else {
            onKeyPress();
        }
    }

    public void onKeyPress() {


        try {
            Robot robot = new Robot();
            for (Pair<Integer, Integer> p : buttonPressCodeMap) {
                if (p.getValue() != null) {
                    robot.keyPress(VK_SHIFT);
                }
                if (p.getKey() > 0) {
                    robot.keyPress(p.getKey());
                    robot.keyRelease(p.getKey());
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
            Robot robot = new Robot();
            for (Pair<Integer, Integer> p : buttonReleaseCodeMap) {
                if (p.getValue() != null) {
                    robot.keyPress(VK_SHIFT);
                }
                if (p.getKey() > 0) {
                    robot.keyPress(p.getKey());
                    robot.keyRelease(p.getKey());
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

    public void setButtonPressFunction(ArrayList<String> list) {
    }

    public void setButtonReleaseFunction(ArrayList<String> list) {
    }

    public void setButtonReleaseCodeMap(ArrayList<Pair<Integer, Integer>>  receivedCodeMap) {
        buttonReleaseCodeMap = receivedCodeMap;
    }

    public void setButtonPressCodeMap(ArrayList<Pair<Integer, Integer>> receivedCodeMap) {
        buttonPressCodeMap = receivedCodeMap;
    }
}


