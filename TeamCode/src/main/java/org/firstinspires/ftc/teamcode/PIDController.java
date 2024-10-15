package org.firstinspires.ftc.teamcode;

public class PIDController {

    private double P, I, D;
    private double prevError = 0;
    private double integral = 0;
    private long lastTime = 0;
    private double lastGoal = 0;

    PIDController(double P, double I, double D){
        this.P = P;
        this.I = I;
        this.D = D;
    }

    public double calculate(double goal, double currentPosition){
        double error = goal - currentPosition;
        double derivative = 0;
        double output = 0;
        long currentTime = System.currentTimeMillis();
        long elaspsedTime = currentTime - lastTime;

        if( goal != lastGoal){
            this.reset(goal, error);
        }

        integral += error/elaspsedTime;
        derivative = (error-prevError)/elaspsedTime;
        output = P * error + I * integral + D * derivative;

        prevError = error;
        lastTime = currentTime;
        lastGoal = goal;

        return output;
    }

    private void reset(double goal, double error){
        lastTime = System.currentTimeMillis();
        lastGoal = goal;
        integral = 0;
        prevError = error;

    }

    void setP(double P){
        this.P = P;
    }

    void setI(double I){
        this.I = I;
    }

    void setD(double D){
        this.D = D;
    }

    double getP(){
        return this.P;
    }

    double getI(){
        return this.I;
    }

    double getD(){
        return this.D;
    }
}
