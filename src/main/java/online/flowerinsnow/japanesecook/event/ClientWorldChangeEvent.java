package online.flowerinsnow.japanesecook.event;

import net.minecraft.client.multiplayer.WorldClient;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientWorldChangeEvent extends Event {
    public final WorldClient oldWorld;
    public final WorldClient newWorld;

    public ClientWorldChangeEvent(WorldClient oldWorld, WorldClient newWorld) {
        this.oldWorld = oldWorld;
        this.newWorld = newWorld;
    }
}
