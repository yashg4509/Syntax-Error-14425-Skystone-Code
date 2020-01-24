package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name = "MainTeleop")

public class MainTeleOp extends LinearOpMode {

    HardwareConfig robot = new HardwareConfig();

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap); //giving robot hardware map config

        waitForStart(); //when start is pressed opModeIsActive returns true; will return false when stopped

        //left stick controls direction, right stick controls rotation
        while (opModeIsActive()) {
            double x1 = -gamepad1.left_stick_x;
            double y1 = gamepad1.left_stick_y;
            double x2 = gamepad1.right_stick_x;

            boolean rotYAdd = gamepad1.dpad_down;
            boolean rotYSub = gamepad1.dpad_up;
            boolean rotZAdd = gamepad1.dpad_right;
            boolean rotZSub = gamepad1.dpad_left;

            boolean closeClaw = gamepad1.left_bumper;  //claw
            boolean openClaw = gamepad1.right_bumper;
//
            boolean LinActuator_Up = gamepad1.b;   //servo
            boolean LinActuator_Down = gamepad1.a;

            double bl = y1 - x1 + x2;
            double br = y1 + x1 - x2;
            double fl = y1 - x1 - x2;
            double fr = y1 + x1 + x2;


            robotDrive(bl, br, fl, fr);

            if(openClaw) {
                openClaw();
            }

            if(closeClaw) {
                closeClaw();
            }
//
//
            if(LinActuator_Up) {
                scissorMove(2.0);
            }

            if(LinActuator_Down) {
                scissorMove(-2.0);
            }

        }
    }

    public void robotDrive(double bl, double br, double fl, double fr) {
        robot.mBackLeft.setPower(-bl);
        robot.mBackRight.setPower(-br);
        robot.mFrontLeft.setPower(-fl);
        robot.mFrontRight.setPower(-fr);
    }





    public void openClaw () {
        robot.ClawServo.setPosition(0.5);
    }


    public void closeClaw () {
        robot.ClawServo.setPosition(0.0);
    }

    public void scissorMove(double power) {              //still need to check this method not sure which way the motors are supposed to turn
        robot.LinearActuator.setPower(-power);
    }

}
