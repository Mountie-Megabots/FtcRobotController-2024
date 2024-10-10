package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Teleop")
public class Teleop extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d beginPose = new Pose2d(0, 0, 0);
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);


        waitForStart();

        drive.leftBigArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drive.rightBigArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drive.smallArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        drive.leftBigArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        drive.rightBigArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        drive.smallArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);





        while(opModeIsActive()){

//            double y = -gamepad1.left_stick_y; // Remember, Y stick is reversed!
//            double x = gamepad1.left_stick_x;
//            double rx = gamepad1.right_stick_x;
//
//            drive.leftFront.setPower(y + x + rx);
//            drive.leftBack.setPower(y - x + rx);
//            drive.rightFront.setPower(y - x - rx);
//            drive.rightBack.setPower(y + x - rx);
            int bigArmEnc = drive.leftFront.getCurrentPosition();
            int smallArmEnc = drive.smallArm.getCurrentPosition();

            telemetry.addData("Big Arm pos", bigArmEnc);
            telemetry.addData("Small Arm pos", smallArmEnc);

            drive.leftBigArm.setPower(-gamepad2.left_stick_y);
            drive.rightBigArm.setPower(-gamepad2.left_stick_y);
            drive.smallArm.setPower(gamepad2.right_stick_y);
            drive.intake.setPower(gamepad2.right_trigger);

            if(gamepad2.left_trigger >= 0.75){

            }



            telemetry.update();


        }
    }
}
