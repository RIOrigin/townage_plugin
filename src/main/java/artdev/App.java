package artdev;
import org.bukkit.plugin.java.JavaPlugin;
/**
 * Hello world!
 *
 */
public class App extends JavaPlugin
{
    @Override
    public void onEnable(){
        getLogger().info("Townage enable.");
    }

    @Override
    public void onDisable(){
        getLogger().info("Townage disable.");
    }
}
