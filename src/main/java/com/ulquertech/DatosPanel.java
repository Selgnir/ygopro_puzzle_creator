package com.ulquertech;

import com.ulquertech.dominio.DatosPuzzle;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import plataforma1.wicket.semantic.NotifierProvider;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.Arrays;

public class DatosPanel extends AbstractPanel {
    @Inject
    private NotifierProvider notifierProvider;
    private CompoundPropertyModel<DatosPuzzle> model;

    public interface Callback extends Serializable {
        void onChanged(AjaxRequestTarget ajaxRequestTarget);
    }

    DatosPanel(String id) {
        super(id);
        addForm();
    }

    private void addForm() {
        model = new CompoundPropertyModel<>(new DatosPuzzle());
        Form<DatosPuzzle> form = new Form<>("form");
        form.setModel(model);
        form.add(new TextField<String>("puzzleName"));
        form.add(new TextField<String>("oponentName"));
        form.add(new NumberTextField<>("userLP"));
        form.add(new NumberTextField<>("oponentLP"));
        form.add(new DropDownChoice<>("masterRule", Arrays.asList(1, 2, 3, 4)).setRequired(true));
        form.add(new AjaxSubmitLink("validar") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                validar();
            }
        });
        add(form);
    }

    private void validar() {
        StringBuilder stringBuilder = new StringBuilder();
        if (model.getObject().getPuzzleName() == null || model.getObject().getPuzzleName().isEmpty()) {
            stringBuilder.append("<li>Puzzle name is required</li>");
        } else if (model.getObject().getPuzzleName().length() > 255) {
            stringBuilder.append("<li>Puzzle name must have less than 255 characters</li>");
        }
        if (model.getObject().getOponentName() == null || model.getObject().getOponentName().isEmpty()) {
            stringBuilder.append("<li>Oponent name is required</li>");
        } else if (model.getObject().getOponentName().length() > 30) {
            stringBuilder.append("<li>Oponent name must have less than 30 characters</li>");
        }
        if (model.getObject().getUserLP() <= 0) {
            stringBuilder.append("<li>Your life Points must be greater than 0</li>");
        }
        if (model.getObject().getOponentLP() <= 0) {
            stringBuilder.append("<li>Your oponent's life Points must be greater than 0</li>");
        }

        if (stringBuilder.length() > 0) {
            notifierProvider.getNotifier(getPage()).notify("Error", "<ul>" + stringBuilder.toString() + "</ul>");
        } else {
            notifierProvider.getNotifier(getPage()).notify("OK", "");
        }
    }
}
