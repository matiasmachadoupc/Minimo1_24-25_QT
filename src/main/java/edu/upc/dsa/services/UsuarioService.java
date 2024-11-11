package edu.upc.dsa.services;

import edu.upc.dsa.UsuarioManager;
import edu.upc.dsa.UsuarioManagerImpl;
import edu.upc.dsa.models.Usuario;
import edu.upc.dsa.models.PuntoInteres;
import edu.upc.dsa.models.ElementType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value = "/usuarios", description = "Endpoint to Usuarios Service")
@Path("/usuarios")
public class UsuarioService {
    private UsuarioManager manager;

    public UsuarioService() {
        this.manager = UsuarioManagerImpl.getInstance();
    }

    @POST
    @ApiOperation(value = "add a new user", notes = "Adds a new user")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "User created successfully"),
            @ApiResponse(code = 400, message = "Invalid input")
    })
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUsuario(Usuario usuario) {
        this.manager.addUsuario(usuario);
        return Response.status(201).build();
    }

    @GET
    @ApiOperation(value = "get all users", notes = "Returns the list of all users")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = List.class),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsuarios() {
        List<Usuario> usuarios = this.manager.getUsuarios();
        GenericEntity<List<Usuario>> entity = new GenericEntity<List<Usuario>>(usuarios) {};
        return Response.status(200).entity(entity).build();
    }

    @GET
    @ApiOperation(value = "get user by ID", notes = "Returns a user by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = Usuario.class),
            @ApiResponse(code = 404, message = "User not found")
    })
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsuarioById(@PathParam("id") String id) {
        Usuario usuario = this.manager.getUsuarioById(id);
        if (usuario == null) {
            return Response.status(404).build();
        }
        return Response.status(200).entity(usuario).build();
    }

    @POST
    @ApiOperation(value = "add a new point of interest", notes = "Adds a new point of interest")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Point of interest created successfully"),
            @ApiResponse(code = 400, message = "Invalid input")
    })
    @Path("/puntosInteres")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPuntoInteres(PuntoInteres puntoInteres) {
        this.manager.addPuntoInteres(puntoInteres);
        return Response.status(201).build();
    }

    @POST
    @ApiOperation(value = "register user at point of interest", notes = "Registers a user at a point of interest")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "User registered successfully"),
            @ApiResponse(code = 404, message = "User or Point of Interest not found"),
    })
    @Path("/{userId}/puntosInteres")
    public Response registrarUsuarioPuntoInteres(@PathParam("userId") String userId, @QueryParam("coordX") int coordX, @QueryParam("coordY") int coordY) {
        try {
            this.manager.registrarUsuarioPuntoInteres(userId, coordX, coordY);
            return Response.status(201).build();
        } catch (IllegalArgumentException e) {
            return Response.status(404).build();
        }
    }

    @GET
    @ApiOperation(value = "get points of interest by user", notes = "Returns points of interest by user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = List.class),
            @ApiResponse(code = 404, message = "User not found")
    })
    @Path("/{userId}/puntosInteres")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPuntosInteresByUsuario(@PathParam("userId") String userId) {
        List<PuntoInteres> puntos = this.manager.getPuntosInteresByUsuario(userId);
        if (puntos == null) {
            return Response.status(404).build();
        }
        GenericEntity<List<PuntoInteres>> entity = new GenericEntity<List<PuntoInteres>>(puntos) {};
        return Response.status(200).entity(entity).build();
    }

    @GET
    @ApiOperation(value = "get users by point of interest", notes = "Returns users by point of interest")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = List.class),
            @ApiResponse(code = 404, message = "Point of interest not found")
    })
    @Path("/puntosInteres/{coordX}/{coordY}/usuarios")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsuariosByPuntoInteres(@PathParam("coordX") int coordX, @PathParam("coordY") int coordY) {
        try {
            List<Usuario> usuarios = this.manager.getUsuariosByPuntoInteres(coordX, coordY);
            GenericEntity<List<Usuario>> entity = new GenericEntity<List<Usuario>>(usuarios) {};
            return Response.status(200).entity(entity).build();
        } catch (IllegalArgumentException e) {
            return Response.status(404).build();
        }
    }

    @GET
    @ApiOperation(value = "get points of interest by type", notes = "Returns points of interest by type")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = List.class),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @Path("/puntosInteres/tipo/{tipo}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPuntosInteresByTipo(@PathParam("tipo") ElementType tipo) {
        List<PuntoInteres> puntos = this.manager.getPuntosInteresByTipo(tipo);
        GenericEntity<List<PuntoInteres>> entity = new GenericEntity<List<PuntoInteres>>(puntos) {};
        return Response.status(200).entity(entity).build();
    }
}