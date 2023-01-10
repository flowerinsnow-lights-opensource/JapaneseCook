package online.flowerinsnow.japanesecook.listener;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import online.flowerinsnow.japanesecook.event.ClientTickEndEvent;
import online.flowerinsnow.japanesecook.event.ClientWorldChangeEvent;

@SideOnly(Side.CLIENT)
public class EventTrigger {
    private WorldClient world;

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            MinecraftForge.EVENT_BUS.post(new ClientTickEndEvent());
        }
    }

    @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        if (world != mc.theWorld) {
            MinecraftForge.EVENT_BUS.post(new ClientWorldChangeEvent(world, mc.theWorld));
            world = mc.theWorld;
        }
    }
}
