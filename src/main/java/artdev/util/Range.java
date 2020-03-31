package artdev.util;

import static java.lang.Math.abs;

public class Range {
    public String name;
    public Vector v1;
    public Vector v2;
    public double GetV(){
        return (abs(v1.x-v2.x)*abs(v1.y-v2.y)*abs(v1.z-v2.z));
    }
    public String toString(){
        return String.format("%d %d %d %d %d %d",v1.x,v1.y,v1.z,v2.x,v2.y,v2.z);
    }
}
