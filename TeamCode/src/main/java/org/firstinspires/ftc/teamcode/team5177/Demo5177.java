package org.firstinspires.ftc.teamcode.team5177;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Code on 3/28/2017.
 */

@TeleOp(name = "Template: Itedemo rative OpMode", group = "Iterative Opmode")
// @Autonomous(...) is the other common choice
@Disabled
public class Demo5177 extends OpMode {
    /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();
    private double leftThrottle = 0;
    private double rightThrottle = 0;
    private final double LIGHT_THRESHOLD = 100;
    private boolean isFirstTime = true;
    private int targetHeading = 0;
    private boolean flippersLocked = true;
    private double speed;
    private double xValue;


    HocoHardware robot = new HocoHardware();


    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");


        robot.init(hardwareMap);
        robot.gyro.calibrate();


        if (getBatteryVoltage() < 12) telemetry.addData("BATTERY VOLTAGE LOW", getBatteryVoltage());






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
        if (!robot.gyro.isCalibrating()) {
            telemetry.addData("gryo calibrated!", robot.gyro.getHeading());

        } else {
            telemetry.addData("gryo is calibrating", "");
        }
        telemetry.update();

        // robot.shooter.setPosition(.5);
    }


    @Override
    public void start() {
        //telemetry.addData("Blast Off", " All Systems are GO!");
        runtime.reset();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        telemetry.addData("Status", "Running: " + runtime.toString());
        speed = gamepad1.left_trigger-gamepad1.right_trigger;
        xValue = gamepad1.left_stick_x;
        rightThrottle = -speed + xValue;
        leftThrottle = -speed - xValue;

        //makes the throttle less sensitive if right bumper is pressed.




        if (gamepad2.a) {
            flippersLocked = true;
        }
        if (gamepad2.left_trigger != 0 || gamepad2.right_trigger != 0) {
            flippersLocked = false;
        }


       if (gamepad1.x) {
           // robot.groundSensor.enableLed(true);

        }
        if (gamepad1.y) {
           // robot.groundSensor.enableLed(false);
        }
        //telemetry.addData("Color", robot.groundSensor.alpha());
        if (gamepad1.b){
            resetDriveEncoders();
        }

        if (gamepad2.right_bumper|| gamepad1.right_bumper) {
            // if(Math.abs(robot.rack.getCurrentPosition())<6000){
            robot.rack.setPower(-1);
            // }
            // if(gamepad2.y){
            //   robot.rack.setPower(1);
            // }
        } else if (gamepad2.left_bumper||gamepad1.left_bumper)
            robot.rack.setPower(1);
        else {
            robot.rack.setPower(0);
        }
        telemetry.addData("Rack Encoder:", robot.rack.getCurrentPosition());
        telemetry.addData("average encoder", averageEncoders());


//elchawpawashere
        //flexonurxbox
        // eg: Run wheels in tank mode (note: The joystick goes negative when pushed forwards)
        // leftMotor.setPower(-gamepad1.left_stick_y);
        // rightMotor.setPower(-gamepad1.right_stick_y);
        leftThrottle = clipRanges(leftThrottle);
        rightThrottle = clipRanges(rightThrottle);


        robot.rightMotor.setPower(rightThrottle);
        robot.leftMotor.setPower(leftThrottle);


    }



    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
        telemetry.addData("Nice Job Driver!", "");
        telemetry.update();
    }

    double getBatteryVoltage() {
        double result = Double.POSITIVE_INFINITY;
        for (VoltageSensor sensor : hardwareMap.voltageSensor) {
            double voltage = sensor.getVoltage();
            if (voltage > 0) {
                result = Math.min(result, voltage);
            }
        }
        return result;
    }



    double clipRanges(double whatIAmClipping){

        whatIAmClipping = Math.min(whatIAmClipping, 1);
        whatIAmClipping = Math.max (whatIAmClipping , -1);

        return whatIAmClipping;

    }
    int averageEncoders() {
        return (robot.leftMotor.getCurrentPosition() + robot.rightMotor.getCurrentPosition()) / 2;
    }
    void resetDriveEncoders() {
        robot.leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
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


