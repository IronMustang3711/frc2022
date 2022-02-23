package frc.robot.util;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardContainer;

import java.util.EnumSet;
import java.util.Set;

public class TalonUtil {

  private static final Set<ControlMode> CLOSED_LOOP_MODES = EnumSet.complementOf(EnumSet.of(ControlMode.Disabled,
      ControlMode.Follower,
      ControlMode.PercentOutput,
      ControlMode.MusicTone));

  private static final Set<ControlMode> MOTION_PROFILE_MODES = EnumSet.of(ControlMode.MotionMagic,
      ControlMode.MotionProfile, ControlMode.MotionProfileArc);

  public static String getName(WPI_TalonSRX talon) {
    return SendableRegistry.getName(talon);
  }

  public static void requireOK(ErrorCode err) {
    if (ErrorCode.OK.equals(err))
      return;
    throw new RuntimeException(err.name());
  }

  public static void setupControlMode(WPI_TalonSRX talon, ControlMode mode) {
    if (MOTION_PROFILE_MODES.contains(mode))
      talon.selectProfileSlot(0, 0);

    else if (ControlMode.Current.equals(mode))
      talon.selectProfileSlot(1, 0);
    else
      throw new RuntimeException("control mode not set up!! ");
  }

  public static class BasicTalonTelemetry implements Runnable {
    private final NetworkTableEntry outputPercent;
    private final NetworkTableEntry outputVoltage;
    private final NetworkTableEntry outputCurrent;
    private final NetworkTableEntry inputCurrent;
    private final WPI_TalonSRX talon;

    public BasicTalonTelemetry(WPI_TalonSRX talon, ShuffleboardContainer container) {
      this.talon = talon;
      outputPercent = container.add("outputPercent", 0.0).getEntry();
      outputVoltage = container.add("outputVoltage", 0.0).getEntry();
      outputCurrent = container.add("outputCurrent", 0.0).getEntry();
      inputCurrent = container.add("inputCurrent", 0.0).getEntry();
    }

    @Override
    public void run() {
      outputPercent.setDouble(talon.getMotorOutputPercent());
      outputVoltage.setDouble(talon.getMotorOutputVoltage());
      outputCurrent.setDouble(talon.getStatorCurrent());
      inputCurrent.setDouble(talon.getSupplyCurrent());
    }
  }

  public static class EncoderTelemetry implements Runnable {
    final WPI_TalonSRX talon;
    final NetworkTableEntry position;
    final NetworkTableEntry velocity;

    public EncoderTelemetry(WPI_TalonSRX talon, ShuffleboardContainer container) {
      this.talon = talon;
      position = container.add("position", 0.0).getEntry();
      velocity = container.add("velocity", 0.0).getEntry();
    }

    @Override
    public void run() {
      position.setDouble(talon.getSelectedSensorPosition());
      velocity.setDouble(talon.getSelectedSensorVelocity());
    }
  }

  public static class ClosedLoopTelemetry implements Runnable {
    final WPI_TalonSRX talon;
    final NetworkTableEntry target;
    final NetworkTableEntry error;
    final NetworkTableEntry errorDerivative;
    final NetworkTableEntry iAccum;

    public ClosedLoopTelemetry(WPI_TalonSRX talon, ShuffleboardContainer container) {
      this.talon = talon;
      target = container.add("target", 0.0).getEntry();// table.getEntry("target");
      error = container.add("error", 0.0).getEntry();
      errorDerivative = container.add("errorDeriv", 0.0).getEntry();
      iAccum = container.add("iAccum", 0.0).getEntry();// table.getEntry("iAccum");

    }

    @Override
    public void run() {
      if (!CLOSED_LOOP_MODES.contains(talon.getControlMode()))
        return;
      target.setDouble(talon.getClosedLoopTarget());
      error.setDouble(talon.getClosedLoopError());
      errorDerivative.setDouble(talon.getErrorDerivative());
      iAccum.setDouble(talon.getIntegralAccumulator());
    }
  }

  public static class MotionMagicTelemetry implements Runnable {
    final WPI_TalonSRX talon;
    final NetworkTableEntry trajPosition;
    final NetworkTableEntry trajVelocity;
    final NetworkTableEntry trajFF;

   public MotionMagicTelemetry(WPI_TalonSRX talon, ShuffleboardContainer container) {
      this.talon = talon;
      trajPosition = container.add("trajPosition", 0.0).getEntry();// table.getEntry("trajPosition");
      trajVelocity = container.add("trajVelocity", 0.0).getEntry();// table.getEntry("trajVelocity");
      trajFF = container.add("trajFF", 0.0).getEntry();// table.getEntry("trajFF");
    }

    @Override
    public void run() {
      if (!MOTION_PROFILE_MODES.contains(talon.getControlMode()))
        return;

      trajPosition.setDouble(talon.getActiveTrajectoryPosition());
      trajVelocity.setDouble(talon.getActiveTrajectoryVelocity());
      trajFF.setDouble(talon.getActiveTrajectoryArbFeedFwd());
    }
  }
}
