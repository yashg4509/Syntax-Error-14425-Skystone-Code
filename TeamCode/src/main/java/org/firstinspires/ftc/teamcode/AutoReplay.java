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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Autonomous(name = "AutoReplay")

public class AutoReplay extends LinearOpMode{
    Hardware_MecanumTest robot = new Hardware_MecanumTest();
    private ElapsedTime     runtime = new ElapsedTime();

    public void runOpMode (){
        robot.init(hardwareMap);
        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Ready to run");    //
        telemetry.update();

        waitForStart();

        ArrayList<String> commands = new ArrayList<String>();
        try {
            File file = new File("autodata.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));

        String st;
        commands = new ArrayList<String>(); // ew

        while ((st = br.readLine()) != null)
            commands.add(st);

        } catch (IOException e) {

            telemetry.log().add("Probably BufferedReader");

        }

        runtime.reset();
        while (opModeIsActive()) {
            //REPOSITIONING WO TO PARKING



                double lf = 0, rf = 0, lb = 0, rb = 0;
                double mindist = 999999;

                for (int i = 0; i < commands.size() / 5; i += 5) {
                    if (Math.abs(Double.valueOf(commands.get(i)) - runtime.seconds()) < mindist) {
                        mindist = Math.abs(Double.valueOf(commands.get(i)) - runtime.seconds());

                        lf = Double.valueOf(commands.get(i + 1));
                        rf = Double.valueOf(commands.get(i + 2));
                        lb = Double.valueOf(commands.get(i + 3));
                        rb = Double.valueOf(commands.get(i + 4));
                    }
                }

                robot.LFmotor.setPower(lf);
                robot.RFmotor.setPower(rf);
                robot.LBmotor.setPower(lb);
                robot.RBmotor.setPower(rb);




        }



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
            robot.LBmotor.setPower(-bl);
            robot.RBmotor.setPower(-br);
            robot.LFmotor.setPower(-fl);
            robot.RFmotor.setPower(-fr);
        }

        robot.LBmotor.setPower(0);
        robot.RBmotor.setPower(0);
        robot.LFmotor.setPower(0);
        robot.RFmotor.setPower(0);

    }


    public void pause (double time) {
        runtime.reset();
        int x = 0;
        while (runtime.seconds() > time) {
            x+=0;
        }
        return;
    }

//    private void strafeSideTime(double power, double time) {
//
//        //negative power: strafe left, positive power: strafe right
//        double x2 = power;
//
//        double bl = x2;
//        double br = -x2;
//        double fl = -x2;
//        double fr = x2;
//
//        runtime.reset();
//        while (runtime.seconds() < time) {
//            robot.LBmotor.setPower(bl);
//            robot.RBmotor.setPower(br);
//            robot.LFmotor.setPower(fl);
//            robot.RFmotor.setPower(fr);
//        }
//        runtime.reset();
//
//        robot.LBmotor.setPower(0);
//        robot.RBmotor.setPower(0);
//        robot.LFmotor.setPower(0);
//        robot.RFmotor.setPower(0);
//
//    }
//
//    public void robotDrive(double bl, double br, double fl, double fr, double time) {
//        runtime.reset();
//        while (opModeIsActive() && (runtime.seconds() < time)) {
//            robot.LBmotor.setPower(bl);
//            robot.RBmotor.setPower(br);
//            robot.LFmotor.setPower(fl);
//            robot.RFmotor.setPower(fr);
//        }
//    }
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
//            robot.LBmotor.setPower(power);
//            robot.RBmotor.setPower(power);
//        }
//
////        runtime.reset();
////        while (opModeIsActive() && (runtime.seconds() < time)) {
////            robot.LBmotor.setPower(power);
////            robot.RBmotor.setPower(-power);
////        }
//    }
//
//    public void backward (double power, double time) {
//        runtime.reset();
//        while (opModeIsActive() && (runtime.seconds() < time)) {
//            robot.LFmotor.setPower(-power);
//            robot.RFmotor.setPower(power);
//        }
//    }
//
////    public void backward (double power) {
////        robot.LFmotor.setPower(-power);
////        robot.RFmotor.setPower(power);
////    }
//
//    public void shiftLeft (double power, double time) {
//        runtime.reset();
//        while (opModeIsActive() && (runtime.seconds() < time)) {
//            robot.RBmotor.setPower(power);
//            robot.RFmotor.setPower(-power);
//        }
//    }
//
//    public void shiftRight (double power, double time) {
//        runtime.reset();
//        while (opModeIsActive() && (runtime.seconds() < time)) {
//            robot.LBmotor.setPower(-power);
//            robot.LFmotor.setPower(power);
//        }
//    }
//
////    public void shiftRight (double power) {
////        robot.LBmotor.setPower(-power);
////        robot.LFmotor.setPower(power);
////    }
//
//    public void turnRight (double power, double time) {
//        runtime.reset();
//        while (opModeIsActive() && (runtime.seconds() < time)) {
//            robot.LBmotor.setPower(-power);
//            robot.LFmotor.setPower(-power);
//            robot.RBmotor.setPower(-power);
//            robot.RFmotor.setPower(-power);
//        }
//    }
//
//    public void turnLeft (double power, double time) {
//        runtime.reset();
//        while (opModeIsActive() && (runtime.seconds() < time)) {
//            robot.LBmotor.setPower(power);
//            robot.LFmotor.setPower(power);
//            robot.RBmotor.setPower(power);
//            robot.RFmotor.setPower(power);
//        }
//    }

//    public void turnLeft (double power) {
//        robot.LBmotor.setPower(power);
//        robot.LFmotor.setPower(power);
//        robot.RBmotor.setPower(power);
//        robot.RFmotor.setPower(power);
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
//    public void openClaw () {
//        robot.ClawServo.setPosition(Servo.MAX_POSITION);
//    }
//
//    public void closeClaw () {
//        robot.ClawServo.setPosition(Servo.MIN_POSITION);
//
//    }
}


