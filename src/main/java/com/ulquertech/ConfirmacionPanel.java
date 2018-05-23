package com.ulquertech;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;


public class ConfirmacionPanel extends Panel {

    private OkCancelCallback okCancelCallback;
    private Model<String> model;

    public ConfirmacionPanel(String id) {
        super(id);
        addMensaje();
        setOutputMarkupId(true);
    }

    private void addMensaje() {
        model = new Model<>("");
        add(new Label("mensaje", model));
        add(new AjaxLink<Void>("aceptar") {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                okCancelCallback.onOk(ajaxRequestTarget);
            }
        });
        add(new AjaxLink<Void>("cancelar") {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                okCancelCallback.onCancel(ajaxRequestTarget);
            }
        });
    }


    public void cargar(String s, OkCancelCallback okCancelCallback) {
        this.okCancelCallback = okCancelCallback;
        model.setObject(s);
    }

//    public void prepareModal(ModalWindow modal) {
//        modal.setInitialWidth(400);
//        modal.setMinimalWidth(400);
//        modal.setInitialHeight(150);
//        modal.setMinimalHeight(150);
//        modal.setCookieName("sedeModal");
//        modal.setContent(this);
//        modal.setTitle("Confirmación");
//    }
}
