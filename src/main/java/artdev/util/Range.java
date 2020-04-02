package artdev.util;

import static java.lang.Math.abs;

public class Range {
    public String name;
    public Vector v_min;
    public Vector v_max;
    public Range(){

    }
    public Range(Vector v1, Vector v2){
        SetRange(v1,v2);
    }
    public void SetRange(Vector v1, Vector v2){
        v_min = Vector.AllMin(v1,v2);
        v_max = Vector.AllMax(v1,v2);
    }
    public double GetV(){
        Vector v = v_max.Sub(v_min);
        return v.x*v.y*v.z;
    }
    public String toString(){
        return String.format("%d %d %d %d %d %d",v_min.x,v_min.y,v_min.z,v_max.x,v_max.y,v_max.z);
    }
    public boolean IsIn(Vector v){
        //Vector u = v_max.Sub(v_min);
        //return v.x <= u.x && v.y <= u.y && v.z <= u.z;
        return v.x >= v_min.x && v.x <= v_max.x
                && v.y >= v_min.y && v.y <= v_max.y
                && v.z >= v_min.z && v.z <= v_max.z;
    }
}
