/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.DisplayMetrics;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.vuforia.Frame;
import com.vuforia.Image;
import com.vuforia.PIXEL_FORMAT;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.nio.ByteBuffer;
import java.util.List;


@Autonomous(name = "EncoderCraterLookAt1 Updated")

public class AutoEncoderCraterLookAt1 extends LinearOpMode {

    /* Declare OpMode members. */
    HardwareConfig         robot   = new HardwareConfig();   // Use a Pushbot's hardware
    private ElapsedTime     runtime = new ElapsedTime();

    static final double     COUNTS_PER_MOTOR_REV    = 1440 ;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 2.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
                                                      (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double     DRIVE_SPEED             = 0.6;
    static final double     TURN_SPEED              = 0.5;

    int jewelPos = 0;

    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";


    private static final String VUFORIA_KEY = "AcEFFIj/////AAABmSOIZr8rt0Dnsi6XmQK3QAIBsi7nd0+XZ6PfQ" +
            "TABZb9Yy4udMduTjqyXp3NosaSoevs06bzlDbNIOq2JNHffHhH2JJTMdDQM190/ILL/NdboLihbj3JGyK8LTLdM" +
            "I2jcAACH4dye2N/12RObvEbFxQCQJafMOWDmPImiDw9ffmSxKMaHyo5HXUR+9Ajfs6oKvMJDJXlisysaFK0iCDI" +
            "vytTlV365YEYAu+2CO/t3M/qqiahV4w0zUR6mIQVzikl1tj9VsBTsMahPezFnxrYR6q7om658ANSDtGsf5GulAi" +
            "xbif3LXNKSK8x6yL7qIw9me0qRa3r4wJhbhGJXef/K14UvI85kraIzpscMMWmlYi54";

    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    private VuforiaLocalizer vuforia;

    /**
     * {@link #tfod} is the variable we will use to store our instance of the Tensor Flow Object
     * Detection engine.
     */
    private TFObjectDetector tfod;


    @Override
    public void runOpMode() {

        robot.init(hardwareMap);
        initVuforia();

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Resetting Encoders");
        telemetry.update();

        robot.mBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.mBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.mFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.mFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        robot.mBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.mBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.mFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.mFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);



        // Send telemetry message to indicate successful Encoder reset
        telemetry.addData("Path0",  "Starting at %7d :%7d :%7d :%7d");
            robot.mBackLeft.getCurrentPosition();
            robot.mBackRight.getCurrentPosition();
            robot.mFrontLeft.getCurrentPosition();
            robot.mFrontRight.getCurrentPosition();
        telemetry.update();

        robot.armBot.setPower(0);
        robot.armTop.setPower(0);

        robot.liftMotor.setPower(0.8);
        sleep(1500);
        robot.liftMotor.setPower(0);

        //move to the right for a bit
        strafeSideEncoder(0.3, 2);


        strafeForwardEncoder(0.3, 1);

        robot.liftMotor.setPower(-0.8);
        sleep(1500);
        robot.liftMotor.setPower(0);

        //move back
        strafeSideEncoder(-0.4, 1);

        //move forward a bit
        strafeForwardEncoder(0.4, 4);

        //rotate camera towards the jewels
        rotEncoder(-0.4, 8);

        tfod.activate();

        if(checkGold()){
            telemetry.addData("Gold Mineral Position", "Center");
            telemetry.update();
            //jewel position: middle or unknown

            tfod.deactivate();

            //knock off the mineral
            strafeSideEncoder(-0.3, 10);


            stop();
        }

        strafeForwardEncoder(0.4, 6);

        if(checkGold()){
            telemetry.addData("Gold Mineral Position", "Right");
            telemetry.update();
            //jewel position: right

            tfod.deactivate();

            //knock off the mineral and go to the wall
            strafeSideEncoder(-0.3, 10);

            stop();
        }

        strafeForwardEncoder(-0.6, 12);

        //if(checkGold()) {
        telemetry.addData("Gold Mineral Position", "Left");
        telemetry.update();
        //jewel position: left

        tfod.deactivate();

        //knock off the jewel
        strafeSideEncoder(-0.3, 10);



        telemetry.addData("Path", "Complete");
        telemetry.update();

        sleep(3000);

        stop();
        //}


    }

    private boolean checkGold(){
        boolean isGold = false;

        runtime.reset();
        while(runtime.seconds() < 2) {
            if (tfod != null) {
                // getUpdatedRecognitions() will return null if no new information is available since
                // the last time that call was made.
                List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                if (updatedRecognitions != null) {
                    telemetry.addData("# Object Detected", updatedRecognitions.size());

                    if (updatedRecognitions.size() == 1) {
                        int goldMineralX;
                        if (updatedRecognitions.get(0).getLabel().equals(LABEL_GOLD_MINERAL)) {
                            goldMineralX = (int) updatedRecognitions.get(0).getLeft();
                            isGold = true;
                            telemetry.addData("isGold: ", "true");
                            telemetry.update();
                        } else {
                            isGold = false;
                            telemetry.addData("isGold: ", "false");
                            telemetry.update();
                        }
                    }
                    else if (updatedRecognitions.size() > 1){
                        telemetry.addData("size is greater than 1: ", updatedRecognitions.size());
                        telemetry.update();

                        for (Recognition rec: updatedRecognitions){
                            if(rec.getLabel().equals(LABEL_GOLD_MINERAL)){
                                if(rec.getConfidence() >= 0.9){
                                    telemetry.addData("isGold: ", "true");
                                    telemetry.update();
                                    isGold = true;
                                }
                            }
                        }
                    }
                    else{
                        telemetry.addData("Object detected?", isGold);
                        telemetry.update();
                    }
                }
//                telemetry.addData("Runtime: ", getRuntime());
//                telemetry.update();
            }
        }


        return isGold;
    }


    /*
     *  Method to move the robot, split into forward strafing, side strafing, and rotating
     */
    private void strafeForwardEncoder(double speed, double fInches) {
        //negative power: strafe back, positive power: strafe forward

        speed = -speed;

        fInches = speed<0? -fInches: fInches;

        int newBLTarget;
        int newBRTarget;
        int newFLTarget;
        int newFRTarget;

        double y1 = speed;

        double bl = y1;
        double br = y1;
        double fl = y1;
        double fr = y1;

        robot.mBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.mBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.mFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.mFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Determine new target position, and pass to motor controller
        newBLTarget = (int)(fInches * COUNTS_PER_INCH);
        newBRTarget = (int)(fInches * COUNTS_PER_INCH);
        newFLTarget = (int)(fInches * COUNTS_PER_INCH);
        newFRTarget = (int)(fInches * COUNTS_PER_INCH);

        robot.mBackLeft.setTargetPosition(newBLTarget);
        robot.mBackRight.setTargetPosition(newBRTarget);
        robot.mFrontLeft.setTargetPosition(newFLTarget);
        robot.mFrontRight.setTargetPosition(newFRTarget);

        // Turn On RUN_TO_POSITION
        robot.mBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.mBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.mFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.mFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // reset the timeout time and start motion.
        runtime.reset();
        robot.mBackLeft.setPower(bl);
        robot.mBackRight.setPower(br);
        robot.mFrontLeft.setPower(fl);
        robot.mFrontRight.setPower(fr);

        while (robot.mBackRight.isBusy() && robot.mBackLeft.isBusy()
                && robot.mFrontRight.isBusy() && robot.mFrontLeft.isBusy()){
            telemetry.addData("BL tick count: ", robot.mBackLeft.getCurrentPosition());
            telemetry.addData("BR tick count: ", robot.mBackRight.getCurrentPosition());
            telemetry.addData("FL tick count: ", robot.mFrontLeft.getCurrentPosition());
            telemetry.addData("FR tick count: ", robot.mFrontRight.getCurrentPosition());
            telemetry.update();
        }

        robot.mBackLeft.setPower(0);
        robot.mBackRight.setPower(0);
        robot.mFrontLeft.setPower(0);
        robot.mFrontRight.setPower(0);

        // Turn off RUN_TO_POSITION
        robot.mBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.mBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.mFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.mFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //  sleep(250);   // optional pause after each move

    }

    private void strafeSideEncoder(double speed, double fInches) {
        //negative power: strafe left, positive power: strafe right

        fInches = speed<0? -fInches: fInches;

        int newBLTarget;
        int newBRTarget;
        int newFLTarget;
        int newFRTarget;

        double x2 = speed;

        double bl = x2;
        double br = -x2;
        double fl = -x2;
        double fr = x2;

        robot.mBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.mBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.mFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.mFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Determine new target position, and pass to motor controller
        newBLTarget = (int)(fInches * COUNTS_PER_INCH);
        newBRTarget = -(int)(fInches * COUNTS_PER_INCH);
        newFLTarget = -(int)(fInches * COUNTS_PER_INCH);
        newFRTarget = (int)(fInches * COUNTS_PER_INCH);

        robot.mBackLeft.setTargetPosition(newBLTarget);
        robot.mBackRight.setTargetPosition(newBRTarget);
        robot.mFrontLeft.setTargetPosition(newFLTarget);
        robot.mFrontRight.setTargetPosition(newFRTarget);

        // Turn On RUN_TO_POSITION
        robot.mBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.mBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.mFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.mFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // reset the timeout time and start motion.
        runtime.reset();
        robot.mBackLeft.setPower(bl);
        robot.mBackRight.setPower(br);
        robot.mFrontLeft.setPower(fl);
        robot.mFrontRight.setPower(fr);

        while (robot.mBackRight.isBusy() && robot.mBackLeft.isBusy()
                && robot.mFrontRight.isBusy() && robot.mFrontLeft.isBusy()){
            telemetry.addData("BL tick count: ", robot.mBackLeft.getCurrentPosition());
            telemetry.addData("BR tick count: ", robot.mBackRight.getCurrentPosition());
            telemetry.addData("FL tick count: ", robot.mFrontLeft.getCurrentPosition());
            telemetry.addData("FR tick count: ", robot.mFrontRight.getCurrentPosition());
            telemetry.update();
        }

        robot.mBackLeft.setPower(0);
        robot.mBackRight.setPower(0);
        robot.mFrontLeft.setPower(0);
        robot.mFrontRight.setPower(0);

        // Turn off RUN_TO_POSITION
        robot.mBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.mBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.mFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.mFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //  sleep(250);   // optional pause after each move

    }

    private void rotEncoder(double speed, double fInches) {
        //negative power: rotate counterclockwise (left), positive power: rotate clockwise (right)

        fInches = speed<0? -fInches: fInches;

        int newBLTarget;
        int newBRTarget;
        int newFLTarget;
        int newFRTarget;

        double x1 = speed;

        double bl = -x1;
        double br = x1;
        double fl = -x1;
        double fr = x1;

        robot.mBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.mBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.mFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.mFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Determine new target position, and pass to motor controller
        newBLTarget = -(int)(fInches * COUNTS_PER_INCH);
        newBRTarget = (int)(fInches * COUNTS_PER_INCH);
        newFLTarget = -(int)(fInches * COUNTS_PER_INCH);
        newFRTarget = (int)(fInches * COUNTS_PER_INCH);

        robot.mBackLeft.setTargetPosition(newBLTarget);
        robot.mBackRight.setTargetPosition(newBRTarget);
        robot.mFrontLeft.setTargetPosition(newFLTarget);
        robot.mFrontRight.setTargetPosition(newFRTarget);

        // Turn On RUN_TO_POSITION
        robot.mBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.mBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.mFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.mFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // reset the timeout time and start motion.
        runtime.reset();
        robot.mBackLeft.setPower(bl);
        robot.mBackRight.setPower(br);
        robot.mFrontLeft.setPower(fl);
        robot.mFrontRight.setPower(fr);

        while (robot.mBackRight.isBusy() && robot.mBackLeft.isBusy()
                && robot.mFrontRight.isBusy() && robot.mFrontLeft.isBusy()){
            telemetry.addData("BL tick count: ", robot.mBackLeft.getCurrentPosition());
            telemetry.addData("BR tick count: ", robot.mBackRight.getCurrentPosition());
            telemetry.addData("FL tick count: ", robot.mFrontLeft.getCurrentPosition());
            telemetry.addData("FR tick count: ", robot.mFrontRight.getCurrentPosition());
            telemetry.update();
        }

        robot.mBackLeft.setPower(0);
        robot.mBackRight.setPower(0);
        robot.mFrontLeft.setPower(0);
        robot.mFrontRight.setPower(0);

        // Turn off RUN_TO_POSITION
        robot.mBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.mBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.mFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.mFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //  sleep(250);   // optional pause after each move

    }


    private void initVuforia() {
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the Tensor Flow Object Detection engine.
    }

    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
    }

    private Bitmap getBitmap() throws InterruptedException{
        Frame frame;
        Bitmap BM0 = Bitmap.createBitmap(new DisplayMetrics(), 100, 100, Bitmap.Config.RGB_565);
        if(vuforia.getFrameQueue().peek() != null){
            frame = vuforia.getFrameQueue().take();
            for(int i = 0; i < frame.getNumImages(); i++){
                if(frame.getImage(i).getFormat() == PIXEL_FORMAT.RGB565){
                    Image image = frame.getImage(i);
                    ByteBuffer pixels = image.getPixels();
                    Matrix matrix = new Matrix();
                    matrix.preScale(-1, -1);
                    Bitmap bitmap = Bitmap.createBitmap(new DisplayMetrics(), image.getWidth(), image.getHeight(), Bitmap.Config.RGB_565);
                    bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
                    bitmap.copyPixelsFromBuffer(pixels);
                    return bitmap;
                }
            }
        }
        return BM0;
    }
/*
    private void takePic() {
        try{
            Bitmap bitmap = getBitmap();
            Bitmap newBitmap = RotateBitmap180(bitmap);
            saveImageToExternalStorage(bitmap);
        } catch (Exception e){
            e.printStackTrace();
        }

    }*/





}
