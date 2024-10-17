package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.canvas.Rotation;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Rotation2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.PIDController;

@TeleOp(name = "Teleop")
public class Teleop extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d beginPose = new Pose2d(0, 0, 0);
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);
        PIDController pid = new PIDController(.1, 0, 0);
        ArmSystem armSystem = new ArmSystem(hardwareMap, -5, drive);
        //ArmSystem smallArm = new ArmSystem(hardwareMap, -24, baseArm);

        waitForStart();

        armSystem.leftBigArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armSystem.rightBigArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armSystem.smallArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        armSystem.leftBigArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        armSystem.rightBigArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        armSystem.smallArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        armSystem.armTargPos = 0;

        while(opModeIsActive()){

//            double y = -gamepad1.left_stick_y; // Remember, Y stick is reversed!
//            double x = gamepad1.left_stick_x;
//            double rx = gamepad1.right_stick_x;
//
//            drive.leftFront.setPower(y + x + rx);
//            drive.leftBack.setPower(y - x + rx);
//            drive.rightFront.setPower(y - x - rx);
//            drive.rightBack.setPower(y + x - rx);

            //Large Arm
            //down pos = -34 deg -20 tick
            // straight up 90 deg 2852 tick
            // 0.043175 degrees per tick

            //Small Arm;
            //home 137 deg 9 tick
            // 0 deg -3100 tick

            if(gamepad2.left_bumper){
                armSystem.armTargPos = -32;
            }
            else if(gamepad2.right_bumper){
               armSystem.armTargPos = 75;
            } else {
                armSystem.baseArmMotorSpeed = armSystem.getGravityFeedForward(armSystem.leftBigArm, armSystem.bigArmEnc, armSystem.smallArmEnc);
            }

            if(Math.abs(gamepad2.right_stick_y) > 0.05){
                armSystem.smallArmMotorSpeed = gamepad2.right_stick_y/1.35;
            } else {
                armSystem.smallArmMotorSpeed = armSystem.getGravityFeedForward(armSystem.smallArm, armSystem.bigArmEnc, armSystem.smallArmEnc);
            }

            double pidValue = pid.calculate(armSystem.armTargPos, armSystem.getPositionDegrees(armSystem.initBaseAngle, armSystem.bigArmEnc));

            armSystem.periodic(gamepad2, pidValue, drive);

            armSystem.intake.setPower(gamepad2.right_trigger);
            armSystem.intake.setPower(-gamepad2.left_trigger);

            armSystem.writeTelemetry(telemetry, pidValue);

            telemetry.update();
        }
    }
}