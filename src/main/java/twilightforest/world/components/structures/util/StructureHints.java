package twilightforest.world.components.structures.util;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import twilightforest.TwilightForestMod;
import twilightforest.entity.TFEntities;
import twilightforest.entity.monster.Kobold;

// TODO Data-driven Book-setting. Or we could use a loot table instead to create a book?
public interface StructureHints {
    String BOOK_AUTHOR = "A Forgotten Explorer";

    /**
     * Create a hint book for the specified feature.  Only features with block protection will need this.
     */
    default ItemStack createHintBook() {
        ItemStack book = new ItemStack(Items.WRITTEN_BOOK);
        this.addBookInformation(book, new ListTag());
        return book;
    }

    default void addBookInformation(ItemStack book, ListTag bookPages) {
        addTranslatedPages(bookPages, TwilightForestMod.ID + ".book.unknown", 2);

        book.addTagElement("pages", bookPages);
        book.addTagElement("author", StringTag.valueOf(BOOK_AUTHOR));
        book.addTagElement("title", StringTag.valueOf("Notes on the Unexplained"));
    }

    static void addTranslatedPages(ListTag bookPages, String translationKey, int pageCount) {
        for (int i = 1; i <= pageCount; i++)
            bookPages.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable(translationKey + "." + i))));
    }

    /**
     * Try to spawn a hint monster near the specified player
     */
    default void trySpawnHintMonster(Level world, Player player) {
        this.trySpawnHintMonster(world, player, player.blockPosition());
    }

    /**
     * Try several times to spawn a hint monster
     */
    void trySpawnHintMonster(Level world, Player player, BlockPos pos);


    /**
     * Try once to spawn a hint monster near the player.  Return true if we did.
     * <p>
     * We could change up the monster depending on what feature this is, but we currently are not doing that
     */
    default boolean didSpawnHintMonster(Level world, Player player, BlockPos pos) {
        // find a target point
        int dx = world.random.nextInt(16) - world.random.nextInt(16);
        int dy = world.random.nextInt( 4) - world.random.nextInt( 4);
        int dz = world.random.nextInt(16) - world.random.nextInt(16);

        // make our hint monster
        Kobold hinty = TFEntities.KOBOLD.get().create(world);
        hinty.moveTo(pos.offset(dx, dy, dz), 0f, 0f);

        // check if the bounding box is clear
        if (hinty.checkSpawnObstruction(world) && hinty.getSensing().hasLineOfSight(player)) {

            // add items and hint book
            ItemStack book = this.createHintBook();

            hinty.setItemSlot(EquipmentSlot.MAINHAND, book);
            hinty.setDropChance(EquipmentSlot.MAINHAND, 1.0F);
            //hinty.setDropItemsWhenDead(true);

            world.addFreshEntity(hinty);
            return true;
        }

        return false;
    }
}
