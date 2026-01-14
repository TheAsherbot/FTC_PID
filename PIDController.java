package org.firstinspires.ftc.teamcode.core;


public class PIDController {

    private double kP;
    private double maxError;

    private double kI;
    private double errorSum = 0;
    private double kIActiveZone = 0;

    private double kD;
    private double lastError;

    private boolean canLoop = false;
    private double loopMin;
    private double loopMax;


    public PIDController(double kP, double kI, double kD)
    {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;

        maxError = -1;
        errorSum = 0;
        kIActiveZone = -1;
    }
    public PIDController(double kP, double maxError, double kI, double kD)
    {
        this.kP = kP;
        this.maxError = maxError;
        this.kI = kI;
        this.kD = kD;

        errorSum = 0;
        kIActiveZone = -1;
    }
    public PIDController(double kP, double maxError, double kI, double kIActiveZone, double kD)
    {
        this.kP = kP;
        this.maxError = maxError;
        this.kI = kI;
        this.kIActiveZone = kIActiveZone;
        this.kD = kD;

        errorSum = 0;
    }

    public double Calculate(double currentPosition, double targetPosition, double deltaTime)
    {
        double difference = loopMax - loopMin;
        double error = 0;
        double errorRate = 0;

        if (canLoop)
        {
            while (targetPosition > loopMax)
            {
                targetPosition -= difference;
            }
            while (targetPosition < loopMin)
            {
                targetPosition += difference;
            }
            
            error = targetPosition - currentPosition;

            while (error > loopMax)
            {
                error -= difference;
            }
            while (error < loopMin)
            {
                error += difference;
            }
         
            double directDifference = Math.abs(error);
            double loopDifference = difference - Math.abs(error);

            if (directDifference < loopDifference)
            {
                error = error;
            }
            else
            {
                // Loop power is faster
                error = -difference + error;
            }
        }
        else
        {
            error = targetPosition - currentPosition;
        }

        // Proportional
        if (kP != 0)
        {
            if (maxError >= 0 && Math.abs(error) > maxError)
            {
                error = maxError * (error / Math.abs(error));
            }
        }

        // Interval
        if (kI != 0)
        {
            if (kIActiveZone >= 0)
            {
                if (Math.abs(error) < kIActiveZone)
                {
                    errorSum += error * deltaTime;
                    // If passes the target then set error sum to 0
                    if ((error < 0 && lastError > 0) || (error > 0 && lastError < 0))
                    {
                        errorSum = 0;
                    }
                }
                else
                {
                    errorSum = 0;
                }
            }
            else
            {
                errorSum += error * deltaTime;
            }
        }

        // Derivative
        if (kD != 0)
        {
            if (Math.abs(error) < maxError)
            {
                if (error != lastError)
                {
                    errorRate = (error - lastError) / deltaTime;
                }
            }
        }

        lastError = errorRate;
        return (kP * error) + (kI * errorSum) + (kD * errorRate);
    }

    public void Reset()
    {
        errorSum = 0;
    }

    public void SetLoop(boolean canLoop, double loopMin, double loopMax)
    {
        this.canLoop = canLoop;
        this.loopMin = loopMin;
        this.loopMax = loopMax;
    }

}
