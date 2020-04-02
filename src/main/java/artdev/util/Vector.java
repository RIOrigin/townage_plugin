package artdev.util;

import org.bukkit.Location;

public class Vector {
    public int x = 0;
    public int y = 0;
    public int z = 0;
    public Vector(){

    }
    public Vector(int x,int y,int z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Vector Sub(Vector v){
        return new Vector(x-v.x,y-v.y,z-v.z);
    }
    public static Vector Sub(Vector u,Vector v){return u.Sub(v);}
    public Vector Add(Vector v){
        return new Vector(x+v.x,y+v.y,z+v.z);
    }
    public static Vector Add(Vector u,Vector v){return u.Add(v);}
    public Vector(Location loc){
        x = loc.getBlockX();
        y = loc.getBlockY();
        z = loc.getBlockZ();
    }
    public Vector(String x,String y,String z){
        this.x = Integer.parseInt(x);
        this.y = Integer.parseInt(y);
        this.z = Integer.parseInt(z);
    }
    public String toString(){
        return String.format("%d %d %d",x,y,z);
    }
    public static Vector AllMin(Vector v1,Vector v2){
        return new Vector(Math.min(v1.x,v2.x),Math.min(v1.y,v2.y),Math.min(v1.z,v2.z));
    }
    public static Vector AllMax(Vector v1,Vector v2){
        return new Vector(Math.max(v1.x,v2.x),Math.max(v1.y,v2.y),Math.max(v1.z,v2.z));
    }
}
