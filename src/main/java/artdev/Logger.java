package artdev;

import artdev.util.ExceptionUtil;


public class Logger {
    public static void Log(String s){
        //App.instance.getServer().getConsoleSender().sendMessage(s);
    }
    public static void Debug(String s){
        //App.instance.getServer().getConsoleSender().sendMessage(s);
    }
    public static void Err(Exception e){
        App.instance.getServer().getConsoleSender().sendMessage(ExceptionUtil.getStackTrace(e));
    }
}