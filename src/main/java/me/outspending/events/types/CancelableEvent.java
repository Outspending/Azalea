package me.outspending.events.types;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class CancelableEvent implements Event {
    private boolean cancelled = false;
}
