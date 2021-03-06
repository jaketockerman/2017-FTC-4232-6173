/*
Copyright (c) 2016 Robert Atkinson
All rights reserved.
Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:
Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.
Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.
Neither the name of Robert Atkinson nor the names of his contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.
NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESSFOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package org.firstinspires.ftc.teamcode.team5177;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This file contains an example of an iterative (Non-Linear) "OpMode".
 * An OpMode is a 'program' that runs in either the autonomous or the teleop period of an FTC match.
 * The names of OpModes appear on the menu of the FTC Driver Station.
 * When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a PushBot
 * It includes all the skeletal structure that all iterative OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@Autonomous(name="AutoRedTeam", group="Autonomous")  // @Autonomous(...) is the other common choice
@Disabled
public class AutoMode extends OpMode
{
    /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();
    private int autoState = 0;
    private double leftThrottle, rightThrottle;
    HocoHardware robot = new HocoHardware();
    private boolean isFirstTime = true;
    private int targetHeading = 0;
    private int rackTarget = 0;
    // private DcMotor leftMotor = null;
    // private DcMotor rightMotor = null;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");

        robot.init(hardwareMap);
        robot.gyro.calibrate();
        //robot.groundSensor.enableLed(true);
        robot.rack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        /* eg: Initialize the hardware variables. Note that the strings used here as parameters
         * to 'get' must correspond to the names assigned during the robot configuration
         * step (using the FTC Robot Controller app on the phone).
         */
        // leftMotor  = hardwareMap.dcMotor.get("left motor");
        // rightMotor = hardwareMap.dcMotor.get("right motor");

        // eg: Set the drive motor directions:
        // Reverse the motor that runs backwards when connected directly to the battery
        // leftMotor.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
        //  rightMotor.setDirection(DcMotor.Direction.REVERSE);// Set to FORWARD if using AndyMark motors
        // telemetry.addData("Status", "Initialized");
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
        if (!robot.gyro.isCalibrating()){
            telemetry.addData("gryo calibrated!", robot.gyro.getHeading());

        }else{
            telemetry.addData("gryo is calibrating", "");
        }
        telemetry.update();

        if (getRuntime() > 15000){
            robot.gyro.calibrate();
        }
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        runtime.reset();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        telemetry.addData("Status", "Running: " + runtime.toString());
        double LIGHT_THRESHHOLD = .7;
        switch (autoState){
            case 1:
                //the target heading is 315 from the heading zero that the robot should be positioned at now.
                if( robot.gyro.getHeading() >= 0 || robot.gyro.getHeading() <=359)
                {
                    rightThrottle = .5;
                    leftThrottle = -.5;
                } if(robot.gyro.getHeading() < 330) //Throttle down as approach target
            {
                rightThrottle = .15;
                leftThrottle = -.15;
            }
                if(robot.gyro.getHeading() < 320){ // Continue throttle
                    rightThrottle = .1;
                    leftThrottle = -.1;
                } if (robot.gyro.getHeading() >= 314 || robot.gyro.getHeading() <= 317){ //stop within 2 degrees of target, small margin of error
                rightThrottle = 0;
                leftThrottle = 0;
                autoState ++;
                resetDriveEncoders();
            } if(robot.gyro.getHeading()<314){ // Reverse if overturn
                rightThrottle = -.1;
                leftThrottle = .1;
            }

                break;

            case 2: //This Case drives the robot straight for 12.56 rotations

                if (averageEncoders() < 1440 * 12.56) {//there are 1440 ticks per each revolution
                    driveStraightGodDamnIt(.75);//Dank method name
                }
                else {
                    resetDriveEncoders();
                    autoState++;
                }
                break;
            //Target heading is zero

            case 3:
                if (robot.gyro.getHeading() <= 320){
                    rightThrottle = .5;
                    leftThrottle = -.5;
                }
                if (robot.gyro.getHeading() <= 350 && robot.gyro.getHeading() >= 320){
                    rightThrottle = .15;
                    leftThrottle = -.15;
                }
                if (robot.gyro.getHeading() < 360 && robot.gyro.getHeading() >= 350){
                    rightThrottle = .1;
                    leftThrottle = -.1;
                }
                if (robot.gyro.getHeading() > 358 || robot.gyro.getHeading() < 2){
                    rightThrottle = 0;
                    leftThrottle = 0;
                    resetDriveEncoders();
                    autoState++;
                }
                if(robot.gyro.getHeading() >= 2){
                    rightThrottle = -.1;
                    leftThrottle = .1;
                }
                break;
            case 4:
                rightThrottle = .25;
                leftThrottle = .25;
                /*if(robot.groundSensor.alpha()>LIGHT_THRESHHOLD){
                    rightThrottle = 0;
                    leftThrottle = 0;
                    resetDriveEncoders();
                    autoState++;
                }*/
                break;
            case 5:
                robot.rack.setPower(.75);
                robot.rack.setTargetPosition(20160 / 2);
                if(!(robot.rack.isBusy())) autoState ++;
                break;
            case 6:


               /* if( robot.pokerColor.red()>robot.pokerColor.blue()) {
                    robot.rack.setTargetPosition(20160);
                    autoState= 8;
                }
                else{
                    autoState = 7;
                }*/
                break;






            case 7: //First position is red
                //Documentation says the isBusy() function doesn't work.
                driveStraightGodDamnIt(.7);
                if (averageEncoders() > 1440){
                    autoState = 9;
                }
                break;

            case 8:

                robot.leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                robot.rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                robot.leftMotor.setTargetPosition(1834);
                robot.rightMotor.setTargetPosition(1834);
                //First position is not red, is blue, requires forward

                break;
        }
        robot.rightMotor.setPower(rightThrottle);
        robot.leftMotor.setPower(leftThrottle);

    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
        robot.leftMotor.setPower(0);
        robot.rightMotor.setPower(0);
    }
    void driveStraightGodDamnIt(double speed){

        if (isFirstTime) {
            targetHeading = robot.gyro.getHeading();
            isFirstTime = false;
        }
        isFirstTime = false;
        int error = targetHeading - robot.gyro.getHeading();
        double correctionFactor = (error/75.0);

        if(targetHeading > (robot.gyro.getHeading() - 0.5) || targetHeading < (robot.gyro.getHeading() + 0.5))
        {
            leftThrottle = (speed - correctionFactor);
            rightThrottle = (speed + correctionFactor);
        }
    }

    void resetDriveEncoders(){
        robot.leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    int averageEncoders(){
        return (robot.leftMotor.getCurrentPosition() + robot.rightMotor.getCurrentPosition()) / 2;
    }
}