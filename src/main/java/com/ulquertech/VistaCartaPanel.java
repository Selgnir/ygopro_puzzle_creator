package com.ulquertech;

import com.ulquertech.dominio.Card;
import com.ulquertech.dominio.PuzzleCardBuilder;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.PackageResourceReference;
import plataforma1.wicket.semantic.NotifierProvider;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

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
        descriptionArea.setEnabled(false);
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
        stringBuilder.append(card.getName()).append("[").append(card.getId()).append("]\n");
        if(card.isMonster()) {
            stringBuilder.append(card.getType()).append(" ").append(card.getRace()).append("/").append(card.getAttribute()).append("\n");
            if (!card.getType().contains("Link")) {
                stringBuilder.append("[").append(tipoDeEstrellaBy(card)).append(card.getLevel()).append("] ");
            }
            stringBuilder.append(card.getAtk()).append("/");
            if(card.getType().contains("Link")) {
                stringBuilder.append("Link ").append(card.getLevel()).append(" ").append(flechasBy(card));
            } else {
                stringBuilder.append(card.getDef());
                if(card.getType().contains("Pendulum")){
                    stringBuilder.append(" ").append(card.getScale()).append("/").append(card.getScale());
                }
            }
        } else {
            stringBuilder.append(card.getType());
        }
        stringBuilder.append("\n").append(card.getDesc());
        return stringBuilder.toString();
    }

    private String tipoDeEstrellaBy(Card card) {
        return card.getType().contains("XYZ") ? "&#9734;" : "&#9733;";
        //☆ White 5 point star "&#9734;"
        //★ Black 5 point star "&#9733;"
    }

    private String flechasBy(Card card) {
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, String> mapa = getMapaFlechas();
        for(String arrow : card.getArrows()){
            stringBuilder.append(mapa.get(arrow));
        }
        return stringBuilder.toString();
    }

    private Map<String, String> getMapaFlechas() {
        Map<String,String> mapa = new HashMap<>(); //Los corchetes no son parte del caracter de flecha
        mapa.put("N","[&#8593;]");
        mapa.put("NE","[&#8599;]");
        mapa.put("E","[&#8594;]");
        mapa.put("NW","[&#8598;]");
        mapa.put("W","[&#8592;]");
        mapa.put("SE","[&#8600;]");
        mapa.put("S","[&#8595;]");
        mapa.put("SW","[&#8601;]");
        return mapa;
    }
}