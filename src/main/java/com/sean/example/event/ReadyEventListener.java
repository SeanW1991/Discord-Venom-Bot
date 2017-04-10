package com.sean.example.event;

import com.sean.example.Application;
import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.ReadyEvent;

/**
 * Created by sean on 06/04/2017.
 */
public final class ReadyEventListener implements IListener<ReadyEvent> {

    /**
     * The {@link Application} reference.
     */
    private final Application application;

    /**
     * Creates a new {@link ReadyEventListener}.
     * @param application The {@link Application} reference.
     */
    public ReadyEventListener(Application application) {
        this.application = application;
    }

    @Override
    public void handle(ReadyEvent event) {

    }
}
