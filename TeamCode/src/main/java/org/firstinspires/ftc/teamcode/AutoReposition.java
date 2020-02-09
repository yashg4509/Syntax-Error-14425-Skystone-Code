package org.firstinspires.ftc.teamcode;

import android.os.Environment;

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
import java.util.Scanner;

@Autonomous(name = "AutoReposition")


public class AutoReposition extends LinearOpMode{
    Hardware_MecanumTest robot = new Hardware_MecanumTest();
    private ElapsedTime     runtime = new ElapsedTime();

    public void runOpMode (){
        robot.init(hardwareMap);
        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Ready to run");    //
        telemetry.update();

        waitForStart();
        File file = new File(Environment.getExternalStorageDirectory() + "/autoreposition.txt");

        telemetry.addData("Status", "opened file");    //
        telemetry.update();

        ArrayList<String> commands = new ArrayList<String>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            commands = new ArrayList<String>(); // ew

            String st;
            while ((st = br.readLine()) != null)
                commands.add(st);


        } catch (IOException e) {

            telemetry.addData("Status", e.getMessage());    //
            telemetry.update();

        }

        telemetry.addData("testdata", commands.get(5));
        telemetry.update();

        runtime.reset();

        int testcount = 0;
        while (opModeIsActive()) {
            //REPOSITIONING WO TO PARKING
            double lf = 0, rf = 0, lb = 0, rb = 0;
            double lliftpower = 0, rliftpower = 0, servopos = 0;
            double mindist = 999999.0;

            double matchtime = runtime.seconds();

            int l = 0, r = commands.size() / 8, mid = (l + r) / 2;
            while(l < r)
            {
                mid = (l+r)/2;
                telemetry.addData("bs", "searching...");
                telemetry.update();
                if(Double.valueOf(commands.get(8*mid)) < matchtime)
                {
                    l = mid + 1;
                }
                else
                {
                    r = mid - 1;
                }
            }

            telemetry.addData("bs", "running");
            telemetry.addData("time", Double.toString(runtime.seconds()));

            lf = Double.valueOf(commands.get(8*mid + 1));
            rf = Double.valueOf(commands.get(8*mid + 2));
            lb = Double.valueOf(commands.get(8*mid + 3));
            rb = Double.valueOf(commands.get(8*mid + 4));

            lliftpower = Double.valueOf(commands.get(8*mid+5));
            rliftpower = Double.valueOf(commands.get(8*mid+6));

            servopos = Double.valueOf(commands.get(8*mid+7));


            telemetry.addData("lf", Double.toString(lf));
            telemetry.addData("rf", Double.toString(rf));
            telemetry.addData("lb", Double.toString(lb));
            telemetry.addData("rb", Double.toString(rb));

            telemetry.addData("llift", Double.toString(lliftpower));
            telemetry.addData("rlift", Double.toString(rliftpower));
            telemetry.addData("servo", Double.toString(servopos));

            telemetry.update();

            robot.LFmotor.setPower(lf);
            robot.RFmotor.setPower(rf);
            robot.LBmotor.setPower(lb);
            robot.RBmotor.setPower(rb);

            robot.Lliftmotor.setPower(lliftpower);
            robot.Rliftmotor.setPower(rliftpower);
            robot.claw.setPosition(servopos);

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


