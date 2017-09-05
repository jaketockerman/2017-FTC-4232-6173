package org.firstinspires.ftc.teamcode.team4232;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cColorSensor;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
/**
 * Created by Jake Code on 11/10/2016.
 */

public class Hardware4232 {

    /* Public OpMode members. */
    public DcMotor  leftMotor   = null;
    public DcMotor  rightMotor  = null;
    public Servo leftBall;
    public Servo rightBall;
    public Servo leftFlip;
    public Servo rightFlip;
    public Servo beaconFlip;



    /* Local OpMode members. */

    HardwareMap hwMap  = null;
    private ElapsedTime period  = new ElapsedTime();

    /* Constructor */
    public Hardware4232() {
    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // save reference to HW Map
        hwMap = ahwMap;
        // groundSensor = hwMap.colorSensor.get("ground color sensor");
        //pokerColor = hwMap.colorSensor.get("poker color sensor");
        //gyro = (ModernRoboticsI2cGyro)hwMap.gyroSensor.get("gyro");
        // Define and Initialize Motors
        leftMotor   = hwMap.dcMotor.get("left motor");
        rightMotor  = hwMap.dcMotor.get("right motor");

      //
        //  groundSensor = (ModernRoboticsI2cColorSensor)hwMap.get("groundcolor");

        leftMotor.setDirection(DcMotor.Direction.REVERSE);

        // Set all motors to zero power
        leftMotor.setPower(0);
        rightMotor.setPower(0);

        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        // Define and initialize ALL installed servos.
        leftBall = hwMap.servo.get("left ball");
        rightBall = hwMap.servo.get("right ball");
        leftFlip = hwMap.servo.get("left flip");
        rightFlip = hwMap.servo.get("right flip");
        beaconFlip = hwMap.servo.get("beacon");
    }

    /***
     *
     * waitForTick implements a periodic delay. However, this acts like a metronome with a regular
     * periodic tick.  This is used to compensate for varying processing times for each cycle.
     * The function looks at the elapsed cycle time, and sleeps for the remaining time interval.
     *
     * @param periodMs  Length of wait cycle in mSec.
     * @throws InterruptedException
     */
    public void waitForTick(long periodMs)  throws InterruptedException {

        long  remaining = periodMs - (long)period.milliseconds();

        // sleep for the remaining portion of the regular cycle period.
        if (remaining > 0)
            Thread.sleep(remaining);

        // Reset the cycle clock for the next pass.
        period.reset();
    }
}
