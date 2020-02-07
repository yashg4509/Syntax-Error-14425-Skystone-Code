package org.firstinspires.ftc.teamcode;

import android.os.Environment;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


@TeleOp(name="TeleOp: MecanumTestv1", group="Linear Opmode")

public class MainTeleOp extends LinearOpMode {

    ElapsedTime runtime = new ElapsedTime();

    Hardware_MecanumTest robot = new Hardware_MecanumTest();

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);
        //giving robot hardware map config


        waitForStart(); //when start is pressed opModeIsActive returns true; will return false when stopped

        runtime.reset();
        int p = (int)runtime.seconds();
        while (opModeIsActive()) {


            File f = new File(Environment.getExternalStorageDirectory() + "/data.txt");
            try {
                if(f.createNewFile())
                {
                    telemetry.log().add("created new file");
                }
                else
                {
                    telemetry.log().add("already exists");
                }
            }
            catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }




            /*
            Expansion Hub 1:
            port 0 = right back
            port 1 = left back
            port 2: right front
            port 3: left front
            */

            //rotation - right joystick
            double rightX = -gamepad1.right_stick_x;
            //movement - left joystick
            double leftX = gamepad1.left_stick_x;
            double leftY = -gamepad1.left_stick_y;
            double MIN_POSITION = 0;
            double MAX_POSITION = 1;

            //driving
            robot.LFmotor.setPower(leftY + rightX + leftX);
            robot.RFmotor.setPower(leftY - rightX - leftX);
            robot.LBmotor.setPower(leftY + rightX - leftX);
            robot.RBmotor.setPower(leftY - rightX + leftX);


            BufferedWriter out = null;

            try {
                FileWriter fstream = new FileWriter(Environment.getExternalStorageDirectory() + "/data.txt", true); // make code outside loop one day
                out = new BufferedWriter(fstream);

                out.write(Double.toString(runtime.seconds()) + "\n");
                out.write(Double.toString(leftX + rightX + leftX) + "\n"); //LF
                out.write(Double.toString(leftY - rightX - leftX) + "\n"); //RF
                out.write(Double.toString(leftX + rightX - leftX) + "\n"); //LB
                out.write(Double.toString(leftY - rightX + leftX) + "\n"); //RB

                out.close();
            }

            catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
            }


            /*
            Expansion Hub 2:
            port 0 = left lift motor
            port 1 = right lift motor
             */

            /* lift motors - left trigger/right trigger */
            /*right trigger = move lift down
            left trigger = move lift up
             */


            double liftUp = gamepad1.right_trigger;
            double liftDown = -gamepad1.left_trigger;

            if (gamepad1.left_trigger > 0) {
                robot.Lliftmotor.setPower(0.5*liftDown);
                robot.Rliftmotor.setPower(0.5*liftDown);
            } else if (gamepad1.right_trigger > 0) {
                robot.Lliftmotor.setPower(0.5*liftUp);
                robot.Rliftmotor.setPower(0.5*liftUp);
            } else {
                robot.Lliftmotor.setPower(0);
                robot.Rliftmotor.setPower(0);
            }


            boolean clawOpen = gamepad1.right_bumper;
            boolean clawClose = gamepad1.left_bumper;

            if (clawOpen) {
                robot.claw.setPosition(Servo.MAX_POSITION);
            }
            else if (clawClose) {
                robot.claw.setPosition(Servo.MIN_POSITION);
            }

        }
    }


    /*

    private void strafeForwardTime(double power, double time) {
        //negative power: strafe back, positive power: strafe forward
        double y1 = -3 * power;

        double bl = y1;
        double br = y1;
        double fl = y1;
        double fr = y1;

        runtime.reset();
        while (runtime.seconds() < time) {
            robot.LBmotor.setPower(bl);
            robot.RBmotor.setPower(br);
            robot.LFmotor.setPower(fl);
            robot.RFmotor.setPower(fr);
        }
        runtime.reset();

        robot.LBmotor.setPower(0);
        robot.RBmotor.setPower(0);
        robot.LFmotor.setPower(0);
        robot.RFmotor.setPower(0);

    }

    private void strafeSideTime(double power, double time) {

        //negative power: strafe left, positive power: strafe right
        double x2 = 3 * power;

        double bl = x2;
        double br = -x2;
        double fl = -x2;
        double fr = x2;

        runtime.reset();
        while (runtime.seconds() < time) {
            robot.LBmotor.setPower(bl);
            robot.RBmotor.setPower(br);
            robot.LFmotor.setPower(fl);
            robot.RFmotor.setPower(fr);
        }
        runtime.reset();

        robot.LBmotor.setPower(0);
        robot.RBmotor.setPower(0);
        robot.LFmotor.setPower(0);
        robot.RFmotor.setPower(0);

    }

    */


    public void liftClaw() {
        double liftUp = gamepad1.right_trigger;
        double liftDown = -gamepad1.left_trigger;

        if (gamepad1.left_trigger > 0) {
            robot.Lliftmotor.setPower(liftDown);
            robot.Rliftmotor.setPower(liftDown);
        } else if (gamepad1.right_trigger > 0) {
            robot.Lliftmotor.setPower(liftUp);
            robot.Rliftmotor.setPower(liftUp);
        } else {
            robot.Lliftmotor.setPower(0);
            robot.Rliftmotor.setPower(0);
        }
    }


//    public void strafe() {
//        double rightStickY = -gamepad1.right_stick_y;
//        double leftStickY = -gamepad1.left_stick_y;
//        boolean rightBumper = gamepad1.right_bumper;
//        boolean leftBumper = gamepad1.right_bumper;
//
//        if(rightBumper) {
//            robot.LFmotor.setPower(1);
//            robot.LBmotor.setPower(1);
//            robot.RBmotor.setPower(-1);
//            robot.RFmotor.setPower(-1);
//        } else if (leftBumper) {
//            robot.LFmotor.setPower(-1);
//            robot.LBmotor.setPower(1);
//            robot.RBmotor.setPower(1);
//            robot.RFmotor.setPower(-1);
//        } else {
//            robot.LFmotor.setPower(leftStickY);
//            robot.LBmotor.setPower(leftStickY);
//            robot.RBmotor.setPower(-rightStickY);
//            robot.RFmotor.setPower(-rightStickY);
//        }
//    }
}

/*
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

            if (backLinActuator) {
                robot.rightLinearActuator.setPower(-1);
                robot.leftLinearActuator.setPower(-1);
            }
            else {
                robot.rightLinearActuator.setPower(0);
                robot.leftLinearActuator.setPower(0);
            }

        }
    }

    public void robotDrive(double bl, double br, double fl, double fr) {
        robot.LBmotor.setPower(-bl);
        robot.RBmotor.setPower(-br);
        robot.LFmotor.setPower(-fl);
        robot.RFmotor.setPower(-fr);
    }





    public void openClaw () {
        robot.leftClawServo.setPosition(Servo.MAX_POSITION);
        robot.rightClawServo.setPosition(Servo.MIN_POSITION);
    }


    public void closeClaw () {
        robot.leftClawServo.setPosition(Servo.MIN_POSITION);
        robot.rightClawServo.setPosition(Servo.MAX_POSITION);
    }

    public void forwardLinearActuator(double power) {

    }

}
*/