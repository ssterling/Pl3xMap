/*
 * MIT License
 *
 * Copyright (c) 2020-2023 William Blake Galbreath
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package net.pl3x.map.bukkit;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.pl3x.map.bukkit.network.ClientboundMapPayload;
import net.pl3x.map.bukkit.network.ClientboundServerPayload;
import net.pl3x.map.bukkit.network.ServerboundMapPayload;
import net.pl3x.map.bukkit.network.ServerboundServerPayload;
import net.pl3x.map.core.configuration.Config;
import net.pl3x.map.core.network.Constants;
import net.pl3x.map.core.network.Network;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.craftbukkit.map.CraftMapRenderer;
import org.bukkit.entity.Player;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.jetbrains.annotations.NotNull;

public class BukkitNetwork extends Network {
    private final Pl3xMapBukkit plugin;

    public BukkitNetwork(Pl3xMapBukkit plugin) {
        this.plugin = plugin;
    }

    public void register() {
        Bukkit.getMessenger().registerOutgoingPluginChannel(this.plugin, ClientboundServerPayload.TYPE.id().toString());
        Bukkit.getMessenger().registerOutgoingPluginChannel(this.plugin, ClientboundMapPayload.TYPE.id().toString());
        Bukkit.getMessenger().registerIncomingPluginChannel(this.plugin, ServerboundServerPayload.TYPE.id().toString(),
                (channel, player, bytes) -> {
                    // sendServerData
                    // TODO: parse bytes

                    ServerPlayer serverPlayer = ((CraftPlayer) player).getHandle();
                    sendCustomPayloadPacket(serverPlayer, new ClientboundServerPayload(Constants.PROTOCOL, Constants.RESPONSE_SUCCESS, Config.WEB_ADDRESS));
                }
        );
        Bukkit.getMessenger().registerIncomingPluginChannel(this.plugin, ServerboundMapPayload.TYPE.id().toString(),
                (channel, player, bytes) -> {
                    // sendMapData
                    // TODO: parse bytes

                    // TODO: replace with id from incoming channel
                    int mapId = 0;

                    ServerPlayer serverPlayer = ((CraftPlayer) player).getHandle();

                    MapView map = Bukkit.getMap(mapId);
                    if (map == null) {
                        sendCustomPayloadPacket(serverPlayer, new ClientboundMapPayload(Constants.PROTOCOL, Constants.ERROR_NO_SUCH_MAP, mapId));
                        return;
                    }

                    World world = map.getWorld();
                    if (world == null) {
                        sendCustomPayloadPacket(serverPlayer, new ClientboundMapPayload(Constants.PROTOCOL, Constants.ERROR_NO_SUCH_WORLD, mapId));
                        return;
                    }

                    sendCustomPayloadPacket(serverPlayer, new ClientboundMapPayload(
                            Constants.PROTOCOL, Constants.RESPONSE_SUCCESS, mapId,
                            getScale(map), map.getCenterX(), map.getCenterZ(), world.getName()
                    ));
                }
        );
    }

    @NotNull
    private static void sendCustomPayloadPacket(ServerPlayer player, CustomPacketPayload customPacketPayload) {
        player.connection.send(new ClientboundCustomPayloadPacket(customPacketPayload));
    }

    public void unregister() {
        Bukkit.getMessenger().unregisterOutgoingPluginChannel(this.plugin, ClientboundServerPayload.TYPE.id().toString());
        Bukkit.getMessenger().unregisterOutgoingPluginChannel(this.plugin, ClientboundMapPayload.TYPE.id().toString());
        Bukkit.getMessenger().unregisterIncomingPluginChannel(this.plugin, ServerboundServerPayload.TYPE.id().toString());
        Bukkit.getMessenger().unregisterIncomingPluginChannel(this.plugin, ServerboundMapPayload.TYPE.id().toString());
    }

    @Override
    protected <T> void sendServerData(T player) {

    }

    @Override
    protected <T> void sendMapData(T player, int id) {

    }

    @Override
    protected <T> void send(T player, ByteArrayDataOutput out) {

    }

    @SuppressWarnings("deprecation")
    private byte getScale(MapView map) {
        return map.getScale().getValue();
    }
}
