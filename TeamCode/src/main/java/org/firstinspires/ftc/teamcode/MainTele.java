package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Hardware;

//import org.openftc.revextensions2.ExpansionHubEx;

public class MainTele extends OpMode {

    private DcMotor FR;
    private DcMotor FL;
    private DcMotor BR;
    private DcMotor BL;

    public void init() {
       /* RevExtensions2.init();
        hub = hardwareMap.get(ExpansionHubEx.class, "Expansion Hub 2");
        hub2 = hardwareMap.get(ExpansionHubEx.class, "Expansion Hub 1");

        frontMotor = new FR (hardwareMap, telemetry);
        FL = new dcMotor(hardwareMap, telemetry);
        intake = new Intake(hardwareMap);
        grabber = new Grabber(hardwareMap);
        //grabber = new GrabberV2(hardwareMap);
        flipper = new FlipperV2(hardwareMap, telemetry);
        tape = new Tape_Extention(hardwareMap);

*/
    }

    @Override
    public void loop() {
        
    }
}