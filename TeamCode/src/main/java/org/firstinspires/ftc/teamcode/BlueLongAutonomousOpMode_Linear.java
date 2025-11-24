package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Autonomous(name = "Robot: Basic Linear Autonomous OpMode Blue Side Long", group = "Linear OpMode")
public class BlueLongAutonomousOpMode_Linear extends LinearOpMode {
    @Override
    public void runOpMode()
    {
        OpModeHelper helper = new OpModeHelper(
                "FL", "FR", "BL", "BR",
                "FW", "F1", "F2",
                "PINPOINT",
                hardwareMap,
                DistanceUnit.MM, AngleUnit.DEGREES,
                -95.25, 19.05,
                DcMotor.Direction.REVERSE, DcMotor.Direction.REVERSE, DcMotor.Direction.REVERSE, DcMotor.Direction.FORWARD,
                DcMotor.Direction.FORWARD, DcMotor.Direction.FORWARD, DcMotor.Direction.REVERSE,
                GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD,
                GoBildaPinpointDriver.EncoderDirection.FORWARD, GoBildaPinpointDriver.EncoderDirection.REVERSED,
                telemetry
        );

        // Start Runtime Debugger
        ElapsedTime runtime = helper.runtime;

        // Declare Drive Train Motors
        DcMotor frontLeftDrive = helper.frontLeftDrive;
        DcMotor backLeftDrive = helper.backLeftDrive;
        DcMotor frontRightDrive = helper.frontRightDrive;
        DcMotor backRightDrive = helper.backRightDrive;

        // Flywheels and Launcher Feeder
        DcMotor flywheelMotor = helper.flywheelMotor;
        CRServo feeder1 = helper.feeder2;
        CRServo feeder2 = helper.feeder2;

        // goBilda Pinpoint Odometry Computer
        GoBildaPinpointDriver pinpoint = helper.pinpoint;
        double xPos = helper.xPos;
        double yPos = helper.yPos;
        DistanceUnit du = DistanceUnit.MM;
        AngleUnit au = AngleUnit.DEGREES;

        telemetry.addData("Status", "Initialized");
        // Calculate xPos and yPos with Odometry Computer
        pinpoint.update();
        xPos = pinpoint.getPosX(du);
        yPos = pinpoint.getPosY(du);
        helper.heading = pinpoint.getHeading(au);
        telemetry.addData("Odometry Computer X Position", xPos);
        telemetry.addData("Odometry Computer Y Position", yPos);
        telemetry.addData("Odometry Computer Heading", helper.heading);
        telemetry.update();

        waitForStart();
        // Calculate xPos and yPos with Odometry Computer
        pinpoint.update();
        xPos = pinpoint.getPosX(du);
        yPos = pinpoint.getPosY(du);
        helper.heading = pinpoint.getHeading(au);
        telemetry.addData("Odometry Computer X Position", xPos);
        telemetry.addData("Odometry Computer Y Position", yPos);
        telemetry.addData("Odometry Computer Heading", helper.heading);
        telemetry.update();
        runtime.reset();

        // Start Pos: (, ), 90deg

        /*
        // Go Right
        while (opModeIsActive() && runtime.seconds() < 3.6)
        {
            frontLeftDrive.setPower(speedMod);
            backLeftDrive.setPower(-speedMod);
            frontRightDrive.setPower(-speedMod);
            backRightDrive.setPower(speedMod);
        }

        frontLeftDrive.setPower(0);
        backLeftDrive.setPower(0);
        frontRightDrive.setPower(0);
        backRightDrive.setPower(0);

        // Calculate xPos and yPos with Odometry Computer
        pinpoint.update();
        xPos = pinpoint.getPosX(du);
        yPos = pinpoint.getPosY(du);
        heading = pinpoint.getHeading(au);
        telemetry.addData("Odometry Computer X Position", xPos);
        telemetry.addData("Odometry Computer Y Position", yPos);
        telemetry.addData("Odometry Computer Heading", heading);
        telemetry.update();
        runtime.reset();

        sleep(5000);

        // Calculate xPos and yPos with Odometry Computer
        pinpoint.update();
        xPos = pinpoint.getPosX(du);
        yPos = pinpoint.getPosY(du);
        heading = pinpoint.getHeading(au);
        telemetry.addData("Odometry Computer X Position", xPos);
        telemetry.addData("Odometry Computer Y Position", yPos);
        telemetry.addData("Odometry Computer Heading", heading);
        telemetry.update();
        runtime.reset();

        // Go Left
        while (opModeIsActive() && runtime.seconds() < 3.6)
        {
            frontLeftDrive.setPower(-speedMod);
            backLeftDrive.setPower(speedMod);
            frontRightDrive.setPower(speedMod);
            backRightDrive.setPower(-speedMod);
        }

        frontLeftDrive.setPower(0);
        backLeftDrive.setPower(0);
        frontRightDrive.setPower(0);
        backRightDrive.setPower(0);

        // Calculate xPos and yPos with Odometry Computer
        pinpoint.update();
        xPos = pinpoint.getPosX(du);
        yPos = pinpoint.getPosY(du);
        heading = pinpoint.getHeading(au);
        telemetry.addData("Odometry Computer X Position", xPos);
        telemetry.addData("Odometry Computer Y Position", yPos);
        telemetry.addData("Odometry Computer Heading", heading);
        telemetry.update();
        runtime.reset();
        */

        // Drive Forwards
        while (opModeIsActive() && runtime.seconds() < 0.16)
        {
            frontLeftDrive.setPower(helper.autoSpeedMod);
            backLeftDrive.setPower(helper.autoSpeedMod);
            frontRightDrive.setPower(helper.autoSpeedMod);
            backRightDrive.setPower(helper.autoSpeedMod);
        }

        // Calculate xPos and yPos with Odometry Computer
        pinpoint.update();
        xPos = pinpoint.getPosX(du);
        yPos = pinpoint.getPosY(du);
        helper.heading = pinpoint.getHeading(au);
        telemetry.addData("Odometry Computer X Position", xPos);
        telemetry.addData("Odometry Computer Y Position", yPos);
        telemetry.addData("Odometry Computer Heading", helper.heading);
        telemetry.update();
        runtime.reset();

        // Turn Right
        while (opModeIsActive() && runtime.seconds() < 0.4)
        {
            frontLeftDrive.setPower(helper.autoSpeedMod);
            backLeftDrive.setPower(helper.autoSpeedMod);
            frontRightDrive.setPower(-helper.autoSpeedMod);
            backRightDrive.setPower(-helper.autoSpeedMod);
        }

        runtime.reset();

        // Drive Forwards
        while (opModeIsActive() && runtime.seconds() < 0.4)
        {
            frontLeftDrive.setPower(helper.autoSpeedMod);
            backLeftDrive.setPower(helper.autoSpeedMod);
            frontRightDrive.setPower(helper.autoSpeedMod);
            backRightDrive.setPower(helper.autoSpeedMod);
        }

        frontLeftDrive.setPower(0);
        backLeftDrive.setPower(0);
        frontRightDrive.setPower(0);
        backRightDrive.setPower(0);

        // Start Launcher Flywheel
        flywheelMotor.setPower(0.85);

        sleep(10500);

        // Calculate xPos and yPos with Odometry Computer
        pinpoint.update();
        xPos = pinpoint.getPosX(du);
        yPos = pinpoint.getPosY(du);
        helper.heading = pinpoint.getHeading(au);
        telemetry.addData("Odometry Computer X Position", xPos);
        telemetry.addData("Odometry Computer Y Position", yPos);
        telemetry.addData("Odometry Computer Heading", helper.heading);
        telemetry.update();
        runtime.reset();

        // Start Feeders
        while (opModeIsActive() && runtime.seconds() < 10)
        {
            feeder1.setPower(0.3);
            feeder2.setPower(0.3);
        }

        // Turn off Flywheel and Feeders
        // Calculate xPos and yPos with Odometry Computer
        pinpoint.update();
        xPos = pinpoint.getPosX(du);
        yPos = pinpoint.getPosY(du);
        helper.heading = pinpoint.getHeading(au);
        telemetry.addData("Odometry Computer X Position", xPos);
        telemetry.addData("Odometry Computer Y Position", yPos);
        telemetry.addData("Odometry Computer Heading", helper.heading);
        telemetry.update();
        runtime.reset();

        feeder1.setPower(0.0);
        feeder2.setPower(0.0);

        // Calculate xPos and yPos with Odometry Computer
        pinpoint.update();
        xPos = pinpoint.getPosX(du);
        yPos = pinpoint.getPosY(du);
        helper.heading = pinpoint.getHeading(au);
        telemetry.addData("Odometry Computer X Position", xPos);
        telemetry.addData("Odometry Computer Y Position", yPos);
        telemetry.addData("Odometry Computer Heading", helper.heading);
        telemetry.update();
        runtime.reset();

        flywheelMotor.setPower(0.0);

        // Calculate xPos and yPos with Odometry Computer
        pinpoint.update();
        xPos = pinpoint.getPosX(du);
        yPos = pinpoint.getPosY(du);
        helper.heading = pinpoint.getHeading(au);
        telemetry.addData("Odometry Computer X Position", xPos);
        telemetry.addData("Odometry Computer Y Position", yPos);
        telemetry.addData("Odometry Computer Heading", helper.heading);
        telemetry.update();
        runtime.reset();

        while (opModeIsActive() && runtime.seconds() < 0.9)
        {
            frontLeftDrive.setPower(helper.speedMod);
            backLeftDrive.setPower(helper.speedMod);
            frontRightDrive.setPower(helper.speedMod);
            backRightDrive.setPower(helper.speedMod);
        }

        frontLeftDrive.setPower(0);
        backLeftDrive.setPower(0);
        frontRightDrive.setPower(0);
        backRightDrive.setPower(0);

        // Calculate xPos and yPos with Odometry Computer
        pinpoint.update();
        xPos = pinpoint.getPosX(du);
        yPos = pinpoint.getPosY(du);
        helper.heading = pinpoint.getHeading(au);
        telemetry.addData("Odometry Computer X Position", xPos);
        telemetry.addData("Odometry Computer Y Position", yPos);
        telemetry.addData("Odometry Computer Heading", helper.heading);
        telemetry.update();
        telemetry.update();
    }
}
