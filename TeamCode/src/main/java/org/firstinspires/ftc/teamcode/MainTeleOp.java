package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name="TeleOp: MecanumTestv1", group="Linear Opmode")

public class MainTeleOp extends LinearOpMode {

    Hardware_MecanumTest robot = new Hardware_MecanumTest();

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);
        //giving robot hardware map config


        waitForStart(); //when start is pressed opModeIsActive returns true; will return false when stopped
        while (opModeIsActive()) {

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
    }
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
        robot.mBackLeft.setPower(-bl);
        robot.mBackRight.setPower(-br);
        robot.mFrontLeft.setPower(-fl);
        robot.mFrontRight.setPower(-fr);
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