package com.ulquertech;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import plataforma1.wicket.semantic.NotifierProvider;

import javax.inject.Inject;
import java.io.Serializable;

public class BusquedaFiltroPanel extends AbstractPanel {
    @Inject
    private NotifierProvider notifierProvider;
    private final OnChangeCallback callback;
    private CompoundPropertyModel<BusquedaFiltroData> model;

    public BusquedaFiltroPanel(String id, OnChangeCallback callback) {
        super(id);
        this.callback = callback;
        addComponents();
    }

    private void addComponents() {
        BusquedaFiltroData busquedaFiltroData = new BusquedaFiltroData();
        model = new CompoundPropertyModel<>(busquedaFiltroData);
        Form<BusquedaFiltroData> form = new Form<>("form", model);
        add(form);
        form.add(new TextField<String>("criterio"));
        form.add(new AjaxSubmitLink("buscar") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                model.getObject().setCriterio(model.getObject().getCriterio().replaceAll("'", "''"));
                callback.onChanged(target);
                target.add(this);
            }
        });
    }

    public BusquedaFiltroData getBusquedaFiltroData() {
        return model.getObject();
    }
}
