<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
        version="1.0"
        xmlns:fo="http://www.w3.org/1999/XSL/Format"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <!--  defines the layout master  -->
    <xsl:template match="/story">
        <fo:root>
            <fo:layout-master-set>
                <fo:simple-page-master master-name="first" page-height="21cm" page-width="29.7cm" margin-top="1cm"
                                       margin-bottom="2cm" margin-left="2.5cm" margin-right="2.5cm">
                    <fo:region-body margin-top="1cm"/>
                    <fo:region-before extent="1cm"/>
                    <fo:region-after extent="1.5cm"/>
                </fo:simple-page-master>
            </fo:layout-master-set>
            <!--  starts actual layout  -->
            <fo:page-sequence master-reference="first">
                <fo:flow flow-name="xsl-region-body">
                    <fo:block font-size="44pt"
                              font-family="sans-serif"
                              color="white"
                              background-color="#4f81bd"
                              padding="5mm"
                              space-before="5mm"
                              space-after="5mm"
                              linefeed-treatment="preserve">
                        <xsl:apply-templates select="header"/>
                    </fo:block>

                    <fo:block font-family="verdana"
                              font-size="36pt"
                              linefeed-treatment="preserve">
                        <xsl:apply-templates select="body"/>
                    </fo:block>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>