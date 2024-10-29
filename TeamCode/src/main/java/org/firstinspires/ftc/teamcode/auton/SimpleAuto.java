package org.firstinspires.ftc.teamcode.auton;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.tuning.TuningOpModes;
@Autonomous(name = "TestAuto", group = "Autonomous")
public class SimpleAuto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d beginPose = new Pose2d(0, 0, 0);
        if (TuningOpModes.DRIVE_CLASS.equals(MecanumDrive.class)) {
            MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);

            waitForStart();

            double y = 0.25;
            double x = 0;
            double rx = 0;

            drive.leftFront.setPower(y + x + rx);
            drive.leftBack.setPower(y - x + rx);
            drive.rightFront.setPower(y - x - rx);
            drive.rightBack.setPower(y + x - rx);

            sleep(500);

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


//            TrajectoryActionBuilder tab1 = drive.actionBuilder(beginPose)
//                    .lineToX(54);
//
//            Actions.runBlocking(tab1.build());
        }


    }
}
