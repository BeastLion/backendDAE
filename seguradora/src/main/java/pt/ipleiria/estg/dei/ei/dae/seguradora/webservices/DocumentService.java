package pt.ipleiria.estg.dei.ei.dae.seguradora.webservices;


import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import pt.ipleiria.estg.dei.ei.dae.seguradora.DTOs.DocumentDTO;
import pt.ipleiria.estg.dei.ei.dae.seguradora.Exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.seguradora.ejbs.DocumentBean;
import pt.ipleiria.estg.dei.ei.dae.seguradora.ejbs.OccurrenceBean;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Document;
import pt.ipleiria.estg.dei.ei.dae.seguradora.entities.Users.User;
import pt.ipleiria.estg.dei.ei.dae.seguradora.security.Authenticated;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Path("documents")
@Authenticated
public class DocumentService {
    @EJB
    private OccurrenceBean occurrenceBean;
    @EJB
    private DocumentBean documentBean;
    @Context
    private SecurityContext securityContext;

    @POST
    @Path("")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response upload(MultipartFormDataInput input, Long id) throws IOException, MyEntityNotFoundException {
        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        var username = securityContext.getUserPrincipal().getName();
        List<InputPart> inputParts = uploadForm.get("file");
        List<Document> documents = new LinkedList<Document>();
        for (InputPart inputPart : inputParts) {
            MultivaluedMap<String, String> headers = inputPart.getHeaders();
            String filename = getFilename(headers);
            // convert the uploaded file to inputstream
            InputStream inputStream = inputPart.getBody(InputStream.class, null);
            byte[] bytes = IOUtils.toByteArray(inputStream);
            String homedir = System.getProperty("user.home");
            String dirpath = homedir + File.separator + "uploads" + File.separator + username ;
            mkdirIfNotExists(dirpath);
            String filepath = dirpath + File.separator + filename;
            writeFile(bytes, filepath);
            var document = documentBean.create(filepath, filename, id);
            documents.add(document);
        }
        return Response.ok(DocumentDTO.from(documents)).build();
    }

    private void mkdirIfNotExists(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

/*
    @GET
    @Path("download/{id}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response download(@PathParam("id") Long id) throws MyEntityNotFoundException {
        var document = documentBean.find(id);

        if (document == null)
            throw new MyEntityNotFoundException("Document not found");

        // TODO: Can I download this? - Security Breach here! How do you fix it?
        var response = Response.ok(new File(document.getFilepath()));
        response.header("Content-Disposition", "attachment;filename=" + document.getFilename());
        return response.build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDocuments(@PathParam("id") Long id) throws MyEntityNotFoundException {
        var username = securityContext.getUserPrincipal().getName();
        var documents = documentBean.getOccurenceDocuments(id);
        return Response.ok(DocumentDTO.from(documents)).build();
    }
    @GET
    @Path("exists")
    public Response hasDocuments() throws MyEntityNotFoundException {
        Principal principal = securityContext.getUserPrincipal();

        var username = securityContext.getUserPrincipal().getName();


        if (student == null)
            throw new MyEntityNotFoundException("Student not found");

        return Response.status(Response.Status.OK)
                .entity(!student.getDocuments().isEmpty())
                .build();
    }

    */
    private String getFilename(MultivaluedMap<String, String> headers) {
        var contentDisposition = headers.getFirst("Content-Disposition").split(";");
        for (String filename : contentDisposition) {
            if ((filename.trim().startsWith("filename"))) {
                String[] name = filename.split("=");
                return name[1].trim().replaceAll("\"", "");
            }
        }
        return "unknown";
    }

    private void writeFile(byte[] content, String filename) throws IOException {
        var file = new File(filename);
        if (!file.exists()) {
            file.createNewFile();
        }

        FileOutputStream fop = new FileOutputStream(file);
        fop.write(content);
        fop.flush();
        fop.close();
    }

}
