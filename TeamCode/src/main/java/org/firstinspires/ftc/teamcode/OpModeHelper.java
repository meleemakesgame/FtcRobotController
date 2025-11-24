package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;

public class OpModeHelper {
    /// Declarations
    // Start Runtime Debugger
    ElapsedTime runtime = new ElapsedTime();

    // Telemetry
    Telemetry telemetry;

    // Declare Drive Train Motors
    DcMotor frontLeftDrive = null;
    DcMotor backLeftDrive = null;
    DcMotor frontRightDrive = null;
    DcMotor backRightDrive = null;

    // Flywheels and Launcher Feeder
    DcMotor flywheelMotor = null;
    CRServo feeder1 = null;
    CRServo feeder2 = null;

    // goBilda Pinpoint Odometry Computer
    GoBildaPinpointDriver pinpoint = null;
    double xPos = 0.0;
    double yPos = 0.0;
    double heading = 0.0;
    double speedMod = 1.0;
    double autoSpeedMod = 0.2;

    // Other
    boolean canMove = true;

    public OpModeHelper(
            /// Hardware Names
            // Drive Train Motors
            String FL_NAME, String FR_NAME, String BL_NAME, String BR_NAME,
            // Shooting Mechanism Motors & Servos
            String FW_NAME, String F1_NAME, String F2_NAME,
            // Odometry Computer
            String PINPOINT_NAME,
            /// Hardware Values
            // Mapping
            HardwareMap hardwareMap,
            // Units
            DistanceUnit distanceUnit,
            AngleUnit angleUnit,
            double OffsetX,
            double OffsetY,
            // Drive Train Directions
            DcMotor.Direction FLDir, DcMotor.Direction BLDir, DcMotor.Direction FRDir, DcMotor.Direction BRDir,
            // Shooting Mechanism Directions
            DcMotor.Direction FWDir, DcMotor.Direction F1Dir, DcMotor.Direction F2Dir,
            // Odometry Computer
            GoBildaPinpointDriver.GoBildaOdometryPods OdometryPodType,
            GoBildaPinpointDriver.EncoderDirection EncoderDirectionX, GoBildaPinpointDriver.EncoderDirection EncoderDirectionY,
            /// Extra
            // Telemetry
            Telemetry telemetryUpdater
    ) {
        /// Initialization
        // Declare Drive Train Motor Location
        frontLeftDrive = hardwareMap.get(DcMotor.class, FL_NAME);
        backLeftDrive = hardwareMap.get(DcMotor.class, BL_NAME);
        frontRightDrive = hardwareMap.get(DcMotor.class, FR_NAME);
        backRightDrive = hardwareMap.get(DcMotor.class, BR_NAME);

        // Declare Launcher Locations
        flywheelMotor = hardwareMap.get(DcMotor.class, FW_NAME);
        feeder1 = hardwareMap.get(CRServo.class, F1_NAME);
        feeder2 = hardwareMap.get(CRServo.class, F2_NAME);

        // Declare goBilda Pinpoint Odometry Computer
        pinpoint = hardwareMap.get(GoBildaPinpointDriver.class, PINPOINT_NAME);

        // Telemetry
        telemetry = telemetryUpdater;

        // Set Drive Train Motor Directions
        frontLeftDrive.setDirection(FLDir);
        backLeftDrive.setDirection(BLDir);
        frontRightDrive.setDirection(FRDir);
        backRightDrive.setDirection(BRDir);

        // Set Launcher Motor and Servo Directions
        flywheelMotor.setDirection(FWDir);
        feeder1.setDirection(F1Dir);
        feeder2.setDirection(F2Dir);

        // Initialize Pinpoint Odometry Computer
        pinpoint.initialize();
        pinpoint.setOffsets(OffsetX, OffsetY, distanceUnit);
        pinpoint.setEncoderResolution(OdometryPodType);
        pinpoint.setEncoderDirections(EncoderDirectionX, EncoderDirectionY);

        pinpoint.resetPosAndIMU();
        Pose2D startPos = new Pose2D(distanceUnit, 0, 0, angleUnit, 0);
        pinpoint.setPosition(startPos);
    }

    /// Functions
    public void MoveTo(double wishXPos, double wishYPos)
    {

    }

    public void RotateToHeading(double wishHeading)
    {
        telemetry.addData("Status", "Trying to turning");
        canMove = true;
        if ((heading > wishHeading + 3) || (heading < wishHeading - 3))
        {
            canMove = false;
            telemetry.addData("Status", "Turning");
            if (heading > wishHeading)
            {
                frontLeftDrive.setPower(autoSpeedMod);
                backLeftDrive.setPower(autoSpeedMod);
                frontRightDrive.setPower(-autoSpeedMod);
                backRightDrive.setPower(-autoSpeedMod);
            }
            else if  (heading < wishHeading)
            {
                frontLeftDrive.setPower(-autoSpeedMod);
                backLeftDrive.setPower(-autoSpeedMod);
                frontRightDrive.setPower(autoSpeedMod);
                backRightDrive.setPower(autoSpeedMod);
            }
        }
        else
        {
            telemetry.addData("Status", "Unacceptable Turn Conditions");
        }
    }
}