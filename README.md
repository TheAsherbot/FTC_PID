# FTC_PID

Make a new package named "core" under "org.firstinspires.ftc.teamcode."

Copy the "PIDController.java" and "Timer.java" scripts into your core package.

Create a Class veriable for the PIDController, and Timer.

    private PIDController pidController;
    private Timer timer;

In your Init Fucntion Create a new instance of PIDController, and Timer

    pidController = new PIDController(0.8, 0.2, 0.2);
    timer = new Timer();

Make sure to change the kP, kI, and kD veriables to fit your needs. If you do not need one of the constants, then leave it at 0, and it will not compute it.

Every cycle (frame) make sure to call the Update function for the timer

    timer.Update();

When you need to power a motor use the pidController's Calculate function

    elevatorMotor.setPower(pidController.Calculate(elevatorMotor.getCurrentPosition(), targetElevatorPosition, timer.deltaTime));
