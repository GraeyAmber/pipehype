package net.graeyamber.pipehype.client.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.graeyamber.pipehype.PipeHype;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import org.joml.Vector3f;

public class BlockHighLightRenderer {

    public static void renderBlockHighLight(PoseStack poseStack, Camera camera, BlockHitResult target, MultiBufferSource multiBufferSource, float partialTick) {
        var mc = Minecraft.getInstance();
        var level = mc.level;
        var player = mc.player;

        // if(player.tickCount % 10 != 0) { return; }

        // PipeHype.LOGGER.debug("---render highlight---");

        if (level != null && player != null) {
            // PipeHype.LOGGER.debug("---player in level---");
            ItemStack held = player.getMainHandItem();
            BlockPos blockPos = target.getBlockPos();
            if(held.is(Items.STICK)) {

                // PipeHype.LOGGER.debug("---holding stick---");

                var box = new AABB(blockPos);

                var topRight = new Vector3f((float) box.maxX - 0.2f, (float) box.maxY, (float) box.maxZ);
                var bottomRight = new Vector3f((float) box.maxX - 0.2f, (float) box.minY, (float) box.maxZ);

                if(player.tickCount % 10 == 0) {
                    PipeHype.LOGGER.debug("---topRight---" + topRight);
                    PipeHype.LOGGER.debug("---bottomRight---" + bottomRight);
                }

                level.addParticle(
                        ParticleTypes.COMPOSTER,
                        true,
                        topRight.x, topRight.y, topRight.z,
                        0.0, 0.0, 0.0);

                level.addParticle(
                        ParticleTypes.COMPOSTER,
                        true,
                        bottomRight.x, bottomRight.y, bottomRight.z,
                        0.0, 0.0, 0.0);

                var normal = new Vector3f(topRight).sub(bottomRight);
                var mat = poseStack.last().pose();

                var buffer = multiBufferSource.getBuffer(RenderType.lines());

                buffer.vertex(mat, topRight.x, topRight.y, topRight.z).color(1f, 0f, 0f, 1f).normal(normal.x, normal.y, normal.z).endVertex();
                buffer.vertex(mat, bottomRight.x, bottomRight.y, bottomRight.z).color(1f, 0f, 0f, 1f).normal(normal.x, normal.y, normal.z).endVertex();

                poseStack.pushPose();
                RenderSystem.disableDepthTest();
                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();




            }
        }
    }
}
