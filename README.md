# FTC_PID

Copy the "PIDController.java" and "Timer.java" scripts into your project.

Create a Class veriable for the PID Controller, and Timer.

    private PIDController pidController;
    private Timer timer;

In your Init Fucntion Create a new instance of PIDController, and Timner

    pidController = new PIDController(0.8, 0.2, 0.2);
    timer = new Timer();

Make sure to change the kP, kI, and kD veriables to fit your needs. If you do one of the constants, then leave it at 0, and it will not compute it.

Every cycle (frame) make sure to call the Update function for the timer

    timer.Update();

When you need to the power for a motor use the pidController's calculate function

    elevatorMotor.setPower(pidController.Calculate(elevatorMotor.getCurrentPosition(), targetElevatorPosition, timer.deltaTime));
