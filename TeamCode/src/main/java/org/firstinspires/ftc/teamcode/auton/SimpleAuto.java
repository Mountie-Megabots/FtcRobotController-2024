package org.firstinspires.ftc.teamcode.auton;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Arm;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.tuning.TuningOpModes;
@Autonomous(name = "TestAuto", group = "Autonomous")
public class SimpleAuto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d beginPose = new Pose2d(0, 0, 0);
        if (TuningOpModes.DRIVE_CLASS.equals(MecanumDrive.class)) {
            MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);
            Arm bigArm = new Arm(drive.leftBigArm, drive.rightBigArm, drive.leftFront, -37);
            Arm smallArm = new Arm(drive.smallArm, drive.smallArm, 142, bigArm);
            Vector2d pos = new Vector2d(-52,-52);

            waitForStart();

            TrajectoryActionBuilder tab1 = drive.actionBuilder(beginPose)
                    .splineTo(pos, -3).turn(Math.toRadians(-143.6));

            Actions.runBlocking(new SequentialAction(
                    tab1.build(),
                    new Action() {
                        @Override
                        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                            smallArm.setTarget(75.6);
                            bigArm.setTarget(94.2);
                            return Math.abs(smallArm.getPositionDegrees()) > 5 || Math.abs(bigArm.getPositionDegrees()) > 5;
                        }
                    },
                    new Action() {
                        @Override
                        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                            drive.intake.setPower(-1);
                            return false;
                        }
                    },
                    new SleepAction(5)
                    //Tune and finish first part before continuing
            ));
        }


    }
}
