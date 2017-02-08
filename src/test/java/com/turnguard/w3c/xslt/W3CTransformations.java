package com.turnguard.w3c.xslt;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import net.sf.saxon.TransformerFactoryImpl;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openrdf.rio.rdfxml.RDFXMLWriter;
import org.openrdf.rio.turtle.TurtleWriter;

/**
 *
 * @author turnguard
 */
public class W3CTransformations {
    
    private final TransformerFactoryImpl transformerFactory = new net.sf.saxon.TransformerFactoryImpl();
    private final File outputDirectory;
    public W3CTransformations() {
        ClassLoader classLoader = getClass().getClassLoader();
        File testClasses = new File(classLoader.getResource("./").getFile());
        outputDirectory = new File(testClasses.toString()+"/../rdf-output");
        if(!outputDirectory.exists()){
            outputDirectory.mkdirs();
        }        
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * @see https://www.w3.org/TR/ld-glossary/
     */
    //@Test
    public void w3c_ld_glossary_print_sys_out() {
        File xsl = new File(getClass().getClassLoader().getResource("w3c/ld-glossary/ld-glossary.xsl").getFile());
        File xml = new File(getClass().getClassLoader().getResource("w3c/ld-glossary/Linked Data Glossary.html").getFile());
        System.out.println("\n");
        System.out.println("xml:" + xml.getAbsolutePath());
        System.out.println("xsl:" + xsl.getAbsolutePath());
        System.out.println();
        XMLtoRDFConverter converter = new XMLtoRDFConverter(
                xml.getAbsolutePath(),
                xsl.getAbsolutePath(),
                this.transformerFactory);
        converter.run();
    }
    @Test
    public void w3c_ld_glossary_to_rdf_xml() throws IOException {
        File xsl = new File(getClass().getClassLoader().getResource("w3c/ld-glossary/ld-glossary.xsl").getFile());
        File xml = new File(getClass().getClassLoader().getResource("w3c/ld-glossary/Linked Data Glossary.html").getFile());
        System.out.println("\n");
        System.out.println("xml:" + xml.getAbsolutePath());
        System.out.println("xsl:" + xsl.getAbsolutePath());
        System.out.println();
        XMLtoRDFConverter converter = new XMLtoRDFConverter(
                xml.getAbsolutePath(),
                xsl.getAbsolutePath(),
                new RDFXMLWriter(new FileWriter(new File(this.outputDirectory,"ld-glossary.rdf"))),
                this.transformerFactory);
        converter.run();
    }
    
    @Test
    public void w3c_ld_glossary_to_turtle() throws IOException {
        File xsl = new File(getClass().getClassLoader().getResource("w3c/ld-glossary/ld-glossary.xsl").getFile());
        File xml = new File(getClass().getClassLoader().getResource("w3c/ld-glossary/Linked Data Glossary.html").getFile());
        System.out.println("\n");
        System.out.println("xml:" + xml.getAbsolutePath());
        System.out.println("xsl:" + xsl.getAbsolutePath());
        System.out.println();
        XMLtoRDFConverter converter = new XMLtoRDFConverter(
                xml.getAbsolutePath(),
                xsl.getAbsolutePath(),
                new TurtleWriter(new FileWriter(new File(this.outputDirectory,"ld-glossary.ttl"))),
                this.transformerFactory);
        converter.run();
    }    
    
}
