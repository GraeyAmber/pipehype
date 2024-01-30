package net.graeyamber.pipehype.registry;

import net.graeyamber.pipehype.PipeHype;
import net.graeyamber.pipehype.item.GauntletItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ItemRegistry {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(PipeHype.MODID);

    public static Item.Properties baseItem() {
        return new Item.Properties();
    }


    public static final DeferredItem<GauntletItem> GAUNTLET = ITEMS.register("gauntlet",
            () -> new GauntletItem(baseItem()));
}
