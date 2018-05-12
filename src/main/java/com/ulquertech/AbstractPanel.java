package com.ulquertech;

import org.apache.wicket.markup.html.panel.Panel;
import plataforma1.wicket.semantic.NotifierProvider;

import javax.inject.Inject;

public class AbstractPanel extends Panel {
    @Inject
    private NotifierProvider notifierProvider;

    public AbstractPanel(String id) {
        super(id);
    }

    public void notifyUser(String title, String message) {
        notifierProvider.getNotifier(getPage()).notify(title, message);
    }

}