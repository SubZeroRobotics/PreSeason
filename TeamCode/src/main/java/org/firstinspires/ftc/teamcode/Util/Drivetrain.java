package org.firstinspires.ftc.teamcode.Util;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.sql.Array;
import java.util.Arrays;
import java.util.List;

public class Drivetrain {


    //TODO: Hardware
    public DcMotorEx frontLeft;
    public DcMotorEx frontRight;
    public DcMotorEx backLeft;
    public DcMotorEx backRight;

    public List<DcMotorEx> driveMotors;


    /**
     * Instantiation of Robot
     * Pass in parameters to initialize a robot object
     */

    public Drivetrain(HardwareMap hardwareMap){
        /* Hardware mapping */
        frontLeft = hardwareMap.get(DcMotorEx.class, "FL");
        frontRight = hardwareMap.get(DcMotorEx.class, "FR");
        backLeft = hardwareMap.get(DcMotorEx.class, "BL");
        backRight = hardwareMap.get(DcMotorEx.class, "BR");

        driveMotors = Arrays.asList(frontLeft, frontRight, backLeft, backRight);



    }

    /**
     * Other methods on drivetrain
     **/


}
