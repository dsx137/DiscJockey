package com.sunset.discjockey.block.BlockEntity.Controller.Widget.AbstractWidget;

import com.sunset.discjockey.block.BlockEntity.Controller.Widget.Base.ControllerWidget;
import com.sunset.discjockey.block.BlockEntity.Controller.Widget.Base.ControllerWidgetManager;
import com.sunset.discjockey.util.SpecialType.OneShotBoolean;
import com.sunset.discjockey.util.TouchMap.Vec2Type.PlaneRange;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

public abstract class ControllerButton extends ControllerWidget {
    public OneShotBoolean pressed = new OneShotBoolean();

    public ControllerButton(String id, PlaneRange planeRange) {
        super(id, ControllerWidgetManager.InteractType.PRESS, planeRange);
    }

    @Override
    public void executeOnServer(Player player, double value, boolean condition) {
        pressed.set(true);
        this.markExecute();
        this.markDirty();
    }

    @Override
    public CompoundTag getCompoundTag() {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putBoolean("pressed", pressed.get());
        return compoundTag;
    }

    @Override
    public void writeCompoundTag(CompoundTag compoundTag) {
        pressed.set(compoundTag.getBoolean("pressed"));
    }

}
