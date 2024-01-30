package net.graeyamber.pipehype.registry;

import net.graeyamber.pipehype.PipeHype;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CreativeTabRegistry {

    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, PipeHype.MODID);

    // Creates a creative tab
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> PIPEHYPE_ITEMS_TAB =
            CREATIVE_MODE_TABS.register("pipehype_items_tab", () -> CreativeModeTab.builder()
                    .withTabsBefore(CreativeModeTabs.COMBAT)
                    .icon(() -> ItemRegistry.GAUNTLET.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        output.accept(ItemRegistry.GAUNTLET.get());
                    }).build());
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> PIPEHYPE_BLOCKS_TAB =
            CREATIVE_MODE_TABS.register("pipehype_blocks_tab", () -> CreativeModeTab.builder()
                    .withTabsBefore(CreativeTabRegistry.PIPEHYPE_ITEMS_TAB.getKey())
                    .icon(() -> BlockRegistry.REDBRIDGE.asItem().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        output.accept(BlockRegistry.REDBRIDGE.get());
                    }).build());
}
