package com.kreckin.herobrine.actions;

import com.kreckin.herobrine.api.Action;
import com.kreckin.herobrine.api.ActionResult;
import com.kreckin.herobrine.api.ActionType;
import com.kreckin.herobrine.util.Util;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class CreateTNTTrap extends Action {

    public CreateTNTTrap() {
        super(ActionType.STANDARD);
    }
    
    @Override
    public ActionResult callAction(Player player, Object[] metadata) {
        Block plate = Util.getNearbyLocation(player, 5).getBlock();
        Block ground = plate.getLocation().subtract(0, 1, 0).getBlock();
        Block tnt = ground.getLocation().subtract(0, 1, 0).getBlock();
        if (Util.isValid(plate) && Util.isSolid(ground) && Util.isSolid(tnt)) {
            plate.setTypeId(new Random().nextBoolean() ? 70 : 72);
            tnt.setType(Material.TNT);
            Location loc = plate.getLocation();
            return (new ActionResult("Done.", "Location: " + loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ()));
        }
        return (new ActionResult("Failed, could not find a proper location!"));
    }
}
