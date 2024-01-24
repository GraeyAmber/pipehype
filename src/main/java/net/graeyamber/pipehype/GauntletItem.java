package net.graeyamber.pipehype;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

public class GauntletItem extends Item {


    protected Vec3 points[] = {
            new Vec3(0.5, 0.5, 0),
            new Vec3(0.7, 0.5, 0),
            new Vec3(0.3, 0.5, 0),
            new Vec3(0.5, 0.7, 0),
            new Vec3(0.5, 0.3, 0),
            new Vec3(0.7, 0.3, 0) };

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
                    ItemStack gauntlet = ((Player) pEntity).getMainHandItem();
                    if(gauntlet.is(pStack.getItem())){

                        BlockHitResult hit = getHitResult(pEntity, pLevel);

                        BlockPos blockPos = hit.getBlockPos();
                        BlockState blockHit = pLevel.getBlockState(blockPos);


                        CompoundTag tagGauntlet = gauntlet.getOrCreateTag();
                        tagGauntlet.putString("block_hit", blockHit.getBlock().toString());

                        if(blockHit.is(Blocks.AIR) || blockHit.is(Blocks.CAVE_AIR) || blockHit.is(Blocks.VOID_AIR)){
                            return;
                        }

                        // go for bounds() instead
                        AABB blockShape = blockHit.getShape(pLevel, blockPos).bounds();
                        tagGauntlet.putString("block_shape", blockShape.toString());


                        renderCross(pLevel, blockPos);

                        Vec3 locationHit = hit.getLocation();

                        // TO GET THE FACE YOU'RE LOOKING AT, REFER TO PLAYER FACING DIRECTION

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

    public void renderCross(Level pLevel, BlockPos blockPos) {
        pLevel.addParticle(
                ParticleTypes.COMPOSTER,
                true,
                blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 1.1,
                0.0, 0.0, 0.0);

        pLevel.addParticle(
                ParticleTypes.COMPOSTER,
                true,
                blockPos.getX() + 0.7, blockPos.getY() + 0.5, blockPos.getZ() + 1.1,
                0.0, 0.0, 0.0);

        pLevel.addParticle(
                ParticleTypes.COMPOSTER,
                true,
                blockPos.getX() + 0.3, blockPos.getY() + 0.5, blockPos.getZ() + 1.1,
                0.0, 0.0, 0.0);

        pLevel.addParticle(
                ParticleTypes.COMPOSTER,
                true,
                blockPos.getX() + 0.5, blockPos.getY() + 0.7, blockPos.getZ() + 1.1,
                0.0, 0.0, 0.0);

        pLevel.addParticle(
                ParticleTypes.COMPOSTER,
                true,
                blockPos.getX() + 0.5, blockPos.getY() + 0.3, blockPos.getZ() + 1.1,
                0.0, 0.0, 0.0);

        pLevel.addParticle(
                ParticleTypes.COMPOSTER,
                true,
                blockPos.getX() + 0.7, blockPos.getY() + 0.3, blockPos.getZ() + 1.1,
                0.0, 0.0, 0.0);
    }

    public BlockHitResult getHitResult(Entity pEntity, Level pLevel) {
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
        return pLevel.clip(
                new ClipContext(vec3, vec31, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, pEntity));}

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack gauntlet = pPlayer.getItemInHand(InteractionHand.MAIN_HAND);
        CompoundTag tagGauntlet = gauntlet.getOrCreateTag();
        PipeHype.LOGGER.debug(":block: "+tagGauntlet.getString("block_hit"));
        PipeHype.LOGGER.debug(":shape: "+tagGauntlet.getString("block_shape"));

        BlockHitResult hit = getHitResult(pPlayer, pLevel);
        Vec3 locationHit = hit.getLocation();
        Vec3 points2[] = new Vec3[6];
        for(int i = 0; i < 6; i++) {
            points2[i] = points[i].add(hit.getBlockPos().getX(), hit.getBlockPos().getY(), hit.getBlockPos().getZ());
        }

        PipeHype.LOGGER.debug(":hit: "+locationHit);

        double lengths[] = new double[6];
        lengths[0] = locationHit.subtract(points2[0]).length();
        double lMin = lengths[0];
        int iMin = 0;
        for(int i = 1; i < 6; i++) {
            lengths[i] = locationHit.subtract(points2[i]).length();
            PipeHype.LOGGER.debug(":pre min: "+lMin);
            if(lMin > lengths[i]) {
                lMin = lengths[i];
                iMin = i;
            }
            PipeHype.LOGGER.debug(":post min: "+lMin);
        }
        String particles[] = {"front", "right", "left", "up", "down", "back"};

        PipeHype.LOGGER.debug(":nearest: "+particles[iMin]);

        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
