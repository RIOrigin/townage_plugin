package artdev;

import org.bukkit.command.CommandSender;

public class Permission {
    public static boolean CheckOp(CommandSender sender){
        boolean result =  sender.isOp();
        if(!result){
            sender.sendMessage("目前是灰度测试，部分用户无法使用哦。");
        }
        return result;
    }
}
