package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;


public class Arm {
    Arm base;     // Reference to big arm (used by little arm for FF).
    DcMotorEx motor;
    DcMotorEx motor1;
    DcMotorEx encoder;
    PIDController pid;

    // Fixed values
    double startingPose;
    double forwardLimit;
    double backwardLimit;
    double limitBias = 1;

    double goal = 0;
    double manual = 0;



    public Arm(DcMotorEx motor, DcMotorEx motor1, DcMotorEx encoder, double startingPose){
        this.motor = motor;
        this.motor1= motor1;
        this.encoder = encoder;
        this.startingPose = startingPose;
        pid = new PIDController(.1,0,0);
    }

    public Arm(DcMotorEx motor, DcMotorEx encoder, double startingPose, Arm base){
        this.motor = motor;
        this.encoder = encoder;
        this.startingPose = startingPose;
        this.base = base;
        pid = new PIDController(.1,0,0);
    }

    public double getPositionDegrees(){
        double initialArmAngle = startingPose;
//        if(base != null) {
//            initialArmAngle = base.startingPose;
//        }
        return initialArmAngle + encoder.getCurrentPosition()*0.04453015;
    }


    private double getGravityFeedForward(){
        double angle = getPositionDegrees();
        double initial = .20;

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
        double motorSpeed = 0;
        double FF = this.getGravityFeedForward();
        if(Math.abs(manual) > .05 && Math.abs(manual) > .75){
            motorSpeed = manual+FF;
        }
        else if(Math.abs(manual) >= .75){
            motorSpeed = manual;
        }
        else{
            double pidValue = pid.calculate(goal, this.getPositionDegrees());
            motorSpeed = pidValue + FF;
        }


        // Enforce limits
        if( motorSpeed > 0 && this.getPositionDegrees() > forwardLimit*limitBias){
            motorSpeed = FF;
        }
        else if( motorSpeed < 0 && this.getPositionDegrees() < backwardLimit*limitBias){
            motorSpeed = FF;
        }

        if(base == null){
            motor.setPower(motorSpeed);
        }
        else{
            motor.setPower(motorSpeed/2);
        }

        if(motor1 != null){
            motor1.setPower(motorSpeed);
        }
    }

    /*
        This function should be called by the main robot program to pass it's data to Telemetry.
     */
    public void writeTelemetry(Telemetry telemetry, String caption){
        telemetry.addData(caption+" Encoder", encoder.getCurrentPosition());
        telemetry.addData(caption+" Angle", this.getPositionDegrees());
        telemetry.addData(caption+" Power", motor.getPower());
        telemetry.addData(caption+" FF", getGravityFeedForward());
        telemetry.addData(caption +" Goal", this.goal);

        if(base != null){
            telemetry.addData(caption+" Real Angle", base.getPositionDegrees()+this.getPositionDegrees());
        }
    }

    /*
        This function changes the arm's target position.
     */
    public void setTarget(double target){
        goal = target;
    }

    public void setManual(double x){
        manual = x;
    }

    public void setPID(double p, double i, double d){
        this.pid.setP(p);
        this.pid.setI(i);
        this.pid.setD(d);
    }

    public void setIZone(double iz){
        this.pid.setIZone(iz);
    }

    public void setForwardLimit(double limit){
        this.forwardLimit = limit;
    }

    public void setBackwardLimit(double limit){
        this.backwardLimit = limit;
    }

    public void setLimitBias(double limit){
        this.limitBias = limit;
    }

    private void setMotorPower(double power){
        motor.setPower(power);

        if(base == null){
            motor1.setPower(power);
        }
    }

    public void setTargetToCurrent(){
        setTarget(this.getPositionDegrees());
    }
}
