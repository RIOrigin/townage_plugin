package artdev;

import artdev.util.Range;
import artdev.util.Vector;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.Map;

public class TListener implements Listener {
    @EventHandler
    public void OnBlockBreak(BlockBreakEvent event){
        //Logger.Debug("Block break.");
        if(event.getPlayer().isOp())return;
        if(Dynamic.CheckProtectBlock(event.getBlock())){
            event.getPlayer().sendMessage("这里受到魔法保护，不能破坏。");
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void OnBlockPlace(BlockPlaceEvent event){
        //Logger.Debug("Block place.");
        if(event.getPlayer().isOp())return;
        if(Dynamic.CheckProtectBlock(event.getBlock())){
            event.getPlayer().sendMessage("这里受到魔法保护，不能放置。");
            event.setCancelled(true);
        }
    }
}
