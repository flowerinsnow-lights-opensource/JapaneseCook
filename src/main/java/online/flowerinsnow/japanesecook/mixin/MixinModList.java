package online.flowerinsnow.japanesecook.mixin;

import net.minecraftforge.fml.common.network.handshake.FMLHandshakeMessage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import online.flowerinsnow.japanesecook.JapaneseCook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Map;

@Mixin(FMLHandshakeMessage.ModList.class)
@SideOnly(Side.CLIENT)
public class MixinModList {
    @Redirect(
            method = "<init>(Ljava/util/List;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/Map;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;",
                    ordinal = 0
            )
    )
    private static <K, V> V init(Map<K, V> instance, K k, V v) {
        if (!k.equals(JapaneseCook.MODID)) {
            return instance.put(k, v);
        }
        return null;
    }
}
