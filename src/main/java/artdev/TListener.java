package artdev;

import artdev.util.Range;
import artdev.util.Vector;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.Map;

public class TListener implements Listener {
    @EventHandler
    public void OnBlockBreak(BlockBreakEvent event){
        Block block = event.getBlock();
        Location loc = block.getLocation();
        Vector v = new Vector(loc);
        boolean canBreak = true;
        for(Map.Entry<String, Range> e:Dynamic.state.dynamicRanges.entrySet()){
            if(e.getValue().IsIn(v)){
                canBreak = false;
            }
        }
        if(!canBreak){
            event.getPlayer().sendMessage("这里受到魔法保护，不能破坏。");
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void OnBlockPlace(BlockPlaceEvent event){
        Block block = event.getBlock();
        Location loc = block.getLocation();
        Vector v = new Vector(loc);
        boolean canPlace = true;
        for(Map.Entry<String, Range> e:Dynamic.state.dynamicRanges.entrySet()){
            if(e.getValue().IsIn(v)){
                canPlace = false;
            }
        }
        if(!canPlace){
            event.getPlayer().sendMessage("这里受到魔法保护，不能放置。");
            event.setCancelled(true);
        }
    }
}
