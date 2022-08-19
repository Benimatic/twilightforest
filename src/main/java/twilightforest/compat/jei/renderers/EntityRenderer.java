package twilightforest.compat.jei.renderers;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import mezz.jei.api.ingredients.IIngredientRenderer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;
import twilightforest.TwilightForestMod;

import java.util.*;

@SuppressWarnings({"rawtypes", "deprecation"})
//yoinked from TCon, entity melting rendering looks so good and I couldnt be bothered to write my own system
public class EntityRenderer implements IIngredientRenderer<EntityType> {

	private static final Set<EntityType<?>> IGNORED_ENTITIES = new HashSet<>();
	private final int size;
	private final Map<EntityType<?>, Entity> ENTITY_MAP = new HashMap<>();

	public EntityRenderer(int size) {
		this.size = size;
	}

	@Override
	public int getWidth() {
		return this.size;
	}

	@Override
	public int getHeight() {
		return this.size;
	}

	@Override
	public void render(PoseStack stack, @Nullable EntityType type) {
		if (type != null) {
			Level level = Minecraft.getInstance().level;
			if (level != null && !IGNORED_ENTITIES.contains(type)) {
				Entity entity;
				// players cannot be created using the type, but we can use the client player
				// side effect is it renders armor/items
				if (type == EntityType.PLAYER) {
					entity = Minecraft.getInstance().player;
				} else {
					// entity is created with the client world, but the entity map is thrown away when JEI restarts so they should be okay I think
					entity = this.ENTITY_MAP.computeIfAbsent(type, t -> t.create(level));
				}
				// only can draw living entities, plus non-living ones don't get recipes anyways
				if (entity instanceof LivingEntity livingEntity) {
					// scale down large mobs, but don't scale up small ones
					int scale = this.size / 2;
					float height = entity.getBbHeight();
					float width = entity.getBbWidth();
					if (height > 2.25F || width > 2.25F) {
						scale = (int) (20 / Math.max(height, width));
					}
					// catch exceptions drawing the entity to be safe, any caught exceptions blacklist the entity
					try {
						PoseStack modelView = RenderSystem.getModelViewStack();
						modelView.pushPose();
						modelView.mulPoseMatrix(stack.last().pose());
						InventoryScreen.renderEntityInInventoryRaw(this.size / 2, this.size - 2, scale, 25.0F, 145.0F, livingEntity);
						modelView.popPose();
						RenderSystem.applyModelViewMatrix();
						return;
					} catch (Exception e) {
						TwilightForestMod.LOGGER.error("Error drawing entity " + ForgeRegistries.ENTITY_TYPES.getKey(type), e);
						IGNORED_ENTITIES.add(type);
						this.ENTITY_MAP.remove(type);
					}
				} else {
					// not living, so might as well skip next time
					IGNORED_ENTITIES.add(type);
					this.ENTITY_MAP.remove(type);
				}
			}

			// fallback, draw a pink and black "spawn egg"
			RenderSystem.setShader(GameRenderer::getPositionTexShader);
			RenderSystem.setShaderTexture(0, TwilightForestMod.getGuiTexture("transformation_jei.png"));
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

			int offset = (this.size - 16) / 2;
			Screen.blit(stack, offset, offset, 149.0F, 58.0F, 16, 16, 256, 256);
		}
	}

	@Override
	public List<Component> getTooltip(EntityType type, TooltipFlag flag) {
		List<Component> tooltip = new ArrayList<>();
		tooltip.add(type.getDescription());
		if (flag.isAdvanced()) {
			tooltip.add(Component.literal(Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.getKey(type)).toString()).withStyle(ChatFormatting.DARK_GRAY));
		}
		return tooltip;
	}
}