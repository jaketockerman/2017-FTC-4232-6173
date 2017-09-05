package org.firstinspires.ftc.teamcode.team4232;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;
/**
 * Created by Jake Code on 11/10/2016.
 */

@TeleOp(name="K9bot: Telop Tank", group="K9bot")
@Disabled
public class Demo4232 extends LinearOpMode {

    /* Declare OpMode members. */
    Hardware4232 robot = new Hardware4232();              // Use a K9'shardware

    @Override
    public void runOpMode() throws InterruptedException {
        double left;
        double right;
        double leftFlipValue;
        double rightBallValue;
        double rightFlipValue;
        double leftBallValue;
        boolean beaconFlipValue;
        double beaconChange;
        double speed;
        double xValue;
        boolean rightBallValueB;
        boolean leftBallValueB;
        boolean leftFlipValueB;
        boolean rightFlipValueB;


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
            left = gamepad1.right_stick_y;
            right = gamepad1.left_stick_y;
            //servos

            leftBallValueB = gamepad1.left_bumper;
            rightBallValueB = gamepad1.right_bumper;
            leftFlipValue = gamepad1.left_trigger;
            rightFlipValue = gamepad1.right_trigger;
            beaconFlipValue = gamepad1.y;
            //convert boolean to double
            beaconChange = booleanToDouble(beaconFlipValue,1,0);
           /* rightFlipValue = booleanToDouble(rightFlipValueB,1,0);
            leftFlipValue = booleanToDouble(leftFlipValueB,1,0);
           */
            rightBallValue = booleanToDouble(rightBallValueB,1,0);
            leftBallValue = booleanToDouble(leftBallValueB,1,0);

            //clip values between -1,1
            left = Range.clip(left, -1, 1);
            right = Range.clip(right, -1, 1);


            //set motors to variables
            robot.leftMotor.setPower(right);
            robot.rightMotor.setPower(left);
            //set Servos to variables
            //left and right servos are flipped physically which requires modifications to values
            robot.leftFlip.setPosition(leftFlipValue);
            robot.rightFlip.setPosition(-rightFlipValue + 1);
            robot.leftBall.setPosition(leftBallValue);
            robot.rightBall.setPosition(-rightBallValue +1);
            robot.beaconFlip.setPosition(beaconChange);
            // Send telemetry message to signify robot running;

            telemetry.addData("Left Drive Power: ", "%.2f", left);
            telemetry.addData("Right Drive Power: ", "%.2f", right);
            telemetry.addData("Left Flip Servo: ", "%.2f", robot.leftFlip.getPosition());
            telemetry.addData("Right Flip Servo: ", "%.2f", robot.rightFlip.getPosition());
            telemetry.addData("Left Ball Servo: ", "%.2f", robot.leftBall.getPosition());
            telemetry.addData("Right Ball Servo: ", "%.2f", robot.rightBall.getPosition());
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


