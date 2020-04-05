package com.project;

import com.project.api.ApplicationInit;
import com.project.migrations.Migrations;
import com.project.properties.PropertiesReader;
import io.undertow.Undertow;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.server.handlers.resource.PathResourceManager;
import io.undertow.server.handlers.resource.ResourceHandler;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;

import static io.undertow.Handlers.resource;

public class Server {

    private static Integer PORT_NUMBER = null;

    public static UndertowJaxrsServer server = null;

    public static void main(String[] args) {

        new PropertiesReader();

        handlerPort(args);

        server = new UndertowJaxrsServer();

        ResteasyDeployment deployment = new ResteasyDeployment();
        deployment.setApplicationClass(ApplicationInit.class.getName());
        deployment.setInjectorFactoryClass("org.jboss.resteasy.cdi.CdiInjectorFactory");

        DeploymentInfo deploymentInfo = server.undertowDeployment(deployment, "/");
        deploymentInfo.setClassLoader(Server.class.getClassLoader());
        deploymentInfo.setDeploymentName("challengee");
        deploymentInfo.setContextPath("/api");

        deploymentInfo.addListener(Servlets.listener(org.jboss.weld.environment.servlet.Listener.class));

        server.deploy(deploymentInfo);

        server.addResourcePrefixPath("/",
                resource(new ClassPathResourceManager(Server.class.getClassLoader()))
                        .addWelcomeFiles("index.html"));


        Undertow.Builder builder = Undertow.builder()
                .addHttpListener(PORT_NUMBER, PropertiesReader.properties.getProperty("server.host"));

        server.start(builder);

        System.out.println("Server Listen PORT > "+ PORT_NUMBER);

        if (Boolean.valueOf(PropertiesReader.properties.getProperty("db.migration"))) {
            Migrations.run();
        }

    }

    public static void shutdownServer() {

        server.stop();
    }


    public static void handlerPort (String[] args) {

        if(args != null && args.length >= 1) {
           PORT_NUMBER = Integer.valueOf(args[args.length - 1]);
           PropertiesReader.properties.setProperty("server.port", PORT_NUMBER.toString());
        }else {
            PORT_NUMBER = Integer.valueOf(PropertiesReader.properties.getProperty("server.port"));
        }
    }
}


