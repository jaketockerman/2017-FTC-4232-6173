package org.firstinspires.ftc.teamcode.donationBot;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Jake Code on 11/10/2016.
 */

 @TeleOp(name="K9bot: Telop Tank", group="K9bot")
@Disabled
public class DonationBot extends LinearOpMode {

    /* Declare OpMode members. */
    HardwareDonationBot robot = new HardwareDonationBot();              // Use a K9'shardware

    @Override
    public void runOpMode() throws InterruptedException {
        double left;
        double right;
        boolean wave;
        double waveValue;
        double leftFlipValue;
        double rightFlipValue;


        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);


        // Send telemetry message to signify robot waiting;
      /*  telemetry.addData("Left Flip Servo: ", "%.2f", robot.leftFlip.getPosition());
        telemetry.addData("Right Flip Servo: ", "%.2f", robot.rightFlip.getPosition());
        telemetry.addData("Left Ball Servo: ", "%.2f", robot.leftBall.getPosition());
        telemetry.addData("Right Ball Servo: ", "%.2f", robot.rightBall.getPosition());
        telemetry.update();*/


        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // Run wheels in tank mode (note: The joystick goes negative when pushed forwards, so negate it)

            //motors
            right = gamepad1.right_stick_y;
            left = gamepad1.left_stick_y;
            //servos
            leftFlipValue = gamepad1.left_trigger;
            rightFlipValue = gamepad1.right_trigger;
            wave = gamepad1.right_bumper;
            //convert boolean to double
            waveValue =  booleanToDouble(wave,1,0);
            /*if (beaconFlipValue == true) {
                beaconChange = 1;
            } else {
                beaconChange = 0;
            }*/
            //clip values between -1,1
            left = Range.clip(left, -1, 1);
            right = Range.clip(right, -1, 1);


            //set motors to variables
            robot.leftMotor.setPower(left);
            robot.rightMotor.setPower(right);
            //set Servos to variables
            //left and right servos are flipped physically which requires modifications to values
            robot.leftFlip.setPosition(leftFlipValue);
            robot.rightFlip.setPosition(-rightFlipValue + 1);
            robot.waveFlip.setPosition(waveValue);
            // Send telemetry message to signify robot running;

            telemetry.addData("Left Drive Power: ", "%.2f", left);
            telemetry.addData("Right Drive Power: ", "%.2f", right);
            telemetry.addData("Left Flip Servo: ", "%.2f", robot.leftFlip.getPosition());
            telemetry.addData("Right Flip Servo: ", "%.2f", robot.rightFlip.getPosition());
            telemetry.addData("Wave Servo: ", "%.2f", robot.waveFlip.getPosition());
            telemetry.update();

            // Pause for metronome tick.  40 mS each cycle = update 25 times a second.
            robot.waitForTick(40);
            idle(); // Always call idle() at the bottom of your while(opModeIsActive()) loop
        }
    }
    double booleanToDouble(boolean test,double trueValue, double falseValue){
        double exit;
        if (test){
             exit = trueValue;
        }
        else exit = falseValue;
        return exit;

    }
}


