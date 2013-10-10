package org.dnt.whoami;

import com.google.code.morphia.logging.MorphiaLoggerFactory;
import com.google.code.morphia.logging.slf4j.SLF4JLogrImplFactory;
import org.dnt.whoami.dao.DaoClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.net.UnknownHostException;

/**
 * The Context Listener class, initialises the service on start-up
 *
 * @author dima
 * @since 8/4/13 9:04 PM
 */
public class WhoamiContextListener implements ServletContextListener {
    static final Logger log = LoggerFactory.getLogger(WhoamiContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // use slf4j logger with morphia
        MorphiaLoggerFactory.registerLogger(SLF4JLogrImplFactory.class);

        ServletContext sc = sce.getServletContext();
        String daoHost = sc.getInitParameter("daoHost");
        int daoPort = Integer.parseInt(sc.getInitParameter("daoPort"));
        String datastoreName = sc.getInitParameter("datastoreName");

        try {
            DaoClient.Instance.connect(daoHost, daoPort, datastoreName);
            log.info("Successfully connected to DAO {}", datastoreName);
        } catch (UnknownHostException e) {
            log.error("Unable to connect to dao.", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
