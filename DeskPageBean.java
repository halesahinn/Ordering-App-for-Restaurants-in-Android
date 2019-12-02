package org.cse486.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import org.cse486.domain.Desk;
import org.cse486.service.DeskDao;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import javax.annotation.PostConstruct;
import javax.el.MethodExpression;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ManagedBean
@ViewScoped
public class DeskPageBean implements Serializable {

    private List<Desk> desks;

    @ManagedProperty("#{deskDao}")
    private DeskDao deskDao;

    private String newDeskName;
    private MethodExpression generateQrCode;

    @PostConstruct
    public void init() {
        this.desks = deskDao.getDesks();
    }

    public List<Desk> getDesks() {
        return desks;
    }

    public void setDesks(List<Desk> desks) {
        this.desks = desks;
    }

    public DeskDao getDeskDao() {
        return deskDao;
    }

    public void setDeskDao(DeskDao deskDao) {
        this.deskDao = deskDao;
    }

    public void getAddNew() {
        Desk newDesk = new Desk();
        newDesk.setDesk(this.newDeskName);
        newDesk.setAvailable(true);
        deskDao.addNewDesk(newDesk);
        this.newDeskName = "";
        this.desks = deskDao.getDesks();

        FacesContext.getCurrentInstance().addMessage("Desk is added to selected category", new FacesMessage("Desk is added to selected category"));


    }

    public void delete(Desk desk) {

        deskDao.delete(desk);
        this.desks = deskDao.getDesks();

        FacesContext.getCurrentInstance().addMessage("Desk is deleted successfully", new FacesMessage("Desk is deleted successfully"));

    }

    public String getNewDeskName() {
        return newDeskName;
    }

    public void setNewDeskName(String newDeskName) {
        this.newDeskName = newDeskName;
    }

    public StreamedContent getGenerateQrCode(Desk desk) {

        int size = 250;
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        String requestURL = request.getRequestURL().toString();
        String url = requestURL.substring(0, requestURL.lastIndexOf("/"));

        String myCodeText = "";
        InetAddress IP = null;
        try {
            IP = InetAddress.getLocalHost();
            Map<String, Object> loginParams = new HashMap<>();
            loginParams.put("server", request.getLocalAddr());
            loginParams.put("port", request.getLocalPort());
            loginParams.put("apiBase", "api");
            loginParams.put("desk", desk.getId());
            loginParams.put("longitude", "-122.084");
            loginParams.put("latitude", "37.422");
            Gson gson = new GsonBuilder().create();
            myCodeText = gson.toJson(loginParams);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        System.out.println("IP of my system is := " + IP.getHostAddress());
        try {

            Map<EncodeHintType, Object> hintMap = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
            hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");

            // Now with zxing version 3.2.1 you could change border size (white border size to just 1)
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix byteMatrix = qrCodeWriter.encode(myCodeText, BarcodeFormat.QR_CODE, size,
                    size, hintMap);
            int CrunchifyWidth = byteMatrix.getWidth();
            BufferedImage image = new BufferedImage(CrunchifyWidth, CrunchifyWidth,
                    BufferedImage.TYPE_INT_RGB);
            image.createGraphics();

            Graphics2D graphics = (Graphics2D) image.getGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, CrunchifyWidth, CrunchifyWidth);
            graphics.setColor(Color.BLACK);

            for (int i = 0; i < CrunchifyWidth; i++) {
                for (int j = 0; j < CrunchifyWidth; j++) {
                    if (byteMatrix.get(i, j)) {
                        graphics.fillRect(i, j, 1, 1);
                    }
                }
            }

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(image, "png", os);
            InputStream is = new ByteArrayInputStream(os.toByteArray());
            StreamedContent file = new DefaultStreamedContent(is, "image/png", desk.getId() + "_code.png");

            return file;

        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("\n\nYou have successfully created QR Code.");
        return null;

    }
}
