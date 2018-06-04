package com.ulquertech;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import plataforma1.wicket.semantic.NotifierProvider;

import javax.inject.Inject;

public class HomePage extends WebPage {
    @Inject
    private NotifierProvider notifierProvider;

    public HomePage(final PageParameters parameters) {
        super(parameters);
        setVersioned(false);
        notifierProvider.createNotifier(this, "notifier");
        addButtons();
        addPanel();
    }

    private void addPanel() {
        MainPanel mainPanel = new MainPanel("content");
        add(mainPanel);
    }

    private void addButtons() {
        AjaxLink newLink = new AjaxLink("nuevo") {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                nuevo();
            }
        };
        add(newLink);
        AjaxLink openLink = new AjaxLink("abrir") {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                abrir();
            }
        };
        add(openLink);
        AjaxLink saveLink = new AjaxLink("guardar") {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                guardar();
            }
        };
        add(saveLink);
        AjaxLink cleanLink = new AjaxLink("limpiar") {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                limpiar();
            }
        };
        add(cleanLink);
    }


    private void nuevo() {
        //ConfirmacionPanel con opciones Si, No, Cancelar, en su callback
        //TODO: revisar si hay cartas en el puzzle, y preguntar si desea guardar el puzzle actual (usar un ConfirmationPanel, para que me retorne las acciones a realizar en este panel)
        //TODO: Ok: llamar a guardar y cerrar el archivo abierto (si alguno), resetear el panel de datos, limpiar el campo, y vaciar la lista de cartas en el puzzle
        //TODO: No: resetear el panel de datos, limpiar el campo, y vaciar la lista de cartas en el puzzle
        //TODO: Cancel: cierra el modal y no hace nada
    }

    private void abrir() {
        //ConfirmacionPanel con opciones Si, No, Cancelar, en su callback
        //TODO: revisar si hay cartas en el puzzle, y preguntar si desea guardar el puzzle actual (usar un ConfirmationPanel, para que me retorne las acciones a realizar en este panel)
        //TODO: Ok: llamar a guardar y cerrar el archivo abierto (si alguno), y abrir el diálogo de selección (cargar un archivo, implica limpiar y reemplazar todo con sus datos).
        //TODO: No: sólo abrir el diálogo de selección
        //TODO: Cancel: cancela

        //TODO: cerrar el archivo abierto, si alguno, antes de abrir el nuevo
    }

    private void guardar() {
        //ConfirmacionPanel con opciones Si, Cancelar, en su callback
        //TODO: guardar los datos ingresados en un archivo nuevo, o en el abierto, si alguno
        //TODO: preguntar si desea sobreescribir el archivo, si existe
        //TODO: informar si se guardó correctamente
    }

    private void limpiar() {
        //ConfirmacionPanel con opciones Si, Cancelar, en su callback
        //TODO: revisar si hay cartas en el puzzle, y preguntar si está seguro de que quiere limpiar el puzzle actual (usar un ConfirmationPanel, para que me retorne las acciones a realizar en este panel)
        //TODO: resetear el panel de datos, limpiar el campo, vaciar la lista de cartas en el puzzle
    }

    public NotifierProvider getNotifierProvider() {
        return notifierProvider;
    }

}
