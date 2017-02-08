package com.turnguard.w3c.xslt;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import net.sf.saxon.TransformerFactoryImpl;
import org.openrdf.model.Statement;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFHandler;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.RDFParser;
import org.openrdf.rio.Rio;
import org.openrdf.rio.helpers.RDFHandlerBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author http://www.turnguard.com/turnguard
 * 
 * note that the xsl stylesheet must produce valid application/rdf+xml .
 */
public class XMLtoRDFConverter implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(XMLtoRDFConverter.class);    
    private final String xmlPath;
    private final String xslPath;
    private final RDFHandler handler;
    private final TransformerFactoryImpl transformerFactory;
    
    public XMLtoRDFConverter(String xmlPath, String xslPath, TransformerFactoryImpl transformerFactory){        
        this.xmlPath = xmlPath;
        this.xslPath = xslPath;        
        this.transformerFactory = transformerFactory;
        handler = new RDFHandlerBase(){
            @Override
            public void handleStatement(Statement st) throws RDFHandlerException {
                System.out.println(st);
            }
        };
        
    }
    
    public XMLtoRDFConverter(String xmlPath, String xslPath, RDFHandler handler, TransformerFactoryImpl transformerFactory){
        this.xmlPath = xmlPath;
        this.xslPath = xslPath;
        this.transformerFactory = transformerFactory;
        this.handler = handler;
    }    
    
    @Override
    public void run() {        
        Source xmlSource;
        Source xslSource;
        Transformer transformer;
        ByteArrayOutputStream outputStream = null;
        ByteArrayInputStream inputStream = null;
        RDFParser parser = Rio.createParser(RDFFormat.RDFXML);

        try {
            xmlSource = new StreamSource((new java.net.URL("file", "", xmlPath).openStream()));
            xslSource = new StreamSource((new java.net.URL("file", "", xslPath).openStream()));
            transformer = transformerFactory.newTransformer(xslSource);
            outputStream = new ByteArrayOutputStream();
            transformer.transform(xmlSource, new StreamResult(outputStream));  
            parser.setRDFHandler(handler);                
            inputStream = new ByteArrayInputStream(outputStream.toByteArray()); 
            parser.parse(inputStream, "");
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);            
        } finally {
            if(outputStream!=null){
                try {
                    outputStream.close();
                } catch (IOException ex) {
                    log.error(ex.getMessage(), ex);
                }
            }
            if(inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException ex) {
                    log.error(ex.getMessage(), ex);
                }
            }            
        }
    }
}
