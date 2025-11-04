package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.sql.Time;

@Autonomous(name = "Robot: Basic Linear Autonomous OpMode", group = "Linear OpMode")
public class ShortAutonomousOpMode_Linear extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();

    // Declare Drive Train Motors
    private DcMotor frontLeftDrive = null;
    private DcMotor backLeftDrive = null;
    private DcMotor frontRightDrive = null;
    private DcMotor backRightDrive = null;

    // Flywheels and Launcher Feeder
    private DcMotor flywheelMotor = null;
    private CRServo feeder1 = null;
    private CRServo feeder2 = null;

    static final double     forwardSpeed = 0.6;
    static final double     turnSpeed    = 0.5;

    @Override
    public void runOpMode()
    {
        // Declare Drive Train Motor Location
        frontLeftDrive = hardwareMap.get(DcMotor.class, "FL");
        backLeftDrive = hardwareMap.get(DcMotor.class, "BL");
        frontRightDrive = hardwareMap.get(DcMotor.class, "FR");
        backRightDrive = hardwareMap.get(DcMotor.class, "BR");

        // Declare Launcher Locations
        flywheelMotor = hardwareMap.get(DcMotor.class, "FW");
        feeder1 = hardwareMap.get(CRServo.class, "F1");
        feeder2 = hardwareMap.get(CRServo.class, "F2");

        // Set Drive Train Motor Directions
        frontLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        backLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        frontRightDrive.setDirection(DcMotor.Direction.REVERSE);
        backRightDrive.setDirection(DcMotor.Direction.FORWARD);

        // Set Launcher Motor and Servo Directions
        flywheelMotor.setDirection(DcMotor.Direction.FORWARD);
        feeder1.setDirection(CRServo.Direction.FORWARD);
        feeder2.setDirection(CRServo.Direction.REVERSE);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();

        // Drive Backwards for 1.5s
        while (opModeIsActive() && runtime.seconds() < 0.75)
        {
            frontLeftDrive.setPower(-1.0);
            backLeftDrive.setPower(-1.0);
            frontRightDrive.setPower(-1.0);
            frontLeftDrive.setPower(-1.0);
        }

        runtime.reset();

        // Start Launcher Flywheel
        flywheelMotor.setPower(0.54);

        // Start Feeders
        while (opModeIsActive() && runtime.seconds() < 3.0)
        {
            feeder1.setPower(0.95);
            feeder2.setPower(0.95);
        }

        runtime.reset();

        // Turn off Flywheel and Feeders
        flywheelMotor.setPower(0.0);
        feeder1.setPower(0.0);
        feeder2.setPower(0.0);
    }
}
