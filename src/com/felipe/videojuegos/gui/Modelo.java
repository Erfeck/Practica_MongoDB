package com.felipe.videojuegos.gui;

import com.felipe.videojuegos.gui.base.Desarrollador;
import com.felipe.videojuegos.gui.base.Puntuacion;
import com.felipe.videojuegos.gui.base.Videojuego;
import com.felipe.videojuegos.util.Util;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * Clase encargada de gestionar la conexión con la base de datos utilizando Hibernate.
 * Proporciona métodos para realizar operaciones CRUD (crear, leer, actualizar, eliminar)
 * sobre las entidades mapeadas, como Desarrollador, Videojuego, Usuario, Ticket, etc.
 */
public class Modelo {

    private final static String DATABASE = "dbVideojuegos";
    private String ip;
    private String user;
    private String pwd;
    private String userDB;
    private MongoClient mongoClient;
    private MongoDatabase db;
    private MongoCollection coleccionDesarrolladores;
    private MongoCollection coleccionVideojuegos;
    private MongoCollection coleccionPuntuaciones;

    public void setValues() {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("config.properties")) {
            properties.load(input);
            ip = properties.getProperty("ip");
            user = properties.getProperty("user");
            pwd = properties.getProperty("pwd");
            userDB = properties.getProperty("userDB");
        } catch (IOException e) {
            System.out.println("Error al cargar el archivo de configuración: " + e.getMessage());
        }
    }

    public void conectarMongo() {
        MongoCredential credential = MongoCredential.createCredential(user, userDB, pwd.toCharArray());
        mongoClient = new MongoClient(new ServerAddress(ip, 27017), Arrays.asList(credential));
        db = mongoClient.getDatabase(DATABASE);
        coleccionDesarrolladores = db.getCollection("desarrollador");
        coleccionVideojuegos = db.getCollection("videojuego");
        coleccionPuntuaciones = db.getCollection("puntuacion");
    }

    public void desconectarMongo() {
        mongoClient.close();
    }

    public List<Desarrollador> getDevs() {
        List<Desarrollador> listaDevs = new ArrayList<>();

        Iterator<Document> it = coleccionDesarrolladores.find().iterator();
        while (it.hasNext()) {
            listaDevs.add(documentToDesarrollador(it.next()));
        }
        return listaDevs;
    }

    public List<Desarrollador> getDevs(String texto) {
        List<Desarrollador> listaDevs = new ArrayList<>();

        Document query = new Document();
        List<Document> listaCriterios = new ArrayList<>();
        listaCriterios.add(new Document("nombre", new Document("$regex", "/*" + texto + "/*")));
        listaCriterios.add(new Document("email", new Document("$regex", "/*" + texto + "/*")));
        query.append("$or", listaCriterios);

        Iterator<Document> iterator = coleccionDesarrolladores.find(query).iterator();
        while (iterator.hasNext()) {
            listaDevs.add(documentToDesarrollador(iterator.next()));
        }
        return listaDevs;
    }

    public List<Videojuego> getJuegos() {
        List<Videojuego> listaJuegos = new ArrayList<>();

        Iterator<Document> it = coleccionVideojuegos.find().iterator();
        while (it.hasNext()) {
            listaJuegos.add(documentToVideojuego(it.next()));
        }
        return listaJuegos;
    }

    public List<Videojuego> getJuegos(String texto) {
        List<Videojuego> listaJuegos = new ArrayList<>();

        Document query = new Document();
        List<Document> listaCriterios = new ArrayList<>();
        listaCriterios.add(new Document("titulo", new Document("$regex", "/*" + texto + "/*")));
        listaCriterios.add(new Document("genero", new Document("$regex", "/*" + texto + "/*")));
        listaCriterios.add(new Document("plataforma", new Document("$regex", "/*" + texto + "/*")));
        query.append("$or", listaCriterios);

        Iterator<Document> iterator = coleccionVideojuegos.find(query).iterator();
        while (iterator.hasNext()) {
            listaJuegos.add(documentToVideojuego(iterator.next()));
        }
        return listaJuegos;
    }

    public List<Puntuacion> getRatings() {
        List<Puntuacion> listaRatings = new ArrayList<>();

        Iterator<Document> it = coleccionPuntuaciones.find().iterator();
        while (it.hasNext()) {
            Puntuacion unaPuntuacion = documentToPuntuacion(it.next());
            if (unaPuntuacion != null) {
                listaRatings.add(unaPuntuacion);
            }

        }
        return listaRatings;
    }

    public List<Puntuacion> getRatings(String texto) {
        List<Puntuacion> listaRatings = new ArrayList<>();

        Document query = new Document();
        List<Document> listaCriterios = new ArrayList<>();
        listaCriterios.add(new Document("fuente", new Document("$regex", "/*" + texto + "/*")));
        query.append("$or", listaCriterios);

        Iterator<Document> iterator = coleccionPuntuaciones.find(query).iterator();
        while (iterator.hasNext()) {
            Puntuacion unaPuntuacion = documentToPuntuacion(iterator.next());
            if (unaPuntuacion != null) {
                listaRatings.add(unaPuntuacion);
            }
        }
        return listaRatings;
    }

    public Desarrollador getDevById(ObjectId idDev) {
        Document document = (Document) coleccionDesarrolladores
                .find(Filters.eq("_id", idDev))
                .first();
        return documentToDesarrollador(document);
    }

    public Videojuego getJuegoById(ObjectId idJuego) {
        Document document = (Document) coleccionVideojuegos
                .find(Filters.eq("_id", idJuego))
                .first();
        return documentToVideojuego(document);
    }

    public Puntuacion getRatingById(ObjectId idRating) {
        Document document = (Document) coleccionPuntuaciones
                .find(Filters.eq("_id", idRating))
                .first();
        return documentToPuntuacion(document);
    }

    public List<Videojuego> getJuegosDeUnDev(ObjectId objectIdDev) {
        List<Videojuego> listaJuegosDeUnDesarrollador = new ArrayList<>();

        List<Videojuego> listaJuegos = getJuegos();
        for (Videojuego unJuego : listaJuegos) {
            for (Desarrollador unDesarrolladorDeUnJuego : unJuego.getDesarrolladores()) {
                if (unDesarrolladorDeUnJuego.getObjectId().toString().equals(objectIdDev.toString())) {
                    listaJuegosDeUnDesarrollador.add(unJuego);
                    break;
                }
            }
        }
        return listaJuegosDeUnDesarrollador;
    }

    public void guardarDev(Desarrollador desarrollador) {
        coleccionDesarrolladores.insertOne(desarrolladorToDocument(desarrollador));
    }

    public void guardarJuego(Videojuego videojuego) {
        coleccionVideojuegos.insertOne(videojuegoToDocument(videojuego));
    }

    public void guardarPuntuacion(Puntuacion puntuacion) {
        coleccionPuntuaciones.insertOne(puntuacionToDocument(puntuacion));
    }

    public void modificarDev(Desarrollador desarrollador) {
        coleccionDesarrolladores.replaceOne(
                new Document("_id", desarrollador.getObjectId()),
                desarrolladorToDocument(desarrollador));
    }

    public void modificarJuego(Videojuego videojuego) {
        coleccionVideojuegos.replaceOne(
                new Document("_id", videojuego.getObjectId()),
                videojuegoToDocument(videojuego));
    }

    public void modificarPuntuacion(Puntuacion puntuacion) {
        coleccionPuntuaciones.replaceOne(
                new Document("_id", puntuacion.getObjectId()),
                puntuacionToDocument(puntuacion));
    }

    public void borrarDev(Desarrollador desarrollador) {
        coleccionDesarrolladores.deleteOne(desarrolladorToDocument(desarrollador));
    }

    public void borrarJuego(Videojuego videojuego) {
        coleccionVideojuegos.deleteOne(videojuegoToDocument(videojuego));
    }

    public void borrarPuntuacion(Puntuacion puntuacion) {
        coleccionPuntuaciones.deleteOne(puntuacionToDocument(puntuacion));
    }

    public boolean existsDevById(ObjectId idDev) {
        long count = coleccionDesarrolladores.count(Filters.eq("_id", idDev));
        return count > 0;
    }

    public boolean existsGameById(Document document) {
        ObjectId idJuego = document.getObjectId("videojuego");
        long count = coleccionVideojuegos.count(Filters.eq("_id", idJuego));
        return count > 0;
    }

    private Desarrollador documentToDesarrollador(Document document) {
        Desarrollador unDesarrollador = new Desarrollador();
        unDesarrollador.setObjectId(document.getObjectId("_id"));
        unDesarrollador.setNombre(document.getString("nombre"));
        unDesarrollador.setEmail(document.getString("email"));
        unDesarrollador.setExperiencia(document.getInteger("experiencia"));
        unDesarrollador.setTipoDesarrollo(document.getString("tipo_desarrollo"));
        return unDesarrollador;
    }

    private Videojuego documentToVideojuego(Document document) {
        Videojuego unVideojuego = new Videojuego();
        unVideojuego.setObjectId(document.getObjectId("_id"));
        unVideojuego.setTitulo(document.getString("titulo"));
        unVideojuego.setGenero(document.getString("genero"));
        unVideojuego.setPrecio(document.getDouble("precio"));
        unVideojuego.setPlataforma(document.getString("plataforma"));
        unVideojuego.setFechaLanzamiento(Util.parsearFecha(document.getString("fecha_lanzamiento")));
        List<ObjectId> objectIdDesarrolladores = (List<ObjectId>) document.get("desarrolladores");
        List<Desarrollador> desarrolladores = new ArrayList<>();
        for (ObjectId unObjectID : objectIdDesarrolladores) {
            if (existsDevById(unObjectID)) {
                desarrolladores.add(getDevById(unObjectID));
            }
        }
        unVideojuego.setDesarrolladores(desarrolladores);
        return unVideojuego;
    }

    private Puntuacion documentToPuntuacion(Document document) {
        Puntuacion unaPuntuacion = new Puntuacion();
        unaPuntuacion.setObjectId(document.getObjectId("_id"));
        //unaPuntuacion.setVideojuego(documentToVideojuego(document.get("videojuego",Document.class)));
        unaPuntuacion.setVideojuego(getJuegoById(document.getObjectId("videojuego")));

        if (!existsGameById(document)) {
            return null;
        }
        unaPuntuacion.setFuente(document.getString("fuente"));
        unaPuntuacion.setNota(document.getDouble("puntuacion"));
        unaPuntuacion.setFechaPuntuacion(Util.parsearFecha(document.getString("fecha_puntuacion")));
        return unaPuntuacion;
    }

    private Document desarrolladorToDocument(Desarrollador desarrollador) {
        Document document = new Document();
        document.append("nombre", desarrollador.getNombre());
        document.append("email", desarrollador.getEmail());
        document.append("experiencia", desarrollador.getExperiencia());
        document.append("tipo_desarrollo", desarrollador.getTipoDesarrollo());
        return document;
    }

    private Document videojuegoToDocument(Videojuego videojuego) {
        Document document = new Document();
        document.append("titulo", videojuego.getTitulo());
        document.append("genero", videojuego.getGenero());
        document.append("precio", videojuego.getPrecio());
        document.append("plataforma", videojuego.getPlataforma());
        document.append("fecha_lanzamiento", Util.formatearFecha(videojuego.getFechaLanzamiento()));
        List<ObjectId> objectIdDesarrolladores = new ArrayList<>();
        for (Desarrollador unDev : videojuego.getDesarrolladores()) {
            objectIdDesarrolladores.add(unDev.getObjectId());
        }
        document.append("desarrolladores", objectIdDesarrolladores);
        return document;
    }

    private Document puntuacionToDocument(Puntuacion puntuacion) {
        Document document = new Document();
        document.append("videojuego", puntuacion.getVideojuego().getObjectId());
        document.append("fuente", puntuacion.getFuente());
        document.append("puntuacion", puntuacion.getNota());
        document.append("fecha_puntuacion", Util.formatearFecha(puntuacion.getFechaPuntuacion()));
        return document;
    }
}