package com.ulquertech;

import com.ulquertech.dominio.Card;
import com.ulquertech.dominio.CartasService;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.util.ListModel;
import plataforma1.wicket.semantic.NotifierProvider;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

class MainPanel extends AbstractPanel {
    @Inject
    private NotifierProvider notifierProvider;
    @Inject
    private CartasService cartasService;
    private BusquedaFiltroPanel busquedaFiltroPanel;
    private ListModel<Card> listModel;
    private ListView<Card> listView;
    private WebMarkupContainer container;

    MainPanel(String id) {
        super(id);
        addPanels();
        addCartas();
    }

    private void cargar() {
        List<Card> cards = cartasService.getCartasByExample(busquedaFiltroPanel.getBusquedaFiltroData());
        listModel.setObject(cards);
    }

    private void addPanels() {
        CampoPanel campoPanel = new CampoPanel("fieldPanel");
        DatosPanel datosPanel = new DatosPanel("dataPanel");
        busquedaFiltroPanel = new BusquedaFiltroPanel("searchFilterPanel", (BusquedaFiltroPanel.Callback) ajaxRequestTarget -> {
            cargar();
            ajaxRequestTarget.add(container);
            ajaxRequestTarget.add(busquedaFiltroPanel);
        });
        busquedaFiltroPanel.setOutputMarkupId(true);
        add(campoPanel);
        add(datosPanel);
        add(busquedaFiltroPanel);
    }

    private void addCartas() {
        container = new WebMarkupContainer("container");
        container.setOutputMarkupId(true);
        add(container);

        listModel = new ListModel<>(new ArrayList<>());
        listView = new ListView<Card>("item", listModel) {
            @Override
            protected void populateItem(ListItem<Card> listItem) {
                listItem.add(new Label("name", listItem.getModelObject().getName()));
                listItem.add(new AjaxLink("view") {
                    @Override
                    public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                        ver(listItem.getModelObject());
                    }
                });
            }
        };
        container.add(listView);

        AbstractReadOnlyModel sizeModel = new AbstractReadOnlyModel() {
            @Override
            public Object getObject() {
                return listView.getList().size();
            }
        };
        add(new Label("results", sizeModel));
    }

    private void ver(Card card) {
    }

}
