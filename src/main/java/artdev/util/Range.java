package artdev.util;

import static java.lang.Math.abs;

public class Range {
    public String name;
    private Vector v_min;
    private Vector v_max;
    public Range(){

    }
    public Range(Vector v1, Vector v2){
        SetRange(v1,v2);
    }
    public void SetRange(Vector v1, Vector v2){
        v_min = new Vector(Math.min(v1.x,v2.x),Math.min(v1.y,v2.y),Math.min(v1.y,v2.y));
        v_max = new Vector(Math.max(v1.x,v2.x),Math.max(v1.y,v2.y),Math.max(v1.y,v2.y));
    }
    public double GetV(){
        Vector v = v_max.Sub(v_min);
        return v.x*v.y*v.z;
    }
    public String toString(){
        return String.format("%d %d %d %d %d %d",v_min.x,v_min.y,v_min.z,v_max.x,v_max.y,v_max.z);
    }
    public boolean IsIn(Vector v){
        Vector u = v_max.Sub(v_min);
        return v.x < u.x && v.y < u.y && v.z < u.z;
    }
}
