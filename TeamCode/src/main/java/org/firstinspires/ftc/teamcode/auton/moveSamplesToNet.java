package org.firstinspires.ftc.teamcode.auton;

import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Arm;
import org.firstinspires.ftc.teamcode.AutonCommands;
import org.firstinspires.ftc.teamcode.MecanumDrive;

@Autonomous(name = "SamplesToNetZone")
public class moveSamplesToNet extends LinearOpMode {



    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d beginPose = new Pose2d(-33,-62, Math.PI/2);
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);
        Arm bigArm = new Arm(drive.leftBigArm, drive.rightBigArm, drive.leftFront, -37);
        Arm smallArm = new Arm(drive.smallArm, drive.smallArm, 142, bigArm);
        Pose2d lastPose = beginPose;

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

        Pose2d basketScore = new Pose2d(-52,-50, Math.PI/4);

        AutonCommands.initAutonCommands(drive, smallArm, bigArm, telemetry);


        waitForStart();

        drive.leftBigArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        drive.rightBigArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        drive.smallArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        TrajectoryActionBuilder tab1 = drive.actionBuilder(beginPose)
                .strafeTo(new Vector2d(-36, -34)).strafeTo(new Vector2d(-43, -34)).strafeTo(new Vector2d(-36, -10)).strafeTo(new Vector2d(-47.5, -10))
                .strafeTo(new Vector2d(-47.5, -55)).strafeTo(new Vector2d(-47.5, -10)).strafeTo(new Vector2d(-55, -10)).strafeTo(new Vector2d(-55, -48))
                .strafeTo(new Vector2d(-55, -10)).strafeTo(new Vector2d(-64, -10)).strafeTo(new Vector2d(-64, -46)).strafeToLinearHeading(new Vector2d(-27.5, 5), Math.PI);//.turnTo(Math.PI/1.5)
        //.strafeTo(new Vector2d(-37, -39)).strafeTo(new Vector2d(-39, -37));

        TrajectoryActionBuilder tab2 = drive.actionBuilder(basketScore).turnTo(Math.PI/1.3)
                .strafeTo(new Vector2d(-28.5, -39));

        TrajectoryActionBuilder tab3 = drive.actionBuilder(new Pose2d(-32, -35, Math.PI/1.3))
                .strafeTo(new Vector2d(-42, -31));

        TrajectoryActionBuilder returnToBasketOne = drive.actionBuilder(new Pose2d(-42, -31,Math.PI/1.3))
                .strafeToLinearHeading(basketScore.position, Math.PI/4);

        TrajectoryActionBuilder prepIntakePosOne = drive.actionBuilder(new Pose2d(-41, -31, Math.PI/1.3))
                .strafeToLinearHeading(new Vector2d(-40, -25),Math.PI);

        bigArm.setTarget(-37);
        smallArm.setTarget(142);

        smallArm.periodic();
        bigArm.periodic();

        smallArm.writeTelemetry(telemetry, "Small Arm");
        bigArm.writeTelemetry(telemetry, "Big Arm");
        telemetry.update();

        Actions.runBlocking(
                new ParallelAction(
                    new SequentialAction(
                        tab1.build(),
                        AutonCommands.setArmPositionLowBasket()
                    ),
                        AutonCommands.runArmPeriodic()
                )

        );

    }


}
