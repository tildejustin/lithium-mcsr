package me.jellysquid.mods.lithium.mixin.entity.replace_entitytype_predicates;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.jellysquid.mods.lithium.common.world.WorldHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.AbstractDecorationEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;
import java.util.function.Predicate;

@Mixin(ItemFrameEntity.class)
public abstract class ItemFrameEntityMixin extends AbstractDecorationEntity {
    protected ItemFrameEntityMixin(EntityType<? extends AbstractDecorationEntity> entityType_1, World world_1) {
        super(entityType_1, world_1);
    }

    @WrapOperation(
            method = "canStayAttached",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;getEntities(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/Box;Ljava/util/function/Predicate;)Ljava/util/List;"
            )
    )
    private List<Entity> getAbstractDecorationEntities(World world, Entity excluded, Box box, Predicate<? super Entity> predicate, Operation<List<Entity>> original) {
        if (predicate == PREDICATE) {
            return WorldHelper.getEntitiesOfClass(world, excluded, AbstractDecorationEntity.class, box);
        }
        return original.call(world, excluded, box, predicate);
    }
}
