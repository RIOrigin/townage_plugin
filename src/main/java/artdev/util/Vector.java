package artdev.util;

import org.bukkit.Location;

public class Vector {
    public int x = 0;
    public int y = 0;
    public int z = 0;
    public Vector(int x,int y,int z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Vector Sub(Vector v){
        return new Vector(x-v.x,y-v.y,z-v.z);
    }
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
}
