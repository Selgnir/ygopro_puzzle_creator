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

@SuppressWarnings("CdiManagedBeanInconsistencyInspection")
class MainPanel extends AbstractPanel {
    @Inject
    private NotifierProvider notifierProvider;
    @Inject
    private CartasService cartasService;
    private BusquedaFiltroPanel busquedaFiltroPanel;
    private CampoPanel campoPanel;
    private DatosPanel datosPanel;
    private VistaCartaPanel vistaPanel;
    private ListModel<Card> listModel;
    private ListView<Card> listView;
    private WebMarkupContainer container;

    MainPanel(String id) {
        super(id);
        addCampo();
        addDatos();
        addFiltro();
        addVista();
        addCartas();
    }

    private void cargar() {
        List<Card> cards = cartasService.getCartasByExample(busquedaFiltroPanel.getBusquedaFiltroData());
        listModel.setObject(cards);
    }

    private void addCampo() {
        campoPanel = new CampoMR4Panel("fieldPanel");
        campoPanel.setOutputMarkupId(true);
        add(campoPanel);
    }

    private void addDatos() {
        datosPanel = new DatosPanel("dataPanel", (OnChangeCallback) ajaxRequestTarget -> {
            recargarCampo(datosPanel.getDatosPuzzle().getMasterRule());
            campoPanel.cargar();
            vistaPanel.cargar(datosPanel.getDatosPuzzle().getMasterRule(), ajaxRequestTarget);
            //otra opción es pasar ajaxRequestTarget por parámetro a campoPanel.cargar y que añada el container
            ajaxRequestTarget.add(campoPanel);
        });
        datosPanel.setOutputMarkupId(true);
        add(datosPanel);
    }

    private void addFiltro() {
        busquedaFiltroPanel = new BusquedaFiltroPanel("searchFilterPanel", (OnChangeCallback) ajaxRequestTarget -> {
            cargar();
            ajaxRequestTarget.add(container);
            ajaxRequestTarget.add(busquedaFiltroPanel);
        });
        busquedaFiltroPanel.setOutputMarkupId(true);
        add(busquedaFiltroPanel);
    }

    private void addVista() {
        vistaPanel = new VistaCartaPanel("viewPanel", (OnAddCardCallback) (ajaxRequestTarget, stringBuilder) -> {
            if (notificar(stringBuilder)) {
                campoPanel.cargar();
                //otra opción es pasar ajaxRequestTarget por parámetro a campoPanel.cargar y que añada el container
                ajaxRequestTarget.add(campoPanel);
            }
        });
        vistaPanel.setOutputMarkupId(true);
        add(vistaPanel);
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
                        vistaPanel.cargar(listItem.getModelObject());
                        ajaxRequestTarget.add(vistaPanel);
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

    private void recargarCampo(Integer masterRule) {
        switch (masterRule){
            case 1:
            case 2:
                campoPanel = new CampoMR1y2Panel("fieldPanel");
                break;
            case 3:
                campoPanel = new CampoMR3Panel("fieldPanel");
                break;
            case 4:
                campoPanel = new CampoMR4Panel("fieldPanel");
        }
        campoPanel.setOutputMarkupId(true);
    }

    private Boolean notificar(StringBuilder stringBuilder) {
        if (stringBuilder.length() > 0) {
            notifierProvider.getNotifier(getPage()).notify("Error", "<ul>" + stringBuilder.toString() + "</ul>");
            return false;
        } else {
            return true;
        }
    }

}
