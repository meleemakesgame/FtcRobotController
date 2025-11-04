package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "Basic: Omni Linear OpMode", group = "Linear OpMode")
public class BasicOmniOpMode_Linear extends LinearOpMode {

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

            // Set Drive Train Motor Power
            frontLeftDrive.setPower(frontLeftPower);
            frontRightDrive.setPower(frontRightPower);
            backLeftDrive.setPower(backLeftPower);
            backRightDrive.setPower(backRightPower);

            // Debugging
            int timesLauncherChanged = 0;

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

            feeder1.setPower(gamepadTriangle * 0.95);
            feeder2.setPower(gamepadTriangle * 0.95);

            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Axial", axial);
            telemetry.addData("Lateral", lateral);
            telemetry.addData("Yaw", yaw);
            telemetry.addData("Launcher Motor Power", gamepadCross);
            telemetry.addData("Launcher Servo Power", gamepadCircle);
            telemetry.addData("Amount of times Launcher was Activated/Deactivated", timesLauncherChanged);
            telemetry.addData("Front left/Right", "%4.2f, %4.2f", frontLeftPower, frontRightPower);
            telemetry.addData("Back left/Right", "%4.2f, %4.2f", backLeftPower, backRightPower);
            telemetry.update();
        }
    }
}