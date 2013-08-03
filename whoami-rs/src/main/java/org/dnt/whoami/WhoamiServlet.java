package org.dnt.whoami;

import org.dnt.whoami.dao.DaoClient;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.net.UnknownHostException;

/**
 * TODO: add class description
 * @author dima
 * @since  5/24/13 4:44 AM
 * Date: 5/23/13
 */

public class WhoamiServlet extends HttpServlet {

    private String daoHost;
    private int daoPort;

    public WhoamiServlet() {
        this.daoHost = DaoClient.defaultHost;
        this.daoPort = DaoClient.defaultPort;
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);    //To change body of overridden methods use File | Settings | File Templates.
        this.daoHost = config.getInitParameter("daoHost");
        this.daoPort = Integer.parseInt(config.getInitParameter("daoPort"));

        try {
            DaoClient.Instance.connect(daoHost, daoPort);
        } catch (UnknownHostException e) {
            throw new ServletException("Unable to connect to dao.", e);
        }
    }
}
