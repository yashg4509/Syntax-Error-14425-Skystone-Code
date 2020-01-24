//package org.firstinspires.ftc.teamcode;
//
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.qualcomm.robotcore.hardware.Servo;
//
//@TeleOp(name = "ServoTesting")
//
//public class ServoTesting extends LinearOpMode {
//
//    HardwareConfig robot = new HardwareConfig();
//
//
//    public void runOpMode() throws InterruptedException {
//        robot.init(hardwareMap);
//
//        waitForStart();
//
//        double tgtPower = 0;
//        while (opModeIsActive()) {
//            tgtPower = -this.gamepad1.left_stick_y;
//            // check to see if we need to move the servo.
//            if(gamepad1.y) {
//                // move to 0 degrees.
//                robot.ClawServo.setPosition(0);
//            } else if (gamepad1.x || gamepad1.b) {
//                // move to 90 degrees.
//                robot.ClawServo.setPosition(0.5);
//            } else if (gamepad1.a) {
//                // move to 180 degrees.
//                robot.ClawServo.setPosition(1);
//            }
//            telemetry.addData("Servo Position", robot.ClawServo.getPosition());
//            telemetry.addData("Target Power", tgtPower);
//            telemetry.addData("Status", "Running");
//            telemetry.update();
//
//        }
//    }
//
//
//}
