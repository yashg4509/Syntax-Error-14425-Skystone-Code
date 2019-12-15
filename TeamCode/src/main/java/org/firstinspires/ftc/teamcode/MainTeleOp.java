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

            boolean closeClaw = gamepad1.left_bumper;
            boolean openClaw = gamepad1.right_bumper;

            boolean dropClaw = gamepad1.x;
            boolean raiseClaw = gamepad1.y;
            
            boolean forwardLinActuator = gamepad1.a;
            boolean backLinActuator = gamepad1.b;



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

            if(dropClaw) {
                dropClaw();
            }

            if(raiseClaw) {
                raiseClaw();
            }



        }
    }

    public void robotDrive(double bl, double br, double fl, double fr) {
        robot.mBackLeft.setPower(-bl);
        robot.mBackRight.setPower(-br);
        robot.mFrontLeft.setPower(-fl);
        robot.mFrontRight.setPower(-fr);
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
        robot.flipClaw.setPosition(Servo.MAX_POSITION);
    }

    public void dropClaw () {
        robot.flipClaw.setPosition(Servo.MIN_POSITION);

    }

//    public void openClaw () {
//        robot.leftClawServo.setDirection(Servo.Direction.FORWARD);
//        robot.rightClawServo.setDirection(Servo.Direction.REVERSE);
//    }

    public void openClaw () {
        robot.leftClawServo.setPosition(Servo.MAX_POSITION);
        robot.rightClawServo.setPosition(Servo.MIN_POSITION);
    }

//    public void closeClaw () {
//        robot.leftClawServo.setDirection(Servo.Direction.REVERSE);
//        robot.rightClawServo.setDirection(Servo.Direction.FORWARD);
//    }

    public void closeClaw () {
        robot.leftClawServo.setPosition(Servo.MIN_POSITION);
        robot.rightClawServo.setPosition(Servo.MAX_POSITION);
    }

    public void forwardLinearActuator(double power) {

    }

}
