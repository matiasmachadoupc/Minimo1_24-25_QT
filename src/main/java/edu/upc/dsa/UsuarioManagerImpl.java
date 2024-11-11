package edu.upc.dsa;

import edu.upc.dsa.models.Usuario;
import edu.upc.dsa.models.PuntoInteres;
import edu.upc.dsa.models.ElementType;
import edu.upc.dsa.models.UsuarioPuntoInteres;
import org.apache.log4j.Logger;

import java.util.*;

public class UsuarioManagerImpl implements UsuarioManager {
    private static UsuarioManager instance;
    private Map<String, Usuario> usuarios;
    private Map<String, PuntoInteres> puntosInteres;
    private List<UsuarioPuntoInteres> usuarioPuntoInteresList;
    final static Logger logger = Logger.getLogger(UsuarioManagerImpl.class);

    private UsuarioManagerImpl() {
        this.usuarios = new HashMap<>();
        this.puntosInteres = new HashMap<>();
        this.usuarioPuntoInteresList = new ArrayList<>();
    }

    public static UsuarioManager getInstance() {
        if (instance == null) {
            instance = new UsuarioManagerImpl();
        }
        return instance;
    }

    @Override
    public void addUsuario(Usuario usuario) {
        logger.info("addUsuario(" + usuario + ")");
        usuarios.put(usuario.getId(), usuario);
        logger.info("Usuario added: " + usuario);
    }

    @Override
    public List<Usuario> getUsuarios() {
        logger.info("getUsuarios()");
        List<Usuario> usuarioList = new ArrayList<>(usuarios.values());
        usuarioList.sort(Comparator.comparing(Usuario::getApellidos).thenComparing(Usuario::getNombre));
        logger.info("Usuarios retrieved: " + usuarioList);
        return usuarioList;
    }

    @Override
    public Usuario getUsuarioById(String id) {
        logger.info("getUsuarioById(" + id + ")");
        Usuario usuario = usuarios.get(id);
        if (usuario == null) {
            logger.error("Usuario not found: " + id);
        } else {
            logger.info("Usuario retrieved: " + usuario);
        }
        return usuario;
    }

    @Override
    public void addPuntoInteres(PuntoInteres puntoInteres) {
        logger.info("addPuntoInteres(" + puntoInteres + ")");
        String key = puntoInteres.getCoordenadaHorizontal() + "," + puntoInteres.getCoordenadaVertical();
        puntosInteres.put(key, puntoInteres);
        logger.info("PuntoInteres added: " + puntoInteres);
    }

    @Override
    public void registrarUsuarioPuntoInteres(String userId, int coordX, int coordY) {
        logger.info("registrarUsuarioPuntoInteres(userId: " + userId + ", coordX: " + coordX + ", coordY: " + coordY + ")");
        Usuario usuario = usuarios.get(userId);
        String key = coordX + "," + coordY;
        PuntoInteres puntoInteres = puntosInteres.get(key);

        if (usuario == null || puntoInteres == null) {
            logger.error("Usuario or PuntoInteres not found");
            throw new IllegalArgumentException("Usuario or PuntoInteres not found");
        }

        usuarioPuntoInteresList.add(new UsuarioPuntoInteres(usuario, puntoInteres));
        logger.info("UsuarioPuntoInteres registered: " + usuario + " -> " + puntoInteres);
    }

    @Override
    public List<PuntoInteres> getPuntosInteresByUsuario(String userId) {
        logger.info("getPuntosInteresByUsuario(" + userId + ")");
        List<PuntoInteres> result = new ArrayList<>();
        for (UsuarioPuntoInteres upi : usuarioPuntoInteresList) {
            if (upi.getUsuario().getId().equals(userId)) {
                result.add(upi.getPuntoInteres());
            }
        }
        logger.info("PuntosInteres retrieved for userId " + userId + ": " + result);
        return result;
    }

    @Override
    public List<Usuario> getUsuariosByPuntoInteres(int coordX, int coordY) {
        logger.info("getUsuariosByPuntoInteres(coordX: " + coordX + ", coordY: " + coordY + ")");
        List<Usuario> result = new ArrayList<>();
        String key = coordX + "," + coordY;
        PuntoInteres puntoInteres = puntosInteres.get(key);

        if (puntoInteres == null) {
            logger.error("PuntoInteres not found at coordinates: " + key);
            throw new IllegalArgumentException("PuntoInteres not found at coordinates: " + key);
        }

        for (UsuarioPuntoInteres upi : usuarioPuntoInteresList) {
            if (upi.getPuntoInteres().equals(puntoInteres)) {
                result.add(upi.getUsuario());
            }
        }
        logger.info("Usuarios retrieved for PuntoInteres at " + key + ": " + result);
        return result;
    }

    @Override
    public List<PuntoInteres> getPuntosInteresByTipo(ElementType tipo) {
        logger.info("getPuntosInteresByTipo(" + tipo + ")");
        List<PuntoInteres> result = new ArrayList<>();
        for (PuntoInteres pi : puntosInteres.values()) {
            if (pi.getTipo() == tipo) {
                result.add(pi);
            }
        }
        logger.info("PuntosInteres retrieved for tipo " + tipo + ": " + result);
        return result;
    }
}