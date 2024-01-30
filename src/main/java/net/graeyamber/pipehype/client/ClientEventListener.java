package net.graeyamber.pipehype.client;

import net.graeyamber.pipehype.client.renderer.BlockHighLightRenderer;
import net.graeyamber.pipehype.PipeHype;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RenderHighlightEvent;

@Mod.EventBusSubscriber(modid = PipeHype.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientEventListener {
    @SubscribeEvent
    public static void onBlockHighLightEvent(RenderHighlightEvent.Block event) {
        BlockHighLightRenderer.renderBlockHighLight(event.getPoseStack(), event.getCamera(), event.getTarget(), event.getMultiBufferSource(), event.getPartialTick());
    }
}
