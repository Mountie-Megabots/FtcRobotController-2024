package org.firstinspires.ftc.teamcode.auton;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TimeTrajectory;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.tuning.TuningOpModes;
@Autonomous(name = "TestAuto", group = "Autonomous")
public class SampleAuton extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d beginPose = new Pose2d(0, 0, 0);
        if (TuningOpModes.DRIVE_CLASS.equals(MecanumDrive.class)) {
            MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);

            waitForStart();


            TrajectoryActionBuilder tab1 = drive.actionBuilder(beginPose)
                    .lineToXSplineHeading(33, Math.toRadians(0))
                    .waitSeconds(2)
                    .setTangent(Math.toRadians(90))
                    .lineToY(48)
                    .setTangent(Math.toRadians(0))
                    .lineToX(32)
                    .strafeTo(new Vector2d(44.5, 30))
                    .turn(Math.toRadians(180))
                    .lineToX(47.5)
                    .waitSeconds(3);

            Actions.runBlocking(tab1.build());
        }


    }
}
