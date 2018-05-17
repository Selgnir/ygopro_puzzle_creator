package com.ulquertech;

import com.ulquertech.dominio.DatosPuzzle;
import com.ulquertech.dominio.PuzzleCardBuilder;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import plataforma1.wicket.semantic.NotifierProvider;

import javax.inject.Inject;
import java.util.Arrays;

public class DatosPanel extends AbstractPanel {
    @Inject
    private NotifierProvider notifierProvider;
    @Inject
    private PuzzleCardBuilder puzzleCardBuilder;
    private CompoundPropertyModel<DatosPuzzle> model;
    private DatosPuzzle datosPuzzle = new DatosPuzzle();
    private OnChangeCallback callback;

    DatosPanel(String id, OnChangeCallback callback) {
        super(id);
        this.callback = callback;
        addForm();
    }

    public DatosPuzzle getDatosPuzzle() {
        return datosPuzzle;
    }

    private void addForm() {
        model = new CompoundPropertyModel<>(datosPuzzle);
        Form<DatosPuzzle> form = new Form<>("form");
        form.setModel(model);
        AjaxFormComponentUpdatingBehavior validarOnUpdate = new AjaxFormComponentUpdatingBehavior("onchange") {
            @Override
            protected void onUpdate(AjaxRequestTarget ajaxRequestTarget) {
                StringBuilder stringBuilder = new StringBuilder();
                validar();
                notificar(stringBuilder);
            }
        };

        TextField puzzleNameTextField = new TextField<String>("puzzleName");
        puzzleNameTextField.add(validarOnUpdate);
        form.add(puzzleNameTextField);

        TextField opponentNameTextField = new TextField<String>("opponentName");
        opponentNameTextField.add(validarOnUpdate);
        form.add(opponentNameTextField);

        NumberTextField userLpNumberField = new NumberTextField<Integer>("userLP");
        userLpNumberField.add(validarOnUpdate);
        form.add(userLpNumberField);

        NumberTextField opponentLpNumberField = new NumberTextField<Integer>("opponentLP");
        opponentLpNumberField.add(validarOnUpdate);
        form.add(opponentLpNumberField);

        DropDownChoice masterRuleDrop = new DropDownChoice<>("masterRule", Arrays.asList(1, 2, 3, 4));
        masterRuleDrop.setNullValid(false);
        masterRuleDrop.add(new AjaxFormComponentUpdatingBehavior("onchange") {
            @Override
            protected void onUpdate(AjaxRequestTarget ajaxRequestTarget) {
                StringBuilder stringBuilder = new StringBuilder();
                puzzleCardBuilder.adaptToNewMasterRule(datosPuzzle.getMasterRule(), stringBuilder);
                callback.onChanged(ajaxRequestTarget);
                notificar(stringBuilder);
            }
        });
        form.add(masterRuleDrop);

        add(form);
    }

    private Boolean validar() {
        StringBuilder stringBuilder = new StringBuilder();
        if (model.getObject().getPuzzleName() == null || model.getObject().getPuzzleName().isEmpty()) {
            stringBuilder.append("<li>Puzzle name is required</li>");
        } else if (model.getObject().getPuzzleName().length() > 255) {
            stringBuilder.append("<li>Puzzle name must have less than 255 characters</li>");
        }
        if (model.getObject().getOpponentName() == null || model.getObject().getOpponentName().isEmpty()) {
            stringBuilder.append("<li>Opponent name is required</li>");
        } else if (model.getObject().getOpponentName().length() > 30) {
            stringBuilder.append("<li>Opponent name must have less than 30 characters</li>");
        }
        if (model.getObject().getUserLP() <= 0) {
            stringBuilder.append("<li>Your life Points must be greater than 0</li>");
        }
        if (model.getObject().getOpponentLP() <= 0) {
            stringBuilder.append("<li>Your opponent's life Points must be greater than 0</li>");
        }

        return notificar(stringBuilder);
    }

    private Boolean notificar(StringBuilder stringBuilder) {
        if (stringBuilder.length() > 0) {
            notifierProvider.getNotifier(getPage()).notify("Error", "<ul>" + stringBuilder.toString() + "</ul>");
            return false;
        } else {
            notifierProvider.getNotifier(getPage()).notify("OK", "");
            return true;
        }
    }
}
