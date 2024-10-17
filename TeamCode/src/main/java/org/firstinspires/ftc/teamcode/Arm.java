package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;


public class Arm {
    Arm base;     // Reference to big arm (used by little arm for FF).
    DcMotorEx motor;
    DcMotorEx encoder;
    double startingPose;


    public Arm(DcMotorEx motor, DcMotorEx encoder, double startingPose){
        this.motor = motor;
        this.encoder = encoder;
        this.startingPose = startingPose;
    }

    public Arm(DcMotorEx motor, DcMotorEx encoder, double startingPose, Arm base){
        this.motor = motor;
        this.encoder = encoder;
        this.startingPose = startingPose;
        this.base = base;
    }

    public double getPositionDegrees(){
        double initialArmAngle = startingPose;
        if(base != null) {
            initialArmAngle = base.startingPose;
        }
        return initialArmAngle + encoder.getCurrentPosition()*0.04453015;
    }


    private double getGravityFeedForward(){
        double angle = getPositionDegrees();
        double initial = .25;

        // If this Arm is connected to another arm segment
        if(base != null){
            angle+= base.getPositionDegrees();
            initial = .46;
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
    public void periodic(){

    }

    /*
        This function should be called by the main robot program to pass it's data to Telemetry.
     */
    public void writeTelemetry(Telemetry telemetry, String caption){

    }

    /*
        This function changes the arm's target position.
     */
    public void setTarget(){

    }
}
