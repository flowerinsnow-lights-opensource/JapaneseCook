package online.flowerinsnow.japanesecook.util;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public final class MessageUtils {
    private MessageUtils() {
    }

    public static void showText(String text) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.thePlayer != null) {
            ClientChatReceivedEvent event = new ClientChatReceivedEvent((byte) 0, new ChatComponentText(text));
            if (!MinecraftForge.EVENT_BUS.post(event)) {
                mc.thePlayer.addChatComponentMessage(event.message);
            }
        }
    }

    public static String removeColourCode(String text) {
        return text.replaceAll("§.", "");
    }
}
