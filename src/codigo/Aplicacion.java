/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codigo;

import datos.Enemy;
import datos.Gem;
import datos.Place;
import datos.Ranking;
import datos.Superheroe;
import datos.User;
import excepciones.ExcepcionApp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que contiene el código principal de la aplicación
 *
 * @author alu2017363
 */
public class Aplicacion {

    //Indica si un usuario ha inciado sesión
    private boolean login = false;
    //Clase controlador que maneja todas las operaciones de la base de datos
    private Bddcontrol controlador;
    //Lista de tipos de gemas
    private String tiposgemas[] = {"Mind Gem", "Power Gem", "Reality Gem", "Soul Gem", "Space Gem", "Time Gem"};
    //Guarda el usuario que ha iniciado sesión
    private User usuario = null;
    boolean ganado = false;

    /**
     * Muestra superheroes
     *
     * @param entrada Parámetros , solo se comprueba que la longitud sea 1
     * @throws ExcepcionApp
     * @throws SQLException
     */
    private void muestrasuperheroes(String[] entrada) throws ExcepcionApp, SQLException {
        if (entrada.length == 1) {
            ArrayList<Superheroe> superheroes = new ArrayList<Superheroe>();
            //Se obtienen los superheroes
            superheroes = controlador.consultasuperheroes();
            //Se muestran los superheroes
            Metodos.muestrasuperheroes(superheroes);

        } else {
            throw new ExcepcionApp(ExcepcionApp.NUMPARAMETROS);
        }
    }

    /**
     * Método que interpreta los comandos que introduce el usuario
     *
     * @param entrada Entrada del usuario separada por palabras o comandos
     */
    public void procesa(String[] entrada) {
        //Guarda la dirección donde quiere ir el usuario
        String direccion;
        //Guarda el tipo de gema en las opciones que lo requieren
        String tipogema;
        ArrayList<String> transaccion = new ArrayList<String>();
        //Si hay más de 0 parámetros
        if (entrada.length > 0) {
            try {
                //En caso de no haber usuario logueado
                if (!login) {
                    switch (entrada[0].toLowerCase()) {
                        case "v":
                            //Se muestran los superheroes
                            muestrasuperheroes(entrada);
                            break;
                        case "r":
                            //Registro de usuario
                            if (entrada.length == 4) {
                                if (!entrada[1].equals("null")) {
                                    //Funciona si hay 4 parámetros
                                    //Si el usuario no existe
                                    if (!controlador.usuarioexiste(entrada[1])) {
                                        //Y existe el superheroe
                                        if (controlador.existesuperheroe(entrada[3])) {
                                            //Se crea el usuario nuevo en memoria
                                            usuario = Metodos.creausuario(Arrays.copyOfRange(entrada, 1, entrada.length));
                                            //Se registra el usuario en la base de datos
                                            controlador.registrausuario(usuario, transaccion);
                                            //Se ponen 6 gemas
                                            controlador.ponseisgemas(usuario, tiposgemas, transaccion);
                                            controlador.ejecutatransaccion(transaccion);
                                            System.out.println("User registrered.");

                                        } else {
                                            throw new ExcepcionApp(ExcepcionApp.SUPERHEROENOEXISTE);
                                        }
                                    } else {
                                        throw new ExcepcionApp(ExcepcionApp.USUARIOEXISTE);
                                    }
                                } else {
                                    throw new ExcepcionApp(ExcepcionApp.VALORNOPERMITIDO);
                                }

                            } else {
                                throw new ExcepcionApp(ExcepcionApp.NUMPARAMETROS);
                            }

                            break;
                        case "l":
                            //Login
                            if (entrada.length == 3) {
                                //Funciona si hay 3 parámetros
                                if (controlador.iniciasesion(entrada[1], entrada[2])) {
                                    //Si el login es correcto se inicia sesión
                                    login = true;
                                    System.out.println("Sesión iniciada.");
                                    ganado = false;
                                    //Se lee la información del usuario
                                    usuario = controlador.dameinfouser(entrada[1]);
                                    //Se muestra la información del lugar
                                    muestrainfo(usuario.getPlace(), usuario.getUsername());
                                    if (controlador.compruebaganado(usuario)) {
                                        System.out.println("You win, you have all gems.");
                                        ganado = true;
                                    }
                                }
                            } else {
                                throw new ExcepcionApp(ExcepcionApp.NUMPARAMETROS);
                            }
                            break;
                        case "x":
                            System.out.println("Adios.");
                            break;
                        case "g":
                        case "b":
                        case "d":
                        case "k":
                        case "n":
                        case "s":
                        case "w":
                        case "e":
                            //Las opciones que requieren login dan error si no hay usuario logueado.
                            throw new ExcepcionApp(ExcepcionApp.NOLOGIN);
                        default:
                            throw new ExcepcionApp(ExcepcionApp.COMANDOINCORRECTO);
                    }
                } else {
                    //Si un usuario ha iniciado sesión
                    switch (entrada[0].toLowerCase()) {
                        case "v":
                            //Mostrar superheroes
                            if (!ganado) {
                                muestrasuperheroes(entrada);
                            } else {
                                throw new ExcepcionApp(ExcepcionApp.JUEGOTERMINADO);
                            }

                            break;
                        case "g":
                            //Recoger gema, requiere 3 parámetros
                            if (entrada.length == 3) {
                                if (!ganado) {
                                    //Se obtiene el tipo de gema separada con un espacio
                                    tipogema = entrada[1] + " " + entrada[2];
                                    //Se recoge la gema de ese tipo para ese usuario si se puede
                                    recogegema(tipogema, usuario);
                                } else {
                                    throw new ExcepcionApp(ExcepcionApp.JUEGOTERMINADO);
                                }

                            } else {
                                throw new ExcepcionApp(ExcepcionApp.NUMPARAMETROS);
                            }
                            break;
                        case "b":
                            //Lucha, funciona con dos parámetros
                            if (entrada.length == 2) {
                                if (!ganado) {
                                    //Se inicia l lucha del usuario contra el villano
                                    lucha(usuario, entrada[1]);
                                } else {
                                    throw new ExcepcionApp(ExcepcionApp.JUEGOTERMINADO);
                                }

                            } else {
                                throw new ExcepcionApp(ExcepcionApp.NUMPARAMETROS);
                            }

                            break;
                        case "d":
                            //Borrar usuario
                            if (entrada.length == 2) {
                                //Funciona con dos parámetros
                                if (controlador.iniciasesion(usuario.getUsername(), entrada[1])) {
                                    //Si el login es correcto
                                    controlador.borrarusuario(usuario.getUsername());
                                    //Se borra el usuario
                                    login = false;
                                    System.out.println("User deleted.");
                                } else {
                                    throw new ExcepcionApp(ExcepcionApp.PASSDELETEWRONG);
                                }
                            } else {
                                throw new ExcepcionApp(ExcepcionApp.NUMPARAMETROS);
                            }
                            break;
                        case "k":
                            //Se muestra el ranking
                            ranking();
                            break;
                        case "x":
                            System.out.println("Adios.");
                            break;
                        case "r":
                        case "l":
                            //Opciones que solo se pueden consultar sin haber hecho login dan error con login
                            throw new ExcepcionApp(ExcepcionApp.LOGGED);
                        default:
                            //Si no era ninguna de las anteriores se comprueba que el usuario no ha introducido una dirección
                            direccion = entrada[0].toLowerCase();

                            if (direccion.equals("n") || direccion.equals("s") || direccion.equals("w") || direccion.equals("e")) {
                                //Si el usuario ha introducido una dirección este se mueve si puede.
                                mueve(usuario, direccion);
                            } else {
                                throw new ExcepcionApp(ExcepcionApp.COMANDOINCORRECTO);
                            }
                    }
                }
            } catch (ExcepcionApp ex) {
                System.out.println(ex.getMessage());
            } catch (SQLException ex) {
                System.out.println("Error en la base de datos.");
                Logger.getLogger(Aplicacion.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("Introduce algo.");
        }
    }

    /**
     * Crea y muestra el ranking
     *
     * @throws SQLException
     */
    private void ranking() throws SQLException {
        ArrayList<Ranking> ranking = new ArrayList<>();
        //Se obtienen todos los usuarios
        ArrayList<User> usuarios = controlador.dametodosusuarios();
        ArrayList<Gem> gemas;

        //Para cada usuario
        for (int cont = 0; cont < usuarios.size(); cont++) {
            //Se obtienen las gemas del usuario
            gemas = controlador.damegemausuario(usuarios.get(cont).getUsername());
            if (gemas.size() > 0) {
                //Si el usuario tiene gemas se añade al ranking con sus datos y el número de gemas.
                ranking.add(new Ranking(usuarios.get(cont).getUsername(), usuarios.get(cont).getSuperhero(), gemas.size(), usuarios.get(cont).getLevel(), usuarios.get(cont).getPoints()));
            }
        }
        //Se muestra el ranking.
        Metodos.muestraranking(ranking);
    }

    /**
     * Lucha del jugador contra el villanoo
     *
     * @param usuario Usuario que ha iniciado sesión
     * @param nombreenemigo Nombre del villano que está en el lugar del usuario
     * @throws SQLException
     * @throws ExcepcionApp
     */
    private void lucha(User usuario, String nombreenemigo) throws SQLException, ExcepcionApp {
        //Se recoge el villano en el lugar donde está el usuario
        Enemy villano = controlador.damevillanolugarnombre(usuario.getPlace(), nombreenemigo);
        String ataquea, ataqueb;
        int victoriasa = 0;
        int victoriasb = 0;
        int cuentacincuenta = 0;
        int turnosa, turnosb;
        int ganador;
        int nivelusuarioanterior = 0;
        //Si existe el villano
        if (villano != null) {
            //Se inicia la lucha
            System.out.println("Fight begins.");
            //Los turnos máximos de cada luchador se obtienen del nivel
            turnosa = usuario.getLevel();
            turnosb = villano.getLevel();
            nivelusuarioanterior = usuario.getLevel();

            //Mientras a uno de los luchadores le queden turnos se hacen luchas
            while (turnosa > 0 && turnosb > 0) {
                //Se informa de los ataques que tiene cada uno.
                System.out.println("You have " + usuario.getLevel() + " attacks.");
                System.out.println(villano.getName() + " has " + villano.getLevel() + " attacks.");
                //se deciden aleatoriamente los tipos de ataque que se lanzarán
                ataquea = Metodos.obtenataque();
                ataqueb = Metodos.obtenataque();
                //Se muestran los ataques
                System.out.println(usuario.getUsername() + " attack: " + ataquea);
                System.out.println(villano.getName() + " attack: " + ataqueb);
                //Se calcula el ganador
                ganador = Metodos.obtenganador(ataquea, ataqueb);
                if (ganador == 0) {
                    //Si gana el usuario
                    System.out.println(usuario.getUsername() + " wins.");
                    //Se suman las victorias
                    victoriasa++;
                    cuentacincuenta++;
                    if (cuentacincuenta == 50) {
                        //Cada cincuenta victorias el usuario sube un nivel
                        usuario.setLevel(usuario.getLevel() + 1);

                        cuentacincuenta = 0;
                    }

                } else if (ganador == 1) {
                    //Si gana el villano
                    System.out.println(villano.getName() + " wins.");
                    //Se suman las victorias del villano
                    victoriasb++;
                } else if (ganador == 2) {
                    //Empate
                    System.out.println("Tie.");
                }
                //Se restan turnos
                turnosa--;
                turnosb--;
            }
            //Cuando termina la lucha
            System.out.println("Fight Finished!");

            //Se muestran las victorias de cada uno
            System.out.println(usuario.getUsername() + ": " + victoriasa + " wins. - " + nombreenemigo + ": " + victoriasb + " wins.");
            String lugarnuevo = dameunlugar(villano.getPlace());
            if (victoriasa > victoriasb) {
                //Si ha ganado el usuario
                System.out.println(usuario.getUsername() + " wins.");
                //El usuario gana 5 de nivel
                usuario.setLevel(usuario.getLevel() + 5);

                try {
                    controlador.iniciatransaccion();
                    controlador.actualizanivelusuario(usuario);
                    //El villano pierde las gemas creadas por el jugador y quedan en null
                    controlador.quitagemas(villano, usuario.getUsername());

                    //El villano se mueve a otro lugar
                    controlador.cambialugarvillanosinmovergemas(villano, lugarnuevo);
                    //Las gemas del villano le acompañan
                    controlador.muevegemasvillano(villano, lugarnuevo);

                } catch (SQLException ex) {
                    controlador.vuelveatras();
                } finally {
                    controlador.finalizatransaccion();
                }
                System.out.println("The enemy has lost their gems.");
                System.out.println("You win 5 points.");
                System.out.println("Your points: " + usuario.getLevel());

            } else if (victoriasa < victoriasb) {
                //Si gana el villano
                System.out.println(nombreenemigo + " wins.");
                //el usuario pierde 2 de nivel
                usuario.setLevel(usuario.getLevel() - 2);
                //El nivel nunca bajará de 0
                if (usuario.getLevel() < 0) {
                    usuario.setLevel(0);
                }
                try {

                    controlador.iniciatransaccion();

                    controlador.actualizanivelusuario(usuario);

                    //El villano se lleva las gemas del usuario y se mueve junto a ellas al lugar nuevo
                    controlador.pongemasusuariovillanolugar(usuario, villano, lugarnuevo);
                    //El villano se mueve
                    controlador.cambialugarvillanosinmovergemas(villano, lugarnuevo);
                    //Todas las gemas del villano se mueven con el
                    controlador.muevegemasvillano(villano, lugarnuevo);

                } catch (SQLException ex) {
                    controlador.vuelveatras();
                } finally {
                    controlador.finalizatransaccion();
                }
            } else {
                try {
                    if (nivelusuarioanterior != usuario.getLevel()) {
                        controlador.actualizanivelusuario(usuario);
                        //El villano se mueve a un lugar nuevo sin mover las gemas.
                        controlador.cambialugarvillanosinmovergemas(villano, lugarnuevo);
                        controlador.muevegemasvillano(villano, lugarnuevo);
                        //empate
                        System.out.println("Tie.");
                    }
                } catch (SQLException ex) {
                    controlador.vuelveatras();
                } finally {
                    controlador.finalizatransaccion();
                }

            }
            muestrainfo(lugarnuevo,usuario.getUsername());
        } else {
            //Caso en que el enemigo no existe en ese lugar
            throw new ExcepcionApp(ExcepcionApp.NOENEMIGOESTENOMBRE);
        }
        
    }

    /**
     * Da un lugar aleatorio excluyendo el que se le pasa
     *
     * @param excluye Nombre del lugar a excluir
     * @return Devuelve el nombre del lugar aleatorio
     * @throws SQLException
     */
    private String dameunlugar(String excluye) throws SQLException {
        Random rn = new Random();
        ArrayList<Place> lugares;
        //Se leen todos los lugares
        lugares = controlador.dametodoslugares();
        //Se quita el lugar a excluir
        Metodos.quitalugar(lugares, excluye);
        int aleatorio = rn.nextInt(lugares.size());
        //Se devuelve el lugar aleatorio
        return lugares.get(aleatorio).getName();
    }

    /**
     * Recoge una gema
     *
     * @param tipogema Tipo de gema a recoger
     * @param usuario Usuario que ha iniciado sesión
     * @throws ExcepcionApp
     * @throws SQLException
     */
    private void recogegema(String tipogema, User usuario) throws ExcepcionApp, SQLException {
        boolean sal = false;
        boolean encontrado = false;
        ArrayList<Gem> gemas;
        int cont = 0;
        //Se leen todos los villanos
        ArrayList<Enemy> villanos = controlador.dametodosvillanos();

        //Se obtienen todas las gemas del lugar donde está el usuario que tienen el nombre requerido
        gemas = controlador.damegemalugarnombre(tipogema, usuario);
        
        if (gemas.size() > 0) {
            //Si existen gemas se recorren estas buscando una que se pueda coger
            do {
                if (cont < gemas.size()) {
                    //if (Metodos.puedocogergema(gemas.get(cont), usuario, villanos)) {             
                    if (Metodos.puedocogergema(gemas.get(cont))) {
                        //Si la gema se puede coger modifico el propietario de la gema por el usuario 
                        controlador.modificapropietariogema(gemas.get(cont), usuario);
                        System.out.println("You have got the gem.");
                        encontrado = true;
                        sal = true;
                        muestrainfo(usuario.getPlace(),usuario.getUsername());
                        if (controlador.compruebaganado(usuario)) {
                            System.out.println("You win, you have all gems.");
                            ganado = true;
                        }
                    } else {
                        cont++;
                    }
                } else {
                    sal = true;
                }
            } while (!sal);
            if (!encontrado) {
                //Si ninguna gema se puede coger se lanza excepción.
                throw new ExcepcionApp(ExcepcionApp.NOSEENCUENTRAGEMA);
            }
        } else {
            //Si no hay gemas se lanza excepción.
            throw new ExcepcionApp(ExcepcionApp.NOSEENCUENTRAGEMA);
        }
    }

    /**
     * Muestra la información de un lugar.
     *
     * @param lugar Nombre del lugar a mostrar la información
     * @throws SQLException
     */
    private void muestrainfo(String lugar, String usuario) throws SQLException {
        //Muestra información del lugar.
        muestrainfolugar(lugar);
        //Muestra información de los villanos del lugar.
        muestravillanos(lugar);
        //Muestra gemas del lugar.
        muestragemas(lugar, usuario);
        //Muestra direcciones posibles a las que se puede ir.
        muestradirecciones(lugar);
    }

    /**
     * Mueve el usuario de lugar en una dirección concreta
     *
     * @param usuario Usuario que ha iniciado sesión
     * @param direccion Dirección a la que se va a dirigir.
     * @throws SQLException
     * @throws ExcepcionApp
     */
    private void mueve(User usuario, String direccion) throws SQLException, ExcepcionApp {
        //Se obtiene la información del lugar
        Place lugar = controlador.damelugar(usuario.getPlace());
        ArrayList<String> direcciones = new ArrayList<>();
        //Si el usuario he elegido una dirección no posible se lanza excepción.
        //Se obtiene el destino si la elección ha sido correcta.
        String destino = null;
        switch (direccion) {
            case "n":
                if (lugar.getNorth() == null) {
                    throw new ExcepcionApp(ExcepcionApp.DIRECCIONINCORRECTA);
                } else {
                    destino = lugar.getNorth();
                }
                break;
            case "s":
                if (lugar.getSouth() == null) {
                    throw new ExcepcionApp(ExcepcionApp.DIRECCIONINCORRECTA);
                } else {
                    destino = lugar.getSouth();
                }
                break;
            case "e":
                if (lugar.getEast() == null) {
                    throw new ExcepcionApp(ExcepcionApp.DIRECCIONINCORRECTA);
                } else {
                    destino = lugar.getEast();
                }
                break;
            case "w":
                if (lugar.getWest() == null) {
                    throw new ExcepcionApp(ExcepcionApp.DIRECCIONINCORRECTA);
                } else {
                    destino = lugar.getWest();
                }
                break;
            default:

        }
        try {
            controlador.iniciatransaccion();
            //Se cambia el lugar del jugador al destino correcto
            controlador.cambialugarjugador(usuario, destino);
            controlador.muevegemasjugador(usuario, destino);
        } catch (SQLException ex) {
            controlador.vuelveatras();
        } finally {
            controlador.finalizatransaccion();
        }

        //Se muestra la información del nuevo lugar.
        muestrainfo(destino, usuario.getUsername());
        usuario.setPlace(destino);
    }

    /**
     * Muestra las direcciones a las que se puede ir desde un lugar.
     *
     * @param nombrelugar Nombre del lugar
     * @throws SQLException
     */
    private void muestradirecciones(String nombrelugar) throws SQLException {
        //Se obtiene la información del lugar
        Place lugar = controlador.damelugar(nombrelugar);
        if (lugar != null) {
            //Si el lugar existe se muestran las direcciones del lugar.
            Metodos.muestradirecciones(lugar);
        } else {
            System.out.println("Error el lugar donde está el jugador no existe.");
        }
    }

    /**
     * Muestra la información de un lugar
     *
     * @param nombrelugar Nombre del lugar
     * @throws SQLException
     */
    private void muestrainfolugar(String nombrelugar) throws SQLException {
        //Se obtiene la información del lugar.
        Place lugar = controlador.damelugar(nombrelugar);
        if (lugar != null) {
            //Si el lugar existe se muestra el nombre y su descripción.
            System.out.println("Lugar - " + lugar.getName());
            System.out.println("Descripción - " + lugar.getDescription());
        } else {
            System.out.println("Error el lugar donde está el jugador no existe.");
        }
    }

    /**
     * Muestra los villanos que hay en un lugar
     *
     * @param nombrelugar Nombre del lugar
     * @throws SQLException
     */
    private void muestravillanos(String nombrelugar) throws SQLException {
        ArrayList<Enemy> villanoslugar = new ArrayList<>();
        //Se obtiene la información del lugar
        Place lugar = controlador.damelugar(nombrelugar);
        if (lugar != null) {
            //Si el lugar existe se obtienen los villanos del lugar.
            villanoslugar = controlador.damevillanoslugar(nombrelugar);
            //Se muestran los villanos del lugar.
            Metodos.muestravillanos(villanoslugar);
        } else {
            System.out.println("Error el lugar donde está el jugador no existe.");
        }
    }

    /**
     * Muestra las gemas que hay en un lugar.
     *
     * @param nombrelugar Nombre del lugar
     * @throws SQLException
     */
    private void muestragemas(String nombrelugar, String usuario) throws SQLException {
        ArrayList<Gem> gemaslugar = new ArrayList<>();
        //Se obtiene la información de lugar.
        Place lugar = controlador.damelugar(nombrelugar);
        if (lugar != null) {
            //Si el lugar existe se obtienen las gemas que hay en ese lugar.
            gemaslugar = controlador.damegemaslugar(nombrelugar, usuario);
            //Se muestran las gemas
            Metodos.muestragemas(gemaslugar);
        } else {
            System.out.println("Error el lugar donde está el jugador no existe.");
        }
    }

    /**
     * Constructor de la clase, se inicializa el controlador que maneja la base
     * de datos.
     *
     * @throws SQLException
     */
    public Aplicacion() throws SQLException {
        controlador = new Bddcontrol();
    }

    /**
     * Al cerrar la aplicación se cierra el controlador.
     *
     * @throws SQLException
     */
    public void cerrar() throws SQLException {
        controlador.disconnect();

    }

}
