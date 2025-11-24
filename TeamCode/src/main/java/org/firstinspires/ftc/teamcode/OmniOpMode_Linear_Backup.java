package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;

@TeleOp(name = "Omni Linear OpMode BACKUP VERSION", group = "Linear OpMode")
public class OmniOpMode_Linear_Backup extends LinearOpMode {

    // Start Runtime Debugger
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

    // goBilda Pinpoint Odometry Computer
    private GoBildaPinpointDriver pinpoint = null;
    private double xPos = 0.0;
    private double yPos = 0.0;
    private double heading = 0.0;
    private DistanceUnit du = DistanceUnit.MM;
    private AngleUnit au = AngleUnit.DEGREES;

    @Override
    public void runOpMode() {
        // Declare Drive Train Motor Location
        frontLeftDrive = hardwareMap.get(DcMotor.class, "FL");
        backLeftDrive = hardwareMap.get(DcMotor.class, "BL");
        frontRightDrive = hardwareMap.get(DcMotor.class, "FR");
        backRightDrive = hardwareMap.get(DcMotor.class, "BR");

        // Declare Launcher Locations
        flywheelMotor = hardwareMap.get(DcMotor.class, "FW");
        feeder1 = hardwareMap.get(CRServo.class, "F1");
        feeder2 = hardwareMap.get(CRServo.class, "F2");

        // Declare goBilda Pinpoint Odometry Computer
        pinpoint = hardwareMap.get(GoBildaPinpointDriver.class, "PINPOINT");

        // Set Drive Train Motor Directions
        frontLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        backLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        frontRightDrive.setDirection(DcMotor.Direction.REVERSE);
        backRightDrive.setDirection(DcMotor.Direction.FORWARD);

        // Set Launcher Motor and Servo Directions
        flywheelMotor.setDirection(DcMotor.Direction.FORWARD);
        feeder1.setDirection(CRServo.Direction.FORWARD);
        feeder2.setDirection(CRServo.Direction.REVERSE);

        // Initialize Pinpoint Odometry Computer
        pinpoint.initialize();
        pinpoint.setOffsets(-95.25, 19.05, du);
        pinpoint.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        pinpoint.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD, GoBildaPinpointDriver.EncoderDirection.REVERSED);

        pinpoint.resetPosAndIMU();
        Pose2D startPos = new Pose2D(du, 0, 0, au, 0);
        pinpoint.setPosition(startPos);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {
            // weird controller shit
            double max;

            double axial = -gamepad2.left_stick_y;
            double lateral = gamepad2.left_stick_x;
            double yaw = -gamepad2.right_stick_x;

            double frontLeftPower = axial + lateral + yaw;
            double frontRightPower = axial - lateral - yaw;
            double backLeftPower = axial - lateral + yaw;
            double backRightPower = axial + lateral - yaw;

            // Calculate xPos and yPos with Odometry Computer
            pinpoint.update();
            xPos = pinpoint.getPosX(du);
            yPos = pinpoint.getPosY(du);
            heading = pinpoint.getHeading(au);

            // Modifiers for Motors
            double launcherPower = 0.9;

            // weird math shit
            max = Math.max(Math.abs(frontLeftPower), Math.abs(frontRightPower));
            max = Math.max(max, Math.abs(backLeftPower));
            max = Math.max(max, Math.abs(backRightPower));

            if (max > 1.0) {
                frontLeftPower /= max;
                frontRightPower /= max;
                backLeftPower /= max;
                backRightPower /= max;
            }

            // Get Buttons Down
            double gamepadSquare = gamepad1.x ? 1.0 : 0.0;
            double gamepadCircle = gamepad1.b ? 1.0 : 0.0;
            double gamepadCross = gamepad1.a ? 1.0 : 0.0;
            double gamepadTriangle = gamepad1.y ? 1.0 : 0.0;
            double dpadU = gamepad1.dpad_up ? 1.0 : 0.0;
            double dpadR = gamepad1.dpad_right ? 1.0 : 0.0;
            double dpadD = gamepad1.dpad_down ? 1.0 : 0.0;
            double dpadL = gamepad1.dpad_left ? 1.0 : 0.0;

            // Set Drive Train Motor Power
            frontLeftDrive.setPower(frontLeftPower);
            frontRightDrive.setPower(frontRightPower);
            backLeftDrive.setPower(backLeftPower);
            backRightDrive.setPower(backRightPower);

            // Motor Buttons
            if (gamepadCross == 1.0) // Motor OFF
            {
                flywheelMotor.setPower(0 * launcherPower);
            }
            else if (gamepadCircle == 1.0) // Motor LOW
            {
                flywheelMotor.setPower(0.6 * launcherPower);
            }
            else if (gamepadSquare == 1.0) // Motor HIGH
            {
                flywheelMotor.setPower(0.65 * launcherPower);
            }

            // Positioning Buttons
            else if (dpadU == 1.0) // Rotate to Short Distance Target
            {

            }
            else if (dpadD == 1.0) // Rotate to Long Distance Target
            {

            }

            feeder1.setPower(gamepadTriangle * 0.95);
            feeder2.setPower(gamepadTriangle * 0.95);

            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Axial", axial);
            telemetry.addData("Lateral", lateral);
            telemetry.addData("Yaw", yaw);
            telemetry.addData("Odometry Computer X Position", xPos);
            telemetry.addData("Odometry Computer Y Position", yPos);
            telemetry.addData("Odometry Computer Heading", heading);
            telemetry.update();
        }
    }
}