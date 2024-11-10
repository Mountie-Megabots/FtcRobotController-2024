package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Rotation2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.Vector2dDual;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);
        Pose2d basketScore = new Pose2d(-48,-48, Math.PI/4);
        Pose2d pos2 = new Pose2d(-34, -20, Math.PI/-6);
        Vector2d humanPlayerPark = new Vector2d(60,-60);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();
        RoadRunnerBotEntity anotherBot = new DefaultBotBuilder(meepMeep)
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                        .build();
        RoadRunnerBotEntity andAnotherBot = new DefaultBotBuilder(meepMeep)
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();
//-62 mid start
        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(0, -62, Math.PI/2))
                .splineToLinearHeading(basketScore, Math.PI).splineToLinearHeading(pos2, -Math.PI).build());
        anotherBot.runAction(anotherBot.getDrive().actionBuilder(new Pose2d(0, -62, Math.PI/2))
                .lineToY(-34).lineToY(-37).strafeTo(humanPlayerPark).build());
        andAnotherBot.runAction(andAnotherBot.getDrive().actionBuilder(new Pose2d(0,-62, Math.PI/2))
                .lineToY(-34).lineToY(-37).build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .addEntity(anotherBot)
                .addEntity(andAnotherBot)
                .start();
    }
}