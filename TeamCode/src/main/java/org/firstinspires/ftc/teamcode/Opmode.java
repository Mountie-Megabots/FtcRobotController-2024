package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "Teleop")
public class Opmode extends LinearOpMode {

    DcMotor m_frontLeft;
    DcMotor m_frontRight;
    DcMotor m_backLeft;
    DcMotor m_backRight;
    @Override
    public void runOpMode() throws InterruptedException {
        m_backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        m_backRight = hardwareMap.get(DcMotor.class, "backRight");
        m_frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        m_frontRight = hardwareMap.get(DcMotor.class, "frontRight");

        m_backLeft.setDirection(DcMotor.Direction.REVERSE);
        m_frontLeft.setDirection(DcMotor.Direction.REVERSE);
        m_backRight.setDirection(DcMotor.Direction.FORWARD);
        m_frontRight.setDirection(DcMotor.Direction.FORWARD);

        waitForStart();

        while(opModeIsActive()){
            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x;
            double rotation = gamepad1.right_stick_x;

            m_frontLeft.setPower(y + x + rotation);
            m_backLeft.setPower(y - x + rotation);
            m_frontRight.setPower(y - x - rotation);
            m_backRight.setPower(y + x - rotation);
        }
    }
}
