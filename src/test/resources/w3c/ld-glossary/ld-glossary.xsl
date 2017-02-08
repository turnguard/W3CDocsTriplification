<?xml version="1.0"?>
<xsl:stylesheet version="2.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xhtml="http://www.w3.org/1999/xhtml"
                xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
                xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#"
                xmlns:foaf="http://xmlns.com/foaf/spec/"
                xmlns:xsd="http://www.w3.org/2001/XMLSchema#"
                xmlns:dct="http://purl.org/dc/terms/"
                xmlns:sioc="http://rdfs.org/sioc/ns#"
                xmlns:skos="http://www.w3.org/2004/02/skos/core#">
    
    <xsl:template match="/">
        <!-- RDF Start -->
        <rdf:RDF>
            <xsl:apply-templates select="//xhtml:section[not(@class) and @id!='toc' and @id!='normative-references' and @id!='informative-references']"/>
        </rdf:RDF>
        <!-- RDF End -->	
    </xsl:template>

    <xsl:template match="xhtml:section">
        <!-- baseURL -> should end in # or / -->
        <xsl:variable name="baseURL">
            <xsl:text>http://data.turnguard.com/w3c/ld-glossary#</xsl:text>
        </xsl:variable>

        <!-- Entity URI (Could be created by java code or API call) -->
        <rdf:Description>
            <!-- creating URI from @id -->
            <xsl:attribute name="rdf:about">
                <xsl:value-of select="concat($baseURL,@id)"/>
            </xsl:attribute>
            <!-- rdf:type for now -->
            <rdf:type rdf:resource="http://www.w3.org/2004/02/skos/core#Concept"/>
            
            <!-- creating skos:description -->
            <xsl:variable name="description"
                          select="normalize-space(string-join(.//text()[not(ancestor::xhtml:h2)],''))" />
            <skos:description>
                <xsl:attribute name="xml:lang">
                    <xsl:text>en</xsl:text>
                </xsl:attribute>    
                <xsl:value-of select="$description"/>
            </skos:description>        
            
            <!-- creating skos:prefLabel -->            
            <xsl:apply-templates select="./xhtml:h2"/>
        
        </rdf:Description>
    </xsl:template>

    <xsl:template match="xhtml:h2">
        <xsl:analyze-string select="." regex="([a-zA-Z].*)">
            <xsl:matching-substring>
                <skos:prefLabel>
                    <xsl:attribute name="xml:lang">
                        <xsl:text>en</xsl:text>
                    </xsl:attribute>                        
                    <xsl:value-of select="regex-group(1)"/>
                </skos:prefLabel>
            </xsl:matching-substring>
        </xsl:analyze-string>         
    </xsl:template>
        
</xsl:stylesheet>
