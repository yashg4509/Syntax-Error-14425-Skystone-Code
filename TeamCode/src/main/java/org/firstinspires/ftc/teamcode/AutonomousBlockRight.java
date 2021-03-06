//package org.firstinspires.ftc.teamcode;
//
//
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.hardware.Servo;
//import com.qualcomm.robotcore.util.ElapsedTime;
//
//import org.firstinspires.ftc.robotcore.external.ClassFactory;
//import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
//import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
//import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
//import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
//
//import java.util.List;
//
//@Autonomous(name = "AutonomousV1")
//
//public class AutonomousBlockRight extends LinearOpMode{
//    HardwareConfig robot = new HardwareConfig();
//    private ElapsedTime     runtime = new ElapsedTime();
//
//    public void runOpMode () {
//        robot.init(hardwareMap);
//
//        // Send telemetry message to signify robot waiting;
//        telemetry.addData("Status", "Ready to run");    //
//        telemetry.update();
//
//        waitForStart();
//
//        dropClaw();
//
//        //auto for right side w/ blocks
//        forward(pow, 3000);
//        pause(500);
//        closeClaw();
//        pause(1000);
//        backward(pow, 2000);
//        pause(200);
//        turnRight(pow, 1500); //experiment for amount of time for 90 degrees
//        pause(200);
//        forward(pow, 4000);
//        pause(200);
//        openClaw();
//
//
//
//    }
//
//    double pow = 1.0;
//
//    public void pause (double time) {
//        runtime.reset();
//        if (opModeIsActive() && (runtime.seconds() >= time)) {
//            return;
//        }
//    }
//
//    public void forward (double power, double time) {
//        runtime.reset();
//        while (opModeIsActive() && (runtime.seconds() < time)) {
//            robot.mBackLeft.setPower(power);
//            robot.mBackRight.setPower(-power);
//        }
//    }
//
//    public void backward (double power, double time) {
//        runtime.reset();
//        while (opModeIsActive() && (runtime.seconds() < time)) {
//            robot.mFrontLeft.setPower(-power);
//            robot.mFrontRight.setPower(power);
//        }
//    }
//
////    public void backward (double power) {
////        robot.mFrontLeft.setPower(-power);
////        robot.mFrontRight.setPower(power);
////    }
////
////    double pow = 1.0;
////
////    public void pause (double time) {
////        runtime.reset();
////        if (opModeIsActive() && (runtime.seconds() >= time)) {
////            return;
////        }
////    }
////
////    public void forward (double power, double time) {
////        runtime.reset();
////        while (opModeIsActive() && (runtime.seconds() < time)) {
////            robot.mBackLeft.setPower(power);
////            robot.mBackRight.setPower(-power);
////        }
////    }
//
//    public void raiseClaw() {
//        robot.flipClaw.setPosition(0);
//    }
//
//    public void dropClaw () {
//        robot.flipClaw.setPosition(160);
//
//    }
//
//    public void openClaw () {
//        robot.ClawServo.setPosition(Servo.MAX_POSITION);
//    }
//
//    public void closeClaw () {
//        robot.ClawServo.setPosition(Servo.MIN_POSITION);
//    }
//}
//
//
//
