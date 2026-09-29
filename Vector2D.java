
/**
 * Represents a two-dimensional vector with x and y components.
 * Provides methods for vector manipulation and mathematical operations.
 *
 * @author Mr. Murphy
 * @version May 2025
 */
public class Vector2D {
    private double x; // x-coordinate of the vector
    private double y; // y-coordinate of the vector

    /**
     * Constructs a new vector with the specified x and y coordinates.
     *
     * @param x the x-coordinate of the vector
     * @param y the y-coordinate of the vector
     */
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Constructs a new vector as a copy of an existing vector.
     *
     * @param v the vector to copy
     */
    public Vector2D(Vector2D v) {
        this.x = v.x;
        this.y = v.y;
    }

    /**
     * Adds another vector to this vector, modifying this vector in place.
     *
     * @param v the vector to add
     */
    public void add(Vector2D v) {
        this.x += v.x;
        this.y += v.y;
    }

    /**
     * Subtracts another vector from this vector, modifying this vector in place.
     *
     * @param v the vector to subtract
     */
    public void sub(Vector2D v) {
        this.x -= v.x;
        this.y -= v.y;
    }

    /**
     * Multiplies this vector by a scalar, modifying this vector in place.
     *
     * @param n the scalar to multiply by
     */
    public void mult(double n) {
        this.x *= n;
        this.y *= n;
    }

    /**
     * Divides this vector by a scalar, modifying this vector in place.
     * Prevents division by zero.
     *
     * @param n the scalar to divide by
     * @throws ArithmeticException if n is zero
     */
    public void div(double n) {
        if (n == 0) {
            throw new ArithmeticException("Cannot divide vector by zero");
        }
        this.x /= n;
        this.y /= n;
    }

    /**
     * Calculates the magnitude (length) of this vector.
     *
     * @return the magnitude of the vector
     */
    public double mag() {
        return Math.sqrt(x * x + y * y);
    }

    /**
     * Normalizes this vector to a unit vector (length of 1).
     * If the vector is a zero vector, it remains unchanged.
     */
    public void normalize() {
        double m = mag();
        if (m != 0) {
            div(m);
        }
    }

    /**
     * Limits the magnitude of this vector to a maximum value.
     *
     * @param max the maximum magnitude
     */
    public void limit(double max) {
        if (mag() > max) {
            normalize();
            mult(max);
        }
    }

    /**
     * Calculates the heading (angle) of this vector in radians.
     *
     * @return the angle of the vector in radians
     */
    public double heading() {
        return Math.atan2(y, x);
    }

    /**
     * Calculates the distance between this vector and another vector.
     *
     * @param other the vector to calculate distance to
     * @return the distance between the two vectors
     */
    public double dist(Vector2D other) {
        double dx = this.x - other.x;
        double dy = this.y - other.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Creates a new vector by subtracting another vector from this vector.
     *
     * @param other the vector to subtract
     * @return a new vector representing the difference
     */
    public Vector2D subtract(Vector2D other) {
        return new Vector2D(this.x - other.x, this.y - other.y);
    }

    /**
     * Gets the x-coordinate of the vector.
     *
     * @return the x-coordinate
     */
    public double getX() {
        return x;
    }

    /**
     * Gets the y-coordinate of the vector.
     *
     * @return the y-coordinate
     */
    public double getY() {
        return y;
    }

    /**
     * Sets the x-coordinate of the vector.
     *
     * @param x the new x-coordinate
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Sets the y-coordinate of the vector.
     *
     * @param y the new y-coordinate
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Returns a string representation of the vector.
     *
     * @return a string in the format "[x, y]"
     */
    public String toString() {
        return "[" + x + ", " + y + "]";
    }
}