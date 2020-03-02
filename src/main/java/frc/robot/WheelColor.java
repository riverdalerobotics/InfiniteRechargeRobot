/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * Add your docs here.
 */
public enum WheelColor {

    RED(1), YELLOW(2), BLUE(3), GREEN(4), NONE(0);

    private int relNum;

    private WheelColor(int relNum) {
        this.relNum = relNum;
    }

    public int getNum() {
        return this.relNum;
    }

    public static WheelColor getColorForChar(char c) {
        switch (c) {
        case 'R':
            return RED;
        case 'Y':
            return YELLOW;
        case 'B':
            return BLUE;
        case 'G':
            return GREEN;
        default:
            return NONE;
        }
    }

    @Override
    public String toString() {
        switch (this.relNum) {
        case 1:
            return "Red";
        case 2:
            return "Yellow";
        case 3:
            return "Blue";
        case 4:
            return "Green";
        default:
            return "None";
        }
    }

}
