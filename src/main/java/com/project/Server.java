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

import java.io.File;
import java.nio.file.Paths;

import static io.undertow.Handlers.resource;

public class Server {

    public static UndertowJaxrsServer server = null;

    public static void main(String[] args) {

        new PropertiesReader();
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

        Integer portNumber = null;

        if(args != null && args.length > 0){

            portNumber = Integer.getInteger(args[0]);

        }else {

            portNumber = Integer.valueOf(PropertiesReader.properties.getProperty("server.port"));

        }

        Undertow.Builder builder = Undertow.builder()
                .addHttpListener(portNumber, PropertiesReader.properties.getProperty("server.host"));

        server.start(builder);

        System.out.println("Server Listen PORT > "+ portNumber);

        if (Boolean.valueOf(PropertiesReader.properties.getProperty("db.migration"))) {
            Migrations.run();
        }

    }

    public static void shutdownServer() {

        server.stop();
    }

}


