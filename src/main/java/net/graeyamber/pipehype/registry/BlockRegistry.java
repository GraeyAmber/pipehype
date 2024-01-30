package net.graeyamber.pipehype.registry;

import net.graeyamber.pipehype.PipeHype;
import net.graeyamber.pipehype.block.RedBridgeBlock;
import net.graeyamber.pipehype.item.GauntletItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static net.graeyamber.pipehype.registry.ItemRegistry.ITEMS;

public class BlockRegistry {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(PipeHype.MODID);

    private static <T extends Block> DeferredBlock<Block>  registerBlock(String name, Supplier<T> block) {
        DeferredBlock<Block> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> DeferredItem<BlockItem> registerBlockItem(String name, DeferredBlock<Block> block) {
        return ItemRegistry.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    // Creates a new Block
    public static final DeferredBlock<Block> REDBRIDGE = registerBlock("redbridge",
            () -> new RedBridgeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS)));

}
