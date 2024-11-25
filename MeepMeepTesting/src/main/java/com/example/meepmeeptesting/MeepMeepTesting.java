package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);
        Pose2d basketScore = new Pose2d(-53,-51, Math.PI/4);
        Pose2d pos2 = new Pose2d(-34, -20, Math.PI/-6);
        Vector2d humanPlayerPark = new Vector2d(60,-60);


        RoadRunnerBotEntity basketScorer = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();
        RoadRunnerBotEntity anotherBot = new DefaultBotBuilder(meepMeep)
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        RoadRunnerBotEntity specimenScore = new DefaultBotBuilder(meepMeep)
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();
        RoadRunnerBotEntity scoreObservation = new DefaultBotBuilder(meepMeep)
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();
//-62 mid start
        basketScorer.runAction(basketScorer.getDrive().actionBuilder(new Pose2d(-33, -62, Math.PI/2))
                .splineToLinearHeading(basketScore, Math.PI).turnTo(Math.PI/1.3)
                .strafeTo(new Vector2d(-29.5, -41)).strafeTo(new Vector2d(-41, -33)).strafeToLinearHeading(basketScore.position, Math.PI/4)
                .strafeToLinearHeading(new Vector2d(-35, -26),Math.PI).strafeTo(new Vector2d(-45, -26)).strafeToLinearHeading(basketScore.position, Math.PI/4)
                .strafeToLinearHeading(new Vector2d(-50, -26), Math.PI).strafeTo(new Vector2d(-54, -26)).strafeToLinearHeading(basketScore.position, Math.PI/4).build());



        anotherBot.runAction(anotherBot.getDrive().actionBuilder(new Pose2d(0, -62, Math.PI/2))
                .lineToY(-34).lineToY(-37).strafeTo(humanPlayerPark).build());


        specimenScore.runAction(specimenScore.getDrive().actionBuilder(new Pose2d(15,-62, Math.PI/2))
                .strafeTo(new Vector2d(36, -37)).strafeTo(new Vector2d(36, -10)).strafeTo(new Vector2d(45, -10))
                .strafeTo(new Vector2d(45, -55)).strafeTo(new Vector2d(45, -10)).strafeTo(new Vector2d(55, -10)).strafeTo(new Vector2d(55, -55))
                .strafeTo(new Vector2d(55, -10)).strafeTo(new Vector2d(65, -10)).strafeTo(new Vector2d(65, -55)).build());

        scoreObservation.runAction(scoreObservation.getDrive().actionBuilder(new Pose2d(-33,-62, Math.PI/2))
                .splineToLinearHeading(basketScore, Math.PI).strafeTo(new Vector2d(-36, -34)).turnTo(Math.PI/2).strafeTo(new Vector2d(-36, -28)).strafeTo(new Vector2d(-40, -28)).strafeTo(new Vector2d(-36, -10)).strafeTo(new Vector2d(-45, -10))
                .strafeTo(new Vector2d(-45, -55)).strafeTo(new Vector2d(-45, -10)).strafeTo(new Vector2d(-55, -10)).strafeTo(new Vector2d(-55, -48))
                .strafeTo(new Vector2d(-55, -10)).strafeTo(new Vector2d(-62, -10)).strafeTo(new Vector2d(-62, -46)).strafeTo(new Vector2d(-50, -5)).strafeToLinearHeading(new Vector2d(-25,-5), Math.PI).build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                //.addEntity(basketScorer)
                //.addEntity(anotherBot)
                .addEntity(specimenScore)
                //.addEntity(scoreObservation)
                .start();
    }
}