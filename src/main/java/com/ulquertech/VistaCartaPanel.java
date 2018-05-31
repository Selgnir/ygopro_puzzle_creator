package com.ulquertech;

import com.googlecode.wicket.jquery.ui.widget.menu.ContextMenu;
import com.googlecode.wicket.jquery.ui.widget.menu.ContextMenuBehavior;
import com.googlecode.wicket.jquery.ui.widget.menu.IMenuItem;
import com.googlecode.wicket.jquery.ui.widget.menu.MenuItem;
import com.ulquertech.dominio.Card;
import com.ulquertech.dominio.PuzzleCard;
import com.ulquertech.dominio.PuzzleCardBuilder;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.PackageResourceReference;
import plataforma1.wicket.semantic.NotifierProvider;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VistaCartaPanel extends AbstractPanel {
    @Inject
    private NotifierProvider notifierProvider;
    @Inject
    private PuzzleCardBuilder puzzleCardBuilder;
    private WebMarkupContainer container;
    private TextArea descriptionArea;
    private Image cardView;
    private ContextMenuBehavior menu;
    private Model<String> model;
    private OnAddCardCallback callback;
    private Card cardOnPreview;
    private Integer masterRule = 4;
    private List<IMenuItem> pendulumMenus = new ArrayList<>();
    private List<IMenuItem> extraMZMenus = new ArrayList<>();


    VistaCartaPanel(String id, OnAddCardCallback callback) {
        super(id);
        this.callback = callback;
        addContainer();
        addVista();
        addContextMenu();
    }

    private void addContainer() {
        container = new WebMarkupContainer("container");
        container.setOutputMarkupId(true);
        add(container);
    }

    private void addVista() {
        model = new Model<>("");

        PackageResourceReference resourceReference = new PackageResourceReference(getClass(), "unknown.jpg");
        cardView = new Image("cardView", resourceReference);
        cardView.setOutputMarkupId(true);
        container.add(cardView);

        descriptionArea = new TextArea<>("cardText", model);
        descriptionArea.setEnabled(false);
        descriptionArea.setOutputMarkupId(true);
        container.add(descriptionArea);
    }

    private void addContextMenu() {
        List<IMenuItem> list = new ArrayList<>();
        list.add(menuItem(0));
        list.add(menuItem(1));
        ContextMenu menu = new ContextMenu("menu", list);
        cardView.add(new ContextMenuBehavior(menu));
    }

    private IMenuItem menuItem(Integer user) {
        MenuItem userMenu = new MenuItem(user == 0 ? "Add to your" : "Add to opponent's");


        userMenu.addItem(new CardMenuItem("Hand"));
        userMenu.addItem(new CardMenuItem("Deck"));


        MenuItem usExtraDeckMenu = new MenuItem("Extra deck as");
        usExtraDeckMenu.addItem(new CardMenuItem("Extra deck monster"));
        if (masterRule >= 4) {
            usExtraDeckMenu.addItem(new CardMenuItem("Pendulum monster"));
        }
        userMenu.addItem(usExtraDeckMenu);


        userMenu.addItem(new CardMenuItem("Graveyard"));
        userMenu.addItem(new CardMenuItem("Banished"));


        MenuItem usMonsterMenu = new MenuItem("Monster zone");

        MenuItem usMAtkMenu = new MenuItem("In attack position");
        usMAtkMenu.addItem(new CardMenuItem("1"));
        usMAtkMenu.addItem(new CardMenuItem("2"));
        usMAtkMenu.addItem(new CardMenuItem("3"));
        usMAtkMenu.addItem(new CardMenuItem("4"));
        usMAtkMenu.addItem(new CardMenuItem("5"));
        usMonsterMenu.addItem(usMAtkMenu);

        MenuItem usMDefMenu = new MenuItem("In defense position");
        usMDefMenu.addItem(new CardMenuItem("1"));
        usMDefMenu.addItem(new CardMenuItem("2"));
        usMDefMenu.addItem(new CardMenuItem("3"));
        usMDefMenu.addItem(new CardMenuItem("4"));
        usMDefMenu.addItem(new CardMenuItem("5"));
        usMonsterMenu.addItem(usMDefMenu);

        MenuItem usMFaceDownMenu = new MenuItem("Face-down");
        usMFaceDownMenu.addItem(new CardMenuItem("1"));
        usMFaceDownMenu.addItem(new CardMenuItem("2"));
        usMFaceDownMenu.addItem(new CardMenuItem("3"));
        usMFaceDownMenu.addItem(new CardMenuItem("4"));
        usMFaceDownMenu.addItem(new CardMenuItem("5"));
        usMonsterMenu.addItem(usMFaceDownMenu);

        if (masterRule >= 4) {
            MenuItem usMExtraMZMenu = new MenuItem("In extra monster zone");
            usMExtraMZMenu.addItem(new CardMenuItem("Left (6)"));
            usMExtraMZMenu.addItem(new CardMenuItem("Right (7)"));
            usMonsterMenu.addItem(usMExtraMZMenu);
        }

        MenuItem usMXYZMenu = new MenuItem("Face-down");
        usMXYZMenu.addItem(new CardMenuItem("1"));
        usMXYZMenu.addItem(new CardMenuItem("2"));
        usMXYZMenu.addItem(new CardMenuItem("3"));
        usMXYZMenu.addItem(new CardMenuItem("4"));
        usMXYZMenu.addItem(new CardMenuItem("5"));
        usMXYZMenu.addItem(new CardMenuItem("6"));
        usMXYZMenu.addItem(new CardMenuItem("7"));
        usMonsterMenu.addItem(usMXYZMenu);

        userMenu.addItem(usMonsterMenu);


        MenuItem usSpellTrapMenu = new MenuItem("Spell/Trap zone");
        usSpellTrapMenu.addItem(new CardMenuItem("1"));
        usSpellTrapMenu.addItem(new CardMenuItem("2"));
        usSpellTrapMenu.addItem(new CardMenuItem("3"));
        usSpellTrapMenu.addItem(new CardMenuItem("4"));
        usSpellTrapMenu.addItem(new CardMenuItem("5"));
        usSpellTrapMenu.addItem(new CardMenuItem("As field spell"));
        userMenu.addItem(usSpellTrapMenu);


        if (masterRule >= 3) {
            MenuItem usPendulumScalesMenu = new MenuItem("Pendulum scale");
            usPendulumScalesMenu.addItem(new CardMenuItem("Left"));
            usPendulumScalesMenu.addItem(new CardMenuItem("Right"));
            userMenu.addItem(usPendulumScalesMenu);
        }

        return userMenu;
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

    public void cargar(Integer masterRule, AjaxRequestTarget target) {
        this.masterRule = masterRule;
        menu = new ContextMenuBehavior(new ContextMenu("menu", menuItemsList()));
        target.add(cardView);
        target.add(descriptionArea);
    }

    public class CardMenuItem extends MenuItem {
        private Integer idCard;
        private Integer user;
        private Integer zone;
        private String location;
        private String position;

        public CardMenuItem(String title, Integer id, Integer us, Integer zn, String loc, String pos) {
            super(title);
            idCard = id;
            user = us;
            zone = zn;
            location = loc;
            position = pos;
        }

        @Override
        public void onClick(AjaxRequestTarget target) {
            StringBuilder stringBuilder = new StringBuilder();
            PuzzleCard puzzleCard = puzzleCardBuilder.createPuzzleCard(idCard, user, zone, location, position);
            puzzleCardBuilder.addCard(puzzleCard, masterRule, stringBuilder);
            callback.onAddCard(target, stringBuilder);
        }
    }
}