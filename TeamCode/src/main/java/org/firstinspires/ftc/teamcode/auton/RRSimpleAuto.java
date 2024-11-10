package org.firstinspires.ftc.teamcode.auton;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Arm;
import org.firstinspires.ftc.teamcode.AutonCommands;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.tuning.TuningOpModes;
@Autonomous(name = "TestAuto", group = "Autonomous")
public class RRSimpleAuto extends LinearOpMode {
    @Override

    public void runOpMode() throws InterruptedException {
        Pose2d beginPose = new Pose2d(0, -62, Math.PI /2);
        if (TuningOpModes.DRIVE_CLASS.equals(MecanumDrive.class)) {
            MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);
            AutonCommands autoComm = new AutonCommands(hardwareMap, beginPose);
            Arm bigArm = new Arm(drive.leftBigArm, drive.rightBigArm, drive.leftFront, -37);
            Arm smallArm = new Arm(drive.smallArm, drive.smallArm, 142, bigArm);
            Vector2d pos = new Vector2d(-52,-52);
            Pose2d basketScore = new Pose2d(-52,-50, Math.PI/4);
            Pose2d pos2 = new Pose2d(-34, -20, Math.PI/-6);
            Vector2d humanPlayerPark = new Vector2d(60,-60);


            waitForStart();

            TrajectoryActionBuilder tab1 = drive.actionBuilder(beginPose)
                    .splineToLinearHeading(basketScore, Math.PI);

            Actions.runBlocking(
                new ParallelAction(
                        new SequentialAction(
                                autoComm.initializeArm(),
                                tab1.build(),
                                new Action() {
                                    @Override
                                    public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                                        smallArm.setTarget(75.6);
                                        bigArm.setTarget(125);
                                        telemetry.addData("Arm", "targets set");
                                        return false;
                                    }
                                },
                                 new Action() {
                                    @Override
                                    public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                                        bigArm.periodic();
                                        smallArm.periodic();
                                        telemetry.addData("Periodic", "loop finished");
                                        return true;

                                    }
                                },
                                new Action() {
                                    @Override
                                    public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                                        bigArm.writeTelemetry(telemetry, "bigArm");
                                        smallArm.writeTelemetry(telemetry, "smallArm");
                                        telemetry.update();
                                        return false;
                                    }
                                },

                                autoComm.setIntakePower(-1)
                                //Tune and finish first part before continuing
                        ),
                        new Action() {
                            @Override
                            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
//                                bigArm.writeTelemetry(telemetry, "BigArm");
//                                smallArm.writeTelemetry(telemetry, "SmallArm");
//                                telemetry.update();
                                return true;
                            }
                        }
                )
            );
        }


    }
}
