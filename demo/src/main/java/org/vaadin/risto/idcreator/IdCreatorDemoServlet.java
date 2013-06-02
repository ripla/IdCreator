package org.vaadin.risto.idcreator;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import com.vaadin.server.VaadinServlet;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/*", initParams = { @WebInitParam(name = "UI", value = "org.vaadin.risto.idcreator.IdCreatorDemoUI") })
public class IdCreatorDemoServlet extends VaadinServlet {

}
