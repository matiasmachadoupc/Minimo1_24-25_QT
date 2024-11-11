package edu.upc.dsa;

import edu.upc.dsa.models.Usuario;
import edu.upc.dsa.models.PuntoInteres;
import edu.upc.dsa.models.ElementType;

import java.util.List;

public interface UsuarioManager {
    // Añadir un usuario
    void addUsuario(Usuario usuario);

    // Listar todos los usuarios ordenados alfabéticamente
    List<Usuario> getUsuarios();

    // Consultar la información de un usuario usando su identificador
    Usuario getUsuarioById(String id);

    // Añadir un punto de interés en el mapa
    void addPuntoInteres(PuntoInteres puntoInteres);

    // Registrar que un usuario pasa por un punto de interés
    void registrarUsuarioPuntoInteres(String userId, int coordX, int coordY);

    // Consultar los puntos de interés por los que un usuario ha pasado
    List<PuntoInteres> getPuntosInteresByUsuario(String userId);

    // Listar los usuarios que han pasado por un punto de interés
    List<Usuario> getUsuariosByPuntoInteres(int coordX, int coordY);

    // Consultar los puntos de interés del mapa que sean de un tipo determinado
    List<PuntoInteres> getPuntosInteresByTipo(ElementType tipo);
}