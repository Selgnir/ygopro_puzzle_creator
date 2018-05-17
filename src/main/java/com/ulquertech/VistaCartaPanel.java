package com.ulquertech;

import com.ulquertech.dominio.Card;
import com.ulquertech.dominio.PuzzleCardBuilder;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.PackageResourceReference;
import plataforma1.wicket.semantic.NotifierProvider;

import javax.inject.Inject;

public class VistaCartaPanel extends AbstractPanel {
    @Inject
    private NotifierProvider notifierProvider;
    @Inject
    private PuzzleCardBuilder puzzleCardBuilder;
    private WebMarkupContainer container;
    private TextArea descriptionArea;
    private Image cardView;
    private Model<String> model;
    private OnChangeCallback callback;
    private Card cardOnPreview;


    VistaCartaPanel(String id, OnChangeCallback callback) {
        super(id);
        this.callback = callback;
        addContainer();
    }

    private void addContainer() {
        model = new Model<>("");
        container = new WebMarkupContainer("container");
        container.setOutputMarkupId(true);

        PackageResourceReference resourceReference = new PackageResourceReference(getClass(), "unknown.jpg");
        cardView = new Image("cardView", resourceReference);
        cardView.setOutputMarkupId(true);
        container.add(cardView);

        descriptionArea = new TextArea<>("cardText", model);
        descriptionArea.setOutputMarkupId(true);
    }

    public void cargar(Card card) {
        cardOnPreview = card;
//        intentar conseguir la imagen y si no recupera el archivo, setear unknown.jpg
        PackageResourceReference resourceReference = new PackageResourceReference(getClass(), "pics/" + card.getId() + ".jpg");
        cardView.setImageResourceReference(resourceReference);
        model.setObject(constructCardText(card));
    }

    private String constructCardText(Card card) {
        StringBuilder stringBuilder = new StringBuilder();
        if(card.isMonster()) {

        } else {
            stringBuilder.append(card.getType())
                    .append("\n")
                    .append(card.getDesc());
        }
        return stringBuilder.toString();
    }
}