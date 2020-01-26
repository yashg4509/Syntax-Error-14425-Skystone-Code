package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="AutoComp3")

public class AutoComp3 extends LinearOpMode {
    ElapsedTime runtime = new ElapsedTime();
    Hardware_MecanumTest robot = new Hardware_MecanumTest();
    int power = 1;

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {
            strafeForwardTime(power, 1.75);
            pause(0.5);
            rotate(-0.375, 1); //rotate 135 degrees
            strafeForwardTime(power, 1);
            strafeSideTime(1, 1);
            rotate(-0.125, 1); //rotate 45 degrees
            strafeForwardTime(power, 0.5);
            strafeSideTime(-power, 2);


        }

    }

    private void strafeForwardTime(double power, double time) {
        //negative power: strafe back, positive power: strafe forward
        double y1 = -power;

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
        double x2 = power;

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

    private void rotate(double power, double time) {

        //negative power: rotate counterclockwise (left), positive power: rotate clockwise (right)
        double x1 = power;

        double bl = -x1;
        double br = x1;
        double fl = -x1;
        double fr = x1;

        runtime.reset();

        while (runtime.seconds() < time) {
            robot.LBmotor.setPower(bl);
            robot.RBmotor.setPower(br);
            robot.LFmotor.setPower(fl);
            robot.RFmotor.setPower(fr);
        }


        robot.LBmotor.setPower(0);
        robot.RBmotor.setPower(0);
        robot.LFmotor.setPower(0);
        robot.RFmotor.setPower(0);

    }

    public void pause (double time) {
        runtime.reset();
        while(runtime.seconds() < time) {
            return;
        }
    }
}
