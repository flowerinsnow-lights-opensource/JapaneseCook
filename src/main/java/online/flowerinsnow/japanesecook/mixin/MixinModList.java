package online.flowerinsnow.japanesecook.mixin;

import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.network.handshake.FMLHandshakeMessage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import online.flowerinsnow.japanesecook.JapaneseCook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Map;

@Mixin(FMLHandshakeMessage.ModList.class)
@SideOnly(Side.CLIENT)
public class MixinModList {
    @Shadow
    private Map<String,String> modTags;

    @Inject(
            method = "<init>(Ljava/util/List;)V",
            at = @At("RETURN")
    )
    private void init(List<ModContainer> modList, CallbackInfo ci) {
        modTags.remove(JapaneseCook.MODID);
    }
}
