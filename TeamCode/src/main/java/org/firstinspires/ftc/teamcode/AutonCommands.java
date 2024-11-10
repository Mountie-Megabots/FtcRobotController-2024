package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class AutonCommands {
    Arm smallArm, bigArm;
    MecanumDrive drive;

    public AutonCommands(HardwareMap hardwareMap, Pose2d beginPose){
         drive = new MecanumDrive(hardwareMap, beginPose);
         bigArm = new Arm(drive.leftBigArm, drive.rightBigArm, drive.leftFront, -37);
         smallArm = new Arm(drive.smallArm, drive.smallArm, 142, bigArm);
    }

    public Action bigArmTarget(double target){
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                bigArm.setTarget(target);
                return Math.abs(target - bigArm.getPositionDegrees()) > 5;
            }
        };
    }

    public Action smallArmTarget(double target){
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                smallArm.setTarget(target);
                return Math.abs(target - smallArm.getPositionDegrees()) > 5;
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
                    new ParallelAction(
                        smallArmTarget(75.6),
                        bigArmTarget(94.2)
                    ),
                        setIntakePower(-1)
                    );
                return Math.abs(smallArm.getPositionDegrees()) > 5 || Math.abs(bigArm.getPositionDegrees()) > 5;
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
                        bigArmTarget(-38)
                );
                return Math.abs(smallArm.getPositionDegrees()) > 5 || Math.abs(bigArm.getPositionDegrees()) > 5;
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
