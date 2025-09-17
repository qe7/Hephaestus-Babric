package io.github.qe7.toolbox;

import io.github.qe7.core.common.Global;
import lombok.experimental.UtilityClass;
import net.minecraft.src.Packet;

@UtilityClass
public final class PacketUtil implements Global {

    public static void sendPacket(final Packet packet) {
        if (mc.theWorld != null && mc.theWorld.multiplayerWorld) {
            mc.getSendQueue().addToSendQueue(packet);
        }
    }
}
