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
        Arm bigArm = new Arm(drive.leftBigArm, drive.rightBigArm, drive.leftFront, -37);
        Arm smallArm = new Arm(drive.smallArm, drive.smallArm, 142, bigArm);

        drive.leftBigArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drive.rightBigArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drive.smallArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        bigArm.setPID(.03, .1, 0);
        bigArm.setIZone(6);
        smallArm.setPID(.01, 0, 0);

        // Set limits
        bigArm.setForwardLimit(126.4);
        bigArm.setBackwardLimit(-37);

        smallArm.setForwardLimit(141);
        smallArm.setBackwardLimit(-117.6);



        waitForStart();

        drive.leftBigArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        drive.rightBigArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        drive.smallArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        bigArm.setTarget(-37);
        smallArm.setTarget(142);



        while(opModeIsActive()){

            double y = -gamepad1.left_stick_y; // Remember, Y stick is reversed!
            double x = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;

            drive.leftFront.setPower(y + x + rx);
            drive.leftBack.setPower(y - x + rx);
            drive.rightFront.setPower(y - x - rx);
            drive.rightBack.setPower(y + x - rx);


            //Large Arm
            //down pos = -34 deg -20 tick
            // straight up 90 deg 2852 tick
            // 0.043175 degrees per tick



            //Small Arm;
            //home 137 deg 9 tick
            // 0 deg -3100 tick

            //Big Arm Controls
            if(Math.abs(gamepad2.left_stick_y) > 0.05){
                bigArm.setManual(-gamepad2.left_stick_y);
            }
            else{
                bigArm.setManual(0);
            }

            if(gamepad2.left_bumper){
                drive.intake.setPower(-1);
            }
            //else if(gamepad2.right_bumper){
            //    drive.intake.setPower(1);
            //}
            else{
                drive.intake.setPower(gamepad2.left_trigger);
            }

            if(gamepad2.right_trigger > .1){
                smallArm.setTargetToCurrent();
                bigArm.setTargetToCurrent();
            }

            // Home Position
            if(gamepad2.a){
                smallArm.setTarget(141.9);
                bigArm.setTarget(-38);
            }
            // Intake Position
            else if(gamepad2.x){
                smallArm.setTarget(28);
                bigArm.setTarget(-37);

            }
            // Basket Score
            else if(gamepad2.b){
            smallArm.setTarget(75.6);
            bigArm.setTarget(94.2);
            }
            // Specimen Score
            else if(gamepad2.dpad_down){
                smallArm.setTarget(33);
                bigArm.setTarget(50.5);
            }

            if(Math.abs(gamepad2.right_stick_y) > 0.05){
                smallArm.setManual(-gamepad2.right_stick_y);
            }
            else{
                smallArm.setManual(0);
            }

            smallArm.periodic();
            bigArm.periodic();

            smallArm.writeTelemetry(telemetry, "Small Arm");
            bigArm.writeTelemetry(telemetry, "Big Arm");




            telemetry.update();


        }
    }

    double getArmDegrees(int encoder){
        double intitialArmAngle = -37;
        return intitialArmAngle + encoder*0.04453015;
    }

    double getSmallArmDegrees(int encoder){
        double initialSmallArmAngle = 142;
        return initialSmallArmAngle + encoder*0.04453015;
    }

    double getSmallArmRealAngle(int small, int big){
         return getSmallArmDegrees(small) + getArmDegrees(big);
    }



    double getArmFeedForward(double angle){
        double initial = .25;

        double radians = angle*(Math.PI/180);

        if(angle < 90){
            return Math.cos(radians)*initial;
        }
        else{
            return Math.cos(radians)*initial;
        }
    }

    double getSmallArmFeedForward(double angle){
        double initial = .46;

        double radians = angle*(Math.PI/180);

        if(angle < 90){
            return Math.cos(radians)*initial;
        }
        else{
            return Math.cos(radians)*initial;
        }
    }
}
