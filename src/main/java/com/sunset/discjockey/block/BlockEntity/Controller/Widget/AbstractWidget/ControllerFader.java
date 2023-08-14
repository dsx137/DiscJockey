package com.sunset.discjockey.block.BlockEntity.Controller.Widget.AbstractWidget;

import com.sunset.discjockey.block.BlockEntity.Controller.Widget.Base.ControllerWidget;
import com.sunset.discjockey.block.BlockEntity.Controller.Widget.Base.ControllerWidgetManager;
import com.sunset.discjockey.util.TouchMap.Vec2Type.PlaneRange;
import net.minecraft.nbt.CompoundTag;

public abstract class ControllerFader extends ControllerWidget
{
    public double value = 0.0; //between -1 to 1

    public ControllerFader(String id, PlaneRange planeRange) {
        super(id, ControllerWidgetManager.InteractType.DRAG, planeRange);
    }

    @Override
    public CompoundTag getCompoundTag() {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putDouble("value", this.value);
        return compoundTag;
    }

    @Override
    public void writeCompoundTag(CompoundTag compoundTag) {
        this.value = compoundTag.getDouble("value");
    }
}
