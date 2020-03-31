package artdev;

import artdev.util.Range;
import artdev.util.Vector;
import org.bukkit.scheduler.BukkitRunnable;
import sun.rmi.runtime.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Dynamic {
    public static class Stage {
        public String name;
        public Vector pos;
        public int internal = 1;
        public ArrayList<Range> ranges = new ArrayList<>();
        public int _counter = 0;
        public int _pointer = 0;
        public boolean Check(){
            return _counter == 0;
        }
        public Range Get(){
            Range result = ranges.get(_pointer);
            _pointer = (_pointer + 1) % ranges.size();
            return result;
        }
        public void Tick(){
            _counter = (_counter + 1) % internal;
        }
    }

    public static void Enable(){
        dynamicTask = new DynamicTask();
        dynamicTask.runTaskTimer(App.instance,20L,20L);
    }
    public static DynamicTask dynamicTask;
    public static HashMap<String, Range> dynamicRanges = new HashMap<>();
    public static HashMap<String, Stage> stages = new HashMap<>();
}

class DynamicTask extends BukkitRunnable{

    @Override
    public void run() {
        Logger.Log("Dynamic Task Tick.");
        for(Map.Entry<String, Dynamic.Stage> e:Dynamic.stages.entrySet()){
            Dynamic.Stage stage = e.getValue();
            Logger.Log(String.format("%s stage tick.",stage.name));
            if(stage.Check()){
                Range range = stage.Get();
                Logger.Log(String.format("clone %s %s masked",range,stage.pos));
                App.instance.getServer().dispatchCommand(App.instance.getServer().getConsoleSender(),String.format("clone %s %s replace",range,stage.pos));
            }
            stage.Tick();
        }
        //App.instance.getServer().dispatchCommand(String.format("clone %d %d %d "))
    }
}
