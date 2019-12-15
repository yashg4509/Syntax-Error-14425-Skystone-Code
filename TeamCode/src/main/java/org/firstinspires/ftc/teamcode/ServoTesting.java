package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "ServoTesting")

public class ServoTesting extends LinearOpMode {

    HardwareConfig robot = new HardwareConfig();

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);

        waitForStart();

        while(opModeIsActive()) {
//            robot.leftClawServo.setDirection(Servo.Direction.REVERSE);
//            robot.rightClawServo.setDirection(Servo.Direction.FORWARD);
//            robot.leftClawServo.setDirection(Servo.Direction.FORWARD);
//            robot.rightClawServo.setDirection(Servo.Direction.REVERSE);

            telemetry.addData("Status", "running servo program");
            telemetry.update();

            robot.leftClawServo.setPosition(1);
            robot.rightClawServo.setPosition(1);
            robot.leftClawServo.setPosition(0);
            robot.rightClawServo.setPosition(0);
        }
    }


}
