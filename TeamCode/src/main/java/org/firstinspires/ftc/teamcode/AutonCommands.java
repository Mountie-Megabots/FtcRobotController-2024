package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public final class AutonCommands{
    static Arm smallArm, bigArm;
    static MecanumDrive drive;
    static Telemetry telemetry;

    public static void initAutonCommands(MecanumDrive d, Arm s, Arm b, Telemetry t){
         drive = d;
         smallArm = s;
         bigArm = b;
         telemetry = t;
    }

    public static Action runArmPeriodic(){
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                smallArm.periodic();
                bigArm.periodic();
                smallArm.writeTelemetry(telemetry, "Small Arm");
                bigArm.writeTelemetry(telemetry, "Big Arm");
                telemetry.addLine("Periodic has ran");
                telemetry.update();
                return true;
            }
        };
    }

    public static Action getScoreLowBasket(){
        return new SequentialAction(
                AutonCommands.setArmPositionLowBasket(),
                new SleepAction(2),
                AutonCommands.setIntakePower(.5),
                new SleepAction(2),
                AutonCommands.setArmPositionHome(),
                new SleepAction(1));
    }

    public static Action getIntakeAction(){
        return new SequentialAction(
                AutonCommands.setIntakePower(-1),
                AutonCommands.setArmPositionIntake(),
                new SleepAction(.5),
                AutonCommands.setSmallArmManual(.3));
    }

    public static Action setArmPositionLowBasket(){
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                smallArm.setTarget(75.6);
                bigArm.setTarget(94.2);
                drive.intake.setPower(-1);
                return false;
            }
        };
    }

    public static Action setArmPositionHome(){
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                smallArm.setTarget(142);
                bigArm.setTarget(-37);
                return false;
            }
        };
    }

    public static Action setArmPositionIntake(){
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                smallArm.setTarget(30);
                bigArm.setTarget(-37);
                return false;
            }
        };
    }

    public static Action setIntakePower(double power){
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                drive.intake.setPower(power);
                return false;
            }
        };
    }

    private static Action setSmallArmManual(double power){
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                smallArm.setManual(power);
                return false;
            }
        };
    }

    /*public Action intakeSample(){
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

     */

}
