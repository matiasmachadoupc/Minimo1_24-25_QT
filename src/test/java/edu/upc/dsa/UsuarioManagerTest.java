package edu.upc.dsa;

import edu.upc.dsa.models.Usuario;
import edu.upc.dsa.models.PuntoInteres;
import edu.upc.dsa.models.ElementType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.Assert.*;

public class UsuarioManagerTest {

    private UsuarioManager manager;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Before
    public void setUp() throws ParseException {
        manager = UsuarioManagerImpl.getInstance();
        manager.addUsuario(new Usuario("1", "John", "Doe", "john.doe@example.com", dateFormat.parse("1990-01-01")));
        manager.addUsuario(new Usuario("2", "Jane", "Smith", "jane.smith@example.com", dateFormat.parse("1992-02-02")));
        manager.addPuntoInteres(new PuntoInteres(10, 20, ElementType.DOOR));
        manager.addPuntoInteres(new PuntoInteres(30, 40, ElementType.TREE));
    }

    @After
    public void tearDown(){this.manager = null;}

    @Test
    public void addUsuarioTest() throws ParseException {
        assertEquals(2, manager.getUsuarios().size());

        manager.addUsuario(new Usuario("3", "Alice", "Johnson", "alice.johnson@example.com", dateFormat.parse("1995-03-03")));

        assertEquals(3, manager.getUsuarios().size());
    }

    @Test
    public void getUsuarioTest() {
        Usuario usuario = manager.getUsuarioById("1");
        assertNotNull(usuario);
        assertEquals("John", usuario.getNombre());
        assertEquals("Doe", usuario.getApellidos());
    }

    @Test
    public void getUsuariosTest() {
        List<Usuario> usuarios = manager.getUsuarios();
        assertEquals(2, usuarios.size());

        Usuario u = usuarios.get(0);
        assertEquals("John", u.getNombre());
        assertEquals("Doe", u.getApellidos());

        u = usuarios.get(1);
        assertEquals("Jane", u.getNombre());
        assertEquals("Smith", u.getApellidos());
    }

    @Test
    public void addPuntoInteresTest() {
        assertEquals(1, manager.getPuntosInteresByTipo(ElementType.DOOR).size());


        manager.addPuntoInteres(new PuntoInteres(50, 60, ElementType.COIN));

        List<PuntoInteres> puntos = manager.getPuntosInteresByTipo(ElementType.COIN);
        assertEquals(1, puntos.size());
        assertEquals(60, puntos.get(0).getCoordenadaHorizontal());
    }

    @Test
    public void registrarUsuarioPuntoInteresTest() {
        manager.registrarUsuarioPuntoInteres("1", 40, 30);
        List<PuntoInteres> puntos = manager.getPuntosInteresByUsuario("1");
        assertEquals(1, puntos.size());
        assertEquals(40, puntos.get(0).getCoordenadaHorizontal());
    }

    @Test
    public void getUsuariosByPuntoInteresTest() {
        manager.registrarUsuarioPuntoInteres("1", 40, 30);
        List<Usuario> usuarios = manager.getUsuariosByPuntoInteres(40, 30);
        assertEquals(1, usuarios.size());
        assertEquals("John", usuarios.get(0).getNombre());
    }

    @Test
    public void getPuntosInteresByTipoTest() {
        List<PuntoInteres> puntos = manager.getPuntosInteresByTipo(ElementType.DOOR);
        assertEquals(1, puntos.size());
        assertEquals(20, puntos.get(0).getCoordenadaHorizontal());
    }
}