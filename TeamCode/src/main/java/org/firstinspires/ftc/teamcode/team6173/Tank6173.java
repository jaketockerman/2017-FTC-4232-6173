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
//package org.firstinspires.ftc.robotcontroller.external.samples;
package org.firstinspires.ftc.teamcode.team6173;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;


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

@TeleOp(name="6173 Tank",group="Pusbot")
@Disabled
public class Tank6173 extends OpMode{

    /* Declare OpMode members. */
    Hardware6173 robot       = new Hardware6173();
    double left;
    double right;
    double rack;
    double kicker;
    double leftie;
    double rightie;


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

        // set variables to hardware
        //motors
        left = -gamepad1.left_stick_y;
        right = -gamepad1.right_stick_y;
        if (gamepad2.right_bumper) {
            rack = 1;
        } else {
            if (gamepad2.left_bumper){
                rack = -1;
            }
            else rack = 0;
        }
        //clip values to be between -1,1
        left = Range.clip(left,-1,1);
        right = Range.clip(right,-1,1);
        rack = Range.clip(rack,-1,1);
        //servos
        kicker = gamepad2.left_stick_y;
        leftie = gamepad2.left_trigger;
        rightie = - gamepad2.right_trigger + 1;
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
