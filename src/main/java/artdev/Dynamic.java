package artdev;

import artdev.util.FileUtil;
import artdev.util.Range;
import artdev.util.Vector;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.bukkit.scheduler.BukkitRunnable;
import sun.rmi.runtime.Log;

import java.io.File;
import java.io.IOException;
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
        ReadData();
        dynamicTask = new DynamicTask();
        dynamicTask.runTaskTimer(App.instance,20L,20L);
        persistTask = new PersistTask();
        persistTask.runTaskTimer(App.instance, 20L, 200L);
    }
    public static void ReadData(){
        File file = new File("./dynamic.data");
        try{
            String str = FileUtil.readFile(file);
            Gson gson = new Gson();
            PersistentState state = new PersistentState();
            state = gson.fromJson(str,state.getClass());
            if(state!=null){
                Dynamic.state = state;
            }
        }catch(IOException e){
            Logger.Err(e);
        }finally {

        }

    }
    public static DynamicTask dynamicTask;
    public static PersistTask persistTask;
    public static class PersistentState{
        public HashMap<String, Range> dynamicRanges = new HashMap<>();
        public HashMap<String, Stage> stages = new HashMap<>();
    }
    public static PersistentState state = new PersistentState();
}

class PersistTask extends BukkitRunnable{
    @Override
    public void run(){
        Logger.Log("Persist task run.");
        try{
            Gson gson = new Gson();
            String json = gson.toJson(Dynamic.state);
            File file =  new File("./dynamic.data");
            FileUtil.saveFile(file,json);
        }catch (IOException e){
            Logger.Err(e);
        }
    }
}

class DynamicTask extends BukkitRunnable{

    @Override
    public void run() {
        Logger.Log("Dynamic Task Tick.");
        for(Map.Entry<String, Dynamic.Stage> e:Dynamic.state.stages.entrySet()){
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
