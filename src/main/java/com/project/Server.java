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

        ResourceHandler resourceHandler = new ResourceHandler(new PathResourceManager(
                Paths.get(new File(".").getAbsolutePath()), 100))
                .setDirectoryListingEnabled(true)
                .addWelcomeFiles("index.html");
        server.addResourcePrefixPath("/", resourceHandler);

        //server.addResourcePrefixPath("/", resource(new ClassPathResourceManager(Server.class.getClassLoader())).addWelcomeFiles("index.html"));

        Undertow.Builder builder = Undertow.builder()
                .addHttpListener(8080, "0.0.0.0");

        server.start(builder);

        new PropertiesReader();

        if(Boolean.valueOf(PropertiesReader.properties.getProperty("db.migration"))){
            Migrations.run();
        }

    }

    public static void shutdownServer (){

        server.stop();
    }

}


