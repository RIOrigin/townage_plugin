package artdev;

import artdev.util.Range;
import artdev.util.Vector;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class TListener implements Listener {
    @EventHandler
    public void OnBlockBreak(BlockBreakEvent event){
        //Logger.Debug("Block break.");
        //if(event.getPlayer().isOp())return;
        if(Dynamic.CheckProtectBlock(event.getBlock())){
            event.getPlayer().sendMessage("这里受到魔法保护，不能破坏。");
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void OnBlockPlace(BlockPlaceEvent event){
        //Logger.Debug("Block place.");
        //if(event.getPlayer().isOp())return;
        if(Dynamic.CheckProtectBlock(event.getBlock())){
            event.getPlayer().sendMessage("这里受到魔法保护，不能放置。");
            event.setCancelled(true);
        }
    }
    public static class BuildRangeAction{
        public Player player;
        public enum STATE {
            NONE,
            POINT,
            RANGE
        }
        public STATE state = STATE.NONE;
        public Vector firstLoc;
        public Range range;
        public BuildRangeAction(Player player){
            this.player = player;
        }
        public void Add(Location loc){
            if(state == STATE.NONE || state == STATE.RANGE){
                firstLoc = new Vector(loc);
                player.sendMessage(String.format("已选择一个点：(%s)",firstLoc));
                state = STATE.POINT;
            }
            else if(state == STATE.POINT){
                range = new Range(firstLoc,new Vector(loc));
                player.sendMessage(String.format("已选择一个范围：(%s)",range));
                state = STATE.RANGE;
            }
        }
    }
    public static HashMap<Player,BuildRangeAction> playersAction = new HashMap<>();
    @EventHandler
    public void OnPlayerInteract(PlayerInteractEvent event){
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getHand() == EquipmentSlot.HAND){
            ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
            if(item.getType() == Material.DIAMOND_AXE){
                Player player = event.getPlayer();
                if(!playersAction.containsKey(player))playersAction.put(player,new BuildRangeAction(player));
                playersAction.get(player).Add(event.getClickedBlock().getLocation());
            }
        }
    }
}
