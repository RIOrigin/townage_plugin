package artdev;

import artdev.util.Range;
import artdev.util.Vector;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Map;

public class CommandRange implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        try{
            String subCmd = strings[0];
            if(subCmd.equals("add")){ // range add (rangeName:string) (x1:int) (y1:int) (z1:int) (x2:int) (y2:int) (z1:int)
                if(strings.length<8){
                    commandSender.sendMessage("参数数量不对啊！");
                    return false;
                }
                String rangeName = strings[1];
                Range range = new Range();
                range.name = rangeName;

                try{
                    range.v1 = new Vector(strings[2],strings[3],strings[4]);
                    range.v2 = new Vector(strings[5],strings[6],strings[7]);
                }catch (Exception e){
                    commandSender.sendMessage("不行啊，你看看你输入的是整数吗");
                    return false;
                }

                if(range.GetV()>1000){
                    commandSender.sendMessage("范围太大啦，不能超过1000");
                    return false;
                }
                Dynamic.dynamicRanges.put(rangeName,range);
                return true;
            }else if(subCmd.equals("ls")){
                ArrayList<String> result = new ArrayList<>();
                for(Map.Entry<String,artdev.util.Range> entry: Dynamic.dynamicRanges.entrySet()){
                    artdev.util.Range range = entry.getValue();
                    result.add(String.format("Name:%s;V1:(%s);V2:(%s))",entry.getKey(),range.v1,range.v2));
                }
                commandSender.sendMessage(result.toArray(new String[0]));
                return true;
            }else if(subCmd.equals("rm")){
                if(strings.length<2){
                    commandSender.sendMessage("至少告诉我要删掉什么啊喂。");
                    return false;
                }
                String rangeName = strings[1];
                if(!Dynamic.dynamicRanges.containsKey(rangeName)){
                    commandSender.sendMessage("你要删的这个东西它不存在。");
                    return false;
                }
                Dynamic.dynamicRanges.remove(rangeName);
                return true;
            }

        }catch (Exception e){
            commandSender.sendMessage("你再看看是哪里不对。");
            App.instance.getServer().getConsoleSender().sendMessage(e.getLocalizedMessage());
            return false;
        }
        return false;
    }
}
