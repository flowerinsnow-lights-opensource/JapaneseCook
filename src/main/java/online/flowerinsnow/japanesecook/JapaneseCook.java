package online.flowerinsnow.japanesecook;

import net.minecraft.client.Minecraft;
import net.minecraft.crash.CrashReport;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import online.flowerinsnow.japanesecook.command.JapaneseCookCommand;
import online.flowerinsnow.japanesecook.config.Config;
import online.flowerinsnow.japanesecook.listener.EventTrigger;
import online.flowerinsnow.japanesecook.listener.MainListener;
import online.flowerinsnow.japanesecook.manager.KeyManager;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

@Mod(
        modid = JapaneseCook.MODID,
        version = JapaneseCook.VERSION,
        clientSideOnly = true
)
@SideOnly(Side.CLIENT)
public class JapaneseCook {
    public static final String MODID = "japanesecook";
    public static final String VERSION = "@VERSION@";

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Config.init(event.getSuggestedConfigurationFile());
        Config.load();
        Config.save();
        try {
            Pattern.compile(Config.Message.cooldown.getString());
        } catch (PatternSyntaxException e) {
            Minecraft.getMinecraft().crashed(CrashReport.makeCrashReport(e, "配置文件中的message.cooldown不是一个合法的正则表达式"));
        }
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
        EventBus eb = MinecraftForge.EVENT_BUS;
        eb.register(new EventTrigger());
        eb.register(new MainListener());

        ClientCommandHandler.instance.registerCommand(new JapaneseCookCommand());

        ClientRegistry.registerKeyBinding(KeyManager.TOGGLE);
    }
}
