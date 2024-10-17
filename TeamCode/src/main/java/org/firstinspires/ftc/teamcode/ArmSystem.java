package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;


public class ArmSystem {
    ArmSystem base;     // Reference to big arm (used by little arm for FF).
    DcMotorEx leftBigArm, rightBigArm, smallArm, intake;
    int bigArmEnc, smallArmEnc, armTargPos;
    double startingPose, bigArmSpeed, smallArmSpeed, initSmallAngle, initBaseAngle, baseArmMotorSpeed, smallArmMotorSpeed;


    public ArmSystem(HardwareMap hardwareMap, double startingPose, MecanumDrive drive){
        leftBigArm = hardwareMap.get(DcMotorEx.class, "leftBigArm");
        rightBigArm = hardwareMap.get(DcMotorEx.class, "rightBigArm");
        bigArmEnc = drive.leftFront.getCurrentPosition();
        leftBigArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBigArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBigArm.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBigArm.setDirection(DcMotorSimple.Direction.FORWARD);
        smallArm = hardwareMap.get(DcMotorEx.class, "smallArmMotor");
        smallArmEnc = smallArm.getCurrentPosition();
        intake = hardwareMap.get(DcMotorEx.class, "intake");
        smallArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        initBaseAngle = -37;
        initSmallAngle = 142;
    }

    /*
    public ArmSystem(HardwareMap hardwareMap, double startingPose, ArmSystem base){
        smallArm = hardwareMap.get(DcMotorEx.class, "smallArmMotor");
        smallArmEnc = smallArm.getCurrentPosition();
        intake = hardwareMap.get(DcMotorEx.class, "intake");
        smallArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    */
    public double getPositionDegrees(double initAngle, double encoder){
        return initAngle + encoder*0.04453015;
    }

    public double getGravityFeedForward(DcMotorEx encoder, double encAngle, double smallEncAngle){
        double initial = .25;
        double baseAngle = initBaseAngle;
        double angle = getPositionDegrees(initBaseAngle, encAngle);

        // If this Arm is connected to another arm segment
        if(encoder == smallArm){
            baseAngle = initSmallAngle;
            initial = .46;
            angle = getPositionDegrees(baseAngle, smallEncAngle) + getPositionDegrees(initBaseAngle, encAngle);
        }


        double radians = angle*(Math.PI/180);

        if(angle < 90){
            return Math.cos(radians)*initial;
        }
        else{
            return Math.cos(radians)*initial;
        }

        // Rest of the FeedForward code goes here.
    }

    /*
        This function should be called in the robot main loop and should set motor speeds.
     */
    public void periodic(Gamepad gamepad2, double pid, MecanumDrive drive){
        smallArmEnc = smallArm.getCurrentPosition();
        bigArmEnc = drive.leftFront.getCurrentPosition();
        baseArmMotorSpeed = pid;

        rightBigArm.setPower(baseArmMotorSpeed);
        leftBigArm.setPower(baseArmMotorSpeed);
        smallArm.setPower(smallArmMotorSpeed);
    }

    /*
        This function should be called by the main robot program to pass it's data to Telemetry.
     */
    public void writeTelemetry(Telemetry telemetry, double Pid){
        telemetry.addData("Big Arm Target", armTargPos);
        telemetry.addData("Big Arm PID", Pid);
        telemetry.addData("Big Arm Pos",  getPositionDegrees(initBaseAngle, bigArmEnc));
        telemetry.addData("Big Arm Power", leftBigArm.getPower());
        telemetry.addData("FF large", getGravityFeedForward(leftBigArm, bigArmEnc, smallArmEnc));
        telemetry.addData("Small Arm Pos",  getPositionDegrees(initSmallAngle, smallArmEnc));
        telemetry.addData("Small Arm Power", smallArm.getPower());
        telemetry.addData("Small Arm Real", getPositionDegrees(initSmallAngle, smallArmEnc) + getPositionDegrees(initBaseAngle, bigArmEnc));
        telemetry.addData("FF small", getGravityFeedForward(smallArm, bigArmEnc, smallArmEnc));
    }
    /*
        This function changes the arm's target position.
     */
    public void setTarget(){

    }
}
