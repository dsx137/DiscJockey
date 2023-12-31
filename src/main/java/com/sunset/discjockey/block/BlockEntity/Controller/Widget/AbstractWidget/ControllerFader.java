package com.sunset.discjockey.block.BlockEntity.Controller.Widget.AbstractWidget;

import com.sunset.discjockey.block.BlockEntity.Controller.Widget.Base.ControllerWidget;
import com.sunset.discjockey.block.BlockEntity.Controller.Widget.Base.ControllerWidgetManager;
import com.sunset.discjockey.util.SpecialType.SimpleInterpolationValue;
import com.sunset.discjockey.util.TouchMap.Vec2Type.PlaneRange;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

public class ControllerFader extends ControllerWidget {

    public SimpleInterpolationValue value = new SimpleInterpolationValue();
    public SimpleInterpolationValue renderValue = new SimpleInterpolationValue();

    public ControllerFader(String id, PlaneRange planeRange) {
        super(id, ControllerWidgetManager.InteractType.DRAG, planeRange);
        this.value.onServerInterpolate = this::markDirty;
    }

    @Override
    public void executeOnServer(Player player, double value, boolean condition) {
        this.value.setTarget(value);
        this.markExecute();
        this.markDirty();
    }

    @Override
    public void executeOnClient() {
        //TODO
    }

    @Override
    public CompoundTag getCompoundTag() {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putDouble("value", this.value.get());
        compoundTag.putDouble("future_value", this.value.interpolateFuture());
        return compoundTag;
    }

    @Override
    public void writeCompoundTag(CompoundTag compoundTag) {
        this.value.set(compoundTag.getDouble("value"));
        this.renderValue.set(this.value.get());
        this.renderValue.setTarget(compoundTag.getDouble("future_value"));
    }

    @Override
    public void onServerTick() {
        super.onServerTick();
        this.value.onServerTick();
    }

    @Override
    public void onClientTick() {
        super.onClientTick();
        this.value.onClientTick();
    }
}
