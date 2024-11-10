package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class AutonCommands{
    Arm smallArm, bigArm;
    MecanumDrive drive;

    public AutonCommands(HardwareMap hardwareMap, Pose2d beginPose){
         drive = new MecanumDrive(hardwareMap, beginPose);
         bigArm = new Arm(drive.leftBigArm, drive.rightBigArm, drive.leftFront, -37);
         smallArm = new Arm(drive.smallArm, drive.smallArm, 142, bigArm);
    }

    public Action initializeArm(){
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                drive.leftBigArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                drive.rightBigArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                drive.smallArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

                bigArm.setPID(.03, .1, 0);
                bigArm.setIZone(6);
                smallArm.setPID(.01, 0, 0);

                // Set limits
//                bigArm.setForwardLimit(126.4);
//                bigArm.setBackwardLimit(-37);
//
//                smallArm.setForwardLimit(141);
//                smallArm.setBackwardLimit(-117.6);

                drive.leftBigArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                drive.rightBigArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                drive.smallArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


                bigArm.setTarget(-37);
                smallArm.setTarget(142);
                telemetryPacket.addLine("Bot Initialized. BigArm goal = " + bigArm.goal + " SmallArm goal = " + smallArm.goal);
                return false;
            }
        };
    }

    public Action runArmPeriodic(){
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                bigArm.periodic();
                smallArm.periodic();
                telemetryPacket.addLine("Periodic running");
                return Math.abs(smallArm.goal - smallArm.getPositionDegrees()) > 5
                        || Math.abs(bigArm.goal - bigArm.getPositionDegrees()) > 5;
            }
        };
    }

    public Action bigArmTarget(double target){
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                bigArm.goal = target;
                telemetryPacket.addLine("BigArm new goal = " + bigArm.goal);
                return false;
            }
        };
    }

    public Action smallArmTarget(double target){
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                smallArm.goal = target;
                telemetryPacket.addLine("SmallArm new goal = " + smallArm.goal);
                return false;
            }
        };
    }

    public Action setIntakePower(double power){
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                drive.intake.setPower(power);
                return false;
            }
        };
    }

    public Action lowBasket(){
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                new SequentialAction(
                        smallArmTarget(75.6),
                        bigArmTarget(94.2),
                        runArmPeriodic(),
                        setIntakePower(-1)
                );
                return false;
            }
        };
    }

    public Action intakeSample(){
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                new SequentialAction(
                        new ParallelAction(
                                smallArmTarget(34),
                                bigArmTarget(-37)
                        ),
                        setIntakePower(1),
                        new SleepAction(1),
                        setIntakePower(0)
                );
                return false;
            }
        };
    }

    public Action homePosition(){
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                new ParallelAction(
                        smallArmTarget(141.9),
                        bigArmTarget(-38),
                        runArmPeriodic()
                );
                return false;
            }
        };
    }

    public Action specimenGrab(){
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                smallArmTarget(10);
                bigArmTarget(10);
                return false;
            }
        };
    }

}
