package artdev;
import artdev.util.ExceptionUtil;
import artdev.util.Range;
import artdev.util.Vector;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class App extends JavaPlugin
{
    public static JavaPlugin instance;
    @Override
    public void onEnable(){
        instance = this;
        this.getCommand("Trange").setExecutor(new CommandRange());
        this.getCommand("Tstage").setExecutor(new CommandStage());
        getServer().getPluginManager().registerEvents(new TListener(), this);
        Dynamic.Enable();
        getLogger().info("Townage enable.");
    }

    @Override
    public void onDisable(){
        getLogger().info("Townage disable.");
    }
}

class CommandStage implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!Permission.CheckOp(commandSender))return true;
        try{
            String subCmd = strings[0];
            if(subCmd.equals("build")){ // stage build (stageName:string) (internal:int) (rangeName,...:Range)
                if(strings.length < 4){
                    commandSender.sendMessage("参数数量不对，看看是不是少了什么。");
                    return false;
                }
                if(!(commandSender instanceof Player)){
                    commandSender.sendMessage("此指令只能由玩家发出。");
                    return false;
                }
                Player player = (Player)commandSender;
                TListener.BuildRangeAction action = TListener.playersAction.get(player);
                if(action == null || action.state!= TListener.BuildRangeAction.STATE.POINT){
                    commandSender.sendMessage("快用你的钻石斧头选择一个点，也可能是你一不小心选了个范围。");
                    return false;
                }
                Dynamic.Stage stage = new Dynamic.Stage();
                stage.name = strings[1];
                stage.internal = Integer.parseInt(strings[2]);
                stage.pos = action.firstLoc;
                if(stage.internal <1){
                    commandSender.sendMessage("间隔时间不能设置太短。");
                    return false;
                }
                for(int i = 3; i < strings.length;i++){
                    String rangeName = strings[i];
                    if(!Dynamic.state.dynamicRanges.containsKey(rangeName)){
                        commandSender.sendMessage(String.format("这个帧：%s 不存在啊。",rangeName));
                        return false;
                    }
                    stage.rangeNames.add(rangeName);
                }
                stage.SetProtect();
                if(Dynamic.state.stages.containsKey(stage.name)){
                    commandSender.sendMessage(String.format("%s 已存在。",stage.name));
                    return false;
                }

                Dynamic.state.stages.put(stage.name,stage);
                commandSender.sendMessage(String.format("成功创建STAGE：%s",stage.name));
                return true;
            }
            else if(subCmd.equals("add")){ // stage add (stageName:string) (internal:int) (x:int) (y:int) (z:int) (rangeName,...:Range)

                Dynamic.Stage stage = new Dynamic.Stage();
                stage.name = strings[1];
                stage.internal = Integer.parseInt(strings[2]);
                stage.pos = new Vector(strings[3],strings[4],strings[5]);
                if(stage.internal <1){
                    commandSender.sendMessage("间隔时间不能设置太短。");
                    return false;
                }
                for(int i = 6; i < strings.length;i++){
                    String rangeName = strings[i];
                    if(!Dynamic.state.dynamicRanges.containsKey(rangeName)){
                        commandSender.sendMessage(String.format("这个帧：%s 不存在啊。",rangeName));
                        return false;
                    }
                    stage.rangeNames.add(rangeName);
                }
                stage.SetProtect();
                if(Dynamic.state.stages.containsKey(stage.name)){
                    commandSender.sendMessage(String.format("%s 已存在。",stage.name));
                    return false;
                }

                Dynamic.state.stages.put(stage.name,stage);
                commandSender.sendMessage(String.format("成功创建STAGE：%s",stage.name));
                return true;
            }else if(subCmd.equals("ls")){
                ArrayList<String> result = new ArrayList<>();
                for(Map.Entry<String, Dynamic.Stage> entry: Dynamic.state.stages.entrySet()){
                    Dynamic.Stage stage = entry.getValue();
                    String[] ranges = new String[stage.rangeNames.size()];
                    stage.rangeNames.toArray(ranges);
                    result.add(String.format("Name:%s;Internal:%d;Pos:(%s),Stages:%s",stage.name,stage.internal, stage.pos,StringUtils.join(ranges,",")));
                }
                commandSender.sendMessage(result.toArray(new String[0]));
                return true;
            }else if(subCmd.equals("rm")){
                if(strings.length<2){
                    commandSender.sendMessage("至少告诉我要删掉什么啊喂。");
                    return false;
                }
                String stageName = strings[1];
                if(!Dynamic.state.stages.containsKey(stageName)){
                    commandSender.sendMessage("你要删的这个东西它不存在。");
                    return false;
                }
                Dynamic.state.stages.remove(stageName);
                commandSender.sendMessage(String.format("成功删除STAGE：%s",stageName));
                return true;
            }
        }catch (Exception e){
            commandSender.sendMessage("仔细想想是不是哪里搞错了啊。");
            Logger.Err(e);
            return false;
        }
        return false;
    }
}

class CommandRange implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!Permission.CheckOp(commandSender))return true;
        try{
            String subCmd = strings[0];
            if(subCmd.equals("build")){ // trange build (rangeName:string)
                if(strings.length<2){
                    commandSender.sendMessage("参数数量不对啊！");
                    return false;
                }
                String rangeName = strings[1];
                if(!(commandSender instanceof Player)){
                    commandSender.sendMessage("此指令只能由玩家发出。");
                    return false;
                }
                Player player = (Player)commandSender;
                TListener.BuildRangeAction action = TListener.playersAction.get(player);
                if(action == null || action.state!= TListener.BuildRangeAction.STATE.RANGE){
                    commandSender.sendMessage("快用你的钻石斧头选择一个范围。");
                    return false;
                }
                Range range = action.range;

                if(range.GetV()>1000 && !commandSender.isOp()){
                    commandSender.sendMessage("范围太大啦，不能超过1000。");
                    return false;
                }
                if(Dynamic.state.dynamicRanges.containsKey(rangeName)){
                    commandSender.sendMessage(String.format("%s 已存在。",rangeName));
                    return false;
                }
                Dynamic.state.dynamicRanges.put(rangeName,range);
                commandSender.sendMessage(String.format("成功创建RANGE：%s",rangeName));
                return true;
            }
            if(subCmd.equals("add")){ // range add (rangeName:string) (x1:int) (y1:int) (z1:int) (x2:int) (y2:int) (z1:int)
                if(strings.length<8){
                    commandSender.sendMessage("参数数量不对啊！");
                    return false;
                }
                String rangeName = strings[1];
                Range range = new Range();
                range.name = rangeName;

                try{
                    Vector v1 = new Vector(strings[2],strings[3],strings[4]);
                    Vector v2 = new Vector(strings[5],strings[6],strings[7]);
                    range.SetRange(v1,v2);
                }catch (Exception e){
                    commandSender.sendMessage("不行啊，你看看你输入的是整数吗");
                    return false;
                }

                if(range.GetV()>1000){
                    commandSender.sendMessage("范围太大啦，不能超过1000");
                    return false;
                }
                if(Dynamic.state.dynamicRanges.containsKey(rangeName)){
                    commandSender.sendMessage(String.format("%s 已存在。",rangeName));
                    return false;
                }
                Dynamic.state.dynamicRanges.put(rangeName,range);
                commandSender.sendMessage(String.format("成功创建RANGE：%s",rangeName));
                return true;
            }else if(subCmd.equals("ls")){
                ArrayList<String> result = new ArrayList<>();
                for(Map.Entry<String,artdev.util.Range> entry: Dynamic.state.dynamicRanges.entrySet()){
                    artdev.util.Range range = entry.getValue();
                    result.add(String.format("Name:%s;(%s)",entry.getKey(),range));
                }
                commandSender.sendMessage(result.toArray(new String[0]));
                return true;
            }else if(subCmd.equals("rm")){
                if(strings.length<2){
                    commandSender.sendMessage("至少告诉我要删掉什么啊喂。");
                    return false;
                }
                String rangeName = strings[1];
                if(!Dynamic.state.dynamicRanges.containsKey(rangeName)){
                    commandSender.sendMessage("你要删的这个东西它不存在。");
                    return false;
                }
                Dynamic.state.dynamicRanges.remove(rangeName);
                commandSender.sendMessage(String.format("成功删除RANGE：%s",rangeName));
                return true;
            }

        }catch (Exception e){
            commandSender.sendMessage("你再看看是哪里不对。");
            Logger.Err(e);
            return false;
        }
        return false;
    }
}

