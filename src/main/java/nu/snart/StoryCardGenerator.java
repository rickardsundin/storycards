package nu.snart;

import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;

import javax.xml.transform.*;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

import static org.apache.commons.io.IOUtils.toInputStream;

/**
 * Generates printable story card for user story.
 *
 * The story is converted to a simple xml format,
 * which is the transformed by xslt to an XSL-FO document,
 * which is in turn transformed into a PDF file.
 */
public class StoryCardGenerator {

    public File generatePdf(Story story) throws TransformerException, IOException, FOPException {
        String storyXml = generateXml(story);
        String xslFo = generateFoFromXml(storyXml);
        File pdfFile = new File(story.id + ".pdf");
        generatePdfFromFo(xslFo, pdfFile);
        return pdfFile;
    }

    /**
     * Generate xml for a story.
     */
    String generateXml(Story story) {
        return "<story>" +
                "<header>" + story.id + " = " + story.points + " SPs" + System.lineSeparator() + story.title + "</header>" +
                "<body>" + story.userStory + "</body>" +
                "</story>";
    }

    /**
     * Transform a story in xml format to xsl-fo that will display nicely.
     */
    String generateFoFromXml(String storyXml) throws TransformerException {
        Source inputXml = new StreamSource(toInputStream(storyXml));
        Source xslt = new StreamSource(getClass().getResourceAsStream("/story2fo.xsl"));
        return transform(inputXml, xslt);
    }

    /**
     * Transform xml using xslt.
     */
    private String transform(Source inputXml, Source xslt) throws TransformerException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Result xmlOutput = new StreamResult(outputStream);

        Transformer transformer = TransformerFactory.newInstance().newTransformer(xslt);
        transformer.transform(inputXml, xmlOutput);
        return outputStream.toString();
    }

    /**
     * Generate a PDF from XSL-FO.
     */
    void generatePdfFromFo(String xslFo, File pdfFile) throws IOException, FOPException, TransformerException {
        OutputStream out = new BufferedOutputStream(new FileOutputStream(pdfFile));
        try {
            Fop fop = FopFactory.newInstance().newFop(MimeConstants.MIME_PDF, out);
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            Source source = new StreamSource(toInputStream(xslFo));
            Result result = new SAXResult(fop.getDefaultHandler());
            transformer.transform(source, result);
        } finally {
            out.close();
        }
    }
}
