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
package net.pl3x.map.core.command.parser;

import java.util.stream.Collectors;
import net.pl3x.map.core.Pl3xMap;
import net.pl3x.map.core.command.Sender;
import net.pl3x.map.core.command.exception.PlayerParseException;
import net.pl3x.map.core.configuration.Lang;
import net.pl3x.map.core.player.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.context.CommandInput;
import org.incendo.cloud.parser.ArgumentParseResult;
import org.incendo.cloud.parser.ArgumentParser;
import org.incendo.cloud.parser.ParserDescriptor;
import org.incendo.cloud.suggestion.BlockingSuggestionProvider;
import org.jetbrains.annotations.NotNull;

/**
 * Parser that parses strings into {@link Player}s.
 *
 * @param <C> command sender type
 */
public class PlayerParser<C> implements ArgumentParser<@NotNull C, @NotNull Player>, BlockingSuggestionProvider.Strings<C> {
    public static <C> ParserDescriptor<C, Player> parser() {
        return ParserDescriptor.of(new PlayerParser<>(), Player.class);
    }

    @Override
    public @NonNull ArgumentParseResult<@NonNull @NotNull Player> parse(@NonNull CommandContext<@NonNull @NotNull C> commandContext, @NonNull CommandInput commandInput) {
        String input = commandInput.peekString();
        if (input == null) {
            return ArgumentParseResult.failure(new PlayerParseException(null, PlayerParseException.MUST_SPECIFY_PLAYER));
        }

        Player player = Pl3xMap.api().getPlayerRegistry().get(input);
        if (player == null) {
            return ArgumentParseResult.failure(new PlayerParseException(input, PlayerParseException.NO_SUCH_PLAYER));
        }

        commandInput.readString();
        return ArgumentParseResult.success(player);
    }

    public static @NotNull Player resolvePlayer(@NotNull CommandContext<@NotNull Sender> context) {
        Sender sender = context.sender();
        Player player = context.getOrDefault("player", null);
        if (player != null) {
            return player;
        }
        if (sender instanceof Sender.Player<?> senderPlayer) {
            player = Pl3xMap.api().getPlayerRegistry().get(senderPlayer.getUUID());
            if (player != null) {
                return player;
            }
        }
        sender.sendMessage(Lang.ERROR_MUST_SPECIFY_PLAYER);
        ArgumentParseResult.failure(new PlayerParseException(null, PlayerParseException.MUST_SPECIFY_PLAYER));
        return player;
    }

    @Override
    public @NonNull Iterable<@NonNull String> stringSuggestions(@NonNull CommandContext<C> commandContext, @NonNull CommandInput input) {
        return Pl3xMap.api().getPlayerRegistry()
                .values().stream()
                .map(Player::getName)
                .collect(Collectors.toList());
    }
}
