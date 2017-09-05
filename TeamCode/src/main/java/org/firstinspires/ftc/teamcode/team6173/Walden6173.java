package org.firstinspires.ftc.teamcode.team6173;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


/**
 * This file provides basic Telop driving for a Pushbot robot.
 * The code is structured as an Iterative OpMode
 *
 * This OpMode uses the common Pushbot hardware class to define the devices on the robot.
 * All device access is managed through the HardwarePushbot class.
 *
 * This particular OpMode executes a basic Tank Drive Teleop for a PushBot
 * It raises and lowers the claw using the Gampad Y and A buttons respectively.
 * It also opens and closes the claws slowly using the left and right Bumper buttons.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the
 * @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="6173 Walden",group="Pusbot")

public class Walden6173 extends OpMode{

    /* Declare OpMode members. */
    Hardware6173 robot       = new Hardware6173();
    double left;
    double right;
    double rack;
    double kicker;
    double leftie;
    double rightie;
    double speed;
    double xValue;
    boolean leftieB;
    boolean rightieB;


    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);



        // Send telemetry message to signify robot waiting;

    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
        telemetry.addData("Enslave", "Teh Hooman Rice in 3.2.1...");
        telemetry.addData("left",  "%.2f", robot.leftMotor.getPower());
        telemetry.addData("right", "%.2f", robot.rightMotor.getPower());
        telemetry.addData("rack", "%.2f", rack);
        telemetry.addData("left servo", "%.2f", robot.leftServo.getPosition());
        telemetry.addData("right servo", "%.2f", robot.rightServo.getPosition());
        telemetry.addData("kicker servo", "%.2f", robot.kickerServo.getPosition());
        telemetry.update();
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        telemetry.addData("NOW", " All Systems are GO!");
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        if(gamepad1.a) kicker = 1;
        else kicker = 0;

        if(gamepad1.left_trigger>0) leftie = 1;
        else leftie = 0;

        if(gamepad1.right_trigger>0) rightie = 0;
        else rightie = 1;




        right = -gamepad1.left_stick_y-gamepad1.left_stick_x;
        left = -gamepad1.left_stick_y+gamepad1.left_stick_x;
        //set motors to variables
        robot.leftMotor.setPower(left);
        robot.rightMotor.setPower(right);
        /*if (Math.abs(rack)<0.1 && rack !=0)
            robot.rackMotor.setPower(rack);
        else robot.rackMotor.setPower(0);*/
        robot.rackMotor.setPower(rack);
        //set Servos to variables
        robot.kickerServo.setPosition(kicker);
        robot.leftServo.setPosition(leftie);
        robot.rightServo.setPosition(rightie);
        // Send telemetry message to signify robot running;
        telemetry.addData("left",  "%.2f", robot.leftMotor.getPower());
        telemetry.addData("right", "%.2f", robot.rightMotor.getPower());
        telemetry.addData("rack", "%.2f", rack);
        telemetry.addData("left servo", "%.2f", robot.leftServo.getPosition());
        telemetry.addData("right servo", "%.2f", robot.rightServo.getPosition());
        telemetry.addData("kicker servo", "%.2f", robot.kickerServo.getPosition());
        telemetry.update();

    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {

    }


}
