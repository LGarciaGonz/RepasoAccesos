package classes;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.sql.Date;
import java.util.ArrayList;

public class PedidoHandler extends DefaultHandler {

    //variable donde almacenar pedidos
    private ArrayList<Pedido> pedidos = new ArrayList<Pedido>();
    //Pedido que leo y que añado al arraylist
    private Pedido pedidoAux;
    //para almacenar el texto contenido en un nodo texto
    private StringBuilder buffer = new StringBuilder();

    public ArrayList<Pedido> getPedidos() {
        return pedidos;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        switch (qName) {
            case "pedidos":
                break;
            case "pedido":
                pedidoAux = new Pedido();
                break;
            case "total", "fecha", "cliente", "comercial":
                buffer.delete(0, buffer.length());
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName) {
            case "pedidos":
                break;
            case "pedido":
                pedidos.add(pedidoAux);
                break;
            case "total":
                pedidoAux.setTotal(Double.parseDouble(buffer.toString()));
                break;
            case "fecha":
                pedidoAux.setFecha(Date.valueOf(buffer.toString()));
                break;
            case "cliente":
                pedidoAux.setIdCliente(Integer.parseInt(buffer.toString()));
                break;
            case "comercial":
                pedidoAux.setIdComercial(Integer.parseInt(buffer.toString()));
                break;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        buffer.append(ch, start, length);
    }

}
