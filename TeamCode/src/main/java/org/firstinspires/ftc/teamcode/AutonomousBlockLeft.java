package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

@Autonomous(name = "AutonomousBlockLeft")

public class AutonomousBlockLeft extends LinearOpMode{
    HardwareConfig robot = new HardwareConfig();
    private ElapsedTime     runtime = new ElapsedTime();

    public void runOpMode () {
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Ready to run");    //
        telemetry.update();

        waitForStart();

        //auto for right side w/ blocks
//        forward(pow, 3000);
//        pause(500);
//        closeClaw();
//        pause(1000);
//        backward(pow, 2000);
//        pause(200);
//        turnLeft(pow, 1500); //experiment for amount of time for 90 degrees
//        pause(200);
//        forward(pow, 4000);
//        pause(200);
//        openClaw();

        strafeForwardTime(1.0, 1.5);




    }

    double pow = 1.0;

    private void strafeForwardTime(double power, double time) {
        //negative power: strafe back, positive power: strafe forward
        double y1 = -power;

        double bl = y1;
        double br = y1;
        double fl = y1;
        double fr = y1;

        runtime.reset();
        while (runtime.seconds() < time) {
            robot.mBackLeft.setPower(-bl);
            robot.mBackRight.setPower(-br);
            robot.mFrontLeft.setPower(-fl);
            robot.mFrontRight.setPower(-fr);
        }

        robot.mBackLeft.setPower(0);
        robot.mBackRight.setPower(0);
        robot.mFrontLeft.setPower(0);
        robot.mFrontRight.setPower(0);

    }

    private void strafeSideTime(double power, double time) {

        //negative power: strafe left, positive power: strafe right
        double x2 = power;

        double bl = x2;
        double br = -x2;
        double fl = -x2;
        double fr = x2;

        runtime.reset();
        while (runtime.seconds() < time) {
            robot.mBackLeft.setPower(bl);
            robot.mBackRight.setPower(br);
            robot.mFrontLeft.setPower(fl);
            robot.mFrontRight.setPower(fr);
        }
        runtime.reset();

        robot.mBackLeft.setPower(0);
        robot.mBackRight.setPower(0);
        robot.mFrontLeft.setPower(0);
        robot.mFrontRight.setPower(0);

    }

    public void robotDrive(double bl, double br, double fl, double fr, double time) {
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < time)) {
            robot.mBackLeft.setPower(bl);
            robot.mBackRight.setPower(br);
            robot.mFrontLeft.setPower(fl);
            robot.mFrontRight.setPower(fr);
        }
    }

    public void pause (double time) {
        runtime.reset();
        if (opModeIsActive() && (runtime.seconds() >= time)) {
            return;
        }
    }

    public void forward (double power, double time) {
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < time)) {
            robot.mBackLeft.setPower(power);
            robot.mBackRight.setPower(power);
        }

//        runtime.reset();
//        while (opModeIsActive() && (runtime.seconds() < time)) {
//            robot.mBackLeft.setPower(power);
//            robot.mBackRight.setPower(-power);
//        }
    }

    public void backward (double power, double time) {
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < time)) {
            robot.mFrontLeft.setPower(-power);
            robot.mFrontRight.setPower(power);
        }
    }

//    public void backward (double power) {
//        robot.mFrontLeft.setPower(-power);
//        robot.mFrontRight.setPower(power);
//    }

    public void shiftLeft (double power, double time) {
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < time)) {
            robot.mBackRight.setPower(power);
            robot.mFrontRight.setPower(-power);
        }
    }

    public void shiftRight (double power, double time) {
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < time)) {
            robot.mBackLeft.setPower(-power);
            robot.mFrontLeft.setPower(power);
        }
    }

//    public void shiftRight (double power) {
//        robot.mBackLeft.setPower(-power);
//        robot.mFrontLeft.setPower(power);
//    }

    public void turnRight (double power, double time) {
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < time)) {
            robot.mBackLeft.setPower(-power);
            robot.mFrontLeft.setPower(-power);
            robot.mBackRight.setPower(-power);
            robot.mFrontRight.setPower(-power);
        }
    }

    public void turnLeft (double power, double time) {
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < time)) {
            robot.mBackLeft.setPower(power);
            robot.mFrontLeft.setPower(power);
            robot.mBackRight.setPower(power);
            robot.mFrontRight.setPower(power);
        }
    }

//    public void turnLeft (double power) {
//        robot.mBackLeft.setPower(power);
//        robot.mFrontLeft.setPower(power);
//        robot.mBackRight.setPower(power);
//        robot.mFrontRight.setPower(power);
//    }

//    public void raiseClaw() {
//        robot.flipClaw.setPosition(0);
//    }
//
//    public void dropClaw () {
//        robot.flipClaw.setPosition(160);
//
//    }
//
    public void openClaw () {
        robot.ClawServo.setPosition(Servo.MAX_POSITION);
    }

    public void closeClaw () {
        robot.ClawServo.setPosition(Servo.MIN_POSITION);

    }
}


