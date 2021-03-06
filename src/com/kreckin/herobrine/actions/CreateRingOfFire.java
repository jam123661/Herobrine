package com.kreckin.herobrine.actions;

import com.kreckin.herobrine.api.Action;
import com.kreckin.herobrine.api.ActionResult;
import com.kreckin.herobrine.api.ActionType;
import com.kreckin.herobrine.util.Util;
import java.util.ArrayList;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class CreateRingOfFire extends Action {
    
    public CreateRingOfFire() {
        super(ActionType.STANDARD);
    }

    @Override
    public ActionResult callAction(Player player, Object[] metadata) {
        ArrayList<Block> blocks = new ArrayList<Block>();
        blocks.add(player.getLocation().add(2, 0, 0).getBlock());
        blocks.add(player.getLocation().add(0, 0, 2).getBlock());
        blocks.add(player.getLocation().add(0, 0, -2).getBlock());
        blocks.add(player.getLocation().add(-2, 0, 0).getBlock());
        blocks.add(player.getLocation().add(1, 0, 1).getBlock());
        blocks.add(player.getLocation().add(1, 0, -1).getBlock());
        blocks.add(player.getLocation().add(-1, 0, 1).getBlock());
        blocks.add(player.getLocation().add(-1, 0, -1).getBlock());
        boolean bad = false;
        for (Block block : blocks) {
            if (!Util.isValid(block)) {
                bad = true;
            }
        }
        if (!bad) {
            for (Block block : blocks) {
                block.setType(Material.FIRE);
            }
            Location loc = player.getLocation();
            return (new ActionResult("Done.", "Location: " + loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ()));
        }
        return (new ActionResult("Failed, could not find a proper location!"));
    }
}
