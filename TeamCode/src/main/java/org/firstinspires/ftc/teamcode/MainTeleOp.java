package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


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

            float closeClaw = gamepad1.right_trigger;
            boolean openClaw = gamepad1.right_bumper;
            boolean dropClaw = gamepad1.a;
            boolean raiseClaw = gamepad1.b;
            
            boolean forwardLinActuator = gamepad1.x;
            boolean backLinActuator = gamepad1.y;



            double bl = y1 - x1 + x2;
            double br = y1 + x1 - x2;
            double fl = y1 - x1 - x2;
            double fr = y1 + x1 + x2;



            robotDrive(bl, br, fl, fr);

            if(openClaw) {
                openClaw();
            }

            if(closeClaw > 0) {
                closeClaw();
            }

            if(dropClaw) {
                dropClaw();
            }

            if(raiseClaw) {
                raiseClaw();
            }



        }
    }

    public void robotDrive(double bl, double br, double fl, double fr) {
        robot.mBackLeft.setPower(bl);
        robot.mBackRight.setPower(br);
        robot.mFrontLeft.setPower(fl);
        robot.mFrontRight.setPower(fr);
    }

    public void forward (double power) {
        robot.mBackLeft.setPower(power);
        robot.mBackRight.setPower(-power);
    }

    public void backward (double power) {
        robot.mFrontLeft.setPower(-power);
        robot.mFrontRight.setPower(power);
    }

    public void shiftLeft (double power) {
        robot.mBackRight.setPower(power);
        robot.mFrontRight.setPower(-power);
    }

    public void shiftRight (double power) {
        robot.mBackLeft.setPower(-power);
        robot.mFrontLeft.setPower(power);
    }

    public void turnRight (double power) {
        robot.mBackLeft.setPower(-power);
        robot.mFrontLeft.setPower(-power);
        robot.mBackRight.setPower(-power);
        robot.mFrontRight.setPower(-power);
    }

    public void turnLeft (double power) {
        robot.mBackLeft.setPower(power);
        robot.mFrontLeft.setPower(power);
        robot.mBackRight.setPower(power);
        robot.mFrontRight.setPower(power);
    }

    public void raiseClaw() {
        robot.flipClaw.setPosition(0);
    }

    public void dropClaw () {
        robot.flipClaw.setPosition(160);

    }

    public void openClaw () {
        robot.leftClawServo.setPosition(0);
        robot.rightClawServo.setPosition(0);
    }

    public void closeClaw () {
        robot.leftClawServo.setPosition(-85);
        robot.rightClawServo.setPosition(85);
    }

    public void forwardLinearActuator(double power) {

    }

}
