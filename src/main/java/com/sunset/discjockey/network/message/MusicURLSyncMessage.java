package com.sunset.discjockey.network.message;

import com.sunset.discjockey.block.BlockEntity.Controller.AbstractControllerEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;

import java.util.Vector;
import java.util.function.Supplier;

import static com.sunset.discjockey.DiscJockey.DEBUG_LOGGER;

public class MusicURLSyncMessage {
    public BlockPos pos;
    public Vector<String> urls = new Vector<>();

    public MusicURLSyncMessage(BlockPos pos, Vector<String> urls) {
        this.pos = pos;
        this.urls = urls;
    }

    public static void encode(MusicURLSyncMessage message, FriendlyByteBuf buf) {
        buf.writeBlockPos(message.pos);
        for (String url : message.urls) {
            buf.writeUtf(url);
        }
    }

    public static MusicURLSyncMessage decode(FriendlyByteBuf buf) {
        BlockPos pos = buf.readBlockPos();
        Vector<String> urls = new Vector<>();
        while (buf.isReadable()) {
            urls.add(buf.readUtf());
        }
        return new MusicURLSyncMessage(pos, urls);
    }

    public static void handle(MusicURLSyncMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
//            context.getSender().level().getBlockEntity(message.pos).getCapability(MusicURLSyncMessageProvider.MUSIC_URL_SYNC_MESSAGE_CAPABILITY).ifPresent(cap -> {
//                cap.setURLs(message.urls);
//            });
                    Level level = context.getSender().level();
                    if (level != null && level.isLoaded(message.pos)) {
                        BlockEntity blockEntity = level.getBlockEntity(message.pos);
//            if(blockEntity instanceof MusicURLSyncMessageProvider) {
//                ((MusicURLSyncMessageProvider) blockEntity).setURLs(message.urls);
//            }
                        if (blockEntity instanceof AbstractControllerEntity controller) {
                            controller.controllerAudioManager.audios = message.urls;
                            context.getSender().sendSystemMessage(Component.literal("url has been set!"));
                        } else {
                            DEBUG_LOGGER.debug("what the hell?" + MusicURLSyncMessage.class.getName());
                        }
                    }
                }
        );
        context.setPacketHandled(true);
    }
}