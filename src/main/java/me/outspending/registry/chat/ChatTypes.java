package me.outspending.registry.chat;

import lombok.Getter;
import me.outspending.NamespacedID;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;

import java.util.List;

import static me.outspending.registry.chat.ChatTypeDecoration.Parameter;

@Getter
public enum ChatTypes {

    CHAT_TYPE(ChatType.builder("chat")
            .chat(new ChatTypeDecoration(
                    "chat.type.text",
                    List.of(Parameter.SENDER, Parameter.CONTENT),
                    Style.empty()
            ))
            .narration(new ChatTypeDecoration(
                    "chat.type.text.narrate",
                    List.of(Parameter.SENDER, Parameter.CONTENT),
                    Style.empty()
            ))
            .build()
    ),

    EMOTE_COMMAND(ChatType.builder("emote_command")
            .chat(new ChatTypeDecoration(
                    "chat.type.emote",
                    List.of(Parameter.SENDER, Parameter.CONTENT),
                    Style.empty()
            ))
            .narration(new ChatTypeDecoration(
                    "chat.type.emote",
                    List.of(Parameter.SENDER, Parameter.CONTENT),
                    Style.empty()
            ))
            .build()
    ),

    MSG_COMMAND_INCOMING(ChatType.builder("msg_command_incoming")
            .chat(new ChatTypeDecoration(
                    "commands.message.display.incoming",
                    List.of(Parameter.SENDER, Parameter.CONTENT),
                    Style.style(
                            TextColor.color(170, 170, 170),
                            TextDecoration.ITALIC
                    )
            ))
            .narration(new ChatTypeDecoration(
                    "chat.type.text.narrate",
                    List.of(Parameter.SENDER, Parameter.CONTENT),
                    Style.empty()
            ))
            .build()
    ),

    MSG_COMMAND_OUTGOING(ChatType.builder("msg_command_outgoing")
            .chat(new ChatTypeDecoration(
                    "commands.message.display.outgoing",
                    List.of(Parameter.TARGET, Parameter.CONTENT),
                    Style.style(
                            TextColor.color(170, 170, 170),
                            TextDecoration.ITALIC
                    )
            ))
            .narration(new ChatTypeDecoration(
                    "chat.type.text.narrate",
                    List.of(Parameter.SENDER, Parameter.CONTENT),
                    Style.empty()
            ))
            .build()
    ),

    SAY_COMMAND(ChatType.builder("say_command")
            .chat(new ChatTypeDecoration(
                    "chat.type.announcement",
                    List.of(Parameter.SENDER, Parameter.CONTENT),
                    Style.empty()
            ))
            .narration(new ChatTypeDecoration(
                    "chat.type.text.narrate",
                    List.of(Parameter.SENDER, Parameter.CONTENT),
                    Style.empty()
            ))
            .build()
    ),

    TEAM_MSG_COMMAND_INCOMING(ChatType.builder("team_msg_command_incoming")
            .chat(new ChatTypeDecoration(
                    "chat.type.text",
                    List.of(Parameter.TARGET, Parameter.SENDER, Parameter.CONTENT),
                    Style.empty()
            ))
            .narration(new ChatTypeDecoration(
                    "chat.type.text.narrate",
                    List.of(Parameter.SENDER, Parameter.CONTENT),
                    Style.empty()
            ))
            .build()
    ),

    TEAM_MSG_COMMAND_OUTGOING(ChatType.builder("team_msg_command_outgoing")
            .chat(new ChatTypeDecoration(
                    "chat.type.text",
                    List.of(Parameter.TARGET, Parameter.SENDER, Parameter.CONTENT),
                    Style.empty()
            ))
            .narration(new ChatTypeDecoration(
                    "chat.type.text.narrate",
                    List.of(Parameter.SENDER, Parameter.CONTENT),
                    Style.empty()
            ))
            .build()
    );

    private final ChatType type;

    ChatTypes(ChatType type) {
        this.type = type;
    }

    public static ChatTypes[] all() {
        return ChatTypes.values();
    }

}
