package ch.felberto.service;

import ch.felberto.domain.GroupRankingList;
import ch.felberto.domain.RankingList;
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
 * Service for printing RankingList.
 */
@Service
@Transactional
public class RankingListPrintService {

    private final Logger log = LoggerFactory.getLogger(RankingListPrintService.class);

    private static final String PDF_RESOURCES = "pdf-resources/";
    private final SpringTemplateEngine templateEngine;

    public RankingListPrintService(SpringTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public File generatePdf(RankingList rankingList) throws IOException, DocumentException {
        Context context;
        String html;
        switch (rankingList.getCompetition().getCompetitionType()) {
            case ZSAV_NAWU:
                context = getContext(rankingList);
                html = loadAndFillTemplate(context, rankingList.getType());
                break;
            case ZSAV_NAWU_GM:
                context = getContextZsavNawuGmSingle(rankingList);
                html = loadAndFillTemplateZsavNawuGmSingle(context);
                break;
            case EASV_WORLDCUP:
                context = getContextEasvWorldcup(rankingList);
                html = loadAndFillTemplateEasvWorldcup(context);
                break;
            case EASV_STAENDEMATCH:
                context = getContextEasvStaendematchSingle(rankingList);
                html = loadAndFillTemplateEasvStaendematchSingle(context);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + rankingList.getCompetition().getCompetitionType());
        }

        return renderPdf(html);
    }

    public File generatePdfGroup(GroupRankingList rankingList) throws IOException, DocumentException {
        Context context;
        String html;
        switch (rankingList.getCompetition().getCompetitionType()) {
            case ZSAV_NAWU_GM:
                context = getContextZsavNawuGmGroup(rankingList);
                html = loadAndFillTemplateZsavNawuGmGroup(context);
                break;
            case EASV_STAENDEMATCH:
                context = getContextEasvStaendematchGroup(rankingList);
                html = loadAndFillTemplateEasvStaendematchGroup(context);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + rankingList.getCompetition().getCompetitionType());
        }

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

    public Context getContext(RankingList rankingList) {
        Context context = new Context();
        context.setVariable("rangliste", rankingList);

        String runde = "";
        String titleDoc = "";
        String title = "";
        String subTitle = "";
        switch (rankingList.getType()) {
            case 1:
                runde = "1. Runde";
                titleDoc =
                    "Rangliste " + rankingList.getCompetition().getName() + " " + rankingList.getCompetition().getYear() + " " + runde;
                title = "ZSAV " + rankingList.getCompetition().getName() + " " + rankingList.getCompetition().getYear();
                subTitle = "Rangliste: " + runde;
                break;
            case 2:
                runde = "2. Runde";
                titleDoc =
                    "Rangliste " + rankingList.getCompetition().getName() + " " + rankingList.getCompetition().getYear() + " " + runde;
                title = "ZSAV " + rankingList.getCompetition().getName() + " " + rankingList.getCompetition().getYear();
                subTitle = "Rangliste: " + runde;
                break;
            case 100:
                runde = "1. + 2. Runde";
                titleDoc =
                    "Rangliste " + rankingList.getCompetition().getName() + " " + rankingList.getCompetition().getYear() + " " + runde;
                title = "ZSAV " + rankingList.getCompetition().getName() + " " + rankingList.getCompetition().getYear();
                subTitle = "Rangliste: " + runde;
                break;
            case 99:
                runde = "Final";
                titleDoc =
                    "Rangliste " + rankingList.getCompetition().getName() + " " + rankingList.getCompetition().getYear() + " " + runde;
                title = "ZSAV " + rankingList.getCompetition().getName() + " " + rankingList.getCompetition().getYear();
                subTitle = "Rangliste: " + runde;
                break;
            case 101:
                runde = "Qualifikation Verbändefinal";
                titleDoc =
                    "Rangliste " + rankingList.getCompetition().getName() + " " + rankingList.getCompetition().getYear() + " " + runde;
                title = "ZSAV " + rankingList.getCompetition().getName() + " " + rankingList.getCompetition().getYear();
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
        context.setVariable("anzahlQualiFinal", rankingList.getCompetition().getNumberOfFinalAthletes());
        if (rankingList.getVfDate() != null) {
            context.setVariable("vfDate", formatter.format(rankingList.getVfDate()));
            context.setVariable("vfTime", rankingList.getVfTime());
            context.setVariable("vfOrt", rankingList.getVfOrt());
            context.setVariable("vfAnzahl", rankingList.getVfAnzahl());
        }
        context.setVariable("date", formatter.format(date));
        return context;
    }

    public Context getContextEasvWorldcup(RankingList rankingList) {
        Context context = new Context();
        context.setVariable("rankingList", rankingList);

        String titleDoc = rankingList.getCompetition().getName() + " " + rankingList.getCompetition().getYear();
        String title = rankingList.getCompetition().getName() + " " + rankingList.getCompetition().getYear();
        String subTitle = "";
        switch (rankingList.getType()) {
            case 1:
                subTitle = "MEN";
                break;
            case 2:
                subTitle = "WOMEN";
                break;
            default:
                break;
        }
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        context.setVariable("titleDoc", titleDoc);
        context.setVariable("title", title);
        context.setVariable("subTitle", subTitle);
        context.setVariable("date", formatter.format(date));
        return context;
    }

    public Context getContextZsavNawuGmGroup(GroupRankingList rankingList) {
        Context context = new Context();
        context.setVariable("rankingList", rankingList);

        String titleDoc = "ZSAV " + rankingList.getCompetition().getName() + " " + rankingList.getCompetition().getYear();
        String title = rankingList.getCompetition().getName() + " " + rankingList.getCompetition().getYear();
        String subTitle = "Gruppenrangliste";
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        context.setVariable("titleDoc", titleDoc);
        context.setVariable("title", title);
        context.setVariable("subTitle", subTitle);
        context.setVariable("date", formatter.format(date));

        return context;
    }

    public Context getContextZsavNawuGmSingle(RankingList rankingList) {
        Context context = new Context();
        context.setVariable("rankingList", rankingList);

        String titleDoc = "ZSAV " + rankingList.getCompetition().getName() + " " + rankingList.getCompetition().getYear();
        String title = rankingList.getCompetition().getName() + " " + rankingList.getCompetition().getYear();
        String subTitle = "Einzelrangliste";
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        context.setVariable("titleDoc", titleDoc);
        context.setVariable("title", title);
        context.setVariable("subTitle", subTitle);
        context.setVariable("date", formatter.format(date));
        return context;
    }

    public Context getContextEasvStaendematchGroup(GroupRankingList rankingList) {
        Context context = new Context();
        context.setVariable("rankingList", rankingList);

        String titleDoc = "EASV " + rankingList.getCompetition().getName() + " " + rankingList.getCompetition().getYear();
        String title = rankingList.getCompetition().getName() + " " + rankingList.getCompetition().getYear();
        String subTitle = "Verbandsrangliste";
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        context.setVariable("titleDoc", titleDoc);
        context.setVariable("title", title);
        context.setVariable("subTitle", subTitle);
        context.setVariable("date", formatter.format(date));

        return context;
    }

    public Context getContextEasvStaendematchSingle(RankingList rankingList) {
        Context context = new Context();
        context.setVariable("rankingList", rankingList);

        String titleDoc = "EASV " + rankingList.getCompetition().getName() + " " + rankingList.getCompetition().getYear();
        String title = rankingList.getCompetition().getName() + " " + rankingList.getCompetition().getYear();
        String subTitle = "Einzelrangliste";
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        context.setVariable("titleDoc", titleDoc);
        context.setVariable("title", title);
        context.setVariable("subTitle", subTitle);
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

    public String loadAndFillTemplateEasvWorldcup(Context context) {
        return templateEngine.process("ranglisten/rangliste_easv_worldcup", context);
    }

    public String loadAndFillTemplateZsavNawuGmGroup(Context context) {
        return templateEngine.process("ranglisten/rangliste_zsav_nawu_gm_group", context);
    }

    public String loadAndFillTemplateZsavNawuGmSingle(Context context) {
        return templateEngine.process("ranglisten/rangliste_zsav_nawu_gm_single", context);
    }

    public String loadAndFillTemplateEasvStaendematchGroup(Context context) {
        return templateEngine.process("ranglisten/rangliste_easv_staendematch_group", context);
    }

    public String loadAndFillTemplateEasvStaendematchSingle(Context context) {
        return templateEngine.process("ranglisten/rangliste_easv_staendematch_single", context);
    }
}
