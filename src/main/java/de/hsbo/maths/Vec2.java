package de.hsbo.maths;

import lombok.Getter;
import lombok.Setter;

public class Vec2 {
    @Getter @Setter
    float x, y;
    public Vec2(float x, float y) {
        this.x = x; this.y = y;
    }
    public boolean equals(Vec2 v2) {
        return v2.getX() == this.x && v2.getY() == this.y;
    }
    public void add(Vec2 v2) {
        this.x += v2.getX();
        this.y += v2.getY();
    }
    public void subtract(Vec2 v2) {
        this.x -= v2.getX();
        this.y -= v2.getY();
    }
    public void invertSigns() {
        this.x *= -1;
        this.y *= -1;
    }
    public void scale(float scalar) {
        this.x += scalar;
        this.y += scalar;
    }
    public float getAbsoluteLength() {
        return (float) Math.sqrt(x*x + y*y);
    }
}