// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.SensorVelocityMeasPeriod;

import frc.robot.util.TalonUtil;

/** Add your docs here. */
public class TalonConfigs {

    static interface TalonConfigurator {
        void configure(WPI_TalonSRX talon);
    }

    static class HangarConfigs {
        static void configureArm(WPI_TalonSRX talon) {
            TalonSRXConfiguration config = new TalonSRXConfiguration();

            config.peakCurrentLimit = 15;
            config.peakCurrentDuration = 10;
            config.continuousCurrentLimit = 10;

            config.primaryPID.selectedFeedbackSensor = FeedbackDevice.QuadEncoder;
            config.primaryPID.selectedFeedbackCoefficient = 1.0;
            config.auxiliaryPID.selectedFeedbackSensor = FeedbackDevice.None;

            config.forwardLimitSwitchSource = LimitSwitchSource.Deactivated;
            config.reverseLimitSwitchSource = LimitSwitchSource.Deactivated;

            config.openloopRamp = 1.0;
            config.closedloopRamp = 1.0;

            config.peakOutputForward = 0.5;
            config.peakOutputReverse = -0.5;

            config.nominalOutputForward = 0.0;
            config.nominalOutputReverse = 0.0;

            config.neutralDeadband = 0.01;

            config.voltageCompSaturation = 10.0;
            config.voltageMeasurementFilter = 32; // default = 32
            config.velocityMeasurementPeriod = SensorVelocityMeasPeriod.Period_100Ms; // default = 100ms
            config.velocityMeasurementWindow = 64;// default = 64;
            config.forwardSoftLimitThreshold = 10000;
            config.forwardSoftLimitEnable = false;
            config.reverseSoftLimitThreshold = -1000;
            config.reverseSoftLimitEnable = false;

            config.motionCruiseVelocity = 100;
            config.motionAcceleration = 100;
            config.motionCurveStrength = 4;

            config.clearPositionOnLimitF = false;
            config.clearPositionOnLimitR = false;
            config.clearPositionOnQuadIdx = false;
            config.limitSwitchDisableNeutralOnLOS = true;
            config.softLimitDisableNeutralOnLOS = true;

            config.motionProfileTrajectoryPeriod = 1;
            config.trajectoryInterpolationEnable = true;

            config.slot0.kP = 0.001;
            config.slot0.kI = 0.0;
            config.slot0.kD = 0.0;
            config.slot0.integralZone = 10;
            config.slot0.allowableClosedloopError = 10;
            config.slot0.maxIntegralAccumulator = 100;
            config.slot0.closedLoopPeakOutput = 1.0;
            config.slot0.closedLoopPeriod = 1;

            /*
             * TODO: verify that this is still true
             * things not handled with config:
             * []Current Limit Enable (though the thresholds are configs)
             * []Voltage Compensation Enable (though the nominal voltage is a config)
             * Control Mode and Target/Output demand (percent, position, velocity, etc.)
             * []Invert direction and sensor phase
             * []Closed-loop slot selection [0,3] for primary and aux PID loops.
             * Neutral mode override (convenient to temporarily override configs)
             * Limit switch override (convenient to temporarily override configs)
             * Soft Limit override (convenient to temporarily override configs)
             * Status Frame Periods
             */

            TalonUtil.check(talon.configAllSettings(config, 50));

            talon.enableCurrentLimit(true);
            talon.enableVoltageCompensation(true);
            talon.selectProfileSlot(0, 0);

            talon.setInverted(true);
            talon.setSensorPhase(false);
            talon.setNeutralMode(NeutralMode.Brake);
        }

        static void configureWinch(WPI_TalonSRX talon) {

            TalonSRXConfiguration config = new TalonSRXConfiguration();

            config.peakCurrentLimit = 20;
            config.peakCurrentDuration = 10;
            config.continuousCurrentLimit = 15;

            config.primaryPID.selectedFeedbackSensor = FeedbackDevice.QuadEncoder;
            config.primaryPID.selectedFeedbackCoefficient = 1.0;
            config.auxiliaryPID.selectedFeedbackSensor = FeedbackDevice.None;

            config.forwardLimitSwitchSource = LimitSwitchSource.Deactivated;
            config.reverseLimitSwitchSource = LimitSwitchSource.Deactivated;

            config.openloopRamp = 1.0;
            config.closedloopRamp = 1.0;

            config.peakOutputForward = 1.0;
            config.peakOutputReverse = -1.0;

            config.nominalOutputForward = 0.0;
            config.nominalOutputReverse = 0.0;

            config.neutralDeadband = 0.01;

            config.voltageCompSaturation = 10.0;
            config.voltageMeasurementFilter = 32; // default = 32
            config.velocityMeasurementPeriod = SensorVelocityMeasPeriod.Period_100Ms; // default = 100ms
            config.velocityMeasurementWindow = 64;// default = 64;
            config.forwardSoftLimitThreshold = 100;
            config.forwardSoftLimitEnable = false;
            config.reverseSoftLimitThreshold = -1700;
            config.reverseSoftLimitEnable = false;

            config.motionCruiseVelocity = 100;
            config.motionAcceleration = 100;
            config.motionCurveStrength = 4;

            config.clearPositionOnLimitF = false;
            config.clearPositionOnLimitR = false;
            config.clearPositionOnQuadIdx = false;
            config.limitSwitchDisableNeutralOnLOS = true;
            config.softLimitDisableNeutralOnLOS = true;

            config.motionProfileTrajectoryPeriod = 1;
            config.trajectoryInterpolationEnable = true;

            config.slot0.kP = 0.001;
            config.slot0.kI = 0.0;
            config.slot0.kD = 0.0;
            config.slot0.integralZone = 10;
            config.slot0.allowableClosedloopError = 10;
            config.slot0.maxIntegralAccumulator = 100;
            config.slot0.closedLoopPeakOutput = 1.0;
            config.slot0.closedLoopPeriod = 1;

            /*
             * TODO: verify that this is still true
             * things not handled with config:
             * []Current Limit Enable (though the thresholds are configs)
             * []Voltage Compensation Enable (though the nominal voltage is a config)
             * Control Mode and Target/Output demand (percent, position, velocity, etc.)
             * []Invert direction and sensor phase
             * []Closed-loop slot selection [0,3] for primary and aux PID loops.
             * Neutral mode override (convenient to temporarily override configs)
             * Limit switch override (convenient to temporarily override configs)
             * Soft Limit override (convenient to temporarily override configs)
             * Status Frame Periods
             */

            TalonUtil.check(talon.configAllSettings(config, 50));

            talon.setInverted(false);
            talon.setSensorPhase(false);

            talon.setNeutralMode(NeutralMode.Brake);
            talon.enableCurrentLimit(true);
            talon.enableVoltageCompensation(true);
            talon.selectProfileSlot(0, 0);
        }

        static void configureHook(WPI_TalonSRX talon) {

            TalonSRXConfiguration config = new TalonSRXConfiguration();

            config.peakCurrentLimit = 15;
            config.peakCurrentDuration = 10;
            config.continuousCurrentLimit = 10;

            config.primaryPID.selectedFeedbackSensor = FeedbackDevice.QuadEncoder;
            config.primaryPID.selectedFeedbackCoefficient = 1.0;
            config.auxiliaryPID.selectedFeedbackSensor = FeedbackDevice.None;

            config.forwardLimitSwitchSource = LimitSwitchSource.Deactivated;
            config.reverseLimitSwitchSource = LimitSwitchSource.Deactivated;

            config.openloopRamp = 0.0;
            config.closedloopRamp = 0.0;

            config.peakOutputForward = 0.3;
            config.peakOutputReverse = -0.3;

            config.nominalOutputForward = 0.0;
            config.nominalOutputReverse = 0.0;

            config.neutralDeadband = 0.01;

            config.voltageCompSaturation = 10.0;
            config.voltageMeasurementFilter = 32; // default = 32
            config.velocityMeasurementPeriod = SensorVelocityMeasPeriod.Period_100Ms; // default = 100ms
            config.velocityMeasurementWindow = 64;// default = 64;
            config.forwardSoftLimitThreshold = 10000;
            config.forwardSoftLimitEnable = false;
            config.reverseSoftLimitThreshold = -1000;
            config.reverseSoftLimitEnable = false;

            config.motionCruiseVelocity = 100;
            config.motionAcceleration = 100;
            config.motionCurveStrength = 4;

            config.clearPositionOnLimitF = false;
            config.clearPositionOnLimitR = false;
            config.clearPositionOnQuadIdx = false;
            config.limitSwitchDisableNeutralOnLOS = true;
            config.softLimitDisableNeutralOnLOS = true;

            config.motionProfileTrajectoryPeriod = 1;
            config.trajectoryInterpolationEnable = true;

            config.slot0.kP = 0.001;
            config.slot0.kI = 0.0;
            config.slot0.kD = 0.0;
            config.slot0.integralZone = 10;
            config.slot0.allowableClosedloopError = 10;
            config.slot0.maxIntegralAccumulator = 100;
            config.slot0.closedLoopPeakOutput = 1.0;
            config.slot0.closedLoopPeriod = 1;

            /*
             * TODO: verify that this is still true
             * things not handled with config:
             * []Current Limit Enable (though the thresholds are configs)
             * []Voltage Compensation Enable (though the nominal voltage is a config)
             * Control Mode and Target/Output demand (percent, position, velocity, etc.)
             * []Invert direction and sensor phase
             * []Closed-loop slot selection [0,3] for primary and aux PID loops.
             * Neutral mode override (convenient to temporarily override configs)
             * Limit switch override (convenient to temporarily override configs)
             * Soft Limit override (convenient to temporarily override configs)
             * Status Frame Periods
             */

            TalonUtil.check(talon.configAllSettings(config, 50));
            talon.setInverted(false);
            talon.setSensorPhase(true);

            talon.setNeutralMode(NeutralMode.Brake);
            talon.enableCurrentLimit(true);
            talon.enableVoltageCompensation(true);
            talon.selectProfileSlot(0, 0);
        }
    }

    public static void configureHangarTalons(HangarSubsystem hangar) {
        HangarConfigs.configureArm(hangar.arm);
        HangarConfigs.configureHook(hangar.hook);
        HangarConfigs.configureWinch(hangar.winch);
    }
}
