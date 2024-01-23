package net.graeyamber.pipehype;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class GauntletItem extends Item {
    public GauntletItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if(pLevel.isClientSide) {
            if(pEntity instanceof Player) {
                // do every 10 tics
                if (pEntity.tickCount % 10 == 0) {
                    // check if holding gauntlet
                    if(((Player) pEntity).getMainHandItem().is(pStack.getItem())){

                        float f = pEntity.getXRot();
                        float f1 = pEntity.getYRot();
                        Vec3 vec3 = pEntity.getEyePosition();
                        float f2 = Mth.cos(-f1 * (float) (Math.PI / 180.0) - (float) Math.PI);
                        float f3 = Mth.sin(-f1 * (float) (Math.PI / 180.0) - (float) Math.PI);
                        float f4 = -Mth.cos(-f * (float) (Math.PI / 180.0));
                        float f5 = Mth.sin(-f * (float) (Math.PI / 180.0));
                        float f6 = f3 * f4;
                        float f7 = f2 * f4;
                        double d0 = ((Player)pEntity).getBlockReach();
                        if (!((Player) pEntity).isCreative() && d0 != 0) d0 += 0.5D; // The vanilla constant here was 5.0, but the default survival block reach is 4.5. Creative default is already 5.0.
                        Vec3 vec31 = vec3.add((double)f6 * d0, (double)f5 * d0, (double)f7 * d0);
                        BlockHitResult hit = pLevel.clip(
                                new ClipContext(vec3, vec31, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, pEntity));
                        //Vec3 rtLook = hit.getLocation();
                        Vec3 rtLook = hit.getBlockPos().getCenter();
                        if(!pLevel.getBlockState(hit.getBlockPos()).is(Blocks.COBBLESTONE_WALL))
                            return;

                        Vec3 head = pEntity.getEyePosition();
                        Vec3 line = rtLook.subtract(head);

                        Vec3 gui = line.subtract(
                                line.x >= 0 ? 0.5 : -0.5,
                                line.y >= 0 ? 0.5 : -0.5,
                                line.z >= 0 ? 0.5 : -0.5);

                        pLevel.addParticle(
                                ParticleTypes.COMPOSTER,
                                true,
                                rtLook.x- gui.x, rtLook.y- gui.y, rtLook.z- gui.z,
                                0.0, 0.0, 0.0);

                        pLevel.addParticle(
                                ParticleTypes.COMPOSTER,
                                true,
                                rtLook.x- gui.x + 0.1, rtLook.y- gui.y, rtLook.z- gui.z,
                                0.0, 0.0, 0.0);

                        pLevel.addParticle(
                                ParticleTypes.COMPOSTER,
                                true,
                                rtLook.x- gui.x - 0.1, rtLook.y- gui.y, rtLook.z- gui.z,
                                0.0, 0.0, 0.0);

                        pLevel.addParticle(
                                ParticleTypes.COMPOSTER,
                                true,
                                rtLook.x- gui.x, rtLook.y- gui.y + 0.1, rtLook.z- gui.z,
                                0.0, 0.0, 0.0);

                        pLevel.addParticle(
                                ParticleTypes.COMPOSTER,
                                true,
                                rtLook.x- gui.x, rtLook.y- gui.y - 0.1, rtLook.z- gui.z,
                                0.0, 0.0, 0.0);

                        // Draw a line from playerhead to looked block
                        /*
                        Vec3 head = pEntity.getEyePosition();
                        Vec3 result = rtLook.subtract(head);
                        PipeHype.LOGGER.debug("-look- " + rtLook);
                        double len = Math.abs(result.length());
                        PipeHype.LOGGER.debug("-len- " + len);
                        for(double i = 0; i < len; i+=0.02) {
                            pLevel.addParticle(
                                    ParticleTypes.COMPOSTER,
                                    true,
                                    (double) head.x + result.x*i/len, (double) head.y +result.y*i/len, (double) head.z +result.z*i/len,
                                    0.0, 0.0, 0.0);
                        } */
                    }
                }
            }
        }
    }
}
