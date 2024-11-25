package org.firstinspires.ftc.teamcode.auton;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Arm;
import org.firstinspires.ftc.teamcode.MecanumDrive;


@Autonomous(name = "ParkAuton", group = "Autonomous")
public class ParkAuton extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException{
        Pose2d beginPose = new Pose2d(0, -62, Math.PI /2);
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);
        Arm bigArm = new Arm(drive.leftBigArm, drive.rightBigArm, drive.leftFront, -37);
        Arm smallArm = new Arm(drive.smallArm, drive.smallArm, 127, bigArm);


        init();

        smallArm.setManual(-1);

        waitForStart();

            double y = 0.25;
            double x = 0;
            double rx = 0;

            drive.leftFront.setPower(y + x + rx);
            drive.leftBack.setPower(y - x + rx);
            drive.rightFront.setPower(y - x - rx);
            drive.rightBack.setPower(y + x - rx);

    sleep(100);

    y = 0;
    x = 0.4;
    rx = 0;

            drive.leftFront.setPower(y + x + rx);
            drive.leftBack.setPower(y - x + rx);
            drive.rightFront.setPower(y - x - rx);
            drive.rightBack.setPower(y + x - rx);

    sleep(5000);

    x = 0;

            drive.leftFront.setPower(y + x + rx);
            drive.leftBack.setPower(y - x + rx);
            drive.rightFront.setPower(y - x - rx);
            drive.rightBack.setPower(y + x - rx);
 }
}
