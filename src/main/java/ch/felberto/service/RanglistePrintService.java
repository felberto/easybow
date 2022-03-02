package ch.felberto.service;

import ch.felberto.domain.Rangliste;
import com.lowagie.text.DocumentException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;

/**
 * Service for printing Rangliste.
 */
@Service
@Transactional
public class RanglistePrintService {

    private final Logger log = LoggerFactory.getLogger(RanglistePrintService.class);

    private static final String PDF_RESOURCES = "pdf-resources/";
    private final SpringTemplateEngine templateEngine;

    public RanglistePrintService(SpringTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public File generatePdf(Rangliste rangliste) throws IOException, DocumentException {
        Context context = getContext(rangliste);
        String html = loadAndFillTemplate(context, rangliste.getType());
        return renderPdf(html);
    }

    public File renderPdf(String html) throws IOException, DocumentException {
        File file = File.createTempFile("rangliste", ".pdf");
        OutputStream outputStream = new FileOutputStream(file);
        ITextRenderer renderer = new ITextRenderer(20f * 4f / 3f, 20);
        renderer.setDocumentFromString(html, new ClassPathResource(PDF_RESOURCES).getURL().toExternalForm());
        renderer.layout();
        renderer.createPDF(outputStream);
        outputStream.close();
        file.deleteOnExit();
        return file;
    }

    public Context getContext(Rangliste rangliste) {
        Context context = new Context();
        context.setVariable("rangliste", rangliste);

        String runde = "";
        String titleDoc = "";
        String title = "";
        String subTitle = "";
        switch (rangliste.getType()) {
            case 1:
                runde = "1. Runde";
                titleDoc = "Rangliste " + rangliste.getWettkampf().getName() + " " + rangliste.getWettkampf().getJahr() + " " + runde;
                title = "ZSAV " + rangliste.getWettkampf().getName() + " " + rangliste.getWettkampf().getJahr();
                subTitle = "Rangliste: " + runde;
                break;
            case 2:
                runde = "2. Runde";
                titleDoc = "Rangliste " + rangliste.getWettkampf().getName() + " " + rangliste.getWettkampf().getJahr() + " " + runde;
                title = "ZSAV " + rangliste.getWettkampf().getName() + " " + rangliste.getWettkampf().getJahr();
                subTitle = "Rangliste: " + runde;
                break;
            case 100:
                runde = "1. + 2. Runde";
                titleDoc = "Rangliste " + rangliste.getWettkampf().getName() + " " + rangliste.getWettkampf().getJahr() + " " + runde;
                title = "ZSAV " + rangliste.getWettkampf().getName() + " " + rangliste.getWettkampf().getJahr();
                subTitle = "Rangliste: " + runde;
                break;
            case 99:
                runde = "Final";
                titleDoc = "Rangliste " + rangliste.getWettkampf().getName() + " " + rangliste.getWettkampf().getJahr() + " " + runde;
                title = "ZSAV " + rangliste.getWettkampf().getName() + " " + rangliste.getWettkampf().getJahr();
                subTitle = "Rangliste: " + runde;
                break;
            case 101:
                runde = "Qualifikation Verbändefinal";
                titleDoc = "Rangliste " + rangliste.getWettkampf().getName() + " " + rangliste.getWettkampf().getJahr() + " " + runde;
                title = "ZSAV " + rangliste.getWettkampf().getName() + " " + rangliste.getWettkampf().getJahr();
                subTitle = "Rangliste: " + runde;
                break;
            default:
                break;
        }
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        context.setVariable("titleDoc", titleDoc);
        context.setVariable("title", title);
        context.setVariable("subTitle", subTitle);
        context.setVariable("anzahlQualiFinal", rangliste.getWettkampf().getAnzahlFinalteilnehmer());
        if (rangliste.getVfDate() != null) {
            context.setVariable("vfDate", formatter.format(rangliste.getVfDate()));
            context.setVariable("vfTime", rangliste.getVfTime());
            context.setVariable("vfOrt", rangliste.getVfOrt());
            context.setVariable("vfAnzahl", rangliste.getVfAnzahl());
        }
        context.setVariable("date", formatter.format(date));
        return context;
    }

    public String loadAndFillTemplate(Context context, int type) {
        switch (type) {
            case 1:
            case 2:
                return templateEngine.process("ranglisten/rangliste_1", context);
            case 100:
                return templateEngine.process("ranglisten/rangliste_100", context);
            case 99:
                //TODO error, resultate stimmen nicht, nochmals überprüfen
                return templateEngine.process("ranglisten/rangliste_99", context);
            case 101:
                //TODO final resultat fehlt
                return templateEngine.process("ranglisten/rangliste_101", context);
            default:
                break;
        }
        return null;
    }
}
